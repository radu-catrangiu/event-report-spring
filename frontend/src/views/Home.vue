<template>
  <div class="container-fluid">
    <login-modal></login-modal>
    <image-modal></image-modal>
    <div class="row align-items-center">
      <div class="col text-left">
        <!-- TODO: Logo here -->
        <h2 class="ml-5 font-weight-bold">EventReport</h2>
      </div>
      <div class="col text-right">
        <!-- TODO: Show current user or login btn -->
        <login/>
      </div>
    </div>
    <div class="row">
      <div class="col-3">
        <events-list/>
      </div>
      <div class="col-9">
        <google-map/>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import GoogleMap from "@/components/GoogleMap/GoogleMap";
import EventsList from "@/components/EventsList";
import LoginModal from "@/components/LoginModal";
import ImageModal from "@/components/ImageModal";
import Login from '@/components/Login';
export default {
  name: "Home",
  components: {
    GoogleMap,
    EventsList,
    LoginModal,
    ImageModal,
    Login
  },
  mounted() {

  }
};
</script>

<style>
.no-gutters {
  margin-right: 0;
  margin-left: 0;
  min-height: 100vh;
}
</style>