<script setup lang="ts">
import type { Candidate } from '@/types/Candidate'
import CandidateCard from './CandidateCard.vue';

const props = defineProps<{
    applicants: Candidate[]
}>()

</script>

<template>
    <div class="row justify-content-lg-start">
        <div class="col-lg-8">
            <div class="mb-4">
                <div class="card-body">
                    <h3>Applicants</h3>
                    <div class="list-group">
                        <CandidateCard v-for="(candidate, index) in  applicants" :key="index"
                            :candidate-id="candidate.candidateId" :name="candidate.name" :surname="candidate.surname">
                        </CandidateCard>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style></style>