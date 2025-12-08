<template>
  <div class="signup-page">
    <div class="signup-container">
      <!-- 로고 -->
      <div class="logo">
        <img src="@/assets/img/logo.png" alt="PlanIt" />
      </div>

      <!-- 회원가입 폼 -->
      <div class="signup-form">
        <!-- 이름 입력 -->
        <div class="input-group">
          <label class="input-label">이름</label>
          <input type="text" class="input-field" placeholder="이름을 입력해주세요" v-model="name" />
        </div>

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

        <!-- 닉네임 입력 -->
        <div class="input-group">
          <label class="input-label">닉네임</label>
          <input
            type="text"
            class="input-field"
            placeholder="사용할 닉네임을 입력해주세요"
            v-model="nickname"
          />
        </div>

        <!-- 회원가입 버튼 -->
        <button class="signup-button" @click="handleSignup">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const name = ref("");
const email = ref("");
const password = ref("");
const nickname = ref("");

const handleSignup = () => {
  // 유효성 검사
  if (!name.value || !email.value || !password.value || !nickname.value) {
    alert("모든 항목을 입력해주세요.");
    return;
  }

  // 이메일 형식 검사
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value)) {
    alert("올바른 이메일 형식을 입력해주세요.");
    return;
  }

  // 비밀번호 길이 검사
  if (password.value.length < 6) {
    alert("비밀번호는 6자 이상이어야 합니다.");
    return;
  }

  // 실제로는 API 호출
  console.log("Signup:", {
    name: name.value,
    email: email.value,
    password: password.value,
    nickname: nickname.value,
  });

  // 회원가입 성공 후 로그인 페이지로 이동
  alert("회원가입이 완료되었습니다!");
  router.push("/login");
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

.signup-page {
  min-height: 100vh;
  background: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
  padding: 2rem;
}

.signup-container {
  width: 100%;
  max-width: 728px;
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

/* 회원가입 폼 */
.signup-form {
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

/* 텍스트 영역 */
.input-textarea {
  width: 100%;
  padding: 1rem;
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 16px;
  font-family: inherit;
  color: #1e1e1e;
  outline: none;
  transition: border-color 0.2s;
  resize: vertical;
  min-height: 80px;
  line-height: 1.5;
}

.input-textarea::placeholder {
  color: #9ca3af;
}

.input-textarea:focus {
  border-color: #2563eb;
}

/* 회원가입 버튼 */
.signup-button {
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

.signup-button:hover {
  background: #1e40af;
}

.signup-button:active {
  background: #1e3a8a;
}

/* 반응형 */
@media (max-width: 768px) {
  .signup-page {
    padding: 1rem;
  }

  .signup-container {
    gap: 2rem;
  }

  .logo img {
    height: 40px;
  }

  .signup-form {
    padding: 1.25rem;
    gap: 1.25rem;
  }

  .input-label {
    font-size: 15px;
  }

  .input-field,
  .input-textarea {
    font-size: 15px;
    padding: 0.625rem 0.875rem;
  }

  .input-textarea {
    min-height: 70px;
  }

  .signup-button {
    font-size: 15px;
  }
}

@media (max-width: 480px) {
  .signup-container {
    gap: 1.5rem;
  }

  .logo img {
    height: 36px;
  }

  .signup-form {
    padding: 1rem;
    gap: 1rem;
  }

  .input-label {
    font-size: 14px;
  }

  .input-field,
  .input-textarea {
    font-size: 14px;
    padding: 0.5rem 0.75rem;
  }

  .input-textarea {
    min-height: 60px;
  }

  .signup-button {
    font-size: 14px;
    padding: 0.625rem;
  }
}
</style>
