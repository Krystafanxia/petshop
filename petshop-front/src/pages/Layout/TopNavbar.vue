<template>
  <md-toolbar md-elevation="0" class="md-transparent">
    <div class="md-toolbar-row">
      <div class="md-toolbar-section-start">
        <h3 class="md-title">{{ $route.name }}</h3>
      </div>
      <div class="md-toolbar-section-end">
        <md-button
          class="md-just-icon md-simple md-toolbar-toggle"
          :class="{ toggled: $sidebar.showSidebar }"
          @click="toggleSidebar"
        >
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </md-button>

        <div class="md-collapse">
          <!--div class="md-autocomplete">
            <md-autocomplete
              class="search"
              v-model="selectedEmployee"
              :md-options="employees"
            >
              <label>Search...</label>
            </md-autocomplete>
          </div!-->
          <md-list>
            <!--md-list-item href="#/">
              <i class="material-icons">dashboard</i>
              <p class="hidden-lg hidden-md">Dashboard</p>
            </md-list-item!-->

            <!-- <md-list-item href="#/notifications" class="dropdown">
              <drop-down>
                <a slot="title" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="material-icons">notifications</i>
                  <span class="notification">5</span>
                  <p class="hidden-lg hidden-md">Notifications</p>
                </a>
                <ul class="dropdown-menu dropdown-menu-right">
                  <li><a href="#">Mike John responded to your email</a></li>
                  <li><a href="#">You have 5 new tasks</a></li>
                  <li><a href="#">You're now friend with Andrew</a></li>
                  <li><a href="#">Another Notification</a></li>
                  <li><a href="#">Another One</a></li>
                </ul>
              </drop-down>
            </md-list-item> -->

            <!--li class="md-list-item">
              <a
                href="#/notifications"
                class="
                  md-list-item-router md-list-item-container md-button-clean
                  dropdown
                "
              >
                <div class="md-list-item-content">
                  <drop-down>
                    <md-button
                      slot="title"
                      class="md-button md-just-icon md-simple"
                      data-toggle="dropdown"
                    >
                      <md-icon>notifications</md-icon>
                      <span class="notification">5</span>
                      <p class="hidden-lg hidden-md">Notifications</p>
                    </md-button>
                    <ul class="dropdown-menu dropdown-menu-right">
                      <li><a href="#">Mike John responded to your email</a></li>
                      <li><a href="#">You have 5 new tasks</a></li>
                      <li><a href="#">You're now friend with Andrew</a></li>
                      <li><a href="#">Another Notification</a></li>
                      <li><a href="#">Another One</a></li>
                    </ul>
                  </drop-down>
                </div>
              </a>
            </li !-->

            <li v-if="userName" class="md-list-item">
              <a
                href="javascript:void(0)"
                class="md-list-item-router md-list-item-container md-button-clean dropdown"
              >
                <div class="md-list-item-content">
                  <drop-down direction="down">
                    <md-button
                      slot="title"
                      class="md-button md-button-link md-simple dropdown-toggle"
                      data-toggle="dropdown"
                    >
                      <md-avatar><scale-img :file="avatarFile" :rate="1" /></md-avatar>
                      <p>&nbsp;&nbsp;{{ userName  }}</p>
                    </md-button>
                    <ul class="dropdown-menu">
                      <li>
                        <a href="javascript:;" @click.stop="exit">
                          <i class="material-icons">fingerprint</i>
                          <p>Logout</p>
                        </a>
                      </li>
                      <li>
                        <a href="#/user/detail">
                          <i class="material-icons">account_circle</i>
                          <p>User Center</p>
                        </a>
                      </li>
                    </ul>
                  </drop-down>
                </div>
              </a>
            </li>
          </md-list>
        </div>
      </div>
    </div>
  </md-toolbar>
</template>

<script>
import {mapActions, mapGetters, mapState} from "vuex";
import ScaleImg from "@/views/components/ScaleImg";

export default {
  components: {ScaleImg},
  data() {
    return {
      ...mapActions({ logout: 'user/logout' }),
      selectedEmployee: null,
      employees: [
        "Jim Halpert",
        "Dwight Schrute",
        "Michael Scott",
        "Pam Beesly",
        "Angela Martin",
        "Kelly Kapoor",
        "Ryan Howard",
        "Kevin Malone",
      ],
    };
  },
  computed: {
    ...mapGetters(['userId']),
    ...mapState({ userName: state => state.user.info.name, avatarFile: state => state.user.info.avatarFile }),
  },
  methods: {
    exit() {
      this.logout();
      this.$router.push({ path: '/login' });
    },
    toggleSidebar() {
      this.$sidebar.displaySidebar(!this.$sidebar.showSidebar);
    },
  },
};
</script>

<style lang="scss" scoped>
  .md-toolbar {
    padding-top: 0!important;
  }
  .dropdown-menu a {
    display: flex;
  }
</style>
