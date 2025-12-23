<template>
  <transition name="modal">
    <div v-if="show" class="modal-overlay" @click.self="close">
      <div class="modal-content share-modal">
        <!-- 헤더 -->
        <div class="share-section">
          <div class="section-icon-header">
            <div class="icon-wrapper">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path
                  d="M14.1667 17.5V15.8333C14.1667 14.9493 13.8155 14.1014 13.1904 13.4763C12.5652 12.8512 11.7174 12.5 10.8333 12.5H4.16667C3.28261 12.5 2.43476 12.8512 1.80964 13.4763C1.18452 14.1014 0.833328 14.9493 0.833328 15.8333V17.5"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M7.50008 9.16667C9.34103 9.16667 10.8334 7.67428 10.8334 5.83333C10.8334 3.99238 9.34103 2.5 7.50008 2.5C5.65913 2.5 4.16675 3.99238 4.16675 5.83333C4.16675 7.67428 5.65913 9.16667 7.50008 9.16667Z"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M19.1667 17.5V15.8333C19.1662 15.0948 18.9205 14.3773 18.4681 13.7936C18.0157 13.2099 17.3821 12.793 16.6667 12.6083"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M13.3333 2.60834C14.0503 2.79192 14.6858 3.20892 15.1396 3.79359C15.5935 4.37827 15.8398 5.09736 15.8398 5.8375C15.8398 6.57765 15.5935 7.29674 15.1396 7.88141C14.6858 8.46609 14.0503 8.88309 13.3333 9.06667"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="flex-between">
              <div>
                <h4 class="section-title">팀원</h4>
              </div>
              <span class="badge-count">{{ users.length }}명</span>
            </div>
          </div>

          <div class="users-list">
            <div v-for="user in users" :key="user.id" class="user-card">
              <div class="user-main">
                <div class="user-avatar">
                  <img
                    :src="`https://api.dicebear.com/7.x/avataaars/svg?seed=${user.email}`"
                    :alt="user.nickname"
                  />
                </div>
                <div class="user-name-row">
                  <div class="user-name">{{ user.nickname }}</div>
                  <div class="role-badge" :class="`badge-${user.role.toLowerCase()}`">
                    <span class="badge-dot"></span>
                    {{ getRoleLabel(user.role) }}
                  </div>
                </div>
                <div class="user-email">{{ user.email }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, watch } from "vue";
import { groupShareApi } from "@/api/plan/groupShareApi";

const props = defineProps({
  show: Boolean,
  groupId: Number,
  currentUserRole: {
    type: String,
    default: "VIEWER",
  },
});

const emit = defineEmits(["close", "success", "error"]);

const users = ref([]);
const shareLink = ref(null);
const copied = ref(false);
const newUserRole = ref("EDITOR");
const linkInput = ref(null);

// 닉네임 검색 관련
const searchNickname = ref("");
const searchedUsers = ref([]);
const selectedUser = ref(null);
const showSearchResults = ref(false);
const isSearching = ref(false);
let searchTimeout = null;

const getRoleLabel = (role) => {
  const labels = {
    OWNER: "소유자",
    EDITOR: "편집자",
    VIEWER: "뷰어",
  };
  return labels[role] || role;
};

const loadData = async () => {
  if (!props.show || !props.groupId) return;
  try {
    users.value = await groupShareApi.getGroupUsers(props.groupId);
    shareLink.value = null;
  } catch (error) {
    console.error("데이터 로드 실패:", error);
    emit("error", "데이터를 불러오는데 실패했습니다");
  }
};

// 선택 해제
const clearSelection = () => {
  selectedUser.value = null;
  searchNickname.value = "";
  searchedUsers.value = [];
  showSearchResults.value = false;
};

const close = () => {
  clearSelection();
  emit("close");
};

watch(
  () => props.show,
  (newValue) => {
    if (newValue) {
      loadData();
    } else {
      clearSelection();
    }
  }
);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap");

* {
  font-family: "Inter", -apple-system, BlinkMacSystemFont, sans-serif;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(4px);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000;
  padding: 1rem;
}

