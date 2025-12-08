<template>
  <div class="search-container">
    <div class="search-bar">
      <input type="text" placeholder="어디로 떠나고 싶나요?" class="search-input" />
      <img src="@/assets/img/searchIcon.png" alt="search" class="search-icon" />
    </div>
  </div>
</template>

<script setup></script>

<style scoped>
.search-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.search-bar {
  width: 100%;
  max-width: 800px;
  height: 64px;
  margin: 0 auto;
  padding: 0 1.5rem;
  border-radius: 9999px;
  border: 1px solid #e5e7eb;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  background: #ffffff;
  transition: all 0.2s ease;
}

.search-bar:focus-within {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.search-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 15px;
  color: #1e1e1e;
  font-family: "Noto Sans KR", sans-serif;
}

.search-input::placeholder {
  color: #9ca3af;
}

.search-icon {
  width: 32px;
  height: 32px;
  flex-shrink: 0;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.search-icon:hover {
  transform: scale(1.1);
}

/* 반응형 */
@media (max-width: 768px) {
  .search-bar {
    height: 56px;
    padding: 0 1.25rem;
  }

  .search-input {
    font-size: 14px;
  }

  .search-icon {
    width: 28px;
    height: 28px;
  }
}

@media (max-width: 480px) {
  .search-bar {
    height: 48px;
    padding: 0 1rem;
  }

  .search-input {
    font-size: 13px;
  }

  .search-icon {
    width: 24px;
    height: 24px;
  }
}
</style>
