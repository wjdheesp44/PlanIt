<template>
  <div class="spot-detail">
    <div v-if="isLoading" class="loading-overlay">
      <div class="loading-spinner"></div>
    </div>

    <!-- 메인 컨텐츠 -->
    <main class="main-content" :class="{ 'is-loading': isLoading }">
      <!-- 메인 이미지 -->
      <div class="main-image-section">
        <img
          :src="spot.image1 || 'https://placehold.co/1020x1389'"
          :alt="spot.title"
          class="main-image"
        />
      </div>

      <!-- 스팟 정보 -->
      <section class="spot-info">
        <div class="info-header">
          <div class="title-section">
            <h1 class="spot-title">{{ spot.title }}</h1>
            <div class="like-wrapper">
              <LikeButton
                :spotId="spot.id"
                :initialLiked="spot.isFavorite"
              />
            </div>
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
            <span class="rating-score">{{ spot.avgRating || "-" }}</span>
            <span class="rating-count">({{ formatNumber(spot.reviewCount) }})</span>
          </div>
        </div>

        <div class="info-detail">
          <p class="info-date">{{ spot.date || "상시운영" }}</p>
          <p class="info-address">{{ spot.doroAddr || spot.jibunAddr }}</p>
          <p class="info-description">{{ spot.description }}</p>
        </div>
      </section>

      <!-- 지도 섹션 -->
      <section class="map-section" v-if="spot.latitude && spot.longitude">
        <div class="map-container">
          <div id="map" class="map-image"></div>
        </div>
        <div class="map-address">
          <p>{{ spot.doroAddr || spot.jibunAddr }}</p>
        </div>
      </section>

      <!-- 함께 많이 본 여행지 -->
      <section class="related-spots" v-if="spot.hotSpots && spot.hotSpots.length > 0">
        <h2 class="section-title">함께 많이 본 여행지</h2>

        <div class="spots-carousel">
          <button
            class="carousel-btn prev"
            @click="scrollCarousel('prev')"
            v-if="spot.hotSpots.length > 3"
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

          <div class="spots-grid" ref="carouselRef">
            <SpotCard
              v-for="relatedSpot in spot.hotSpots"
              :key="relatedSpot.id"
              :spot="relatedSpot"
              @click="goToSpot"
              @favorite="handleRelatedFavorite"
            />
          </div>

          <button
            class="carousel-btn next"
            @click="scrollCarousel('next')"
            v-if="spot.hotSpots.length > 3"
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

      <!-- 후기 섹션 -->
      <section class="reviews-section">
        <div class="reviews-header">
          <h2 class="section-title">후기</h2>
          <p class="reviews-count">
            총 <span class="count-highlight">{{ formatNumber(spot.reviewCount) }}</span
            >건
          </p>
          <button class="review-open-btn" @click="openReviewModal" @click.prevent="onClickReview">
            후기 작성
          </button>
        </div>
        <div class="reviews-list">
          <ReviewCard
            v-for="review in reviews"
            :key="review.id"
            :review="review"
            @helpful="handleHelpful"
            @delete="deleteReview"
            @edit="startEdit"
          />
        </div>
      </section>
    </main>

    <!-- 후기 작성 모달 -->
      <div v-if="isReviewModalOpen" class="modal-overlay" @click.self="closeReviewModal">
        <div class="modal">
          <h3 class="modal-title">후기 작성</h3>

          <!-- 별점 -->
          <div class="rating-input">
            <span
              v-for="n in 5"
              :key="n"
              class="star"
              :class="{ active: n <= newReview.rating }"
              @click="newReview.rating = n"
            >
              ★
            </span>
          </div>

          <!-- 후기 내용 -->
          <textarea
            v-model="newReview.content"
            placeholder="후기를 작성해 주세요"
            rows="5"
          />

          <!-- 버튼 -->
          <div class="modal-actions">
            <button class="cancel-btn" @click="closeReviewModal">취소</button>
            <button class="submit-btn" @click="submitReview">등록</button>
          </div>
        </div>
      </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onUnmounted, nextTick, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import SpotCard from "@/components/SpotCard.vue";
import ReviewCard from "@/components/spot/ReviewCard.vue";
import spotApi from "@/api/spot/spotApi";
import reviewApi from "@/api/review/reviewApi";
import LikeButton from "@/components/likes/LikeButton.vue";

