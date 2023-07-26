<script setup lang="ts">
const props = defineProps<{
    jobTitle: string,
    jobRole: string,
    jobId: string
}>()
</script>

<template>
    <div class="card mx-1 ">
        <div class="card-body focus-ring">
            <h6 class="card-title">{{ jobTitle }}</h6>
            <h6 class="card-text">{{ jobRole }}</h6>
            <RouterLink to="/" class="stretched-link"></RouterLink>
        </div>
    </div>
</template>

<style></style>
