<script setup>
import {formatNumbers} from "@/utils.js";
import {useBillingStatesStore} from "@/stores/billingStatesStore.js";

const props = defineProps({
  data: {
    type: Object,
    required: false,
  },
  short: {
    type: Boolean,
    default: false,
    required: false,
  },
})

const billingState = useBillingStatesStore()
</script>

<template>
  <div class="horizontal-item">
    <h4>Счёт</h4>
    <p>От: {{data.registeredService.title}}</p>
    <p>Создан: {{data.createdAt}}</p>
    <p>Сумма: {{formatNumbers(data.amount)}} ₽</p>
    <p v-if="!short">Статус: {{billingState.getTitle(data.state)}}</p>
  </div>
</template>

<style scoped>

</style>