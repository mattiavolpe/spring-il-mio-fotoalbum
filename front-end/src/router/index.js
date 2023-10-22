import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ShowView from '../views/ShowView.vue'
import CategoryView from '../views/CategoryView.vue'
import ContactView from '../views/ContactView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/:slug',
      name: 'show',
      component: ShowView
    },
    {
      path: '/category/:slug',
      name: 'categoryShow',
      component: CategoryView
    },
    {
      path: '/contact',
      name: 'contact',
      component: ContactView
    }
  ]
})

export default router
