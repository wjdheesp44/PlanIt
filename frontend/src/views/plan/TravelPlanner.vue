<template>
  <div class="planner-page">
    <!-- 헤더 섹션 -->
    <div class="planner-header">
      <div class="header-content">
        <div class="title-section">
          <h1 class="page-title">나의 여행 일정</h1>
          <p class="plan-count">총 {{ plans.length }}개의 일정</p>
        </div>
        <button class="create-button">
          <svg
            width="20"
            height="20"
            viewBox="0 0 20 20"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M4.1665 10H15.8332"
              stroke="white"
              stroke-width="1.66667"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M10 4.1665V15.8332"
              stroke="white"
              stroke-width="1.66667"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
          <span>새 일정 만들기</span>
        </button>
      </div>
    </div>

    <!-- 일정 목록 -->
    <div class="plans-container">
      <div
        v-for="(plan, index) in plans"
        :key="index"
        class="plan-card"
        :class="{ 'has-delete': plan.showDelete }"
      >
        <!-- 썸네일 이미지 -->
        <div class="plan-thumbnail">
          <img :src="plan.thumbnail" :alt="plan.title" />
        </div>

        <!-- 일정 정보 -->
        <div class="plan-info">
          <div class="plan-header-row">
            <h2 class="plan-title">{{ plan.title }}</h2>
            <button class="menu-button" @click="toggleMenu(index)">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M9.99984 10.8332C10.4601 10.8332 10.8332 10.4601 10.8332 9.99984C10.8332 9.5396 10.4601 9.1665 9.99984 9.1665C9.5396 9.1665 9.1665 9.5396 9.1665 9.99984C9.1665 10.4601 9.5396 10.8332 9.99984 10.8332Z"
                  stroke="#6A7282"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M9.99984 5.00016C10.4601 5.00016 10.8332 4.62707 10.8332 4.16683C10.8332 3.70659 10.4601 3.3335 9.99984 3.3335C9.5396 3.3335 9.1665 3.70659 9.1665 4.16683C9.1665 4.62707 9.5396 5.00016 9.99984 5.00016Z"
                  stroke="#6A7282"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M9.99984 16.6667C10.4601 16.6667 10.8332 16.2936 10.8332 15.8333C10.8332 15.3731 10.4601 15 9.99984 15C9.5396 15 9.1665 15.3731 9.1665 15.8333C9.1665 16.2936 9.5396 16.6667 9.99984 16.6667Z"
                  stroke="#6A7282"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </button>

            <!-- 삭제 버튼 (메뉴 클릭시 표시) -->
            <div v-if="plan.showDelete" class="delete-dropdown">
              <button class="delete-button" @click="deletePlan(index)">
                <svg
                  width="16"
                  height="16"
                  viewBox="0 0 16 16"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M6.6665 7.3335V11.3335"
                    stroke="#E7000B"
                    stroke-width="1.33333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M9.3335 7.3335V11.3335"
                    stroke="#E7000B"
                    stroke-width="1.33333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M12.6668 4V13.3333C12.6668 13.687 12.5264 14.0261 12.2763 14.2761C12.0263 14.5262 11.6871 14.6667 11.3335 14.6667H4.66683C4.31321 14.6667 3.97407 14.5262 3.72402 14.2761C3.47397 14.0261 3.3335 13.687 3.3335 13.3333V4"
                    stroke="#E7000B"
                    stroke-width="1.33333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M2 4H14"
                    stroke="#E7000B"
                    stroke-width="1.33333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M5.3335 4.00016V2.66683C5.3335 2.31321 5.47397 1.97407 5.72402 1.72402C5.97407 1.47397 6.31321 1.3335 6.66683 1.3335H9.3335C9.68712 1.3335 10.0263 1.47397 10.2763 1.72402C10.5264 1.97407 10.6668 2.31321 10.6668 2.66683V4.00016"
                    stroke="#E7000B"
                    stroke-width="1.33333"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
                <span>삭제</span>
              </button>
            </div>
          </div>

          <!-- 스팟 개수 -->
          <div class="spot-info">
            <svg
              width="16"
              height="16"
              viewBox="0 0 16 16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M13.3332 6.66683C13.3332 9.9955 9.6405 13.4622 8.4005 14.5328C8.28499 14.6197 8.14437 14.6667 7.99984 14.6667C7.85531 14.6667 7.71469 14.6197 7.59917 14.5328C6.35917 13.4622 2.6665 9.9955 2.6665 6.66683C2.6665 5.25234 3.22841 3.89579 4.2286 2.89559C5.2288 1.8954 6.58535 1.3335 7.99984 1.3335C9.41433 1.3335 10.7709 1.8954 11.7711 2.89559C12.7713 3.89579 13.3332 5.25234 13.3332 6.66683Z"
                stroke="#4A5565"
                stroke-width="1.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M8 8.6665C9.10457 8.6665 10 7.77107 10 6.6665C10 5.56193 9.10457 4.6665 8 4.6665C6.89543 4.6665 6 5.56193 6 6.6665C6 7.77107 6.89543 8.6665 8 8.6665Z"
                stroke="#4A5565"
                stroke-width="1.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
            <span>스팟 {{ plan.spotCount }}개</span>
          </div>

          <!-- 공유 정보 -->
          <div class="share-info">
            <svg
              width="16"
              height="16"
              viewBox="0 0 16 16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M10.6668 14V12.6667C10.6668 11.9594 10.3859 11.2811 9.88578 10.781C9.38568 10.281 8.70741 10 8.00016 10H4.00016C3.29292 10 2.61464 10.281 2.11454 10.781C1.61445 11.2811 1.3335 11.9594 1.3335 12.6667V14"
                stroke="#6A7282"
                stroke-width="1.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M10.6665 2.08545C11.2383 2.2337 11.7448 2.56763 12.1063 3.03482C12.4678 3.50202 12.664 4.07604 12.664 4.66678C12.664 5.25752 12.4678 5.83154 12.1063 6.29874C11.7448 6.76594 11.2383 7.09987 10.6665 7.24812"
                stroke="#6A7282"
                stroke-width="1.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M14.6665 13.9998V12.6664C14.6661 12.0756 14.4694 11.5016 14.1074 11.0346C13.7454 10.5677 13.2386 10.2341 12.6665 10.0864"
                stroke="#6A7282"
                stroke-width="1.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M6.00016 7.33333C7.47292 7.33333 8.66683 6.13943 8.66683 4.66667C8.66683 3.19391 7.47292 2 6.00016 2C4.5274 2 3.3335 3.19391 3.3335 4.66667C3.3335 6.13943 4.5274 7.33333 6.00016 7.33333Z"
                stroke="#6A7282"
                stroke-width="1.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>

            <div class="user-avatars">
              <div
                v-for="(user, idx) in plan.sharedUsers"
                :key="idx"
                class="avatar"
                :style="{ left: idx * 20 + 'px' }"
              >
                {{ user }}
              </div>
            </div>

            <span class="share-count">{{ plan.sharedCount }}명과 공유</span>
          </div>

          <!-- 최근 수정일 -->
          <div class="last-modified">최근 수정: {{ plan.lastModified }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const plans = ref([
  {
    thumbnail: "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=400",
    title: "제주도 여행",
    spotCount: 12,
    sharedUsers: ["김", "이", "박"],
    sharedCount: 3,
    lastModified: "2025.12.02",
    showDelete: false,
  },
  {
    thumbnail: "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400",
    title: "제주도 여행",
    spotCount: 12,
    sharedUsers: ["김", "이", "박"],
    sharedCount: 3,
    lastModified: "2025.12.02",
    showDelete: false,
  },
  {
    thumbnail: "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?w=400",
    title: "제주도 여행",
    spotCount: 12,
    sharedUsers: ["김", "이", "박"],
    sharedCount: 3,
    lastModified: "2025.12.02",
    showDelete: false,
  },
  {
    thumbnail: "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400",
    title: "제주도 여행",
    spotCount: 12,
    sharedUsers: ["김", "이", "박"],
    sharedCount: 3,
    lastModified: "2025.12.02",
    showDelete: false,
  },
]);

const toggleMenu = (index) => {
  plans.value[index].showDelete = !plans.value[index].showDelete;
};

const deletePlan = (index) => {
  plans.value.splice(index, 1);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.planner-page {
  min-height: 100vh;
  background: #ffffff;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  padding: 2rem 0;
}

/* 헤더 섹션 */
.planner-header {
  max-width: 1103px;
  margin: 0 auto 3rem;
  padding: 0 2rem;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title-section {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.page-title {
  font-size: 24px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.plan-count {
  font-size: 16px;
  color: #4a5568;
  margin: 0;
  line-height: 1.5;
}

.create-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  background: #1e3a8a;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  font-weight: 400;
  cursor: pointer;
  transition: background 0.2s ease;
}

.create-button:hover {
  background: #1e40af;
}

.create-button svg {
  flex-shrink: 0;
}

/* 일정 목록 */
.plans-container {
  max-width: 1103px;
  margin: 0 auto;
  padding: 0 2rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 일정 카드 */
.plan-card {
  background: #ffffff;
  border: 0.8px solid #e5e7eb;
  border-radius: 16px;
  display: flex;
  overflow: hidden;
  transition: all 0.2s ease;
  position: relative;
}

.plan-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.plan-card.has-delete {
  padding-right: 160px;
}

/* 썸네일 */
.plan-thumbnail {
  width: 321px;
  height: 216px;
  flex-shrink: 0;
  overflow: hidden;
  background: #f5f5f5;
}

.plan-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 일정 정보 */
.plan-info {
  flex: 1;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  position: relative;
}

.plan-header-row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 1rem;
  position: relative;
}

.plan-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
  flex: 1;
}

.menu-button {
  background: none;
  border: none;
  padding: 0.25rem;
  cursor: pointer;
  border-radius: 4px;
  transition: background 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.menu-button:hover {
  background: #f3f4f6;
}

/* 삭제 드롭다운 */
.delete-dropdown {
  position: absolute;
  right: 0;
  top: 2.5rem;
  background: #ffffff;
  border: 0.8px solid #e5e7eb;
  border-radius: 10px;
  box-shadow: 0 4px 6px -4px rgba(0, 0, 0, 0.1), 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  z-index: 10;
}

.delete-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  background: none;
  border: none;
  color: #e7000b;
  font-size: 16px;
  cursor: pointer;
  width: 100%;
  transition: background 0.2s ease;
}

.delete-button:hover {
  background: #fef2f2;
}

/* 스팟 정보 */
.spot-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 14px;
  color: #4a5568;
}

/* 공유 정보 */
.share-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.user-avatars {
  display: flex;
  position: relative;
  height: 28px;
  margin-right: 0.5rem;
}

.avatar {
  position: absolute;
  width: 28px;
  height: 28px;
  background: linear-gradient(135deg, #60a5fa, #a78bfa);
  border: 1.6px solid #ffffff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  font-size: 12px;
  font-weight: 400;
}

.share-count {
  font-size: 14px;
  color: #4a5568;
}

/* 최근 수정일 */
.last-modified {
  font-size: 14px;
  color: #9ca3af;
  margin-top: auto;
}

/* 반응형 - 태블릿 */
@media (max-width: 1024px) {
  .planner-header,
  .plans-container {
    max-width: 100%;
    padding: 0 1.5rem;
  }

  .plan-card {
    flex-direction: column;
  }

  .plan-card.has-delete {
    padding-right: 0;
  }

  .plan-thumbnail {
    width: 100%;
    height: 200px;
  }

  .delete-dropdown {
    right: 1.5rem;
  }
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .planner-page {
    padding: 1.5rem 0;
  }

  .planner-header,
  .plans-container {
    padding: 0 1rem;
  }

  .header-content {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
  }

  .page-title {
    font-size: 20px;
  }

  .plan-count {
    font-size: 14px;
  }

  .create-button {
    width: 100%;
    justify-content: center;
  }

  .plans-container {
    gap: 1rem;
  }

  .plan-card {
    border-radius: 12px;
  }

  .plan-thumbnail {
    height: 160px;
  }

  .plan-info {
    padding: 1rem;
  }

  .plan-title {
    font-size: 15px;
  }

  .spot-info,
  .share-count,
  .last-modified {
    font-size: 13px;
  }
}

@media (max-width: 480px) {
  .planner-header,
  .plans-container {
    padding: 0 0.75rem;
  }

  .page-title {
    font-size: 18px;
  }

  .create-button {
    font-size: 14px;
    padding: 0.625rem 0.875rem;
  }

  .plan-info {
    gap: 0.75rem;
  }
}
</style>
