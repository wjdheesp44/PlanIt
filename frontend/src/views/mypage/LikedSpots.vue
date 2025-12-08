<template>
  <div class="liked-spots-page">
    <!-- 메인 콘텐츠 -->
    <main class="main-content">
      <!-- 페이지 제목 -->
      <div class="page-header">
        <h1 class="page-title">좋아요한 스팟</h1>
      </div>

      <!-- 스팟 그리드 -->
      <div class="spots-grid">
        <div v-for="spot in likedSpots" :key="spot.id" class="spot-card">
          <!-- 카드 이미지 -->
          <div class="card-image">
            <img :src="spot.image" :alt="spot.name" />
            <span class="badge" :class="getBadgeClass(spot.badge)">
              {{ spot.badge }}
            </span>
            <button class="favorite-btn active" @click="removeFavorite(spot.id)">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor">
                <path
                  d="M1.667 7.917c.002-.928.282-1.834.807-2.598.524-.763 1.24-1.35 2.066-1.69.826-.34 1.733-.41 2.61-.199.878.21 1.682.694 2.315 1.386.044.046.097.083.155.108.058.025.121.038.185.038s.127-.013.185-.038c.058-.025.111-.062.155-.108.618-.706 1.422-1.196 2.301-1.405.879-.21 1.786-.148 2.614.139.828.287 1.545.878 2.068 1.645.523.767.801 1.675.797 2.604 0 1.908-1.25 3.333-2.5 4.583l-4.576 4.428c-.156.185-.346.338-.561.452-.215.115-.448.19-.688.223-.24.032-.484.022-.719-.032-.236-.054-.46-.15-.66-.283l-3.833-3.563c-1.25-1.25-2.5-2.667-2.5-4.583z"
                />
              </svg>
            </button>
          </div>

          <!-- 카드 내용 -->
          <div class="card-content">
            <div class="card-header">
              <h3 class="card-title">{{ spot.name }}</h3>
              <div class="rating">
                <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                  <path
                    d="M7.678 1.529c.03-.059.075-.109.131-.144.056-.035.121-.053.187-.053s.131.018.187.053c.056.035.101.085.131.144L9.85 4.646c.101.206.251.383.436.518.185.135.4.222.627.255l3.441.504c.066.01.127.037.178.08.05.042.088.098.108.161.02.063.023.13.007.194-.016.064-.05.122-.097.168l-2.489 2.424c-.164.16-.287.357-.358.575-.071.218-.088.45-.05.676l.588 3.424c.011.066.004.133-.021.195-.025.061-.066.114-.12.153-.054.039-.117.062-.183.067-.066.004-.132-.01-.191-.041l-3.076-1.617c-.203-.107-.478-.163-.767-.163s-.564.056-.767.163l-3.076 1.617c-.058.031-.124.045-.19.041-.066-.005-.129-.028-.183-.067-.054-.039-.095-.092-.12-.153-.026-.062-.033-.129-.022-.195l.587-3.424c.039-.226.022-.458-.047-.676-.071-.218-.194-.415-.358-.575L1.439 6.526c-.048-.046-.082-.104-.098-.168-.016-.064-.013-.131.007-.194.02-.063.058-.119.109-.161.05-.043.112-.07.178-.08l3.441-.504c.227-.034.442-.121.627-.255.185-.135.335-.312.436-.518l1.539-3.117z"
                    fill="#FDC700"
                    stroke="#FDC700"
                    stroke-width="1.332"
                  />
                </svg>
                <span class="rating-value">{{ spot.rating }}</span>
              </div>
            </div>

            <p class="card-time">{{ spot.time }}</p>

            <p class="card-location">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                <path
                  d="M13.325 6.662c0 3.326-3.69 6.791-4.93 7.861-.115.087-.256.134-.4.134-.145 0-.286-.047-.401-.134-1.24-1.07-4.929-4.535-4.929-7.861 0-1.414.562-2.77 1.562-3.77 1-.999 2.355-1.56 3.769-1.56 1.413 0 2.769.561 3.768 1.56 1 1 1.561 2.356 1.561 3.77z"
                  stroke="#99A1AF"
                  stroke-width="1.332"
                />
                <path
                  d="M7.995 8.661c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2z"
                  stroke="#99A1AF"
                  stroke-width="1.332"
                />
              </svg>
              {{ spot.location }}
            </p>

            <p class="card-tags">{{ spot.tags }}</p>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MyPageSidebar from "@/components/MyPageSidebar.vue";

