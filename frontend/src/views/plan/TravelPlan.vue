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
      <aside class="spot-sidebar" :style="{ width: sidebarWidth + 'px' }">
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
                      width="16"
                      height="16"
                      viewBox="0 0 16 16"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M12 10L8 6L4 10"
                        :stroke="index === 0 ? '#D1D5DC' : '#6A7282'"
                        stroke-width="1.33333"
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
                      width="16"
                      height="16"
                      viewBox="0 0 16 16"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M4 6L8 10L12 6"
                        :stroke="index === spots.length - 1 ? '#D1D5DC' : '#6A7282'"
                        stroke-width="1.33333"
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
                        d="M1.6665 7.91662C1.66652 6.98929 1.94783 6.08377 2.47328 5.31967C2.99873 4.55557 3.7436 3.96883 4.60951 3.63695C5.47542 3.30507 6.42164 3.24366 7.32318 3.46082C8.22473 3.67799 9.03919 4.16352 9.659 4.85329C9.70266 4.89996 9.75544 4.93718 9.81407 4.96262C9.8727 4.98806 9.93593 5.00119 9.99984 5.00119C10.0637 5.00119 10.127 4.98806 10.1856 4.96262C10.2442 4.93718 10.297 4.89996 10.3407 4.85329C10.9585 4.15904 11.7732 3.66943 12.6762 3.44962C13.5792 3.22982 14.5277 3.29024 15.3956 3.62286C16.2634 3.95547 17.0093 4.5445 17.5341 5.31154C18.0589 6.07858 18.3376 6.98725 18.3332 7.91662C18.3332 9.82495 17.0832 11.25 15.8332 12.5L11.2565 16.9275C11.1012 17.1058 10.9098 17.249 10.6949 17.3477C10.48 17.4464 10.2465 17.4982 10.0101 17.4997C9.77362 17.5012 9.53954 17.4523 9.32341 17.3564C9.10728 17.2605 8.91403 17.1196 8.7565 16.9433L4.1665 12.5C2.9165 11.25 1.6665 9.83329 1.6665 7.91662Z"
                        :fill="spot.isFavorite ? '#FB2C36' : 'none'"
                        :stroke="spot.isFavorite ? '#FB2C36' : '#99A1AF'"
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
                        d="M8.3335 9.16675V14.1667"
                        stroke="#99A1AF"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                      <path
                        d="M11.6665 9.16675V14.1667"
                        stroke="#99A1AF"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                      <path
                        d="M15.8332 5V16.6667C15.8332 17.1087 15.6576 17.5326 15.345 17.8452C15.0325 18.1577 14.6085 18.3333 14.1665 18.3333H5.83317C5.39114 18.3333 4.96722 18.1577 4.65466 17.8452C4.3421 17.5326 4.1665 17.1087 4.1665 16.6667V5"
                        stroke="#99A1AF"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                      <path
                        d="M2.5 5H17.5"
                        stroke="#99A1AF"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                      <path
                        d="M6.6665 5.00008V3.33341C6.6665 2.89139 6.8421 2.46746 7.15466 2.1549C7.46722 1.84234 7.89114 1.66675 8.33317 1.66675H11.6665C12.1085 1.66675 12.5325 1.84234 12.845 2.1549C13.1576 2.46746 13.3332 2.89139 13.3332 3.33341V5.00008"
                        stroke="#99A1AF"
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
      <div class="sidebar-resizer" @mousedown="startResize"></div>

      <!-- 오른쪽 지도 영역 -->
      <div class="map-area">
        <div id="map" class="map-image"></div>

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
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import FolderSidebar from "@/components/plan/FolderSiderbar.vue";

const router = useRouter();
const route = useRoute();

const currentFolderId = ref(Number(route.params.id) || 1);
const searchQuery = ref("");
const newComment = ref("");

const sidebarWidth = ref(660); // 기본 너비
const isResizing = ref(false);

let map = null;
const markers = ref([]);

const startResize = (e) => {
  isResizing.value = true;
  document.addEventListener("mousemove", handleResize);
  document.addEventListener("mouseup", stopResize);
};

const handleResize = (e) => {
  if (!isResizing.value) return;

  const newWidth = e.clientX - 130; // 260은 왼쪽 폴더 사이드바 너비

  // 최소/최대 너비 제한
  if (newWidth >= 400 && newWidth <= 1200) {
    sidebarWidth.value = newWidth;
  }
};

