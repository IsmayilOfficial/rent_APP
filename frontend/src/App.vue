<template>
  <div id="app">
    <nav class="navbar" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <a class="navbar-item" href="#">
          <h1
            style="font-size: 28px;
    background: green;
    color: #fff;
    border-radius: 10px;"
          >RentIt</h1>
        </a>

        <a
          role="button"
          class="navbar-burger burger"
          aria-label="menu"
          aria-expanded="false"
          data-target="navbarBasicExample"
        >
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
          <span aria-hidden="true"></span>
        </a>
      </div>

      <div id="navbarBasicExample" class="navbar-menu">
        <div class="navbar-start">
          <a class="navbar-item">
            <router-link to="/">Home</router-link>
          </a>

          <a v-if="checkRoles('ROLE_CLERK')" class="navbar-item">
            <router-link to="/plantorder">PlantOrder</router-link>
          </a>

          <a v-if="checkRoles('ROLE_CLERK')" class="navbar-item">
            <router-link to="/plantlisting">Plants</router-link>
          </a>

          <div v-if="checkRoles('ROLE_CLERK')" class="navbar-item has-dropdown is-hoverable">
            <a class="navbar-link">More</a>

            <div class="navbar-dropdown">
              <a class="navbar-item">About</a>
              <a class="navbar-item">Jobs</a>
              <a class="navbar-item">Contact</a>
              <hr class="navbar-divider">
              <a class="navbar-item">Report an issue</a>
            </div>
          </div>
        </div>

        <div class="navbar-end">
          <div class="navbar-item">
            <a v-if="!authenticated()" class="button is-light">
              <router-link to="/login">login</router-link>
            </a>

            <div v-if="authenticated()" class="navbar-item has-dropdown is-hoverable">
              <a class="navbar-link">Hello {{userName()}}</a>

              <div class="navbar-dropdown">
                <!-- <a class="navbar-item">Jobs</a> -->
                <a class="navbar-item">
                  <router-link to="/logout">Log Out</router-link>
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
    <router-view/>
  </div>
</template>


<script>
import auth from "./components/auth/auth.js";
//

export default {
  name: "App",
  components: {
    // appHeader: Header
  },
  data() {
    return {};
  },
  computed: {
    user() {
      return this.$store.getters.user;
    },
    userName() {
      return auth.getUserName;
    },
    authenticated() {
      return auth.authenticated;
    },
    checkRoles() {
      return auth.hasAnyOf;
    }
  },
  created() {},
  mounted() {},
  methods: {}
};
</script>


<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
