<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';
import BlueBadge from '@/components/BlueBadge.vue';
import { ref } from 'vue';
import axios from 'axios';
import useProfileStore from '@/stores/profileStore';
import router from '@/router';

const profileStore = useProfileStore();
const personalSkills = ref<string[]>([]);
const technicalSkills = ref<string[]>([]);
const title = ref('');
const jobDescription = ref('');
const date = ref<Date>(new Date());

const personalSkill = ref('');
const technicalSkill = ref('');
const status = ref('ACTIVE');

function addTechnicalSkill() {
  technicalSkills.value.push(technicalSkill.value);
  technicalSkill.value = '';
}

function addPersonalSkill() {
  personalSkills.value.push(personalSkill.value);
  personalSkill.value = '';
}

function deleteOne(text: string, skills: string[]) {
  console.log('enter')
  const index = skills.findIndex((skill) => skill === text);
  skills.splice(index, 1);
}

function setState(value: string) {
  status.value = value;
}


async function postJob() {

  console.log({
    title: title.value,
    job_description: jobDescription.value,
    state: status.value,
    until: new Date(date.value).getTime(),
    technical_skills: JSON.parse(JSON.stringify(technicalSkills.value)),
    personal_skills: JSON.parse(JSON.stringify(personalSkills.value)),
  })
  const response = await axios.post('http://localhost:8080/job', {
    title: title.value,
    job_description: jobDescription.value,
    status: status.value,
    until: new Date(date.value).getTime(),
    technical_skills: technicalSkills.value,
    personal_skills: personalSkills.value,
  }, {
    headers: {
      Authorization: `Bearer ${profileStore.getJwt}`
    }
  })
  await router.push(`/job/${response.data.jobId}`);
}

</script>


<template>
  <NavBar></NavBar>
  <div class="container">
    <div class="mb-3 mt-3">
      <label for="exampleFormControlInput1" class="form-label">Title</label>
      <input type="email" class="form-control" id="exampleFormControlInput1" v-model="title">
    </div>
    <div class="mb-3">
      <label for="exampleFormControlTextarea1" class="form-label">Job Description</label>
      <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" v-model="jobDescription"></textarea>
    </div>


    <div class="mt-3" v-if="profileStore.getRole === 'HR_SPECIALIST'">
      <div class="form-check">
        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1"
          @click="status = 'PASSIVE'">
        <label class="form-check-label" for="flexRadioDefault1">
          Passive
        </label>
      </div>
      <div class="form-check">
        <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked value="ACTIVE"
          @click="status = 'ACTIVE'">
        <label class="form-check-label" for="flexRadioDefault2">
          Active
        </label>
      </div>
      <label for="endDate">Until</label>
      <div class="mb-3"><input id="datePicker" class="form-control" type="datetime-local" v-model="date"></div>
    </div>



    <label for="technicalSkill">Technical Skills</label>
    <input type="text" class="form-control" id="technicalSkill" v-model="technicalSkill"
      @keydown.enter="addTechnicalSkill">
    <BlueBadge v-for="(techinicalSkill, index) in technicalSkills" :key="index" :text="techinicalSkill"
      @delete="(text: string) => deleteOne(text, technicalSkills)"></BlueBadge>

    <br>
    <label for="personalSkill">Personal Skills</label>
    <input type="text" class="form-control" id="personalSkill" v-model="personalSkill" @keydown.enter="addPersonalSkill">
    <BlueBadge v-for="(personalSkill, index) in personalSkills" :key="index" :text="personalSkill"
      @delete="(text: string) => deleteOne(text, personalSkills)"></BlueBadge>


  </div>
  <div class="text-center"><button type="button" class="btn btn-dark m-3" @click="postJob"
      style="background-color: rgb(7, 24, 61)">Post</button></div>
</template>

<style></style>