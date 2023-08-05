<script setup lang="ts">

import ColorfulLogo from '@/components/ColorfulLogo.vue';
import { useRoute } from 'vue-router'
import axios from 'axios';
import { onMounted } from 'vue'
import router from '@/router';
import useProfileStore from '@/stores/profileStore'


const route = useRoute();
const profileStore = useProfileStore();
let profileUrl: string = '';


async function createProfile() {
    if (route.query.code !== undefined) {
        try {
            console.log(profileStore.getUserId)
                const response = await axios.post('http://localhost:8080/linkedin/build', {
                    candidate_id: profileStore.getUserId,
                    profile_url: profileUrl
                })

            await router.push('/');
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

    const signInResponse = await axios.post('http://localhost:8080/linkedin/sign-in', {
        state: 'foobar',
        code: route.query.code,
        profile_url: profileUrl
    })

    profileStore.setJwt(signInResponse.data.token);
    profileStore.setRole(signInResponse.data.role);
    profileStore.setUserId(signInResponse.data.user_id);
    if (signInResponse.data.message === 'Exists') {
        await router.push('/');
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style></style>