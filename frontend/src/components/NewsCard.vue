<template>
  <article class="news-card">
    <div class="news-meta">
      <span class="news-source">{{ source }}</span>
      <span class="news-time">{{ time }}</span>
    </div>

    <h3 class="news-title">{{ title }}</h3>
    <p class="news-summary"
       :class="{ expanded: isExpanded }"
    >
      {{ summary }}
    </p>
    <div class="keywords">
      <span
        v-for="(k, i) in keywords"
        :key="i"
        class="keyword"
      >
        #{{ k }}
      </span>
    </div>
    <button v-if="showMore" class="news-more"
            @click.stop="toggleExpand"
    >
      {{ isExpanded ? "접기" : "더보기" }}
    </button>
  </article>
</template>

<script setup>
  import { ref } from "vue";

const isExpanded = ref(false);

const toggleExpand = () => {
  isExpanded.value = !isExpanded.value;
};

const props = defineProps({
  source: String,
  time: String,
  category: String,
  title: String,
  summary: String,
  showMore: Boolean,
  keywords: {
    type: Array,
    default: () => [],
  },
});
</script>

<style scoped>
.news-card {
  background: #ffffff;
  border-radius: 16px;
  border: 1px solid #e5e7eb;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  transition: all 0.2s ease;
  cursor: pointer;
}

.news-card:hover {
  border-color: #d1d5db;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.news-meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 12px;
  color: #71717a;
}

.news-source {
  font-weight: 500;
}

.news-time {
  font-weight: 400;
}

.news-category {
  display: inline-block;
  font-size: 12px;
  color: #8b5cf6;
  font-weight: 500;
}

.news-title {
  font-size: 14px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.4;
}

.news-summary {
  font-size: 12px;
  color: #52525b;
  margin: 0;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-summary.expanded {
  -webkit-line-clamp: unset;
  overflow: visible;
  display: block;
}

.news-more {
  background: none;
  border: none;
  font-size: 12px;
  color: #2563eb;
  padding: 0;
  margin-top: 0.25rem;
  cursor: pointer;
  text-align: left;
  font-family: inherit;
  transition: text-decoration 0.2s ease;
}

.news-more:hover {
  text-decoration: underline;
}

/* 반응형 */
@media (max-width: 768px) {
  .news-card {
    padding: 0.875rem;
  }

  .news-title {
    font-size: 13px;
  }

  .news-summary {
    font-size: 11px;
  }
}
</style>
