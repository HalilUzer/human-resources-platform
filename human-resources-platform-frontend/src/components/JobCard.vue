<script setup lang="ts">
const props = defineProps<{
    jobTitle: string,
    jobPoster: string,
    jobId: string
}>()
</script>

<template>
    <div class="card mx-1 ">
        <div class="card-body focus-ring">
            <h6 class="card-title">{{ jobTitle }}</h6>
            <h6 class="card-text">Posted By {{ jobPoster }}</h6>
            <RouterLink :to="`/job/${jobId}`" class="stretched-link"></RouterLink>
        </div>
    </div>
</template>

<style></style>
