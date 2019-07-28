<template>
  <div class="modal-card" style="width: auto">
    <header class="modal-card-head">
      <p class="modal-card-title">{{title}}</p>
    </header>
    <section class="modal-card-body">
      <TableV1 v-if="mode === 'table'" title="title"></TableV1>
      <FormV1 v-if="mode=== 'form'" :title="title" :template="template" :defaultData="defaultData" :submit="submit" :callback="$parent.close" ></FormV1>
    </section>
    <footer class="modal-card-foot">
      <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
  </div>
</template>

<script>
import TableV1 from "./TableV1.vue";
import FormV1 from "./FormV1.vue";

export default {
  name: "ModalV1",
  props: ["collection", "title", "template", "mode", "defaultData", "submit"],
  components: {
    TableV1,
    FormV1
  }
};
</script>