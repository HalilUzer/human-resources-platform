<script setup lang="ts">
const props = defineProps<{
    firtName: string,
    lastName: string,
    about: string,
    email: string
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
                            <p class="mb-0">{{ firtName }}</p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">Surname</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="mb-0">{{ lastName }}</p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">About</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="mb-0">{{ about }}</p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">Email</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="mb-0">{{ email }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style></style>