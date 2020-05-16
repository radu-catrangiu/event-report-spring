<template>
  <!-- Modal -->
  <div
    class="modal fade"
    id="loginModal"
    data-backdrop="static"
    tabindex="-1"
    role="dialog"
    aria-labelledby="loginModalLabel"
    aria-hidden="true"
    ref="modal"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="loginModalLabel">Log In</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form>
            <div class="form-group">
              <label for="emailInput">Email address</label>
              <input type="email" class="form-control" id="emailInput" v-model="email" />
            </div>
            <div class="form-group">
              <label for="passwordInput">Password</label>
              <input
                type="password"
                class="form-control"
                id="passwordInput"
                v-model="password"
                @keyup.enter="login"
              />
            </div>
            <small
              id="emailHelp"
              class="form-text text-muted"
            >We'll never share your data with anyone else.</small>
            <div class="text-danger mt-3" v-if="error">ERROR: {{error}}</div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-warning" @click="register">Register</button>
          <button type="button" class="btn btn-primary" @click="login">Log In</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginModal",
  data() {
    return {
      email: "",
      password: "",
      error: undefined
    };
  },
  methods: {
    async login() {
      try {
        if (this.email.length < 3 || this.password.length < 3) {
          this.error = "Email and password should be longer than 3 characters!";
          return;
        }

        const result = await this.$api.get("/auth/login", {
          params: {
            email: this.email,
            password: this.password
          }
        });

        if (result.status === 200 && result.data) {
          const loginToken = result.data.login_token;
          this.$cookies.set("login_token", loginToken);
          this.$store.commit("user", result.data);

          const claimableEvents = this.$store.getters.claimableEvents;
          if (claimableEvents.length > 0) {
            await this.claimEvents(claimableEvents);
          }
        }
        this.$("#loginModal").modal("hide");
      } catch (error) {
        if (error.response.status === 403) {
          this.error = "Wrong username or password";
        }
        this.$store.commit("user", null);
        this.$cookies.remove("login_token");
      }
    },
    async createAccount() {
      try {
        if (this.email.length < 3 || this.password.length < 3) {
          this.error = "Email and password should be longer than 3 characters!";
          return;
        }

        const result = await this.$api.post("/auth/create", {
          email: this.email,
          password: this.password
        });

        return result.status === 200;
      } catch (error) {
        if (error.response.status === 403) {
          this.error = "Bad username or password";
        }
      }
    },
    async register() {
      const success = await this.createAccount();
      if (success) {
        await this.login();
      }
    },
    async claimEvents(eventClaims) {
      try {
        const loginToken = this.$cookies.get("login_token");
        const result = await this.$api.post("/events/claim", eventClaims, {
          params: {
            login_token: loginToken
          }
        });

        if (result.status === 200) {
          this.$store.commit("clearClaimable");
        }
      } catch (error) {
        // eslint-disable-next-line
        console.error(error);
      }
    }
  },
  mounted() {
    this.$EventBus.$on("open-login-modal", () => {
      // eslint-disable-next-line
      console.log(navigator.userAgent);
      this.$("#loginModal").modal("show");
    });
  }
};
</script>

<style>
</style>