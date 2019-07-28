<template>
    <section>
        <b-field label="Plant Name">
            <b-input v-model="query.name"></b-input>
        </b-field>
        <b-field label="Start date">
            <b-datepicker
                placeholder="Click to select..."
                icon="calendar-today"
                v-model="query.startDate">
            </b-datepicker>
        </b-field>
        <b-field label="End date">
            <b-datepicker
                placeholder="Click to select..."
                icon="calendar-today"
                v-model="query.endDate"
                type=.modal-card-body
                >
            </b-datepicker>
        </b-field>
        <button 
            class="button is-primary is-outlined"
            v-on:click="submit">
            Submit
        </button>
    </section>
</template>

<script>
export default {
    name: "CatalogQuery",
    data: function() {
        return {
            query: {
                name: "",
                startDate: undefined,
                endDate: undefined
            }
        }
    },
    methods:{
        submit: function() {
            console.log(this.query);
            this.$emit("submitCatalogQuery", this.query)
        }
    }
}
</script>