.modal-content {
  position: relative;
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.share-modal {
  max-width: 640px;
  width: 90%;
}

/* 헤더 */
.modal-header {
  padding: 2rem;
  border-bottom: 1px solid #f1f3f5;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.header-content {
  display: flex;
  gap: 1rem;
  flex: 1;
}

.header-icon {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.header-text h3 {
  font-size: 20px;
  font-weight: 700;
  color: #212529;
  margin-bottom: 0.25rem;
}

.header-text p {
  font-size: 14px;
  color: #868e96;
  font-weight: 400;
}

.modal-close {
  width: 32px;
  height: 32px;
  background: transparent;
  border: none;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #868e96;
  transition: all 0.2s;
  flex-shrink: 0;
}

.modal-close:hover {
  background: #f1f3f5;
  color: #212529;
}

/* 바디 */
.modal-body {
  padding: 0;
  max-height: 70vh;
  overflow-y: auto;
}

.share-section {
  padding: 1.5rem 2rem;
  border-bottom: 1px solid #f1f3f5;
}

.share-section:last-child {
  border-bottom: none;
}

.section-icon-header {
  display: flex;
  gap: 0.875rem;
  margin-bottom: 1.25rem;
  align-items: flex-start;
}

.icon-wrapper {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #667eea15 0%, #764ba215 100%);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #667eea;
  flex-shrink: 0;
}

.icon-wrapper.add {
  background: linear-gradient(135deg, #4ade8015 0%, #22c55e15 100%);
  color: #22c55e;
}

.section-title {
  margin-top: 0.5rem;
  font-size: 15px;
  font-weight: 600;
  color: #212529;
  margin-bottom: 0.25rem;
}

.section-subtitle {
  font-size: 13px;
  color: #868e96;
  font-weight: 400;
}

.flex-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex: 1;
}

.badge-count {
  background: #f1f3f5;
  color: #495057;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 600;
}

/* 링크 섹션 */
.link-container {
  display: flex;
  flex-direction: column;
  gap: 0.875rem;
}

.link-display {
  display: flex;
  gap: 0.625rem;
  padding: 0.875rem;
  background: #f8f9fa;
  border-radius: 12px;
  border: 1px solid #e9ecef;
}

.link-input {
  flex: 1;
  border: none;
  background: transparent;
  font-size: 14px;
  color: #495057;
  font-family: "SF Mono", "Monaco", "Menlo", monospace;
  outline: none;
}

.copy-btn {
  padding: 0.5rem 1rem;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: #495057;
  transition: all 0.2s;
  white-space: nowrap;
}

.copy-btn:hover {
  background: #f8f9fa;
  border-color: #adb5bd;
}

.copy-btn.copied {
  background: #d3f9d8;
  border-color: #8ce99a;
  color: #2b8a3e;
}

/* 버튼 */
.primary-button {
  width: 100%;
  padding: 0.875rem 1.25rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  transition: all 0.2s;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
}

.primary-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.4);
}

.primary-button:active {
  transform: translateY(0);
}

.primary-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.primary-button:disabled:hover {
  transform: none;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.3);
}

.text-button {
  padding: 0.625rem;
  background: transparent;
  border: none;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  transition: all 0.2s;
}

.text-button.danger {
  color: #fa5252;
}

.text-button.danger:hover {
  background: #fff5f5;
}

/* 유저 목록 */
.users-list {
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
}

.user-card {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  border-radius: 12px;
  transition: all 0.2s;
}

.user-card:hover {
  background: #f1f3f5;
  border-color: #dee2e6;
}

.user-main {
  display: flex;
  align-items: center;
  gap: 0.875rem;
  flex: 1;
  min-width: 0;
}

.user-avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
  border: 2px solid white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.user-info {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 0.375rem;
}

.user-name-row {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #212529;
}

.user-email {
  font-size: 13px;
  color: #868e96;
}

/* Role Badge 스타일 */
.role-badge {
  display: inline-flex;
  align-items: center;
  gap: 0.25rem;
  padding: 0.25rem 0.625rem;
  border-radius: 6px;
  font-size: 11px;
  font-weight: 600;
  white-space: nowrap;
  flex-shrink: 0;
}

.badge-dot {
  width: 5px;
  height: 5px;
  border-radius: 50%;
  flex-shrink: 0;
}

