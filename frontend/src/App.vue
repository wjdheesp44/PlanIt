<template>
  <Header />
  <RouterView></RouterView>
</template>

<script setup>
import { RouterView } from "vue-router";
import Header from "./components/layout/Header.vue";
</script>

<style scoped></style>
