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
            @click="activeTab = 'events'"
          >
            행사&팝업 스토어
          </button>
          <button
            class="tab"
            :class="{ active: activeTab === 'places' }"
            @click="activeTab = 'places'"
          >
            여행지
          </button>
        </div>

        <!-- Grid of Spot Cards -->
        <div class="cards-container">
          <SpotCard
            v-for="spot in filteredSpots"
            :key="spot.id"
            :spot="spot"
            @click="handleSpotClick"
            @favorite="handleFavorite"
          />
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import FilterSidebar from "@/components/spot/FilterSidebar.vue";
import SpotCard from "@/components/SpotCard.vue";

const router = useRouter();
const activeTab = ref("events");
const filters = ref({});

const spots = ref([
  {
    id: 1,
    name: "감천문화마을",
    image: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
    badge: "관광지",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "부산광역시 사하구 감내2로 203",
    tags: "#감천 #마을",
  },
  {
    id: 2,
    name: "강남 팝업스토어",
    image: "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=400",
    badge: "팝업스토어",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "서울특별시 강남구 테헤란로 203",
    tags: "#강남 #팝업",
  },
  {
    id: 3,
    name: "벚꽃축제",
    image: "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=400",
    badge: "축제",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "서울특별시 여의도 한강공원 203",
    tags: "#벚꽃 #축제",
  },
  {
    id: 4,
    name: "해운대 해수욕장",
    image: "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=400",
    badge: "관광지",
    rating: 4.8,
    time: "24시간",
    location: "부산광역시 해운대구 중동",
    tags: "#해운대 #바다",
  },
  {
    id: 5,
    name: "홍대 팝업스토어",
    image: "https://images.unsplash.com/photo-1555421689-d68471e189f2?w=400",
    badge: "팝업스토어",
    rating: 4.3,
    time: "PM 12:00 - PM 10:00",
    location: "서울특별시 마포구 홍익로",
    tags: "#홍대 #감성",
  },
  {
    id: 6,
    name: "불꽃축제",
    image: "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=400",
    badge: "축제",
    rating: 4.7,
    time: "PM 7:00 - PM 10:00",
    location: "서울특별시 영등포구 여의도동",
    tags: "#불꽃 #축제",
  },
]);

const totalCount = computed(() => filteredSpots.value.length);

const filteredSpots = computed(() => {
  // 여기에 필터링 로직 추가 가능
  return spots.value;
});

const handleFilterChange = (newFilters) => {
  filters.value = newFilters;
  console.log("Filters changed:", newFilters);
  // 필터링 로직 구현
};

const handleSpotClick = (spot) => {
  router.push(`/spots/${spot.id}`);
};

const handleFavorite = (spot) => {
  let targetSpot = spots.value.find((s) => s.id === spot.id);
  targetSpot.isFavorite = !targetSpot.isFavorite;
  console.log("Favorite toggled:", targetSpot);
  // 좋아요 상태 토글 API 호출 등
};
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
