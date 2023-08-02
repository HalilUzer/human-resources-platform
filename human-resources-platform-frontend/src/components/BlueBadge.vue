<script setup lang="ts">



const props = defineProps<{
    text: string
}>();

const emit = defineEmits<{
    (e: 'delete', text: string): void
}>();

</script>

<template>
    <div class="badge m-1" style="background-color: rgb(7, 24, 61);" @click="emit('delete', text)">{{ text }}
    </div>
</template>

<style></style>