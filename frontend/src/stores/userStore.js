import { defineStore } from 'pinia'
import {computed, ref} from "vue";

export const useUserStore = defineStore('user_store', () => {
    const userJwt = ref(localStorage.getItem("userJwt") ?? "")
    const hasUser = computed(() => userJwt.value !== null && userJwt.value !== "")

    function setNewUser(jwt) {
        userJwt.value = jwt;
        localStorage.setItem("userJwt", jwt)
    }

    return { userJwt, hasUser, setNewUser}
})