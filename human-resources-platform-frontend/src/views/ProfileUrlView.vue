<script setup lang="ts">

import ColorfulLogo from '@/components/ColorfulLogo.vue';
import { routerKey, useRoute } from 'vue-router'
import axios from 'axios';
import { onMounted } from 'vue'
import router from '@/router';

const route = useRoute();
let profileUrl: string = '';


async function createProfile() {
    if (route.query.code !== undefined) {
        try {
            const response = await axios.post('http://localhost:8080/linkedin/sign-in', {
                state: 'foobar',
                code: route.query.code,
                profile_url: profileUrl
            })

            
        if(response.status === 200){
            await router.push('/')
        }
        }
        catch (e) {
            console.log(e);
        }

    }
}


onMounted(async () => {
    if (route.query.code === null) {
        await router.push('/unauthorized')
    }
})
</script>

<template>
    <div id="vanityName">
        <div class="container mt-5">
            <ColorfulLogo></ColorfulLogo>
            <div id="sign-in-row" class="row justify-content-center">
                <div id="sign-in-column" class=" justify-content-center col-md-6 text-center">
                    <div id="sign-in-box">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="floatingInput" placeholder="Linkedin Profile URL"
                                v-model="profileUrl">
                            <label for="floatingInput">Please enter Linkedin profile URL to proceed</label>
                        </div>
                        <div class="form-floating">
                            <button type="button" class="btn btn-dark m-3" style="background-color: rgb(7, 24, 61);"
                                @click="createProfile">Submit</button>
                        </div>
                    </div>
                </div>
                <div class="row justify-content-center">
                    <div class="col-2">
                        <SignInLinkedinButton></SignInLinkedinButton>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style></style>