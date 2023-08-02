<script setup lang="ts">

const props = defineProps<{
    date: string,
    university: string
    chapter: string,
}>()
</script>


<template>
    <li class="list-group-item"><b>{{ university }}</b>
        <p>{{ chapter }}</p>
        <p> {{ date }}</p>
    </li>
</template>


<style></style>