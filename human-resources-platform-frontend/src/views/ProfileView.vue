<script setup lang="ts">
import useProfileStore from '@/stores/profileStore';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import ProfileCard from '@/components/ProfileCard.vue';
import ExperienceGroup from '@/components/ExperienceGroup.vue';
import NavBar from '@/components/NavBar.vue';
import type { Experience } from '@/types/Experience';
import type { Education } from '@/types/Education';
import EducationGroup from '@/components/EducationGroup.vue';

const route = useRoute();
const profileStore = useProfileStore();

const name = ref('');
const surname = ref('');
const about = ref('');
let experiences: Experience[];
let educations: Education[];




onMounted(async () => {
    try {
        const response = await axios.get(`http://localhost:8080/candidate/${route.params.candidate_id}`)
        experiences = response.data.experiences;
        educations = response.data.educations;
        name.value = response.data.name;
        surname.value = response.data.surname;
        about.value = response.data.about;
    }
    catch (e) {
        console.log(e);
    }
});

</script>


<template>
    <NavBar></NavBar>
    <div class="container">
        <div class="mt-3 mb-3">
            <ProfileCard :about="about" :firt-name="name" :last-name="surname"></ProfileCard>
        </div>
        <div class="text-center m-2">
            <h3>Experience</h3>
        </div>
        <ExperienceGroup :experiences="experiences"></ExperienceGroup>
        <div class="text-center m-2">
            <h3>Education</h3>
        </div>
        <EducationGroup :educations="educations"></EducationGroup>

    </div>
    <div class="row m-5">
        <div class="btn-group" role="group" aria-label="Basic example">
            <button type="button" class="btn btn-outline-primary w-25">Owned Nfts</button>
            <button type="button" class="btn btn-outline-primary w-25">Nft Metadatas</button>
        </div>
    </div>
    <div class="row align-content-center">
        <div class="mt-4 mb-4">
        </div>
    </div>
</template>


<style></style>