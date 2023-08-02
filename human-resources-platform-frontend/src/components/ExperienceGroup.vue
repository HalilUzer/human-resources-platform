<script setup lang="ts">
import type { Experience } from '@/types/Experience';
import ExperienceCard from '@/components/ExperienceCard.vue'


const props = defineProps<{
    experiences: Experience[]
}>()
</script>


<template>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <card class="mb-4">
                <div class="card body">
                    <ul class="list-group">
                        <ExperienceCard v-for="(experience, index) in props.experiences" :key="index" :company="experience.company" :date="experience.date" :description="experience.description!"
                        :title="experience.title"></ExperienceCard>
                    </ul>
                </div>
            </card>
        </div>
    </div>

</template>



<style></style>