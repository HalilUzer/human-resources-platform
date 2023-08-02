<script setup lang="ts">

const props = defineProps<{
    title: string,
    company: string
    date: string,
    description: string
}>()
</script>


<template>
    <li class="list-group-item"><b>{{ title }}</b>
        <p>{{ company }}</p>
        <p> {{ date }}</p>
        <p> {{ description }} </p>
    </li>
</template>


<style></style>