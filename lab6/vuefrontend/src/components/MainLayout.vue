<template>
  <div class="layout-wrapper">
    <header>
      <nav>
        <h3 class="title">Biblioteka Aliksandryjska</h3>
        <div class="nav-links">
          <router-link to="/">Home</router-link>
          <router-link to="/authors">Autorzy</router-link>
          <router-link to="/books">Książki</router-link>
          <router-link to="/readers">Czytelnicy</router-link>
        </div>
      </nav>
    </header>
    <main>
      <slot></slot>
    </main>
    <footer>
      <p>FututusDevelopment &copy; 2025</p>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'MainLayout'
}
</script>

<style scoped>
.layout-wrapper {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
header {
  background: #f0f0f0;
}
nav {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 20px;
}
.title {
  color: #2e7d32;
  font-weight: bold;
  margin: 0;
}
.nav-links {
  display: flex;
  gap: 20px;
}
nav a {
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #4caf50;
  color: white;
  text-decoration: none;
  padding: 10px 16px;
  border-radius: 6px;
  transition: background-color 0.3s;
  text-align: center;
}
nav a:hover {
  background-color: #45a049;
}
main {
  flex: 1;
  padding: 20px;
}
footer {
  text-align: center;
  padding: 15px;
  background: #eee;
  color: #666;
  font-size: 0.9em;
}
</style>
