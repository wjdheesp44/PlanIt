<template>
  <div class="review-card">
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
        <span class="user-name">{{ review.nickname }}</span>
        <span class="user-tag">{{ review.userTag }}</span>
        <span class="user-age">{{ review.userAge }}</span>
        <span class="review-date">{{ review.createdAt }}</span>
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
      <button class="helpful-btn" @click="handleHelpful">
        <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
          <path
            d="M4.667 6.667V14.667M10 3.92L9.333 6.667H13.22C13.427 6.667 13.631 6.715 13.816 6.808C14.001 6.9 14.162 7.035 14.286 7.2C14.411 7.366 14.495 7.558 14.532 7.762C14.569 7.965 14.558 8.175 14.5 8.374L12.946 13.707C12.866 13.984 12.697 14.227 12.466 14.4C12.236 14.573 11.955 14.667 11.666 14.667H2.666C2.313 14.667 1.974 14.526 1.724 14.276C1.473 14.026 1.333 13.687 1.333 13.333V8C1.333 7.647 1.473 7.307 1.724 7.057C1.974 6.807 2.313 6.667 2.666 6.667H4.506C4.754 6.667 4.998 6.597 5.208 6.467C5.419 6.336 5.589 6.149 5.7 5.927L8 1.333C8.314 1.337 8.624 1.412 8.905 1.553C9.186 1.693 9.432 1.895 9.625 2.144C9.817 2.392 9.951 2.681 10.016 2.989C10.08 3.297 10.075 3.615 10 3.92Z"
            stroke="#4A5565"
            stroke-width="1.333"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        도움이 돼요 <span v-if="review.helpfulCount > 0">{{ review.helpfulCount }}</span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  review: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["helpful"]);

const handleHelpful = () => {
  emit("helpful", props.review.id);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.review-card {
  padding: 1.5rem;
  border-radius: 0.625rem;
  border: 1px solid #e5e7eb;
  background: white;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
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
@media (max-width: 768px) {
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
  .review-content {
    font-size: 0.875rem;
  }

  .review-image {
    width: 80px;
    height: 80px;
  }
}
</style>
