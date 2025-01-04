import { checkLogin, createNavbar } from "../useful-functions.js";

//checkLogin();
//createNavbar();

addAllElements();

function addAllElements() {
    LoginCheck();
}

async function LoginCheck(){
    const authToken = sessionStorage.getItem("Authorization");
    if (!authToken) {
        document.addEventListener("DOMContentLoaded", () => {
            alert("로그인이 필요합니다.");
            window.location.href = "/login/login.html";
        });
    }
}


