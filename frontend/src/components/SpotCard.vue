<template>
  <div class="spot-card" @click="handleClick">
    <div class="card-image">
      <img :src="spot.image || placeholderImage" :alt="spot.name" @error="handleImageError" />
      <span class="badge" :class="badgeClass">{{ spot.badge }}</span>
      <button class="favorite-btn" @click.stop="handleFavorite">
        <svg
          v-if="spot.isFavorite"
          width="27"
          height="23"
          viewBox="0 0 27 23"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M0.833008 7.64098C0.833033 6.26763 1.255 4.92658 2.04317 3.79497C2.83134 2.66336 3.94864 1.79442 5.2475 1.30291C6.54637 0.8114 7.96568 0.720448 9.318 1.04207C10.6703 1.36368 11.892 2.08274 12.8217 3.10427C12.8872 3.1734 12.9664 3.22851 13.0543 3.26619C13.1423 3.30387 13.2371 3.32331 13.333 3.32331C13.4288 3.32331 13.5237 3.30387 13.6116 3.26619C13.6996 3.22851 13.7787 3.1734 13.8442 3.10427C14.771 2.0761 15.993 1.351 17.3475 1.02548C18.702 0.699951 20.1248 0.78944 21.4265 1.28203C22.7283 1.77463 23.8472 2.64696 24.6343 3.78293C25.4215 4.91889 25.8396 6.26461 25.8329 7.64098C25.8329 10.4672 23.9579 12.5776 22.0829 14.4288L15.218 20.9858C14.985 21.2499 14.6979 21.4621 14.3755 21.6082C14.0532 21.7543 13.703 21.831 13.3483 21.8332C12.9936 21.8354 12.6425 21.7631 12.3183 21.621C11.9941 21.479 11.7043 21.2704 11.468 21.0092L4.583 14.4288C2.708 12.5776 0.833008 10.4795 0.833008 7.64098Z"
            fill="#FB2C36"
            stroke="#FB2C36"
            stroke-width="1.66667"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <svg
          v-else
          width="27"
          height="23"
          viewBox="0 0 27 23"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M0.833008 7.64098C0.833033 6.26763 1.255 4.92658 2.04317 3.79497C2.83134 2.66336 3.94864 1.79442 5.2475 1.30291C6.54637 0.8114 7.96568 0.720448 9.318 1.04207C10.6703 1.36368 11.892 2.08274 12.8217 3.10427C12.8872 3.1734 12.9664 3.22851 13.0543 3.26619C13.1423 3.30387 13.2371 3.32331 13.333 3.32331C13.4288 3.32331 13.5237 3.30387 13.6116 3.26619C13.6996 3.22851 13.7787 3.1734 13.8442 3.10427C14.771 2.0761 15.993 1.351 17.3475 1.02548C18.702 0.699951 20.1248 0.78944 21.4265 1.28203C22.7283 1.77463 23.8472 2.64696 24.6343 3.78293C25.4215 4.91889 25.8396 6.26461 25.8329 7.64098C25.8329 10.4672 23.9579 12.5776 22.0829 14.4288L15.218 20.9858C14.985 21.2499 14.6979 21.4621 14.3755 21.6082C14.0532 21.7543 13.703 21.831 13.3483 21.8332C12.9936 21.8354 12.6425 21.7631 12.3183 21.621C11.9941 21.479 11.7043 21.2704 11.468 21.0092L4.583 14.4288C2.708 12.5776 0.833008 10.4795 0.833008 7.64098Z"
            stroke="#FB2C36"
            stroke-width="1.8"
            stroke-linecap="round"
            stroke-linejoin="round"
            fill="none"
          />
        </svg>
      </button>
    </div>
    <div class="card-content">
      <div class="card-header">
        <h3 class="card-title">{{ spot.name }}</h3>
        <div class="rating">
          <svg
            width="16"
            height="16"
            viewBox="0 -2 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M9.604 1.912c.036-.073.093-.135.163-.179.07-.043.15-.066.233-.066.082 0 .163.023.233.066.07.044.126.106.163.179l1.925 3.9c.127.256.314.478.546.646.231.169.5.278.783.32l4.305.63c.065.012.128.046.191.099.063.053.11.123.136.201.025.078.028.162.008.242-.018.08-.06.153-.119.211l-3.113 3.032c-.195.2-.339.447-.427.72-.089.272-.106.562-.058.844l.735 4.283c.011.065.004.133-.021.196-.025.063-.067.117-.134.165-.067.049-.146.078-.229.084-.083.005-.166-.009-.239-.048l-3.849-2.023c-.243-.133-.525-.203-.812-.203-.287 0-.569.07-.812.203l-3.849 2.023c-.071.039-.154.053-.226.047-.073-.006-.152-.035-.219-.084-.067-.048-.108-.115-.139-.192-.03-.076-.039-.16-.024-.225l.735-4.283c.038-.282.017-.572-.072-.844-.089-.273-.243-.52-.448-.72L1.8 8.163c-.048-.057-.08-.13-.1-.21-.02-.08-.018-.164.007-.243.021-.079.068-.148.131-.202.064-.053.14-.087.212-.096l4.305-.63c.283-.042.552-.131.783-.3.232-.168.419-.39.546-.646l1.925-3.9z"
              fill="#FBBF24"
              stroke="#FBBF24"
              stroke-width="1.667"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
          <span class="rating-value" v-if="spot.rating > 0">{{ spot.rating }}</span>
          <span class="rating-value" v-else>별점없음</span>
        </div>
      </div>
      <div class="card-time">
        <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
          <path
            d="M5.33 1.333v2.665M10.66 1.333v2.665M12.659 2.665H3.332c-.736 0-1.332.597-1.332 1.333v9.327c0 .736.596 1.332 1.332 1.332h9.327c.736 0 1.332-.596 1.332-1.332V3.998c0-.736-.596-1.333-1.332-1.333zM2 6.662h11.991"
            stroke="currentColor"
            stroke-width="1.332"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <span>{{ spot.time }}</span>
      </div>
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
const placeholderImage =
  "https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg";

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

const handleImageError = (event) => {
  event.target.src = placeholderImage;
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
  top: 14px;
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
  top: 18px;
  right: 16px;
  background: transparent;
  border: none;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.favorite-btn svg {
  width: 27px;
  height: 23px;
}

.favorite-btn:hover {
  transform: scale(1.1);
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

.rating-value {
  font-size: 13px;
  color: #52525b;
  font-weight: 500;
}

.card-time {
  display: flex;
  align-items: center;
  gap: 0.375rem;
  font-size: 13px;
  color: #71717a;
  margin: 0.5rem 0 0 0;
  line-height: 1.5;
}

.card-time svg {
  flex-shrink: 0;
}

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
