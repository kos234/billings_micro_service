<script setup>
import {pingBackend} from "./api.js"
import {onMounted, ref} from "vue";
import MainInfo from "@/components/MainInfo.vue";
import {useBillingStatesStore} from "@/stores/billingStatesStore.js";

const billingStates = useBillingStatesStore()

onMounted(async () => {
  pingBackend()
      .then(res => res.text().then())
      .then(text => {
        if (text !== "pong") throw Error(text)
      })
      .catch(e => {
        console.log(e)
        alert("Сервер недоступен")
      })
  await billingStates.init();
})
</script>

<template>
  <nav>
    <RouterLink to="/">Главная</RouterLink>
    <RouterLink to="/logout">Выйти</RouterLink>
  </nav>
  <main class="container">
    <RouterView />
  </main>
</template>

<style>
.container {
  max-width: 1000px;
  margin: 0 auto;
}

.summary-cards {
  display: flex;
  gap: 20px;
  margin-bottom: 30px;
}

.card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  flex: 1;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  text-align: center;
}

.card-title {
  font-size: 14px;
  color: #777;
  margin-bottom: 8px;
}

.card-value {
  font-size: 28px;
  font-weight: bold;
  color: #2c3e50;
}

.section {
  margin-bottom: 30px;
}

.section-title {
  font-size: 20px;
  margin-bottom: 16px;
  color: #2c3e50;
}


.horizontal-list {
  display: flex;
  gap: 16px;
  overflow-x: auto;
  padding-bottom: 10px;
}

.horizontal-item {
  min-width: 220px;
  background: white;
  border-radius: 10px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  flex-shrink: 0;
}

.horizontal-item h4 {
  margin-bottom: 8px;
  color: #2c3e50;
}

.horizontal-item p {
  font-size: 14px;
  color: #555;
}


.vertical-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.vertical-item {
  background: white;
  padding: 14px;
  border-radius: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  display: flex;
  justify-content: space-between;
}

.vertical-item .desc {
  font-weight: 600;
}

.vertical-item .amount {
  font-family: monospace;
  font-weight: bold;
}
</style>
