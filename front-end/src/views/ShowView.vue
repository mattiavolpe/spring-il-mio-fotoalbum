<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router'

const route = useRoute();

const API_URL = "http://localhost:8080/api/v1";

const photo = ref(null);

function fetchPhoto() {
  axios
  .get(`${API_URL}/${route.params.id}`)
  .then(res => {
    if (res.status == 200)
      photo.value = res.data;
  })
  .catch(error => {
    console.error(error);
  })
}

function goFullScreen() {
  const overlayEl = document.getElementById("full_screen_overlay");

  overlayEl.classList.remove("d-none");
  overlayEl.classList.add("d-flex");
  document.body.append(overlayEl);
}

function exitFullScreen() {
  const overlayEl = document.getElementById("full_screen_overlay");

  overlayEl.classList.remove("d-flex");
	overlayEl.classList.add("d-none");
}

onMounted(() => {
  fetchPhoto();

  document.body.addEventListener("click", e => {
    const overlayEl = document.getElementById("full_screen_overlay");

    if (e.target == document.getElementById("single_image") || e.target == document.getElementById("zoom_icon_wrapper") || e.target == document.querySelector("#zoom_icon_wrapper [name='plus-circle']")) {
      overlayEl.classList.remove("d-none");
      overlayEl.classList.add("d-flex");
    }
  });

  document.body.addEventListener("click", () => {
    const overlayEl = document.getElementById("full_screen_overlay");

    overlayEl.classList.remove("d-flex");
    overlayEl.classList.add("d-none");
  }, true);
})

</script>

<template>
  <main class="py-5">
    <div v-if="photo != null" class="container">
				
      <h1 class="text-center mb-4 text-light">{{ photo.title }}</h1>
      
      <div class="position-relative d-flex align-items-center justify-content-center" id="single_image_wrapper">
        <img :src="photo.url" :alt="photo.title + ' image'" class="img-fluid" id="single_image" />
        <div id="zoom_icon_wrapper" class="position-absolute top-50 start-50 translate-middle d-flex align-items-center justify-content-center rounded-circle opacity-0" >
          <box-icon name='plus-circle' type='solid' style="flex-shrink: 0; fill: #1e1e1e;" ></box-icon>
        </div>
      </div>
      
      <div class="d-none align-items-center justify-content-center position-fixed top-0 start-0 z-2" id="full_screen_overlay" >
        <img :src="photo.url" :alt="photo.title + ' image'" />
      </div>
      
      <h3 class="mt-4 mb-0 text-center text-light">{{photo.description}}</h3>

      <div v:if="photo.categories.length > 0" class="bg-dark mt-4 p-3 rounded">
        <h4 class="m-0 text-light">Categories:</h4>
        <span v-for="(category, index) in photo.categories">
          <span v-if="index < photo.categories.length - 1 && photo.categories.length > 1" class="fst-italic text-secondary">
            {{category.name}} - 
          </span>
        </span>
        <span v-for="(category, index) in photo.categories">
          <span v-if="index === photo.categories.length - 1" class="fst-italic text-secondary">
            {{category.name}}
          </span>
        </span>
      </div>
      
    </div>
  </main>
</template>
