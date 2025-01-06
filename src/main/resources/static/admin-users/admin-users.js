import { addCommas, checkAdmin, createNavbar } from "../../useful-functions.js";
import * as Api from "../../api.js";

// 요소(element), input 혹은 상수
const usersCount = document.querySelector("#usersCount"); //회원 수
const adminCount = document.querySelector("#adminCount"); //관리자 수 -> 삭제할예정
const usersContainer = document.querySelector("#usersContainer"); //가입날짜 이메일 이런거 써있는거..
const modal = document.querySelector("#modal"); //회원정보 삭제할지 묻는 모달창
const modalBackground = document.querySelector("#modalBackground");
const modalCloseButton = document.querySelector("#modalCloseButton");
const deleteCompleteButton = document.querySelector("#deleteCompleteButton"); //삭제 ok 버튼
const deleteCancelButton = document.querySelector("#deleteCancelButton"); //삭제 취소 버튼

//checkAdmin();
addAllElements();
addAllEvents();

// 요소 삽입 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  AdminCheck();
 // createNavbar();
  insertUsers();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  modalBackground.addEventListener("click", closeModal); //모달 창 닫기
  modalCloseButton.addEventListener("click", closeModal); //모달 창 닫기
  document.addEventListener("keydown", keyDownCloseModal); //모달 창 닫기
  deleteCompleteButton.addEventListener("click", deleteUserData); //회원 탈퇴
  deleteCancelButton.addEventListener("click", cancelDelete); //뭐지이건
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


// 페이지 로드 시 실행, 삭제할 회원 id를 전역변수로 관리함
let userIdToDelete;

async function insertUsers() {

  const response = await fetch("/api/user/findAllDto", {
    method: "GET",
    credentials : "include",
    headers: {
      "Authorization" : sessionStorage.getItem("Authorization"),
      "Content-Type": "application/json"
    }
  });

  const users = await response.json();  //json으로 변환을 안 해줘서 TypeError: users is not iterable 에러 뜸


    // 총 요약에 활용
    const summary = {
      usersCount: 0,
      //adminCount: 0,
    };


  for (const user of users) {
    const {user_id, username, real_name, email, phone, role} = user; //변수명을 fetch에서 받아오는 데이터와 동일하게 맞춰줘야 제대로 내려옴
    // const date = createdAt;

    const isAdmin = role.includes("ADMIN");

    summary.usersCount += 1;
    /*
        if (roles.includes('ADMIN')) {
          summary.adminCount += 1;
        }
    */
    usersContainer.insertAdjacentHTML( //사용자 정보를 html로 변환하여 삽입..
        "beforeend",
        `
        <div class="columns orders-item" id="user-${user_id}">
          <div class="column is-2">${username}</div>
          <div class="column is-2">${real_name}</div>
          <div class="column is-2">${email}</div>
          <div class="column is-2">${phone}</div>
           <div class="column is-2">
            ${isAdmin ? "관리자" : `<button class="button" id="deleteButton-${user_id}">회원 탈퇴</button>`}
          </div>
        </div>
      `
    );

    // 요소 선택
    if(!isAdmin){
      const deleteButton = document.querySelector(`#deleteButton-${user_id}`);

      // 이벤트 - 삭제버튼 클릭 시 Modal 창 띄우고, 동시에, 전역변수에 해당 주문의 id 할당
      deleteButton.addEventListener("click", () => {
        userIdToDelete = user_id;
        openModal();
      });
    }

      }

      // 총 요약에 값 삽입
      usersCount.innerText = addCommas(summary.usersCount);
     // adminCount.innerText = addCommas(summary.adminCount);
}

// db에서 회원정보 삭제
async function deleteUserData(e) {
  e.preventDefault();

    const response = await fetch(`/api/user/adminDelete/${userIdToDelete}`, { //삭제 진행
      method: "DELETE"
    });

    if(response.ok){
      alert("회원 정보가 삭제되었습니다.");

      // 삭제한 아이템 화면에서 지우기
      const deletedItem = document.querySelector(`#user-${userIdToDelete}`);
      deletedItem.remove();

      // 전역변수 초기화
      userIdToDelete = "";

      closeModal();
    }
    else{
      alert("회원정보 삭제 과정에서 오류가 발생하였습니다.");
    }

}

// Modal 창에서 아니오 클릭할 시, 전역 변수를 다시 초기화함.
function cancelDelete() {
  userIdToDelete = "";
  closeModal();
}

// Modal 창 열기
function openModal() {
  modal.classList.add("is-active");
}

// Modal 창 닫기
function closeModal() {
  modal.classList.remove("is-active");
}

// 키보드로 Modal 창 닫기
function keyDownCloseModal(e) {
  // Esc 키
  if (e.keyCode === 27) {
    closeModal();
  }
}
