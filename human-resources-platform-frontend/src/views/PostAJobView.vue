<script setup lang="ts">
import NavBar from '@/components/NavBar.vue';
import BlueBadge from '@/components/BlueBadge.vue';
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import useProfileStore from '@/stores/profileStore';
import router from '@/router';
import validator from 'validator';
import JobTitleInput from '@/components/JobTitleInput.vue';
import JobDescriptionInput from '@/components/JobDescriptionInput.vue';
import ChangeJobStatusInput from '@/components/ChangeJobStatusInput.vue';

const profileStore = useProfileStore();
const personalSkills = ref<string[]>([]);
const technicalSkills = ref<string[]>([]);
const title = ref('');
const jobDescription = ref('');
const date = ref<Date>(new Date());
const alertFlag = ref<boolean>(false);
const isPermanent = ref<boolean>(false);

const isPostable = computed(() => {
  const fromHour = new Date();
  fromHour.setHours(fromHour.getHours() + 1)
  if (validator.isEmpty(jobDescription.value) || validator.isEmpty(title.value) || !(fromHour.getTime() < date.value.getTime() || isPermanent)) {
    return false;
  }
  else {
    return true;
  }
})

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
  const index = skills.findIndex((skill) => skill === text);
  skills.splice(index, 1);
}


async function postJob() {
  try {
    if (!isPostable.value) {
      console.log('postable')
      return;
    }
    const response = await axios.post(`${import.meta.env.VITE_URL}/job`, {
      title: title.value,
      job_description: jobDescription.value,
      status: status.value,
      until: `${date.value.getDate()} ${date.value.getMonth() + 1} ${date.value.getFullYear()} ${date.value.getHours()} ${date.value.getMinutes()}`,
      technical_skills: technicalSkills.value,
      personal_skills: personalSkills.value,
      is_permanent: isPermanent.value
    }, {
      headers: {
        Authorization: `Bearer ${profileStore.getJwt}`
      }

    })
    console.log(response);
    await router.push(`/job/${response.data.jobId}`);
  }
  catch (e: any) {
    console.log(e);
    if (e.code === 'ERR_BAD_REQUEST') {
      alertFlag.value = true;
    }
  }
}
onMounted(async () => {
  if (profileStore.getRole !== 'HR_SPECIALIST') {
    await router.push('/unauthorized');
  }
})
</script>


<template>
  <NavBar></NavBar>
  <div class="container">

    <div class="mb-3 mt-3">
      <JobTitleInput v-model="title"></JobTitleInput>
    </div>
    <div class="mb-3">
      <JobDescriptionInput v-model="jobDescription"></JobDescriptionInput>
    </div>
    <ChangeJobStatusInput v-model:date="date" v-model:is-permanent="isPermanent" v-model:status="status">
    </ChangeJobStatusInput>
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
    <div class="alert alert-danger mt-3" v-if="alertFlag" role="alert">
      Please fill all the gaps!
    </div>
    <div class="text-center"><button type="button" class="btn btn-dark m-3" @click="postJob"
        style="background-color: rgb(7, 24, 61)">Post</button></div>
  </div>
</template>

<style></style>