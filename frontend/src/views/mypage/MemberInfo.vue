<template>
  <div class="mypage">
    <!-- 메인 콘텐츠 -->
    <main class="main-content">
      <!-- 헤더 -->
      <div class="page-header">
        <h1 class="page-title">회원정보</h1>
        <div class="button-group">
          <button class="cancel-button" @click="cancel">
            <svg
              width="16"
              height="16"
              viewBox="0 0 16 16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M14.1063 4.53832C14.4586 4.18616 14.6565 3.7085 14.6566 3.21042C14.6566 2.71233 14.4588 2.23463 14.1067 1.88238C13.7545 1.53014 13.2769 1.33222 12.7788 1.33215C12.2807 1.33209 11.803 1.52989 11.4507 1.88205L2.55919 10.7756C2.4045 10.9298 2.29011 11.1197 2.22607 11.3286L1.34598 14.228C1.32876 14.2856 1.32746 14.3468 1.34222 14.4051C1.35697 14.4634 1.38723 14.5166 1.42979 14.5591C1.47234 14.6016 1.52561 14.6318 1.58393 14.6465C1.64225 14.6611 1.70345 14.6597 1.76104 14.6424L4.66115 13.763C4.8698 13.6995 5.05967 13.5858 5.21413 13.4319L14.1063 4.53832Z"
                stroke="white"
                stroke-width="1.33247"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
            취소
          </button>
          <button class="save-button" @click="save">
            <svg
              width="16"
              height="16"
              viewBox="0 0 16 16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M14.1063 4.53832C14.4586 4.18616 14.6565 3.7085 14.6566 3.21042C14.6566 2.71233 14.4588 2.23463 14.1067 1.88238C13.7545 1.53014 13.2769 1.33222 12.7788 1.33215C12.2807 1.33209 11.803 1.52989 11.4507 1.88205L2.55919 10.7756C2.4045 10.9298 2.29011 11.1197 2.22607 11.3286L1.34598 14.228C1.32876 14.2856 1.32746 14.3468 1.34222 14.4051C1.35697 14.4634 1.38723 14.5166 1.42979 14.5591C1.47234 14.6016 1.52561 14.6318 1.58393 14.6465C1.64225 14.6611 1.70345 14.6597 1.76104 14.6424L4.66115 13.763C4.8698 13.6995 5.05967 13.5858 5.21413 13.4319L14.1063 4.53832Z"
                stroke="white"
                stroke-width="1.33247"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
            저장
          </button>
        </div>
      </div>
      <!-- 프로필 카드 -->
      <div class="profile-card">
        <!-- 프로필 헤더 -->
        <div class="profile-header">
          <div class="profile-avatar">
            <svg
              width="40"
              height="40"
              viewBox="0 0 40 40"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M31.6663 35V31.6667C31.6663 29.8986 30.964 28.2029 29.7137 26.9526C28.4635 25.7024 26.7678 25 24.9997 25H14.9997C13.2316 25 11.5359 25.7024 10.2856 26.9526C9.03539 28.2029 8.33301 29.8986 8.33301 31.6667V35"
                stroke="#9810FA"
                stroke-width="3.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
              <path
                d="M19.9997 18.3333C23.6816 18.3333 26.6663 15.3486 26.6663 11.6667C26.6663 7.98477 23.6816 5 19.9997 5C16.3178 5 13.333 7.98477 13.333 11.6667C13.333 15.3486 16.3178 18.3333 19.9997 18.3333Z"
                stroke="#9810FA"
                stroke-width="3.33333"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </svg>
          </div>
          <div class="profile-info">
            <h2 class="profile-name" v-if="userInfo">{{ userInfo.name }}</h2>
            <p class="profile-nickname" v-if="userInfo">{{ userInfo.nickname }}</p>
            <span class="user-badge" v-if="userInfo">{{
              userInfo.role === "ADMIN" ? "관리자" : "일반 사용자"
            }}</span>
          </div>
        </div>

        <!-- 정보 목록 -->
        <div class="info-list">
          <!-- 이름 -->
          <div class="info-item">
            <div class="info-icon">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M15.8337 17.5V15.8333C15.8337 14.9493 15.4825 14.1014 14.8573 13.4763C14.2322 12.8512 13.3844 12.5 12.5003 12.5H7.50033C6.61627 12.5 5.76842 12.8512 5.1433 13.4763C4.51818 14.1014 4.16699 14.9493 4.16699 15.8333V17.5"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M10.0003 9.16667C11.8413 9.16667 13.3337 7.67428 13.3337 5.83333C13.3337 3.99238 11.8413 2.5 10.0003 2.5C8.15938 2.5 6.66699 3.99238 6.66699 5.83333C6.66699 7.67428 8.15938 9.16667 10.0003 9.16667Z"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="info-content">
              <span class="info-label">이름</span>
              <input class="info-value" v-model="form.name" />
            </div>
          </div>

          <!-- 아이디 -->
          <div class="info-item">
            <div class="info-icon">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M18.3337 5.83325L10.8412 10.6058C10.5869 10.7534 10.2981 10.8312 10.0041 10.8312C9.71004 10.8312 9.42125 10.7534 9.16699 10.6058L1.66699 5.83325"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M16.667 3.33325H3.33366C2.41318 3.33325 1.66699 4.07944 1.66699 4.99992V14.9999C1.66699 15.9204 2.41318 16.6666 3.33366 16.6666H16.667C17.5875 16.6666 18.3337 15.9204 18.3337 14.9999V4.99992C18.3337 4.07944 17.5875 3.33325 16.667 3.33325Z"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="info-content">
              <span class="info-label">아이디</span>
              <p class="info-value" v-if="userInfo">{{ userInfo.email }}</p>
            </div>
          </div>

          <!-- 닉네임 -->
          <div class="info-item">
            <div class="info-icon">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M10.0003 13.3334C11.8413 13.3334 13.3337 11.841 13.3337 10.0001C13.3337 8.15913 11.8413 6.66675 10.0003 6.66675C8.15938 6.66675 6.66699 8.15913 6.66699 10.0001C6.66699 11.841 8.15938 13.3334 10.0003 13.3334Z"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M13.3337 6.66676V10.8334C13.3337 11.4965 13.5971 12.1323 14.0659 12.6012C14.5347 13.07 15.1706 13.3334 15.8337 13.3334C16.4967 13.3334 17.1326 13.07 17.6014 12.6012C18.0703 12.1323 18.3337 11.4965 18.3337 10.8334V10.0001C18.3337 8.12284 17.6998 6.30059 16.5348 4.82856C15.3699 3.35654 13.742 2.32098 11.9149 1.88968C10.0879 1.45838 8.16878 1.65659 6.46847 2.45221C4.76817 3.24784 3.38631 4.59425 2.54678 6.27331C1.70725 7.95237 1.45923 9.86571 1.84292 11.7033C2.22661 13.5409 3.21951 15.1952 4.66077 16.398C6.10203 17.6009 7.9072 18.2818 9.78382 18.3306C11.6604 18.3794 13.4985 17.7931 15.0003 16.6668"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="info-content">
              <span class="info-label">닉네임</span>
              <input class="info-value" v-model="form.nickname" />
            </div>
          </div>

          <!-- 비밀번호 -->
          <div class="info-item">
            <div class="info-icon">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M15.8333 9.16675H4.16667C3.24619 9.16675 2.5 9.91294 2.5 10.8334V16.6667C2.5 17.5872 3.24619 18.3334 4.16667 18.3334H15.8333C16.7538 18.3334 17.5 17.5872 17.5 16.6667V10.8334C17.5 9.91294 16.7538 9.16675 15.8333 9.16675Z"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
                <path
                  d="M5.83301 9.16675V5.83341C5.83301 4.72835 6.27199 3.66854 7.0534 2.88714C7.8348 2.10573 8.89461 1.66675 9.99967 1.66675C11.1047 1.66675 12.1646 2.10573 12.946 2.88714C13.7274 3.66854 14.1663 4.72835 14.1663 5.83341V9.16675"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="info-content">
              <span class="info-label">새 비밀번호</span>
              <input
                class="info-value"
                type="password"
                placeholder="사용할 비밀번호를 입력해주세요"
                v-model="form.password"
              />
            </div>
          </div>

          <!-- 권한 -->
          <div class="info-item">
            <div class="info-icon">
              <svg
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M16.6663 10.8333C16.6663 15 13.7497 17.0833 10.283 18.2916C10.1015 18.3532 9.90429 18.3502 9.72467 18.2833C6.24967 17.0833 3.33301 15 3.33301 10.8333V4.99997C3.33301 4.77895 3.42081 4.56699 3.57709 4.41071C3.73337 4.25443 3.94533 4.16663 4.16634 4.16663C5.83301 4.16663 7.91634 3.16663 9.36634 1.89997C9.54289 1.74913 9.76747 1.66626 9.99967 1.66626C10.2319 1.66626 10.4565 1.74913 10.633 1.89997C12.0913 3.17497 14.1663 4.16663 15.833 4.16663C16.054 4.16663 16.266 4.25443 16.4223 4.41071C16.5785 4.56699 16.6663 4.77895 16.6663 4.99997V10.8333Z"
                  stroke="#4A5565"
                  stroke-width="1.66667"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </div>
            <div class="info-content">
              <span class="info-label">권한</span>
              <p class="info-value" v-if="userInfo">
                {{ userInfo.role === "ADMIN" ? "관리자" : "일반 사용자" }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { getMyInfo, updateMyInfo } from "@/api/user/user";
import { useRouter } from "vue-router";
import { checkNicknameApi } from "@/api/user/authApi";

const router = useRouter();
const userInfo = ref(null);
const isLoading = ref(true); // 로딩 상태를 추적하는 변수

const form = reactive({
  name: "",
  nickname: "",
  password: "",
});

onMounted(async () => {
  try {
    const res = await getMyInfo();

    userInfo.value = res.data;

    form.name = res.data.name;
    form.nickname = res.data.nickname;
  } catch (e) {
    console.error("유저 정보 로드 실패", e);
  } finally {
    isLoading.value = false; // API 호출 완료 후 로딩 상태 해제
  }
});

const goEdit = () => {
  router.push({ name: "MemberInfoEdit" });
};

const cancel = () => {
  router.push("/mypage/profile");
};

const save = async () => {
  /* 빈 값 체크 */
  if (!form.name || !form.nickname || !form.password) {
    alert("값을 모두 입력해주세요.");
    return;
  }

  /* 비밀번호 길이 체크 (입력했을 때만) */
  if (form.password && form.password.length < 6) {
    alert("비밀번호는 6자리 이상이어야 합니다.");
    return;
  }

  /* 닉네임 중복 체크 (변경된 경우만) */
  if (form.nickname !== userInfo.value.nickname) {
    const res = await checkNicknameApi(form.nickname);

    if (res.data.isDuplicate) {
      alert("이미 사용 중인 닉네임입니다.");
      return;
    }
  }

  /* 실제 저장 API 호출 */
  await updateMyInfo({
    email: userInfo.value.email,
    name: form.name,
    nickname: form.nickname,
    password: form.password || null,
  });

  alert("회원정보가 저장되었습니다.");
  router.push("/mypage/profile");
};

const handleMenuChange = (menu) => {
  console.log("Selected menu:", menu);
  // 메뉴 변경에 따른 로직 처리
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Arimo:wght@400;500;600;700&display=swap");

.mypage {
  display: flex;
  min-height: 100vh;
  background: #ffffff;
  font-family: "Arimo", -apple-system, BlinkMacSystemFont, sans-serif;
}

/* 메인 콘텐츠 */
.main-content {
  flex: 1;
  padding: 2rem 3rem;
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

/* 페이지 헤더 */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-title {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.cancel-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1rem;
  background: #7c3aed;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.cancel-button:hover {
  background: #6d28d9;
}

.save-button {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.625rem 1rem;
  background: #7c3aed;
  color: #ffffff;
  border: none;
  border-radius: 10px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.2s ease;
}

.save-button:hover {
  background: #6d28d9;
}

/* 프로필 카드 */
.profile-card {
  background: #ffffff;
  border: 0.83px solid #e5e7eb;
  border-radius: 16px;
  overflow: hidden;
}

.button-group {
  display: flex;
  gap: 0.75rem; /* 취소 ↔ 저장 간격 */
}

/* 프로필 헤더 */
.profile-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1.5rem;
  background: rgba(243, 244, 246, 0.3);
  border-bottom: 0.83px solid #e5e7eb;
}

.profile-avatar {
  width: 80px;
  height: 80px;
  background: #ddd6fe;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.profile-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.profile-name {
  font-size: 16px;
  font-weight: 400;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

.profile-nickname {
  font-size: 16px;
  color: #4a5568;
  margin: 0;
  line-height: 1.5;
}

.user-badge {
  display: inline-block;
  padding: 0.125rem 0.75rem;
  background: #ffffff;
  color: #7c3aed;
  font-size: 14px;
  border-radius: 9999px;
  line-height: 1.43;
  margin-top: 0.5rem;
  width: fit-content;
}

/* 정보 목록 */
.info-list {
  display: flex;
  flex-direction: column;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 1.5rem;
  border-bottom: 0.83px solid #f3f4f6;
}

.info-item:last-child {
  border-bottom: none;
}

.info-icon {
  width: 40px;
  height: 40px;
  background: #f3f4f6;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.info-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.info-label {
  font-size: 14px;
  color: #4a5568;
  line-height: 1.43;
}

.info-value {
  font-size: 16px;
  color: #1e1e1e;
  margin: 0;
  line-height: 1.5;
}

/* 반응형 - 태블릿 */
@media (max-width: 1024px) {
  .mypage {
    flex-direction: column;
  }

  .main-content {
    padding: 2rem 1.5rem;
  }
}

/* 반응형 - 모바일 */
@media (max-width: 768px) {
  .main-content {
    padding: 1.5rem 1rem;
  }

  .page-header {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
  }

  .save-button {
    width: 100%;
    justify-content: center;
  }

  .profile-header {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }

  .profile-avatar {
    width: 64px;
    height: 64px;
  }

  .profile-info {
    align-items: center;
  }

  .info-item {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.75rem;
    padding: 1.25rem;
  }

  .info-icon {
    width: 36px;
    height: 36px;
  }

  .withdraw-section {
    justify-content: center;
  }
}

@media (max-width: 480px) {
  .main-content {
    padding: 1rem 0.75rem;
  }

  .profile-header {
    padding: 1.25rem;
  }

  .info-item {
    padding: 1rem;
  }
}
</style>
