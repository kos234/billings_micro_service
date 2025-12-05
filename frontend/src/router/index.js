import { createRouter, createWebHistory } from 'vue-router'
import MainPage from "@/pages/MainPage.vue";
import LoginPageDev from "@/pages/LoginPageDev.vue";
import LogoutPage from "@/pages/LogoutPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: MainPage },
    { path: '/login', component: LoginPageDev },
    { path: '/logout', component: LogoutPage },
  ],
})

export default router
