import Vue from 'vue';
import App from '@/App.vue';
import store from '@/store';
import router from '@/router';
import EventBus from '@/EventBus';
import $ from 'jquery';
import 'bootstrap'; 
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios';
import config from './config';

Vue.config.productionTip = false

Vue.prototype.$config = config;
Vue.prototype.$EventBus = EventBus;
Vue.prototype.$ = $;
Vue.prototype.$api = axios.create({
  baseURL: config.apiUrl
});
Vue.prototype.$imgServiceApi = axios.create({
  baseURL: config.imgServiceApiUrl
});

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')
