async function logout() {
    const res = await fetch("/auth/logout", {
        method: "POST",
        credentials: "include"
    });

    if (res.ok) {
        console.log("로그아웃 성공");
        window.location.href = "/auth/login";
    } else {
        alert("ログアウトに失敗しました。");
    }
}