<script setup lang="ts">
import { linkedinSigninConstants } from '@/constants';

const signInWithLinkedinUrl = `https://www.linkedin.com/oauth/v2/authorization?response_type=${linkedinSigninConstants.responseType}&client_id=${linkedinSigninConstants.clientId}&redirect_uri=${linkedinSigninConstants.redirectUri}&state=${linkedinSigninConstants.state}&scope=r_liteprofile`
</script>

<template>
    <a :href=signInWithLinkedinUrl target="_self"><img src="@/assets/signin-button.png" height="41" width="215"/></a>
</template>

<style scoped></style>