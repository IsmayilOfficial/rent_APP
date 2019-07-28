<!-- eslint-disable vue/no-use-v-if-with-v-for,vue/no-confusing-v-for-v-if -->
<template>
  <div>
    <table class="table is-striped is-fullwidth">
      <thead>
        <tr>
          <th class="has-text-center">Plant</th>
          <th class="has-text-center">Rental period</th>
          <th class="has-text-center">Status</th>
          <th class="has-text-center">Price</th>
          <th class="has-text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(order, order_index) in orders" :key="order_index">
          <td>
            {{order.plant.name}}
            <br>
            <a
              class="button is-link is-small is-outlined"
              @click="plantModal(order.plant)"
            >See details</a>
          </td>
          <td>{{order.rentalPeriod.startDate}} / {{order.rentalPeriod.endDate}}</td>
          <td>{{order.status}}</td>
          <td class="has-text-right">{{order.total}}</td>

          <td>
            <a
              v-for="(link, index) in order.links"
              :key="index"
              v-if="link.rel !== 'self' && link.rel !== 'fetch'"
              v-bind:class="{ 'is-danger': link.type === 'DELETE', 'is-link': link.type !== 'DELETE' }"
              class="button is-small is-outlined"
              @click="followLink(link, order)"
            >{{link.rel}}</a>

            <a
              v-for="(link, rel) in order._links"
              :key="rel"
              v-if="rel !== 'self'"
              v-bind:class="{ 'is-danger': link.method === 'DELETE', 'is-link': link.method !== 'DELETE' }"
              class="button is-small is-outlined"
              @click="followLink(link, rel)"
            >{{rel}}</a>

            <a
              v-for="(link, index) in order.links"
              :key="index"
              @click="viewDetails(link, order)"
              class="button is-small is-outlined"
              v-if="link.rel ==='fetch'"
            >View Details</a>

            <!-- <a
              v-for="(link, rel) in order._links"
              :key="rel"
              v-if="rel ==='fetch'"
              class="button is-small is-outlined"
              @click="viewDetails({'href': link}, order)"
            >{{rel === 'fetch' ? 'View Details': ''}}</a>-->
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";
import PlantModal from "./PlantModal.vue";
import PoDetails from "./PODetailsModal.vue";
import PoUpdate from "./POUpdate.vue";
import Generic from "./Generic.vue";
import { HTTP } from "./../../http-common.js";

export default {
  name: "OpenOrders",
  data: function() {
    return {
      orders: []
    };
  },
  mounted: function() {
    HTTP()
      .get("sales/orders")
      .then(response => {
        this.orders = response.data;
      });
  },
  methods: {
    plantModal: function(plant) {
      this.$modal.open({
        parent: this,
        component: PlantModal,
        props: { plant: plant }
      });
    },
    viewDetails: function(po) {
      HTTP()
        .get(po.href)
        .then(response => {
          let podetail = response.data;
          // console.log(podetail);
          this.$modal.open({
            parent: this,
            component: PoDetails,
            props: { po: podetail }
          });
        });
    },
    // viewDetails: function(po) {
    //   HTTP()
    //     .get(po.href)
    //     .then(response => {
    //       let podetail = response.data;
    //       // console.log(podetail);
    //       this.$modal.open({
    //         parent: this,
    //         component: PoDetails,
    //         props: { po: podetail }
    //       });
    //     });
    // },
    followLink: function(link, order) {
      if (link.type) {
        if (link.type === "updated") {
          this.$modal.open({
            parent: this,
            component: PoUpdate,
            props: { po: order }
          });
        }

        HTTP()({
          method: link.type,
          url: link.href
        })
          .then(response => {
            this.orders = this.orders.map(item => {
              if (response.data._id === item._id) {
                return response.data;
              } else {
                return item;
              }
            });
            // .findIndex( response.data);
            // Vue.set(this.orders, pos, response.data);
          })
          .catch(error => console.log(error));
      } else {
        HTTP()
          .get(link.href)
          .then(response => {
            let collection = [];
            if (
              response.data._embedded &&
              response.data._embedded.purchaseOrderDToes
            )
              collection = response.data._embedded.purchaseOrderDToes;
            this.$modal.open({
              parent: this,
              component: Generic,
              props: {
                title: rel,
                collection: collection,
                template: response.data._templates.default,
                url: response.data._links.self.href
              }
            });
          })
          .catch(error => console.log(error));
      }
    }
  }
};
</script>