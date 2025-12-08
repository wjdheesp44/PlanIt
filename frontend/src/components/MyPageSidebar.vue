<template>
  <aside class="sidebar">
    <h2 class="sidebar-title">마이페이지</h2>

    <nav class="sidebar-nav">
      <!-- 좋아요한 스팟 -->
      <RouterLink to="/mypage/liked-spots" class="nav-item">
        <div class="nav-icon">
          <svg
            width="17"
            height="17"
            viewBox="0 0 17 17"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M1.34082 6.37037C1.34083 5.62416 1.5672 4.8955 1.99002 4.28064C2.41285 3.66578 3.01223 3.19364 3.70902 2.92658C4.40581 2.65951 5.16722 2.61009 5.89268 2.78485C6.61814 2.9596 7.27353 3.3503 7.77229 3.90534C7.80741 3.94291 7.84988 3.97285 7.89706 3.99332C7.94424 4.0138 7.99512 4.02436 8.04655 4.02436C8.09798 4.02436 8.14886 4.0138 8.19604 3.99332C8.24321 3.97285 8.28569 3.94291 8.32081 3.90534C8.818 3.34669 9.47354 2.95271 10.2002 2.77583C10.9268 2.59896 11.6901 2.64758 12.3884 2.91523C13.0867 3.18288 13.687 3.65686 14.1093 4.27409C14.5316 4.89132 14.7558 5.62252 14.7523 6.37037C14.7523 7.90598 13.7464 9.05266 12.7406 10.0585L9.05777 13.6213C8.93282 13.7648 8.77877 13.8801 8.60584 13.9594C8.43291 14.0388 8.24507 14.0805 8.05479 14.0817C7.86451 14.0829 7.67616 14.0436 7.50224 13.9664C7.32832 13.8892 7.17281 13.7759 7.04606 13.634L3.35254 10.0585C2.34668 9.05266 1.34082 7.91269 1.34082 6.37037Z"
              stroke="#6A7282"
              stroke-width="1.34115"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </div>
        <div class="nav-content">
          <h3 class="nav-title">좋아요한 스팟</h3>
          <p class="nav-description">내가 저장한 곳을 확인하세요</p>
        </div>
      </RouterLink>

      <!-- 분석 리포트 -->
      <RouterLink to="/mypage/report" class="nav-item">
        <div class="nav-icon">
          <svg
            width="15"
            height="15"
            viewBox="0 0 15 15"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M1.84863 1.84888V11.71C1.84863 12.0369 1.9785 12.3504 2.20966 12.5816C2.44083 12.8128 2.75436 12.9426 3.08127 12.9426H12.9424"
              stroke="#6A7282"
              stroke-width="1.23264"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M11.0938 10.4774V5.54688"
              stroke="#6A7282"
              stroke-width="1.23264"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M8.01172 10.4774V3.08154"
              stroke="#6A7282"
              stroke-width="1.23264"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M4.93066 10.4774V8.62842"
              stroke="#6A7282"
              stroke-width="1.23264"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </div>
        <div class="nav-content">
          <h3 class="nav-title">분석 리포트</h3>
          <p class="nav-description">AI 기반 스팟 분석을 확인하세요</p>
        </div>
      </RouterLink>

      <!-- 회원정보 -->
      <RouterLink to="/mypage/profile" class="nav-item">
        <div class="nav-icon">
          <svg
            width="20"
            height="20"
            viewBox="0 0 20 20"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M15.2148 16.8164V15.2148C15.2148 14.3653 14.8774 13.5506 14.2767 12.9499C13.676 12.3492 12.8612 12.0117 12.0117 12.0117H7.20703C6.35751 12.0117 5.54278 12.3492 4.94208 12.9499C4.34138 13.5506 4.00391 14.3653 4.00391 15.2148V16.8164"
              stroke="#31187D"
              stroke-width="1.60156"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M9.60938 8.80859C11.3784 8.80859 12.8125 7.37451 12.8125 5.60547C12.8125 3.83643 11.3784 2.40234 9.60938 2.40234C7.84034 2.40234 6.40625 3.83643 6.40625 5.60547C6.40625 7.37451 7.84034 8.80859 9.60938 8.80859Z"
              stroke="#31187D"
              stroke-width="1.60156"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </div>
        <div class="nav-content">
          <h3 class="nav-title">회원정보</h3>
          <p class="nav-description">회원 정보를 관리합니다</p>
        </div>
      </RouterLink>

      <!-- 내가 쓴 후기 -->
      <RouterLink to="/mypage/reviews" class="nav-item">
        <div class="nav-icon">
          <svg
            width="18"
            height="18"
            viewBox="0 0 18 18"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M4.43696 16.2685C4.04472 16.2685 3.66854 16.1127 3.39118 15.8353C3.11383 15.558 2.95801 15.1818 2.95801 14.7896V2.95796C2.95801 2.56571 3.11383 2.18954 3.39118 1.91218C3.66854 1.63482 4.04472 1.47901 4.43696 1.47901H10.3528C10.5868 1.47863 10.8187 1.52456 11.0349 1.61416C11.2512 1.70376 11.4476 1.83526 11.6128 2.00108L14.2661 4.65431C14.4323 4.81959 14.5642 5.01617 14.6541 5.2327C14.7439 5.44923 14.79 5.68142 14.7896 5.91586V14.7896C14.7896 15.1818 14.6338 15.558 14.3564 15.8353C14.0791 16.1127 13.7029 16.2685 13.3107 16.2685H4.43696Z"
              stroke="#6A7282"
              stroke-width="1.47895"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M10.3525 1.479V5.17638C10.3525 5.3725 10.4304 5.56059 10.5691 5.69927C10.7078 5.83794 10.8959 5.91585 11.092 5.91585H14.7894"
              stroke="#6A7282"
              stroke-width="1.47895"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M7.39497 6.65527H5.91602"
              stroke="#6A7282"
              stroke-width="1.47895"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M11.8318 9.61328H5.91602"
              stroke="#6A7282"
              stroke-width="1.47895"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M11.8318 12.571H5.91602"
              stroke="#6A7282"
              stroke-width="1.47895"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </div>
        <div class="nav-content">
          <h3 class="nav-title">내가 쓴 후기</h3>
          <p class="nav-description">작성한 후기를 확인하세요</p>
        </div>
      </RouterLink>
    </nav>
  </aside>
