<script setup lang="ts">
import useProfileStore from '@/stores/profileStore';
import ApplicantImage from './ApplicantImage.vue';
import type { Applicant } from '@/types/Applicant';
import axios from 'axios';


const profileStore = useProfileStore();

const props = defineProps<{
    applicant: Applicant
}>()

const emit = defineEmits<{
    (e: 'changeStatus', status: string, applicationId: string): void
}>();

async function changeStatus(status: string) {
    try {
        const response = await axios.put(`http://localhost:8080/application/{applicationId}/status`, {
            status
        }, {
            headers: {
                Authorization: `Bearer ${profileStore.getJwt}`
            }
        })
    }
    catch (e) {
        console.log(e);
    }
}


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
            @click="emit('changeStatus', 'ON_EVALUATION', applicant.applicationId)">Being Evaluated</button>
        <button type="button" class="btn btn-success m-1"
            @click="emit('changeStatus', 'ACCEPTED', applicant.applicationId)">Accept</button>
        <button type="button" class="btn btn-danger m-1"
            @click="emit('changeStatus', 'DENIED', applicant.applicationId)">Deny</button>
    </li>
</template>

<style></style>