<template>
  <div>
    <div v-if="user">
      <button class="btn btn-secondary" @click="logOut">Log Out Admin</button>
    </div>
    <div v-else>
      <button class="btn btn-secondary" @click="openLoginModal">Log In Admin</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: null
    };
  },
  async mounted() {
    this.$store.subscribe(mutation => {
      if (mutation.type !== "user") return;
      this.user = this.$store.getters.user;
    });
    const loginToken = this.$cookies.get("login_token");
    if (loginToken) {
      try {
        const result = await this.$api.get("/auth/token", {
          params: {
            login_token: loginToken
          }
        });
        if ((result.status === 200, result.data)) {
          this.$store.commit("user", result.data);
          this.user = result.data;
        }
      } catch (error) {
        this.$store.commit("user", null);
        this.$cookies.remove("login_token");
      }
    }
  },
  methods: {
    logOut() {
      this.$store.commit("user", null);
      this.$cookies.remove("login_token");
    },
    openLoginModal() {
      this.$EventBus.$emit("open-login-modal");
    }
  }
};
</script>

<style>
</style>