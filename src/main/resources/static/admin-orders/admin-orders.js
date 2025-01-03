import { checkLogin, createNavbar } from "../../useful-functions.js";
import * as Api from "../../api.js";


// 요소(element), input 혹은 상수
const ordersContainer = document.querySelector("#ordersContainer");
// const modal = document.querySelector("#modal");
// const modalBackground = document.querySelector("#modalBackground");
// const modalCloseButton = document.querySelector("#modalCloseButton");
// const deleteCompleteButton = document.querySelector("#deleteCompleteButton");
// const deleteCancelButton = document.querySelector("#deleteCancelButton");

// checkLogin();
addAllElements();
// addAllEvents();

// 요소 삽입 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  createNavbar();
  // insertOrders();
}

// 여러 개의 addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
// function addAllEvents() {
//    modalBackground.addEventListener("click", closeModal);
//    modalCloseButton.addEventListener("click", closeModal);
//   document.addEventListener("keydown", keyDownCloseModal);
//   deleteCompleteButton.addEventListener("click", deleteOrderData);
//   deleteCancelButton.addEventListener("click", cancelDelete);
// }

const getAddress = async function () {
  try {
    // 서버에 GET 요청 보내기
    const response = await fetch(`/api/order`, {
      method: 'GET',
    });

    if (!response.ok) {
      throw new Error(`Error fetching product`);
    }

    // JSON 데이터를 파싱
    const responseText = await response.text();
    console.log("responseText: "+responseText);
    let jsonData;
    try {
      jsonData = JSON.parse(responseText);
      console.log("변환된 JSON 데이터:", jsonData);
    } catch (error) {
      console.error("JSON 변환 중 오류 발생:", error.message);
    }

    return jsonData;
  } catch (error) {
    console.error("Failed to fetch product:", error);
    // 기본값을 반환하거나 에러를 다시 던짐
    throw error;
  }
};

// 함수 실행 부분
getAddress() .then(data => {
  console.log("Address data received:", data);
})
    .catch(error => { console.error("Error occurred while getting address:", error);
});


//내가 추가한 함수
// document.addEventListener('DOMContentLoaded', () => {
//   const button = document.querySelector('.toggle-button');
//   const details = document.querySelector('.product-details');
//
//   if (button && details) {
//     button.addEventListener('click', () => {
//       if (details.style.display === 'none') {
//         details.style.display = 'block';
//         button.textContent = '↑';
//       } else {
//         details.style.display = 'none';
//         button.textContent = '↓';
//       }
//     });
//   } else {
//     console.error('Button or details element not found.');
//   }
// });

//검색어 처리 로직
// document.getElementById('searchButton').addEventListener('click', function () {
//   const searchValue = document.getElementById('searchInput').value.toLowerCase();
//   const orders = document.querySelectorAll('#ordersContainer .columns');
//
//   orders.forEach(order => {
//     const date = order.dataset.date?.toLowerCase() || '';
//     const status = order.dataset.status?.toLowerCase() || '';
//     const info = order.dataset.info?.toLowerCase() || '';
//
//     if (date.includes(searchValue) || status.includes(searchValue) || info.includes(searchValue)) {
//       order.style.display = 'flex';
//     } else {
//       order.style.display = 'none';
//     }
//   });
// });



//샘플
const sampleText = `{
  "orderNumber": "202501027b67d1a5-61f",
  "status": "ORDERED",
  "summaryTittle": "생지 와이드 데님 팬츠 / 1개\\n상품3 / 1개",
  "totalPrice": 70000,
  "request": "배송 전 연락바랍니다.",
  "receiver": {
    "name": "테스트",
    "phoneNumber": "123123123",
    "zipCode": "06252",
    "streetAddress": "서울 강남구 강남대로 328  (역삼동)",
    "detailAddress": "서울역"
  },
  "productOrdersResponseDto": []
}`;

// JSON 파싱
const orderData = JSON.parse(sampleText);

// summaryTittle를 \n으로 분리
const summaryParts = orderData.summaryTittle.split("\n");
const mainProduct = summaryParts[0]; // 첫 번째 항목
const additionalProducts = summaryParts.slice(1).join(" / "); // 나머지 항목



// 새로 추가할 HTML 템플릿 생성
const newOrderHTML = `
  <div class="columns notification is-info is-light is-mobile orders-top">
    <div class="column is-2 order-date">${new Date().toISOString().split('T')[0]}</div>
    <div class="column is-2 userId">user123</div>
    <div class="column is-4 order-product" id="order-product">
      <span>${mainProduct}</span>
      <button class="toggle-button">↓</button>
      <span class="product-details" style="display: none;">${additionalProducts || ""}</span>
    </div>
    <div class="column is-2 order-total">${orderData.totalPrice.toLocaleString()}원</div>
  </div>
`;

// 기존 컨테이너에 새 HTML 삽입
ordersContainer.insertAdjacentHTML('beforeend', newOrderHTML);

// 토글 버튼 기능 추가 (상품 상세 보기)
document.querySelectorAll('.toggle-button').forEach(button => {
  button.addEventListener('click', function () {
    const details = this.nextElementSibling;
    if (details.style.display === 'none') {
      details.style.display = 'block';
      this.textContent = '↑';
    } else {
      details.style.display = 'none';
      this.textContent = '↓';
    }
  });
});



