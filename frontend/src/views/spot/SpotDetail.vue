<template>
  <div class="spot-detail">
    <!-- 메인 컨텐츠 -->
    <main class="main-content">
      <!-- 메인 이미지 -->
      <div class="main-image-section">
        <img :src="spot.mainImage" :alt="spot.title" class="main-image" />
      </div>

      <!-- 스팟 정보 -->
      <section class="spot-info">
        <div class="info-header">
          <div class="title-section">
            <h1 class="spot-title">{{ spot.title }}</h1>
            <button
              class="favorite-btn"
              :class="{ active: spot.isFavorite }"
              @click="toggleFavorite"
            >
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path
                  d="M2 9.5C2 8.38724 2.33759 7.30062 2.96813 6.3837C3.59867 5.46678 4.49252 4.7627 5.53161 4.36444C6.5707 3.96618 7.70616 3.89248 8.78801 4.15308C9.86987 4.41368 10.8472 4.99632 11.591 5.82404C11.6434 5.88005 11.7067 5.92471 11.7771 5.95524C11.8474 5.98577 11.9233 6.00152 12 6.00152C12.0767 6.00152 12.1526 5.98577 12.2229 5.95524C12.2933 5.92471 12.3566 5.88005 12.409 5.82404C13.1504 4.99094 14.128 4.40341 15.2116 4.13964C16.2952 3.87588 17.4335 3.94839 18.4749 4.34752C19.5163 4.74666 20.4114 5.45349 21.0411 6.37394C21.6708 7.29439 22.0053 8.3848 22 9.5C22 11.79 20.5 13.5 19 15L13.508 20.313C13.3217 20.527 13.0919 20.699 12.834 20.8173C12.5762 20.9357 12.296 20.9979 12.0123 20.9997C11.7285 21.0015 11.4476 20.9429 11.1883 20.8278C10.9289 20.7127 10.697 20.5437 10.508 20.332L5 15C3.5 13.5 2 11.8 2 9.5Z"
                  :stroke="spot.isFavorite ? '#FB2C36' : '#99A1AF'"
                  :fill="spot.isFavorite ? '#FB2C36' : 'none'"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </button>
          </div>

          <div class="rating">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path
                d="M7.68323 1.53009C7.71245 1.47107 7.75758 1.42138 7.81353 1.38664C7.86949 1.3519 7.93404 1.3335 7.9999 1.3335C8.06576 1.3335 8.13031 1.3519 8.18626 1.38664C8.24222 1.42138 8.28735 1.47107 8.31656 1.53009L9.85656 4.64943C9.95802 4.85474 10.1078 5.03236 10.293 5.16706C10.4782 5.30176 10.6933 5.3895 10.9199 5.42276L14.3639 5.92676C14.4292 5.93621 14.4905 5.96374 14.5409 6.00622C14.5913 6.04871 14.6288 6.10446 14.6492 6.16716C14.6696 6.22987 14.6721 6.29703 14.6563 6.36105C14.6405 6.42507 14.6071 6.48339 14.5599 6.52943L12.0692 8.95476C11.905 9.11483 11.7821 9.31241 11.7111 9.53051C11.6402 9.74861 11.6233 9.98069 11.6619 10.2068L12.2499 13.6334C12.2614 13.6987 12.2544 13.7658 12.2296 13.8272C12.2048 13.8886 12.1632 13.9418 12.1096 13.9808C12.056 14.0197 11.9925 14.0428 11.9265 14.0474C11.8604 14.052 11.7944 14.0379 11.7359 14.0068L8.65723 12.3881C8.45438 12.2816 8.22868 12.2259 7.99956 12.2259C7.77044 12.2259 7.54475 12.2816 7.3419 12.3881L4.2639 14.0068C4.20545 14.0377 4.1395 14.0516 4.07353 14.0469C4.00757 14.0422 3.94424 14.0191 3.89076 13.9802C3.83728 13.9413 3.79579 13.8882 3.771 13.8269C3.74622 13.7656 3.73914 13.6986 3.75056 13.6334L4.3379 10.2074C4.37669 9.98125 4.35989 9.74902 4.28892 9.53079C4.21796 9.31256 4.09497 9.11486 3.93056 8.95476L1.4399 6.53009C1.39229 6.48411 1.35856 6.42569 1.34254 6.36147C1.32652 6.29726 1.32886 6.22984 1.34928 6.16689C1.36971 6.10393 1.40741 6.04799 1.45808 6.00541C1.50876 5.96284 1.57037 5.93536 1.6359 5.92609L5.07923 5.42276C5.30607 5.38976 5.52149 5.30213 5.70695 5.16742C5.89242 5.0327 6.04237 4.85494 6.1439 4.64943L7.68323 1.53009Z"
                fill="#FDC700"
                stroke="#FDC700"
                stroke-width="1.33333"
              />
            </svg>
            <span class="rating-score">{{ spot.rating }}</span>
            <span class="rating-count">({{ spot.reviewCount.toLocaleString() }})</span>
          </div>
        </div>

        <div class="info-detail">
          <p class="info-date">{{ spot.date }}</p>
          <p class="info-address">{{ spot.address }}</p>
          <p class="info-description">{{ spot.description }}</p>
        </div>
      </section>

      <!-- 지도 섹션 -->
      <section class="map-section">
        <div class="map-container">
          <img :src="spot.mapImage" alt="Map" class="map-image" />
          <button class="map-marker">
            <svg width="56" height="56" viewBox="0 0 56 56" fill="none">
              <circle cx="28" cy="18" r="16" fill="#FB2C36" stroke="white" stroke-width="4" />
            </svg>
          </button>
        </div>
        <div class="map-address">
          <p>{{ spot.address }}</p>
        </div>
      </section>

      <!-- 함께 많이 본 여행지 -->
      <section class="related-spots">
        <h2 class="section-title">함께 많이 본 여행지</h2>

        <div class="spots-carousel">
          <button class="carousel-btn prev" @click="scrollCarousel('prev')">
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

          <div class="spots-grid" ref="carouselRef">
            <SpotCard
              v-for="relatedSpot in relatedSpots"
              :key="relatedSpot.id"
              :spot="relatedSpot"
              @click="goToSpot"
              @favorite="handleRelatedFavorite"
            />
          </div>

          <button class="carousel-btn next" @click="scrollCarousel('next')">
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

      <!-- 후기 섹션 -->
      <section class="reviews-section">
        <div class="reviews-header">
          <h2 class="section-title">후기</h2>
          <p class="reviews-count">
            총 <span class="count-highlight">{{ spot.reviewCount.toLocaleString() }}</span
            >건
          </p>
        </div>

        <div class="reviews-list">
          <ReviewCard
            v-for="review in reviews"
            :key="review.id"
            :review="review"
            @helpful="handleHelpful"
          />
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import SpotCard from "@/components/SpotCard.vue";
import ReviewCard from "@/components/spot/ReviewCard.vue";

