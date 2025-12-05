<script setup>

import {ref} from "vue";
import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";

const devIds = ref([1, 2, 3, 4, 5, 6, 7])
const userStore = useUserStore()

function clickOnId(id){
  userStore.setNewUser(id)
  router.push({path: "/"})
}
</script>

<template>
  <div>Вход в систему счетов (DEV)</div>
  <div>Выберите один из готовых аккаунтов</div>
  <div>
    <div
        v-for="id in devIds"
        @click="clickOnId(id)"
        >
      ID {{id}} {{id === 7 ? "(not found)" : ""}}
    </div>
  </div>
</template>

<style scoped>

</style>