<script setup lang="ts">
import type { JobLite } from '@/types/JobLite';
import JobCard from './JobCard.vue';

const props = defineProps<{
     jobs: JobLite[]
}>()


</script>

<template>
     <div class="row row-cols-6 justify-content-center mt-3 g-5">
          <JobCard v-for="(job, index) in props.jobs" :key="index" :job="job"></JobCard>
     </div>
</template>

<style></style>