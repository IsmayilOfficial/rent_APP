import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Login from './components/auth/Login.vue'
import auth from './components/auth/auth.js'

Vue.use(Router)

export default new Router({
  routes: [{
      path: '/',
      name: 'home',
      component: Home,
      beforeEnter: (_to, _from, next) => {
        if (!auth.authenticated()) {
          next({
            path: '/login'
          })
        } else {
          next()
        }
      }
    },
    {
      path: '/plantorder',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: function () {
        return import( /* webpackChunkName: "about" */ './views/About.vue')
      },
      beforeEnter: (_to, _from, next) => {
        if (!auth.authenticated()) {
          next({
            path: '/login'
          })
        } else {
          next()
        }
      }
    },
    {
      path: '/plantlisting',
      name: 'plants',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: function () {
        return import( /* webpackChunkName: "about" */ './views/PlantListing.vue')
      },
      beforeEnter: (_to, _from, next) => {
        if (!auth.authenticated()) {
          next({
            path: '/login'
          })
        } else {
          next()
        }
      }
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/logout',
      name: 'logout',
      component: Login,
      beforeEnter: (_to, _from, next) => {
        auth.logout()
        next({
          path: '/login'
        })
      }
    }
  ]
})