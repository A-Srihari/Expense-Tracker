async function signup() {
    const data = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    if (!data.name || !data.email || !data.password) {
        alert("All fields are required");
        return;
    }

    const res = await fetch("/auth/signup", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const msg = await res.text();
    alert(msg);

    if (msg === "Signup successful") {
        window.location.href = "login.html";
    }
}

async function login() {
    const data = {
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    if (!data.email || !data.password) {
        alert("All fields are required");
        return;
    }

    const res = await fetch("/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });

    const msg = await res.text();
    alert(msg);

    if (msg === "Login successful") {
        window.location.href = "dashboard.html";
    }
}
