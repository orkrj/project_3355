import { checkAdmin, createNavbar } from "../useful-functions.js";

//checkLogin();
//createNavbar();

addAllElements();

function addAllElements() {
    AdminCheck();
}

async function AdminCheck(){
    const token = sessionStorage.getItem("Authorization");
    if (!token) {
        document.addEventListener("DOMContentLoaded", () => {
            window.document.body.style.display = "block";
            alert("로그인이 필요합니다.");
            window.location.href = "/login/login.html";
        });
    }

    const response = await fetch("/api/user/admin-check", {
        method : "POST",
        headers: {
            "Content-Type" : "application/json",
            Authorization: `Bearer ${token}`,
        },
    });

    if(!response.ok){
        alert("관리자 전용 페이지입니다.");
        window.location.replace("/home/home.html");
    }

    const { result } = await response.json();
    console.log(result);

    if (result === "success") { // 관리자 체크 후 가져온 값이 success여야함
        window.document.body.style.display = "block";

        return;
    } else {
        alert("관리자 전용 페이지입니다.");
        window.location.replace("/home/home.html");
    }
}