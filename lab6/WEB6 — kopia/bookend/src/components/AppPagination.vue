<template>
  <div style="margin: 20px 0;">
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