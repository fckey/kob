<template>
  <NarBar />
  <router-view />
</template>

<script>
import NarBar from './components/NarBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default {
  components: {
    NarBar
  }
}
</script>
<style>
body {
  background-image: url("./assets/images/background.png");
  background-size: cover;
}
</style>
