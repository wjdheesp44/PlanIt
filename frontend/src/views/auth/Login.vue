<template>
  <div class="login-page">
    <div class="login-container">
      <img src="@/assets/img/logo.png" alt="PlanIt" />
      <!-- form으로 감싸기 -->
        <form class="login-form" @submit.prevent="handleLogin">
          <!-- 이메일 입력 -->
          <div class="input-group">
            <label class="input-label">이메일</label>
            <input
              type="email"
              class="input-field"
              placeholder="이메일을 입력해주세요"
              v-model="email"
            />
          </div>

          <!-- 비밀번호 입력 -->
          <div class="input-group">
            <label class="input-label">비밀번호</label>
            <input
              type="password"
              class="input-field"
              placeholder="비밀번호를 입력해주세요"
              v-model="password"
            />
          </div>

          <!-- submit 버튼 -->
          <button type="submit" class="login-button">
            로그인
          </button>

          <!-- 링크 -->
          <div class="links">
            <a href="/signup" class="/signup">회원가입</a>
            <a href="/forgot-password-email" class="/forgot-password">비밀번호 찾기</a>
          </div>
        </form>
      </div>
    </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import authApi from "@/api/user/authApi";
import { useAuthStore } from "@/stores/auth";

const router = useRouter();
const authStore = useAuthStore();

const email = ref("");
const password = ref("");
const isLoading = ref(false);

const handleLogin = async () => {
  // 간단한 유효성 검사
  if (!email.value || !password.value) {
    alert("이메일과 비밀번호를 입력해주세요.");
    return;
  }

  try {
      isLoading.value = true;

      // authApi 사용
      const res = await authApi.post("/api/login", {
        username: email.value,
        password: password.value,
      });

      // 백엔드에서 내려준 JWT 저장
      const { accessToken, user } = res.data;

      if (!accessToken) {
        throw new Error("토큰이 없습니다.");
      }

      localStorage.setItem("accessToken", accessToken);
      authStore.setLogin(accessToken, user);

      // 로그인 성공 후 이동
      router.replace("/"); // 뒤로가기 방지
    } catch (err) {
      console.error(err);

      const message =
        err.response?.status === 401
          ? "이메일 또는 비밀번호가 올바르지 않습니다."
          : "로그인 중 오류가 발생했습니다.";

      alert(message);
    } finally {
      isLoading.value = false;
    }

  // 로그인 성공 후 메인 페이지로 이동
  router.push("/");
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

.login-page {
  min-height: 100vh;
  background: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  padding: 2rem;
}

.login-container {
  width: 100%;
  max-width: 609px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 3rem;
}

/* 로고 */
.logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 24px;
  font-weight: 700;
  color: #1e3a8a;
}

/* 로그인 폼 */
.login-form {
  width: 100%;
  padding: 1.5rem;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 입력 그룹 */
.input-group {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.input-label {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  line-height: 1.5;
}

.input-field {
  width: 100%;
  padding: 0.75rem 1rem;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 16px;
  font-family: inherit;
  color: #1e1e1e;
  outline: none;
  transition: border-color 0.2s;
}

.input-field::placeholder {
  color: #9ca3af;
}

.input-field:focus {
  border-color: #2563eb;
}

/* 로그인 버튼 */
.login-button {
  width: 100%;
  padding: 0.75rem;
  background: #2563eb;
  color: #ffffff;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 400;
  cursor: pointer;
  transition: background 0.2s;
}

.login-button:hover {
  background: #1e40af;
}

.login-button:active {
  background: #1e3a8a;
}

/* 링크 */
.links {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.link {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  text-decoration: underline;
  cursor: pointer;
  transition: color 0.2s;
}

.link:hover {
  color: #2563eb;
}

/* 반응형 */
@media (max-width: 768px) {
  .login-page {
    padding: 1rem;
  }

  .login-container {
    gap: 2rem;
  }

  .logo {
    font-size: 20px;
  }

  .login-form {
    padding: 1.25rem;
    gap: 1.25rem;
  }

  .input-label {
    font-size: 15px;
  }

  .input-field {
    font-size: 15px;
    padding: 0.625rem 0.875rem;
  }

  .login-button {
    font-size: 15px;
  }

  .link {
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .login-container {
    gap: 1.5rem;
  }

  .logo {
    font-size: 18px;
  }

  .login-form {
    padding: 1rem;
    gap: 1rem;
  }

  .input-label {
    font-size: 14px;
  }

  .input-field {
    font-size: 14px;
    padding: 0.5rem 0.75rem;
  }

  .login-button {
    font-size: 14px;
    padding: 0.625rem;
  }

  .link {
    font-size: 13px;
  }
}
</style>
