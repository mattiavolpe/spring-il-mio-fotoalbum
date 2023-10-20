<script setup>
import { RouterLink } from 'vue-router'
</script>

<template>
  <header class="position-sticky top-0 z-1">
    <div class="container position-relative h-100">
      <div class="position-absolute top-50 translate-middle-y" style="left: 12px;">
        <RouterLink :to="{ name: 'contact' }" class="contact btn btn-outline-light">
          Contact me
        </RouterLink>
        <RouterLink :to="{ name: 'contact' }" class="contact-mobile btn btn-outline-light d-flex align-items-center justicy-content-center">
          <font-awesome-icon icon="fa-regular fa-envelope" size="lg" class="text-light" />
        </RouterLink>
      </div>
      <div class="text-center position-absolute top-50 start-50 translate-middle">
        <RouterLink :to="{ name: 'home' }" class="text-decoration-none">
          <img src="../assets/img/logo.png" alt="Site logo" height="104" id="site_logo" />
        </RouterLink>
      </div>
      <div class="position-absolute top-50 translate-middle-y" style="right: 12px;">
        <a href="http://localhost:8080/" target="_blank" class="login btn btn-outline-light">
          Personal area
        </a>
        <a href="http://localhost:8080/" target="_blank" class="login-mobile btn btn-outline-light d-flex align-items-center justicy-content-center">
          <font-awesome-icon icon="fa-regular fa-user" size="lg" class="text-light" />
        </a>
      </div>
    </div>
  </header>
</template>