const route = useRoute();
const router = useRouter();
const carouselRef = ref(null);

// 스팟 상세 정보 (실제로는 API에서 가져옴)
const spot = ref({
  id: route.params.id,
  title: "김천김밥축제",
  rating: 2.7,
  reviewCount: 30123,
  date: "2025.10.1~2025.12.25",
  address: "경상북도 김천시 대항면 직지사길 130",
  description: "김천김밥 판매부스 및 시식회, 다양한 체험프로그램 운영",
  mainImage: "https://placehold.co/1020x1389",
  mapImage: "https://placehold.co/995x263",
  isFavorite: false,
});

// 관련 스팟 (SpotCard 컴포넌트 형식에 맞게 변환)
const relatedSpots = ref([
  {
    id: "1",
    name: "감천문화마을",
    badge: "관광지",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "부산광역시 사하구 감내2로 203",
    tags: "#감천 #마을",
    image: "https://placehold.co/280x192",
    isFavorite: true,
  },
  {
    id: "2",
    name: "강남 팝업스토어",
    badge: "팝업스토어",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "서울특별시 강남구 테헤란로 203",
    tags: "#강남 #팝업",
    image: "https://placehold.co/280x192",
    isFavorite: true,
  },
  {
    id: "3",
    name: "벚꽃축제",
    badge: "축제",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "서울특별시 여의도 한강공원 203",
    tags: "#벚꽃 #축제",
    image: "https://placehold.co/280x192",
    isFavorite: true,
  },
  {
    id: "4",
    name: "해운대 해수욕장",
    badge: "관광지",
    rating: 4.8,
    time: "AM 9:00 - PM 10:00",
    location: "부산광역시 해운대구 우동",
    tags: "#해운대 #바다",
    image: "https://placehold.co/280x192",
    isFavorite: false,
  },
]);

