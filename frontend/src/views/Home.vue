<template>
  <div class="home">
    <!-- 메인 컨테이너 -->
    <main class="main-container">
      <!-- 검색 섹션 -->
      <!-- <section class="search-section">
        <SearchBar />
      </section> -->

      <!-- 지금 뜨는 축제 -->
      <section class="content-section">
        <h2 class="section-title">지금 뜨는 축제 & 관광지</h2>
        <div class="spots-carousel">
          <!-- prev -->
          <button
            class="carousel-btn prev"
            @click="scrollCarousel('prev', 'festival')"
            v-if="festivals.length > 3"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <path
                d="M15 18L9 12L15 6"
                stroke="#4A5565"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
          <!-- cards -->
          <div class="spots-grid horizontal" ref="festivalCarouselRef">
            <SpotCard
              v-for="(festival, i) in festivals"
              :key="`festival-${i}`"
              :spot="festival"
              @click="handleSpotClick"
              @favorite="handleFavorite"
            />
          </div>
          <!-- next -->
          <button
            class="carousel-btn next"
            @click="scrollCarousel('next', 'festival')"
            v-if="festivals.length > 3"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <path
                d="M9 18L15 12L9 6"
                stroke="#4A5565"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
        </div>
      </section>

      <!-- 지금 뜨는 팝업 -->
      <section class="content-section">
        <h2 class="section-title">지금 뜨는 팝업스토어</h2>
        <div class="spots-carousel">
          <!-- prev -->
          <button
            class="carousel-btn prev"
            @click="scrollCarousel('prev', 'popup')"
            v-if="festivals.length > 3"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <path
                d="M15 18L9 12L15 6"
                stroke="#4A5565"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
          <!-- cards -->
          <div class="spots-grid horizontal" ref="popupCarouselRef">
            <SpotCard
              v-for="(popup, i) in popups"
              :key="`popup-${i}`"
              :spot="popup"
              @click="handleSpotClick"
              @favorite="handleFavorite"
            />
          </div>
          <!-- next -->
          <button
            class="carousel-btn next"
            @click="scrollCarousel('next', 'popup')"
            v-if="festivals.length > 3"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <path
                d="M9 18L15 12L9 6"
                stroke="#4A5565"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              1
            </svg>
          </button>
        </div>
      </section>

      <!-- 뉴스 섹션 -->
      <section class="content-section">
        <div class="news-header">
          <h2 class="section-title">스팟 블로그 후기 모음</h2>
          <span class="ai-badge">AI 요약</span>
        </div>
        <div class="spots-carousel">
          <!-- prev -->
          <button
            class="carousel-btn prev"
            @click="scrollCarousel('prev', 'news')"
            v-if="newsList.length > 3"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <path
                d="M15 18L9 12L15 6"
                stroke="#4A5565"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
          <div class="news-grid horizontal" ref="newsCarouselRef">
            <NewsCard
              v-for="news in newsList"
              :key="news.spotId"
              :title="news.title"
              :keywords="news.keywords"
              :summary="news.category"
              :showMore="true"
            />
          </div>
          <!-- next -->
          <button
            class="carousel-btn next"
            @click="scrollCarousel('next', 'news')"
            v-if="newsList.length > 3"
          >
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
              <path
                d="M9 18L15 12L9 6"
                stroke="#4A5565"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              1
            </svg>
          </button>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import SearchBar from "@/components/SearchBar.vue";
import SpotCard from "@/components/SpotCard.vue";
import NewsCard from "@/components/NewsCard.vue";
import { getSpotRanking, getPopupRanking } from "@/api/rank/rankApi";
import { getSpotKeywords } from "@/api/news/newsApi";

const router = useRouter();

// 캐러셀 ref
const festivalCarouselRef = ref(null);
const popupCarouselRef = ref(null);
const newsCarouselRef = ref(null);

// ------------------------------
// festivals
// ------------------------------
const festivals = ref([]);

// ------------------------------
// popups
// ------------------------------
const popups = ref([]);
const isLoading = ref(false);

// news
const keywordMap = ref({});
// { 1: ["데이트", "야간산책"], 2: [...] }

// ------------------------------
// fetch data
// ------------------------------
const fetchRankings = async () => {
  try {
    isLoading.value = true;

    const [mainRes, popupRes] = await Promise.all([getSpotRanking(10), getPopupRanking(10)]);

    festivals.value = mainRes.data.map(mapSpotCard);
    popups.value = popupRes.data.map(mapSpotCard);

    // festivals 기준으로 키워드 조회
    fetchKeywordsForFestivals();
  } catch (e) {
    console.error("랭킹 조회 실패", e);
  } finally {
    isLoading.value = false;
  }
};

const fetchKeywordsForFestivals = async () => {
  for (const festival of festivals.value) {
    try {
      const res = await getSpotKeywords(festival.id, festival.name);

      keywordMap.value[festival.id] = res.data.map((article) => article.summary);
    } catch (e) {
      console.error("키워드 조회 실패:", festival.name, e);
    }
  }
};

const newsList = computed(() =>
  festivals.value.map((f) => ({
    spotId: f.id,
    title: f.name,
    category: (keywordMap.value[f.id] || []).join(" "),
  }))
);

