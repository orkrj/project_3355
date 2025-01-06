import * as Api from "../../api.js";
import { checkLogin, createNavbar } from "../../useful-functions.js";

// 요소(element), input 혹은 상수
const passwordInput = document.querySelector("#passwordInput"); //비밀번호 확인
const modal = document.querySelector("#modal"); //모달 전체?
const modalBackground = document.querySelector("#modalBackground"); // 모달 전체? 모달과 모달백그라운드의 차이는??
const modalCloseButton = document.querySelector("#modalCloseButton"); //모달창 닫는 버튼
const deleteCompleteButton = document.querySelector("#deleteCompleteButton"); //모달창에서 삭제하는 버튼
const deleteCancelButton = document.querySelector("#deleteCancelButton"); //모달창에서 삭제 취소하는 버튼

//checkLogin();
addAllElements();
addAllEvents();

const token = sessionStorage.getItem("Authorization");

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
async function addAllElements() {
  LoginCheck();
  //createNavbar();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  submitButton.addEventListener("click", openModal); //submit(회원정보 안전하게 삭제하기) 버튼 누르면 모달 열림
  modalBackground.addEventListener("click", closeModal);
  modalCloseButton.addEventListener("click", closeModal);
  document.addEventListener("keydown", keyDownCloseModal);
  deleteCompleteButton.addEventListener("click", deleteUserData); //회원 정보 삭제, 얘 빼고는 다 창 닫기
  deleteCancelButton.addEventListener("click", closeModal);
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

// Modal 창 열기
function openModal(e) {
  if (e) {
    e.preventDefault();
  }
  modal.classList.add("is-active");
}

// Modal 창 닫기
function closeModal(e) {
  if (e) {
    e.preventDefault();
  }
  modal.classList.remove("is-active");
}

// 키보드로 Modal 창 닫기
function keyDownCloseModal(e) {
  // Esc 키
  if (e.keyCode === 27) {
    closeModal();
  }
}

// db에서 회원정보 삭제
// deleteCompleteButton 누르면 하단의 회원정보 삭제 함수 실행
async function deleteUserData(e) {
  e.preventDefault();

  /*
    1. 비밀번호를 입력한다
    2. 삭제하기 버튼을 누르면 모달 창이 뜨고, 다시 한 번 삭제할 것인지 물어봄
    3. '네' 버튼을 누르면 입력한 비밀번호와 db에 저장된 비밀번호가 같은지 확인하고
    4. 같으면 탈퇴 처리하고
    5. 다르면 오류메세지 출력
   */

  const data = passwordInput.value; //입력한 비밀번호
  const password = {password : data}; // {password: password}와 동일 비번을 객체로 저장함

  const response = await fetch("/api/user/passwordCheck",{
          method : "POST",
          credentials : "include",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type" : "application/json", //안 쓰면 Content-Type 'text/plain;charset=UTF-8' is not supported 오류
          },
          body : JSON.stringify(password)
      });

  if(response.ok){ //비밀번호 비교가 되었으면
    const isPasswordCorrect = await response.json(); //passwordCheck의 리턴값

    if(isPasswordCorrect === true) { //비번이 일치할 경우,
      const response = await fetch("/api/user/delete", { //삭제 진행
        method: "DELETE",
        credentials: "include", //쿠키 포함?
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) { // 삭제 성공
        alert("회원 탈퇴가 완료되었습니다.");
        closeModal();

        // 토큰 삭제
        sessionStorage.removeItem("Authorization");
        window.location.href = "/home/home.html"; //홈으로 이동
      }
      else { //삭제 실패
        const {error, errorMessage} = await response.json();
        if (error === "비즈니스 에러 발생") {
          alert(errorMessage);
        } else {
          alert("회원 탈퇴 중 에러가 발생했습니다.");
        }
        closeModal();
      }
    }
    else{ //비번이 일치하지 않으면
      alert("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
      closeModal();
    }
  }
  else { //비번 비교가 안 되었음
    alert("오류 발생");
  }

}




