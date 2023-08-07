<script setup lang="ts">
import type { Applicant } from '@/types/Applicant';
import ApplicantCard from './ApplicantCard.vue';

const props = defineProps<{
    applicants: Applicant[],
}>()

const emit = defineEmits(['changeStatus']);

</script>

<template>
    <div class="row justify-content-lg-start">
        <div class="col-lg-8">
            <div class="mb-4">
                <div class="card-body">
                    <div class="list-group">
                        <ApplicantCard v-for="(applicant, index) in  applicants" :key="index" :applicant="applicant"
                            @change-status="emit('changeStatus', $event)">
                        </ApplicantCard>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style></style>