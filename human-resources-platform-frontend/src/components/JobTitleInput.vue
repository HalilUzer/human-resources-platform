<script setup lang="ts">
import { computed, watch, ref } from 'vue';
import validator from 'validator';
import AlertMessage from '@/components/AlertMessage.vue';

const props = defineProps(['modelValue'])
const emit = defineEmits(['update:modelValue'])


const alertFlag = ref(false);
const value = computed({
  get() {
    return props.modelValue
  },
  set(value) {
    emit('update:modelValue', value)
  }
})


watch(value, (newValue, oldValue) => {
  alertFlag.value = false;
  if (validator.isEmpty(newValue)) {
    alertFlag.value = true;
  }
})
</script>

<template>
  <label for="exampleFormControlInput1" class="form-label">Title</label>
  <input type="text" class="form-control" id="exampleFormControlInput1" v-model="value">
  <AlertMessage v-if="alertFlag" message="Job title cannot be empty"></AlertMessage>
</template>

<style></style>