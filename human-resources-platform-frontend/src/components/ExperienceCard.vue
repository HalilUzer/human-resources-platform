<script setup lang="ts">

const props = defineProps<{
    title: string,
    company: string
    date: string,
    description: string
}>()
</script>


<template>
<div class="row justify-content-center">
    <div class="col-lg-8">
        <div class="card mb-4">
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-3">
                        <p class="mb-0">Name</p>
                    </div>
                    <div class="col-sm-9">
                        <p class="text-muted mb-0">{{  }}</p>
                    </div>
                    <div class="col-sm-9">
                        <p class="text-muted mb-0">{{  }}</p>
                    </div>
                    <div class="col-sm-9">
                        <p class="text-muted mb-0">{{  }}</p>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <p class="mb-0">Surname</p>
                    </div>
                    <div class="col-sm-9">
                        <p class="text-muted mb-0">{{  }}</p>
                    </div>
                    <div class="col-sm-9">
                        <p class="text-muted mb-0">{{  }}</p>
                    </div>
                    <div class="col-sm-9">
                        <p class="text-muted mb-0">{{  }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>



<style>

</style>