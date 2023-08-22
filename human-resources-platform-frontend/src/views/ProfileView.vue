<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import ProfileCard from '@/components/ProfileCard.vue';
import ExperienceGroup from '@/components/ExperienceGroup.vue';
import NavBar from '@/components/NavBar.vue';
import type { Experience } from '@/types/Experience';
import type { Education } from '@/types/Education';
import EducationGroup from '@/components/EducationGroup.vue';
import ProfileImage from '@/components/ProfileImage.vue';
import ApplicationGroup from '@/components/ApplicationGroup.vue';
import useProfileStore from '@/stores/profileStore';
import type { Application } from '@/types/Application';
import router from '@/router';

const route = useRoute();
const profileStore = useProfileStore();
const name = ref('');
const surname = ref('');
const about = ref('');
const email = ref('');
const imageSource = ref('');
const applications = ref<Application[]>([])
const experiences = ref<Experience[]>([])
const educations = ref<Education[]>([])
const isBlackListed = ref(false);



onMounted(async () => {
    try {
        const response = await axios.get(`${import.meta.env.VITE_URL}/candidate/${route.params.candidate_id}`)
        experiences.value = response.data.experiences;
        educations.value = response.data.educations;
        name.value = response.data.name;
        surname.value = response.data.surname;
        about.value = response.data.about;
        email.value = response.data.email;
        imageSource.value = response.data.imageSource;
    }
    catch (e: any) {
        if (e.code === 'NOT_FOUND' || e.code === 'ERR_BAD_REQUEST') {
            await router.push('/404');
        }
        console.log(e)
    }


    if (profileStore.getRole === 'HR_SPECIALIST' || (profileStore.getUserId === route.params.candidate_id && profileStore.getRole === 'CANDIDATE')) {

        try {
            const response = await axios.get(`${import.meta.env.VITE_URL}/candidate/${route.params.candidate_id}/applications`,
                {
                    headers: {
                        Authorization: `Bearer ${profileStore.getJwt}`
                    }
                });

            applications.value = response.data.applications.map((application: any) => {
                return {
                    title: application.job.title,
                    jobId: application.job.jobId,
                    poster: application.job.poster,
                    status: application.status
                }
            });
        }
        catch (e) {
            console.log(e);
        }

        try {
            const response = await axios.get(`${import.meta.env.VITE_URL}/candidate/${route.params.candidate_id}/black-list`,
                {
                    headers: {
                        Authorization: `Bearer ${profileStore.getJwt}`
                    }
                });

            console.log(response.data);

            profileStore.setBlackListed(response.data.isBlackListed);
            isBlackListed.value = response.data.isBlackListed;
        }
        catch (e) {
            console.log(e);
        }

    }
});


async function blackListCandidate() {
    try {
        const response = await axios.put(`${import.meta.env.VITE_URL}/candidate/${route.params.candidate_id}/black-list`, null, {
            headers: {
                Authorization: `Bearer ${profileStore.getJwt}`
            }
        });
        if (response.status === 202) {
            profileStore.setBlackListed(true);
            isBlackListed.value = true;
        }
    }
    catch (e) {
        console.log(e);
    }
}

</script>


<template>
    <NavBar></NavBar>
    <div class="container">
        <div class="text-center m-3">
            <ProfileImage :image-source="imageSource" :name="name" :surname="surname"></ProfileImage>
        </div>
        <div class="mt-3 mb-3 text-center">
            <button type="button" class="justify-content-center btn btn-danger"
                v-if="profileStore.getRole === 'HR_SPECIALIST' && !isBlackListed" @click="blackListCandidate">Black
                List</button>
                <p style="color: red;" v-if="((profileStore.getRole === 'CANDIDATE' && profileStore.getUserId === route.params.candidate_id) || profileStore.getRole === 'HR_SPECIALIST') && isBlackListed">Black listed</p>
            <p></p>
        </div>
        <div class="mt-3 mb-3">

            <ProfileCard :about="about" :firt-name="name" :last-name="surname" :email="email"></ProfileCard>
        </div>
        <div class="text-center m-2">
            <h3>Experience</h3>
        </div>
        <ExperienceGroup :experiences="experiences"></ExperienceGroup>
        <div class="text-center m-2">
            <h3>Education</h3>
        </div>
        <EducationGroup :educations="educations"></EducationGroup>
        <div class="text-center m-2">
            <h3>Applications</h3>
        </div>
        <ApplicationGroup :applications="applications"></ApplicationGroup>
    </div>
    <div class="row align-content-center">
        <div class="mt-4 mb-4">
        </div>
    </div>
</template>


<style></style>