.badge-owner {
  background: #fff4e6;
  color: #e8590c;
}

.badge-owner .badge-dot {
  background: #fd7e14;
}

.badge-editor {
  background: #e7f5ff;
  color: #1971c2;
}

.badge-editor .badge-dot {
  background: #4dabf7;
}

.badge-viewer {
  background: #f3f4f6;
  color: #495057;
}

.badge-viewer .badge-dot {
  background: #868e96;
}

.icon-button.remove {
  width: 32px;
  height: 32px;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #868e96;
  transition: all 0.2s;
}

.icon-button.remove:hover {
  background: #fff5f5;
  border-color: #ffc9c9;
  color: #fa5252;
}

/* 사용자 추가 폼 */
.add-user-form {
  display: flex;
  flex-direction: column;
  gap: 0.875rem;
}

.form-row {
  display: flex;
  gap: 0.625rem;
}

.modern-input {
  flex: 1;
  padding: 0.875rem 1rem;
  border: 1px solid #dee2e6;
  border-radius: 10px;
  font-size: 14px;
  outline: none;
  transition: all 0.2s;
  background: white;
}

.modern-input:hover {
  border-color: #adb5bd;
}

.modern-input:focus {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.modern-input::placeholder {
  color: #adb5bd;
}

/* 검색 입력 관련 */
.search-input-wrapper {
  position: relative;
}

.search-input {
  width: 100%;
}

/* 검색 결과 드롭다운 */
.search-results {
  position: absolute;
  top: calc(100% + 0.5rem);
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-height: 250px;
  overflow-y: auto;
  z-index: 100;
}

/* 검색 로딩 */
.search-loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  gap: 0.75rem;
  color: #868e96;
}

.spinner {
  width: 24px;
  height: 24px;
  border: 2px solid #f1f3f5;
  border-top-color: #667eea;
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

/* 검색 결과 없음 */
.search-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  gap: 0.75rem;
  color: #adb5bd;
}

.search-empty p {
  font-size: 13px;
}

/* 검색 결과 리스트 */
.search-results-list {
  padding: 0.5rem;
}

.search-result-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.75rem;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
}

.search-result-item:hover {
  background: #f8f9fa;
}

.result-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
  border: 2px solid white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.result-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.result-info {
  flex: 1;
  min-width: 0;
}

.result-name {
  font-size: 14px;
  font-weight: 600;
  color: #212529;
  margin-bottom: 0.125rem;
}

.result-email {
  font-size: 12px;
  color: #868e96;
}

.search-result-item svg {
  color: #667eea;
  flex-shrink: 0;
}

/* 선택된 유저 표시 */
.selected-user {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.875rem;
  background: linear-gradient(135deg, #667eea15 0%, #764ba215 100%);
  border: 1px solid #667eea30;
  border-radius: 10px;
}

.selected-user-info {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  flex: 1;
  min-width: 0;
}

.selected-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
  border: 2px solid white;
  box-shadow: 0 2px 6px rgba(102, 126, 234, 0.2);
}

.selected-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.selected-name {
  font-size: 14px;
  font-weight: 600;
  color: #212529;
  margin-bottom: 0.125rem;
}

.selected-email {
  font-size: 12px;
  color: #667eea;
}

.clear-selection {
  width: 28px;
  height: 28px;
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #868e96;
  transition: all 0.2s;
  flex-shrink: 0;
}

.clear-selection:hover {
  background: #f8f9fa;
  border-color: #adb5bd;
  color: #495057;
}

/* 스크롤바 */
.modal-body::-webkit-scrollbar {
  width: 8px;
}

.modal-body::-webkit-scrollbar-track {
  background: #f8f9fa;
}

.modal-body::-webkit-scrollbar-thumb {
  background: #dee2e6;
  border-radius: 4px;
}

.modal-body::-webkit-scrollbar-thumb:hover {
  background: #adb5bd;
}

/* 애니메이션 */
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-active .modal-content,
.modal-leave-active .modal-content {
  transition: transform 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-content,
.modal-leave-to .modal-content {
  transform: scale(0.95) translateY(-20px);
}
</style>
