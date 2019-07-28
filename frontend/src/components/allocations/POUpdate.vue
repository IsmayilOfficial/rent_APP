<template>
<div class="modal-card" style="width: auto">
    <header class="modal-card-head">
        <p class="modal-card-title">Plant info</p>
    </header>
    <section class="modal-card-body">
        <b-field label="Name">
            <b-input v-model="plant.name" readonly></b-input>
        </b-field>
        <b-field label="Description">
            <b-input v-model="plant.description" readonly></b-input>
        </b-field>
        <b-field label="Price">
            <b-input v-model="plant.price" readonly></b-input>
        </b-field>
    </section>
    <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
    </footer>
</div>
</template>

<script>
export default {
    name: "PlantModal",
    props: ['plant']
}
</script>