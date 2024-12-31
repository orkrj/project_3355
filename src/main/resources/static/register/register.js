import * as Api from "../api.js";
import { validateEmail, createNavbar } from "../useful-functions.js";

// 요소(element), input 혹은 상수
const real_nameInput = document.querySelector("#real_nameInput"); //이름
const emailInput = document.querySelector("#emailInput"); //이메일
const passwordInput = document.querySelector("#passwordInput"); //비밀번호
const passwordConfirmInput = document.querySelector("#passwordConfirmInput"); //비밀번호 확인

const IdInput = document.querySelector("#IdInput"); //아이디
const PhoneInput = document.querySelector("#PhoneInput"); //휴대폰 번호

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

// 회원가입 진행
async function handleSubmit(e) {
  e.preventDefault();

  const real_name = real_nameInput.value;
  const email = emailInput.value;
  const password = passwordInput.value;
  const passwordConfirm = passwordConfirmInput.value;
  const id = IdInput.value;
  const phone = PhoneInput.value;

  document.getElementById("usernameError").innerText = "";
  document.getElementById("realNameError").innerText = "";
  document.getElementById("emailError").innerText = "";
  document.getElementById("passwordError").innerText = "";
  document.getElementById("passwordSameError").innerText = "";
  document.getElementById("phoneError").innerText = "";

  // 잘 입력했는지 확인
  const isRealNameValid = real_name.length >= 2; //이름 2글자 이상
  const isEmailValid = validateEmail(email); //이메일 형식 체크
  const isPasswordValid = (password.length < 8) || (password.length > 16); //비밀번호 길이가 8~16자
  const isPasswordSame = password === passwordConfirm; //비번 일치하는지
  const isIdValid = id.length >= 2;
  const isPhoneValid = phone.length===11;

  const phoneRegex = /^[0-9]+$/;

  let isValid = true;

  if (!isRealNameValid) {
    document.getElementById("usernameError").innerText = "아이디를 2글자 이상 입력해주세요.";
    isValid=false;
  }

  if (!isEmailValid) {
    document.getElementById("emailError").innerText = "이메일 형식이 맞지 않습니다.";
    isValid=false;
  }

  if (!isPasswordSame) {
    document.getElementById("passwordSameError").innerText = "비밀번호가 일치하지 않습니다.";
    isValid=false;
  }

  if(isPasswordValid){
    document.getElementById("passwordError").innerText = "비밀번호는 8자 이상, 16자 이하로 입력해야 합니다.";
    isValid=false;
  }

  if (!isIdValid) {
    document.getElementById("realNameError").innerText = "이름을 2글자 이상 입력해주세요.";
    isValid=false;
  }

  if ((!phoneRegex.test(phone)) && (!isPhoneValid)){
    document.getElementById("phoneError").innerText = "휴대폰 번호는 숫자만 입력해야합니다.";
    isValid=false;
  }

  if(!id){
    document.getElementById("usernameError").innerText = "아이디를 필수로 입력해주세요.";
    isValid=false;
  }

  if(!password){
    document.getElementById("passwordError").innerText = "비밀번호를 필수로 입력해주세요.";
    isValid=false;
  }

  if(!real_name){
    document.getElementById("realNameError").innerText = "이름을 입력해주세요.";
    isValid=false;
  }
  if(!email){
    document.getElementById("emailError").innerText = "이메일을 입력해주세요.";
    isValid=false;
  }
  if(!phone){
    document.getElementById("phoneError").innerText = "휴대폰 번호를 입력해주세요.";
    isValid=false;
  }

  // 회원가입 api 요청
if(isValid){
  const data = {
    username: id,
    password: password,
    real_name: real_name,
    email: email,
    phone: phone
  };

  const response = await fetch("/api/user", {
    method : "POST",
    headers : {
      "Content-Type" : "application/json",
    },
    body : JSON.stringify(data),});

  if(response.ok){
    alert("회원가입에 성공하셨습니다.");
    window.location.href = "/login/login.html";
  }
  else {
    alert("회원가입에 실패하셨습니다.");
  }
}
else {
  alert("회원가입에 실패하셨습니다.");
}

}