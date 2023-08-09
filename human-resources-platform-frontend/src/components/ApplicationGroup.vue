<script setup lang="ts">
import ApplicationCard from './ApplicationCard.vue';
import type { Application } from '@/types/Application';
const props = defineProps<{
    applications: Application[]
}>()
</script>


<template>
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="mb-4">
                <div class="card-body">
                    <div class="list-group">
                        <ApplicationCard v-for="(application, index) in applications" :key="index"
                            :job-id="application.jobId" :poster-name="application.poster.username"
                            :title="application.title" :status="application.status">
                        </ApplicationCard>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style></style>
