<script setup>
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
import { RouterLink } from 'vue-router'

const API_URL = "http://localhost:8080/api/v1";

const photos = ref([]);

const filter = ref("");

const lastResearch = ref("");

const loading = ref(true);

const isMobile = ref(window.innerWidth < 992);

function searchPhotos() {
  if (filter.value === "")
    return;
  
  fetchPhotos(filter.value);
}

function fetchPhotos(keyword) {
  loading.value = true;
  lastResearch.value = keyword;
  axios
  .get(`${API_URL}?filter=${keyword}`)
  .then(res => {
    if (res.status == 200)
      photos.value = res.data;
    else
      photos.value = [];
  })
  .catch(error => {
    console.error(error);
    photos.value = [];
  })
  .finally(() => {
    loading.value = false;
  })
}

function fetchAll() {
  loading.value = true;
  axios
  .get(API_URL)
  .then(res => {
    if (res.status == 200)
    photos.value = photos.value.concat(res.data);
    else
      photos.value = [];
  })
  .catch(error => {
    console.error(error);
    photos.value = [];
  })
  .finally(() => {
    loading.value = false;
  })
}

onMounted(() => {
  console.log();
  fetchAll();

  const main = document.querySelector("main");

  main.addEventListener("scroll", e => {
    if (e.target.scrollTop >= e.target.scrollHeight - e.target.offsetHeight - 300) {
      console.log("FETCH");
      fetchAll();
    }
  })

  window.addEventListener("resize", () => {
    isMobile.value = window.innerWidth < 992
  })
})

</script>

<template>
  <main class="py-5">

    <div class="container">
      <div v-if="loading" class="container text-secondary text-center">
        <font-awesome-icon icon="fa-solid fa-hourglass" spin size="2xl" />
        <h2 class="mt-3">LOADING PHOTOS</h2>
      </div>
    </div>

    <div v-if="photos.length > 0 || lastResearch != ''" class="container">

      <form class="mb-4" @submit.prevent="searchPhotos()">
        <label for="filter" class="text-light mb-2">Search</label>
        <div id="search" class="d-flex flex-column align-items-start gap-3">
          <input class="px-1 py-2 rounded border border-light text-light" type="text" name="filter" id="filter" placeholder="Type a filter..." v-model.trim="filter" />
          <button class="btn btn-outline-light px-3 py-2 rounded" type="submit">Search</button>
          <button v-if="lastResearch != ''" class="btn btn-outline-danger px-3 py-2 rounded" @click="fetchAll(); filter = ''; lastResearch = ''">All photos</button>
        </div>
      </form>

      <h3 v-if="lastResearch != ''" class="text-center text-light">
        Search results for "{{ lastResearch }}"
      </h3>
      
      <div :class="photos.length > 2 || lastResearch != '' ? 'row row-cols-1 row-cols-lg-3' : 'row row-cols-1'">

        <div v-if="isMobile" v-for="photo in photos" class="col mobile_column">
          <RouterLink :to="{ name: 'show', params: { slug: photo.slug }}" class="d-block position-relative text-decoration-none text-light mb-4">
            <img :src="photo.url" :alt="photo.title + ' image'" class="w-100" />
            <div class="image_overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center">
              <h3 class="mb-0 text-center px-3">{{ photo.title }}</h3>
            </div>
          </RouterLink>
        </div>

        <div v-if="!isMobile" class="col large_column">
          <template v-for="(photo, index) in photos">
            <template v-if="index % 3 == 0">
              <RouterLink :to="{ name: 'show', params: { slug: photo.slug }}" class="d-block position-relative text-decoration-none text-light mb-4">
                <img :src="photo.url" :alt="photo.title + ' image'" class="w-100" />
                <div class="image_overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center">
                  <h3 class="mb-0 text-center px-3">{{ photo.title }}</h3>
                </div>
              </RouterLink>
            </template>
          </template>
        </div>

        <div v-if="!isMobile" class="col large_column">
          <template v-for="(photo, index) in photos">
            <template v-if="index % 3 == 1">
              <RouterLink :to="{ name: 'show', params: { slug: photo.slug }}" class="d-block position-relative text-decoration-none text-light mb-4">
                <img :src="photo.url" :alt="photo.title + ' image'" class="w-100" />
                <div class="image_overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center">
                  <h3 class="mb-0 text-center px-3">{{ photo.title }}</h3>
                </div>
              </RouterLink>
            </template>
          </template>
        </div>

        <div v-if="!isMobile" class="col large_column">
          <template v-for="(photo, index) in photos">
            <template v-if="index % 3 == 2">
              <RouterLink :to="{ name: 'show', params: { slug: photo.slug }}" class="d-block position-relative text-decoration-none text-light mb-4">
                <img :src="photo.url" :alt="photo.title + ' image'" class="w-100" />
                <div class="image_overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center">
                  <h3 class="mb-0 text-center px-3">{{ photo.title }}</h3>
                </div>
              </RouterLink>
            </template>
          </template>
        </div>

      </div>
      
    </div>

    <div v-if="photos.length < 1 && !loading" class="container text-secondary text-center">
      <font-awesome-icon icon="fa-solid fa-ban" size="2xl" />
      <h2 class="mt-3">NO PHOTOS AT THE MOMENT</h2>
      <h3>COME BACK TO CHECK LATER</h3>
    </div>
  </main>
</template>
