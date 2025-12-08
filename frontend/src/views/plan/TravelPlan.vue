<template>
  <div class="planner-folder-page">
    <!-- 메인 컨텐츠 -->
    <div class="main-content">
      <!-- 왼쪽 폴더 목록 사이드바 -->
      <FolderSidebar
        :folders="folders"
        :current-folder-id="currentFolderId"
        @select-folder="selectFolder"
      />

      <!-- 중앙 스팟 목록 사이드바 -->
      <aside class="spot-sidebar">
        <div class="sidebar-top">
          <!-- 검색 -->
          <div class="search-box">
            <svg
              width="20"
              height="20"
              viewBox="0 0 20 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M9.16667 15.8333C12.8486 15.8333 15.8333 12.8486 15.8333 9.16667C15.8333 5.48477 12.8486 2.5 9.16667 2.5C5.48477 2.5 2.5 5.48477 2.5 9.16667C2.5 12.8486 5.48477 15.8333 9.16667 15.8333Z"
                stroke="#9CA3AF"
                stroke-width="1.66667"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M17.5 17.5L13.875 13.875"
                stroke="#9CA3AF"
                stroke-width="1.66667"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
            <input type="text" placeholder="스팟 검색" v-model="searchQuery" />
          </div>

          <!-- 폴더 정보 -->
          <div class="folder-header">
            <h1 class="folder-title">부산 여행</h1>
            <div class="folder-meta">
              <span>공유 중</span>
              <span class="separator">·</span>
              <span>나 포함 3명</span>
            </div>
            <button class="share-button">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M15 6.66667C16.3807 6.66667 17.5 5.54738 17.5 4.16667C17.5 2.78595 16.3807 1.66667 15 1.66667C13.6193 1.66667 12.5 2.78595 12.5 4.16667C12.5 5.54738 13.6193 6.66667 15 6.66667Z"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M5 12.5C6.38071 12.5 7.5 11.3807 7.5 10C7.5 8.61929 6.38071 7.5 5 7.5C3.61929 7.5 2.5 8.61929 2.5 10C2.5 11.3807 3.61929 12.5 5 12.5Z"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M15 18.3333C16.3807 18.3333 17.5 17.214 17.5 15.8333C17.5 14.4526 16.3807 13.3333 15 13.3333C13.6193 13.3333 12.5 14.4526 12.5 15.8333C12.5 17.214 13.6193 18.3333 15 18.3333Z"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M7.15833 11.175L12.85 14.6583"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M12.8417 5.34167L7.15833 8.825"
                  stroke="currentColor"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </button>
          </div>

          <!-- 스팟 목록 -->
          <div class="spots-section">
            <div class="section-header">
              <h2>스팟 목록</h2>
              <span class="spot-count">{{ spots.length }}개</span>
            </div>

            <div class="spots-list">
              <div v-for="(spot, index) in spots" :key="spot.id" class="spot-card">
                <!-- 순서 컨트롤 -->
                <div class="order-control">
                  <button class="order-button" @click="moveSpotUp(index)" :disabled="index === 0">
                    <svg
                      width="12"
                      height="12"
                      viewBox="0 0 12 12"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M6 9.5V2.5M6 2.5L2.5 6M6 2.5L9.5 6"
                        stroke="currentColor"
                        stroke-width="1.5"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>
                  <span class="order-number">{{ index + 1 }}</span>
                  <button
                    class="order-button"
                    @click="moveSpotDown(index)"
                    :disabled="index === spots.length - 1"
                  >
                    <svg
                      width="12"
                      height="12"
                      viewBox="0 0 12 12"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M6 2.5V9.5M6 9.5L9.5 6M6 9.5L2.5 6"
                        stroke="currentColor"
                        stroke-width="1.5"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>
                </div>

                <!-- 썸네일 -->
                <div class="spot-thumbnail">
                  <img :src="spot.image" :alt="spot.name" />
                </div>

                <!-- 스팟 정보 -->
                <div class="spot-info">
                  <div class="spot-header">
                    <h3 class="spot-name">{{ spot.name }}</h3>
                    <span class="spot-badge" :class="getBadgeClass(spot.category)">
                      {{ spot.category }}
                    </span>
                  </div>
                  <p class="spot-address">{{ spot.address }}</p>
                  <div class="spot-memo" v-if="spot.memo">
                    <span class="memo-label">메모</span>
                    <span class="memo-text">{{ spot.memo }}</span>
                  </div>
                </div>

                <!-- 액션 버튼 -->
                <div class="spot-actions">
                  <button
                    class="action-button favorite"
                    :class="{ active: spot.isFavorite }"
                    @click="toggleFavorite(spot)"
                  >
                    <svg
                      width="20"
                      height="20"
                      viewBox="0 0 20 20"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M17.3667 3.8415C16.9412 3.41583 16.4369 3.07803 15.8815 2.84757C15.3261 2.61711 14.7301 2.49854 14.1284 2.49854C13.5266 2.49854 12.9306 2.61711 12.3752 2.84757C11.8198 3.07803 11.3155 3.41583 10.89 3.8415L9.99999 4.73150L9.10999 3.8415C8.25113 2.98264 7.09142 2.49898 5.88165 2.49898C4.67189 2.49898 3.51218 2.98264 2.65332 3.8415C1.79446 4.70036 1.31079 5.86007 1.31079 7.06984C1.31079 8.2796 1.79446 9.43931 2.65332 10.2982L3.54332 11.1882L9.99999 17.6448L16.4567 11.1882L17.3467 10.2982C17.7723 9.87265 18.1101 9.36833 18.3406 8.81293C18.5711 8.25754 18.6896 7.66152 18.6896 7.05984C18.6896 6.45816 18.5711 5.86214 18.3406 5.30674C18.1101 4.75135 17.7723 4.24703 17.3467 3.8215"
                        :fill="spot.isFavorite ? '#FB2C36' : 'none'"
                        :stroke="spot.isFavorite ? '#FB2C36' : 'currentColor'"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>
                  <button class="action-button delete" @click="deleteSpot(spot.id)">
                    <svg
                      width="20"
                      height="20"
                      viewBox="0 0 20 20"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M2.5 5H4.16667H17.5"
                        stroke="currentColor"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                      <path
                        d="M15.8332 5.00016V16.6668C15.8332 17.1087 15.6576 17.5327 15.345 17.8453C15.0325 18.1578 14.6085 18.3335 14.1665 18.3335H5.83317C5.39114 18.3335 4.96722 18.1578 4.65466 17.8453C4.3421 17.5327 4.1665 17.1087 4.1665 16.6668V5.00016M6.6665 5.00016V3.3335C6.6665 2.89147 6.8421 2.46755 7.15466 2.15499C7.46722 1.84243 7.89114 1.66683 8.33317 1.66683H11.6665C12.1085 1.66683 12.5325 1.84243 12.845 2.15499C13.1576 2.46755 13.3332 2.89147 13.3332 3.3335V5.00016"
                        stroke="currentColor"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 댓글 섹션 -->
        <div class="comments-section">
          <div class="section-header">
            <h2>댓글</h2>
            <span class="comment-count">{{ comments.length }}</span>
          </div>

          <div class="comments-list">
            <div v-for="comment in comments" :key="comment.id" class="comment-item">
              <div class="comment-avatar">
                <img :src="comment.avatar" :alt="comment.author" />
              </div>
              <div class="comment-content">
                <div class="comment-header">
                  <span class="comment-author">{{ comment.author }}</span>
                  <span class="comment-time">{{ comment.time }}</span>
                </div>
                <p class="comment-text">{{ comment.text }}</p>
              </div>
            </div>
          </div>

          <div class="comment-input-wrapper">
            <input
              type="text"
              class="comment-input"
              placeholder="댓글을 입력하세요"
              v-model="newComment"
              @keyup.enter="addComment"
            />
            <button class="comment-submit" @click="addComment">댓글 달기</button>
          </div>
        </div>
      </aside>

      <!-- 오른쪽 지도 영역 -->
      <div class="map-area">
        <div class="map-placeholder">
          <img src="https://images.unsplash.com/photo-1524661135-423995f22d0b?w=1200" alt="Map" />
          <!-- 마커들 -->
          <div
            v-for="(marker, index) in mapMarkers"
            :key="index"
            class="map-marker"
            :style="{ left: marker.x + '%', top: marker.y + '%' }"
          >
            <div class="marker-pin">{{ index + 1 }}</div>
          </div>
        </div>

        <!-- 줌 컨트롤 -->
        <div class="zoom-controls">
          <button class="zoom-button" @click="zoomIn">
            <svg
              width="20"
              height="20"
              viewBox="0 0 20 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M10 4.16667V15.8333"
                stroke="currentColor"
                stroke-width="1.66667"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M4.16699 10H15.8337"
                stroke="currentColor"
                stroke-width="1.66667"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
          <button class="zoom-button" @click="zoomOut">
            <svg
              width="20"
              height="20"
              viewBox="0 0 20 20"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M4.16699 10H15.8337"
                stroke="currentColor"
                stroke-width="1.66667"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import FolderSidebar from "@/components/plan/FolderSiderbar.vue";

