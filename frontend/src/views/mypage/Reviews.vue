<template>
  <div class="reviews-page">
    <!-- 페이지 헤더 -->
    <div class="page-header">
      <h1 class="page-title">내가 쓴 후기</h1>
      <p class="page-subtitle">총 {{ reviews.length }}개의 후기</p>
    </div>

    <!-- 후기 리스트 -->
    <div class="reviews-list">
      <MyReviewCard
        v-for="review in reviews"
        :key="review.id"
        :review="review"
        @edit="editReview"
        @delete="deleteReview"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MyReviewCard from "@/components/mypage/MyReviewCard.vue";

const reviews = ref([
  {
    id: 1,
    title: "감천문화마을",
    category: "관광지",
    date: "2024.12.01",
    location: "부산광역시 사하구 감내2로 203",
    rating: 5,
    image: "https://images.unsplash.com/photo-1578662996442-48f60103fc96?w=400",
    text: "정말 아름다운 곳이었습니다. 알록달록한 집들이 언덕에 빼곡히 들어차 있는 모습이 인상적이었어요. 사진 찍기 좋은 포인트도 많고, 골목골목 산책하는 재미가 있습니다. 다만 경사가 있어서 편한 신발을 신고 가는 것을 추천합니다!",
  },
  {
    id: 2,
    title: "강남 팝업스토어",
    category: "팝업스토어",
    date: "2024.11.25",
    location: "서울특별시 강남구 테헤란로 203",
    rating: 4,
    image: "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=400",
    text: "최근 오픈한 브랜드 팝업스토어인데 정말 세련되고 트렌디한 공간이었어요. 포토존도 잘 꾸며져 있고, 한정판 굿즈도 구매할 수 있어서 좋았습니다. 주말에는 사람이 많아서 평일 방문을 추천드려요.",
  },
  {
    id: 3,
    title: "벚꽃축제",
    category: "축제",
    date: "2024.11.20",
    location: "서울특별시 여의도 한강공원 203",
    rating: 5,
    image: "https://images.unsplash.com/photo-1522383225653-ed111181a951?w=400",
    text: "벚꽃이 만개한 시기에 방문했는데 정말 환상적이었습니다! 밤에는 야간 조명도 켜져서 낮과는 또 다른 분위기를 즐길 수 있어요. 다양한 먹거리와 공연도 있어서 가족, 연인과 함께 오기 좋은 축제입니다.",
  },
]);

const editReview = (id) => {
  console.log("Edit review:", id);
  // 수정 페이지로 이동 또는 모달 열기
};

const deleteReview = (id) => {
  if (confirm("정말 삭제하시겠습니까?")) {
    reviews.value = reviews.value.filter((review) => review.id !== id);
  }
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.reviews-page {
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 페이지 헤더 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

.page-title {
  font-size: 20px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.6;
}

.page-subtitle {
  font-size: 16px;
  font-weight: 400;
  color: #4a5568;
  margin: 0;
  line-height: 1.5;
}

/* 후기 리스트 */
.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }
}

@media (max-width: 480px) {
  .page-title {
    font-size: 18px;
  }

  .page-subtitle {
    font-size: 14px;
  }
}
</style>
