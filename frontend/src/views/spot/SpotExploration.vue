<template>
  <div class="spot-exploration">
    <!-- Main Content -->
    <div class="main-content">
      <!-- Sidebar Filters -->
      <FilterSidebar :total-count="totalCount" @filter-change="handleFilterChange" />

      <!-- Spot Cards Grid -->
      <main class="spots-grid">
        <!-- Tab Navigation -->
        <div class="tabs">
          <button
            class="tab"
            :class="{ active: activeTab === 'events' }"
            @click="changeTab('events')"
          >
            행사&팝업 스토어
          </button>
          <button
            class="tab"
            :class="{ active: activeTab === 'places' }"
            @click="changeTab('places')"
          >
            여행지
          </button>
        </div>

        <!-- Loading State -->
        <div v-if="loading" class="loading-state">
          <div class="spinner"></div>
          <p>스팟을 불러오는 중...</p>
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="error-state">
          <p>{{ error }}</p>
          <button @click="loadSpots" class="retry-btn">다시 시도</button>
        </div>

        <!-- Empty State -->
        <div v-else-if="spots.length === 0" class="empty-state">
          <p>검색 결과가 없습니다.</p>
        </div>

        <!-- Grid of Spot Cards -->
        <div v-else class="cards-container">
          <SpotCard
            v-for="spot in spots"
            :key="spot.id"
            :spot="spot"
            @click="handleSpotClick"
            @favorite="handleFavorite"
          />
        </div>

        <!-- Pagination -->
        <div v-if="!loading && spots.length > 0" class="pagination">
          <button
            class="page-btn"
            :disabled="currentPage === 1"
            @click="changePage(currentPage - 1)"
          >
            이전
          </button>
          <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
          <button class="page-btn" :disabled="isLastPage" @click="changePage(currentPage + 1)">
            다음
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter } from "vue-router";
import FilterSidebar from "@/components/spot/FilterSidebar.vue";
import SpotCard from "@/components/SpotCard.vue";
import spotApi from "@/api/spot/spotApi";

const router = useRouter();
const activeTab = ref("events");
const filters = ref({});
const spots = ref([]);
const loading = ref(false);
const error = ref(null);

// Pagination
const currentPage = ref(1);
const pageSize = ref(18);
const totalCount = ref(0);
const totalPages = ref(0);
const isLastPage = ref(false);

// 탭 변경
const changeTab = (tab) => {
  activeTab.value = tab;
  currentPage.value = 1; // 페이지 1로 초기화
  loadSpots();
};

// 페이지 변경
const changePage = (page) => {
  currentPage.value = page;
  loadSpots();
  window.scrollTo({ top: 0, behavior: "smooth" });
};

// 필터를 API 파라미터로 변환
const buildApiParams = () => {
  const params = {
    page: currentPage.value,
    size: pageSize.value,
  };

  // 탭에 따른 타입 설정
  if (activeTab.value === "events") {
    params.type = ["FESTIVAL", "POPUP"];
  } else {
    params.type = ["ATTRACTION"];
  }

  // 날짜
  if (filters.value.dateFrom) {
    params.startDate = filters.value.dateFrom;
  }
  if (filters.value.dateTo) {
    params.endDate = filters.value.dateTo;
  }

  // 날씨
  const weatherConditions = [];
  if (filters.value.weather?.clear) weatherConditions.push("맑음");
  if (filters.value.weather?.goodAir) weatherConditions.push("좋음");
  if (weatherConditions.length > 0) {
    params.weather = weatherConditions;
  }

  // 별점
  const selectedRatings = [];
  if (filters.value.stars) {
    for (let i = 1; i <= 5; i++) {
      if (filters.value.stars[i]) {
        selectedRatings.push(i);
      }
    }
  }
  if (selectedRatings.length > 0) {
    params.rating = selectedRatings;
  }

  // 좋아요수
  if (filters.value.likes && filters.value.likes > 0) {
    params.likesCount = filters.value.likes;
  }

  // 검색어
  if (filters.value.searchTerm) {
    params.search = filters.value.searchTerm;
  }

  // 태그
  if (filters.value.tags && filters.value.tags.length > 0) {
    // # 제거
    params.tag = filters.value.tags.map((tag) => tag.replace("#", ""));
  }

  // 지역
  if (filters.value.selectedRegions && filters.value.selectedRegions.length > 0) {
    const regionIds = [];
    filters.value.selectedRegions.forEach((region) => {
      if (region.sidoId === "all" && region.allSidoIds) {
        // 전체 시도
        regionIds.push(...region.allSidoIds);
      } else if (region.gugunId === "all" && region.allGugunIds) {
        // 특정 시도의 전체 구군
        regionIds.push(...region.allGugunIds);
      } else if (region.gugunId) {
        // 특정 구군
        regionIds.push(region.gugunId);
      }
    });
    if (regionIds.length > 0) {
      params.region = [...new Set(regionIds)]; // 중복 제거
    }
  }

  // 정렬
  const sortMap = {
    popular: "POPULAR",
    latest: "LATEST",
    rating: "RATING_DESC",
    likes: "LIKES_DESC",
  };
  if (filters.value.sort) {
    params.sort = sortMap[filters.value.sort] || "LATEST";
  }

  return params;
};

