<template>
  <MainLayout>
    <div class="books-container">
      <h1 class="page-title">Lista książek</h1>

      <!-- Formularz -->
      <div class="form-section">
        <h2 class="form-title">{{ bookForm.id ? 'Edytuj książkę' : 'Dodaj nową książkę' }}</h2>
        <form @submit.prevent="saveBook" class="book-form">
          <div class="form-group">
            <label for="title">Tytuł</label>
            <input v-model="bookForm.title" type="text" id="title" required />
          </div>

          <div class="form-group">
            <label for="author">Autor</label>
            <select v-model="bookForm.authorId" id="author" required>
              <option disabled value="">Wybierz autora</option>
              <option v-for="author in authors" :key="author.id" :value="author.id">
                {{ author.name }}
              </option>
            </select>
          </div>

          <div class="form-group">
            <label for="pages">Liczba stron</label>
            <input v-model.number="bookForm.pages" type="number" id="pages" min="1" required />
          </div>

          <div class="form-buttons">
            <button type="submit" class="btn submit-btn">
              {{ bookForm.id ? 'Aktualizuj' : 'Dodaj' }}
            </button>
            <button type="button" v-if="bookForm.id" @click="resetForm" class="btn">
              Anuluj
            </button>
          </div>
        </form>
      </div>

      <!-- Tabela  -->
      <table class="book-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Tytuł</th>
            <th>Autor</th>
            <th>Strony</th>
            <th>Wypożyczona przez</th>
            <th>Akcje</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="book in paginatedBooks" :key="book.id">
            <td>{{ book.id }}</td>
            <td>{{ book.title }}</td>
            <td>{{ book.author.name }}</td>
            <td>{{ book.pages }}</td>
            <td>{{ book.borrower ? book.borrower.name : '---' }}</td>
            <td>
              <button @click="editBook(book)" class="btn edit-btn">Edytuj</button>
              <button @click="toggleBorrowDropdown(book.id)" class="btn" style="background-color: #42a5f5; color: white; margin: 0 6px;">
                Wypożycz
              </button>
              <button @click="deleteBook(book.id)" class="btn delete-btn">Usuń</button>

              <div v-if="borrowDropdownBookId === book.id" style="margin-top: 8px;">
                <select @change="borrowBook(book.id, $event.target.value)" class="btn" style="margin-top: 6px;">
                  <option disabled selected value="">Wybierz czytelnika</option>
                  <option v-for="reader in readers" :key="reader.id" :value="reader.id">{{ reader.name }}</option>
                </select>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <AppPagination
        :totalItems="books.length"
        :currentPage="currentPage"
         @update:currentPage="currentPage = $event"
        :pageSize="pageSize"
      />
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from '@/components/MainLayout.vue'
import AppPagination from '@/components/AppPagination.vue'
import axios from 'axios'

export default {
  name: 'BooksView',
  components: { MainLayout, AppPagination },
  data() {
    return {
      readers: [],
      borrowDropdownBookId: null,
      books: [],
      authors: [],
      bookForm: {
        id: null,
        title: '',
        authorId: '',
        pages: ''
      },
      currentPage: 1,
      pageSize: 5,
    }
  },
  created() {
    this.fetchBooks()
    this.fetchAuthors()
  },
  computed: {
    paginatedBooks() {
      const start = (this.currentPage - 1) * this.pageSize
      const end = start + this.pageSize
      return this.books.slice(start, end)
    }
  },
  methods: {
    async fetchReaders() {
      try {
        const res = await axios.get('http://localhost:9090/readers')
        this.readers = res.data
      } catch (e) {
        console.error('Błąd przy pobieraniu czytelników:', e)
      }
    },

    toggleBorrowDropdown(bookId) {
      if (this.borrowDropdownBookId === bookId) {
        this.borrowDropdownBookId = null
      } else {
        this.borrowDropdownBookId = bookId
        this.fetchReaders()
      }
    },

    async borrowBook(bookId, readerId) {
      if (!readerId) return
      try {
        await axios.put(`http://localhost:9090/lending/${bookId}/borrow/${readerId}`)
        this.borrowDropdownBookId = null
        this.fetchBooks()
      } catch (e) {
        console.error('Błąd przy wypożyczaniu książki:', e)
      }
    },
    async fetchBooks() {
      try {
        const res = await axios.get('http://localhost:9090/books')
        this.books = res.data
      } catch (e) {
        console.error('Błąd przy pobieraniu książek:', e)
      }
    },
    async fetchAuthors() {
      try {
        const res = await axios.get('http://localhost:9090/authors')
        this.authors = res.data
      } catch (e) {
        console.error('Błąd przy pobieraniu autorów:', e)
      }
    },
    async saveBook() {
      const author = this.authors.find(a => a.id === this.bookForm.authorId)
      const payload = {
        id: this.bookForm.id,
        title: this.bookForm.title,
        author,
        pages: this.bookForm.pages
      }

      try {
        if (payload.id) {
          await axios.put(`http://localhost:9090/books/${payload.id}`, payload)
        } else {
          await axios.post('http://localhost:9090/books', payload)
        }
        this.resetForm()
        this.fetchBooks()
      } catch (e) {
        console.error('Błąd przy zapisie książki:', e)
      }
    },
    editBook(book) {
      this.bookForm = {
        id: book.id,
        title: book.title,
        authorId: book.author.id,
        pages: book.pages
      }
    },
    async deleteBook(id) {
      try {
        await axios.delete(`http://localhost:9090/books/${id}`)
        this.fetchBooks()
      } catch (e) {
        console.error('Błąd przy usuwaniu książki:', e)
      }
    },
    resetForm() {
      this.bookForm = {
        id: null,
        title: '',
        authorId: '',
        pages: ''
      }
    }
  }
}
</script>

<style scoped>
.books-container {
  max-width: 1000px;
  margin: 30px auto;
  background-color: #ffffff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 128, 0, 0.1);
  font-family: 'Segoe UI', sans-serif;
}

.page-title {
  color: #2e7d32;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 24px;
  text-align: center;
}

.form-section {
  margin-bottom: 30px;
  border-top: 1px solid #c8e6c9;
  padding-top: 20px;
}

.form-title {
  color: #2e7d32;
  font-size: 20px;
  margin-bottom: 12px;
}

.book-form {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 16px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 500;
  margin-bottom: 6px;
}

.book-form input,
.book-form select {
  padding: 8px;
  border: 1px solid #a5d6a7;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-buttons {
  grid-column: span 2;
  text-align: right;
}

.btn {
  padding: 8px 16px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s ease;
}

.submit-btn {
  background-color: #388e3c;
  color: white;
}

.submit-btn:hover {
  background-color: #2e7d32;
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

.book-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.book-table th,
.book-table td {
  padding: 12px;
  border: 1px solid #c8e6c9;
  text-align: left;
}

.book-table thead {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.book-table tbody tr:hover {
  background-color: #f1f8e9;
}
</style>