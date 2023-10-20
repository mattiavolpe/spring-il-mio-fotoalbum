<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { RouterLink, useRoute } from 'vue-router'

const route = useRoute();

const API_URL = "http://localhost:8080/api/v1";

const photos = ref([]);

const loading = ref(true);

function fetchAll() {
  loading.value = true;
  axios
  .get(`${API_URL}/category/${route.params.id}`)
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

onMounted(() => {
  fetchAll();
})

</script>

<template>
  <main class="py-5">
    <div v-if="loading" class="container text-secondary text-center">
      <font-awesome-icon icon="fa-solid fa-hourglass" spin size="2xl" />
      <h2 class="mt-3">LOADING PHOTOS</h2>
    </div>

    <div v-if="photos.length > 0" class="container">
      
      <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
        <div v-for="photo in photos" class="col" id="index_image">
          <div class="card h-100 border-0">
            <div class="card-body flex-grow-1 d-flex align-items-center justify-content-center">
              <RouterLink :to="{ name: 'show', params: { id: photo.id}}" class="d-block h-100 text-decoration-none text-light">
                <div class="img_wrapper position-relative h-100">
                  <img :src="photo.url" :alt="photo.title + ' image'" class="w-100 h-100" />
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
      <font-awesome-icon icon="fa-solid fa-ban" size="2xl" />
      <h2 class="mt-3">NO PHOTOS AT THE MOMENT</h2>
      <h3>COME BACK TO CHECK LATER</h3>
    </div>
  </main>
</template>
