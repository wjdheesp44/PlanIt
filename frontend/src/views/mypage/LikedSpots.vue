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
import { ref } from "vue";
import { useRouter } from "vue-router";
import SpotCard from "@/components/SpotCard.vue";

const router = useRouter();

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
    isFavorite: true,
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
    isFavorite: true,
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
    isFavorite: true,
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
    isFavorite: true,
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
    isFavorite: true,
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
    isFavorite: true,
  },
]);

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
