<template>
  <MainLayout>
    <div class="container">
      <h1 class="section-title">Czytelnicy</h1>
      <form @submit.prevent="saveReader" class="form">
        <input v-model="readerForm.name" type="text" placeholder="Imię i nazwisko" required />
        <button type="submit" class="btn submit-btn">{{ readerForm.id ? 'Aktualizuj' : 'Dodaj' }}</button>
        <button v-if="readerForm.id" type="button" @click="resetForm" class="btn cancel-btn">Anuluj</button>
      </form>

      <table class="styled-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Imię i nazwisko</th>
            <th>Akcje</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="reader in paginatedReaders" :key="reader.id">
            <td>{{ reader.id }}</td>
            <td>{{ reader.name }}</td>
            <td>
              <button class="btn edit-btn" @click="editReader(reader)">Edytuj</button>
              <button class="btn delete-btn" @click="deleteReader(reader.id)">Usuń</button>
            </td>
          </tr>
        </tbody>
      </table>

      <AppPagination 
        :total-items="readers.length" 
        :current-page="currentPageReaders" 
        @update:currentPage="currentPageReaders = $event" 
        :page-size="pageSize" 
      />

      <h2 class="section-title">Wypożyczenia</h2>
      <table class="styled-table">
        <thead>
          <tr>
            <th>ID książki</th>
            <th>Tytuł</th>
            <th>Wypożyczona przez</th>
            <th>Akcje</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="info in paginatedLendingInfos" :key="info.bookId">
            <td>{{ info.bookId }}</td>
            <td>{{ info.bookTitle }}</td>
            <td>{{ info.borrower ? info.borrower.name : '---' }}</td>
            <td>
              <button
                v-if="info.borrower"
                class="btn delete-btn"
                @click="returnBook(info.bookId)"
              >
                Zwrot
              </button>
              <span v-else>✔</span>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Paginacja -->
      
      <AppPagination 
        :total-items="lendingInfos.length" 
        :current-page="currentPageLendings" 
        @update:currentPage="currentPageLendings = $event" 
        :page-size="pageSize" 
      />
    </div>
  </MainLayout>
</template>

<script>
import axios from 'axios'
import MainLayout from '@/components/MainLayout.vue'
import AppPagination from '@/components/AppPagination.vue'

export default {
  name: 'ReadersAndLendingsView',
  components: { MainLayout, AppPagination },
  data() {
    return {
      readers: [],
      lendingInfos: [],
      readerForm: {
        id: null,
        name: ''
      },
      currentPageReaders: 1, // Bieżąca strona dla czytelników
      currentPageLendings: 1, // Bieżąca strona dla wypożyczeń
      pageSize: 5, // Liczba elementów na stronę
    }
  },
  created() {
    this.fetchReaders()
    this.fetchLendingInfos()
  },
  computed: {
    // Paginowane 
    paginatedReaders() {
      const start = (this.currentPageReaders - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.readers.slice(start, end);
    },
    // Paginowane
    paginatedLendingInfos() {
      const start = (this.currentPageLendings - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.lendingInfos.slice(start, end);
    }
  },
  methods: {
    async fetchReaders() {
      try {
        const res = await axios.get('http://localhost:9090/readers')
        this.readers = res.data
      } catch (err) {
        console.error('Błąd pobierania czytelników:', err)
      }
    },
    async fetchLendingInfos() {
      try {
        const res = await axios.get('http://localhost:9090/lending')
        this.lendingInfos = res.data
      } catch (err) {
        console.error('Błąd pobierania wypożyczeń:', err)
      }
    },
    async saveReader() {
      try {
        if (this.readerForm.id) {
          await axios.put(`http://localhost:9090/readers/${this.readerForm.id}`, this.readerForm)
        } else {
          await axios.post('http://localhost:9090/readers', this.readerForm)
        }
        this.resetForm()
        this.fetchReaders()
      } catch (err) {
        console.error('Błąd zapisu czytelnika:', err)
      }
    },
    editReader(reader) {
      this.readerForm = { ...reader }
    },
    async deleteReader(id) {
      try {
        await axios.delete(`http://localhost:9090/readers/${id}`)
        this.fetchReaders()
      } catch (err) {
        console.error('Błąd usuwania czytelnika:', err)
      }
    },
    async returnBook(bookId) {
      try {
        await axios.put(`http://localhost:9090/lending/${bookId}/return`)
        this.fetchLendingInfos()
      } catch (err) {
        console.error('Błąd zwrotu książki:', err)
      }
    },
    resetForm() {
      this.readerForm = { id: null, name: '' }
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 1000px;
  margin: 30px auto;
  background-color: #ffffff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 128, 0, 0.1);
  font-family: 'Segoe UI', sans-serif;
}

.section-title {
  color: #2e7d32;
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 16px;
  text-align: center;
}

.form {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.form input {
  flex: 1;
  padding: 8px;
  border: 1px solid #a5d6a7;
  border-radius: 6px;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 32px;
}

.styled-table th,
.styled-table td {
  padding: 10px 14px;
  border: 1px solid #c8e6c9;
}

.styled-table thead {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.styled-table tbody tr:hover {
  background-color: #f1f8e9;
}

.btn {
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
  font-weight: 500;
}

.submit-btn {
  background-color: #388e3c;
  color: white;
}

.submit-btn:hover {
  background-color: #2e7d32;
}

.cancel-btn {
  background-color: #bbb;
  color: white;
}

.edit-btn {
  background-color: #66bb6a;
  color: white;
  margin-right: 6px;
}

.edit-btn:hover {
  background-color: #57a05c;
}

.delete-btn {
  background-color: #ef5350;
  color: white;
}

.delete-btn:hover {
  background-color: #d32f2f;
}
</style>