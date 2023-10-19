<script setup>

</script>

<template>
  <header class="position-sticky top-0 z-1 bg-white">
    <div class="container position-relative h-100">
      <div class="text-center position-absolute top-50 start-50 translate-middle">
        <a href="/" class="text-danger text-decoration-none">
          <img src="../assets/img/logo.png" alt="Site logo" height="104" />
        </a>
      </div>
      <div class="position-absolute top-50 translate-middle-y" style="right: 12px;">
        <a href="http://localhost:8080/" class="btn btn-danger login text-white">
          Login
        </a>
        <a href="http://localhost:8080/" class="btn btn-danger login-mobile d-flex align-items-center justicy-content-center">
          <box-icon type='solid' name='user' style="fill: white;"></box-icon>
        </a>
      </div>
    </div>
  </header>
</template>
