<script setup lang="ts">


const signInWithLinkedinUrl = `https://www.linkedin.com/oauth/v2/authorization?response_type=${import.meta.env.VITE_RESPONSE_TYPE}&client_id=${import.meta.env.VITE_CLIENT_ID}&redirect_uri=${import.meta.env.VITE_REDIRECT_URI}&state=${import.meta.env.VITE_STATE}&scope=r_liteprofile r_emailaddress`
</script>

<template>
    <a :href=signInWithLinkedinUrl target="_self"><img src="@/assets/signin-button.png" height="41" width="215" /></a>
</template>

<style scoped></style>