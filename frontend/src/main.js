import './assets/style.css' //глобальные стили так подключать вроде
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)

app.mount('#app')