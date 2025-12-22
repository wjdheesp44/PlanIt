<!-- src/components/plan/ShareModal.vue -->
<template>
  <transition name="modal">
    <div v-if="show" class="modal-overlay" @click="close">
      <div class="modal-content share-modal" @click.stop>
        <!-- 헤더 -->
        <div class="modal-header">
          <div class="header-content">
            <div class="header-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path
                  d="M18 8C19.6569 8 21 6.65685 21 5C21 3.34315 19.6569 2 18 2C16.3431 2 15 3.34315 15 5C15 6.65685 16.3431 8 18 8Z"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M6 15C7.65685 15 9 13.6569 9 12C9 10.3431 7.65685 9 6 9C4.34315 9 3 10.3431 3 12C3 13.6569 4.34315 15 6 15Z"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M18 22C19.6569 22 21 20.6569 21 19C21 17.3431 19.6569 16 18 16C16.3431 16 15 17.3431 15 19C15 20.6569 16.3431 22 18 22Z"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M8.58984 13.51L15.4198 17.49"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M15.4098 6.51001L8.58984 10.49"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="header-text">
              <h3>여행 플랜 공유</h3>
              <p>링크로 공유하거나 팀원을 직접 초대하세요</p>
            </div>
          </div>
          <button class="modal-close" @click="close">
            <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
              <path
                d="M15 5L5 15M5 5L15 15"
                stroke="currentColor"
                stroke-width="1.5"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
        </div>

        <!-- 바디 -->
        <div class="modal-body">
          <!-- 링크 공유 섹션 -->
          <div class="share-section link-section">
            <div class="section-icon-header">
              <div class="icon-wrapper">
                <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                  <path
                    d="M8.33333 10.8333C8.69113 11.3118 9.14771 11.7077 9.67213 11.9943C10.1965 12.281 10.7764 12.4518 11.3711 12.4958C11.9658 12.5398 12.5631 12.4561 13.1231 12.25C13.683 12.0438 14.1929 11.7202 14.6167 11.3L16.95 8.96666C17.7003 8.19092 18.1184 7.14798 18.1101 6.06499C18.1017 4.98199 17.6675 3.94561 16.9053 3.18102C16.1431 2.41643 15.1081 1.97886 14.0251 1.96697C12.9421 1.95509 11.8983 2.36975 11.1208 3.11666L9.79167 4.43333"
                    stroke="currentColor"
                    stroke-width="1.66667"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M11.6667 9.16667C11.3089 8.68824 10.8523 8.29228 10.3279 8.00565C9.80344 7.71903 9.22359 7.54816 8.62888 7.50418C8.03416 7.4602 7.43687 7.54387 6.87693 7.75003C6.31699 7.95619 5.80709 8.27984 5.38333 8.7L3.05 11.0333C2.29974 11.8091 1.88156 12.852 1.88991 13.935C1.89826 15.018 2.33248 16.0544 3.09469 16.819C3.85691 17.5836 4.89187 18.0211 5.97486 18.033C7.05786 18.0449 8.10167 17.6303 8.87917 16.8833L10.2 15.5667"
                    stroke="currentColor"
                    stroke-width="1.66667"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
              </div>
              <div>
                <h4 class="section-title">링크로 초대</h4>
                <p class="section-subtitle">링크를 가진 사람은 누구나 이 플랜을 볼 수 있어요</p>
              </div>
            </div>

            <div v-if="shareLink" class="link-container">
              <div class="link-display">
                <input
                  type="text"
                  :value="shareLink.shareUrl"
                  readonly
                  class="link-input"
                  ref="linkInput"
                  @click="selectLinkText"
                />
                <button class="copy-btn" @click="copyLink" :class="{ copied: copied }">
                  <svg v-if="!copied" width="18" height="18" viewBox="0 0 18 18" fill="none">
                    <path
                      d="M12 9.75V12.525C12 13.125 12 13.425 11.886 13.653C11.7853 13.8514 11.6264 14.0102 11.428 14.111C11.2 14.225 10.9 14.225 10.3 14.225H5.475C4.875 14.225 4.575 14.225 4.347 14.111C4.14863 14.0102 3.98979 13.8514 3.889 13.653C3.775 13.425 3.775 13.125 3.775 12.525V7.7C3.775 7.1 3.775 6.8 3.889 6.572C3.98979 6.37363 4.14863 6.21479 4.347 6.114C4.575 6 4.875 6 5.475 6H8.25"
                      stroke="currentColor"
                      stroke-width="1.5"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                    <path
                      d="M7.65 10.35H12.525C13.125 10.35 13.425 10.35 13.653 10.236C13.8514 10.1352 14.0102 9.97637 14.111 9.778C14.225 9.55 14.225 9.25 14.225 8.65V3.825C14.225 3.225 14.225 2.925 14.111 2.697C14.0102 2.49863 13.8514 2.33979 13.653 2.239C13.425 2.125 13.125 2.125 12.525 2.125H7.7C7.1 2.125 6.8 2.125 6.572 2.239C6.37363 2.33979 6.21479 2.49863 6.114 2.697C6 2.925 6 3.225 6 3.825V8.7"
                      stroke="currentColor"
                      stroke-width="1.5"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                  <svg v-else width="18" height="18" viewBox="0 0 18 18" fill="none">
                    <path
                      d="M15 4.5L6.75 12.75L3 9"
                      stroke="currentColor"
                      stroke-width="1.8"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                  <span>{{ copied ? "복사됨!" : "복사" }}</span>
                </button>
              </div>
              <button class="text-button danger" @click="deactivateLink">
                <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                  <path
                    d="M10 6L6 10M6 6L10 10"
                    stroke="currentColor"
                    stroke-width="1.5"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M8 14C11.3137 14 14 11.3137 14 8C14 4.68629 11.3137 2 8 2C4.68629 2 2 4.68629 2 8C2 11.3137 4.68629 14 8 14Z"
                    stroke="currentColor"
                    stroke-width="1.5"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
                링크 비활성화
              </button>
            </div>

            <button v-else class="primary-button" @click="createLink">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path
                  d="M10 6.66667V13.3333"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M6.66699 10H13.3337"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
              공유 링크 생성
            </button>
          </div>

          <!-- 참여자 목록 섹션 -->
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
                  <p class="section-subtitle">이 플랜에 접근할 수 있는 사람들</p>
                </div>
                <span class="badge-count">{{ users.length }}</span>
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
                  <div class="user-info">
                    <div class="user-name">{{ user.nickname }}</div>
                    <div class="user-email">{{ user.email }}</div>
                  </div>
                </div>
                <div class="user-actions">
                  <select
                    v-if="user.role !== 'OWNER' && currentUserRole === 'OWNER'"
                    class="role-select modern"
                    :value="user.role"
                    @change="changeRole(user.id, $event.target.value)"
                  >
                    <option value="EDITOR">편집 가능</option>
                    <option value="VIEWER">보기 전용</option>
                  </select>
                  <div v-else class="role-badge" :class="`badge-${user.role.toLowerCase()}`">
                    <span class="badge-dot"></span>
                    {{ getRoleLabel(user.role) }}
                  </div>
                  <button
                    v-if="user.role !== 'OWNER' && currentUserRole === 'OWNER'"
                    class="icon-button remove"
                    @click="removeUser(user.id)"
                    title="제거"
                  >
                    <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                      <path
                        d="M10 6L6 10M6 6L10 10"
                        stroke="currentColor"
                        stroke-width="1.5"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- 사용자 추가 섹션 -->
          <div v-if="currentUserRole === 'OWNER'" class="share-section add-section">
            <div class="section-icon-header">
              <div class="icon-wrapper add">
                <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                  <path
                    d="M10 6.66667V13.3333"
                    stroke="currentColor"
                    stroke-width="1.66667"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M6.66699 10H13.3337"
                    stroke="currentColor"
                    stroke-width="1.66667"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
              </div>
              <div>
                <h4 class="section-title">새 팀원 초대</h4>
                <p class="section-subtitle">사용자 ID로 직접 초대할 수 있어요</p>
              </div>
            </div>

            <div class="add-user-form">
              <div class="form-row">
                <input
                  type="number"
                  v-model="newUserId"
                  placeholder="사용자 ID (예: 12345)"
                  class="modern-input"
                  @keyup.enter="addUser"
                />
                <select v-model="newUserRole" class="role-select modern">
                  <option value="EDITOR">편집 가능</option>
                  <option value="VIEWER">보기 전용</option>
                </select>
              </div>
              <button class="primary-button" @click="addUser">
                <svg width="18" height="18" viewBox="0 0 18 18" fill="none">
                  <path
                    d="M9 3.75V14.25"
                    stroke="currentColor"
                    stroke-width="1.5"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M3.75 9H14.25"
                    stroke="currentColor"
                    stroke-width="1.5"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
                초대하기
              </button>
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
const newUserId = ref("");
const newUserRole = ref("EDITOR");
const linkInput = ref(null);

