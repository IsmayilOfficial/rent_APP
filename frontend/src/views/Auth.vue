<template>
  <div class="about">
    <open-orders></open-orders>
  </div>
</template>
<script>
// @ is an alias to /src
import OpenOrders from '@/components/allocations/OpenOrders.vue'

export default {
  name: 'about',
  components: {
    OpenOrders
  }
}
</script>
