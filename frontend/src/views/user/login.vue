<template>
  <div class="w-[1920px] h-[1080px] relative bg-white overflow-hidden">
    <!-- 중앙 로그인 로고 -->
    <img
      class="w-52 h-12 left-[825px] top-[276px] absolute"
      src="https://placehold.co/201x52"
      alt="PlanIt Logo"
    />

    <!-- 로그인 폼 -->
    <div class="w-[609px] min-w-80 p-6 left-[624px] top-[367px] absolute bg-white rounded-lg border border-[#D9D9D9] flex flex-col justify-start items-start gap-6">
      <!-- 이메일 입력 -->
      <div class="self-stretch flex flex-col justify-start items-start gap-2">
        <label class="self-stretch text-[#1E1E1E] text-base font-normal font-['Inter'] leading-6">
          이메일
        </label>
        <input
          v-model="email"
          type="email"
          placeholder="이메일을 입력해주세요"
          class="self-stretch min-w-28 px-4 py-3 bg-white rounded-lg border border-[#D9D9D9] text-base font-normal font-['Inter'] leading-4 placeholder:text-[#B3B3B3] focus:outline-none focus:border-[#2C2C2C]"
        />
      </div>

      <!-- 비밀번호 입력 -->
      <div class="self-stretch flex flex-col justify-start items-start gap-2">
        <label class="self-stretch text-[#1E1E1E] text-base font-normal font-['Inter'] leading-6">
          비밀번호
        </label>
        <input
          v-model="password"
          type="password"
          placeholder="비밀번호를 입력해주세요"
          class="self-stretch min-w-28 px-4 py-3 bg-white rounded-lg border border-[#D9D9D9] text-base font-normal font-['Inter'] leading-4 placeholder:text-[#B3B3B3] focus:outline-none focus:border-[#2C2C2C]"
        />
      </div>

      <!-- 로그인 버튼 -->
      <button
        @click="handleLogin"
        class="self-stretch p-3 bg-[#2C2C2C] rounded-lg border border-[#2C2C2C] flex justify-center items-center gap-2 overflow-hidden hover:bg-[#1E1E1E] transition-colors"
      >
        <span class="text-[#F5F5F5] text-base font-normal font-['Inter'] leading-4">
          로그인
        </span>
      </button>

      <!-- 회원가입 링크 -->
      <a
        href="#"
        @click.prevent="goToSignup"
        class="text-[#1E1E1E] text-base font-normal font-['Inter'] underline leading-6 hover:text-[#2C2C2C] cursor-pointer"
      >
        회원가입
      </a>

      <!-- 비밀번호 찾기 링크 -->
      <a
        href="#"
        @click.prevent="goToFindPassword"
        class="left-[489px] top-[276px] absolute text-right text-[#1E1E1E] text-base font-normal font-['Inter'] underline leading-6 hover:text-[#2C2C2C] cursor-pointer"
      >
        비밀번호 찾기
      </a>
    </div>

    <!-- 상단 네비게이션 바 -->
    <div class="w-[1920px] p-8 left-0 top-0 absolute bg-white border-b border-[#D9D9D9] flex justify-start items-center gap-6 flex-wrap overflow-hidden">
      <!-- 로고 -->
      <div class="flex justify-start items-center gap-6">
        <img
          class="w-52 h-12 cursor-pointer"
          src="https://placehold.co/201x52"
          alt="PlanIt"
          @click="goToHome"
        />
      </div>

      <!-- 네비게이션 메뉴 -->
      <div class="flex-1 flex justify-end items-start gap-2 flex-wrap">
        <button
          @click="navigateTo('explore')"
          class="p-2 rounded-lg flex justify-center items-center gap-2 hover:bg-gray-100 transition-colors"
        >
          <span class="text-[#1E1E1E] text-base font-normal font-['Inter'] leading-4">
            스팟 탐색
          </span>
        </button>
        <button
          @click="navigateTo('planner')"
          class="p-2 rounded-lg flex justify-center items-center gap-2 hover:bg-gray-100 transition-colors"
        >
          <span class="text-[#1E1E1E] text-base font-normal font-['Inter'] leading-4">
            여행 플래너
          </span>
        </button>
        <button
          @click="navigateTo('mypage')"
          class="p-2 rounded-lg flex justify-center items-center gap-2 hover:bg-gray-100 transition-colors"
        >
          <span class="text-[#1E1E1E] text-base font-normal font-['Inter'] leading-4">
            마이페이지
          </span>
        </button>
      </div>

      <!-- 프로필 아이콘 -->
      <img
        class="w-8 h-8 cursor-pointer"
        src="https://placehold.co/34x34"
        alt="Profile"
        @click="goToProfile"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: 'PlanItLogin',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    handleLogin() {
      if (!this.email || !this.password) {
        alert('이메일과 비밀번호를 입력해주세요')
        return
      }

      // 로그인 로직 구현
      console.log('로그인:', { email: this.email, password: this.password })
      // API 호출 등의 로직을 여기에 추가
    },
    goToSignup() {
      console.log('회원가입 페이지로 이동')
      // 라우터 이동 로직
    },
    goToFindPassword() {
      console.log('비밀번호 찾기 페이지로 이동')
      // 라우터 이동 로직
    },
    goToHome() {
      console.log('홈으로 이동')
      // 라우터 이동 로직
    },
    navigateTo(page) {
      console.log(`${page} 페이지로 이동`)
      // 라우터 이동 로직
    },
    goToProfile() {
      console.log('프로필 페이지로 이동')
      // 라우터 이동 로직
    }
  }
}
</script>

<style scoped>
/* Inter 폰트가 필요한 경우 */
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap');

/* 추가 스타일이 필요한 경우 여기에 작성 */
</style>
