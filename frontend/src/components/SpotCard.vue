<template>
  <div class="spot-card" @click="handleClick">
    <div class="card-image">
      <img :src="spot.image" :alt="spot.name" />
      <span class="badge" :class="badgeClass">{{ spot.badge }}</span>
      <button class="favorite-btn" @click.stop="handleFavorite">
        <svg width="20" height="20" viewBox="0 0 20 20" fill="currentColor">
          <path
            d="M10 17.5l-6.25-6.25C2.5 10 1.67 7.92 1.67 7.92c0-1.03.37-2.08 1.1-3.2C3.74 3.54 4.74 3 6 3c1.22 0 2.22.46 3 1.38C9.78 3.46 10.78 3 12 3c1.26 0 2.26.54 3 1.72.73 1.12 1.1 2.17 1.1 3.2 0 0-0.83 2.08-2.08 3.33L10 17.5z"
          />
        </svg>
      </button>
    </div>
    <div class="card-content">
      <div class="card-header">
        <h3 class="card-title">{{ spot.name }}</h3>
        <div class="rating">
          <span class="star">⭐</span>
          <span class="rating-value">{{ spot.rating }}</span>
        </div>
      </div>
      <p class="card-time">{{ spot.time }}</p>
      <p class="card-location">
        <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
          <path
            d="M13.3 6.7c0 3.3-3.7 6.8-4.9 7.9-.1.1-.2.1-.4.1-.1 0-.3 0-.4-.1-1.2-1.1-4.9-4.5-4.9-7.9 0-1.4.6-2.8 1.6-3.8 1-1 2.4-1.6 3.8-1.6 1.4 0 2.8.6 3.8 1.6 1 1 1.6 2.4 1.6 3.8z"
            stroke="#99A1AF"
            stroke-width="1.3"
          />
          <path
            d="M8 8.7c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z"
            stroke="#99A1AF"
            stroke-width="1.3"
          />
        </svg>
        {{ spot.location }}
      </p>
      <p class="card-tags">{{ spot.tags }}</p>
    </div>
  </div>
</template>

<script setup>
import { computed, defineProps, defineEmits } from "vue";

const props = defineProps({
  spot: {
    type: Object,
    required: true,
  },
});

const emit = defineEmits(["click", "favorite"]);

const badgeClass = computed(() => {
  const badgeMap = {
    관광지: "badge-tourist",
    팝업스토어: "badge-popup",
    축제: "badge-festival",
  };
  return badgeMap[props.spot.badge] || "";
});

const handleClick = () => {
  emit("click", props.spot);
};

const handleFavorite = () => {
  emit("favorite", props.spot);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;600;700&display=swap");

.spot-card {
  background: #f5f5f5;
  border-radius: 16px;
  overflow: hidden;
  border: 1px solid #e5e5e5;
  transition: all 0.25s ease;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  font-family: "Noto Sans KR", -apple-system, BlinkMacSystemFont, sans-serif;
}

.spot-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
  border-color: #d4d4d4;
}

.card-image {
  position: relative;
  width: 100%;
  aspect-ratio: 280 / 192;
  overflow: hidden;
  background: #e5e5e5;
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.4s ease;
}

.spot-card:hover .card-image img {
  transform: scale(1.04);
}

.badge {
  position: absolute;
  top: 12px;
  left: 12px;
  padding: 4px 12px;
  border-radius: 14px;
  background: rgba(255, 255, 255, 0.95);
  font-size: 12px;
  font-weight: 500;
  backdrop-filter: blur(4px);
}

.badge-tourist {
  color: #3b82f6;
}

.badge-popup {
  color: #8b5cf6;
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
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.95);
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #ef4444;
  transition: all 0.2s ease;
  backdrop-filter: blur(4px);
}

.favorite-btn svg {
  width: 20px;
  height: 20px;
}

.favorite-btn:hover {
  transform: scale(1.1);
  background: #ffffff;
  box-shadow: 0 2px 12px rgba(239, 68, 68, 0.25);
}

.card-content {
  padding: 1rem;
  background: #ffffff;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.625rem;
}

.card-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0;
}

.rating {
  display: flex;
  align-items: center;
  gap: 0.25rem;
}

.star {
  font-size: 15px;
  color: #fbbf24;
}

.rating-value {
  font-size: 13px;
  color: #52525b;
  font-weight: 500;
}

.card-time,
.card-location,
.card-tags {
  font-size: 13px;
  color: #71717a;
  margin: 0.5rem 0 0 0;
  line-height: 1.5;
}

.card-location {
  display: flex;
  align-items: flex-start;
  gap: 0.375rem;
}

.card-location svg {
  flex-shrink: 0;
  margin-top: 2px;
}

.card-tags {
  color: #3b82f6;
  font-weight: 400;
}
</style>
