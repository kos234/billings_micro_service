<script setup>

import MainInfo from "@/components/MainInfo.vue";
import BillingList from "@/components/BillingList.vue";
</script>

<template>
  <header>
    <h1>Личный кабинет 33333</h1>
  </header>

  <!-- Баланс и задолженность -->
  <MainInfo/>

  <!-- Неоплаченные счета -->
  <div class="section">
    <h2 class="section-title">Неоплаченные счета</h2>
    <BillingList :short-billing="true" :states="['NOT_PAID']"/>
  </div>

  <!-- Все выставленные счета -->
  <div class="section">
    <h2 class="section-title">Все выставленные счета</h2>
    <BillingList :short-billing="false" />
  </div>

  <!-- Вертикальные ячейки истории операций -->
  <div class="section">
    <h2 class="section-title">История операций</h2>
    <div class="vertical-list">
      <div class="vertical-item">
        <span class="desc">Пополнение счёта</span>
        <span class="amount">+5 000 ₽</span>
      </div>
      <div class="vertical-item">
        <span class="desc">Оплата счёта #102</span>
        <span class="amount">–800 ₽</span>
      </div>
      <div class="vertical-item">
        <span class="desc">Пополнение счёта</span>
        <span class="amount">+10 000 ₽</span>
      </div>
      <div class="vertical-item">
        <span class="desc">Оплата счёта #98</span>
        <span class="amount">–1 500 ₽</span>
      </div>
      <div class="vertical-item" style="opacity: 0.5;">
        <span class="desc">...</span>
        <span class="amount">...</span>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>