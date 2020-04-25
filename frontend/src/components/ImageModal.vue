<template>
  <!-- Modal -->
  <div
    class="modal fade"
    id="imageModal"
    data-backdrop="static"
    tabindex="-1"
    role="dialog"
    aria-labelledby="imageModalLabel"
    aria-hidden="true"
    ref="modal"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="imageModalLabel">{{event.title}}</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body text-left">
          <h5>Description:</h5>
          <p>{{event.description}}</p>
          <h5>Report Date:</h5>
          <p>{{event.report_date}}</p>
          <h5>Image:</h5>
          <img v-if="event.image" class="img-fluid" :src="event.image" @load="show = true" v-show="show"/>
          <div v-if="!show">
            Loading Image...
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ImageModal",
  data() {
    return {
      event: {},
      show: false
    }
  },
  mounted() {
    this.$("#imageModal").on("hide.bs.modal", () => {
      this.event = {};
      this.show = false;
    });
    this.$EventBus.$on("open-image-modal", async (event) => {
      this.event = event;
      this.$("#imageModal").modal("show");
      this.event.image = `${this.$config.imgServiceApiUrl}/image/${event.image_id}`;
    });
  }
};
</script>

<style>
</style>