const route = useRoute();
const router = useRouter();
const carouselRef = ref(null);
const isLoading = ref(false);

let mapInstance = null;
let markerOverlay = null;
let infoOverlay = null;

// 스팟 상세 정보
const spot = ref({
  id: null,
  title: "",
  badge: "",
  avgRating: null,
  reviewCount: "0",
  date: "",
  doroAddr: "",
  jibunAddr: "",
  latitude: null,
  longitude: null,
  tel: "",
  homepage: "",
  image1: null,
  image2: null,
  image3: null,
  image4: null,
  image5: null,
  description: "",
  isFavorite: false,
  hotSpots: [],
});

// 후기 (임시 데이터)
const reviews = ref([]);

// 숫자 포맷팅
const formatNumber = (num) => {
  if (!num) return "0";
  return Number(num).toLocaleString();
};

// 카카오맵 초기화
const initMap = () => {
  if (!spot.value.latitude || !spot.value.longitude) return;

  if (!window.kakao || !window.kakao.maps) {
    console.error("Kakao Maps SDK is not loaded");
    return;
  }

  window.kakao.maps.load(() => {
    const container = document.getElementById("map");
    if (!container) return;

    const options = {
      center: new window.kakao.maps.LatLng(spot.value.latitude, spot.value.longitude),
      level: 3,
    };

    mapInstance = new window.kakao.maps.Map(container, options);

    // 커스텀 마커 생성
    const markerPosition = new window.kakao.maps.LatLng(spot.value.latitude, spot.value.longitude);

    const markerElement = document.createElement("div");
    markerElement.style.cssText = `
      position: relative;
      width: 30px;
      height: 38px;
      display: flex;
      flex-direction: column;
      align-items: center;
    `;

    const pinIcon = document.createElement("div");
    pinIcon.innerHTML = `
      <svg width="30" height="38" viewBox="0 0 30 38" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M15 0C6.75 0 0 6.75 0 15C0 26.25 15 38 15 38C15 38 30 26.25 30 15C30 6.75 23.25 0 15 0Z" fill="#2563EB"/>
        <circle cx="15" cy="15" r="6" fill="white"/>
      </svg>
    `;
    markerElement.appendChild(pinIcon);

    markerOverlay = new window.kakao.maps.CustomOverlay({
      position: markerPosition,
      content: markerElement,
      yAnchor: 1,
    });
    markerOverlay.setMap(mapInstance);

    // 인포윈도우 생성
    const infoElement = document.createElement("div");
    infoElement.style.cssText = `
      position: relative;
      background: white;
      padding: 10px 14px;
      border-radius: 8px;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
      min-width: 150px;
      max-width: 220px;
      transform: translateY(-45px);
    `;

    infoElement.innerHTML = `
      <div style="
        font-size: 14px;
        font-weight: 600;
        color: #1e1e1e;
        margin-bottom: 4px;
        line-height: 1.3;
      ">${spot.value.title}</div>
      ${
        spot.value.address
          ? `
      <div style="
        font-size: 12px;
        color: #6b7280;
        line-height: 1.3;
      ">${spot.value.address}</div>
      `
          : ""
      }
      <div style="
        position: absolute;
        bottom: -6px;
        left: 50%;
        transform: translateX(-50%);
        width: 0;
        height: 0;
        border-left: 6px solid transparent;
        border-right: 6px solid transparent;
        border-top: 6px solid white;
        filter: drop-shadow(0 1px 2px rgba(0, 0, 0, 0.1));
      "></div>
    `;

    infoOverlay = new window.kakao.maps.CustomOverlay({
      position: markerPosition,
      content: infoElement,
      yAnchor: 1.4,
    });
    infoOverlay.setMap(mapInstance);
  });
};

const cleanupMap = () => {
  if (markerOverlay) {
    markerOverlay.setMap(null); // 마커 제거
    markerOverlay = null;
  }

  if (infoOverlay) {
    infoOverlay.setMap(null); // 인포윈도우 제거
    infoOverlay = null;
  }

  if (mapInstance) {
    mapInstance = null; // 지도 인스턴스 제거
  }
};

onMounted(() => {
  initMap();
});

onUnmounted(() => {
  cleanupMap();
});

