<template>
  <header>
    <RouterLink to="#"><img src="@/assets/img/logo.png" alt="홈 버튼" /></RouterLink>
    <nav>
      <ul>
        <li><RouterLink to="/">홈</RouterLink></li>
      </ul>
    </nav>
  </header>
</template>

<script setup></script>

<style scoped>
header {
  border: 1px solid black;

  ul {
    display: flex;
    justify-content: space-around;
  }
}
</style>
