<script setup lang="ts">

import ColorfulLogo from '@/components/ColorfulLogo.vue';
import { useRoute } from 'vue-router'
import axios from 'axios';
import { onMounted, ref } from 'vue'
import router from '@/router';
import useProfileStore from '@/stores/profileStore'


const route = useRoute();
const profileStore = useProfileStore();
let profileUrl: string = '';


const alertFlag = ref<boolean>(false);

async function createProfile() {
    if (route.query.code !== undefined) {
        try {
            const response = await axios.post(`${import.meta.env.VITE_URL}/linkedin/build`, {
                candidate_id: profileStore.getUserId,
                profile_url: profileUrl
            }, {
                headers: {
                    Authorization: `Bearer ${profileStore.getJwt}`
                }
            })

            await router.push('/');
        }
        catch (e: any) {
            if (e.code === 'ERR_BAD_REQUEST') {
                alertFlag.value = true;
            }
        }

    }
}


onMounted(async () => {
    if (route.query.code === null) {
        await router.push('/unauthorized');
        return;
    }

    if(route.query.error){
        await router.push('/');
        return;
    }

    const signInResponse = await axios.post(`${import.meta.env.VITE_URL}/linkedin/sign-in`, {
        state: 'foobar',
        code: route.query.code,
        profile_url: profileUrl
    })

    profileStore.setJwt(signInResponse.data.token);
    profileStore.setRole(signInResponse.data.role);
    profileStore.setUserId(signInResponse.data.user_id);
    profileStore.setBlackListed(signInResponse.data.is_black_listed);
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

                        <div class="alert alert-danger mt-3" v-if="alertFlag" role="alert">
                            Please enter you url!
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