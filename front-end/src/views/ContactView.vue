<script setup>
import axios from 'axios';
import { ref } from 'vue';

const forminput = ref({
  email: "",
  content: ""
});

const inputErrors = ref({
  email: false,
  content: false
})

const API_URL = "http://localhost:8080/api/v1";

const sending = ref(false);

const sendOk = ref(null);

const regex = /[\S]+@[\S]+\.[\S]+/;

function sendMessage() {
  sendOk.value = null;
  sending.value = true;
  inputErrors.value.email = false;
  inputErrors.value.content = false;

  if (forminput.value.content.length < 3 || forminput.value.content.length > 2000) {
    sendOk.value = false;
    inputErrors.value.content = true;
  }

  if (forminput.value.email.length < 5 || forminput.value.email.length > 255 || !regex.test(forminput.value.email)) {
    sendOk.value = false;
    inputErrors.value.email = true;
  }

  if (inputErrors.value.email || inputErrors.value.content) {
    sending.value = false;
    return;
  }

  axios
  .post(`${API_URL}`, forminput.value)
  .then(res => {
    if (res.status == 200) {
      sendOk.value = true;
      inputErrors.value.email = false;
      inputErrors.value.content = false;
    }
  })
  .catch(error => {
    console.error(error);
    sendOk.value = false;
  })
  .finally(() => {
    sending.value = false;
  })
}
</script>

<template>
  <main class="py-5">
    <div class="container">
      
      <form method="post" class="mb-4" id="contact" @submit.prevent="sendMessage()">
        <div class="my-4">
          <label for="email" class="d-block text-light">E-mail <span class="text-danger">*</span></label>
          <input type="email" maxlength="255" placeholder="Type your e-mail address..." class="px-1 py-2 rounded border border-light text-light" v-model.trim="forminput.email" required />
        </div>

        <div v-if="inputErrors.email" class="alert alert-danger" role="alert">
          <strong>Enter a valid e-mail address</strong>
        </div>

        <div class="my-4">
          <label for="content" class="d-block text-light">Message <span class="text-danger">*</span></label>
          <textarea name="content" id="content" minlength="3" maxlength="2000" placeholder="Type your message..." class="px-1 py-2 rounded border border-light text-light" v-model.trim="forminput.content" required></textarea>
        </div>

        <div v-if="inputErrors.content" class="alert alert-danger" role="alert">
          <strong>The message has to be between 3 and 2000 characters</strong>
        </div>

        <button type="submit" class="btn btn-outline-light px-3 py-2 rounded">Send message</button>
      </form>

      <div v-if="sending" class="alert alert-info" role="alert" id="sending_message_alert">
        <strong>
          Sending message
          <font-awesome-icon icon="fa-solid fa-spinner" size="lg" spin />
        </strong>
      </div>

      <div v-if="sendOk" class="alert alert-success" role="alert">
        <strong>Message sent</strong>
      </div>

      <div v-if="!sendOk && sendOk != null && !inputErrors.email && !inputErrors.content" class="alert alert-danger" role="alert">
        <strong>Send failed</strong>
      </div>
      
    </div>
  </main>
</template>
