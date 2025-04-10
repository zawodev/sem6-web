<template>
  <!-- Główny layout może być zdefiniowany tutaj lub jako osobny komponent (np. MainLayout.vue) -->
  <!-- W tym przykładzie korzystamy z router-view, co powoduje renderowanie widoków zgodnie z konfiguracją routera -->
  <router-view />
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* Globalne style dla całej aplikacji */
body {
  margin: 0;
  font-family: 'Helvetica Neue', Arial, sans-serif;
  background-color: #f5f5f5;
}
</style>
