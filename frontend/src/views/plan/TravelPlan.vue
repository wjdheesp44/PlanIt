<template>
  <div class="planner-folder-page">
    <!-- 토스트 알림 -->
    <transition name="toast">
      <div v-if="toast.show" class="toast" :class="`toast-${toast.type}`">
        <svg
          v-if="toast.type === 'success'"
          width="20"
          height="20"
          viewBox="0 0 20 20"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M16.6668 5L7.50016 14.1667L3.3335 10"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <svg
          v-else-if="toast.type === 'error'"
          width="20"
          height="20"
          viewBox="0 0 20 20"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M10 18C14.4183 18 18 14.4183 18 10C18 5.58172 14.4183 2 10 2C5.58172 2 2 5.58172 2 10C2 14.4183 5.58172 18 10 18Z"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M10 6V10"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M10 14H10.01"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <svg
          v-else
          width="20"
          height="20"
          viewBox="0 0 20 20"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M10 18C14.4183 18 18 14.4183 18 10C18 5.58172 14.4183 2 10 2C5.58172 2 2 5.58172 2 10C2 14.4183 5.58172 18 10 18Z"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M10 10V14"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M10 6H10.01"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <span>{{ toast.message }}</span>
      </div>
    </transition>

    <!-- 메모 편집 모달 -->
    <transition name="modal">
      <div v-if="editingSpot" class="modal-overlay" @click="closeEditModal">
        <div class="modal-content" @click.stop>
          <div class="modal-header">
            <h3>메모 편집</h3>
            <button class="modal-close" @click="closeEditModal">
              <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path
                  d="M18 6L6 18M6 6L18 18"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </button>
          </div>
          <div class="modal-body">
            <div class="spot-preview">
              <img :src="editingSpot.image" :alt="editingSpot.name" />
              <div class="spot-preview-info">
                <h4>{{ editingSpot.name }}</h4>
                <p>{{ editingSpot.address }}</p>
              </div>
            </div>
            <div class="form-group">
              <label>메모</label>
              <textarea
                v-model="editMemo"
                placeholder="이 장소에 대한 메모를 입력하세요"
                rows="4"
              ></textarea>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn-cancel" @click="closeEditModal">취소</button>
            <button class="btn-save" @click="saveMemo">저장</button>
          </div>
        </div>
      </div>
    </transition>

    <!-- 메인 컨텐츠 -->
    <div class="main-content">
      <!-- 왼쪽 폴더 목록 사이드바 -->
      <FolderSidebar
        :folders="folders"
        :current-folder-id="currentFolderId"
        @select-folder="selectFolder"
        @error="handleImageError"
      />

      <!-- 중앙 스팟 목록 사이드바 -->
      <aside class="spot-sidebar" :style="{ width: sidebarWidth + 'px' }">
        <div class="sidebar-top">
          <!-- 검색 -->
          <div class="search-container">
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
              <input
                type="text"
                placeholder="스팟 검색"
                v-model="searchQuery"
                @input="handleSearchInput"
                @focus="handleSearchFocus"
              />
              <button v-if="searchQuery" class="clear-button" @click="clearSearch">
                <svg
                  width="16"
                  height="16"
                  viewBox="0 0 16 16"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M12 4L4 12M4 4L12 12"
                    stroke="#9CA3AF"
                    stroke-width="1.5"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
              </button>
            </div>

            <!-- 검색 결과 드롭다운 -->
            <div
              v-if="showSearchResults && searchQuery"
              class="search-results-dropdown"
              v-click-outside="closeSearchResults"
            >
              <div v-if="isSearching" class="search-loading">
                <div class="spinner"></div>
                <span>검색 중...</span>
              </div>

              <div v-else-if="searchResults.length === 0" class="search-empty">
                <svg
                  width="48"
                  height="48"
                  viewBox="0 0 48 48"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M24 44C35.0457 44 44 35.0457 44 24C44 12.9543 35.0457 4 24 4C12.9543 4 4 12.9543 4 24C4 35.0457 12.9543 44 24 44Z"
                    stroke="#D1D5DB"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M24 16V24"
                    stroke="#D1D5DB"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                  <path
                    d="M24 32H24.02"
                    stroke="#D1D5DB"
                    stroke-width="2"
                    stroke-linecap="round"
                    stroke-linejoin="round"
                  />
                </svg>
                <p>검색 결과가 없습니다</p>
              </div>

              <div v-else class="search-results-list">
                <div
                  v-for="result in searchResults"
                  :key="result.id"
                  class="search-result-item"
                  @click="addSearchResult(result)"
                >
                  <div class="result-thumbnail">
                    <img
                      :src="result.image || placeholderImage"
                      :alt="result.name"
                      @error="handleImageError"
                    />
                  </div>
                  <div class="result-info">
                    <div class="result-header">
                      <h4 class="result-name">{{ result.name }}</h4>
                      <span class="result-badge" :class="getBadgeClass(result.category)">
                        {{ getCategoryLabel(result.category) }}
                      </span>
                    </div>
                    <p class="result-address">{{ result.address }}</p>
                    <div class="result-meta">
                      <span class="result-likes">
                        <svg
                          width="30"
                          height="30"
                          viewBox="0 -4 10 30"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            d="M1.0002 17.0834C9.90016 17.0834 9.80433 17.0542 9.72516 16.9917L4.25016 12.5667C3.79183 12.1834 3.41266 11.7126 3.13516 11.1792C2.85766 10.6459 2.68766 10.0626 2.63516 9.46258C2.58266 8.86258 2.64933 8.25841 2.83099 7.68758C3.01266 7.11675 3.30516 6.59175 3.69183 6.14591C4.47099 5.25425 5.54599 4.75008 6.67516 4.75008C7.45433 4.75008 8.20849 4.96258 8.87099 5.36675C9.20433 5.57091 9.51266 5.82925 9.79183 6.13758C9.87933 6.23341 9.96683 6.33341 10.0502 6.43758C10.1335 6.33341 10.221 6.23341 10.3085 6.13758C10.5877 5.82925 10.896 5.57091 11.2293 5.36675C11.8918 4.96258 12.646 4.75008 13.4252 4.75008C14.5543 4.75008 15.6293 5.25425 16.4085 6.14591C16.7952 6.59175 17.0877 7.11675 17.2693 7.68758C17.451 8.25841 17.5177 8.86258 17.4652 9.46258C17.4127 10.0626 17.2427 10.6459 16.9652 11.1792C16.6877 11.7126 16.3085 12.1834 15.8502 12.5667L10.3752 16.9917C10.296 17.0542 10.2002 17.0834 10.0002 17.0834Z"
                            fill="#FB2C36"
                          />
                        </svg>
                        {{ result.likes || 0 }}
                      </span>
                      <span class="result-rating" v-if="result.rating">
                        ⭐ {{ result.rating }}
                      </span>
                    </div>
                  </div>
                  <button class="add-button">
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
                </div>
              </div>
            </div>
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
                      {{ getCategoryLabel(spot.category) }}
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
                  <button class="action-button edit" @click="openEditModal(spot)">
                    <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                      <path
                        d="M14.1667 2.49993C14.3856 2.28106 14.6454 2.10744 14.9314 1.98899C15.2173 1.87054 15.5238 1.80957 15.8334 1.80957C16.1429 1.80957 16.4494 1.87054 16.7353 1.98899C17.0213 2.10744 17.2811 2.28106 17.5 2.49993C17.7189 2.7188 17.8925 2.97863 18.011 3.2646C18.1294 3.55057 18.1904 3.85706 18.1904 4.16659C18.1904 4.47612 18.1294 4.78262 18.011 5.06859C17.8925 5.35455 17.7189 5.61439 17.5 5.83326L6.25002 17.0833L1.66669 18.3333L2.91669 13.7499L14.1667 2.49993Z"
                        stroke="#99A1AF"
                        stroke-width="1.66667"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      />
                    </svg>
                  </button>

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
                        d="M10.0002 17.0834C9.90016 17.0834 9.80433 17.0542 9.72516 16.9917L4.25016 12.5667C3.79183 12.1834 3.41266 11.7126 3.13516 11.1792C2.85766 10.6459 2.68766 10.0626 2.63516 9.46258C2.58266 8.86258 2.64933 8.25841 2.83099 7.68758C3.01266 7.11675 3.30516 6.59175 3.69183 6.14591C4.47099 5.25425 5.54599 4.75008 6.67516 4.75008C7.45433 4.75008 8.20849 4.96258 8.87099 5.36675C9.20433 5.57091 9.51266 5.82925 9.79183 6.13758C9.87933 6.23341 9.96683 6.33341 10.0502 6.43758C10.1335 6.33341 10.221 6.23341 10.3085 6.13758C10.5877 5.82925 10.896 5.57091 11.2293 5.36675C11.8918 4.96258 12.646 4.75008 13.4252 4.75008C14.5543 4.75008 15.6293 5.25425 16.4085 6.14591C16.7952 6.59175 17.0877 7.11675 17.2693 7.68758C17.451 8.25841 17.5177 8.86258 17.4652 9.46258C17.4127 10.0626 17.2427 10.6459 16.9652 11.1792C16.6877 11.7126 16.3085 12.1834 15.8502 12.5667L10.3752 16.9917C10.296 17.0542 10.2002 17.0834 10.0002 17.0834Z"
                        :fill="spot.isFavorite ? '#FB2C36' : 'none'"
                        :stroke="spot.isFavorite ? '#FB2C36' : '#99A1AF'"
                        stroke-width="1.5"
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
// script 부분만 수정 (기존 template과 style은 동일)

