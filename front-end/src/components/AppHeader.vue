<script setup>

</script>

<template>
  <header class="position-sticky top-0 z-1">
    <div class="container position-relative h-100">
      <div class="text-center position-absolute top-50 start-50 translate-middle">
        <a href="/" class="text-danger text-decoration-none">
          <img src="../assets/img/logo.png" alt="Site logo" height="104" style="filter: invert(1)"/>
        </a>
      </div>
      <div class="position-absolute top-50 translate-middle-y" style="right: 12px;">
        <a href="http://localhost:8080/" class="login btn btn-outline-light text-light">
          Login
        </a>
        <a href="http://localhost:8080/" class="login-mobile btn btn-outline-light d-flex align-items-center justicy-content-center">
          <box-icon type='solid' name='user' style="fill: #f8f9fa;"></box-icon>
        </a>
      </div>
    </div>
  </header>
</template>
