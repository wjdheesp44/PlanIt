<template>
  <div class="signup-page">
    <div class="signup-container">
      <!-- 로고 -->
      <div class="logo">
        <img src="@/assets/img/logo.png" alt="PlanIt" />
      </div>

      <!-- 회원가입 폼 -->
      <div class="signup-form">
        <!-- 이메일 입력 -->
        <div class="input-group">
          <label class="input-label">이메일</label>
          <input
            type="email"
            class="input-field"
            :class="emailClass"
            placeholder="이메일을 입력해주세요"
            v-model="email"
            @blur="checkEmail"
          />
          <p v-if="emailError" class="warn">
            {{ emailError }}
          </p>
        </div>

        <!-- 비밀번호 입력 -->
        <div class="input-group">
          <label class="input-label">비밀번호</label>
          <input
            type="password"
            class="input-field"
            :class="passwordClass"
            placeholder="비밀번호를 입력해주세요"
            v-model="password"
            @blur="checkPassword"
          />
          <p v-if="passwordError" class="warn">
              {{ passwordError }}
            </p>
        </div>

        <!-- 닉네임 입력 -->
        <div class="input-group">
          <label class="input-label">닉네임</label>
          <input
            type="text"
            class="input-field"
            :class="nicknameClass"
            placeholder="사용할 닉네임을 입력해주세요"
            v-model="nickname"
            @blur="checkNickname"
          />
          <p v-if="nicknameError" class="warn">
            {{ nicknameError }}
          </p>
        </div>

        <!-- 이름 입력 -->
        <div class="input-group">
          <label class="input-label">이름</label>
          <input type="text" class="input-field" placeholder="이름을 입력해주세요" v-model="name" />
        </div>

        <!-- 회원가입 버튼 -->
        <button class="signup-button" @click="handleSignup">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import authApi, { checkEmailApi, checkNicknameApi } from "@/api/user/authApi";

const router = useRouter();

const name = ref("");
const email = ref("");
const password = ref("");
const nickname = ref("");

const emailError = ref("");
const nicknameError = ref("");
const passwordError = ref("");

const checkEmail = async () => {
  if (!email.value) {
    emailError.value = "이메일을 입력해주세요.";
    return;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email.value)) {
    emailError.value = "올바른 이메일 형식이 아닙니다.";
    return;
  }

try {
  const res = await checkEmailApi(email.value);

  emailError.value = res.data
    ? "이미 사용 중인 이메일입니다."
    : "사용 가능한 이메일입니다.";
  } catch (e) {
    emailError.value = "이메일 확인 중 오류가 발생했습니다.";
  }
};

const checkNickname = async () => {
  if (!nickname.value) {
    nicknameError.value = "닉네임을 입력해주세요.";
    return;
  }

  try {
    const res = await checkNicknameApi(nickname.value);

    nicknameError.value = res.data
      ? "이미 사용 중인 닉네임입니다."
      : "사용 가능한 닉네임입니다.";
  } catch (e) {
    nicknameError.value = "닉네임 확인 중 오류가 발생했습니다.";
  }
};

const checkPassword = () => {
  if (!password.value) {
    passwordError.value = "비밀번호를 입력해주세요.";
    return;
  }

  if (password.value.length < 6) {
    passwordError.value = "비밀번호는 6자 이상이어야 합니다.";
    return;
  }

  passwordError.value = "사용 가능한 비밀번호입니다.";
};

const handleSignup = async () => {
  // 유효성 검사
  if (!name.value || !email.value || !password.value || !nickname.value) {
    alert("모든 항목을 입력해주세요.");
    return;
  }

  try {
    await authApi.post("/api/join", {
      email: email.value,
      password: password.value,
      nickname: nickname.value,
      name: name.value
    });

    alert("회원가입 성공! 로그인해주세요.");
    router.push("/login");

  } catch (err) {
    console.error(err);
    alert("회원가입 실패!");
  }

};

const emailClass = computed(() => {
  if (!emailError.value) return "";
  return emailError.value === "사용 가능한 이메일입니다."
    ? "success"
    : "error";
});

const nicknameClass = computed(() => {
  if (!nicknameError.value) return "";
  return nicknameError.value === "사용 가능한 닉네임입니다."
    ? "success"
    : "error";
});

const passwordClass = computed(() => {
  if (!passwordError.value) return "";
  return passwordError.value === "사용 가능한 비밀번호입니다."
    ? "success"
    : "error";
});

</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

*,
*::before,
*::after {
  box-sizing: border-box;
}

/* 에러 input 테두리 */
.input-field.error {
  border-color: #ef4444; /* 빨강 */
}

.input-field.success {
  border-color: #22c55e;
}

/* 에러 메시지 */
.warn {
  font-size: 14px;
  color: #ef4444;
  line-height: 1.4;
  margin-top: 4px;
}

.input-field.error:focus {
  border-color: #dc2626;
  box-shadow: 0 0 0 1px rgba(239, 68, 68, 0.3);
}

.input-field.success + .warn {
  color: #22c55e;
}

.warn.success {
  color: #16a34a; /* 초록 */
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