const router = useRouter();
const route = useRoute();

const currentFolderId = ref(Number(route.params.id) || 1);
const searchQuery = ref("");
const newComment = ref("");

// 폴더 목록
const folders = ref([
  {
    id: 1,
    name: "부산 여행",
    thumbnail: "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=200",
    spotCount: 8,
  },
  {
    id: 2,
    name: "제주도 여행",
    thumbnail: "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=200",
    spotCount: 12,
  },
  {
    id: 3,
    name: "서울 여행",
    thumbnail: "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?w=200",
    spotCount: 15,
  },
  {
    id: 4,
    name: "강릉 여행",
    thumbnail: "https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=200",
    spotCount: 6,
  },
]);

// 스팟 목록
const spots = ref([
  {
    id: 1,
    name: "해운대 해수욕장",
    category: "관광지",
    address: "부산 해운대구 우동",
    image: "https://images.unsplash.com/photo-1559827260-dc66d52bef19?w=200",
    memo: "아침 일찍 가면 사람이 적어요",
    isFavorite: true,
  },
  {
    id: 2,
    name: "광안리 해수욕장",
    category: "관광지",
    address: "부산 수영구 광안동",
    image: "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=200",
    memo: "",
    isFavorite: false,
  },
  {
    id: 3,
    name: "감천문화마을",
    category: "관광지",
    address: "부산 사하구 감천동",
    image: "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?w=200",
    memo: "경사가 있으니 편한 신발 추천",
    isFavorite: true,
  },
]);

