<template>
  <div v-if="isOpen" class="modal-overlay" @click.self="closeModal">
    <div class="modal-container">
      <div class="modal-header">
        <h2 class="modal-title">{{ isEditMode ? "일정 수정" : "새 일정 만들기" }}</h2>
        <button class="close-button" @click="closeModal">
          <svg
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M18 6L6 18"
              stroke="#6B7280"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M6 6L18 18"
              stroke="#6B7280"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </button>
      </div>

      <div class="modal-body">
        <!-- 일정 이름 -->
        <div class="form-group">
          <label class="form-label">일정 이름</label>
          <input
            v-model="folderName"
            type="text"
            class="form-input"
            placeholder="예: 제주도 여행"
            maxlength="50"
          />
        </div>

        <!-- 썸네일 이미지 -->
        <div class="form-group">
          <label class="form-label">썸네일 이미지</label>

          <!-- 이미지 프리뷰 -->
          <div v-if="previewUrl" class="image-preview">
            <img :src="previewUrl" alt="미리보기" />
            <button type="button" class="remove-image-button" @click="removeImage">
              <svg
                width="16"
                height="16"
                viewBox="0 0 16 16"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path d="M12 4L4 12" stroke="white" stroke-width="2" stroke-linecap="round" />
                <path d="M4 4L12 12" stroke="white" stroke-width="2" stroke-linecap="round" />
              </svg>
            </button>
          </div>

          <!-- 파일 선택 버튼 -->
          <div v-else class="file-upload-area" @click="triggerFileInput">
            <svg
              width="40"
              height="40"
              viewBox="0 0 40 40"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M35 25V31.6667C35 32.5507 34.6488 33.3986 34.0237 34.0237C33.3986 34.6488 32.5507 35 31.6667 35H8.33333C7.44928 35 6.60143 34.6488 5.97631 34.0237C5.35119 33.3986 5 32.5507 5 31.6667V25"
                stroke="#9CA3AF"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M28.3332 13.3333L19.9998 5L11.6665 13.3333"
                stroke="#9CA3AF"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M20 5V25"
                stroke="#9CA3AF"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
            <p class="upload-text">클릭하여 이미지 선택</p>
            <p class="upload-hint">JPG, PNG (최대 5MB)</p>
          </div>

          <input
            ref="fileInput"
            type="file"
            accept="image/*"
            style="display: none"
            @change="handleFileSelect"
          />
        </div>
      </div>

      <div class="modal-footer">
        <button class="cancel-button" @click="closeModal">취소</button>
        <button class="submit-button" :disabled="!folderName.trim()" @click="handleSubmit">
          {{ isEditMode ? "수정" : "만들기" }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed } from "vue";

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true,
  },
  isEditMode: {
    type: Boolean,
    default: false,
  },
  initialData: {
    type: Object,
    default: null,
  },
});

const emit = defineEmits(["close", "create", "update"]);

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";

const folderName = ref("");
const thumbnailFile = ref(null);
const previewUrl = ref("");
const fileInput = ref(null);

// props 변경 감지하여 초기값 설정
watch(
  [() => props.isOpen, () => props.isEditMode, () => props.initialData],
  () => {
    if (props.isOpen) {
      if (props.isEditMode && props.initialData) {
        // 수정 모드
        folderName.value = props.initialData.name || "";
        if (props.initialData.thumbnailUrl) {
          previewUrl.value = `${API_BASE_URL}${props.initialData.thumbnailUrl}`;
        } else {
          previewUrl.value = "";
        }
        thumbnailFile.value = null;
      } else {
        // 생성 모드
        resetForm();
      }
    }
  },
  { immediate: true }
);

const resetForm = () => {
  folderName.value = "";
  thumbnailFile.value = null;
  previewUrl.value = "";
};

const triggerFileInput = () => {
  fileInput.value?.click();
};

