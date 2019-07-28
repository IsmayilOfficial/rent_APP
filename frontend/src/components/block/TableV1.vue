<template>
  <table class="table is-striped is-fullwidth">
    <thead>
      <tr>
        <th class="has-text-center" v-for="(p, index) in template">{{p.label}}</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(item, index) in collection" :key="index">
        <td v-for="(p, index) in template">
          <span v-if="p.type === 'text'">{{item[p.name]}}</span>
          <span v-else-if="p.type === 'computed'">{{p.name(item)}}</span>
        </td>
        <td>
          <tr v-for="(link, index) in item.links" :key="index">
            <a
              v-if="link.rel !== 'self'"
              v-bind:class="{ 'is-danger': link.type === 'DELETE', 'is-link': link.type !== 'DELETE' }"
              class="button is-small is-outlined"
              @click="followLink(link, index)"
            >{{link.rel}}</a>

            <!-- <a
              v-for="(link, index) in item.links"
              :key="index"
              v-if="link.rel !== 'self' && link.rel !== 'fetch'"
              v-bind:class="{ 'is-danger': link.type === 'DELETE', 'is-link': link.type !== 'DELETE' }"
              class="button is-small is-outlined"
              @click="followLink(link, item)"
            >{{link.rel}}</a>-->
          </tr>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script>
import { HTTP } from "./../../http-common.js";

export default {
  name: "TableV1",
  props: ["collection", "title", "template"],
  data() {
    return {
      data: {}
    };
  },
  methods: {
    submit: function() {
      console.log("TO DO:", this.template.method, this.url, this.data);
    },
    followLink: function(link, rel) {
      // Similar to the one we used for 'OpenOrders.vue'
      HTTP()({
        method: link.type,
        url: link.href
      }).then(response => {
        this.collection = this.collection.map(item => {
          if (response.data.id === item.id) {
            return response.data;
          } else {
            return item;
          }
        });
        // .findIndex( response.data);
        // Vue.set(this.orders, pos, response.data);
      });
    }
  }
};
</script>