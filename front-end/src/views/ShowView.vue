<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute, RouterLink } from 'vue-router'

const route = useRoute();

const API_URL = "http://localhost:8080/api/v1";

const photo = ref(null);

const loading = ref(true);

function fetchPhoto() {
  axios
  .get(`${API_URL}/${route.params.id}`)
  .then(res => {
    if (res.status == 200)
      photo.value = res.data;
  })
  .catch(error => {
    console.error(error);
    photo.value = null;
  })
  .finally(() => {
    loading.value = false;
  })
}

function goFullScreen() {
  const overlayEl = document.getElementById("full_screen_overlay");

  overlayEl.classList.remove("d-none");
  overlayEl.classList.add("d-flex");
}

function exitFullScreen() {
  const overlayEl = document.getElementById("full_screen_overlay");

  overlayEl.classList.remove("d-flex");
	overlayEl.classList.add("d-none");
}

onMounted(() => {
  fetchPhoto();
})

</script>

<template>
  <main class="py-5">
    <div v-if="loading" class="container text-secondary text-center">
      <font-awesome-icon icon="fa-solid fa-hourglass" spin size="2xl" />
      <h2 class="mt-3">LOADING PHOTO</h2>
    </div>

    <div v-if="photo != null" class="container">
				
      <h1 class="text-center mb-4 text-light">{{ photo.title }}</h1>
      
      <div class="position-relative d-flex align-items-center justify-content-center" id="single_image_wrapper">
        <img :src="photo.url" :alt="photo.title + ' image'" class="img-fluid" id="single_image" @click="goFullScreen()"/>
        <div id="zoom_icon_wrapper" class="position-absolute top-50 start-50 translate-middle d-flex align-items-center justify-content-center rounded-circle opacity-0" @click="goFullScreen()">
          <font-awesome-icon icon="fa-solid fa-plus" size="xl" class="text-dark" />
        </div>
      </div>
      
      <div class="d-none align-items-center justify-content-center position-fixed top-0 start-0 z-2" id="full_screen_overlay" @click="exitFullScreen()">
        <img :src="photo.url" :alt="photo.title + ' image'" />
      </div>
      
      <h3 class="mt-4 mb-0 text-center text-light">{{photo.description}}</h3>

      <div v-if="photo.categories.length > 0" class="bg-dark mt-4 p-3 rounded">
        <h4 class="m-0 text-light">Categories:</h4>
        <span v-for="(category, index) in photo.categories">
          <RouterLink :to="{ name: 'categoryShow', params: { id: category.id}}" v-if="index < photo.categories.length - 1 && photo.categories.length > 1" class="fst-italic text-secondary text-decoration-none">
            <span class="text-decoration-underline">{{category.name}}</span> - 
          </RouterLink>
        </span>
        <span v-for="(category, index) in photo.categories">
          <RouterLink :to="{ name: 'categoryShow', params: { id: category.id}}" v-if="index === photo.categories.length - 1" class="fst-italic text-secondary">
            {{category.name}}
          </RouterLink>
        </span>
      </div>
      
    </div>

    <div v-if="photo == null && !loading" class="container text-secondary text-center">
      <font-awesome-icon icon="fa-solid fa-ban" size="2xl" />
      <h1 class="mt-3">PHOTO NOT FOUND</h1>
      <h3>COME BACK TO CHECK LATER</h3>
    </div>
  </main>
</template>
