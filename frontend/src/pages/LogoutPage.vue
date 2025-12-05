<script setup>
  import {useUserStore} from "@/stores/userStore.js";
  import {onBeforeMount} from "vue";
  import router from "@/router/index.js";

  const userStore = useUserStore()

  onBeforeMount(() => {
    userStore.setNewUser("")
    router.push({path: "/login"})
  })
</script>

<template>

</template>

<style scoped>

</style>