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
      path: "/spots",
      name: "SpotExploration",
      component: () => import("@/views/spot/SpotExploration.vue"),
    },
    {
      path: "/plans",
      name: "TravelPlanner",
      component: () => import("@/views/plan/TravelPlanner.vue"),
    },
    {
      path: "/mypage",
      component: () => import("@/views/mypage/MyPageLayout.vue"),
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
      ],
    },
  ],
});

export default router;
