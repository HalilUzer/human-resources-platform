<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';
import useProfileStore from '@/stores/profileStore';
import type { JobLite } from '@/types/JobLite';
import NavBar from '@/components/NavBar.vue';
import JobCardGroup from '@/components/JobCardGroup.vue';

const profileStore = useProfileStore();


const jobsLite = ref<JobLite[]>([]);



onMounted(async () => {
    try {
        const response = await axios.get(`${import.meta.env.VITE_URL}/jobs/${profileStore.getUserId}`,
            {
                headers: {
                    Authorization: `Bearer ${profileStore.getJwt}`
                }
            })

         jobsLite.value = response.data.postedJobs.map((postedJob : any) => {return { 
            title: postedJob.title,
            poster : {
                username: response.data.username
            },
            jobId: postedJob.jobId
         }});

         console.log(response.data);
    }
    catch (e: any) {
        console.log(e);
        /*if (e.response.message === 'Authentication failed') {
            await router.push('/unauthorized');
        }*/
    }
})
</script>

<template>
    <NavBar></NavBar>
    <div class="container text-center">
        <h1 class="mt-3">My Posts</h1>
        <JobCardGroup :jobs="jobsLite"></JobCardGroup>
    </div>
</template>

<style></style>