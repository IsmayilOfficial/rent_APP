<template>
<div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">Purchase Order Details</p>
    </header>
    <section class="modal-card-body">
        <b-field label="Name">
            <b-input v-model="po.status" readonly></b-input>
        </b-field>
        <b-field label="Description">
            <b-input v-model="po.description" readonly></b-input>
        </b-field>
        <b-field label="Price">
            <b-input v-model="po.price" readonly></b-input>
        </b-field>
    </section>
    <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
</div>
</template>

<script>
export default {
    name: "PODetailsModal",
    props: ['po']
}
</script>