import {mapGetters} from "vuex";

export default {
  data: {

  },
  computed: {
    ...mapGetters(['userId'])
  }
};
