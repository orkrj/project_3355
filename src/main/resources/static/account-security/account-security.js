import { checkLogin, createNavbar } from "../../useful-functions.js";
//import * as Api from "../../api.js";

// 요소(element), input 혹은 상수
const securityTitle = document.querySelector("#securityTitle"); //회원정보 관리
const fullNameInput = document.querySelector("#fullNameInput"); //이름

const passwordInput = document.querySelector("#passwordInput"); //비밀번호

const passwordConfirmInput = document.querySelector("#passwordConfirmInput"); //비밀번호 확인

const phoneNumberInput = document.querySelector("#phoneNumberInput"); //휴대폰 번호

const modal = document.querySelector("#modal");
const modalBackground = document.querySelector("#modalBackground");
const modalCloseButton = document.querySelector("#modalCloseButton");
const currentPasswordInput = document.querySelector("#currentPasswordInput"); //현재 비밀번호 입력
const saveCompleteButton = document.querySelector("#saveCompleteButton"); //찐 저장 (모달)

const idInput = document.querySelector("#IdInput"); //아이디
const emailInput = document.querySelector("#emailInput"); //이메일

const token = sessionStorage.getItem("Authorization");

//checkLogin();
addAllElements();
addAllEvents();

// 요소 삽입 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  LoginCheck();
  createNavbar();
  insertUserData();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  saveCompleteButton.addEventListener("click", saveUserData);
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



// 페이지 로드 시 실행
// 나중에 사용자가 데이터를 변경했는지 확인하기 위해, 전역 변수로 userData 설정
let userData;
async function insertUserData() {
 // userData = await Api.get("/users");

  const response = await fetch("/api/user/info", {
    method : "GET",
    credentials : "include",
    headers : {
      Authorization: `Bearer ${token}`,
      "Content-Type" : "application/json"
    }
  });

  const userData = await response.json();

  document.getElementById("IdInput").value = userData.username;
  document.getElementById("fullNameInput").value = userData.real_name;
  document.getElementById("emailInput").value = userData.email;
  document.getElementById("phoneNumberInput").value = userData.phone;
}


// db에 정보 저장 -> 다시 작성
async function saveUserData(e) {
  e.preventDefault();

  /*
    1. 변경된 내용이 없는 경우 -> 변경된 내용이 없습니다 -> 굳이?
    2. 유효성 검사
    3.
   */

  const fullName = fullNameInput.value; //이름
  const password = passwordInput.value; //비번
  const passwordConfirm = passwordConfirmInput.value; //비번확인
  const phoneNumber = phoneNumberInput.value; //휴대폰번호
  const currentPassword = currentPasswordInput.value; //현재 비밀번호
  const id = idInput.value //아이디
  const email = emailInput.value; //이메일

  document.getElementById("passwordError").innerText = "";
  document.getElementById("passwordConfirmError").innerText = "";
  document.getElementById("emailError").innerText = "";
  document.getElementById("fullNameError").innerText = "";
  document.getElementById("phoneError").innerText = "";

  const isPasswordLong = (password.length >= 8) && (password.length <=16);
  const isPasswordSame = password === passwordConfirm;

  const isNameValid = fullName.length >= 2; //이름 2글자 이상
  const isPhoneValid = phoneNumber.length===11;

  const phoneRegex = /^[0-9]+$/;

  const EmailRegex = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  let isValid = true;

  if (password && !isPasswordLong) {
   // closeModal();
    document.getElementById("passwordError").innerText = "비밀번호는 8자 이상, 16자 이하여야합니다.";
    isValid=false;
  }
  if (password && !isPasswordSame) {
    //closeModal();
    document.getElementById("passwordConfirmError").innerText = "비밀번호가 일치하지 않습니다.";
    isValid=false;
  }

  if(!isNameValid){
    document.getElementById("fullNameError").innerText = "이름은 두글자 이상입니다.";
    isValid=false;
  }

  if((!isPhoneValid) && !phoneRegex.test(phoneNumber)){
    document.getElementById("phoneError").innerText = "휴대폰 번호를 정확히 입력해주세요.";
    isValid=false;
  }

  if(!EmailRegex.test(email)){
    document.getElementById("emailError").innerText = "이메일을 정확히 입력해주세요.";
    isValid=false;
  }

  if(!password){
    document.getElementById("passwordError").innerText = "비밀번호를 필수로 입력해주세요.";
    isValid=false;
  }

  if(!fullName){
    document.getElementById("fullNameError").innerText = "이름을 입력해주세요.";
    isValid=false;
  }
  if(!email){
    document.getElementById("emailError").innerText = "이메일을 입력해주세요.";
    isValid=false;
  }
  if(!phoneNumber){
    document.getElementById("phoneError").innerText = "휴대폰 번호를 입력해주세요.";
    isValid=false;
  }



  //const data = { currentPassword };

  if(isValid){
    const data1 = {
      username : id,
      password : password,
      real_name : fullName,
      email : email,
      phone : phoneNumber
    };


    const response = await fetch("/api/user/update", {
      method : "PUT",
      credentials : "include",
      headers : {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body : JSON.stringify(data1)
    });

    if(response.ok){
      alert("회원 정보 수정이 완료되었습니다.");
      window.location.href="/account/account.html"; //마이페이지로?
    }
    else{
      alert("오류 발생");
    }
  }
  else {
    alert("다시 입력해주세요.");
  }

}