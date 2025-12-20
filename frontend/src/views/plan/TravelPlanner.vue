<template>
  <div class="planner-page">
    <!-- 로딩 상태 -->
    <div v-if="loading" class="loading-container">
      <div class="loading-spinner"></div>
      <p>로딩 중...</p>
    </div>

    <!-- 에러 상태 -->
    <div v-else-if="error" class="error-container">
      <p>{{ error }}</p>
      <button @click="fetchFolders" class="retry-button">다시 시도</button>
    </div>

    <!-- 메인 컨텐츠 -->
    <template v-else>
      <!-- 헤더 섹션 -->
      <div class="planner-header">
        <div class="header-content">
          <div class="title-section">
            <h1 class="page-title">나의 여행 일정</h1>
            <p class="plan-count">총 {{ folders.length }}개의 일정</p>
          </div>
          <button class="create-button" @click="createNewPlan">
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
        <!-- 일정이 없을 때 -->
        <div v-if="folders.length === 0" class="empty-state">
          <p>아직 생성된 일정이 없습니다.</p>
          <button @click="createNewPlan" class="create-first-button">첫 일정 만들기</button>
        </div>

        <!-- 일정 카드 -->
        <div
          v-for="(folder, index) in folders"
          :key="folder.id"
          class="plan-card"
          :class="{ 'has-delete': folder.showDelete }"
          @click="goToPlanDetail(folder.id)"
          style="cursor: pointer"
        >
          <!-- 썸네일 이미지 -->
          <div class="plan-thumbnail">
            <img
              :src="getThumbnailUrl(folder.thumbnailUrl)"
              :alt="folder.name"
              @error="handleImageError"
            />
          </div>

          <!-- 일정 정보 -->
          <div class="plan-info">
            <div class="plan-header-row">
              <h2 class="plan-title">{{ folder.name }}</h2>
              <button class="menu-button" @click.stop="toggleMenu(index)">
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
              <div v-if="folder.showDelete" class="delete-dropdown">
                <button class="delete-button" @click.stop="deleteFolder(folder.id, index)">
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

            <!-- 최근 수정일 -->
            <div class="last-modified">최근 수정: {{ formatDate(folder.updatedAt) }}</div>
          </div>
        </div>
      </div>
    </template>
  </div>

  <!-- 새 일정 만들기 모달 -->
  <CreatePlanModal :is-open="isModalOpen" @close="closeModal" @create="handleCreatePlan" />
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import CreatePlanModal from "@/components/plan/CreatePlanModal.vue";
import { folderApi } from "@/api/plan/folderApi";

const router = useRouter();
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

const isModalOpen = ref(false);
const folders = ref([]);
const loading = ref(true);
const error = ref(null);

// 컴포넌트 마운트 시 폴더 목록 조회
onMounted(() => {
  fetchFolders();
});

// 폴더 목록 조회
const fetchFolders = async () => {
  try {
    loading.value = true;
    error.value = null;

    const data = await folderApi.getMyFolders();
    folders.value = data.map((folder) => ({
      ...folder,
      showDelete: false,
    }));
  } catch (err) {
    console.error("폴더 목록 조회 실패:", err);
    error.value = "폴더 목록을 불러오는데 실패했습니다.";
  } finally {
    loading.value = false;
  }
};

// 썸네일 URL 생성
const getThumbnailUrl = (thumbnailUrl) => {
  if (!thumbnailUrl) {
    return "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400";
  }
  return `${API_BASE_URL}${thumbnailUrl}`;
};

// 이미지 로드 실패 시 기본 이미지
const handleImageError = (event) => {
  event.target.src = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400";
};

// 날짜 포맷팅
const formatDate = (dateString) => {
  if (!dateString) return "";

  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}.${month}.${day}`;
};

// 새 일정 만들기
const createNewPlan = () => {
  isModalOpen.value = true;
};

// 모달 닫기
const closeModal = () => {
  isModalOpen.value = false;
};

// 새 일정 생성
const handleCreatePlan = async (newPlanData) => {
  try {
    const createdFolder = await folderApi.createFolder(
      newPlanData.folderName,
      newPlanData.thumbnailFile
    );

    // 생성된 폴더를 목록 맨 앞에 추가
    folders.value.unshift({
      ...createdFolder,
      showDelete: false,
    });

    closeModal();

    // 선택적: 생성된 일정으로 바로 이동
    // router.push(`/plans/${createdFolder.id}`);
  } catch (err) {
    console.error("폴더 생성 실패:", err);
    alert("폴더 생성에 실패했습니다.");
  }
};

// 메뉴 토글
const toggleMenu = (index) => {
  // 다른 메뉴 닫기
  folders.value.forEach((folder, idx) => {
    if (idx !== index) {
      folder.showDelete = false;
    }
  });
  folders.value[index].showDelete = !folders.value[index].showDelete;
};

// 폴더 삭제
const deleteFolder = async (folderId, index) => {
  if (!confirm("정말 삭제하시겠습니까?")) {
    return;
  }

  try {
    await folderApi.deleteFolder(folderId);
    folders.value.splice(index, 1);
  } catch (err) {
    console.error("폴더 삭제 실패:", err);
    alert("폴더 삭제에 실패했습니다.");
  }
};

// 일정 상세 페이지로 이동
const goToPlanDetail = (planId) => {
  router.push(`/plans/${planId}`);
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
  cursor: pointer;
}

.plan-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transform: translateY(-2px);
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
  transition: transform 0.3s ease;
}

.plan-card:hover .plan-thumbnail img {
  transform: scale(1.05);
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
  white-space: nowrap;
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

/* 로딩 스타일 추가 */
.loading-container,
.error-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 400px;
  gap: 1rem;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f4f6;
  border-top-color: #1e3a8a;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

.retry-button {
  padding: 0.75rem 1.5rem;
  background: #1e3a8a;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

.retry-button:hover {
  background: #1e40af;
}

/* 빈 상태 스타일 */
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 400px;
  gap: 1.5rem;
}

.empty-state p {
  font-size: 16px;
  color: #6b7280;
}

.create-first-button {
  padding: 0.75rem 1.5rem;
  background: #1e3a8a;
  color: white;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  transition: background 0.2s ease;
}

.create-first-button:hover {
  background: #1e40af;
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
