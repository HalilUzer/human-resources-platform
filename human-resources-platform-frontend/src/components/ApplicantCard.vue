<script setup lang="ts">
import useProfileStore from '@/stores/profileStore';
import ApplicantImage from './ApplicantImage.vue';
import type { Applicant } from '@/types/Applicant';
import type { ChangeStatus } from '@/types/ChangeStatus';
import axios from 'axios';


const profileStore = useProfileStore();

const props = defineProps<{
    applicant: Applicant
}>()

const emit = defineEmits<{
    (e: 'changeStatus', changeStatus: ChangeStatus): void
}>();



</script>

<template>
    <li class="list-group-item text-center">
        <RouterLink :to="`/candidate/${applicant.candidate.candidateId}`">
            <ApplicantImage :name="applicant.candidate.name" :surname="applicant.candidate.surname"
                :image-source="applicant.candidate.imageSource"></ApplicantImage>
        </RouterLink>
        <p>{{ applicant.candidate.name }} {{ applicant.candidate.surname }}</p>
        <p>Current Status : {{ applicant.status }}</p>
        <button type="button" class="btn btn-primary m-1" style="background-color: rgb(7, 24, 61);"
            @click="emit('changeStatus', { status: 'ON_EVALUATION', applicationId: applicant.applicationId })">Being
            Evaluated</button>
        <button type="button" class="btn btn-success m-1"
            @click="emit('changeStatus', { status: 'ACCEPTED', applicationId: applicant.applicationId })">Accept</button>
        <button type="button" class="btn btn-danger m-1"
            @click="emit('changeStatus', { status: 'DENIED', applicationId: applicant.applicationId })">Deny</button>
    </li>
</template>

<style></style>