<template>
  <Teleport to="body">
    <Transition name="modal">
      <div v-if="isOpen" class="modal-overlay" @click="handleOverlayClick">
        <div class="modal-container" @click.stop>
          <div class="modal-header">
            <h2 class="modal-title">새 일정 만들기</h2>
            <button class="close-button" @click="close">
              <svg
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M18 6L6 18M6 6L18 18"
                  stroke="#4A5565"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </button>
          </div>

          <div class="modal-body">
            <!-- 썸네일 이미지 업로드 -->
            <div class="form-group">
              <label class="form-label">썸네일 이미지</label>
              <div class="image-upload-area">
                <input
                  type="file"
                  ref="fileInput"
                  accept="image/*"
                  @change="handleImageUpload"
                  class="file-input"
                />
                <div v-if="!previewImage" class="upload-placeholder" @click="triggerFileInput">
                  <svg
                    width="48"
                    height="48"
                    viewBox="0 0 48 48"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      d="M38 38H10C9.46957 38 8.96086 37.7893 8.58579 37.4142C8.21071 37.0391 8 36.5304 8 36V12C8 11.4696 8.21071 10.9609 8.58579 10.5858C8.96086 10.2107 9.46957 10 10 10H16L20 6H28L32 10H38C38.5304 10 39.0391 10.2107 39.4142 10.5858C39.7893 10.9609 40 11.4696 40 12V36C40 36.5304 39.7893 37.0391 39.4142 37.4142C39.0391 37.7893 38.5304 38 38 38Z"
                      stroke="#9CA3AF"
                      stroke-width="2"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                    <path
                      d="M24 30C27.3137 30 30 27.3137 30 24C30 20.6863 27.3137 18 24 18C20.6863 18 18 20.6863 18 24C18 27.3137 20.6863 30 24 30Z"
                      stroke="#9CA3AF"
                      stroke-width="2"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                  <p class="upload-text">클릭하여 이미지 업로드</p>
                  <p class="upload-hint">JPG, PNG (최대 5MB)</p>
                </div>
                <div v-else class="image-preview">
                  <img :src="previewImage" alt="Preview" />
                  <button class="remove-image-button" @click="removeImage">
                    <svg
                      width="20"
                      height="20"
                      viewBox="0 0 20 20"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M15 5L5 15M5 5L15 15"
                        stroke="white"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>
                </div>
              </div>
            </div>

            <!-- 폴더명 입력 -->
            <div class="form-group">
              <label class="form-label">폴더명</label>
              <input
                type="text"
                class="form-input"
                placeholder="폴더명을 입력해주세요"
                v-model="folderName"
                @keyup.enter="handleSubmit"
              />
            </div>
          </div>

          <div class="modal-footer">
            <button class="cancel-button" @click="close">취소</button>
            <button class="submit-button" @click="handleSubmit">만들기</button>
          </div>
        </div>
      </div>
    </Transition>
  </Teleport>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  isOpen: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(["close", "create"]);

const fileInput = ref(null);
const previewImage = ref(null);
const folderName = ref("");
const selectedFile = ref(null);

// 파일 입력 트리거
const triggerFileInput = () => {
  fileInput.value?.click();
};

// 이미지 업로드 처리
const handleImageUpload = (event) => {
  const file = event.target.files?.[0];
  if (!file) return;

  // 파일 크기 체크 (5MB)
  if (file.size > 5 * 1024 * 1024) {
    alert("파일 크기는 5MB 이하여야 합니다.");
    return;
  }

  // 파일 타입 체크
  if (!file.type.startsWith("image/")) {
    alert("이미지 파일만 업로드 가능합니다.");
    return;
  }

  selectedFile.value = file;

  // 미리보기 생성
  const reader = new FileReader();
  reader.onload = (e) => {
    previewImage.value = e.target?.result;
  };
  reader.readAsDataURL(file);
};

// 이미지 제거
const removeImage = () => {
  previewImage.value = null;
  selectedFile.value = null;
  if (fileInput.value) {
    fileInput.value.value = "";
  }
};

// 모달 닫기
const close = () => {
  emit("close");
};

// 오버레이 클릭 처리
const handleOverlayClick = () => {
  close();
};

const handleSubmit = () => {
  // 유효성 검사
  if (!folderName.value.trim()) {
    alert("폴더명을 입력해주세요.");
    return;
  }

  if (!previewImage.value) {
    alert("썸네일 이미지를 업로드해주세요.");
    return;
  }

  // 새 일정 데이터 생성
  const newPlan = {
    folderName: folderName.value,
    thumbnailFile: selectedFile.value,
  };

  emit("create", newPlan);
  resetForm();
};

