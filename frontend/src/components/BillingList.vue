<script setup>
import {onMounted, ref} from "vue";
import {formatNumbers} from "../utils.js"
import {getBillings} from "@/api.js";
import BillingItem from "@/components/BillingItem.vue";

const props = defineProps({
  servicesIds: {
    type: Array,
    required: false,
  },
  states: {
    type: Array,
    required: false,
  },
  shortBilling: {
    type: Boolean,
    default: false,
    required: false,
  },
})

const data = ref([])

onMounted(() => {
  getBillings(props.servicesIds ?? [], props.states ?? [])
      .then(res => data.value = res)
})
</script>

<template>
  <div class="horizontal-list">
    <BillingItem
        v-for="billing in data"
        :key="billing.uuid"
        :data="billing"
        :short="shortBilling"
        class="horizontal-item"/>
  </div>
</template>

<style scoped>

</style>