<template>
  <div class="liked-spots-page">
    <!-- 메인 콘텐츠 -->
    <main class="main-content">
      <!-- 페이지 헤더 -->
      <div class="page-header">
        <h1 class="page-title">좋아요한 스팟</h1>
      </div>

      <!-- 스팟 그리드 -->
      <div class="spots-grid">
        <SpotCard
          v-for="spot in likedSpots"
          :key="spot.id"
          :spot="spot"
          @click="handleSpotClick"
          @favorite="handleFavorite"
        />
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getLikedSpots } from "@/api/likes/likesApi";
import SpotCard from "@/components/SpotCard.vue";

const router = useRouter();
const likedSpots = ref([]);

// 스팟 클릭 - 상세 페이지로 이동
const handleSpotClick = (spot) => {
  router.push(`/spots/${spot.id}`);
};

// 좋아요 토글 - 목록에서 제거
const handleFavorite = (spot) => {
  // 좋아요한 스팟 페이지에서는 좋아요 해제 = 목록에서 제거
  likedSpots.value = likedSpots.value.filter((s) => s.id !== spot.id);

  // 실제로는 API 호출로 서버에 좋아요 상태 업데이트
  console.log("Removed from favorites:", spot);
};

const formatTime = (dateTime) => {
  if (!dateTime) return null;
  return dateTime.split("T")[0]; // YYYY-MM-DD
};

onMounted(async () => {
  try {
    const res = await getLikedSpots();
    likedSpots.value = res.data.map((item) => ({
      id: item.id,
      name: item.title,                 // 백엔드 title → 프론트 name
      image: item.image1,
      badge: item.contentType,           // ATTRACTION / FESTIVAL / POPUP
      rating: item.avgRating,
      ratingCount: item.ratingCount,
      time:
        item.startTime && item.endTime
          ? `${formatTime(item.startTime)} - ${formatTime(item.endTime)}`
          : "상시가능",
      location: item.doroAddr ?? item.jibunAddr,
      isFavorite: item.isFavorite,
    }));
  } catch (e) {
    console.error("좋아요 목록 조회 실패", e);
  }
});
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
