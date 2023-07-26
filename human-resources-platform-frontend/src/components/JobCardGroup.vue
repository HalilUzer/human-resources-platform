<script setup lang="ts">
import JobCard from './JobCard.vue';

const props = defineProps<{
     jobs: Array<{ jobId:string, jobName: string, jobTitle: string, jobRole: string }>
}>()


</script>

<template>
     <div class="row row-cols-6 justify-content-center mt-3 g-5">
          <JobCard v-for="(job, index) in props.jobs" :key="index" :job-id="job.jobId"
               :job-role="job.jobRole" :job-title="job.jobTitle"></JobCard>
     </div>
</template>

<style></style>