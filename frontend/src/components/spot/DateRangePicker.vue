<!-- src/components/common/DateRangePicker.vue -->
<template>
  <div class="date-range-picker">
    <div class="date-display" @click="toggleCalendar">
      <div class="date-field">
        <svg class="calendar-icon" width="20" height="20" viewBox="0 0 20 20" fill="none">
          <path
            d="M6 2V5M14 2V5M3 8H17M4 4H16C16.5523 4 17 4.44772 17 5V17C17 17.5523 16.5523 18 16 18H4C3.44772 18 3 17.5523 3 17V5C3 4.44772 3.44772 4 4 4Z"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
        <span class="date-text">{{ displayStartDate }}</span>
      </div>
      <span class="date-separator">~</span>
      <div class="date-field">
        <span class="date-text">{{ displayEndDate }}</span>
      </div>
    </div>

    <div v-if="isOpen" class="calendar-overlay" @click.self="closeCalendar">
      <div class="calendar-container">
        <div class="calendar-header">
          <button class="nav-btn" @click="previousMonth">
            <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
              <path
                d="M12.5 15L7.5 10L12.5 5"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
          <span class="month-year">{{ currentMonthYear }}</span>
          <button class="nav-btn" @click="nextMonth">
            <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
              <path
                d="M7.5 15L12.5 10L7.5 5"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </button>
        </div>

        <div class="calendar-grid">
          <div class="weekday-header">
            <div v-for="day in weekdays" :key="day" class="weekday">{{ day }}</div>
          </div>
          <div class="days-grid">
            <div
              v-for="date in calendarDays"
              :key="date.toString()"
              class="day-cell"
              :class="{
                'other-month': !date.isSameMonth,
                selected: isSelected(date.date),
                'in-range': isInRange(date.date),
                'range-start': isRangeStart(date.date),
                'range-end': isRangeEnd(date.date),
                today: isToday(date.date),
                // disabled: isDisabled(date.date),
              }"
              @click="selectDate(date.date)"
              @mouseenter="hoverDate = date.date"
            >
              {{ date.day }}
            </div>
          </div>
        </div>

        <div class="calendar-footer">
          <button class="reset-btn" @click="resetDates">초기화</button>
          <button class="confirm-btn" @click="confirmDates">적용</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";

const props = defineProps({
  startDate: {
    type: String,
    default: null,
  },
  endDate: {
    type: String,
    default: null,
  },
});

const emit = defineEmits(["update:startDate", "update:endDate", "change"]);

const isOpen = ref(false);
const currentMonth = ref(new Date());
const selectedStart = ref(props.startDate ? new Date(props.startDate) : null);
const selectedEnd = ref(props.endDate ? new Date(props.endDate) : null);
const hoverDate = ref(null);

const weekdays = ["일", "월", "화", "수", "목", "금", "토"];

// 현재 월/년 표시
const currentMonthYear = computed(() => {
  const year = currentMonth.value.getFullYear();
  const month = currentMonth.value.getMonth() + 1;
  return `${year}년 ${month}월`;
});

// 시작 날짜 표시
const displayStartDate = computed(() => {
  if (!selectedStart.value) return "시작 날짜";
  const date = selectedStart.value;
  return `${date.getMonth() + 1}/${date.getDate()}`;
});

// 종료 날짜 표시
const displayEndDate = computed(() => {
  if (!selectedEnd.value) return "종료 날짜";
  const date = selectedEnd.value;
  return `${date.getMonth() + 1}/${date.getDate()}`;
});

// 달력 날짜 생성
const calendarDays = computed(() => {
  const year = currentMonth.value.getFullYear();
  const month = currentMonth.value.getMonth();

  const firstDay = new Date(year, month, 1);
  const lastDay = new Date(year, month + 1, 0);

  const days = [];

  // 이전 달 날짜
  const firstDayOfWeek = firstDay.getDay();
  for (let i = firstDayOfWeek - 1; i >= 0; i--) {
    const date = new Date(year, month, -i);
    days.push({
      date: date,
      day: date.getDate(),
      isSameMonth: false,
    });
  }

  // 현재 달 날짜
  for (let i = 1; i <= lastDay.getDate(); i++) {
    const date = new Date(year, month, i);
    days.push({
      date: date,
      day: i,
      isSameMonth: true,
    });
  }

  // 다음 달 날짜
  const remainingDays = 42 - days.length;
  for (let i = 1; i <= remainingDays; i++) {
    const date = new Date(year, month + 1, i);
    days.push({
      date: date,
      day: i,
      isSameMonth: false,
    });
  }

  return days;
});

// 날짜 선택
const selectDate = (date) => {
  //   if (isDisabled(date)) return;

  if (!selectedStart.value || (selectedStart.value && selectedEnd.value)) {
    // 새로운 범위 시작
    selectedStart.value = date;
    selectedEnd.value = null;
  } else {
    // 범위 종료
    if (date < selectedStart.value) {
      selectedEnd.value = selectedStart.value;
      selectedStart.value = date;
    } else {
      selectedEnd.value = date;
    }
  }
};

// 날짜가 선택된 범위 안에 있는지
const isInRange = (date) => {
  if (!selectedStart.value || !selectedEnd.value) {
    if (selectedStart.value && hoverDate.value) {
      const start = selectedStart.value;
      const end = hoverDate.value;
      return date > start && date < end;
    }
    return false;
  }
  return date > selectedStart.value && date < selectedEnd.value;
};

