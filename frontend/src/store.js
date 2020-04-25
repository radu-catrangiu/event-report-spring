import Vue from 'vue';
import Vuex from 'vuex';
import VueCookies from 'vue-cookies';

Vue.use(Vuex);
Vue.use(VueCookies);

export default new Vuex.Store({
    state: {
        user: null
    },
    mutations: {
        user(state, payload) {
            state.user = payload;
        }
    },
    actions: {},
    getters: {
        user(state) {
            return state.user;
        }
    }
});
