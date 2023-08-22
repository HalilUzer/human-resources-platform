<script setup lang="ts">

const props = defineProps<{
    message: string
}>()
</script>

<template>
    <div class="alert alert-danger mt-3" role="alert">
        {{ message }}
    </div>
</template>

<style></style>