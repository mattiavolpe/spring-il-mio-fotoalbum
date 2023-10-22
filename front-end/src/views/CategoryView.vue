<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { RouterLink, useRoute } from 'vue-router'

const route = useRoute();

const API_URL = "http://localhost:8080/api/v1";

const photos = ref([]);

const category = ref("");

const loading = ref(true);

function fetchAll() {
  loading.value = true;
  axios
  .get(`${API_URL}/category/${route.params.slug}`)
  .then(res => {
    if (res.status == 200) {
      console.log(res.data);
      photos.value = res.data.photos;
      category.value = res.data.category.name;
    }
    else {
      photos.value = [];
      category.value = "";
    }
  })
  .catch(error => {
    console.error(error);
    photos.value = [];
    category.value = "";
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

    <h1 v-if="category != ''" class="text-center text-light">{{ category }}</h1>

    <div class="container">
      <div v-if="loading" class="container text-secondary text-center">
        <font-awesome-icon icon="fa-solid fa-hourglass" spin size="2xl" />
        <h2 class="mt-3">LOADING PHOTOS</h2>
      </div>
    </div>

    <div v-if="photos.length > 0" class="container">
      
      <div class="row row-cols-1 row-cols-lg-3">

        <div v-for="photo in photos" class="col mobile_column">
          <RouterLink :to="{ name: 'show', params: { slug: photo.slug }}" class="d-block position-relative text-decoration-none text-light mb-4">
            <img :src="photo.url" :alt="photo.title + ' image'" class="w-100" />
            <div class="image_overlay position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center">
              <h3 class="mb-0 text-center px-3">{{ photo.title }}</h3>
            </div>
          </RouterLink>
        </div>

        <div class="col large_column">
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

        <div class="col large_column">
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

        <div class="col large_column">
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
