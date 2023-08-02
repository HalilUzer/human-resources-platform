<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';
import BlueBadge from '@/components/BlueBadge.vue';
import { ref } from 'vue';
import axios from 'axios';
import useProfileStore from '@/stores/profileStore';



const profileStore = useProfileStore();
const personalSkills = ref<string[]>([]);
const technicalSkills = ref<string[]>([]);
const title = ref('');
const jobDescription = ref('');
const date = ref<Date>(new Date());

const personalSkill = ref('');
const technicalSkill = ref('');
const state = ref('');

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


function postJob() {

  console.log(date);
  axios.post('http://localhost:8080/candidate', {
    title,
    job_description: jobDescription.value,
    state: state.value,
    until: date.value.getTime(),
    technical_skills: technicalSkills.value,
    personal_skills: personalSkills.value,

  })
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

    <div class="form-check">
      <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault1"
        @click="state = 'PASSIVE'">
      <label class="form-check-label" for="flexRadioDefault1">
        Set passive
      </label>
    </div>
    <div class="form-check">
      <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" checked
        @click="state = 'ACTIVE'">
      <label class="form-check-label" for="flexRadioDefault2">
        Set active
      </label>
    </div>
    <label for="endDate">Until</label>
    <div class="mb-3"><input id="endDate" class="form-control" type="datetime-local" v-model="date"></div>
    <Datepicker></Datepicker>


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