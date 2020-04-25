<template>
  <div class="events-list">
    <div>
      <button
        class="btn btn-lg btn-block"
        :class="{
          'btn-danger': showList,
          'btn-warning': !showList
        }"
        @click="showList = !showList"
      >{{ showList ? "REPORT AN EVENT" : "BACK TO LIST"}}</button>
    </div>
    <div v-bind:hidden="showList">
      <new-event-card />
    </div>
    <div
      id="list-container"
      v-bind:hidden="!showList"
      class="overflow-auto"
      style="max-height: 88vh;"
    >
      <div v-if="eventsList.length > 0">
        <div v-for="(event, index) in eventsList" :key="event._id" :id="event._id">
          <event-card :event="event" :index="index" :adminUser="adminUser"></event-card>
        </div>
      </div>
      <div v-else class="my-5">No events reported</div>
    </div>
  </div>
</template>

<script>
import EventCard from "./EventCard";
import NewEventCard from "./NewEventCard";
export default {
  name: "EventsList",
  components: {
    NewEventCard,
    EventCard
  },
  data() {
    return {
      adminUser: false,
      showList: true,
      eventsList: []
    };
  },
  async mounted() {
    this.$store.subscribe(mutation => {
      if (mutation.type !== "user") return;
      checkUser(this);
    });

    checkUser(this);

    this.$EventBus.$on("map-loaded", async () => {
      const result = await this.$api.get("/events");
      this.eventsList.push(...result.data);
    });

    this.$EventBus.$on("marker-clicked", event => {
      const elem = this.$(`#${event._id} .card-body`);
      const container = this.$("#list-container");

      if (elem.length == 0) {
        return;
      }

      this.showList = true;
      elem.addClass("highlighted");
      container.animate(
        {
          scrollTop:
            elem.offset().top - container.offset().top + container.scrollTop()
        },
        500,
        () => {
          container.scrollTop(
            elem.offset().top - container.offset().top + container.scrollTop()
          );
          setTimeout(
            function() {
              elem.removeClass("highlighted");
            },
            1000,
            "swing"
          );
        }
      );
    });
  },
  methods: {}
};

function checkUser(self) {
  const user = self.$store.getters.user;
  /* eslint-disable */
  console.log(user); 
  if (!user) {
    self.adminUser = false;
    return;
  }
  self.adminUser = user.admin;
}
</script>

<style>
.events-list {
  max-height: 90vh;
  -ms-overflow-style: none;
}
.events-list:-webkit-scrollbar {
  display: none;
}
</style>