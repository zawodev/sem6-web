<template>
  <MainLayout>
    <router-view />
  </MainLayout>
</template>

<script>
import MainLayout from '@/components/MainLayout.vue'
export default {
  name: 'App',
  components: {
    MainLayout
  }
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
