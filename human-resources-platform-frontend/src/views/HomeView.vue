<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';

import axios from 'axios';
import { onMounted } from 'vue'
import JobCardGroup from '@/components/JobCardGroup.vue';
import { ref } from 'vue';
import type { JobLite } from '@/types/JobLite';


const jobs = ref<JobLite[]>([]);




onMounted(async () => {
  const response = await axios.get(`${import.meta.env.VITE_URL}/jobs`)
  jobs.value = response.data;

  console.log(response);
})
</script>

<template>
  <main>
    <NavBar></NavBar>
    <div class="container">
      <JobCardGroup :jobs="jobs"></JobCardGroup>
    </div>

  </main>
</template>
