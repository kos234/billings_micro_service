<script setup>
import {onBeforeMount, ref} from "vue";
import {formatNumbers} from "../utils.js";
import {getMainInfo} from "@/api.js";

const balance = ref(0);
const debt = ref(0);

onBeforeMount(() => {
  getMainInfo().then(res => {
    balance.value = res.balance;
    debt.value = res.debt;
  })
})
</script>

<template>
  <div class="summary-cards">
    <div class="card">
      <div class="card-title">Баланс аккаунта</div>
      <div class="card-value">{{ formatNumbers(balance) }} ₽</div>
    </div>
    <div class="card">
      <div class="card-title">Сумма задолженностей</div>
      <div class="card-value">{{ formatNumbers(debt) }} ₽</div>
    </div>
  </div>
</template>

<style scoped>

</style>