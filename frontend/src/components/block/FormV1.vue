<template>
  <div class="card">
    <header class="card-header">
      <p class="card-header-title">{{title}}</p>
    </header>
    <section>
      <b-field v-for="(p, index) in template" :key="index" :label="p.name">
        <b-datepicker
          placeholder="Click to select..."
          icon="calendar-today"
          v-if="p.name.toLowerCase().includes('date') && !p.readonly"
          v-model="data[p.name]"
        ></b-datepicker>
        <b-input v-model="data[p.name]" readonly="p.readonly" v-else></b-input>
      </b-field>
      <a class="button" type="button" @click="submitHandler()">Submit</a>
    </section>
  </div>
</template>

<script>
export default {
  name: "FormV1",
  props: ["title", "template", "mode", "defaultData", "submit", "callback"],
  data() {
    return {
      data: {}
    };
  },
  methods: {
    submitHandler: function() {
      this.submit(this.data, this.callback);
    }
  },
  mounted() {
    if (this.defaultData) {
      this.data = this.defaultData;
    }
  }
};
</script>