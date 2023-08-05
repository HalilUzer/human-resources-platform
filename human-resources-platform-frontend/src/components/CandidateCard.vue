<script setup lang="ts">

const props = defineProps<{
    name: string,
    surname: string,
    candidateId: string
}>()

</script>

<template>
    <li class="list-group-item">
        <p>{{ name }} {{ surname }}</p>
        <RouterLink :to="`/candidate/${candidateId}`" class="stretched-link"></RouterLink>
    </li>
</template>

<style></style>