const handleFileSelect = (event) => {
  const file = event.target.files?.[0];
  if (!file) return;

  // 파일 크기 체크 (5MB)
  if (file.size > 5 * 1024 * 1024) {
    alert("파일 크기는 5MB 이하여야 합니다.");
    return;
  }

  // 이미지 파일 체크
  if (!file.type.startsWith("image/")) {
    alert("이미지 파일만 업로드 가능합니다.");
    return;
  }

  thumbnailFile.value = file;

  // 미리보기 생성
  const reader = new FileReader();
  reader.onload = (e) => {
    previewUrl.value = e.target?.result;
  };
  reader.readAsDataURL(file);
};

const removeImage = () => {
  thumbnailFile.value = null;
  previewUrl.value = "";
  if (fileInput.value) {
    fileInput.value.value = "";
  }
};

const handleSubmit = () => {
  if (!folderName.value.trim()) {
    alert("일정 이름을 입력해주세요.");
    return;
  }

  const data = {
    folderName: folderName.value.trim(),
    thumbnailFile: thumbnailFile.value,
  };

  if (props.isEditMode) {
    data.folderId = props.initialData.id;
    emit("update", data);
  } else {
    emit("create", data);
  }
};

const closeModal = () => {
  emit("close");
};
</script>

<style scoped>
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

.modal-container {
  background: #ffffff;
  border-radius: 16px;
  width: 100%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  background: none;
  border: none;
  padding: 0.5rem;
  cursor: pointer;
  border-radius: 8px;
  transition: background 0.2s ease;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-button:hover {
  background: #f3f4f6;
}

.modal-body {
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-label {
  font-size: 14px;
  font-weight: 500;
  color: #374151;
}

.form-input {
  padding: 0.75rem 1rem;
  border: 1px solid #d1d5db;
  border-radius: 8px;
  font-size: 16px;
  color: #1e1e1e;
  transition: border-color 0.2s ease;
}

.form-input:focus {
  outline: none;
  border-color: #1e3a8a;
}

.form-input::placeholder {
  color: #9ca3af;
}

.image-preview {
  position: relative;
  width: 100%;
  height: 200px;
  border-radius: 12px;
  overflow: hidden;
  background: #f3f4f6;
}

.image-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-image-button {
  position: absolute;
  top: 0.75rem;
  right: 0.75rem;
  width: 32px;
  height: 32px;
  background: rgba(0, 0, 0, 0.6);
  border: none;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.2s ease;
}

.remove-image-button:hover {
  background: rgba(0, 0, 0, 0.8);
}

.file-upload-area {
  border: 2px dashed #d1d5db;
  border-radius: 12px;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  transition: all 0.2s ease;
}

.file-upload-area:hover {
  border-color: #1e3a8a;
  background: #f9fafb;
}

.upload-text {
  font-size: 16px;
  font-weight: 500;
  color: #374151;
  margin: 0;
}

.upload-hint {
  font-size: 14px;
  color: #9ca3af;
  margin: 0;
}

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
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.cancel-button {
  background: #f3f4f6;
  color: #4b5563;
}

.cancel-button:hover {
  background: #e5e7eb;
}

.submit-button {
  background: #1e3a8a;
  color: #ffffff;
}

.submit-button:hover:not(:disabled) {
  background: #1e40af;
}

.submit-button:disabled {
  background: #9ca3af;
  cursor: not-allowed;
  opacity: 0.6;
}

@media (max-width: 640px) {
  .modal-container {
    max-width: 100%;
    margin: 0;
    border-radius: 16px 16px 0 0;
    max-height: 95vh;
  }

  .modal-header {
    padding: 1.25rem;
  }

  .modal-title {
    font-size: 18px;
  }

  .modal-body {
    padding: 1.25rem;
  }

  .modal-footer {
    padding: 1.25rem;
    flex-direction: column-reverse;
  }

  .cancel-button,
  .submit-button {
    width: 100%;
  }
}
</style>
