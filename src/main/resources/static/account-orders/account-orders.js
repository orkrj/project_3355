import {createNavbar} from "../useful-functions.js";
import * as Api from "../api.js";


// 요소(element), input 혹은 상수
const ordersContainer = document.querySelector("#ordersContainer");
const modal = document.querySelector("#modal");
const modalBackground = document.querySelector("#modalBackground");
const modalCloseButton = document.querySelector("#modalCloseButton");
const deleteCompleteButton = document.querySelector("#deleteCompleteButton");
const deleteCancelButton = document.querySelector("#deleteCancelButton");

// checkLogin();
addAllElements();
addAllEvents();

// 요소 삽입 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  createNavbar();
  insertOrders();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  modalBackground.addEventListener("click", closeModal);
  modalCloseButton.addEventListener("click", closeModal);
  document.addEventListener("keydown", keyDownCloseModal);
  deleteCompleteButton.addEventListener("click", deleteOrderData);
  deleteCancelButton.addEventListener("click", cancelDelete);
}

// 페이지 로드 시 실행, 삭제할 주문 id를 전역변수로 관리함
let orderIdToDelete;
async function insertOrders() {
  const orders = await Api.get("/api/order/user");

  for (const order of orders) {
    const { orderId, createdAt, summaryTitle, totalPrice, status } = order;
    // const date = createdAt.split("T")[0];
    const date = new Date(createdAt).toLocaleDateString('ko-KR');

    ordersContainer.insertAdjacentHTML(
      "beforeend",
      `
        <div class="columns orders-item" id="order-${orderId}">
          <div class="column is-2">${date}</div>
          <div class="column is-4 order-summary">${summaryTitle}</div>
          <div class="column is-2 order-totalPrice">${totalPrice}</div>
          <div class="column is-2" id="status-${orderId}">${status}</div>
          <div class="column is-2">
            <button class="button" id="deleteButton-${orderId}" ${status === "CANCELED" ? "disabled" : ""}>
                주문 취소
            </button>
          </div>
        </div>
      `
    );

    const deleteButton = document.querySelector(`#deleteButton-${orderId}`);

    // Modal 창 띄우고, 동시에, 전역변수에 해당 주문의 id 할당
    deleteButton.addEventListener("click", () => {
      orderIdToDelete = orderId;
      openModal();
    });
  }
}

// db에서 주문정보 삭제
async function deleteOrderData(e) {
  e.preventDefault();

  console.log(orderIdToDelete);

  try {
    const response = await fetch(`/api/order/status/${orderIdToDelete}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("Authorization")}`
      }
    })

    console.log(`%cGET 요청: /api/order/status/${orderIdToDelete} `, "color: #a25cd1;");

    const result = await response.json();

    if (result === "SHIPPING" || result === "DELIVERED") {
      switch (result) {
        case "SHIPPING":
          alert("배송 중인 주문을 취소할 수 없습니다.");
          break;
        case "DELIVERED":
          alert("배송 완료된 상품을 취소할 수 없습니다.\n고객 센터에 문의해주세요.");
          break;
      }

      closeModal();
      return;
    }

    await fetch(`/api/order/${orderIdToDelete}`, {
      method: "PATCH",
      headers: {
        Authorization: `Bearer ${sessionStorage.getItem("Authorization")}`
      }
    });

    // 삭제 성공
    alert("주문을 취소하였습니다.");
    const statusInfo = document.querySelector(`#status-${orderIdToDelete}`);
    statusInfo.textContent = (await getOrderStatus(orderIdToDelete)).replace(/"/g, "");

    // 삭제한 아이템 화면에서 지우기
    // const deletedItem = document.querySelector(`#order-${orderIdToDelete}`);
    // deletedItem.remove();

    // 전역변수 초기화
    orderIdToDelete = "";

    closeModal();
    window.location.reload();
  } catch (err) {
    alert(`주문 취소 과정에서 오류가 발생하였습니다: ${err}`);
  }
}

async function getOrderStatus(orderId) {
  const response = await fetch(`/api/order/status/${orderId}`);
  return await response.text();
}

// Modal 창에서 아니오 클릭할 시, 전역 변수를 다시 초기화함.
function cancelDelete() {
  orderIdToDelete = "";
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
