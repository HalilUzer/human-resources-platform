<script setup lang="ts">
import ApplicantImage from './ApplicantImage.vue';

const props = defineProps<{
    title: string,
    posterName: string,
    jobId: string,
    status: string
}>()

</script>

<template>
    <li class="list-group-item text-center">
        <h6 class="card-title">{{ title }}</h6>
        <h6 class="card-text">Posted By {{ posterName }}</h6>
        <h6 class="card-text">Status {{ status.toLowerCase() }}</h6>
        <RouterLink :to="`/job/${jobId}`" class="stretched-link"></RouterLink>

    </li>
</template>

<style></style>