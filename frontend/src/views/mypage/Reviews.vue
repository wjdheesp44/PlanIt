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
import { onMounted } from "vue";
import reviewApi from "@/api/review/reviewApi";
import { jwtDecode } from "jwt-decode";

const getLoginUserId = () => {
  const token = localStorage.getItem("accessToken");
  if (!token) return null;

  const decoded = jwtDecode(token);
  console.log(decoded)
  return decoded.username;
};

const username = getLoginUserId();

onMounted(() => {
  loadMyReviews();
});

const reviews = ref([]);

const loadMyReviews = async () => {
  const res = await reviewApi.getMyReviews(username);
  reviews.value = res.data;
};

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
