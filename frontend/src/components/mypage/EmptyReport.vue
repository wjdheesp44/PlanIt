<template>
  <div class="empty-report">
    <div class="empty-icon">
      <svg width="80" height="80" viewBox="0 0 80 80" fill="none">
        <path
          d="M36.723 9.38C36.866 8.615 37.271 7.925 37.87 7.428C38.468 6.931 39.222 6.659 40 6.659C40.777 6.659 41.531 6.931 42.129 7.428C42.728 7.925 43.133 8.615 43.276 9.38L46.78 27.907C47.028 29.224 47.668 30.436 48.616 31.383C49.564 32.331 50.776 32.971 52.093 33.22L70.62 36.723C71.384 36.866 72.075 37.272 72.572 37.87C73.069 38.469 73.341 39.222 73.341 40C73.341 40.778 73.069 41.531 72.572 42.13C72.075 42.728 71.384 43.134 70.62 43.277L52.093 46.78C50.776 47.029 49.564 47.669 48.616 48.617C47.668 49.565 47.028 50.776 46.78 52.093L43.276 70.62C43.133 71.385 42.728 72.075 42.129 72.572C41.531 73.069 40.777 73.341 40 73.341C39.222 73.341 38.468 73.069 37.87 72.572C37.271 72.075 36.866 71.385 36.723 70.62L33.22 52.093C32.971 50.776 32.331 49.565 31.383 48.617C30.435 47.669 29.223 47.029 27.906 46.78L9.37901 43.277C8.61486 43.134 7.92424 42.728 7.42724 42.13C6.93024 41.531 6.65820 40.778 6.65820 40C6.65820 39.222 6.93024 38.469 7.42724 37.87C7.92424 37.272 8.61486 36.866 9.37901 36.723L27.906 33.22C29.223 32.971 30.435 32.331 31.383 31.383C32.331 30.436 32.971 29.224 33.22 27.907L36.723 9.38Z"
          stroke="#155DFC"
          stroke-width="6.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M66.666 6.66699V20.0003"
          stroke="#155DFC"
          stroke-width="6.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M73.333 13.333H60"
          stroke="#155DFC"
          stroke-width="6.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M13.333 73.333C17.015 73.333 20 70.348 20 66.666C20 62.984 17.015 60 13.333 60C9.651 60 6.666 62.984 6.666 66.666C6.666 70.348 9.651 73.333 13.333 73.333Z"
          stroke="#155DFC"
          stroke-width="6.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
    </div>

    <h2 class="empty-title">아직 분석 리포트가 없어요</h2>

    <div class="empty-description">
      <p>AI가 당신의 여행 취향을 분석해드립니다.</p>
      <p>버튼을 눌러 분석을 시작해보세요!</p>
    </div>

    <button class="start-analysis-btn" @click="handleStartAnalysis">
      <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
        <path
          d="M9.181 2.345c.036-.191.137-.364.287-.488.15-.124.338-.192.533-.192.194 0 .383.068.532.192.15.124.251.297.287.488l.876 4.632c.062.329.222.632.459.869.237.237.54.397.869.46l4.632.875c.191.036.364.137.488.287.124.15.192.338.192.532 0 .195-.068.383-.192.533-.124.15-.297.251-.488.287l-4.632.875c-.329.062-.632.222-.869.459-.237.237-.397.54-.46.869l-.875 4.632c-.036.191-.137.364-.287.488-.15.124-.338.192-.533.192-.194 0-.383-.068-.532-.192-.15-.124-.251-.297-.287-.488l-.876-4.632c-.062-.329-.222-.632-.459-.869-.237-.237-.54-.397-.869-.46l-4.632-.875c-.191-.036-.364-.137-.488-.287-.124-.15-.192-.338-.192-.532 0-.195.068-.383.192-.533.124-.15.297-.251.488-.287l4.632-.875c.329-.062.632-.222.869-.459.237-.237.397-.54.46-.869l.875-4.632z"
          stroke="currentColor"
          stroke-width="1.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M16.667 1.667v3.333M18.333 3.333H15"
          stroke="currentColor"
          stroke-width="1.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M3.334 18.333a1.667 1.667 0 100-3.333 1.667 1.667 0 000 3.333z"
          stroke="currentColor"
          stroke-width="1.667"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
      <span>AI 분석 시작하기</span>
    </button>

    <p class="empty-hint">분석에는 약 1-2분 정도 소요됩니다</p>
  </div>
</template>

<script setup>
import { defineEmits } from "vue";

const emit = defineEmits(["start-analysis"]);

const handleStartAnalysis = () => {
  emit("start-analysis");
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.empty-report {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 4rem 14rem;
  background: linear-gradient(90deg, #f5f3ff 0%, #eff6ff 100%);
  border: 1px solid #ddd6fe;
  border-radius: 24px;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  min-height: 500px;
}

.empty-icon {
  width: 80px;
  height: 80px;
  background: #ffffff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 6px -4px rgba(0, 0, 0, 0.1), 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

.empty-title {
  font-size: 24px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0 0 2rem 0;
  line-height: 1.33;
  text-align: center;
}

.empty-description {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 3rem;
  text-align: center;
}

.empty-description p {
  font-size: 16px;
  font-weight: 400;
  color: #4b5563;
  margin: 0;
  line-height: 1.5;
}

.start-analysis-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 1rem 2rem;
  background: linear-gradient(90deg, #2563eb 0%, #7c3aed 100%);
  color: #ffffff;
  border: none;
  border-radius: 9999px;
  font-size: 18px;
  font-weight: 400;
  cursor: pointer;
  transition: all 0.2s ease;
  line-height: 1.56;
  margin-bottom: 1.5rem;
}

.start-analysis-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 16px rgba(37, 99, 235, 0.3);
}

.start-analysis-btn:active {
  transform: translateY(0);
}

.start-analysis-btn svg {
  flex-shrink: 0;
}

.empty-hint {
  font-size: 14px;
  font-weight: 400;
  color: #6b7280;
  margin: 0;
  line-height: 1.43;
  text-align: center;
}

/* 반응형 */
@media (max-width: 1024px) {
  .empty-report {
    padding: 3rem 8rem;
  }
}

@media (max-width: 768px) {
  .empty-report {
    padding: 3rem 2rem;
    min-height: 400px;
  }

  .empty-icon {
    width: 64px;
    height: 64px;
    margin-bottom: 1.5rem;
  }

  .empty-icon svg {
    width: 64px;
    height: 64px;
  }

  .empty-title {
    font-size: 20px;
    margin-bottom: 1.5rem;
  }

  .empty-description {
    margin-bottom: 2rem;
  }

  .empty-description p {
    font-size: 15px;
  }

  .start-analysis-btn {
    padding: 0.875rem 1.75rem;
    font-size: 16px;
  }

  .empty-hint {
    font-size: 13px;
  }
}

@media (max-width: 480px) {
  .empty-report {
    padding: 2rem 1rem;
    min-height: 360px;
  }

  .empty-icon {
    width: 56px;
    height: 56px;
    margin-bottom: 1rem;
  }

  .empty-icon svg {
    width: 56px;
    height: 56px;
  }

  .empty-title {
    font-size: 18px;
    margin-bottom: 1rem;
  }

  .empty-description {
    margin-bottom: 1.5rem;
  }

  .empty-description p {
    font-size: 14px;
  }

  .start-analysis-btn {
    padding: 0.75rem 1.5rem;
    font-size: 15px;
  }

  .empty-hint {
    font-size: 12px;
  }
}
</style>
