window.addEventListener('DOMContentLoaded', initApp);

async function initApp() {
    document.querySelector("#login-form").addEventListener("submit", handleLoginSubmit);
}

async function fetchDataFrom(URL, options) {
    let response;

    try {
        response = await fetch(URL, options);
    } catch (error) {
        console.error("Got error: ", error);
    }

    return await response.json();
}

async function handleLoginSubmit(event) {
    event.preventDefault();
    let form = new FormData(document.querySelector("#login-form"));
    let username = form.get("username");
    let password = form.get("password");

    let credentials = {username:username, password:password};

    let options = {
        method: 'POST',
        headers: {
            'content-type': 'application/json',
        },
        body: JSON.stringify(credentials)
    };

    let loginIsValid = await fetchDataFrom("http://localhost:8080/api/login", options);
    if (loginIsValid) {
        window.location.href = "admin-page.html";
    }
    else {
        window.location.href = "login-page.html";
    }
}