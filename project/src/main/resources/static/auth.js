async function fetchWithToken(url, options = {}) {
    let accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");

    options.headers = {
        ...(options.headers || {}),
        "Content-Type": "application/json",
        "Authorization": "Bearer " + accessToken
    };

    let res = await fetch(url, options);

    if (res.status === 401 && refreshToken) {
        const refreshRes = await fetch("/auth/refresh", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({refreshToken})
        });

        if (refreshRes.ok) {
            const data = await refreshRes.json();
            accessToken = data.accessToken;
            localStorage.setItem("accessToken", accessToken);

            options.headers["Authorization"] = "Bearer " + accessToken;
            res = await fetch(url, options);
        } else {
            localStorage.clear();
            window.location.href = "/auth/login";
        }
    }

    return res;
}

function logout() {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("role");
    window.location.href = "/auth/login";
}
