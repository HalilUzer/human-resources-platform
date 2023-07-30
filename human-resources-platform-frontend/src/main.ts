import 'bootstrap/dist/css/bootstrap.css'

import { createApp, watch } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

const pinia = createPinia();

if(localStorage.getItem("state")){
    pinia.state.value = JSON.parse(localStorage.getItem("state")!);
}

watch(
    pinia.state,
    (state) => {
        localStorage.setItem("state", JSON.stringify(state))
    }
)

const app = createApp(App);

app.use(pinia);
app.use(router);

app.mount('#app');

import 'bootstrap/dist/js/bootstrap.js'
