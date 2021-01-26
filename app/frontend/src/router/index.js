import { createRouter, createWebHistory } from 'vue-router'
import Login from '@/views/Login'
import Home from '@/views/Home'
import Checkout from '@/views/Checkout'
import History from '@/views/History'

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: Checkout
  },
  {
    path: '/history',
    name: 'History',
    component: History
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
