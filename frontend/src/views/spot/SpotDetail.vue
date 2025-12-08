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
            <div
              v-for="relatedSpot in relatedSpots"
              :key="relatedSpot.id"
              class="spot-card"
              @click="goToSpot(relatedSpot.id)"
            >
              <div class="card-image">
                <img :src="relatedSpot.image" :alt="relatedSpot.title" />
                <span class="card-badge" :class="relatedSpot.category">
                  {{ getCategoryLabel(relatedSpot.category) }}
                </span>
                <button
                  class="card-favorite"
                  :class="{ active: relatedSpot.isFavorite }"
                  @click.stop="toggleRelatedFavorite(relatedSpot.id)"
                >
                  <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                    <path
                      d="M1.667 7.917C1.667 6.989 1.948 6.084 2.474 5.32C2.999 4.556 3.744 3.969 4.61 3.637C5.476 3.305 6.422 3.244 7.324 3.461C8.225 3.678 9.04 4.164 9.659 4.853C9.703 4.9 9.756 4.937 9.815 4.963C9.873 4.988 9.936 5.001 10 5.001C10.064 5.001 10.128 4.988 10.186 4.963C10.245 4.937 10.298 4.9 10.341 4.853C10.959 4.159 11.774 3.669 12.677 3.45C13.58 3.23 14.528 3.29 15.396 3.623C16.264 3.955 17.01 4.545 17.535 5.312C18.059 6.079 18.338 6.987 18.334 7.917C18.334 9.825 17.084 11.25 15.834 12.5L11.257 16.928C11.102 17.106 10.91 17.249 10.695 17.348C10.481 17.446 10.247 17.498 10.011 17.5C9.774 17.501 9.54 17.452 9.324 17.356C9.108 17.261 8.915 17.12 8.757 16.943L4.167 12.5C2.917 11.25 1.667 9.833 1.667 7.917Z"
                      :fill="relatedSpot.isFavorite ? '#FB2C36' : 'none'"
                      :stroke="relatedSpot.isFavorite ? '#FB2C36' : 'white'"
                      stroke-width="1.667"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                </button>
              </div>
              <div class="card-content">
                <div class="card-header">
                  <h3 class="card-title">{{ relatedSpot.title }}</h3>
                  <div class="card-rating">
                    <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                      <path
                        d="M7.678 1.53C7.707 1.471 7.752 1.421 7.808 1.387C7.864 1.352 7.929 1.333 7.995 1.333C8.061 1.333 8.125 1.352 8.181 1.387C8.237 1.421 8.282 1.471 8.311 1.53L9.851 4.647C9.952 4.852 10.102 5.03 10.287 5.164C10.472 5.299 10.687 5.387 10.914 5.42L14.358 5.924C14.423 5.933 14.484 5.961 14.535 6.003C14.585 6.046 14.623 6.101 14.643 6.164C14.664 6.227 14.666 6.294 14.65 6.358C14.635 6.422 14.601 6.481 14.554 6.527L12.063 8.951C11.899 9.111 11.776 9.309 11.705 9.527C11.634 9.745 11.617 9.977 11.656 10.203L12.244 13.63C12.255 13.695 12.248 13.762 12.223 13.824C12.198 13.885 12.157 13.938 12.104 13.977C12.05 14.016 11.987 14.039 11.921 14.044C11.855 14.048 11.789 14.034 11.73 14.003L8.651 12.384C8.448 12.278 8.223 12.222 7.994 12.222C7.765 12.222 7.54 12.278 7.337 12.384L4.258 14.003C4.2 14.034 4.134 14.048 4.068 14.043C4.002 14.038 3.939 14.015 3.885 13.976C3.832 13.937 3.79 13.884 3.765 13.823C3.74 13.762 3.733 13.695 3.744 13.63L4.332 10.204C4.371 9.978 4.354 9.746 4.283 9.528C4.212 9.31 4.089 9.112 3.925 8.952L1.434 6.528C1.387 6.482 1.353 6.424 1.337 6.36C1.321 6.296 1.323 6.228 1.344 6.166C1.364 6.103 1.402 6.047 1.452 6.004C1.503 5.962 1.565 5.934 1.63 5.925L5.073 5.422C5.3 5.389 5.515 5.301 5.701 5.166C5.886 5.032 6.036 4.854 6.138 4.649L7.678 1.53Z"
                        fill="#FDC700"
                        stroke="#FDC700"
                        stroke-width="1.333"
                      />
                    </svg>
                    <span>{{ relatedSpot.rating }}</span>
                  </div>
                </div>
                <p class="card-hours">{{ relatedSpot.hours }}</p>
                <p class="card-address">
                  <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                    <path
                      d="M13.325 6.662C13.325 9.989 9.635 13.453 8.395 14.523C8.28 14.61 8.139 14.657 7.995 14.657C7.85 14.657 7.71 14.61 7.594 14.523C6.355 13.453 2.665 9.989 2.665 6.662C2.665 5.249 3.227 3.893 4.226 2.894C5.226 1.894 6.581 1.333 7.995 1.333C9.408 1.333 10.764 1.894 11.764 2.894C12.763 3.893 13.325 5.249 13.325 6.662Z"
                      stroke="#99A1AF"
                      stroke-width="1.332"
                    />
                    <path
                      d="M7.995 8.661C9.099 8.661 9.993 7.766 9.993 6.662C9.993 5.558 9.099 4.664 7.995 4.664C6.891 4.664 5.996 5.558 5.996 6.662C5.996 7.766 6.891 8.661 7.995 8.661Z"
                      stroke="#99A1AF"
                      stroke-width="1.332"
                    />
                  </svg>
                  {{ relatedSpot.address }}
                </p>
                <p class="card-tags">{{ relatedSpot.tags }}</p>
              </div>
            </div>
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
          <div v-for="review in reviews" :key="review.id" class="review-card">
            <div class="review-header">
              <div class="review-user-info">
                <div class="review-rating">
                  <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                    <path
                      d="M9.604 1.913C9.641 1.839 9.697 1.777 9.767 1.733C9.837 1.69 9.918 1.667 10 1.667C10.082 1.667 10.163 1.69 10.233 1.733C10.303 1.777 10.359 1.839 10.396 1.913L12.321 5.812C12.448 6.069 12.635 6.291 12.866 6.459C13.098 6.627 13.367 6.737 13.65 6.779L17.955 7.409C18.036 7.42 18.113 7.455 18.176 7.508C18.239 7.561 18.286 7.631 18.312 7.709C18.337 7.787 18.34 7.871 18.32 7.951C18.301 8.031 18.259 8.104 18.2 8.162L15.087 11.194C14.881 11.394 14.728 11.641 14.639 11.913C14.55 12.186 14.529 12.476 14.577 12.759L15.312 17.042C15.327 17.123 15.318 17.207 15.287 17.284C15.256 17.361 15.204 17.427 15.137 17.476C15.07 17.525 14.991 17.554 14.908 17.559C14.826 17.565 14.743 17.548 14.67 17.509L10.822 15.485C10.568 15.352 10.286 15.283 10 15.283C9.713 15.283 9.431 15.352 9.177 15.485L5.33 17.509C5.257 17.547 5.174 17.565 5.092 17.559C5.009 17.553 4.93 17.524 4.863 17.475C4.797 17.427 4.745 17.36 4.714 17.284C4.683 17.207 4.674 17.123 4.688 17.042L5.422 12.759C5.471 12.477 5.45 12.186 5.361 11.914C5.272 11.641 5.119 11.394 4.913 11.194L1.8 8.163C1.74 8.105 1.698 8.032 1.678 7.952C1.658 7.872 1.661 7.787 1.687 7.709C1.712 7.63 1.759 7.56 1.823 7.507C1.886 7.454 1.963 7.419 2.045 7.408L6.349 6.779C6.633 6.737 6.902 6.628 7.134 6.459C7.365 6.291 7.553 6.069 7.68 5.812L9.604 1.913Z"
                      fill="#9810FA"
                      stroke="#9810FA"
                      stroke-width="1.667"
                    />
                  </svg>
                  <span>{{ review.rating }}</span>
                </div>
                <div class="user-avatar-small">👤</div>
                <span class="user-name">{{ review.userName }}</span>
                <span class="user-tag">{{ review.userTag }}</span>
                <span class="user-age">{{ review.userAge }}</span>
                <span class="review-date">{{ review.date }}</span>
              </div>
            </div>

            <h3 class="review-title">{{ review.title }}</h3>
            <p class="review-content">{{ review.content }}</p>

            <div class="review-tags">
              <span v-for="(tag, index) in review.tags" :key="index" class="review-tag">
                {{ tag.icon }} {{ tag.text }}
              </span>
            </div>

            <div v-if="review.images && review.images.length > 0" class="review-images">
              <div v-for="(image, index) in review.images" :key="index" class="review-image">
                <img :src="image" :alt="`Review image ${index + 1}`" />
                <div class="play-overlay">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                    <path
                      d="M5 5C5 4.648 5.093 4.302 5.269 3.998C5.445 3.693 5.699 3.441 6.004 3.265C6.309 3.09 6.655 2.998 7.007 3C7.359 3.001 7.704 3.095 8.008 3.272L20.005 10.27C20.308 10.446 20.559 10.698 20.734 11.001C20.909 11.304 21.001 11.648 21.001 11.998C21.002 12.348 20.91 12.692 20.736 12.996C20.561 13.299 20.311 13.552 20.008 13.728L8.008 20.728C7.704 20.906 7.359 21 7.007 21.001C6.655 21.002 6.309 20.91 6.004 20.735C5.699 20.56 5.445 20.307 5.269 20.002C5.093 19.698 5 19.352 5 19V5Z"
                      fill="#1E2939"
                      stroke="#1E2939"
                      stroke-width="2"
                    />
                  </svg>
                </div>
              </div>
            </div>

            <div class="review-actions">
              <button class="helpful-btn">
                <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                  <path
                    d="M4.667 6.667V14.667M10 3.92L9.333 6.667H13.22C13.427 6.667 13.631 6.715 13.816 6.808C14.001 6.9 14.162 7.035 14.286 7.2C14.411 7.366 14.495 7.558 14.532 7.762C14.569 7.965 14.558 8.175 14.5 8.374L12.946 13.707C12.866 13.984 12.697 14.227 12.466 14.4C12.236 14.573 11.955 14.667 11.666 14.667H2.666C2.313 14.667 1.974 14.526 1.724 14.276C1.473 14.026 1.333 13.687 1.333 13.333V8C1.333 7.647 1.473 7.307 1.724 7.057C1.974 6.807 2.313 6.667 2.666 6.667H4.506C4.754 6.667 4.998 6.597 5.208 6.467C5.419 6.336 5.589 6.149 5.7 5.927L8 1.333C8.314 1.337 8.624 1.412 8.905 1.553C9.186 1.693 9.432 1.895 9.625 2.144C9.817 2.392 9.951 2.681 10.016 2.989C10.08 3.297 10.075 3.615 10 3.92Z"
                    stroke="#4A5565"
                    stroke-width="1.333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
                도움이 돼요 {{ review.helpfulCount }}
              </button>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

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