const stopResize = () => {
  isResizing.value = false;
  document.removeEventListener("mousemove", handleResize);
  document.removeEventListener("mouseup", stopResize);
};

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
    latitude: 35.1587,
    longitude: 129.1603,
  },
  {
    id: 2,
    name: "광안리 해수욕장",
    category: "관광지",
    address: "부산 수영구 광안동",
    image: "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=200",
    memo: "",
    isFavorite: false,
    latitude: 35.1532,
    longitude: 129.1189,
  },
  {
    id: 3,
    name: "감천문화마을",
    category: "관광지",
    address: "부산 사하구 감천동",
    image: "https://images.unsplash.com/photo-1476514525535-07fb3b4ae5f1?w=200",
    memo: "경사가 있으니 편한 신발 추천",
    isFavorite: true,
    latitude: 35.0975,
    longitude: 129.0107,
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

const initKakaoMap = () => {
  if (!window.kakao || !window.kakao.maps) {
    console.error("Kakao Maps SDK not loaded");
    return;
  }

  const container = document.getElementById("map");
  const options = {
    center: new window.kakao.maps.LatLng(35.1796, 129.0756), // 부산 중심
    level: 5,
  };

  map = new window.kakao.maps.Map(container, options);

  // 스팟 목록 기반 마커 생성
  updateMarkers();
};

// 마커 업데이트
const updateMarkers = () => {
  if (!map) return;

  markers.value.forEach((marker) => marker.setMap(null));
  markers.value = [];

  spots.value.forEach((spot, index) => {
    if (!spot.latitude || !spot.longitude) return;

    const position = new window.kakao.maps.LatLng(spot.latitude, spot.longitude);

    // DOM 요소로 생성
    const markerElement = document.createElement("div");
    markerElement.style.cssText =
      "position: relative; display: flex; flex-direction: column; align-items: center;";

    const pinElement = document.createElement("div");
    pinElement.className = "marker-pin";
    pinElement.style.cssText = `
      width: 32px;
      height: 32px;
      background: #2563eb;
      color: white;
      border: 3px solid white;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      font-weight: 600;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
      cursor: pointer;
      transition: all 0.2s;
    `;
    pinElement.textContent = index + 1;

    const labelElement = document.createElement("div");
    labelElement.className = "marker-label";
    labelElement.style.cssText = `
      position: absolute;
      top: -40px;
      left: 50%;
      transform: translateX(-50%);
      background: white;
      color: #1e1e1e;
      padding: 6px 12px;
      border-radius: 8px;
      font-size: 13px;
      font-weight: 500;
      white-space: nowrap;
      box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
      opacity: 0;
      visibility: hidden;
      transition: all 0.2s;
      pointer-events: none;
      z-index: 1000;
    `;
    labelElement.textContent = spot.name;

    const arrow = document.createElement("div");
    arrow.style.cssText = `
      position: absolute;
      bottom: -6px;
      left: 50%;
      transform: translateX(-50%);
      width: 0;
      height: 0;
      border-left: 6px solid transparent;
      border-right: 6px solid transparent;
      border-top: 6px solid white;
    `;
    labelElement.appendChild(arrow);

    markerElement.appendChild(pinElement);
    markerElement.appendChild(labelElement);

    // 호버 이벤트
    markerElement.addEventListener("mouseenter", () => {
      pinElement.style.transform = "scale(1.1)";
      pinElement.style.background = "#1e40af";
      labelElement.style.opacity = "1";
      labelElement.style.visibility = "visible";
      labelElement.style.top = "-45px";
    });

    markerElement.addEventListener("mouseleave", () => {
      pinElement.style.transform = "scale(1)";
      pinElement.style.background = "#2563eb";
      labelElement.style.opacity = "0";
      labelElement.style.visibility = "hidden";
      labelElement.style.top = "-40px";
    });

    const customOverlay = new window.kakao.maps.CustomOverlay({
      position: position,
      content: markerElement,
      yAnchor: 0.5,
    });

    customOverlay.setMap(map);
    markers.value.push(customOverlay);
  });

  if (markers.value.length > 0) {
    const bounds = new window.kakao.maps.LatLngBounds();
    spots.value.forEach((spot) => {
      if (spot.latitude && spot.longitude) {
        bounds.extend(new window.kakao.maps.LatLng(spot.latitude, spot.longitude));
      }
    });
    map.setBounds(bounds);
  }
};

// 줌 컨트롤
const zoomIn = () => {
  if (map) {
    map.setLevel(map.getLevel() - 1);
  }
};

const zoomOut = () => {
  if (map) {
    map.setLevel(map.getLevel() + 1);
  }
};

// 컴포넌트 마운트 시 카카오 지도 초기화
onMounted(() => {
  // 카카오 지도 SDK 로드 확인 후 초기화
  if (window.kakao && window.kakao.maps) {
    window.kakao.maps.load(() => {
      initKakaoMap();
    });
  } else {
    console.error("Kakao Maps SDK not loaded. Please add the script to index.html");
  }
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 지도 */
.map-area {
  flex: 1;
  position: relative;
  background: #f3f4f6;
}

.map-image {
  width: 100%;
  height: 100%;
}

.zoom-controls {
  position: absolute;
  bottom: 2rem;
  right: 2rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  z-index: 10;
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

/* 커스텀 마커 스타일 */
:deep(.custom-marker) {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
}

:deep(.marker-pin) {
  width: 32px;
  height: 32px;
  background: #2563eb;
  color: white;
  border: 3px solid white;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  cursor: pointer;
  transition: all 0.2s;
}

:deep(.marker-pin:hover) {
  transform: scale(1.1);
  background: #1e40af;
}

:deep(.marker-label) {
  position: absolute;
  top: -35px;
  left: 50%;
  transform: translateX(-50%);
  background: white;
  color: #1e1e1e;
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 13px;
  font-weight: 500;
  white-space: nowrap;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
  opacity: 0;
  visibility: hidden;
  transition: all 0.2s;
  pointer-events: none;
  z-index: 1000;
}

:deep(.marker-label::after) {
  content: "";
  position: absolute;
  bottom: -6px;
  left: 50%;
  transform: translateX(-50%);
  width: 0;
  height: 0;
  border-left: 6px solid transparent;
  border-right: 6px solid transparent;
  border-top: 6px solid white;
}

:deep(.custom-marker:hover .marker-label) {
  opacity: 1;
  visibility: visible;
  top: -40px;
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
  min-width: 600px;
  max-width: 1200px;
  background: #ffffff;
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.sidebar-resizer {
  width: 4px;
  background: transparent;
  cursor: col-resize;
  position: relative;
  flex-shrink: 0;
  transition: background 0.2s;
}

.sidebar-resizer:hover {
  background: #e5e7eb;
}

.sidebar-resizer::before {
  content: "";
  position: absolute;
  left: -4px;
  right: -4px;
  top: 0;
  bottom: 0;
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
  gap: 0.5rem;
}

.spot-card {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 16px;
  padding: 1rem;
  display: flex;
  gap: 0.75rem;
  align-items: flex-start;
}

/* 순서 컨트롤 */
.order-control {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
  flex-shrink: 0;
  padding-top: 0.5rem;
}

.order-button {
  width: 24px;
  height: 24px;
  background: transparent;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0.25rem;
  border-radius: 4px;
  transition: all 0.2s;
}

.order-button:hover:not(:disabled) {
  background: #f3f4f6;
}

.order-button:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

.order-number {
  font-size: 12px;
  font-weight: 400;
  color: #6b7280;
  min-width: 16px;
  text-align: center;
}

/* 스팟 썸네일 */
.spot-thumbnail {
  width: 96px;
  height: 96px;
  border-radius: 10px;
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
  font-size: 16px;
  font-weight: 400;
  color: #111827;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.spot-badge {
  padding: 4px 8px;
  border-radius: 8px;
  font-size: 12px;
  font-weight: 400;
  white-space: nowrap;
  flex-shrink: 0;
}

.badge-tourist {
  background: #eef2ff;
  color: #4f46e5;
}

.badge-popup {
  background: #f5f3ff;
  color: #7c3aed;
}

.badge-festival {
  background: #fce7f3;
  color: #ec4899;
}

.spot-address {
  font-size: 14px;
  color: #6b7280;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.spot-memo {
  display: flex;
  gap: 0.5rem;
  font-size: 14px;
}

.memo-label {
  color: #6b7280;
  font-weight: 400;
  flex-shrink: 0;
}

.memo-text {
  color: #374151;
  flex: 1;
}

/* 액션 버튼 */
.spot-actions {
  display: flex;
  gap: 0.25rem;
  flex-shrink: 0;
}

.action-button {
  width: 36px;
  height: 36px;
  background: transparent;
  border: none;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  padding: 0.5rem;
}

.action-button:hover {
  background: #f9fafb;

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
