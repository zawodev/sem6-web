<template>
    <div>
      <header>
        <nav>
          <router-link to="/">Home</router-link> |
          <router-link to="/authors">Autorzy</router-link> |
          <router-link to="/books">Książki</router-link> |
          <router-link to="/readers">Czytelnicy</router-link> |
        </nav>
      </header>
      <main>
        <!-- Wyświetlanie podstrony -->
        <slot></slot>
      </main>
      <footer>
        <p>Books Frontend &copy; 2025</p>
      </footer>
    </div>
  </template>
  
  <script>
  export default {
    name: 'MainLayout'
  }
  </script>
  
  <style scoped>
  nav {
    background: #eef;
    padding: 10px;
  }
  nav a {
    margin-right: 10px;
    text-decoration: none;
    color: #333;
  }
  </style>