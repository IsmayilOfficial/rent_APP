<template>
 <div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">{{title}}</p>
    </header>
    <section class="modal-card-body">

  <div v-if="collection instanceof Array && collection.length > 0">
  <table class="table is-striped is-fullwidth">
    <thead>
        <tr>
            <th class="has-text-center"
                v-for="(value, key) in collection[0]" :key="key" v-if="key !== '_links'">
                {{key}}
            </th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="(item, index) in collection" :key="index">
            <td v-for="(value, key) in item" :key="key" v-if="key !== '_links'">
                {{value}}
            </td>
            <td>
                <tr v-for="(link, rel) in item._links" :key="rel">
                    <a v-if="rel !== 'self'"
                        v-bind:class="{ 'is-danger': link.method === 'DELETE', 'is-link': link.method !== 'DELETE' }"
                        class="button is-small is-outlined"
                        @click="followLink(link, rel)">
                        {{rel}}
                    </a>
                </tr>
            </td>
        </tr>
    </tbody>
  </table>
  </div>
  <div class="card">
    <header class="card-header">
        <p class="card-header-title">Submit new {{title.slice(0,-1)}}</p>
    </header>
    <section>
        <b-field v-for="(p, index) in template.properties" :key="index"
            :label="p.name">
            <input v-model="data[index]" type="date" v-if="p.name.toLowerCase().includes('date')">
            <b-input v-model="data[index]" v-else></b-input>
        </b-field>
        <a class="button" type="button" @click="submit()">Submit</a>
    </section>
  </div>
    </section>
    <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
</div> 
</template>

<script>
export default {
    name: 'Generic',
    props: ['collection', 'title', 'template', 'url'],
    data() {
        return {
            data: {}
        }
    },
    methods: {
        submit: function() {
            let data = {};
            this.template.properties.forEach((tuple, index) => {
                data[tuple.name] = this.data[index];
            });
            console.log("TO DO:", this.template.method, this.url, data);
        },
        followLink: function (link, rel) {
            // Similar to the one we used for 'OpenOrders.vue'
        }
    }
}
</script>