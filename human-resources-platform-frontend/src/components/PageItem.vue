<template>
    <li class="page-item"><a class="page-link" :href="pageLink" style="color: rgb(7, 24, 61);">{{ pageDescription }}</a>
    </li>
</template>

<script setup lang="ts">

const props = defineProps<{
    pageLink: string,
    pageDescription: string
}>()
</script>

<style></style>