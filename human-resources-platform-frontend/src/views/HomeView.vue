<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';

import { useRoute } from 'vue-router'
import axios from 'axios';
import { linkedinSigninConstants } from '@/constants';
import { onMounted } from 'vue'

const route = useRoute();



onMounted(async () => {
  if (route.query.code !== undefined) {
    const response = await axios.post('http://localhost:8080/linkedin/sign-in', {
      state: 'foobar',
      code: route.query.code,
    })

    console.log(response)
  }
})
</script>

<template>
  <main>
    <NavBar></NavBar>
  </main>
</template>
