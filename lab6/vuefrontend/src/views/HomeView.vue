<template>
    <div class="home-container">
      <h1 class="title">Biblioteka Aliksandryjska</h1>
      <p class="description">Uważaj żeby nie zgubić książek! Nie kupiliśmy ich. Poniżej założyciele</p>

      <div class="founders">
        <div class="founder">
          <img src="@/assets/founder1.png" alt="Founder 1" />
          <p>Jakub Borsuk</p>
          <p class="psmaller">Przeczytał 2 książki</p>
        </div>
        <div class="founder">
          <img src="@/assets/founder2.png" alt="Founder 2" />
          <p>Aleksander Stepaniuk</p>
          <p class="psmaller">Nigdy nie przeczytał książki </p>
        </div>
        <div class="founder">
          <img src="@/assets/founder3.png" alt="Founder 3" />
          <p>Daniel Lopez</p>
          <p class="psmaller">Nie mam pojęcia kto to jest jest po prostu na moim komputerze nie wiem czy używanie tego zdjęcia nie narusza jakiejś klauzuli RODO</p>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: 'HomeView'
}
</script>

<style scoped>
.title{
  color: #2e7d32;
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 24px;
}

.home-container {
  text-align: center;
  padding: 40px 20px;
}

.description {
  margin-bottom: 40px;
  font-size: 1.2rem;
  color: #555;
}

.founders {
  display: flex;
  justify-content: center;
  gap: 40px;
  flex-wrap: wrap;
}

.founder {
  max-width: 200px;
}

.founder img {
  width: 100%;
  height: auto;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.founder img:hover {
  transform: scale(1.05);
}

.founder p {
  margin-top: 10px;
  font-weight: bold;
  color: #333;
}

.psmaller {
  margin-top: 10px;
  font-weight: bold;
  color: #333;
  font-size: small;
}
</style>