// 후기
const reviews = ref([
  {
    id: 1,
    rating: 5,
    userName: "Before",
    userTag: "가족여행으로 좋아요",
    userAge: "60대",
    date: "2025.10.20",
    title:
      "[신흥특가]하노이(하롱베이) 5일 #찍찍스쿠터와 #단풍탕&낭풍폭포UP #예가견뽀읍 #예이크쿠즈폭뽕&키퍼 #1팀1건식 #푸꾸딘내#두도닥",
    content:
      "9년만에 디시간 하노이 하롱베이는 공항부터 딸랑겉고 오토바이보단 자동차가 월씬 많이댔다 하롱베이투어는 목선이 아닌 하노투어크루즈로 바뀌운만 없었고통거다 만만 비좁을 감상하며 추억을 쌓기웬 라이브뮤직전지가지만 너무길고 불룬또한 지나치다는 다수의 의견이였다 박씬앙인가이드는 친절하고도 친절하고 배셕싱은 27명을 잘 이끌어주셔서 편안한 여행이였습니다~ 두분모두 감시하고 건강하시길~*",
    tags: [
      { icon: "✨", text: "객실이 깨끗해요" },
      { icon: "🛏️", text: "침실이 맘에요" },
      { icon: "😊", text: "환대 좋았어요" },
      { icon: "😊", text: "가이드가 친절해요" },
    ],
    images: ["https://placehold.co/128x128", "https://placehold.co/128x128"],
    helpfulCount: 1,
  },
]);

// 좋아요 토글
const toggleFavorite = () => {
  spot.value.isFavorite = !spot.value.isFavorite;
};

// 관련 스팟 좋아요 처리
const handleRelatedFavorite = (relatedSpot) => {
  const spot = relatedSpots.value.find((s) => s.id === relatedSpot.id);
  if (spot) {
    spot.isFavorite = !spot.isFavorite;
  }
};

// 캐러셀 스크롤
const scrollCarousel = (direction) => {
  if (!carouselRef.value) return;

  const scrollAmount = 300;
  if (direction === "next") {
    carouselRef.value.scrollBy({ left: scrollAmount, behavior: "smooth" });
  } else {
    carouselRef.value.scrollBy({ left: -scrollAmount, behavior: "smooth" });
  }
};

// 스팟 이동
const goToSpot = (relatedSpot) => {
  router.push(`/spots/${relatedSpot.id}`);
  window.scrollTo({ top: 0, behavior: "smooth" });
};

// 도움이 돼요 처리
const handleHelpful = (reviewId) => {
  const review = reviews.value.find((r) => r.id === reviewId);
  if (review) {
    review.helpfulCount += 1;
  }
};

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  // 실제로는 API 호출
  console.log("Loading spot:", route.params.id);
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.spot-detail {
  min-height: 100vh;
  background: white;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 메인 컨텐츠 */
.main-content {
  max-width: 1020px;
  margin: 0 auto;
  padding: 2rem 1.5rem;
}

/* 메인 이미지 */
.main-image-section {
  width: 100%;
  margin-bottom: 3rem;
}

.main-image {
  width: 100%;
  height: auto;
  border-radius: 1rem;
  object-fit: cover;
}

/* 스팟 정보 */
.spot-info {
  margin-bottom: 3rem;
}

.info-header {
  margin-bottom: 1.5rem;
}

.title-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.5rem;
}

