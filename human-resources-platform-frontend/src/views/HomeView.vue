<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';

import { useRoute } from 'vue-router'
import axios from 'axios';
import { linkedinSigninConstants } from '@/constants';

const route = useRoute();

if(route.query.code === undefined){
  axios.post('https://www.linkedin.com/oauth/v2/accessToken', {
    grant_type: 'authorization_code',
    code: route.query.code,
    client_id: linkedinSigninConstants.clientId,
  })

}
</script>

<template>
  <main>
    <NavBar></NavBar>
  </main>
</template>
