<script setup lang="ts">
const props = defineProps<{
    imageSource: string,
    name: string,
    surname: string,

}>()


</script>

<template>
    <img :src="imageSource" :alt="`${name} ${surname}`" class="img-fluid rounded-circle border  border-3"
        style="width: 240px;">
</template>


<style></style>