<template>
  <TableV1 :collection="plants" :template="template"></TableV1>
</template>
<script>
// @ is an alias to /src
import TableV1 from "@/components/block/TableV1.vue";
import { HTTP } from "./../http-common.js";

export default {
  name: "plantlisting",

  components: {
    TableV1
  },
  data: function() {
    return {
      plants: [],
      template: [
        {
          name: item => {
            return item.plantInfo["name"];
          },
          type: "computed",
          label: "Plant Name"
        },
        { name: "serialNumber", type: "text", label: "Serial Number" },
        {
          name: item => {
            return item.plantInfo["description"];
          },
          type: "computed",
          label: "Description"
        }
      ]
    };
  },
  mounted: function() {
    HTTP()
      .get("sales/plants/listing")
      .then(response => {
        this.plants = response.data;
      });
  }
};
</script>