// 스팟 데이터 로드
const loadSpotData = async () => {
  try {
    isLoading.value = true;
    const spotId = route.params.id;
    const data = await spotApi.getSpotById(spotId);
    spot.value = data;

    await nextTick();
    initMap();
  } catch (error) {
    console.error("Failed to load spot:", error);
    // 에러 처리 (예: 404 페이지로 이동)
  } finally {
    isLoading.value = false;
  }
};

// 좋아요 토글
const toggleFavorite = async () => {
  try {
    await spotApi.toggleFavorite(spot.value.id);
    spot.value.isFavorite = !spot.value.isFavorite;
  } catch (error) {
    console.error("Failed to toggle favorite:", error);
    // 로그인 필요 시 로그인 페이지로 이동
    if (error.response?.status === 401) {
      router.push("/login");
    }
  }
};

// 관련 스팟 좋아요 처리
const handleRelatedFavorite = async (relatedSpot) => {
  try {
    await spotApi.toggleSpotFavorite(relatedSpot.id);
    const spotInList = spot.value.hotSpots.find((s) => s.id === relatedSpot.id);
    if (spotInList) {
      spotInList.isFavorite = !spotInList.isFavorite;
    }
  } catch (error) {
    console.error("Failed to toggle related spot favorite:", error);
    if (error.response?.status === 401) {
      router.push("/login");
    }
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
  // // 데이터 새로 로드
  // loadSpotData();
};

// 도움이 돼요 처리
const handleHelpful = (reviewId) => {
  const review = reviews.value.find((r) => r.id === reviewId);
  if (review) {
    review.helpfulCount += 1;
  }
};

// 라우트 파라미터 변경 감지
watch(
  () => route.params.id,
  async (newId) => {
    if (newId) {
      await loadSpotData();
      await loadReviews();
    }
  },
  { immediate: true } // 컴포넌트 마운트 시에도 즉시 실행
);

const isLogin = computed(() => {
  return !!localStorage.getItem("accessToken");
});

// 후기 작성 버튼
const onClickReview = () => {
  if (!isLogin.value) {
    // 로그인 안 했으면 아무 반응 없음
    router.push("/login");
    return;
  }
};

// 모달
const isReviewModalOpen = ref(false);

const newReview = ref({
  rating: 5,
  content: ""
});

const openReviewModal = () => {
  isReviewModalOpen.value = true;
};

const closeReviewModal = () => {
  isReviewModalOpen.value = false;
  newReview.value = { rating: 5, content: "" };
};

const submitReview = async () => {
  if (!newReview.value.content.trim()) {
    alert("후기 내용을 입력하세요");
    return;
  }

  await reviewApi.createReview(route.params.id, newReview.value);
  await loadReviews();
  closeReviewModal();
};

const loadReviews = async () => {
  const res = await reviewApi.getReviews(route.params.id);

  reviews.value = res.data;

  spot.value.reviewCount = reviews.value.length;
};
</script>

<style scoped>
/* 기존 스타일 유지 */
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

.like-wrapper {
  position: relative;
  width: 32px;
  height: 32px;
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

/* 모달 */
.review-open-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1rem;
  background: #7c3aed;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.review-open-btn:hover {
  background: #6d28d9;
}

/* 모달 배경 */
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
}

/* 모달 박스 */
.modal {
  background: white;
  width: 90%;
  max-width: 420px;
  border-radius: 12px;
  padding: 1.5rem;
}

/* 제목 */
.modal-title {
  font-size: 1.25rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

/* 별점 */
.rating-input {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.star {
  cursor: pointer;
  color: #d1d5db;
}

.star.active {
  color: #facc15;
}

/* 입력 */
.modal textarea {
  width: 100%;
  resize: none;
  padding: 0.75rem;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
  margin-bottom: 1rem;
}

/* 버튼 */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.cancel-btn {
  background: #e5e7eb;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
}

.submit-btn {
  background: #2563eb;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
}


/* 로딩 오버레이 */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255, 255, 255, 0.8);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.loading-spinner {
  width: 50px;
  height: 50px;
  border: 4px solid #f3f4f6;
  border-top: 4px solid #2563eb;
  border-radius: 50%;
  animation: spin 1s linear infinite;
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
}

/* 카카오맵 컨트롤 숨기기 (선택사항) */
#map .custom_zoomcontrol {
  display: none;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

.main-content.is-loading {
  opacity: 0.5;
  pointer-events: none;
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