// 댓글 목록
const comments = ref([
  {
    id: 1,
    author: "김철수",
    avatar: "https://api.dicebear.com/7.x/avataaars/svg?seed=user1",
    time: "2시간 전",
    text: "일정 잘 짰네요! 저도 참고하겠습니다",
  },
  {
    id: 2,
    author: "이영희",
    avatar: "https://api.dicebear.com/7.x/avataaars/svg?seed=user2",
    time: "5시간 전",
    text: "해운대는 주차가 어려우니 대중교통 이용을 추천해요",
  },
]);

// 지도 마커
const mapMarkers = ref([
  { x: 30, y: 40 },
  { x: 50, y: 55 },
  { x: 65, y: 35 },
]);

// 폴더 선택
const selectFolder = (folderId) => {
  router.push(`/plans/${folderId}`);
};

// 배지 클래스
const getBadgeClass = (category) => {
  const classes = {
    관광지: "badge-tourist",
    팝업스토어: "badge-popup",
    축제: "badge-festival",
  };
  return classes[category] || "";
};

// 스팟 이동
const moveSpotUp = (index) => {
  if (index > 0) {
    const temp = spots.value[index];
    spots.value[index] = spots.value[index - 1];
    spots.value[index - 1] = temp;
  }
};

const moveSpotDown = (index) => {
  if (index < spots.value.length - 1) {
    const temp = spots.value[index];
    spots.value[index] = spots.value[index + 1];
    spots.value[index + 1] = temp;
  }
};

// 좋아요 토글
const toggleFavorite = (spot) => {
  spot.isFavorite = !spot.isFavorite;
};

