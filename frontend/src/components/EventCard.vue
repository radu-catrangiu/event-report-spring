<template>
  <div class="event-card my-2">
    <div class="card">
      <div class="card-body">
        <span
          v-if="adminUser"
          class="text-muted text-uppercase text-monospace"
          style="font-size: 9px;"
        >{{event._id}}</span>
        <h5 class="card-title mt-1">{{event.title}}</h5>
        <h6
          class="card-subtitle mb-2"
          :class="{ 
                'text-success': event.resolved,
                'text-danger': !event.resolved,
            }"
        >{{event.resolved ? 'Resolved' : 'Unresolved'}}</h6>

        <span
          v-if="event.tag"
          class="badge badge-pill badge-warning text-uppercase mb-3"
        >{{event.tag}}</span>
        <p class="card-text">{{event.description}}</p>
        <div class="btn-group mb-1">
          <button class="btn btn-warning" @click="showEventImage">Show image</button>
          <button
            v-if="adminUser && !event.resolved"
            class="btn btn-success"
            @click="updateEvent(true)"
          >Resolve</button>
          <button
            v-if="adminUser && event.resolved"
            class="btn btn-secondary"
            @click="updateEvent(false)"
          >Unresolve</button>
        </div>
        <div>
          <button v-if="adminUser" class="btn btn-danger" @click="deleteEvent">Delete Event</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  mounted() {
    this.$EventBus.$emit("add-point-on-map", this.event);
  },
  props: {
    adminUser: Boolean,
    event: Object,
    index: Number
  },
  methods: {
    showEventImage() {
      this.$EventBus.$emit("open-image-modal", this.event);
    },
    async updateEvent(resolved) {
      const loginToken = this.$cookies.get("login_token");
      await this.$api.put(`/events/${this.event._id}`, {
        resolved,
        login_token: loginToken
      });

      this.event.resolved = resolved;
    },
    async deleteEvent() {
      const loginToken = this.$cookies.get("login_token");
      const result = await this.$api.delete(`/events/${this.event._id}`, {
        params: {
          login_token: loginToken
        }
      });
      if (result.status === 200) {
        this.$EventBus.$emit("delete-point-from-map", this.event);
        this.$parent.eventsList.splice(this.index, 1);
      }
    }
  }
};
</script>

<style>
.highlighted {
  background-color: #fed766;
}
</style>