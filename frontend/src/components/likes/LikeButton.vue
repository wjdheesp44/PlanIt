<template>
  <button class="favorite-btn" @click.stop="toggleLike">
    <!-- ❤️ 좋아요 상태 -->
    <svg
      v-if="liked"
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

    <!-- 🤍 좋아요 안 한 상태 -->
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
</template>

<script setup>
import { ref } from "vue";
import api from "@/api/user/api";

const props = defineProps({
  spotId: {
    type: Number,
    required: true,
  },
  initialLiked: {
    type: Boolean,
    required: true,
  },
});

const liked = ref(props.initialLiked);
let isProcessing = false;

const toggleLike = async () => {
  if (isProcessing) return; // 연타 방지
  isProcessing = true;

  try {
    const res = await api.post(`/api/v1/spots/${props.spotId}/likes`);
    liked.value = res.data;
  } catch (e) {
    alert("로그인 후 다시 시도해주세요");
  } finally {
    isProcessing = false;
  }
};
</script>

<style scoped>
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
</style>
