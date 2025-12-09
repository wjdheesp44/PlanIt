<template>
  <div class="review-card">
    <!-- 이미지 -->
    <img :src="review.image" :alt="review.title" class="review-image" />

    <!-- 콘텐츠 -->
    <div class="review-content">
      <!-- 헤더 -->
      <div class="review-header">
        <div class="title-section">
          <div class="title-row">
            <h3 class="review-title">{{ review.title }}</h3>
            <span class="category-badge" :class="categoryClass">
              {{ review.category }}
            </span>
          </div>
          <div class="meta-row">
            <div class="meta-item">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                <path
                  d="M5.33 1.333v2.665M10.66 1.333v2.665M12.659 2.665H3.332c-.736 0-1.332.597-1.332 1.333v9.327c0 .736.596 1.332 1.332 1.332h9.327c.736 0 1.332-.596 1.332-1.332V3.998c0-.736-.596-1.333-1.332-1.333zM2 6.662h11.991"
                  stroke="currentColor"
                  stroke-width="1.332"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
              <span>{{ review.date }}</span>
            </div>
            <div class="meta-item">
              <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                <path
                  d="M13.325 6.662c0 3.326-3.69 6.791-4.93 7.861-.115.087-.256.134-.4.134-.145 0-.286-.047-.401-.134-1.24-1.07-4.929-4.535-4.929-7.861 0-1.414.562-2.77 1.562-3.77 1-.999 2.355-1.56 3.769-1.56 1.413 0 2.769.561 3.768 1.56 1 1 1.561 2.356 1.561 3.77zM7.995 8.661c1.105 0 2-.895 2-2s-.895-2-2-2-2 .895-2 2 .895 2 2 2z"
                  stroke="currentColor"
                  stroke-width="1.332"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
              <span>{{ review.location }}</span>
            </div>
          </div>
        </div>
        <div class="action-buttons">
          <button class="icon-button" @click="handleEdit">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path
                d="M14.107 4.538c.352-.352.55-.83.55-1.328 0-.498-.198-.976-.55-1.328-.352-.352-.83-.55-1.328-.55-.498 0-.976.198-1.328.55L2.56 10.776c-.155.154-.269.344-.333.553l-.88 2.9c-.017.057-.018.118-.003.176.015.058.045.111.088.154.043.042.097.072.155.087.058.015.119.014.176-.003l2.9-.88c.209-.063.399-.177.553-.333l8.894-8.892z"
                stroke="currentColor"
                stroke-width="1.332"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
          <button class="icon-button delete" @click="handleDelete">
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
              <path
                d="M6.662 7.329v3.997M9.327 7.329v3.997M12.658 3.997v9.327c0 .354-.14.693-.39.943-.25.25-.589.39-.943.39H4.664c-.354 0-.693-.14-.943-.39-.25-.25-.39-.589-.39-.943V3.997M2 3.997h11.991M5.33 3.997v-1.332c0-.354.14-.693.39-.943.25-.25.589-.39.943-.39h2.665c.354 0 .693.14.943.39.25.25.39.589.39.943v1.332"
                stroke="currentColor"
                stroke-width="1.332"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
        </div>
      </div>

      <!-- 별점 -->
      <div class="rating-row">
        <svg v-for="star in 5" :key="star" width="20" height="20" viewBox="0 0 20 20">
          <path
            d="M9.604 1.912c.036-.073.093-.135.163-.179.07-.043.15-.066.233-.066.082 0 .163.023.233.066.07.044.126.106.163.179l1.925 3.9c.127.256.314.478.546.646.231.169.5.278.783.32l4.305.63c.065.012.128.046.191.099.063.053.11.123.136.201.025.078.028.162.008.242-.018.08-.06.153-.119.211l-3.113 3.032c-.195.2-.339.447-.427.72-.089.272-.106.562-.058.844l.735 4.283c.011.065.004.133-.021.196-.025.063-.067.117-.134.165-.067.049-.146.078-.229.084-.083.005-.166-.009-.239-.048l-3.849-2.023c-.243-.133-.525-.203-.812-.203-.287 0-.569.07-.812.203l-3.849 2.023c-.071.039-.154.053-.226.047-.073-.006-.152-.035-.219-.084-.067-.048-.108-.115-.139-.192-.03-.076-.039-.16-.024-.225l.735-4.283c.038-.282.017-.572-.072-.844-.089-.273-.243-.52-.448-.72L1.8 8.163c-.048-.057-.08-.13-.1-.21-.02-.08-.018-.164.007-.243.021-.079.068-.148.131-.202.064-.053.14-.087.212-.096l4.305-.63c.283-.042.552-.131.783-.3.232-.168.419-.39.546-.646l1.925-3.9z"
            :fill="star <= review.rating ? '#FDC700' : 'none'"
            :stroke="star <= review.rating ? '#FDC700' : '#D1D5DC'"
            stroke-width="1.667"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </div>

      <!-- 후기 내용 -->
      <p class="review-text">{{ review.text }}</p>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";

