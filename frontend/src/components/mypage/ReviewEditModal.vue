<template>
  <div class="modal-overlay">
    <div class="modal">

      <h3>후기 수정</h3>

      <!-- 별점 -->
      <div class="stars">
        <span
          v-for="i in 5"
          :key="i"
          @click="rating = i"
          :class="{ active: i <= rating }"
        >
          ★
        </span>
      </div>

      <!-- 후기 내용 -->
      <textarea v-model="text" />

      <!-- 이미지 업로드 -->
      <div class="image-upload-area">
        <input
          type="file"
          multiple
          accept="image/*"
          @change="handleImageUpload"
          hidden
          ref="fileInput"
        />

        <div class="upload-placeholder" @click="triggerFileInput">

        </div>

        <div class="preview-list">
          <div
            v-for="(img, i) in previewImages"
            :key="i"
            class="preview-item"
          >
            <img :src="img" />
            <button @click="removeImage(i)">✕</button>
          </div>
        </div>
      </div>


      <div class="buttons">
        <button class="close-btn" @click="emit('close')">취소</button>
        <button class="confirm-btn" @click="onConfirm">확인</button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  review: Object,
});

const emit = defineEmits(["confirm", "close"]);

const rating = ref(0);
const text = ref("");

watch(
  () => props.review,
  (review) => {
    if (review) {
      rating.value = review.rating;
      text.value = review.text;
    }
  },
  { immediate: true }
);

const onConfirm = () => {
  emit("confirm", {
    ...props.review,
    rating: rating.value,
    text: text.value,
  });
};



const previewImages = ref([]);
const reviewImages = ref([]);
const fileInput = ref(null);

const triggerFileInput = () => {
  fileInput.value.click();
};

const handleImageUpload = (e) => {
  const files = Array.from(e.target.files);

  if (previewImages.value.length + files.length > 10) {
    alert("이미지는 최대 10장까지 가능합니다.");
    return;
  }

  files.forEach((file) => {
    reviewImages.value.push(file);
    previewImages.value.push(URL.createObjectURL(file));
  });
};

const removeImage = (index) => {
  reviewImages.value.splice(index, 1);
  previewImages.value.splice(index, 1);
};
const submit = () => {
  const formData = new FormData();

  formData.append(
    "req",
    new Blob(
      [JSON.stringify({ rating: form.rating, content: form.content })],
      { type: "application/json" }
    )
  );

  reviewImages.value.forEach((file) => {
    formData.append("images", file);
  });

  emit("confirm", { id: review.id, formData });
};

</script>
<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.modal {
  background: #fff;
  width: 360px;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
}

.stars {
  margin: 12px 0;
}

.stars span {
  font-size: 22px;
  cursor: pointer;
  color: #ddd;
}

.stars span.active {
  color: #f5b301;
}

textarea {
  width: 95%;
  min-height: 80px;
  margin-top: 10px;
  padding: 8px;
  resize: none;
}

.buttons {
  display: flex;
  justify-content: flex-end; /* 오른쪽 정렬 */
  align-items: center;
  gap: 0.75rem; /* 버튼 사이 간격 */
  padding-top: 10px;
}

.confirm-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1rem;
  background: #7c3aed;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.confirm-btn:hover {
  background: #6d28d9;
}

.close-btn {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1rem;
  background: #8e8e8e;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.close-btn:hover {
  background: gray;
}
</style>