const likedSpots = ref([
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
    name: "감천문화마을",
    image: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
    badge: "관광지",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "부산광역시 사하구 감내2로 203",
    tags: "#감천 #마을",
  },
  {
    id: 5,
    name: "강남 팝업스토어",
    image: "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=400",
    badge: "팝업스토어",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "서울특별시 강남구 테헤란로 203",
    tags: "#강남 #팝업",
  },
  {
    id: 6,
    name: "벚꽃축제",
    image: "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=400",
    badge: "축제",
    rating: 4.5,
    time: "AM 9:00 - PM 6:00",
    location: "서울특별시 여의도 한강공원 203",
    tags: "#벚꽃 #축제",
  },
]);

const getBadgeClass = (badge) => {
  const badgeMap = {
    관광지: "badge-tourist",
    팝업스토어: "badge-popup",
    축제: "badge-festival",
  };
  return badgeMap[badge] || "";
};

const removeFavorite = (id) => {
  likedSpots.value = likedSpots.value.filter((spot) => spot.id !== id);
};

const handleMenuChange = (menu) => {
  console.log("Menu changed:", menu);
  // 라우터로 페이지 이동 처리
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.liked-spots-page {
  display: flex;
  min-height: 100vh;
  background: #ffffff;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 메인 콘텐츠 */
.main-content {
  flex: 1;
  padding: 2rem 3rem;
  max-width: 1400px;
  margin: 0 auto;
}

/* 페이지 헤더 */
.page-header {
  margin-bottom: 2rem;
}

.page-title {
  font-size: 32px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.4;
}

/* 스팟 그리드 */
.spots-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1.5rem;
}

/* 스팟 카드 */
.spot-card {
  background: #ffffff;
  border: 0.83px solid #e5e7eb;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.2s ease;
  cursor: pointer;
}

.spot-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transform: translateY(-2px);
}

/* 카드 이미지 */
.card-image {
  position: relative;
  width: 100%;
  height: 192px;
  overflow: hidden;
  background: #f5f5f5;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.spot-card:hover .card-image img {
  transform: scale(1.05);
}

.badge {
  position: absolute;
  top: 12px;
  left: 12px;
  padding: 4px 12px;
  background: #ffffff;
  border-radius: 9999px;
  font-size: 14px;
  font-weight: 400;
  line-height: 1.43;
}

.badge-tourist {
  color: #2563eb;
}

.badge-popup {
  color: #7c3aed;
}

.badge-festival {
  color: #ec4899;
}

.favorite-btn {
  position: absolute;
  top: 12px;
  right: 12px;
  width: 36px;
  height: 36px;
  background: #ffffff;
  border: none;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s ease;
}

.favorite-btn svg {
  width: 20px;
  height: 20px;
}

.favorite-btn.active {
  color: #fb2c36;
}

.favorite-btn:hover {
  transform: scale(1.1);
  box-shadow: 0 2px 8px rgba(251, 44, 54, 0.3);
}

/* 카드 내용 */
.card-content {
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.5rem;
}

.card-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
  flex: 1;
}

.rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  flex-shrink: 0;
}

.rating-value {
  font-size: 14px;
  color: #4a5568;
  font-weight: 400;
  line-height: 1.43;
}

.card-time,
.card-location,
.card-tags {
  font-size: 14px;
  color: #4a5568;
  margin: 0;
  line-height: 1.43;
}

.card-location {
  display: flex;
  align-items: flex-start;
  gap: 0.25rem;
}

.card-location svg {
  flex-shrink: 0;
  margin-top: 2px;
}

.card-tags {
  color: #2563eb;
}

/* 반응형 - 태블릿 */
@media (max-width: 1200px) {
  .liked-spots-page {
    flex-direction: column;
  }

  .main-content {
    padding: 2rem 1.5rem;
  }

  .spots-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .main-content {
    padding: 1.5rem 1rem;
  }

  .page-title {
    font-size: 24px;
  }

  .spots-grid {
    grid-template-columns: 1fr;
    gap: 1rem;
  }

  .card-image {
    height: 160px;
  }
}

@media (max-width: 480px) {
  .main-content {
    padding: 1rem 0.75rem;
  }

  .page-title {
    font-size: 20px;
  }
}
</style>