const getRoleLabel = (role) => {
  const labels = {
    OWNER: "소유자",
    EDITOR: "편집 가능",
    VIEWER: "보기 전용",
  };
  return labels[role] || role;
};

const selectLinkText = () => {
  linkInput.value?.select();
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

const createLink = async () => {
  try {
    shareLink.value = await groupShareApi.createShareLink(props.groupId);
    emit("success", "공유 링크가 생성되었습니다");
  } catch (error) {
    console.error("링크 생성 실패:", error);
    emit("error", "링크 생성에 실패했습니다");
  }
};

const copyLink = async () => {
  try {
    await navigator.clipboard.writeText(shareLink.value.shareUrl);
    copied.value = true;
    setTimeout(() => (copied.value = false), 2000);
    emit("success", "링크가 복사되었습니다");
  } catch (error) {
    linkInput.value?.select();
    document.execCommand("copy");
    copied.value = true;
    setTimeout(() => (copied.value = false), 2000);
  }
};

const deactivateLink = async () => {
  if (!confirm("공유 링크를 비활성화하시겠습니까?")) return;
  try {
    await groupShareApi.deactivateShareLink(props.groupId);
    shareLink.value = null;
    emit("success", "공유 링크가 비활성화되었습니다");
  } catch (error) {
    emit("error", "링크 비활성화에 실패했습니다");
  }
};

const addUser = async () => {
  if (!newUserId.value) {
    emit("error", "사용자 ID를 입력해주세요");
    return;
  }
  try {
    await groupShareApi.addUserToGroup(props.groupId, Number(newUserId.value), newUserRole.value);
    await loadData();
    newUserId.value = "";
    newUserRole.value = "EDITOR";
    emit("success", "사용자가 추가되었습니다");
  } catch (error) {
    emit("error", error.response?.data?.message || "사용자 추가에 실패했습니다");
  }
};

const changeRole = async (userId, newRole) => {
  try {
    await groupShareApi.updateUserRole(props.groupId, userId, newRole);
    await loadData();
    emit("success", "권한이 변경되었습니다");
  } catch (error) {
    emit("error", "권한 변경에 실패했습니다");
  }
};

const removeUser = async (userId) => {
  if (!confirm("이 사용자를 제거하시겠습니까?")) return;
  try {
    await groupShareApi.removeUserFromGroup(props.groupId, userId);
    await loadData();
    emit("success", "사용자가 제거되었습니다");
  } catch (error) {
    emit("error", "사용자 제거에 실패했습니다");
  }
};

const close = () => emit("close");

watch(
  () => props.show,
  (newValue) => {
    if (newValue) loadData();
  }
);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap");

* {
  font-family: "Inter", -apple-system, BlinkMacSystemFont, sans-serif;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* ← 배경 추가 */
  backdrop-filter: blur(4px); /* ← 블러 효과 (선택) */
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
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: #212529;
  margin-bottom: 0.125rem;
}

.user-email {
  font-size: 13px;
  color: #868e96;
}

.user-actions {
  display: flex;
  align-items: center;
  gap: 0.625rem;
}

.role-select.modern {
  padding: 0.5rem 0.875rem;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  font-size: 13px;
  background: white;
  color: #495057;
  font-weight: 500;
  cursor: pointer;
  outline: none;
  transition: all 0.2s;
}

.role-select.modern:hover {
  border-color: #adb5bd;
}

.role-select.modern:focus {
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}

.role-badge {
  padding: 0.375rem 0.875rem;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 0.375rem;
  white-space: nowrap;
}

.badge-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
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
