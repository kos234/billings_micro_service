import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";

const API_URL = "/api/"

export function pingBackend() {
    return abstractFetch(API_URL + "test/ping");
}

export function getMainInfo() {
    return authAbstractFetch(API_URL + "accounts/main")
        .then(result => result.json())
}

export function getBillings(serverIds, states) {
    return authAbstractFetch(API_URL + "accounts/invoices", {
        serviceId: serverIds,
        states: states,
    })
        .then(result => result.json())
}

export function abstractFetch(url, body = {}) {
    return fetch(url, {
        method: "POST",
        body: JSON.stringify(body)
    }).then(response => {
        if (!response.ok) throw Error(response.statusText);
        return response;
    })
}

export function authAbstractFetch(url, body = {}) {
    const userStore = useUserStore()

    return fetch(url, {
        method: "POST",
        headers: {Authorization: `Bearer ${userStore.userJwt}`},
        body: JSON.stringify(body)
    }).then(async response => {
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            const error = new Error(response.statusText);
            error.status = response.status;
            error.code = response.status;
            error.data = errorData;
            if (response.status === 401) {
                console.warn('Токен недействителен или истёк');
                userStore.setNewUser("");
                router.push({path: "/login"})
            }

            throw error;
        }
        return response;
    }).catch(err => {
        console.log("ERR", err)
    })
}