// 범위 시작/끝 체크
const isRangeStart = (date) => {
  return selectedStart.value && isSameDay(date, selectedStart.value);
};

const isRangeEnd = (date) => {
  return selectedEnd.value && isSameDay(date, selectedEnd.value);
};

const isSelected = (date) => {
  return isRangeStart(date) || isRangeEnd(date);
};

// 오늘인지 체크
const isToday = (date) => {
  const today = new Date();
  return isSameDay(date, today);
};

// 비활성화된 날짜인지 (과거 날짜)
const isDisabled = (date) => {
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  return date < today;
};

// 같은 날짜인지 비교
const isSameDay = (date1, date2) => {
  if (!date1 || !date2) return false;
  return (
    date1.getFullYear() === date2.getFullYear() &&
    date1.getMonth() === date2.getMonth() &&
    date1.getDate() === date2.getDate()
  );
};

// 이전/다음 월
const previousMonth = () => {
  currentMonth.value = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth() - 1,
    1
  );
};

const nextMonth = () => {
  currentMonth.value = new Date(
    currentMonth.value.getFullYear(),
    currentMonth.value.getMonth() + 1,
    1
  );
};

// 달력 토글
const toggleCalendar = () => {
  isOpen.value = !isOpen.value;
};

const closeCalendar = () => {
  isOpen.value = false;
};

// 초기화
const resetDates = () => {
  selectedStart.value = null;
  selectedEnd.value = null;
};

// 적용
const confirmDates = () => {
  if (selectedStart.value) {
    emit("update:startDate", formatDate(selectedStart.value));
  }
  if (selectedEnd.value) {
    emit("update:endDate", formatDate(selectedEnd.value));
  }
  emit("change", {
    startDate: selectedStart.value ? formatDate(selectedStart.value) : null,
    endDate: selectedEnd.value ? formatDate(selectedEnd.value) : null,
  });
  closeCalendar();
};

// 날짜 포맷 (YYYY-MM-DD)
const formatDate = (date) => {
  if (!date) return null;
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  return `${year}-${month}-${day}`;
};

// props 변경 감지
watch(
  () => [props.startDate, props.endDate],
  ([newStart, newEnd]) => {
    if (newStart) selectedStart.value = new Date(newStart);
    if (newEnd) selectedEnd.value = new Date(newEnd);
  }
);
</script>

<style scoped>
.date-range-picker {
  position: relative;
  width: 100%;
  z-index: 9999; /* 기본 레이어 */
}

.date-display {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  background: white;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s;
  position: relative;
  z-index: 9999;
}

.date-display:hover {
  border-color: #7c3aed;
}

.date-field {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  flex: 1;
}

.calendar-icon {
  color: #6b7280;
}

.date-text {
  font-size: 0.875rem;
  color: #374151;
}

.date-separator {
  color: #9ca3af;
}

/* 캘린더 오버레이 - 최우선 레이어 */
.calendar-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5); /* 더 진하게 */
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10000; /* Header(100)보다 훨씬 높게 */
  backdrop-filter: blur(2px);
}

.calendar-container {
  background: white;
  border-radius: 12px;
  box-shadow: 0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04);
  padding: 1.5rem;
  width: 90%;
  max-width: 350px;
  position: relative;
  z-index: 10001; /* 오버레이보다 1 높게 */
}

/* 캘린더 헤더 */
.calendar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.nav-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.25rem;
  border-radius: 4px;
  color: #6b7280;
  transition: all 0.2s;
}

.nav-btn:hover {
  background: #f3f4f6;
  color: #374151;
}

.month-year {
  font-size: 1rem;
  font-weight: 600;
  color: #1f2937;
}

/* 캘린더 그리드 */
.calendar-grid {
  margin-bottom: 1rem;
}

.weekday-header {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 0.25rem;
  margin-bottom: 0.5rem;
}

.weekday {
  text-align: center;
  font-size: 0.75rem;
  font-weight: 600;
  color: #6b7280;
  padding: 0.5rem 0;
}

.days-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 0.25rem;
}

.day-cell {
  aspect-ratio: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  font-size: 0.875rem;
  cursor: pointer;
  transition: all 0.2s;
  position: relative;
}

.day-cell:not(.disabled):hover {
  background: #f3f4f6;
}

.day-cell.other-month {
  color: #d1d5db;
}

.day-cell.disabled {
  color: #d1d5db;
  cursor: not-allowed;
}

.day-cell.today {
  font-weight: 600;
  color: #7c3aed;
}

.day-cell.selected {
  background: #7c3aed;
  color: white;
  font-weight: 600;
}

.day-cell.in-range {
  background: #ede9fe;
  color: #7c3aed;
}

.day-cell.range-start {
  background: #7c3aed;
  color: white;
  border-radius: 6px 0 0 6px;
}

.day-cell.range-end {
  background: #7c3aed;
  color: white;
  border-radius: 0 6px 6px 0;
}

.day-cell.range-start.range-end {
  border-radius: 6px;
}

/* 캘린더 푸터 */
.calendar-footer {
  display: flex;
  gap: 0.5rem;
  justify-content: flex-end;
  padding-top: 1rem;
  border-top: 1px solid #e5e7eb;
}

.reset-btn,
.confirm-btn {
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.875rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
}

.reset-btn {
  background: #f3f4f6;
  color: #374151;
}

.reset-btn:hover {
  background: #e5e7eb;
}

.confirm-btn {
  background: #7c3aed;
  color: white;
}

.confirm-btn:hover {
  background: #6d28d9;
}
</style>
