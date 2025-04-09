<template>
    <MainLayout>
      <div>
        <h1>Strona Główna</h1>
        <p>Witamy na stronie aplikacji Books!</p>
      </div>
    </MainLayout>
  </template>
  
  <script>
  import MainLayout from '@/components/MainLayout.vue'
  
  export default {
    name: 'HomeView',
    components: {
      MainLayout
    }
  }
  </script>