import { ref, onMounted, onUnmounted, nextTick, watch, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import FolderSidebar from "@/components/plan/FolderSiderbar.vue";
import { folderApi } from "@/api/plan/folderApi.js";

import { searchSpots } from "@/api/plan/spotResearchApi";
import { addSpotToGroup, getGroupSpots, updatePlan, deletePlan } from "@/api/plan/planApi";

const router = useRouter();
const route = useRoute();

const loading = ref(true);
const error = ref(null);

// const currentGroupId = ref(Number(route.params.id) || 1);
const searchQuery = ref("");
const newComment = ref("");
const showSearchResults = ref(false);
const isSearching = ref(false);
const searchResults = ref([]);
const toast = ref({
  show: false,
  message: "",
  type: "success",
});
const editingSpot = ref(null);
const editMemo = ref("");

let searchTimeout = null;

const sidebarWidth = ref(660);
const isResizing = ref(false);

let map = null;
const markers = ref([]);

const currentGroupId = computed(() => {
  const id = Number(route.params.id);
  return Number.isFinite(id) ? id : null;
});

const placeholderImage =
  "https://img.freepik.com/premium-vector/default-image-icon-vector-missing-picture-page-website-design-mobile-app-no-photo-available_87543-11093.jpg";

const handleImageError = (event) => {
  event.target.src = placeholderImage;
};

// 외부 클릭 감지 디렉티브
const vClickOutside = {
  mounted(el, binding) {
    el.clickOutsideEvent = (event) => {
      const searchContainer = el.closest(".search-container") || el;
      if (!(searchContainer === event.target || searchContainer.contains(event.target))) {
        binding.value();
      }
    };
    setTimeout(() => {
      document.addEventListener("click", el.clickOutsideEvent);
    }, 0);
  },
  unmounted(el) {
    document.removeEventListener("click", el.clickOutsideEvent);
  },
};

// 토스트 알림 표시
const showToast = (message, type = "success") => {
  toast.value = {
    show: true,
    message,
    type,
  };

  setTimeout(() => {
    toast.value.show = false;
  }, 3000);
};

// 검색 입력 핸들러 (디바운싱)
const handleSearchInput = () => {
  if (searchTimeout) {
    clearTimeout(searchTimeout);
  }

  if (!searchQuery.value.trim()) {
    searchResults.value = [];
    showSearchResults.value = false;
    return;
  }

  showSearchResults.value = true;

  searchTimeout = setTimeout(async () => {
    await performSearch();
  }, 300);
};

// 검색창 포커스 핸들러
const handleSearchFocus = async () => {
  await nextTick();
  if (searchQuery.value.trim()) {
    showSearchResults.value = true;
  }
};

// 검색 수행
const performSearch = async () => {
  if (!searchQuery.value.trim()) {
    searchResults.value = [];
    return;
  }

  try {
    isSearching.value = true;
    searchResults.value = [];

    const response = await searchSpots({
      types: ["ATTRACTION", "FESTIVAL", "POPUP"],
      search: searchQuery.value,
      sort: "LIKES_DESC",
      page: 1,
      size: 10,
    });

    console.log("검색 응답 전체:", response);

    if (response) {
      if (response.content) {
        searchResults.value = response.content;
      } else if (response.data?.content) {
        searchResults.value = response.data.content;
      } else if (Array.isArray(response.data)) {
        searchResults.value = response.data;
      } else if (Array.isArray(response)) {
        searchResults.value = response;
      } else {
        console.warn("예상치 못한 응답 구조:", response);
        searchResults.value = [];
      }
    }

    console.log("검색 결과:", searchResults.value);
  } catch (error) {
    console.error("검색 오류:", error);
    searchResults.value = [];

    if (error.code === "ERR_NETWORK" || error.message.includes("Network Error")) {
      showToast("서버에 연결할 수 없습니다. 서버가 실행 중인지 확인해주세요.", "error");
    } else if (error.response?.status === 404) {
      showToast("검색 API를 찾을 수 없습니다.", "error");
    } else if (error.code === "ECONNABORTED") {
      showToast("요청 시간이 초과되었습니다.", "error");
    } else {
      showToast("검색 중 오류가 발생했습니다.", "error");
    }
  } finally {
    isSearching.value = false;
  }
};

// 검색 결과 추가
const addSearchResult = async (result) => {
  try {
    // 이미 목록에 있는지 확인
    const exists = spots.value.some((spot) => spot.spotId === result.id);
    if (exists) {
      showToast("이미 추가된 스팟입니다.", "info");
      return;
    }

    // API 호출하여 그룹에 추가
    await addSpotToGroup(currentGroupId.value, result.id, "");

    // 스팟 목록 다시 불러오기
    await loadSpots();

    // 검색 초기화
    clearSearch();

    showToast("스팟이 추가되었습니다.", "success");
  } catch (error) {
    console.error("스팟 추가 오류:", error);
    if (error.response?.status === 400 && error.response?.data?.message?.includes("이미")) {
      showToast("이미 추가된 스팟입니다.", "info");
    } else {
      showToast("스팟 추가에 실패했습니다.", "error");
    }
  }
};

// 검색 초기화
const clearSearch = () => {
  searchQuery.value = "";
  searchResults.value = [];
  showSearchResults.value = false;
};

// 검색 결과 닫기
const closeSearchResults = () => {
  showSearchResults.value = false;
};

// 카테고리 라벨 변환
const getCategoryLabel = (category) => {
  const labels = {
    ATTRACTION: "관광지",
    POPUP: "팝업스토어",
    FESTIVAL: "축제",
    관광지: "관광지",
    팝업스토어: "팝업스토어",
    축제: "축제",
  };
  return labels[category] || category;
};

const startResize = (e) => {
  isResizing.value = true;
  document.addEventListener("mousemove", handleResize);
  document.addEventListener("mouseup", stopResize);
};

const handleResize = (e) => {
  if (!isResizing.value) return;

  const newWidth = e.clientX - 130;

  if (newWidth >= 400 && newWidth <= 1200) {
    sidebarWidth.value = newWidth;
  }
};

const stopResize = () => {
  isResizing.value = false;
  document.removeEventListener("mousemove", handleResize);
  document.removeEventListener("mouseup", stopResize);
};

// 폴더 목록 (임시 데이터 - 추후 API 연동)
const folders = ref([]);

// 스팟 목록
const spots = ref([]);

// 댓글 목록 (임시 데이터 - 추후 API 연동)
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

// 스팟 목록 불러오기
const loadSpots = async () => {
  try {
    const response = await getGroupSpots(currentGroupId.value);

    console.log("스팟 목록 응답:", response);

    // API 응답 구조에 맞게 데이터 매핑
    const spotsData = response.data || [];

    spots.value = spotsData.map((spot) => ({
      id: spot.id, // plan ID
      spotId: spot.spotId, // spot ID
      name: spot.name,
      category: spot.badge, // 한글로 이미 변환됨 ("관광지", "축제", "팝업스토어")
      address: spot.location,
      image: spot.image || placeholderImage,
      memo: spot.memo || "",
      isFavorite: spot.isFavorite || false,
      latitude: spot.latitude,
      longitude: spot.longitude,
      sortOrder: spot.sortOrder,
    }));

    // 지도 마커 업데이트
    updateMarkers();

    console.log("스팟 목록 로드 완료:", spots.value);
  } catch (error) {
    console.error("스팟 목록 로드 실패:", error);
    showToast("스팟 목록을 불러오는데 실패했습니다.", "error");
  }
};

// 폴더 선택
const selectFolder = (folderId) => {
  router.push(`/plans/${folderId}`);
};

// 배지 클래스
const getBadgeClass = (category) => {
  const classes = {
    ATTRACTION: "badge-tourist",
    POPUP: "badge-popup",
    FESTIVAL: "badge-festival",
    관광지: "badge-tourist",
    팝업스토어: "badge-popup",
    축제: "badge-festival",
  };
  return classes[category] || "";
};

// 스팟 이동 (위로)
const moveSpotUp = async (index) => {
  if (index === 0) return;

  const spot = spots.value[index];
  const newOrder = index; // 배열 인덱스 기반이므로 index가 새로운 순서

  try {
    await updatePlan(currentGroupId.value, spot.id, {
      sortOrder: newOrder,
    });

    // 목록 다시 불러오기
    await loadSpots();

    showToast("순서가 변경되었습니다.", "success");
  } catch (error) {
    console.error("순서 변경 실패:", error);
    showToast("순서 변경에 실패했습니다.", "error");
  }
};

// 스팟 이동 (아래로)
const moveSpotDown = async (index) => {
  if (index >= spots.value.length - 1) return;

  const spot = spots.value[index];
  const newOrder = index + 2; // 배열 인덱스 기반이므로 +2

  try {
    await updatePlan(currentGroupId.value, spot.id, {
      sortOrder: newOrder,
    });

    // 목록 다시 불러오기
    await loadSpots();

    showToast("순서가 변경되었습니다.", "success");
  } catch (error) {
    console.error("순서 변경 실패:", error);
    showToast("순서 변경에 실패했습니다.", "error");
  }
};

// 편집 모달 열기
const openEditModal = (spot) => {
  editingSpot.value = { ...spot };
  editMemo.value = spot.memo || "";
};

// 편집 모달 닫기
const closeEditModal = () => {
  editingSpot.value = null;
  editMemo.value = "";
};

// 메모 저장
const saveMemo = async () => {
  if (!editingSpot.value) return;

  try {
    await updatePlan(currentGroupId.value, editingSpot.value.id, {
      memo: editMemo.value,
    });

    // 목록 다시 불러오기
    await loadSpots();

    closeEditModal();
    showToast("메모가 저장되었습니다.", "success");
  } catch (error) {
    console.error("메모 저장 실패:", error);
    showToast("메모 저장에 실패했습니다.", "error");
  }
};

// 좋아요 토글 (추후 API 연동)
const toggleFavorite = (spot) => {
  spot.isFavorite = !spot.isFavorite;
  // TODO: 좋아요 API 호출
  showToast(spot.isFavorite ? "좋아요에 추가되었습니다." : "좋아요가 취소되었습니다.", "success");
};

// 스팟 삭제
const deleteSpot = async (planId) => {
  if (!confirm("이 스팟을 삭제하시겠습니까?")) return;

  try {
    await deletePlan(currentGroupId.value, planId);

    // 목록 다시 불러오기
    await loadSpots();

    showToast("스팟이 삭제되었습니다.", "success");
  } catch (error) {
    console.error("스팟 삭제 실패:", error);
    showToast("스팟 삭제에 실패했습니다.", "error");
  }
};

// 댓글 추가 (추후 API 연동)
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
    // TODO: 댓글 추가 API 호출
  }
};

