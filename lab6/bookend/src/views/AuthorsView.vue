<template>
  <MainLayout>
    <div class="author-container">
      <h1 class="page-title">Lista Autorów</h1>

      <table class="author-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nazwa</th>
            <th>Akcje</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="author in paginatedAuthors" :key="author.id">
            <td>{{ author.id }}</td>
            <td>{{ author.name }}</td>
            <td>
              <button class="btn edit-btn" @click="editAuthor(author)">Edytuj</button>
              <button class="btn delete-btn" @click="deleteAuthor(author.id)">Usuń</button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="pagination-wrapper">
        <AppPagination
          :total-items="authors.length"
          v-model:currentPage="currentPage"
          :page-size="pageSize"
        />
      </div>

      <div class="form-section">
        <h2 class="form-title">{{ isEdit ? 'Edytuj autora' : 'Dodaj nowego autora' }}</h2>
        <form @submit.prevent="handleSubmit" class="author-form">
          <div class="form-group">
            <label>Nazwa:</label>
            <input v-model="authorForm.name" required />
          </div>
          <div class="form-buttons">
            <button type="submit" class="btn submit-btn">
              {{ isEdit ? 'Aktualizuj' : 'Dodaj' }}
            </button>
            <button type="button" class="btn cancel-btn" v-if="isEdit" @click="cancelEdit">
              Anuluj
            </button>
          </div>
        </form>
      </div>
    </div>
  </MainLayout>
</template>

<script>
import MainLayout from '@/components/MainLayout.vue'
import AppPagination from '@/components/AppPagination.vue'
import api from '@/api'; 

export default {
  name: 'AuthorsView',
  components: {
    MainLayout,
    AppPagination
  },
  data() {
    return {
      authors: [],
      authorForm: {
        id: null,
        name: ''
      },
      isEdit: false,
      currentPage: 1,
      pageSize: 5
    }
  },
  computed: {
    paginatedAuthors() {
      const start = (this.currentPage - 1) * this.pageSize;
      return this.authors.slice(start, start + this.pageSize)
    }
  },
  created() {
    this.fetchAuthors();
  },
  methods: {
    // Pobiera listę autorów z REST API
    fetchAuthors() {
      api.get('/authors')
        .then(response => {
          this.authors = response.data;
        })
        .catch(error => {
          console.error('Błąd przy pobieraniu autorów:', error);
        });
    },
    // Dodawanie lub aktualizacja autora
    handleSubmit() {
      if (this.isEdit) {
        api.put(`/authors/${this.authorForm.id}`, this.authorForm)
          .then(() => {
            this.fetchAuthors();
            this.resetForm();
          })
          .catch(error => console.error('Błąd przy aktualizacji:', error));
      } else {
        api.post('/authors', this.authorForm)
          .then(() => {
            this.fetchAuthors();
            this.resetForm();
          })
          .catch(error => console.error('Błąd przy dodawaniu:', error));
      }
    },
    editAuthor(author) {
      this.authorForm = { ...author };
      this.isEdit = true;
    },
    cancelEdit() {
      this.resetForm();
    },
    deleteAuthor(id) {
      api.delete(`/authors/${id}`)
        .then(() => {
          this.fetchAuthors();
        })
        .catch(error => console.error('Błąd przy usuwaniu:', error));
    },
    resetForm() {
      this.authorForm = { id: null, name: '' };
      this.isEdit = false;
    }
  }
}
</script>

<style scoped>
.author-container {
  max-width: 800px;
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
  margin-bottom: 20px;
  text-align: center;
}

.author-table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.author-table th,
.author-table td {
  padding: 12px;
  border: 1px solid #c8e6c9;
  text-align: left;
}

.author-table thead {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.author-table tbody tr:hover {
  background-color: #f1f8e9;
}

.btn {
  padding: 6px 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  transition: background-color 0.2s ease;
}

.edit-btn {
  background-color: #66bb6a;
  color: white;
}

.edit-btn:hover {
  background-color: #57a05c;
}

.delete-btn {
  background-color: #ef5350;
  color: white;
  margin-left: 6px;
}

.delete-btn:hover {
  background-color: #d32f2f;
}

.pagination-wrapper {
  text-align: center;
  margin-top: 10px;
  margin-bottom: 20px;
}

.form-section {
  border-top: 1px solid #c8e6c9;
  padding-top: 20px;
}

.form-title {
  color: #2e7d32;
  font-size: 20px;
  margin-bottom: 12px;
}

.author-form .form-group {
  margin-bottom: 12px;
}

.author-form label {
  display: block;
  font-weight: 500;
  margin-bottom: 6px;
}

.author-form input {
  width: 100%;
  padding: 8px;
  border: 1px solid #a5d6a7;
  border-radius: 6px;
  box-sizing: border-box;
  font-size: 14px;
}

.form-buttons {
  margin-top: 10px;
}

.submit-btn {
  background-color: #388e3c;
  color: white;
}

.submit-btn:hover {
  background-color: #2e7d32;
}

.cancel-btn {
  background-color: #ccc;
  color: #333;
  margin-left: 8px;
}

.cancel-btn:hover {
  background-color: #bbb;
}
</style>