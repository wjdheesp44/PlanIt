<template>
  <div class="mypage-layout">
    <!-- 사이드바 -->
    <MyPageSidebar />
    <!-- 로딩 중일 때 로딩 화면 표시 -->
<!--    <div v-if="isLoading">Loading...</div>-->
<!--    <MyPageSidebar v-if="userInfo" :user="userInfo" />-->
    <!-- 메인 콘텐츠 (동적) -->
    <main class="layout-content">
      <RouterView />
    </main>
  </div>
</template>

<script setup>
// import { ref, onMounted } from "vue";
import MyPageSidebar from "@/components/mypage/MyPageSidebar.vue";
// import { getMyInfo } from "@/api/user/user";
//
// const userInfo = ref(null);
// const isLoading = ref(true);  // 로딩 상태를 추적하는 변수
//
// onMounted(async () => {
//   try {
//     const res = await getMyInfo();
//     console.log(res)
//     userInfo.value = res.data; // API 응답 데이터를 userInfo에 할당
//   } catch (e) {
//     console.error("유저 정보 로드 실패", e);
//   } finally {
//     isLoading.value = false;  // API 호출 완료 후 로딩 상태 해제
//   }
// });
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
