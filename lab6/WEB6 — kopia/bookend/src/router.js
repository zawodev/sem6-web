import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import AuthorsView from '@/views/AuthorsView.vue'
import BooksView from '@/views/BooksView.vue'
import ReadersView from '@/views/ReadersView.vue'

const routes = [
  { path: '/', name: 'HomeView', component: HomeView },
  { path: '/authors', name: 'AuthorsView', component: AuthorsView },
  { path: '/books', name: 'BooksView', component: BooksView },
  { path: '/readers', name: 'ReadersView', component: ReadersView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router