// 폼 초기화
const resetForm = () => {
  folderName.value = "";
  previewImage.value = null;
  selectedFile.value = null;
  if (fileInput.value) {
    fileInput.value.value = "";
  }
};

// 모달이 닫힐 때 폼 초기화
watch(
  () => props.isOpen,
  (newValue) => {
    if (!newValue) {
      resetForm();
    }
  }
);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

/* 모달 트랜지션 */
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.3s ease;
}

.modal-enter-from,
.modal-leave-to {
  opacity: 0;
}

.modal-enter-active .modal-container,
.modal-leave-active .modal-container {
  transition: transform 0.3s ease;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  transform: scale(0.95);
}

/* 모달 오버레이 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  padding: 1rem;
}

/* 모달 컨테이너 */
.modal-container {
  background: #ffffff;
  border-radius: 16px;
  width: 100%;
  max-width: 560px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 모달 헤더 */
.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.modal-title {
  font-size: 20px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0;
}

.close-button {
  width: 32px;
  height: 32px;
  border: none;
  background: none;
  cursor: pointer;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s;
}

.close-button:hover {
  background: #f3f4f6;
}

/* 모달 바디 */
.modal-body {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 폼 그룹 */
.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-label {
  font-size: 16px;
  font-weight: 500;
  color: #1e1e1e;
}

/* 이미지 업로드 */
.image-upload-area {
  position: relative;
  width: 100%;
}

.file-input {
  display: none;
}

.upload-placeholder {
  width: 100%;
  height: 240px;
  border: 2px dashed #e5e7eb;
  border-radius: 12px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  cursor: pointer;
  transition: all 0.2s;
  background: #f9fafb;
}

.upload-placeholder:hover {
  border-color: #2563eb;
  background: #eff6ff;
}

.upload-text {
  font-size: 16px;
  font-weight: 500;
  color: #4b5563;
  margin: 0;
}

.upload-hint {
  font-size: 14px;
  color: #9ca3af;
  margin: 0;
}

.image-preview {
  position: relative;
  width: 100%;
  height: 240px;
  border-radius: 12px;
  overflow: hidden;
}

.image-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-image-button {
  position: absolute;
  top: 12px;
  right: 12px;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: none;
  background: rgba(0, 0, 0, 0.6);
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s;
}

.remove-image-button:hover {
  background: rgba(0, 0, 0, 0.8);
}

/* 폼 입력 */
.form-input {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 16px;
  font-family: inherit;
  color: #1e1e1e;
  outline: none;
  transition: border-color 0.2s;
}

.form-input::placeholder {
  color: #9ca3af;
}

.form-input:focus {
  border-color: #2563eb;
}

/* 모달 푸터 */
.modal-footer {
  display: flex;
  gap: 0.75rem;
  padding: 1.5rem;
  border-top: 1px solid #e5e7eb;
}

.cancel-button,
.submit-button {
  flex: 1;
  padding: 0.75rem 1rem;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.cancel-button {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  color: #4b5563;
}

.cancel-button:hover {
  background: #f9fafb;
  border-color: #d1d5db;
}

.submit-button {
  background: #2563eb;
  border: none;
  color: #ffffff;
}

.submit-button:hover {
  background: #1e40af;
}

.submit-button:active {
  background: #1e3a8a;
}

/* 반응형 */
@media (max-width: 768px) {
  .modal-container {
    max-width: 100%;
    border-radius: 12px;
  }

  .modal-header {
    padding: 1.25rem;
  }

  .modal-title {
    font-size: 18px;
  }

  .modal-body {
    padding: 1.25rem;
    gap: 1.25rem;
  }

  .upload-placeholder {
    height: 200px;
  }

  .image-preview {
    height: 200px;
  }

  .modal-footer {
    padding: 1.25rem;
  }
}

@media (max-width: 480px) {
  .modal-overlay {
    padding: 0.5rem;
  }

  .modal-header {
    padding: 1rem;
  }

  .modal-title {
    font-size: 16px;
  }

  .modal-body {
    padding: 1rem;
    gap: 1rem;
  }

  .form-label {
    font-size: 14px;
  }

  .upload-placeholder {
    height: 180px;
  }

  .upload-text {
    font-size: 14px;
  }

  .upload-hint {
    font-size: 13px;
  }

  .image-preview {
    height: 180px;
  }

  .form-input {
    font-size: 14px;
    padding: 0.625rem 0.875rem;
  }

  .modal-footer {
    padding: 1rem;
    gap: 0.5rem;
  }

  .cancel-button,
  .submit-button {
    font-size: 14px;
    padding: 0.625rem 0.875rem;
  }
}
</style>
