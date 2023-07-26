<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';

import { useRoute } from 'vue-router'
import axios from 'axios';
import { onMounted } from 'vue'
import JobCardGroup from '@/components/JobCardGroup.vue';
import Pagination from '@/components/Pagination.vue';




const route = useRoute();
const jobs = [
  {
    jobTitle: "Software developer",
    jobRole: "Software Developer",
    jobName: "Software Developer",
    jobId: "4"
  },
  {
    jobTitle: "Software developer",
    jobRole: "Software Developer",
    jobName: "Software Developer",
    jobId: "4"
  },
  {
    jobTitle: "Software developer",
    jobRole: "Software Developer",
    jobName: "Software Developer",
    jobId: "4"
  },
  {
    jobTitle: "Software developer",
    jobRole: "Software Developer",
    jobName: "Software Developer",
    jobId: "4"
  },
  {
    jobTitle: "Software developer",
    jobRole: "Software Developer",
    jobName: "Software Developer",
    jobId: "4"
  }
]




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
    <div class="container">
      <JobCardGroup :jobs="jobs"></JobCardGroup>
      <Pagination></Pagination>
    
    </div>

  </main>
</template>
