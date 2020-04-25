<template>
  <div class="GoogleMapContainer" :class="{ PointOnMap: point_to_location }"></div>
</template>

<script>
import GoogleMapInit from "./gmaps";
import MapStyle from "./mapStyle";
import init from "./init.json";

let Google;

export default {
  name: "GoogleMap",
  data: function() {
    return {
      point_to_location: false,
      map: {},
      points: {},
      mapLoaded: false,
      pointMarker: null
    };
  },
  props: [],
  async mounted() {
    /* eslint-disable */
    await this.loadMap();

    this.$EventBus.$on("add-point-on-map", event => {
      const marker = new Google.maps.Marker({
        map: this.map
      });
      marker.setPosition(event.location);
      marker.setVisible(true);
      marker.addListener("click", () => {
        this.$EventBus.$emit("marker-clicked", event);
      });
      this.points[event._id] = {
        event,
        marker
      };
    });

    this.$EventBus.$on("delete-point-from-map", event => {
      this.points[event._id].marker.setMap(null);
      delete this.points[event._id];
    });

    this.$EventBus.$on("point-browser-location", location => {
      this.pointMarker.setPosition(location);
      this.pointMarker.setVisible(true);
    });

    this.$EventBus.$on("hide-point-marker", () => {
      this.pointMarker.setVisible(false);
    });

    this.$EventBus.$on("pick-location-on-map", () => {
      this.point_to_location = true;
    });
  },
  methods: {
    async loadMap() {
      try {
        Google = await GoogleMapInit();

        this.map = new Google.maps.Map(this.$el, {
          styles: MapStyle.blueGray,
          zoom: 12,
          streetViewControl: false,
          disableDefaultUI: true,
          gestureHandling: "cooperative"
        });
        let map = this.map;

        map.setCenter(init.geometry.location);
        map.fitBounds(init.geometry.viewport);

        map.addListener("click", e => {
          if (this.point_to_location) {
            const location = {
              lat_lng: {
                lat: e.latLng.lat(),
                lng: e.latLng.lng()
              }
            };
            this.pointMarker.setPosition(location.lat_lng);
            this.pointMarker.setVisible(true);
            this.$EventBus.$emit("pick-location-on-map-result", location);
            this.point_to_location = false;
          }
        });

        this.pointMarker = new Google.maps.Marker({
          map: this.map,
          icon: {
            url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"
          }
        });

        this.mapLoaded = true;
        this.$parent.map_loaded = true;

        this.$EventBus.$emit("map-loaded");
      } catch (error) {
        // eslint-disable-next-line
        console.debug(error);
      }
    }
  }
};
</script>

<style>
html,
body {
  margin: 0;
  padding: 0;
}

.GoogleMapContainer {
  width: 100%;
  height: 90vh;
  background: #efefef;
}

.GoogleMapContainer h1 {
  margin: 0;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

.PointOnMap {
  cursor: pointer;
}
</style>