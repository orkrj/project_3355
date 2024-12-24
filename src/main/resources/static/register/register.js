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

  // 잘 입력했는지 확인
  const isRealNameValid = real_name.length >= 2;
  const isEmailValid = validateEmail(email);
  const isPasswordValid = (password.length >= 8) && (password.length <= 16);
  const isPasswordSame = password === passwordConfirm;

  const isIdValid = (id.length >= 2) && (id.length <= 10);
  //const isPhoneValid = phone.length==11 /*&& validatePhone(phone)*/;

  if (!isRealNameValid || !isPasswordValid) {
    return alert("이름은 2글자 이상이어야 합니다.");
  }

  if (!isEmailValid) {
    return alert("이메일 형식이 맞지 않습니다.");
  }

  if (!isPasswordSame) {
    return alert("비밀번호가 일치하지 않습니다.");
  }

  if (!isIdValid) {
    return alert("아이디는 2글자 이상, 10글자 이하입니다.");
  }

  // 회원가입 api 요청
 // try {
    /*  const data = { id, password, real_name, email, phone }; //json?

      await Api.post("/user", data);

      alert(`정상적으로 회원가입되었습니다.`);
      // 로그인 페이지 이동
      window.location.href = "/login";  //get?
    } catch (err) {
      console.error(err.stack);
      alert(`문제가 발생하였습니다. 확인 후 다시 시도해 주세요: ${err.message}`);
    }*/

    const data = {
      username: id,
      password: password,
      real_name: real_name,
      email: email,
      phone: phone
    };

    const response = await fetch("http://localhost:8080/user", {
      method : "POST",
      headers : {
        "Content-Type" : "application/json",
      },
      body : JSON.stringify(data),});

    if(response.ok){
      alert("회원가입 성공");
      //window.location.href = "/login"; //경로 수정 필요
    }
    else {
      alert("회원가입 실패");
    }

}