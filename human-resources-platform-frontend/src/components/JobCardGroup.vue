<script setup lang="ts">
import type { Job } from '@/types/Job';
import JobCard from './JobCard.vue';

const props = defineProps<{
     jobs: Job[]
}>()


</script>

<template>
     <div class="row row-cols-6 justify-content-center mt-3 g-5">
          <JobCard v-for="(job, index) in props.jobs" :key="index" :job-id="job.jobId"
                :job-title="job.title" :job-poster="job.poster.username"></JobCard>
     </div>
</template>

<style></style>