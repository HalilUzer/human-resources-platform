<script setup lang="ts">

import type { JobLite } from '@/types/JobLite';

const props = defineProps<{
    job: JobLite
}>()
</script>

<template>
    <div class="card mx-1 ">
        <div class="card-body focus-ring">
            <h6 class="card-title">{{ job.title }}</h6>
            <h6 class="card-text">Posted By {{ job.poster.username }}</h6>
            <RouterLink :to="`/job/${job.jobId}`" class="stretched-link"></RouterLink>
        </div>
    </div>
</template>

<style></style>
