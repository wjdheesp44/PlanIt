<template>
  <header class="header">
    <div class="header-container">
      <RouterLink to="/" class="logo">
        <img src="@/assets/img/logo.png" alt="PlanIt" />
      </RouterLink>

      <nav class="nav">
        <RouterLink to="/spots" class="nav-link">스팟 탐색</RouterLink>
        <RouterLink
          to="/plans"
          class="nav-link"
          :class="{ disabled: !isLogin }"
          @click.prevent="onClickPlanner"
          >여행 플래너</RouterLink
        >
        <RouterLink
          to="/mypage"
          class="nav-link"
          :class="{ disabled: !isLogin }"
          @click.prevent="onClickMyPage"
          >마이페이지</RouterLink
        >
      </nav>

      <div class="user-menu">
        <button class="user-icon" @click="handleUserIconClick">
          <svg width="28" height="28" viewBox="0 0 34 34" fill="currentColor">
            <circle cx="17" cy="12" r="5" />
            <path d="M5 30c0-6.627 5.373-12 12-12s12 5.373 12 12" />
          </svg>
        </button>
      </div>
    </div>
  </header>
</template>

<script setup>
import { RouterLink } from "vue-router";
import { computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const isLogin = computed(() => {
  return !!localStorage.getItem("accessToken");
});

const onClickPlanner = () => {
  if (!isLogin.value) {
    // 로그인 안 했으면 아무 반응 없음
    return;
  }
  router.push("/plans");
};

// 마이페이지 메뉴
const onClickMyPage = () => {
  if (!isLogin.value) {
    // 로그인 안 했으면 아무 반응 없음
    return;
  }
  router.push("/mypage");
};

// 사람 아이콘
const handleUserIconClick = () => {
  const accessToken = localStorage.getItem("accessToken");

  // 로그인 안 된 상태
  if (!accessToken) {
    router.push("/login");
    return;
  }

  // 로그인 된 상태 → 로그아웃
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken"); // 있다면 같이 삭제

  alert("로그아웃 되었습니다.");
  router.push("/").then(() => {
    window.location.reload();
  }); // 또는 /login
};
</script>

<style scoped>
/* Header */
.header {
  background: #ffffff;
  border-bottom: 1px solid #e5e5e5;
  padding: 1rem 2rem;
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(10px);
  background: rgba(255, 255, 255, 0.95);
}

.header-container {
  max-width: 1600px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.logo {
  display: block;
}

.logo img {
  height: 36px;
  width: auto;
  display: block;
}

.nav {
  display: flex;
  gap: 0.25rem;
  flex: 1;
  justify-content: flex-end;
}

.nav-link {
  padding: 0.5rem 0.875rem;
  border-radius: 6px;
  text-decoration: none;
  color: #1e1e1e;
  font-size: 14px;
  font-weight: 400;
  transition: all 0.2s ease;
}

.nav-link:hover {
  background: #f5f5f5;
}

.nav-link.router-link-active {
  background: #f0f0f0;
  font-weight: 500;
}

.user-icon {
  background: none;
  border: none;
  cursor: pointer;
  color: #1e1e1e;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.2s ease;
  width: 28px;
  height: 28px;
}

.user-icon svg {
  width: 28px;
  height: 28px;
}

.user-icon:hover {
  transform: scale(1.05);
}

/* Responsive */
@media (max-width: 1024px) {
  .header-container {
    flex-wrap: wrap;
  }

  .nav {
    order: 3;
    width: 100%;
    justify-content: flex-start;
  }
}

@media (max-width: 768px) {
  .header {
    padding: 1rem;
  }

  .logo img {
    height: 32px;
  }

  .nav-link {
    font-size: 13px;
    padding: 0.375rem 0.75rem;
  }
}

@media (max-width: 480px) {
  .header-container {
    gap: 1rem;
  }

  .nav {
    gap: 0.25rem;
  }

  .nav-link {
    font-size: 12px;
    padding: 0.25rem 0.5rem;
  }
}
</style>
