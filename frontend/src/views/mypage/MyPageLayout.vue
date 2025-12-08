<template>
  <div class="mypage-layout">
    <!-- 사이드바 -->
    <MyPageSidebar />

    <!-- 메인 콘텐츠 (동적) -->
    <main class="layout-content">
      <RouterView />
    </main>
  </div>
</template>

<script setup>
import MyPageSidebar from "@/components/MyPageSidebar.vue";
</script>

<style scoped>
.mypage-layout {
  display: flex;
  min-height: 100vh;
  background: #ffffff;
}

.layout-content {
  flex: 1;
  padding: 2rem 3rem;
  max-width: 1400px;
  margin: 0 auto;
}

/* 반응형 */
@media (max-width: 1024px) {
  .mypage-layout {
    flex-direction: column;
  }

  .layout-content {
    padding: 2rem 1.5rem;
  }
}

@media (max-width: 768px) {
  .layout-content {
    padding: 1.5rem 1rem;
  }
}
</style>
