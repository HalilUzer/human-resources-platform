<script setup lang="ts">
import { computed, watch, ref } from 'vue';
import validator from 'validator';
import AlertMessage from '@/components/AlertMessage.vue';

const props = defineProps(['modelValue'])
const emit = defineEmits(['update:modelValue'])

const value = computed({
  get() {
    return props.modelValue
  },
  set(value) {
    emit('update:modelValue', value)
  }
})

const alertFlag = ref(false);

watch(value, (newValue, oldValue) => {
  alertFlag.value = false;
  if (validator.isEmpty(newValue)) {
    alertFlag.value = true;
  }
})
</script>

<template>
    <label for="exampleFormControlTextarea1" class="form-label">Job Description</label>
          <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" v-model="value"></textarea>
          <AlertMessage v-if="alertFlag" message="Job description cannot be empty"></AlertMessage>
</template>

<style>
</style>