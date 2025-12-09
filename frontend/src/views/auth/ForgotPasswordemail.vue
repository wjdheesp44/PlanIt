<template>
  <div class="forgot-password-email-page">
    <div class="forgot-password-email-container">
      <!-- 로고 -->
      <div class="logo">
        <img src="@/assets/img/logo.png" alt="PlanIt" />
      </div>

      <!-- 비밀번호 찾기 폼 -->
      <div class="forgot-password-email-form">
        <div class="form-header">
          <h1 class="form-title">비밀번호 찾기</h1>
          <p class="form-description">
            가입하신 이메일 주소를 입력해주세요.<br />
            비밀번호 재설정 링크를 보내드립니다.
          </p>
        </div>

        <!-- 이메일 입력 -->
        <div class="input-group">
          <label class="input-label">이메일</label>
          <input
            type="email"
            class="input-field"
            placeholder="이메일을 입력해주세요"
            v-model="email"
            @keyup.enter="handleSubmit"
          />
        </div>

        <!-- 전송 버튼 -->
        <button class="submit-button" @click="handleSubmit">비밀번호 재설정 링크 전송</button>

        <!-- 링크 -->
        <div class="links">
          <a href="/login" class="link">로그인으로 돌아가기</a>
          <a href="/signup" class="link">회원가입</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const email = ref("");

const handleSubmit = () => {
  // 유효성 검사
  if (!email.value) {
    alert("이메일을 입력해주세요.");
    return;
  }

  // 이메일 형식 검사
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value)) {
    alert("올바른 이메일 형식을 입력해주세요.");
    return;
  }

  // 실제로는 API 호출
  console.log("Password reset link requested for:", email.value);

  // 성공 메시지
  alert("비밀번호 재설정 링크를 이메일로 전송했습니다.\n이메일을 확인해주세요.");

  // 로그인 페이지로 이동
  router.push("/forgot-password");
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

.forgot-password-email-page {
  min-height: 100vh;
  background: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  padding: 2rem;
}

.forgot-password-email-container {
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
  justify-content: center;
}

.logo img {
  height: 48px;
  width: auto;
}

/* 비밀번호 찾기 폼 */
.forgot-password-email-form {
  width: 100%;
  padding: 1.5rem;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 폼 헤더 */
.form-header {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  text-align: center;
}

.form-title {
  font-size: 24px;
  font-weight: 600;
  color: #1e1e1e;
  margin: 0;
}

.form-description {
  font-size: 14px;
  font-weight: 400;
  color: #6b7280;
  line-height: 1.6;
  margin: 0;
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

/* 전송 버튼 */
.submit-button {
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

.submit-button:hover {
  background: #1e40af;
}

.submit-button:active {
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
  .forgot-password-email-page {
    padding: 1rem;
  }

  .forgot-password-email-container {
    gap: 2rem;
  }

  .logo img {
    height: 40px;
  }

  .forgot-password-email-form {
    padding: 1.25rem;
    gap: 1.25rem;
  }

  .form-title {
    font-size: 20px;
  }

  .form-description {
    font-size: 13px;
  }

  .input-label {
    font-size: 15px;
  }

  .input-field {
    font-size: 15px;
    padding: 0.625rem 0.875rem;
  }

  .submit-button {
    font-size: 15px;
  }

  .link {
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .forgot-password-email-container {
    gap: 1.5rem;
  }

  .logo img {
    height: 36px;
  }

  .forgot-password-email-form {
    padding: 1rem;
    gap: 1rem;
  }

  .form-title {
    font-size: 18px;
  }

  .form-description {
    font-size: 12px;
  }

  .input-label {
    font-size: 14px;
  }

  .input-field {
    font-size: 14px;
    padding: 0.5rem 0.75rem;
  }

  .submit-button {
    font-size: 14px;
    padding: 0.625rem;
  }

  .link {
    font-size: 13px;
  }

  .links {
    flex-direction: column;
    gap: 0.5rem;
  }
}
</style>
