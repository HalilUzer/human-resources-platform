import 'bootstrap/dist/css/bootstrap.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'


const pinia = createPinia();
const app = createApp(App);
app.use(pinia);
app.use(router);

axios.interceptors.response.use((response) => response,
    async (error) => {
        const profileStore = useProfileStore();
        if (profileStore.isExpired === true && profileStore.isAuthenticated === true) {
            profileStore.$reset()
        }
    })

app.mount('#app');

import 'bootstrap/dist/js/bootstrap.js'
import axios from 'axios'
import useProfileStore from './stores/profileStore'
