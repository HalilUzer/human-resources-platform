import 'bootstrap/dist/css/bootstrap.css'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'


const pinia = createPinia();
const app = createApp(App);
app.component('VueDatePicker', VueDatePicker)
app.use(pinia);
app.use(router);

axios.interceptors.response.use((response) => response,
    async (error) => {
        const profileStore = useProfileStore();
        if (profileStore.isExpired === true && profileStore.isAuthenticated === true) {
            profileStore.$reset()
            await router.push('/')
        }
    })

app.mount('#app');

import 'bootstrap/dist/js/bootstrap.js'
import axios from 'axios'
import useProfileStore from './stores/profileStore'
