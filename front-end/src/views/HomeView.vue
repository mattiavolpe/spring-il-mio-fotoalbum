<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { RouterLink } from 'vue-router'

const API_URL = "http://localhost:8080/api/v1";

const photos = ref([]);

const filter = ref("");

const lastResearch = ref("");

const loading = ref(true);

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
      photos.value = res.data;
    else
      photos.value = [];
  })
  .catch(error => {
    console.error(error);
  })
  .finally(() => {
    loading.value = false;
  })
}

onMounted(() => {
  fetchAll();
})

</script>

<template>
  <main class="py-5">
    <div v-if="loading" class="container text-secondary text-center">
      <box-icon name='hourglass' animation="spin" size="lg" id="loading_photos_icon"></box-icon>
      <h2>LOADING PHOTOS</h2>
    </div>

    <div v-if="photos.length > 0" class="container">
      
      <form v:if="photos.length > 0" class="mb-4" @submit.prevent="searchPhotos()">
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
      
      <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <div v-for="photo in photos" class="col">
          <div class="card h-100 border-0">
            <div class="card-body flex-grow-1 d-flex align-items-center justify-content-center">
              <RouterLink :to="{ name: 'show', params: { id: photo.id}}" class="d-block h-100 text-decoration-none text-light">
                <div class="img_wrapper position-relative h-100">
                  <img :src="photo.url" :alt="photo.title + ' image'" class="h-100 w-100" />
                </div>
              </RouterLink>
            </div>
            <div class="card-footer border-0 bg-transparent pt-0 pb-3 text-center">
              <a :href="'/' + photo.id" class="text-decoration-none text-light">
                <h5 class="mb-3">
                  {{ photo.title }}
                </h5>
              </a>
            </div>
          </div>
        </div>
      </div>
      
    </div>

    <div v-if="photos.length < 1 && !loading" class="container text-secondary text-center">
      <box-icon name='camera-off' size="lg" id="no_photos_icon"></box-icon>
      <h1 color="black">NO PHOTOS AT THE MOMENT</h1>
      <h3>COME BACK TO CHECK LATER</h3>
    </div>
  </main>
</template>