</template>

<script setup>
import { defineEmits } from "vue";

const emit = defineEmits(["menu-change"]);
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.sidebar {
  width: 320px;
  min-height: 100vh;
  background: #ffffff;
  border-right: 0.83px solid #e5e7eb;
  padding: 1.5rem;
  display: flex;
  flex-direction: column;
  gap: 2rem;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

.sidebar-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.nav-item {
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
  padding: 1rem;
  border-radius: 10px;
  text-decoration: none;
  transition: all 0.2s ease;
  cursor: pointer;
}

.nav-item:hover {
  background: #f9fafb;
}

/* RouterLink 활성 상태 */
.nav-item.router-link-active {
  background: #ffffff;
  border: 1.67px solid #1e3a8a;
}

.nav-item.router-link-exact-active {
  background: #ffffff;
  border: 1.67px solid #1e3a8a;
}

.nav-item.router-link-active .nav-title,
.nav-item.router-link-exact-active .nav-title {
  color: #1e3a8a;
}

.nav-item.router-link-active .nav-icon svg path,
.nav-item.router-link-exact-active .nav-icon svg path {
  stroke: #1e3a8a;
}

.nav-icon {
  flex-shrink: 0;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.nav-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.nav-description {
  font-size: 14px;
  color: #6b7280;
  margin: 0;
  line-height: 1.43;
}

/* 반응형 - 태블릿 */
@media (max-width: 1024px) {
  .sidebar {
    width: 280px;
    padding: 1.25rem;
  }

  .nav-item {
    padding: 0.875rem;
  }
}

/* 반응형 - 모바일 */
@media (max-width: 1024px) {
  .sidebar {
    width: 100%;
    min-height: auto;
    border-right: none;
    border-bottom: 0.83px solid #e5e7eb;
    padding: 1rem;
  }

  .sidebar-nav {
    flex-direction: row;
    overflow-x: auto;
    gap: 0.5rem;
  }

  .nav-item {
    flex-direction: column;
    align-items: center;
    min-width: 120px;
    padding: 0.75rem;
    text-align: center;
  }

  .nav-content {
    align-items: center;
  }

  .nav-description {
    display: none;
  }
}
</style>
