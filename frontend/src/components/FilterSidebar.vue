<template>
  <aside class="sidebar">
    <!-- 검색 결과 수 -->
    <div class="filter-section">
      <h3 class="filter-title">총 {{ totalCount }}건 검색됨</h3>
    </div>

    <!-- 여행 기간 -->
    <div class="filter-section">
      <h4 class="filter-label">여행 기간</h4>
      <div class="date-inputs">
        <input type="text" placeholder="YYYY.MM.DD" class="date-input" v-model="filters.dateFrom" />
        <span class="date-separator">~</span>
        <input type="text" placeholder="YYYY.MM.DD" class="date-input" v-model="filters.dateTo" />
      </div>
    </div>

    <!-- 날씨 -->
    <div class="filter-section">
      <h4 class="filter-label">날씨</h4>
      <div class="checkbox-group">
        <label class="checkbox-item">
          <input type="checkbox" v-model="filters.weather.clear" @change="handleFilterChange" />
          <span>맑음 or 흐림</span>
        </label>
        <label class="checkbox-item">
          <input type="checkbox" v-model="filters.weather.goodAir" @change="handleFilterChange" />
          <span>미세먼지 좋음</span>
        </label>
      </div>
    </div>

    <!-- 별점 -->
    <div class="filter-section">
      <h4 class="filter-label">별점</h4>
      <div class="checkbox-group">
        <label class="checkbox-item" v-for="star in 5" :key="star">
          <input type="checkbox" :checked="filters.stars[star]" @change="toggleStar(star)" />
          <span>{{ "⭐".repeat(6 - star) }}</span>
        </label>
      </div>
    </div>

    <!-- 좋아요수 -->
    <div class="filter-section">
      <h4 class="filter-label">좋아요수</h4>
      <div class="range-slider">
        <div class="range-values">{{ filters.likes }}-1000+</div>
        <input
          type="range"
          min="0"
          max="1000"
          v-model="filters.likes"
          class="slider"
          @input="handleFilterChange"
        />
      </div>
    </div>

    <!-- 정렬 -->
    <div class="filter-section">
      <h4 class="filter-label">정렬</h4>
      <div class="radio-group">
        <label class="radio-item">
          <input
            type="radio"
            name="sort"
            value="popular"
            v-model="filters.sort"
            @change="handleFilterChange"
          />
          <span>인기순</span>
        </label>
        <label class="radio-item">
          <input
            type="radio"
            name="sort"
            value="latest"
            v-model="filters.sort"
            @change="handleFilterChange"
          />
          <span>최신순</span>
        </label>
        <label class="radio-item">
          <input
            type="radio"
            name="sort"
            value="rating"
            v-model="filters.sort"
            @change="handleFilterChange"
          />
          <span>별점순</span>
        </label>
        <label class="radio-item">
          <input
            type="radio"
            name="sort"
            value="likes"
            v-model="filters.sort"
            @change="handleFilterChange"
          />
          <span>좋아요순</span>
        </label>
      </div>
    </div>

    <!-- 검색 및 태그 -->
    <div class="filter-section">
      <h4 class="filter-label">검색</h4>
      <div class="search-box">
        <input
          type="text"
          placeholder="검색어를 입력해주세요"
          class="search-input"
          v-model="searchInput"
          @keyup.enter="addTag"
        />
      </div>
      <div class="tags" v-if="filters.tags.length > 0">
        <span class="tag" v-for="(tag, index) in filters.tags" :key="index">
          {{ tag }}
          <button class="tag-close" @click="removeTag(index)">×</button>
        </span>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { ref, reactive, defineProps, defineEmits } from "vue";

const props = defineProps({
  totalCount: {
    type: Number,
    default: 100,
  },
});

const emit = defineEmits(["filter-change"]);

const searchInput = ref("");

const filters = reactive({
  dateFrom: "",
  dateTo: "",
  weather: {
    clear: true,
    goodAir: true,
  },
  stars: {
    1: true,
    2: true,
    3: true,
    4: true,
    5: true,
  },
  likes: 0,
  sort: "popular",
  tags: ["#서울", "#카페투어", "#감성"],
});

