<script setup lang="ts">
import { computed, watch, ref, onMounted } from 'vue';
import VueDatePicker from '@vuepic/vue-datepicker';
import AlertMessage from './AlertMessage.vue';



const props = defineProps<{
    status: string,
    date: Date,
    isPermanent: boolean
}>()

const emits = defineEmits(['update:status', 'update:date', 'update:isPermanent'])
const alertFlag = ref(false);


const statusValue = computed({
    get() {
        return props.status
    },
    set(value) {
        emits('update:status', value)
    }
})


const isPermanentValue = computed({
    get() {
        return props.isPermanent
    },
    set(value) {
        emits('update:isPermanent', value);
    }
})

const dateValue = computed({
    get() {
        return props.date
    },
    set(value) {
        emits('update:date', value);
    }
})


watch(dateValue, (newDateValue, oldDateValue) => {
    alertFlag.value = false;
    const fromHour = new Date();
    fromHour.setHours(fromHour.getHours() + 1)
    if ((newDateValue.getTime() < fromHour.getTime())) {
        alertFlag.value = true;
    }

})

</script>


<template>
    <div class="mt-3">
        <div class="form-check">
            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1" value="PASSIVE"
                v-model="statusValue">
            <label class="form-check-label" for="flexRadioDefault1">
                Passive
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked
                value="ACTIVE" v-model="statusValue">
            <label class="form-check-label" for="flexRadioDefault2">
                Active
            </label>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" v-model="isPermanentValue">
            <label class="form-check-label" for="flexCheckDefault">
                Permanent
            </label>
        </div>
        <label for="endDate">Until</label>
        <VueDatePicker v-model="dateValue" :disabled="isPermanentValue"></VueDatePicker>
        <AlertMessage message="Please set date more then 1 hour from now" v-if="alertFlag"></AlertMessage>

    </div>
</template>

<style></style>