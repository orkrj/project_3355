//import {createNavbar} from "../navbar";


const submitButton = document.querySelector("#submitButton");

addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
async function addAllElements() {
    createNavbar();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
    submitButton.addEventListener("click", handleSubmit);
}


async function handleSubmit(e) {
    e.preventDefault();

//로그아웃
// 1. access token 삭제 (sessionStorage)
// 2. refresh token 삭제 (cookie에서 삭제 이건 로그아웃 필터 로직에 포함이 되어있음)
    const response = await fetch("/logout", {
        method: "POST", // POST 요청
        credentials: "include", // 쿠키 포함
        headers: {
            "Content-Type": "application/json", // 요청 타입 명시
        },

    });

    if(response.ok){
        sessionStorage.removeItem('Authorization');
        alert("로그아웃되었습니다");
       // window.location.href="";  //로그아웃 후에도 이동?
    }
    else {
        alert("로그아웃 실패");
    }
}