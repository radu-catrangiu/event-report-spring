import Vue from 'vue';

import LoginModal from '@/components/LoginModal';
import ImageModal from '@/components/ImageModal';
import EventsList from '@/components/EventsList';
import EventCard from '@/components/EventCard';
import GoogleMap from '@/components/GoogleMap/GoogleMap';

const EventBus = new Vue({
    components: {
        LoginModal,
        ImageModal,
        EventsList,
        EventCard,
        GoogleMap
    }
});

export default EventBus;