// 스팟 삭제
const deleteSpot = (id) => {
  if (confirm("이 스팟을 삭제하시겠습니까?")) {
    const index = spots.value.findIndex((s) => s.id === id);
    if (index > -1) {
      spots.value.splice(index, 1);
    }
  }
};

// 댓글 추가
const addComment = () => {
  if (newComment.value.trim()) {
    comments.value.unshift({
      id: Date.now(),
      author: "나",
      avatar: "https://api.dicebear.com/7.x/avataaars/svg?seed=me",
      time: "방금",
      text: newComment.value,
    });
    newComment.value = "";
  }
};

// 지도 줌
const zoomIn = () => {
  console.log("Zoom in");
};

const zoomOut = () => {
  console.log("Zoom out");
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.planner-folder-page {
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  min-height: 100vh;
  background: #ffffff;
  display: flex;
  flex-direction: column;
}

/* 헤더 */
.main-header {
  background: #ffffff;
  border-bottom: 1px solid #e5e7eb;
  padding: 1rem 2rem;
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  max-width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;
}

.logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 20px;
  font-weight: 700;
  color: #1e3a8a;
  text-decoration: none;
}

.main-nav {
  display: flex;
  gap: 2rem;
  flex: 1;
  justify-content: center;
}

.nav-link {
  color: #6b7280;
  text-decoration: none;
  font-size: 16px;
  font-weight: 400;
  transition: color 0.2s;
}

.nav-link:hover {
  color: #1e3a8a;
}

.nav-link.active {
  color: #1e3a8a;
  font-weight: 600;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
}

.user-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 메인 컨텐츠 */
.main-content {
  display: flex;
  flex: 1;
  overflow: hidden;
}

/* 중앙 스팟 목록 사이드바 */
.spot-sidebar {
  width: 660px;
  background: #ffffff;
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar-top {
  flex: 1;
  overflow-y: auto;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 검색 */
.search-box {
  position: relative;
  display: flex;
  align-items: center;
}

.search-box svg {
  position: absolute;
  left: 1rem;
  pointer-events: none;
}

.search-box input {
  width: 100%;
  padding: 0.75rem 1rem 0.75rem 3rem;
  border: 1px solid #e5e7eb;
  border-radius: 10px;
  font-size: 14px;
  font-family: inherit;
  outline: none;
  transition: border-color 0.2s;
}

.search-box input:focus {
  border-color: #2563eb;
}

/* 폴더 헤더 */
.folder-header {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.folder-title {
  font-size: 24px;
  font-weight: 600;
  color: #1e1e1e;
}

.folder-meta {
  font-size: 14px;
  color: #6b7280;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.separator {
  color: #d1d5db;
}

.share-button {
  margin-top: 0.5rem;
  width: fit-content;
  padding: 0.5rem 1rem;
  background: #f3f4f6;
  color: #4b5563;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  transition: background 0.2s;
}

.share-button:hover {
  background: #e5e7eb;
}

/* 섹션 헤더 */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.section-header h2 {
  font-size: 16px;
  font-weight: 600;
  color: #1e1e1e;
}

.spot-count,
.comment-count {
  font-size: 14px;
  color: #6b7280;
}

/* 스팟 목록 */
.spots-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.spot-card {
  background: #f9fafb;
  border-radius: 12px;
  padding: 1rem;
  display: flex;
  gap: 1rem;
  align-items: flex-start;
}

/* 순서 컨트롤 */
.order-control {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
  flex-shrink: 0;
}

.order-button {
  width: 24px;
  height: 24px;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #4b5563;
  transition: all 0.2s;
}

.order-button:hover:not(:disabled) {
  background: #f3f4f6;
  border-color: #2563eb;
  color: #2563eb;
}

.order-button:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}

.order-number {
  font-size: 14px;
  font-weight: 600;
  color: #1e1e1e;
  min-width: 20px;
  text-align: center;
}

/* 스팟 썸네일 */
.spot-thumbnail {
  width: 96px;
  height: 96px;
  border-radius: 8px;
  overflow: hidden;
  flex-shrink: 0;
}

.spot-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 스팟 정보 */
.spot-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  min-width: 0;
}

.spot-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.spot-name {
  font-size: 14px;
  font-weight: 600;
  color: #1e1e1e;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.spot-badge {
  padding: 0.25rem 0.5rem;
  border-radius: 6px;
  font-size: 11px;
  font-weight: 500;
  white-space: nowrap;
  flex-shrink: 0;
}

.badge-tourist {
  background: #dbeafe;
  color: #2563eb;
}

.badge-popup {
  background: #ede9fe;
  color: #7c3aed;
}

.badge-festival {
  background: #fce7f3;
  color: #ec4899;
}

.spot-address {
  font-size: 12px;
  color: #6b7280;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.spot-memo {
  display: flex;
  gap: 0.5rem;
  padding: 0.5rem;
  background: #ffffff;
  border-radius: 6px;
  font-size: 12px;
}

.memo-label {
  color: #6b7280;
  font-weight: 500;
  flex-shrink: 0;
}

.memo-text {
  color: #1e1e1e;
  flex: 1;
}

/* 액션 버튼 */
.spot-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  flex-shrink: 0;
}