.spot-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #1f2937;
}

.favorite-btn {
  width: 2rem;
  height: 2rem;
  border: none;
  background: none;
  cursor: pointer;
  transition: transform 0.2s;
}

.favorite-btn:hover {
  transform: scale(1.1);
}

.rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.rating-score {
  font-size: 0.875rem;
  color: #374151;
}

.rating-count {
  font-size: 0.875rem;
  color: #9ca3af;
}

.info-detail {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.info-date,
.info-address,
.info-description {
  font-size: 1rem;
  color: #374151;
  line-height: 1.5;
}

.info-description {
  color: #4b5563;
}

/* 지도 섹션 */
.map-section {
  margin-bottom: 3rem;
}

.map-container {
  position: relative;
  width: 100%;
  height: 263px;
  border-radius: 0.625rem;
  overflow: hidden;
  border: 1px solid #e5e7eb;
  margin-bottom: 1rem;
}

.map-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.map-marker {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: none;
  background: none;
  cursor: pointer;
}

.map-address {
  padding: 1rem;
  background: white;
  border-bottom: 1px solid #e5e7eb;
  border-radius: 0 0 0.625rem 0.625rem;
}

.map-address p {
  color: #374151;
  font-size: 1rem;
}

/* 함께 많이 본 여행지 */
.related-spots {
  margin-bottom: 3rem;
}

.section-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: black;
  margin-bottom: 1.5rem;
}

.spots-carousel {
  position: relative;
}

.carousel-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 50%;
  border: none;
  background: white;
  box-shadow: 0 4px 6px -4px rgba(0, 0, 0, 0.1), 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  transition: all 0.2s;
}

.carousel-btn:hover {
  transform: translateY(-50%) scale(1.05);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);
}

.carousel-btn.prev {
  left: -1.25rem;
}

.carousel-btn.next {
  right: -1.25rem;
}

.spots-grid {
  display: flex;
  gap: 1rem;
  overflow-x: auto;
  scroll-behavior: smooth;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.spots-grid::-webkit-scrollbar {
  display: none;
}

.spots-grid > * {
  flex: 0 0 280px;
}

/* 후기 섹션 */
.reviews-section {
  margin-bottom: 3rem;
}

.reviews-header {
  display: flex;
  align-items: baseline;
  gap: 1rem;
  margin-bottom: 1.5rem;
}

.reviews-count {
  font-size: 1.25rem;
  color: rgba(0, 0, 0, 0.5);
}

.count-highlight {
  color: rgba(124, 58, 237, 0.5);
}

.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 반응형 */
@media (max-width: 1024px) {
  .main-content {
    padding: 1.5rem 1rem;
  }

  .spots-grid {
    gap: 0.75rem;
  }

  .spots-grid > * {
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
  .main-content {
    padding: 1rem;
  }

  .spot-title {
    font-size: 1.25rem;
  }

  .section-title {
    font-size: 1.25rem;
  }

  .spots-grid > * {
    flex: 0 0 220px;
  }
}

@media (max-width: 480px) {
  .spot-title {
    font-size: 1.125rem;
  }

  .section-title {
    font-size: 1.125rem;
  }

  .spots-grid {
    gap: 0.5rem;
  }

  .spots-grid > * {
    flex: 0 0 200px;
  }

  .carousel-btn {
    width: 2rem;
    height: 2rem;
  }

  .carousel-btn.prev {
    left: -0.5rem;
  }

  .carousel-btn.next {
    right: -0.5rem;
  }
}
</style>
