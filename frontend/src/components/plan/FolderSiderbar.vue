<template>
  <aside class="folder-sidebar">
    <div class="folder-list">
      <div
        v-for="folder in folders"
        :key="folder.id"
        class="folder-item"
        :class="{ active: folder.id === currentFolderId }"
        @click="selectFolder(folder.id)"
      >
        <div class="folder-thumbnail">
          <img
            :src="getThumbnailUrl(folder.thumbnailUrl)"
            :alt="folder.name"
            @error="handleImageError"
          />
        </div>
        <div class="folder-name">{{ folder.name }}</div>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  folders: {
    type: Array,
    required: true,
  },
  currentFolderId: {
    type: Number,
    required: true,
  },
});

const emit = defineEmits(["select-folder"]);

const selectFolder = (folderId) => {
  emit("select-folder", folderId);
};

const getThumbnailUrl = (thumbnailUrl) => {
  if (!thumbnailUrl) {
    return "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400";
  }
  return `http://localhost:8080/api${thumbnailUrl}`;
};

// 이미지 로드 실패 시 기본 이미지
const handleImageError = (event) => {
  event.target.src = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400";
};
</script>

<style scoped>
/* 왼쪽 폴더 목록 사이드바 */
.folder-sidebar {
  width: 120px;
  background: rgba(255, 255, 255, 0.3);
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  padding: 2.25rem 1.75rem;
}

.folder-list {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  align-items: center;
}

.folder-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  transition: all 0.2s;
}

.folder-item:hover .folder-thumbnail {
  transform: scale(1.05);
}

.folder-item.active .folder-thumbnail {
  box-shadow: 0 0 0 3px #2563eb;
}

.folder-thumbnail {
  width: 96px;
  height: 96px;
  border-radius: 30px;
  overflow: hidden;
  flex-shrink: 0;
  transition: all 0.2s;
}

.folder-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.folder-name {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 96px;
}

/* 반응형 */
@media (max-width: 1440px) {
  .folder-sidebar {
    width: 120px;
    padding: 1.5rem 1rem;
  }

  .folder-thumbnail {
    width: 80px;
    height: 80px;
  }

  .folder-name {
    font-size: 14px;
    max-width: 80px;
  }
}

@media (max-width: 1024px) {
  .folder-sidebar {
    width: 100%;
    max-height: 180px;
    padding: 1.5rem 1rem;
    border-right: none;
    border-bottom: 1px solid #e5e7eb;
  }

  .folder-list {
    flex-direction: row;
    overflow-x: auto;
    overflow-y: hidden;
    gap: 1.5rem;
  }
}

@media (max-width: 768px) {
  .folder-sidebar {
    max-height: 160px;
    padding: 1rem;
  }

  .folder-thumbnail {
    width: 72px;
    height: 72px;
    border-radius: 24px;
  }

  .folder-name {
    font-size: 13px;
    max-width: 72px;
  }
}
</style>
