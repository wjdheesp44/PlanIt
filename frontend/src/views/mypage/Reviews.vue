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
      <ReviewEditModal
        v-if="isEditOpen"
        :review="selectedReview"
        @confirm="confirmEdit"
        @close="closeEdit"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MyReviewCard from "@/components/mypage/MyReviewCard.vue";
import { onMounted } from "vue";
import reviewApi from "@/api/review/reviewApi";
import ReviewEditModal from "@/components/mypage/ReviewEditModal.vue";

const isEditOpen = ref(false);
const selectedReview = ref(null);

onMounted(() => {
  loadMyReviews();
});

const reviews = ref([]);

const loadMyReviews = async () => {
  const res = await reviewApi.getMyReviews();
  reviews.value = res.data;
};

const editReview = (review) => {
  selectedReview.value = { ...review }; // 복사
  isEditOpen.value = true;
};

const confirmEdit = async (updatedReview) => {
  await reviewApi.updateReview(
    updatedReview.id,
    {
      rating: updatedReview.rating,
      content: updatedReview.text,
    }
  );

  const idx = reviews.value.findIndex(r => r.id === updatedReview.id);
  if (idx !== -1) {
    reviews.value[idx] = { ...updatedReview };
  }

  isEditOpen.value = false;
};

const closeEdit = () => {
  isEditOpen.value = false;
};

const deleteReview = async (id) => {
  if (confirm("정말 삭제하시겠습니까?")) {
    await reviewApi.deleteReview(id);
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