.action-button {
  width: 36px;
  height: 36px;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #6b7280;
  transition: all 0.2s;
}

.action-button:hover {
  background: #f9fafb;
}

.action-button.favorite.active {
  color: #fb2c36;
  border-color: #fb2c36;
}

.action-button.delete {
  color: #ef4444;
}

.action-button.delete:hover {
  background: #fef2f2;
  border-color: #ef4444;
}

/* 댓글 섹션 */
.comments-section {
  border-top: 1px solid #e5e7eb;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-height: 200px;
  overflow-y: auto;
}

.comment-item {
  display: flex;
  gap: 0.75rem;
}

.comment-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
  flex-shrink: 0;
}

.comment-avatar img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.comment-content {
  flex: 1;
  min-width: 0;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.25rem;
}

.comment-author {
  font-size: 14px;
  font-weight: 600;
  color: #1e1e1e;
}

.comment-time {
  font-size: 12px;
  color: #9ca3af;
}

.comment-text {
  font-size: 14px;
  color: #4b5563;
  line-height: 1.5;
}

.comment-input-wrapper {
  display: flex;
  gap: 0.5rem;
}

.comment-input {
  flex: 1;
  padding: 0.75rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 14px;
  font-family: inherit;
  outline: none;
}

.comment-input:focus {
  border-color: #2563eb;
}

.comment-submit {
  padding: 0.75rem 1rem;
  background: #2563eb;
  color: #ffffff;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  white-space: nowrap;
  transition: background 0.2s;
}

.comment-submit:hover {
  background: #1e40af;
}

/* 지도 영역 */
.map-area {
  flex: 1;
  position: relative;
  background: #f3f4f6;
}

.map-placeholder {
  width: 100%;
  height: 100%;
  position: relative;
}

.map-placeholder img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.map-marker {
  position: absolute;
  transform: translate(-50%, -100%);
}

.marker-pin {
  width: 32px;
  height: 32px;
  background: #2563eb;
  color: #ffffff;
  border: 3px solid #ffffff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

.zoom-controls {
  position: absolute;
  bottom: 2rem;
  right: 2rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.zoom-button {
  width: 40px;
  height: 40px;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color: #4b5563;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.2s;
}

.zoom-button:hover {
  background: #f9fafb;
  color: #2563eb;
}

/* 반응형 */
@media (max-width: 1440px) {
  .spot-sidebar {
    width: 380px;
  }
}

@media (max-width: 1024px) {
  .main-content {
    flex-direction: column;
  }

  .spot-sidebar {
    width: 100%;
    max-height: 60vh;
  }

  .map-area {
    min-height: 40vh;
  }
}

@media (max-width: 768px) {
  .main-header {
    padding: 1rem;
  }

  .header-content {
    flex-wrap: wrap;
  }

  .main-nav {
    order: 3;
    width: 100%;
    justify-content: space-around;
  }

  .spot-sidebar {
    max-height: 50vh;
  }

  .sidebar-top {
    padding: 1rem;
  }

  .spot-card {
    flex-wrap: wrap;
  }

  .order-control {
    flex-direction: row;
    width: 100%;
    order: 3;
  }

  .spot-actions {
    flex-direction: row;
  }
}
</style>
