<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';

import { useRoute } from 'vue-router'
import axios from 'axios';
import { onMounted } from 'vue'
import JobCardGroup from '@/components/JobCardGroup.vue';
import Pagination from '@/components/Pagination.vue';
import type { Job } from '@/types/Job';
import { ref } from 'vue';

const route = useRoute();

const jobs = ref<Job[]>();




onMounted(async () => {
  const response = await axios.get('http://localhost:8080/jobs')
  jobs.value = response.data;
})
</script>

<template>
  <main>
    <NavBar></NavBar>
    <div class="container">
      <JobCardGroup :jobs="jobs!"></JobCardGroup>
    </div>

  </main>
</template>