const initKakaoMap = () => {
  if (!window.kakao || !window.kakao.maps) {
    console.error("Kakao Maps SDK not loaded");
    return;
  }

  const container = document.getElementById("map");
  const options = {
    center: new window.kakao.maps.LatLng(35.1796, 129.0756),
    level: 5,
  };

  map = new window.kakao.maps.Map(container, options);
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

onMounted(async () => {
  // 스팟 목록 불러오기
  // await loadSpots();
  await fetchFolders();
  await fetchByFolder();

  // 카카오 지도 초기화
  if (window.kakao && window.kakao.maps) {
    window.kakao.maps.load(() => {
      initKakaoMap();
    });
  } else {
    console.error("Kakao Maps SDK not loaded. Please add the script to index.html");
  }
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

const fetchByFolder = async () => {
  const id = currentGroupId.value;
  if (id == null) {
    await loadSpots(); // 기본
    return;
  }
  await loadSpots(); // 폴더별
};

onUnmounted(() => {
  if (searchTimeout) {
    clearTimeout(searchTimeout);
  }
});

watch(
  () => route.params.id,
  async () => {
    console.log("route changed:", route.fullPath, route.params);
    await fetchByFolder();
  }
);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 검색 컨테이너 */
.search-container {
  position: relative;
}

/* 검색 박스 */
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
  padding: 0.75rem 3rem 0.75rem 3rem;
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

.clear-button {
  position: absolute;
  right: 1rem;
  width: 24px;
  height: 24px;
  background: transparent;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0;
  border-radius: 4px;
  transition: background 0.2s;
}

.clear-button:hover {
  background: #f3f4f6;
}

/* 검색 결과 드롭다운 */
.search-results-dropdown {
  position: absolute;
  top: calc(100% + 0.5rem);
  left: 0;
  right: 0;
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  max-height: 400px;
  overflow-y: auto;
  z-index: 1000;
}

/* 검색 로딩 */
.search-loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  gap: 1rem;
  color: #6b7280;
}

.spinner {
  width: 32px;
  height: 32px;
  border: 3px solid #e5e7eb;
  border-top-color: #2563eb;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
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
  gap: 1rem;
  color: #9ca3af;
}

.search-empty p {
  font-size: 14px;
}

/* 검색 결과 리스트 */
.search-results-list {
  padding: 0.5rem;
}

.search-result-item {
  display: flex;
  gap: 0.75rem;
  padding: 0.75rem;
  border-radius: 10px;
  cursor: pointer;
  transition: background 0.2s;
  align-items: center;
}

.search-result-item:hover {
  background: #f9fafb;
}

.search-result-item:hover .add-button {
  opacity: 1;
}

/* 검색 결과 썸네일 */
.result-thumbnail {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  overflow: hidden;
  flex-shrink: 0;
}

.result-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 검색 결과 정보 */
.result-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.375rem;
  min-width: 0;
}

.result-header {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.result-name {
  font-size: 15px;
  font-weight: 500;
  color: #111827;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.result-badge {
  padding: 3px 8px;
  border-radius: 6px;
  font-size: 11px;
  font-weight: 500;
  white-space: nowrap;
  flex-shrink: 0;
}

.result-address {
  font-size: 13px;
  color: #6b7280;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.result-meta {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 13px;
}

.result-likes {
  display: flex;
  align-items: center;
  gap: 0.25rem;
  color: #6b7280;
}

.result-rating {
  color: #6b7280;
}

/* 추가 버튼 */
.add-button {
  width: 36px;
  height: 36px;
  background: #2563eb;
  color: white;
  border: none;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  flex-shrink: 0;
  opacity: 0;
  transition: all 0.2s;
}

.add-button:hover {
  background: #1e40af;
  transform: scale(1.05);
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

.planner-folder-page {
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  min-height: 100vh;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  position: relative;
}

/* 토스트 알림 */
.toast {
  position: fixed;
  top: 2rem;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  font-size: 14px;
  font-weight: 500;
  z-index: 9999;
  min-width: 300px;
  max-width: 500px;
}

.toast-success {
  background: #10b981;
  color: white;
}

.toast-error {
  background: #ef4444;
  color: white;
}

.toast-info {
  background: #3b82f6;
  color: white;
}

/* 토스트 애니메이션 */
.toast-enter-active {
  animation: toast-in 0.3s ease-out;
}

.toast-leave-active {
  animation: toast-out 0.3s ease-in;
}

@keyframes toast-in {
  from {
    opacity: 0;
    transform: translateX(-50%) translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(-50%) translateY(0);
  }
}

@keyframes toast-out {
  from {
    opacity: 1;
    transform: translateX(-50%) translateY(0);
  }
  to {
    opacity: 0;
    transform: translateX(-50%) translateY(-20px);
  }
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

/* 편집 버튼 스타일 */
.action-button.edit {
  color: #6b7280;
}

.action-button.edit:hover {
  background: #f3f4f6;
  color: #2563eb;
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
  z-index: 10000;
}

/* 모달 컨텐츠 */
.modal-content {
  background: white;
  border-radius: 16px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
}

/* 모달 헤더 */
.modal-header {
  padding: 1.5rem;
  border-bottom: 1px solid #e5e7eb;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1e1e1e;
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
  color: #6b7280;
  transition: all 0.2s;
}

.modal-close:hover {
  background: #f3f4f6;
  color: #1e1e1e;
}

/* 모달 바디 */
.modal-body {
  padding: 1.5rem;
  flex: 1;
  overflow-y: auto;
}

.spot-preview {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background: #f9fafb;
  border-radius: 12px;
  margin-bottom: 1.5rem;
}

.spot-preview img {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
}

.spot-preview-info h4 {
  font-size: 16px;
  font-weight: 600;
  color: #1e1e1e;
  margin-bottom: 0.25rem;
}

.spot-preview-info p {
  font-size: 14px;
  color: #6b7280;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.form-group label {
  font-size: 14px;
  font-weight: 500;
  color: #374151;
}

.form-group textarea {
  padding: 0.75rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 14px;
  font-family: inherit;
  resize: vertical;
  outline: none;
  transition: border-color 0.2s;
}

.form-group textarea:focus {
  border-color: #2563eb;
}

/* 모달 푸터 */
.modal-footer {
  padding: 1.5rem;
  border-top: 1px solid #e5e7eb;
  display: flex;
  gap: 0.75rem;
  justify-content: flex-end;
}

.btn-cancel,
.btn-save {
  padding: 0.625rem 1.25rem;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

.btn-cancel {
  background: #f3f4f6;
  color: #4b5563;
}

.btn-cancel:hover {
  background: #e5e7eb;
}

.btn-save {
  background: #2563eb;
  color: white;
}

.btn-save:hover {
  background: #1e40af;
}

/* 모달 애니메이션 */
.modal-enter-active {
  animation: modal-in 0.3s ease-out;
}

.modal-leave-active {
  animation: modal-out 0.2s ease-in;
}

@keyframes modal-in {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes modal-out {
  from {
    opacity: 1;
    transform: scale(1);
  }
  to {
    opacity: 0;
    transform: scale(0.95);
  }
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
