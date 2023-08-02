<script setup lang="ts">
import type { Education } from '@/types/Educations';
import EducationCard from './EducationCard.vue';


const props = defineProps<{
    educations: Education[]
}>()
</script>


<template>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <card class="mb-4">
                <div class="card body">
                    <ul class="list-group">
                        <EducationCard v-for="(education, index) in props.educations" :key="index" :chapter="education.chapter" :date="education.date" :university="education.university" ></EducationCard>
                    </ul>
                </div>
            </card>
        </div>
    </div>

</template>



<style></style>