const props = defineProps({
  review: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["edit", "delete"]);

const categoryClass = computed(() => {
  const categoryMap = {
    관광지: "category-tourist",
    팝업스토어: "category-popup",
    축제: "category-festival",
  };
  return categoryMap[props.review.category] || "";
});

const handleEdit = () => {
  emit("edit", props.review.id);
};

const handleDelete = () => {
  emit("delete", props.review.id);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

/* 후기 카드 */
.review-card {
  display: flex;
  background: #ffffff;
  border: 0.83px solid #e5e7eb;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.2s ease;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

.review-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

/* 이미지 */
.review-image {
  width: 256px;
  height: 100%;
  object-fit: cover;
  flex-shrink: 0;
}

/* 콘텐츠 */
.review-content {
  flex: 1;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

/* 헤더 */
.review-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 1rem;
}

.title-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.title-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.review-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.category-badge {
  padding: 2px 8px;
  border-radius: 9999px;
  font-size: 14px;
  font-weight: 400;
  line-height: 1.43;
}

.category-tourist {
  background: #dbeafe;
  color: #2563eb;
}

.category-popup {
  background: #f3e8ff;
  color: #7c3aed;
}

.category-festival {
  background: #fce7f3;
  color: #ec4899;
}

.meta-row {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  font-size: 14px;
  color: #4a5568;
  line-height: 1.43;
}

.meta-item svg {
  width: 16px;
  height: 16px;
  color: #4a5565;
  flex-shrink: 0;
}

/* 액션 버튼 */
.action-buttons {
  display: flex;
  gap: 0.5rem;
}

.icon-button {
  width: 32px;
  height: 32px;
  padding: 8px;
  border: none;
  background: transparent;
  border-radius: 10px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s ease;
  color: #4a5565;
}

.icon-button:hover {
  background: #f3f4f6;
}

.icon-button.delete {
  color: #e7000b;
}

.icon-button.delete:hover {
  background: #fef2f2;
}

.icon-button svg {
  width: 16px;
  height: 16px;
}

/* 별점 */
.rating-row {
  display: flex;
  gap: 0.25rem;
}

.rating-row svg {
  flex-shrink: 0;
}

/* 후기 내용 */
.review-text {
  font-size: 16px;
  font-weight: 400;
  color: #374151;
  line-height: 1.5;
  margin: 0;
}

/* 반응형 - 태블릿 */
@media (max-width: 1024px) {
  .review-card {
    flex-direction: column;
  }

  .review-image {
    width: 100%;
    height: 256px;
  }
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .review-content {
    padding: 1rem;
  }

  .review-header {
    flex-direction: column;
  }

  .action-buttons {
    width: 100%;
    justify-content: flex-end;
  }

  .meta-row {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }

  .review-image {
    height: 192px;
  }
}

@media (max-width: 480px) {
  .review-title {
    font-size: 14px;
  }

  .review-text {
    font-size: 14px;
  }
}
</style>
