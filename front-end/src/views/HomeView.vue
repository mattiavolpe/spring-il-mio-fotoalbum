<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';

const API_URL = "http://localhost:8080/api/v1";

const photos = ref([]);

const filter = ref("");

function searchPhotos() {
  if (filter.value != "")
    fetchPhotos(filter.value);
  else
    fetchAll();
}

function fetchPhotos(keyword) {
  axios
  .get(`${API_URL}?filter=${keyword}`)
  .then(res => {
    if (res.data.length > 0) {
      photos.value = res.data;
      filter.value = "";
    }
    else {
      photos.value = [];
      filter.value = "";
    }
  })
  .catch(error => {
    console.error(error);
  })
}

function fetchAll() {
  axios
  .get(API_URL)
  .then(res => {
    if (res.data.length > 0)
      photos.value = res.data;
  })
  .catch(error => {
    console.error(error);
  })
}

onMounted(() => {
  fetchAll();
})

</script>

<template>
  <main class="py-5">
    <div class="container">
      
      <form v:if="photos.length > 0" class="mb-4" @submit.prevent="searchPhotos()">
        <label for="filter" class="text-light">Search</label>
        <br />
        <div id="search" class="d-flex flex-column align-items-start">
          <input class="my-3 px-1 py-2 rounded border border-light text-light" type="text" name="filter" id="filter" placeholder="Type a filter..." v-model.trim="filter" />
          <button class="btn btn-outline-light px-3 py-2 rounded" type="submit">Search</button>
        </div>
      </form>
      
      <div class="mb-4">
        <a href="/categories" class="btn btn-outline-light">Watch the categories</a>
      </div>
      
      <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <div v-for="photo in photos" class="col">
          <div class="card h-100 border-0">
            <div class="card-body flex-grow-1 d-flex align-items-center justify-content-center">
              <a :href="'/' + photo.id" class="d-block h-100 text-decoration-none text-light">
                <div class="img_wrapper position-relative h-100">
                  <img :src="photo.url" :alt="photo.title + ' image'" class="h-100 w-100" />
                </div>
              </a>
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
  </main>
</template>
