<script setup lang="ts">
import useProfileStore from '@/stores/profileStore';
import WhiteLogo from './WhiteLogo.vue';
import router from '@/router';

const profileStore = useProfileStore();

async function signOut(){
    profileStore.$reset();
    await router.push('/')
}

async function goToProfile() {
    await router.push(`/candidate/${profileStore.getUserId}`)
}

</script>

<template>
    <nav class="navbar navbar-expand-lg" style="background-color: rgb(7, 24, 61);" data-bs-theme="dark">
        <div class="container-fluid">
            <div class="navbar-brand">
                <div class="ms-3">
                    <WhiteLogo></WhiteLogo>
                </div>
            </div>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                </ul>

                <ul class="navbar-nav mr-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <RouterLink to="/sign-in" class="nav-link text-white" role="button" v-if="!profileStore.isAuthenticated">Sign In</RouterLink>
                    </li>
                    <li class="nav-fill">
                        <RouterLink to="/post-a-job" class="nav-link text-white" role="button"
                            v-if="profileStore.getRole === 'HR_SPECIALIST'">Post a Job</RouterLink>
                    </li>
                    <li class="nav-fill">
                        <a  class="nav-link text-white" role="button"
                            v-if="profileStore.getRole === 'CANDIDATE'" @click="goToProfile">Profile</a>
                    </li>
                    <li class="nav-fill">
                        <a href="" class="nav-link text-white" role="button" v-if="profileStore.isAuthenticated"
                            @click="signOut">Sign Out</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<style scoped></style>