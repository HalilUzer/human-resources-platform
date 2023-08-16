<script setup lang="ts">
import ColorfulLogo from '@/components/ColorfulLogo.vue';
import GoBack from '@/components/GoBack.vue';
import SignInLinkedinButton from '@/components/SignInLinkedinButton.vue';
import useProfileStore from '@/stores/profileStore';
import axios from 'axios';
import router from '@/router';
import { ref } from 'vue';


let username: string = '';
let password: string = '';
const errorFlag = ref<boolean>(false);

const profileStore = useProfileStore();



async function hrSpecialistSignIn() {

    try {
        const response = await axios.post('http://localhost:8080/sign-in', {
            username,
            password
        });
        profileStore.setJwt(response.data.token);
        profileStore.setRole(response.data.role);
        profileStore.setUserId(response.data.user_id);
        await router.push('/');
    }
    catch (error: any) {
        if (error.code === 'ERR_BAD_REQUEST') {
            errorFlag.value = true;
        }
        console.log(error);
    }

}

</script>

<template>
    <div id="signIn">
        <div class="container mt-5">
            <GoBack></GoBack>
            <ColorfulLogo></ColorfulLogo>
            <div id="sign-in-row" class="row justify-content-center">
                <div id="sign-in-column" class=" justify-content-center col-md-6 text-center">
                    <div class="py-3 d-flex align-items-center">
                        <hr class="flex-grow-1" />
                        <div class="badge" style="background-color: rgb(7, 24, 61);">Human Resources Specialist Sign In
                        </div>
                        <hr class="flex-grow-1" />
                    </div>
                    <div id="sign-in-box">
                        <div class="alert alert-danger mt-3" v-if="errorFlag" role="alert">
                            Invalid credentials
                        </div>
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="floatingInput" placeholder="username"
                                v-model="username">
                            <label for="floatingInput">Username</label>
                        </div>
                        <div class="form-floating">
                            <input type="password" class="form-control" id="floatingPassword" placeholder="Password"
                                v-model="password">
                            <label for="floatingPassword">Password</label>
                            <button type="button" class="btn btn-dark m-3" style="background-color: rgb(7, 24, 61);"
                                @click="hrSpecialistSignIn">Sign In</button>
                            <div class="py-3 d-flex align-items-center">
                                <hr class="flex-grow-1" />
                                <div class="badge" style="background-color: rgb(7, 24, 61);">Candidates Sign In</div>
                                <hr class="flex-grow-1" />
                            </div>
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





<style scoped></style>