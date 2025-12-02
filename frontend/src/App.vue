<script setup>
import {pingBackend} from "./api.js"
import {onMounted, ref} from "vue";

const count = ref(0)

console.log("script")

onMounted(() => {
  console.log("onMounted")
    pingBackend()
        .then(res => res.text().then())
        .then(text => console.log(text))
        .catch(e => console.log(e))
})
</script>

<template>
  <header>
    <h1>Личный кабинет 33333</h1>
  </header>

  <!-- Баланс и задолженность -->
  <div class="summary-cards">
    <div class="card">
      <div class="card-title">Баланс аккаунта</div>
<!--      <div class="card-value">12 500 ₽</div>-->
      <div class="card-value">{{count}}</div>
    </div>
    <div class="card">
      <div class="card-title">Сумма задолженностей</div>
      <div class="card-value">3 200 ₽</div>
    </div>
  </div>

  <!-- Неоплаченные счета -->
  <div class="section">
    <h2 class="section-title">Неоплаченные счета</h2>
    <div class="horizontal-list">
      <div class="horizontal-item">
        <h4>Счёт #101</h4>
        <p>Срок: 2025-12-01</p>
        <p>Сумма: 1 200 ₽</p>
      </div>
      <div class="horizontal-item">
        <h4>Счёт #105</h4>
        <p>Срок: 2025-12-05</p>
        <p>Сумма: 2 000 ₽</p>
      </div>
    </div>
  </div>

  <!-- Все выставленные счета -->
  <div class="section">
    <h2 class="section-title">Все выставленные счета</h2>
    <div class="horizontal-list">
      <div class="horizontal-item">
        <h4>Счёт #101</h4>
        <p>Дата: 2025-11-10</p>
        <p>Статус: Не оплачен</p>
      </div>
      <div class="horizontal-item">
        <h4>Счёт #102</h4>
        <p>Дата: 2025-11-05</p>
        <p>Статус: Оплачен</p>
      </div>
      <div class="horizontal-item">
        <h4>Счёт #105</h4>
        <p>Дата: 2025-11-15</p>
        <p>Статус: Не оплачен</p>
      </div>
      <div class="horizontal-item" style="opacity: 0.4;">
        <h4>Счёт #...</h4>
        <p>Дата: ...</p>
        <p>Статус: ...</p>
      </div>
    </div>
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
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
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
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
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
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
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
