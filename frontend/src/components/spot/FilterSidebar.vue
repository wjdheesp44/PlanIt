<template>
  <aside class="sidebar">
    <!-- 검색 결과 수 -->
    <div class="filter-section">
      <h3 class="filter-title">총 {{ totalCount }}건 검색됨</h3>
    </div>

    <!-- 여행 기간 -->
    <!-- <div class="filter-section">
      <h4 class="filter-label">여행 기간</h4>
      <div class="date-inputs">
        <input
          type="date"
          class="date-input"
          v-model="filters.dateFrom"
          @change="handleFilterChange"
        />
        <span class="date-separator">~</span>
        <input
          type="date"
          class="date-input"
          v-model="filters.dateTo"
          @change="handleFilterChange"
        />
      </div>
    </div> -->
    <!-- 여행 기간 -->
    <div class="filter-section">
      <h4 class="filter-label">여행 기간</h4>
      <DateRangePicker
        v-model:startDate="filters.dateFrom"
        v-model:endDate="filters.dateTo"
        @change="handleDateRangeChange"
      />
    </div>

    <!-- 날씨 -->
    <div class="filter-section">
      <h4 class="filter-label">날씨</h4>
      <div class="checkbox-group">
        <label
          class="checkbox-item"
          :class="{ disabled: !isWeatherFilterAvailable }"
          :title="!isWeatherFilterAvailable ? '여행 기간을 먼저 선택해주세요 (오늘 ~ 9일 후)' : ''"
        >
          <input
            type="checkbox"
            v-model="filters.weather.clear"
            @change="handleFilterChange"
            :disabled="!isWeatherFilterAvailable"
          />
          <span>맑음</span>
        </label>
        <label
          class="checkbox-item"
          :class="{ disabled: !isWeatherFilterAvailable }"
          :title="!isWeatherFilterAvailable ? '여행 기간을 먼저 선택해주세요 (오늘 ~ 9일 후)' : ''"
        >
          <input
            type="checkbox"
            v-model="filters.weather.cloudy"
            @change="handleFilterChange"
            :disabled="!isWeatherFilterAvailable"
          />
          <span>흐림</span>
        </label>
        <label
          class="checkbox-item"
          :class="{ disabled: !isWeatherFilterAvailable }"
          :title="!isWeatherFilterAvailable ? '여행 기간을 먼저 선택해주세요 (오늘 ~ 9일 후)' : ''"
        >
          <input
            type="checkbox"
            v-model="filters.weather.rain"
            @change="handleFilterChange"
            :disabled="!isWeatherFilterAvailable"
          />
          <span>비</span>
        </label>
        <label
          class="checkbox-item"
          :class="{ disabled: !isWeatherFilterAvailable }"
          :title="!isWeatherFilterAvailable ? '여행 기간을 먼저 선택해주세요 (오늘 ~ 9일 후)' : ''"
        >
          <input
            type="checkbox"
            v-model="filters.weather.snow"
            @change="handleFilterChange"
            :disabled="!isWeatherFilterAvailable"
          />
          <span>눈</span>
        </label>
      </div>
      <p v-if="!isWeatherFilterAvailable" class="filter-hint">
        * 날씨 필터는 오늘부터 9일 이내의 여행 기간에만 사용 가능합니다
      </p>
    </div>

    <!-- 지역 -->
    <div class="filter-section">
      <h4 class="filter-label">지역</h4>
      <div class="region-select">
        <!-- 시도 선택 -->
        <select v-model="selectedSido" @change="handleSidoChange" class="region-dropdown">
          <option value="">시/도 선택</option>
          <option value="all">전체</option>
          <option v-for="region in regions" :key="region.id" :value="region.id">
            {{ region.sidoName }}
          </option>
        </select>

        <!-- 구군 선택 (시도 선택 시 표시) -->
        <select
          v-if="selectedSido && selectedSido !== 'all' && currentGuguns.length > 0"
          v-model="selectedGugun"
          @change="handleGugunChange"
          class="region-dropdown"
        >
          <option value="">구/군 선택</option>
          <option value="all">전체</option>
          <option v-for="gugun in currentGuguns" :key="gugun.id" :value="gugun.id">
            {{ gugun.gugunName }}
          </option>
        </select>
      </div>

      <!-- 선택된 지역 태그 -->
      <div class="selected-regions" v-if="filters.selectedRegions.length > 0">
        <span class="region-tag" v-for="(region, index) in filters.selectedRegions" :key="index">
          {{ region.sidoName }} {{ region.gugunName }}
          <button class="tag-close" @click="removeRegion(index)">×</button>
        </span>
      </div>
    </div>

    <!-- 별점 -->
    <div class="filter-section">
      <h4 class="filter-label">별점</h4>
      <div class="checkbox-group">
        <label class="checkbox-item" v-for="star in 5" :key="star">
          <input type="checkbox" :checked="filters.stars[star]" @change="toggleStar(star)" />
          <span class="star-icons">
            <svg
              v-for="n in 6 - star"
              :key="n"
              width="14"
              height="14"
              viewBox="0 -2 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M9.604 1.912c.036-.073.093-.135.163-.179.07-.043.15-.066.233-.066.082 0 .163.023.233.066.07.044.126.106.163.179l1.925 3.9c.127.256.314.478.546.646.231.169.5.278.783.32l4.305.63c.065.012.128.046.191.099.063.053.11.123.136.201.025.078.028.162.008.242-.018.08-.06.153-.119.211l-3.113 3.032c-.195.2-.339.447-.427.72-.089.272-.106.562-.058.844l.735 4.283c.011.065.004.133-.021.196-.025.063-.067.117-.134.165-.067.049-.146.078-.229.084-.083.005-.166-.009-.239-.048l-3.849-2.023c-.243-.133-.525-.203-.812-.203-.287 0-.569.07-.812.203l-3.849 2.023c-.071.039-.154.053-.226.047-.073-.006-.152-.035-.219-.084-.067-.048-.108-.115-.139-.192-.03-.076-.039-.16-.024-.225l.735-4.283c.038-.282.017-.572-.072-.844-.089-.273-.243-.52-.448-.72L1.8 8.163c-.048-.057-.08-.13-.1-.21-.02-.08-.018-.164.007-.243.021-.079.068-.148.131-.202.064-.053.14-.087.212-.096l4.305-.63c.283-.042.552-.131.783-.3.232-.168.419-.39.546-.646l1.925-3.9z"
                fill="#FBBF24"
                stroke="#FBBF24"
                stroke-width="1.667"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </span>
        </label>
      </div>
    </div>

    <!-- 좋아요수 -->
    <div class="filter-section">
      <h4 class="filter-label">좋아요수</h4>
      <div class="range-slider">
        <div class="range-values">{{ filters.likes }}-100+</div>
        <input
          type="range"
          min="0"
          max="100"
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
          placeholder="#태그 또는 검색어를 입력해주세요"
          class="search-input"
          v-model="searchInput"
          @keyup.enter="addTag"
        />
      </div>

      <!-- 태그 (#으로 시작) -->
      <div class="tags" v-if="filters.tags.length > 0">
        <div class="tag-label">태그</div>
        <span class="tag tag-type" v-for="(tag, index) in filters.tags" :key="`tag-${index}`">
          {{ tag }}
          <button class="tag-close" @click="removeTag(index)">×</button>
        </span>
      </div>

      <!-- 검색어 (일반 텍스트, 하나만) -->
      <div class="tags" v-if="filters.searchTerm">
        <div class="tag-label">검색어</div>
        <span class="tag search-type">
          {{ filters.searchTerm }}
          <button class="tag-close" @click="clearSearchTerm">×</button>
        </span>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from "vue";
import DateRangePicker from "@/components/spot/DateRangePicker.vue";

const props = defineProps({
  totalCount: {
    type: Number,
    default: 100,
  },
});

const emit = defineEmits(["filter-change"]);

const searchInput = ref("");
const regions = ref([]);
const selectedSido = ref("");
const selectedGugun = ref("");

const filters = reactive({
  dateFrom: null,
  dateTo: null,
  weather: {
    clear: false, // 맑음
    cloudy: false, // 흐림
    rain: false, // 비
    snow: false, // 눈
  },
  selectedRegions: [], // 선택된 지역들
  stars: {
    1: true,
    2: true,
    3: true,
    4: true,
    5: true,
  },
  likes: 0,
  sort: "popular",
  tags: [], // 태그 (#으로 시작)
  searchTerm: "", // 일반 검색어 (하나만)
});

// 날씨 필터 사용 가능 여부 계산
const isWeatherFilterAvailable = computed(() => {
  if (!filters.dateFrom || !filters.dateTo) {
    return false;
  }

  const today = new Date();
  today.setHours(0, 0, 0, 0);

  const nineDaysLater = new Date();
  nineDaysLater.setDate(today.getDate() + 9);
  nineDaysLater.setHours(23, 59, 59, 999);

  const startDate = new Date(filters.dateFrom);
  const endDate = new Date(filters.dateTo);

  // 여행 시작일이 오늘 ~ 9일 후 사이에 있는지 확인
  // 또는 여행 기간이 오늘 ~ 9일 후 범위와 겹치는지 확인
  const isStartInRange = startDate >= today && startDate <= nineDaysLater;
  const isEndInRange = endDate >= today && endDate <= nineDaysLater;
  const isOverlapping = startDate <= nineDaysLater && endDate >= today;

  return isStartInRange || isEndInRange || isOverlapping;
});

const handleDateRangeChange = ({ startDate, endDate }) => {
  console.log("날짜 범위 변경:", { startDate, endDate });
  filters.dateFrom = startDate;
  filters.dateTo = endDate;

  // 날씨 필터가 비활성화되면 선택 초기화
  if (!isWeatherFilterAvailable.value) {
    filters.weather.clear = false;
    filters.weather.cloudy = false;
    filters.weather.rain = false;
    filters.weather.snow = false;
  }

  handleFilterChange();
};


// 현재 선택된 시도의 구군 목록
const currentGuguns = computed(() => {
  if (!selectedSido.value) return [];
  const region = regions.value.find((r) => r.id === selectedSido.value);
  return region ? region.guguns : [];
});

// API에서 지역 데이터 가져오기
const fetchRegions = async () => {
  try {
    const response = await fetch("http://localhost:8080/api/v1/regions");
    const data = await response.json();
    regions.value = data;
  } catch (error) {
    console.error("Failed to fetch regions:", error);
  }
};

// 시도 선택 시
const handleSidoChange = () => {
  selectedGugun.value = ""; // 구군 선택 초기화

  // 시도에서 "전체" 선택 시 바로 추가
  if (selectedSido.value === "all") {
    const allSidoIds = regions.value.map((r) => r.id);

    const newRegion = {
      sidoId: "all",
      sidoName: "전체",
      gugunId: null,
      gugunName: "",
      allSidoIds: allSidoIds, // 모든 시도 ID 포함
    };

    // 중복 체크
    const exists = filters.selectedRegions.some((r) => r.sidoId === "all");

    if (!exists) {
      filters.selectedRegions.push(newRegion);
      handleFilterChange();
    }

    // 선택 초기화
    selectedSido.value = "";
  }
};

// 구군 선택 시
const handleGugunChange = () => {
  if (!selectedSido.value || !selectedGugun.value) return;

  const sidoRegion = regions.value.find((r) => r.id === selectedSido.value);

  if (!sidoRegion) return;

  // 구군에서 "전체" 선택 시
  if (selectedGugun.value === "all") {
    const allGugunIds = sidoRegion.guguns.map((g) => g.id);

    const newRegion = {
      sidoId: sidoRegion.id,
      sidoName: sidoRegion.sidoName,
      gugunId: "all",
      gugunName: "전체",
      allGugunIds: allGugunIds, // 해당 시도의 모든 구군 ID 포함
    };

    // 중복 체크
    const exists = filters.selectedRegions.some(
      (r) => r.sidoId === newRegion.sidoId && r.gugunId === "all"
    );

    if (!exists) {
      filters.selectedRegions.push(newRegion);
      handleFilterChange();
    }
  } else {
    // 특정 구군 선택
    const gugunRegion = sidoRegion.guguns.find((g) => g.id === selectedGugun.value);

    if (gugunRegion) {
      const newRegion = {
        sidoId: sidoRegion.id,
        sidoName: sidoRegion.sidoName,
        gugunId: gugunRegion.id,
        gugunName: gugunRegion.gugunName,
      };

      // 중복 체크
      const exists = filters.selectedRegions.some(
        (r) => r.sidoId === newRegion.sidoId && r.gugunId === newRegion.gugunId
      );

      if (!exists) {
        filters.selectedRegions.push(newRegion);
        handleFilterChange();
      }
    }
  }

  // 선택 초기화
  selectedSido.value = "";
  selectedGugun.value = "";
};

// 지역 제거
const removeRegion = (index) => {
  filters.selectedRegions.splice(index, 1);
  handleFilterChange();
};

const toggleStar = (star) => {
  filters.stars[star] = !filters.stars[star];
  handleFilterChange();
};

const addTag = () => {
  if (searchInput.value.trim()) {
    const input = searchInput.value.trim();

    // #으로 시작하면 태그로 추가
    if (input.startsWith("#")) {
      if (!filters.tags.includes(input)) {
        filters.tags.push(input);
        searchInput.value = "";
        handleFilterChange();
      }
    } else {
      // 아니면 검색어로 설정 (덮어쓰기)
      filters.searchTerm = input;
      searchInput.value = "";
      handleFilterChange();
    }
  }
};

const removeTag = (index) => {
  filters.tags.splice(index, 1);
  handleFilterChange();
};

const clearSearchTerm = () => {
  filters.searchTerm = "";
  handleFilterChange();
};

const handleFilterChange = () => {
  emit("filter-change", filters);
};

// 컴포넌트 마운트 시 지역 데이터 로드
onMounted(() => {
  fetchRegions();
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

.star-icons {
  display: inline-flex;
  gap: 2px;
  align-items: center;
}

.star-icons svg {
  width: 20px !important;
  height: 20px !important;
  flex-shrink: 0;
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
  z-index: 101;
}

.filter-section {
  margin-bottom: 1.75rem;
  padding-bottom: 1.75rem;
  border-bottom: 1px solid #f0f0f0;
  position: relative;
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
  padding: 0.5rem;
  border: 0.8px solid #d4d4d4;
  border-radius: 4px;
  font-size: 13px;
  color: #1e1e1e;
  width: 100%;
  font-family: inherit;
  cursor: pointer;
}

.date-input::-webkit-calendar-picker-indicator {
  cursor: pointer;
}

.date-input:focus {
  outline: none;
  border-color: #2c2c2c;
}

.date-separator {
  text-align: center;
  color: #737373;
  font-size: 12px;
}

/* 지역 선택 */
.region-select {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 0.75rem;
}

.region-dropdown {
  width: 100%;
  padding: 0.5rem;
  border: 0.8px solid #d4d4d4;
  border-radius: 4px;
  font-size: 13px;
  color: #1e1e1e;
  font-family: inherit;
  background: #ffffff;
  cursor: pointer;
}

.region-dropdown:focus {
  outline: none;
  border-color: #2c2c2c;
}

.selected-regions {
  display: flex;
  flex-wrap: wrap;
  gap: 0.375rem;
}

.region-tag {
  background: #f0f9ff;
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 12px;
  color: #0369a1;
  display: inline-flex;
  align-items: center;
  gap: 0.375rem;
  border: 1px solid #bae6fd;
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
  align-items: center;
  margin-top: 0.75rem;
}

.tags:first-of-type {
  margin-top: 0.75rem;
}

.tag-label {
  font-size: 11px;
  color: #6b7280;
  font-weight: 600;
  width: 100%;
  margin-bottom: 0.25rem;
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

.tag-type {
  background: #f0f9ff;
  color: #0369a1;
  border-color: #bae6fd;
}

.search-type {
  background: #fef3c7;
  color: #92400e;
  border-color: #fde68a;
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

.checkbox-item.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.checkbox-item.disabled input {
  cursor: not-allowed;
}

.checkbox-item.disabled span {
  color: #9ca3af;
}

.filter-hint {
  margin-top: 0.5rem;
  font-size: 11px;
  color: #ef4444;
  line-height: 1.4;
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