// 관련 스팟
const relatedSpots = ref([
  {
    id: "1",
    title: "감천문화마을",
    category: "tourist",
    rating: 4.5,
    hours: "AM 9:00 - PM 6:00",
    address: "부산광역시 사하구 감내2로 203",
    tags: "#감천 #마을",
    image: "https://placehold.co/280x192",
    isFavorite: true,
  },
  {
    id: "2",
    title: "강남 팝업스토어",
    category: "popup",
    rating: 4.5,
    hours: "AM 9:00 - PM 6:00",
    address: "서울특별시 강남구 테헤란로 203",
    tags: "#강남 #팝업",
    image: "https://placehold.co/280x192",
    isFavorite: true,
  },
  {
    id: "3",
    title: "벚꽃축제",
    category: "festival",
    rating: 4.5,
    hours: "AM 9:00 - PM 6:00",
    address: "서울특별시 여의도 한강공원 203",
    tags: "#벚꽃 #축제",
    image: "https://placehold.co/280x192",
    isFavorite: true,
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

// 카테고리 라벨
const getCategoryLabel = (category) => {
  const labels = {
    tourist: "관광지",
    popup: "팝업스토어",
    festival: "축제",
  };
  return labels[category] || category;
};

// 좋아요 토글
const toggleFavorite = () => {
  spot.value.isFavorite = !spot.value.isFavorite;
};

const toggleRelatedFavorite = (spotId) => {
  const relatedSpot = relatedSpots.value.find((s) => s.id === spotId);
  if (relatedSpot) {
    relatedSpot.isFavorite = !relatedSpot.isFavorite;
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
const goToSpot = (spotId) => {
  router.push(`/spots/${spotId}`);
  window.scrollTo({ top: 0, behavior: "smooth" });
};

// 컴포넌트 마운트 시 데이터 로드
onMounted(() => {
  // 실제로는 API 호출
  console.log("Loading spot:", route.params.id);
});
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.spot-detail {
  min-height: 100vh;
  background: white;
  font-family: "Arimo", sans-serif;
}

/* 헤더 */
.header {
  width: 100%;
  padding: 2rem;
  border-bottom: 1px solid #e5e7eb;
  background: white;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-container {
  max-width: 1920px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.logo {
  width: 201px;
  height: 52px;
  object-fit: contain;
}

.nav-menu {
  flex: 1;
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
}

.nav-link {
  padding: 0.5rem;
  border-radius: 0.5rem;
  text-decoration: none;
  color: #1f2937;
  font-size: 1rem;
  transition: background 0.2s;
}

.nav-link:hover {
  background: #f3f4f6;
}

.nav-link.router-link-exact-active {
  background: #dbeafe;
  color: #1e3a8a;
}

.user-avatar {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background: #f3f4f6;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
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
  opacity: 0.5;
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

/* 스팟 카드 */
.spot-card {
  flex: 0 0 280px;
  border-radius: 1rem;
  border: 1px solid #e5e7eb;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s;
}

.spot-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.card-image {
  position: relative;
  width: 100%;
  height: 192px;
  overflow: hidden;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.spot-card:hover .card-image img {
  transform: scale(1.05);
}

.card-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  padding: 0.25rem 0.75rem;
  border-radius: 9999px;
  font-size: 0.875rem;
  background: white;
}

.card-badge.tourist {
  color: #2563eb;
}

.card-badge.popup {
  color: #7c3aed;
}

.card-badge.festival {
  color: #ec4899;
}

.card-favorite {
  position: absolute;
  top: 12px;
  right: 12px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: none;
  background: white;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.2s;
}

.card-favorite:hover {
  transform: scale(1.1);
}

.card-content {
  padding: 1rem;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 0.75rem;
}

.card-title {
  font-size: 1rem;
  color: #1f2937;
  font-weight: normal;
}

.card-rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  font-size: 0.875rem;
  color: #4b5563;
}

.card-hours,
.card-address,
.card-tags {
  font-size: 0.875rem;
  color: #4b5563;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.card-tags {
  color: #2563eb;
  margin-bottom: 0;
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

/* 후기 카드 */
.review-card {
  padding: 1.5rem;
  border-radius: 0.625rem;
  border: 1px solid #e5e7eb;
  background: white;
}

.review-header {
  margin-bottom: 1rem;
}

.review-user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex-wrap: wrap;
}

.review-rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.user-avatar-small {
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background: #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
}

.user-name,
.user-tag,
.user-age,
.review-date {
  font-size: 1rem;
  color: #4b5563;
}

.review-title {
  font-size: 0.875rem;
  color: #374151;
  margin-bottom: 0.75rem;
  line-height: 1.5;
}

.review-content {
  font-size: 1rem;
  color: #374151;
  line-height: 1.5;
  margin-bottom: 1rem;
}

.review-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1rem;
}

.review-tag {
  padding: 0.5rem 0.75rem;
  border-radius: 9999px;
  border: 1px solid #e5e7eb;
  font-size: 0.875rem;
  color: #374151;
  background: white;
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.review-images {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
}

.review-image {
  position: relative;
  width: 128px;
  height: 128px;
  border-radius: 0.625rem;
  overflow: hidden;
}

.review-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.play-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
}

.play-overlay svg {
  width: 48px;
  height: 48px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 50%;
  padding: 0.75rem;
}

.review-actions {
  display: flex;
  gap: 0.5rem;
}

.helpful-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.5rem;
  border: none;
  background: none;
  color: #4b5563;
  font-size: 0.875rem;
  cursor: pointer;
  transition: background 0.2s;
  border-radius: 0.5rem;
}

.helpful-btn:hover {
  background: #f3f4f6;
}

/* 반응형 */
@media (max-width: 1024px) {
  .header-container {
    padding: 0 1rem;
  }

  .main-content {
    padding: 1.5rem 1rem;
  }

  .spots-grid {
    gap: 0.75rem;
  }

  .spot-card {
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
  .header {
    padding: 1rem;
  }

  .logo {
    width: 150px;
    height: auto;
  }

  .nav-menu {
    gap: 0.25rem;
  }

  .nav-link {
    font-size: 0.875rem;
    padding: 0.5rem;
  }

  .main-content {
    padding: 1rem;
  }

  .spot-title {
    font-size: 1.25rem;
  }

  .section-title {
    font-size: 1.25rem;
  }

  .spot-card {
    flex: 0 0 220px;
  }

  .card-image {
    height: 160px;
  }

  .review-card {
    padding: 1rem;
  }

  .review-user-info {
    font-size: 0.875rem;
  }

  .review-images {
    gap: 0.5rem;
  }

  .review-image {
    width: 96px;
    height: 96px;
  }
}

@media (max-width: 480px) {
  .header {
    padding: 0.75rem;
  }

  .logo {
    width: 120px;
  }

  .nav-menu {
    display: none; /* 모바일에서는 햄버거 메뉴로 대체 권장 */
  }

  .spot-title {
    font-size: 1.125rem;
  }

  .section-title {
    font-size: 1.125rem;
  }

  .spots-grid {
    gap: 0.5rem;
  }

  .spot-card {
    flex: 0 0 200px;
  }

  .card-content {
    padding: 0.75rem;
  }

  .review-content {
    font-size: 0.875rem;
  }

  .review-image {
    width: 80px;
    height: 80px;
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