// ------------------------------
// mapper (API → SpotCard)
// ------------------------------
const mapSpotCard = (spot) => ({
  id: spot.id,
  name: spot.title,
  image: spot.image1,
  badge:
    spot.contentType === "POPUP"
      ? "팝업스토어"
      : spot.contentType === "ATTRACTION"
      ? "관광지"
      : "축제",
  rating: spot.avgRating,
  time: spot.time, // 필요 시 start~end 가공
  location: spot.location, // 나중에 주소 필드 추가 가능
  tags: "", // 확장 포인트
  isFavorite: spot.isFavorite,
});

// ------------------------------
// news
// ------------------------------

// 공통 스크롤 함수
const scrollCarousel = (direction, target) => {
  const el =
    target === "festival"
      ? festivalCarouselRef.value
      : target === "popup"
      ? popupCarouselRef.value
      : newsCarouselRef.value;

  if (!el) return;

  const scrollAmount = el.clientWidth; // 한 화면씩 이동
  el.scrollBy({
    left: direction === "next" ? scrollAmount : -scrollAmount,
    behavior: "smooth",
  });
};

// ------------------------------
// event handlers
// ------------------------------
const handleSpotClick = (spot) => {
  router.push(`/spots/${spot.id}`);
};

const handleFavorite = (spot) => {
  let targetSpot = festivals.value.find((s) => s.id === spot.id);

  // 없으면 popups에서 찾기
  if (!targetSpot) {
    targetSpot = popups.value.find((s) => s.id === spot.id);
  }

  // 찾았으면 토글
  if (targetSpot) {
    targetSpot.isFavorite = !targetSpot.isFavorite;
    // API 호출
  }
  // 좋아요 상태 토글 API 호출 등
};

// ------------------------------
// lifecycle
// ------------------------------
onMounted(fetchRankings);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;600;700&display=swap");

.home {
  min-height: 100vh;
  background: #ffffff;
  font-family: "Noto Sans KR", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 메인 컨테이너 */
.main-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 4rem 2rem 6rem;
  display: flex;
  flex-direction: column;
  gap: 4rem;
}

/* 검색 섹션 */
.search-section {
  width: 100%;
}

/* 컨텐츠 섹션 */
.content-section {
  background: #ffffff;
  border-radius: 24px;
  box-shadow: 0 20px 60px rgba(15, 23, 42, 0.06);
  padding: 2.5rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 섹션 제목 */
.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0;
}

/* 뉴스 헤더 */
.news-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.ai-badge {
  padding: 0.25rem 0.75rem;
  background: linear-gradient(to right, #9333ea, #2563eb);
  color: #ffffff;
  font-size: 12px;
  border-radius: 9999px;
  font-weight: 500;
}

/* 스팟 그리드 */
.spots-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 1.5rem;
}

/* 뉴스 그리드 */
.news-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
}

/* =========================
   뉴스 캐러셀 전용
========================= */
.spots-carousel .news-grid {
  display: flex;
  gap: 16px;
  overflow-x: hidden;
  scroll-behavior: smooth;
}

.spots-carousel .news-grid > * {
  flex: 0 0 340px; /* 뉴스 카드 폭 (SpotCard보다 넓게) */
}

/* 반응형 - 태블릿 */
@media (max-width: 1024px) {
  .main-container {
    padding: 3rem 1.5rem 5rem;
    gap: 3rem;
  }

  .content-section {
    padding: 2rem;
  }

  .spots-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .news-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .main-container {
    padding: 2rem 1rem 4rem;
    gap: 2rem;
  }

  .content-section {
    padding: 1.5rem;
    border-radius: 16px;
  }

  .section-title {
    font-size: 16px;
  }

  .spots-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .news-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
}

@media (max-width: 480px) {
  .main-container {
    padding: 1.5rem 0.75rem 3rem;
    gap: 1.5rem;
  }

  .content-section {
    padding: 1.25rem;
  }
}

/* =========================
   홈 캐러셀 공통
========================= */
.spots-carousel {
  position: relative;
  width: 100%;
  margin-top: 1rem;
}

/* 카드 가로 스크롤 영역 */
.spots-carousel .spots-grid {
  display: flex;
  gap: 16px;
  overflow-x: hidden; /* 버튼으로만 이동 */
  scroll-behavior: smooth;
}

/* 카드 고정 폭 (SpotCard 기준) */
.spots-carousel .spots-grid > * {
  flex: 0 0 280px;
}

/* =========================
   좌 / 우 버튼
========================= */
.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  background: #ffffff;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.15);
  cursor: pointer;
  z-index: 10;

  display: flex;
  align-items: center;
  justify-content: center;

  transition: all 0.2s ease;
}

.carousel-btn:hover {
  transform: translateY(-50%) scale(1.08);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

/* 버튼 위치 */
.carousel-btn.prev {
  left: -16px;
}

.carousel-btn.next {
  right: -16px;
}

/* =========================
   반응형
========================= */
@media (max-width: 1024px) {
  .spots-carousel .spots-grid > * {
    flex: 0 0 250px;
  }

  .carousel-btn.prev {
    left: 0;
  }

  .carousel-btn.next {
    right: 0;
  }
}

@media (max-width: 768px) {
  .spots-carousel .spots-grid > * {
    flex: 0 0 220px;
  }

  .carousel-btn {
    width: 36px;
    height: 36px;
  }
}

@media (max-width: 480px) {
  .spots-carousel .spots-grid {
    gap: 12px;
  }

  .spots-carousel .spots-grid > * {
    flex: 0 0 200px;
  }

  .carousel-btn {
    width: 32px;
    height: 32px;
  }

  .carousel-btn.prev {
    left: -8px;
  }

  .carousel-btn.next {
    right: -8px;
  }
}
</style>
