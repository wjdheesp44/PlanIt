import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "Home",
      component: () => import("@/views/Home.vue"),
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("@/views/auth/Login.vue"),
    },
    {
      path: "/signup",
      name: "Signup",
      component: () => import("@/views/auth/Signup.vue"),
    },
    {
      path: "/forgot-password-email",
      name: "ForgotPasswordEmail",
      component: () => import("@/views/auth/Forgotpasswordemail.vue"),
    },
    {
      path: "/forgot-password",
      name: "Forgotpassword",
      component: () => import("@/views/auth/Forgotpassword.vue"),
    },
    {
      path: "/spots",
      name: "SpotExploration",
      component: () => import("@/views/spot/SpotExploration.vue"),
    },
    {
      path: "/spots/:id",
      name: "SpotDetail",
      component: () => import("@/views/spot/SpotDetail.vue"),
    },
    {
      path: "/plans",
      name: "TravelPlanner",
      component: () => import("@/views/plan/TravelPlanner.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/plans/:id",
      name: "TravelPlan",
      component: () => import("@/views/plan/TravelPlan.vue"),
      meta: { requiresAuth: true },
    },
    {
      path: "/mypage",
      component: () => import("@/views/mypage/MyPageLayout.vue"),
      meta: { requiresAuth: true },
      children: [
        {
          path: "profile",
          name: "MyPage",
          component: () => import("@/views/mypage/Profile.vue"),
        },
        {
          path: "liked-spots",
          name: "LikedSpots",
          component: () => import("@/views/mypage/LikedSpots.vue"),
        },
        {
          path: "report",
          name: "Report",
          component: () => import("@/views/mypage/Report.vue"),
        },
        {
          path: "reviews",
          name: "Reviews",
          component: () => import("@/views/mypage/Reviews.vue"),
        },
        {
          path: "profile/edit",
          name: "MemberInfo",
          component: () => import("@/views/mypage/MemberInfo.vue"),
        },
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  const isLogin = !!localStorage.getItem("accessToken");

  if (to.matched.some((record) => record.meta.requiresAuth) && !isLogin) {
    next("/login");
  } else {
    next();
  }
});

export default router;
