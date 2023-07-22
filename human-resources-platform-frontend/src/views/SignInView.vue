<script setup lang="ts">
import ColorfulLogo from '@/components/ColorfulLogo.vue';
import GoBack from '@/components/GoBack.vue';
import SignInLinkedinButton from '@/components/SignInLinkedinButton.vue';
</script>

<template>
    <div id="signIn">
        <div class="container mt-5">
            <GoBack></GoBack>
            <ColorfulLogo></ColorfulLogo>
            <div id="sign-in-row" class="row justify-content-center">
                <div id="sign-in-column" class=" justify-content-center col-md-6">
                    <div class="py-3 d-flex align-items-center">
                        <hr class="flex-grow-1" />
                        <div class="badge" style="background-color: rgb(7, 24, 61);">Human Resources Specialist Sign In</div>
                        <hr class="flex-grow-1" />
                    </div>
                    <div id="sign-in-box">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="floatingInput" placeholder="username">
                            <label for="floatingInput">Username</label>
                        </div>
                        <div class="form-floating">
                            <input type="password" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">Password</label>
                            <div class="py-3 d-flex align-items-center">
                                <hr class="flex-grow-1" />
                                <div class="badge"  style="background-color: rgb(7, 24, 61);">Candidates Sign In</div>
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





<style scoped>

</style>