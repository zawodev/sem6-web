<template>
  <div>
    <header>
      <nav>
        <h3 class="title">Biblioteka Aliksandryjska</h3> 
        <router-link to="/">Home</router-link>
        <router-link to="/authors">Autorzy</router-link>
        <router-link to="/books">Książki</router-link>
        <router-link to="/readers">Czytelnicy</router-link>
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
.title{
  color: #2e7d32;
  font-weight: bold;
  margin-bottom: 24px;
  text-align: left;
}

body {
  margin: 0;
  font-family: Arial, sans-serif;
}

nav {
  background: #f0f0f0;
  display: flex;
  gap: 10px;
}

nav a {
  padding: 10px 16px;
  background-color: #4caf50;
  color: white;
  text-decoration: none;
  border-radius: 6px;
  transition: background-color 0.3s;

  display: flex;
  justify-content: center;
  padding: 15px;
}

nav a:hover {
  background-color: #45a049;
}

main {
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