const toggleStar = (star) => {
  filters.stars[star] = !filters.stars[star];
  handleFilterChange();
};

const addTag = () => {
  if (searchInput.value.trim()) {
    const tag = searchInput.value.trim().startsWith("#")
      ? searchInput.value.trim()
      : `#${searchInput.value.trim()}`;
    if (!filters.tags.includes(tag)) {
      filters.tags.push(tag);
      searchInput.value = "";
      handleFilterChange();
    }
  }
};

const removeTag = (index) => {
  filters.tags.splice(index, 1);
  handleFilterChange();
};

const handleFilterChange = () => {
  emit("filter-change", filters);
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

.sidebar {
  background: #ffffff;
  border-radius: 8px;
  padding: 1.25rem;
  height: fit-content;
  position: sticky;
  top: 80px;
  border: 1px solid #e5e5e5;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
  font-family: "Noto Sans KR", -apple-system, BlinkMacSystemFont, sans-serif;
}

.filter-section {
  margin-bottom: 1.75rem;
  padding-bottom: 1.75rem;
  border-bottom: 1px solid #f0f0f0;
}

.filter-section:last-child {
  margin-bottom: 0;
  padding-bottom: 0;
  border-bottom: none;
}

.filter-title {
  font-size: 15px;
  font-weight: 500;
  color: #1e1e1e;
  margin: 0 0 0.875rem 0;
}

.filter-label {
  font-size: 14px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0 0 0.625rem 0;
}

.date-inputs {
  display: flex;
  flex-direction: column;
  gap: 0.375rem;
}

.date-input {
  padding: 0.375rem 0.5rem;
  border: 0.8px solid #d4d4d4;
  border-radius: 4px;
  font-size: 13px;
  color: #737373;
  width: 100%;
  font-family: inherit;
}

.date-input::placeholder {
  color: #a3a3a3;
}

.date-separator {
  text-align: center;
  color: #737373;
  font-size: 12px;
}

.checkbox-group,
.radio-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.checkbox-item,
.radio-item {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
  font-size: 14px;
  color: #1e1e1e;
}

.checkbox-item input[type="checkbox"],
.radio-item input[type="radio"] {
  width: 14px;
  height: 14px;
  cursor: pointer;
  accent-color: #2c2c2c;
}

.range-slider {
  padding: 0.375rem 0;
}

.range-values {
  text-align: right;
  font-size: 12px;
  color: #1e1e1e;
  margin-bottom: 0.375rem;
}

.slider {
  width: 100%;
  height: 6px;
  background: #e8e8ff;
  border-radius: 4px;
  outline: none;
  -webkit-appearance: none;
}

.slider::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 14px;
  height: 14px;
  background: #2c2c2c;
  border-radius: 50%;
  cursor: pointer;
}

.slider::-moz-range-thumb {
  width: 14px;
  height: 14px;
  background: #2c2c2c;
  border-radius: 50%;
  cursor: pointer;
  border: none;
}

.search-box {
  margin-bottom: 0.75rem;
}

.search-input {
  width: 100%;
  padding: 0.5rem;
  border: 0.8px solid #d4d4d4;
  border-radius: 4px;
  font-size: 13px;
  color: #737373;
  font-family: inherit;
}

.search-input::placeholder {
  color: #a3a3a3;
}

.tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.375rem;
}

.tag {
  background: #f5f5f5;
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 12px;
  color: #1e1e1e;
  display: inline-flex;
  align-items: center;
  gap: 0.375rem;
  border: 1px solid #e0e0e0;
}

.tag-close {
  background: none;
  border: none;
  font-size: 14px;
  cursor: pointer;
  padding: 0;
  line-height: 1;
  color: #1e1e1e;
  transition: color 0.2s ease;
}

.tag-close:hover {
  color: #ef4444;
}

/* 반응형 */
@media (max-width: 1024px) {
  .sidebar {
    position: static;
  }
}

@media (max-width: 768px) {
  .sidebar {
    padding: 1rem;
  }

  .filter-section {
    margin-bottom: 1.5rem;
    padding-bottom: 1.5rem;
  }
}
</style>
