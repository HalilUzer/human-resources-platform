<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import type { Job } from '@/types/Job';
import { useRoute } from 'vue-router';
import useProfileStore from '@/stores/profileStore';
import router from '@/router';
import ApplicantGroup from '@/components/ApplicantGroup.vue';
import type { Candidate } from '@/types/Candidate';

const profileStore = useProfileStore();
const route = useRoute();
const job = ref<Job>();
const status = ref('ACTIVE');
const date = ref<Date>(new Date());
const applicants = ref<Candidate[]>([]);
onMounted(async () => {
    const response = await axios.get(`http://localhost:8080/job/${route.params.job_id}`);
    job.value = response.data;
    if(profileStore.getRole === 'HR_SPECIALIST' && profileStore.getUserId === job.value?.poster.hrSpecialistId){
        const response = await axios.get(`http://localhost:8080/job/${route.params.job_id}/applicants`,{
            headers: {
                Authorization: `Bearer ${profileStore.getJwt}`
            }
        });
        applicants.value = response.data.applicants;
        console.log(applicants);
    }
})

async function apply() {
    if (profileStore.getRole !== 'CANDIDATE' || profileStore.getJwt === '') {
        await router.push('/sign-in')
    }
    const response = await axios.put(`http://localhost:8080/job/${route.params.job_id}/apply`, null, {
        headers: {
            Authorization: `Bearer ${profileStore.getJwt}`
        }
    });

}



async function setStatus() {
    try {
        const response = await axios.put(`http://localhost:8080/job/${route.params.job_id}/status`, {
            until: new Date(date.value).getTime(),
            status: status.value
        }, {
            headers: {
                Authorization: `Bearer ${profileStore.getJwt}`
            }
        });
    }
    catch (e) {
        console.log(e);
    }
}
</script>

<template>
    <NavBar></NavBar>

    <div class="container">
        <div class="mt-3 mb-3">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h3>{{ job?.title }}</h3>
                                    <p class="text-muted mb-0">Posted By {{ job?.poster.username }}</p>

                                    <div class="mt-2">
                                        <p> {{ job?.jobDescription }}</p>
                                    </div>

                                    <h4>Technical Skills</h4>
                                    <ul>
                                        <li v-for="(skill, index) in job?.technicalSkills" :key="index">{{
                                            skill.technicalSkill }}</li>
                                    </ul>
                                    <h4>Personal Skills</h4>
                                    <ul>
                                        <li v-for="(skill, index) in job?.personalSkills" :key="index">{{
                                            skill.personalSkill }}</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-12 text-center">
                                    <button type="button" class="btn btn-success"
                                        v-if="job?.status === 'ACTIVE' && profileStore.getRole != 'HR_SPECIALIST'"
                                        @click="apply">Apply</button>
                                    <button type="button" class="btn btn-success" disabled
                                        v-if="job?.status === 'PASSIVE' && profileStore.getRole != 'HR_SPECIALIST'">Apply</button>

                                </div>
                                <div class="mt-3" v-if="profileStore.getRole === 'HR_SPECIALIST'">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="flexRadioDefault"
                                            id="flexRadioDefault1" @click="status = 'PASSIVE'">
                                        <label class="form-check-label" for="flexRadioDefault1">
                                            Passive
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="flexRadioDefault"
                                            id="flexRadioDefault2" checked value="ACTIVE" @click="status = 'ACTIVE'">
                                        <label class="form-check-label" for="flexRadioDefault2">
                                            Active
                                        </label>
                                    </div>
                                    <label for="endDate">Until</label>
                                    <div class="mb-3"><input id="datePicker" class="form-control" type="datetime-local"
                                            v-model="date"></div>
                                    <div class="text-center"><button type="button" class="btn btn-dark m-3"
                                            @click="setStatus" style="background-color: rgb(7, 24, 61)">Set Status</button>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
            </div>
            <ApplicantGroup :applicants="applicants"></ApplicantGroup>

        </div>
    
        
    </div>
</template>


<style></style>