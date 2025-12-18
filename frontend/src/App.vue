<template>
  <Header />
  <RouterView></RouterView>
</template>

<script setup>
import { RouterView } from "vue-router";
import Header from "./components/layout/Header.vue";
import { onMounted } from "vue";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();

onMounted(() => {
  authStore.fetchMe();
});
</script>

<style scoped></style>
