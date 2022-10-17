<template>
  <div>
    <div>bot昵称：{{bot_name}}</div>
    <div>bot战力：{{bot_rating}}</div>
  </div>
  <router-view />
</template>

<script>
import $ from 'jquery';
import { ref } from 'vue';

export default {
  name: "APP",
  setup: () => {
    let bot_name = ref("");
    let bot_rating = ref("");
    $.ajax(
      {
        url: "http://localhost:8085/pk/getBotInfo",
        type: "get",
        success: resp => {
          bot_name.value = resp.bot_name
          bot_rating.value = resp.bot_rating;
        }
      }
    );
    return {
      bot_name,
      bot_rating
    }
  }
}
</script>
<style>
body {
  background-image: url("./assets/background.png");
  background-size: cover;
}
</style>
