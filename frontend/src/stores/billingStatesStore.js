import {defineStore} from "pinia";
import {ref} from "vue";
import {abstractFetch} from "@/api.js";
import {useInitializeStore} from "@/hooks/useInitializeStore.js";

export const useBillingStatesStore = defineStore('billing_states_store', () => {
    const billings = ref({})

    async function init(){
        billings.value = await abstractFetch("api/constants/billing_states")
            .then(res => res.json())
    }

    function getTitle(id){
        console.log("billings.value", billings.value)
        return billings.value[id]
    }

    return { billings, init, getTitle }
})