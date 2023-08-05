<!-- eslint-disable vue/multi-word-component-names -->



<template>
  <nav>
    <ul class="pagination">

      <PageItem v-for="(page, index) in pages" :key="index" :page-description="page.pageDescription"
        :page-link="page.pageLink">
      </PageItem>
    </ul>
  </nav>
</template>

<script setup lang="ts">
import PageItem from './PageItem.vue';

const pages = [
  {
    pageLink: "/",
    pageDescription: "1"
  },
  {
    pageLink: "/",
    pageDescription: "1"
  },
  {
    pageLink: "/",
    pageDescription: "1"
  },
  {
    pageLink: "/",
    pageDescription: "1"
  },
  {
    pageLink: "/",
    pageDescription: "1"
  },
  {
    pageLink: "/",
    pageDescription: "1"
  },

]
</script>

<style></style>