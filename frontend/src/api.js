const API_URL = "/api/"

export function pingBackend(){
    return abstractFetch(API_URL + "test/ping");
}

function abstractFetch(url, body = {}){
    return fetch(url, {
        method: "POST",
        body: JSON.stringify(body)
    }).then(response => {
        if (!response.ok) throw Error(response.statusText);
        return response;
    })
}