// 스팟 로드
const loadSpots = async () => {
  loading.value = true;
  error.value = null;

  try {
    const params = buildApiParams();
    const response = await spotApi.getSpots(params);

    spots.value = response.data;
    totalCount.value = response.totalElements;
    totalPages.value = response.totalPages;
    isLastPage.value = response.last;
    currentPage.value = response.page; // API 응답의 page 값 사용 (1-based)
  } catch (err) {
    error.value = "스팟을 불러오는데 실패했습니다.";
    console.error("Load spots error:", err);
  } finally {
    loading.value = false;
  }
};

// 필터 변경 핸들러
const handleFilterChange = (newFilters) => {
  filters.value = newFilters;
  currentPage.value = 1; // 페이지 1로 초기화
  loadSpots();
};

// 스팟 클릭
const handleSpotClick = (spot) => {
  router.push(`/spots/${spot.id}`);
};

// 좋아요 토글
const handleFavorite = async (spot) => {
  try {
    await spotApi.toggleFavorite(spot.id);

    // 로컬 상태 업데이트
    const targetSpot = spots.value.find((s) => s.id === spot.id);
    if (targetSpot) {
      targetSpot.isFavorite = !targetSpot.isFavorite;
    }
  } catch (err) {
    console.error("Toggle favorite error:", err);
    alert("좋아요 처리에 실패했습니다.");
  }
};

// 초기 로드
onMounted(() => {
  loadSpots();
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;600;700&display=swap");

.spot-exploration {
  min-height: 100vh;
  background: #ffffff;
  font-family: "Noto Sans KR", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* Main Content Layout */
.main-content {
  max-width: 1600px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 320px 1fr;
  gap: 3rem;
  padding: 1.5rem 13rem 1.5rem 13rem;
}

/* Main Content Area */
.spots-grid {
  flex: 1;
}

.tabs {
  display: flex;
  gap: 2rem;
  margin-bottom: 1.5rem;
  border-bottom: 1px solid #e5e5e5;
}

.tab {
  background: none;
  border: none;
  padding: 0.875rem 0;
  font-size: 18px;
  font-weight: 400;
  color: #52525b;
  cursor: pointer;
  position: relative;
  font-family: inherit;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.tab:hover {
  color: #1e1e1e;
}

.tab.active {
  font-weight: 600;
  color: #1e1e1e;
}

.tab.active::after {
  content: "";
  position: absolute;
  bottom: -1px;
  left: 0;
  right: 0;
  height: 3px;
  background: #1e1e1e;
  border-radius: 2px 2px 0 0;
}

/* Loading State */
.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem 2rem;
  color: #6b7280;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid #e5e7eb;
  border-top-color: #2563eb;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* Error State */
.error-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem 2rem;
  color: #ef4444;
}

.retry-btn {
  margin-top: 1rem;
  padding: 0.5rem 1.5rem;
  background: #2563eb;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  font-family: inherit;
}

.retry-btn:hover {
  background: #1d4ed8;
}

/* Empty State */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem 2rem;
  color: #6b7280;
}

/* Cards Grid */
.cards-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
  animation: fadeIn 0.5s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Pagination */
.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  margin-top: 3rem;
  padding: 1rem 0;
}

.page-btn {
  padding: 0.5rem 1.25rem;
  background: #ffffff;
  border: 1px solid #d4d4d4;
  border-radius: 6px;
  font-size: 14px;
  font-family: inherit;
  cursor: pointer;
  transition: all 0.2s ease;
}

.page-btn:hover:not(:disabled) {
  background: #f5f5f5;
  border-color: #a3a3a3;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-info {
  font-size: 14px;
  color: #52525b;
  min-width: 80px;
  text-align: center;
}

/* Responsive Design */
@media (max-width: 1400px) {
  .main-content {
    grid-template-columns: 280px 1fr;
    gap: 2.5rem;
    padding: 1.5rem 3rem;
  }

  .cards-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 1024px) {
  .main-content {
    grid-template-columns: 1fr;
    gap: 2rem;
    padding: 1.5rem 2rem;
  }

  .cards-container {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .main-content {
    padding: 1rem;
    gap: 1.5rem;
  }

  .tab {
    font-size: 16px;
  }

  .cards-container {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .tabs {
    gap: 1rem;
  }

  .tab {
    font-size: 15px;
    padding: 0.625rem 0;
  }

  .main-content {
    gap: 1rem;
  }
}
</style>
