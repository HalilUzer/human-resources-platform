<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';

import { useRoute } from 'vue-router'
import axios from 'axios';
import { onMounted } from 'vue'
import JobCardGroup from '@/components/JobCardGroup.vue';
import type { Job } from '@/types/Job';
import { ref } from 'vue';
import type { JobLite } from '@/types/JobLite';


const route = useRoute();

const jobs = ref<JobLite[]>([]);




onMounted(async () => {
  const response = await axios.get('http://localhost:8080/jobs')
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
