<template>
  <div class="pagination-container">
    <button :disabled="currentPage === 1" @click="changePage(currentPage - 1)">Poprzednia</button>
    <span>Strona {{ currentPage }} z {{ totalPages }}</span>
    <button :disabled="currentPage === totalPages" @click="changePage(currentPage + 1)">Następna</button>
  </div>
</template>

<script>
export default {
  name: 'AppPagination',
  props: {
    totalItems: {
      type: Number,
      required: true
    },
    currentPage: {
      type: Number,
      required: true
    },
    pageSize: {
      type: Number,
      default: 5
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalItems / this.pageSize);
    }
  },
  methods: {
    changePage(newPage) {
      this.$emit('update:currentPage', newPage);
    }
  }
}
</script>

<style scoped>
.pagination-container {
  margin: 20px 0;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

button {
  padding: 10px 16px;
  background-color: #D07070;
  color: white;
  border: none;
  border-radius: 6px;
  transition: background-color 0.3s;
  cursor: pointer;
}

button:disabled {
  background-color: #ccc;
  cursor: default;
}

button:hover:not(:disabled) {
  background-color: #bb5d5d;
}

span {
  font-weight: bold;
}
</style>
