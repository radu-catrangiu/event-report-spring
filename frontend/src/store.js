import Vue from 'vue';
import Vuex from 'vuex';
import VueCookies from 'vue-cookies';

Vue.use(Vuex);
Vue.use(VueCookies);

export default new Vuex.Store({
    state: {
        user: null,
        claimableEvents: []
    },
    mutations: {
        user(state, payload) {
            state.user = payload;
        },
        claimableEvents(state, event) {
            state.claimableEvents.push(event);
            localStorage.setItem('claimableEvents', JSON.stringify(state.claimableEvents));
        },
        clearClaimable(state) {
            state.claimableEvents = [];
            localStorage.setItem('claimableEvents', JSON.stringify(state.claimableEvents));
        }
    },
    actions: {},
    getters: {
        user(state) {
            return state.user;
        },
        claimableEvents(state) {
            const stored = localStorage.getItem('claimableEvents');
            if (!stored) {
                return [];
            }
            state.claimableEvents = JSON.parse(stored);
            
            return state.claimableEvents;
        }
    }
});
