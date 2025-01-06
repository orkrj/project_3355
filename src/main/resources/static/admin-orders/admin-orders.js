import { createNavbar, checkAdmin } from "../../useful-functions.js";



// 요소(element), input 혹은 상수
const ordersContainer = document.querySelector("#ordersContainer");
checkAdmin();
addAllElements();

// 요소 삽입 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  createNavbar();
}

let jsonData;

const getAddress = async function () {
  try {
    // 서버에 GET 요청 보내기
    const response = await fetch(`/api/order`, {
      method: 'GET',
      credentials : "include",
      headers: {
        "Authorization" : sessionStorage.getItem("Authorization"),
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) {
      throw new Error(`Error fetching product`);
    }

    // JSON 데이터를 파싱
    const responseText = await response.text();
    //console.log("responseText: "+responseText);

    try {
      jsonData = JSON.parse(responseText);
      console.log("변환된 JSON 데이터:", jsonData);

      const orderState = ["PENDING","ORDERED", "SHIPPING", "DELIVERED", "CANCELED" ];

      for (const orderData of jsonData) {
        const summaryParts = orderData.summaryTitle.split("\n");
        const mainProduct = summaryParts[0];
        const additionalProducts = summaryParts.slice(1).join(" / ");
        const orderId = orderData.orderId;

        //orderState에 현재 상태만 제외하고 저장하기 위해 코드 만듦
        let sample = "";
        for (const state of orderState) {
          sample += `<option value="${state}">${state}</option>`;
        }


        const newOrderHTML = `
            <div class="columns notification is-info is-light is-mobile orders-top" data-order-id="${orderId}">
              <div class="column is-2 order-date">${orderData.orderNumber.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')}</div>
              <div class="column is-2 userId">${orderData.user.username.toLocaleString()}</div>
              <div class="column is-4 order-product" id="order-product">
                <span>${mainProduct}</span>
                <button class="toggle-button">↓</button>
                <span class="product-details" style="display: none;">${additionalProducts || ""}</span>
                </div>
              <div class="column is-2 order-total">${orderData.totalPrice.toLocaleString()}원</div>
              <div class="column is-2 order-status">
                <span>${orderData.status.toLocaleString()}</span>
                 <select name="select-orderstate" class="select-orderstate">
                    <option value=""></option>
                    ${sample}
                 </select>
              </div>
            </div>
        `;

        ordersContainer.insertAdjacentHTML('beforeend', newOrderHTML);
      }

      // 이벤트 위임 적용
      ordersContainer.addEventListener('change', async function(event) {
        // select 요소에서만 동작하도록 제한
        if (event.target.classList.contains('select-orderstate')) {
          const selectElement = event.target; // 선택한 <select> 요소
          const newStatus = selectElement.value; // 새 상태 값 추출

          // 해당 <select>가 속한 주문 데이터 추출
          const parentRow = selectElement.closest('.columns'); // 가장 가까운 .columns 요소 찾기
          const orderId = parentRow.getAttribute('data-order-id'); // 주문 ID 추출

          console.log("newStatus: "+newStatus);

          // 서버에 상태 업데이트 요청
          try {
            const response = await fetch('/api/order/status', {
              method: 'PATCH',
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify({
                orderId: orderId, // 서버에서 필요한 주문 ID
                orderStatus: newStatus, // 새 상태 값
              }),
            });

            if (!response.ok) {
              throw new Error('Failed to update order status');
            }

            const updatedStatus = await response.json();

            // 화면에 상태 업데이트
            const statusSpan = parentRow.querySelector('.order-status span');
            if (statusSpan) {
              statusSpan.textContent = updatedStatus; // 상태 값을 업데이트
            }

            console.log(`Order status updated to: ${updatedStatus}`);
          } catch (error) {
            console.error('Error updating order status:', error);
          }
        }
      });


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



// 정렬 폼 이벤트 리스너
const sortForm = document.querySelector('.sortForm');
sortForm.addEventListener('submit', async (event) => {
  event.preventDefault(); // 폼 제출 기본 동작 방지

  try {
    // 데이터 가져오기
    const response = await fetch(`/api/order`, {
      method: 'GET',
      credentials : "include",
      headers: {
        "Authorization" : sessionStorage.getItem("Authorization"),
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) {
      throw new Error(`Error fetching orders: ${response.status} ${response.statusText}`);
    }

    const responseText = await response.text();
    let jsonData;

    try {
      // JSON 데이터를 파싱
      jsonData = JSON.parse(responseText);
      console.log("Parsed JSON Data:", jsonData);
    } catch (parseError) {
      console.error("Failed to parse JSON data:", parseError);
      return; // JSON 파싱 오류 발생 시 이후 코드 실행 중단
    }

    // 정렬 기준과 방식 가져오기
    const sortMethod = document.getElementById('sort-method').value; // 오름차순/내림차순
    const sortStandard = document.getElementById('sort-standard').value; // 정렬 기준
    let comparison = 0;

    // 정렬 함수 정의
    jsonData.sort((a, b) => {
      if (sortStandard === "날짜") {
        const dateA = a.orderNumber.substring(0, 8);
        const dateB = b.orderNumber.substring(0, 8);
        comparison = dateA.localeCompare(dateB);
      } else if (sortStandard === "사용자 ID") {
        comparison = a.user.username.localeCompare(b.user.username);
      } else if (sortStandard === "주문총액") {
        comparison = a.totalPrice - b.totalPrice;
      }

      return sortMethod === "내림차순" ? comparison * -1 : comparison;
    });

    // 화면에 정렬 결과 업데이트
    const ordersContainer = document.getElementById('ordersContainer');
    ordersContainer.innerHTML = ''; // 기존 내용 제거

    const newNavHTML = `
      <div class="columns notification is-info is-light is-mobile orders-top">
        <div class="column is-2">주문날짜</div>
        <div class="column is-2">주문한 사용자 ID</div>
        <div class="column is-4">주문정보</div>
        <div class="column is-2">주문총액</div>
        <div class="column is-2">주문상태</div>
      </div>`;
    ordersContainer.insertAdjacentHTML('beforeend', newNavHTML);

    // 주문 상태 옵션 정의
    const orderState = ["PENDING", "ORDERED", "SHIPPING", "DELIVERED", "CANCELED"];

    // 주문 데이터 처리
    for (const orderData of jsonData) {
      const summaryParts = orderData.summaryTitle.split("\n");
      const mainProduct = summaryParts[0];
      const additionalProducts = summaryParts.slice(1).join(" / ");
      const orderId = orderData.orderId;

      // 상태 옵션 생성
      let sample = "";
      for (const state of orderState) {
        sample += `<option value="${state}">${state}</option>`;
      }

      const newOrderHTML = `
        <div class="columns notification is-info is-light is-mobile orders-top" data-order-id="${orderId}">
          <div class="column is-2 order-date">${orderData.orderNumber.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')}</div>
          <div class="column is-2 userId">${orderData.user.username.toLocaleString()}</div>
          <div class="column is-4 order-product" id="order-product">
            <span>${mainProduct}</span>
            <button class="toggle-button">↓</button>
            <span class="product-details" style="display: none;">${additionalProducts || ""}</span>
          </div>
          <div class="column is-2 order-total">${orderData.totalPrice.toLocaleString()}원</div>
          <div class="column is-2 order-status">
            <span>${orderData.status}</span>
            <select name="select-orderstate" class="select-orderstate">
              <option value=""></option>
              ${sample}
            </select>
          </div>
        </div>`;
      ordersContainer.insertAdjacentHTML('beforeend', newOrderHTML);
    }

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
  } catch (error) {
    console.error('Failed to fetch or process orders:', error);
  }
});



// 검색 버튼 이벤트 리스너
searchButton.removeEventListener('click', searchHandler);
searchButton.addEventListener('click', searchHandler);

async function searchHandler() {
  const searchQuery = searchInput.value.trim().toLowerCase();
  console.log("Search Query:", searchQuery);

  try {
    // 데이터 가져오기
    const response = await fetch(`/api/order`, {
      method: 'GET',
      credentials : "include",
      headers: {
        "Authorization" : sessionStorage.getItem("Authorization"),
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) {
      throw new Error(`Error fetching orders: ${response.status} ${response.statusText}`);
    }

    const responseText = await response.text();

    try {
      // JSON 데이터를 파싱
      jsonData = JSON.parse(responseText);
      console.log("Parsed JSON Data3:", jsonData);
    } catch (parseError) {
      console.error("Failed to parse JSON data:", parseError);
      return; // JSON 파싱 오류 발생 시 이후 코드 실행 중단
    }

    // 기존 내용 제거
    ordersContainer.innerHTML = '';

    // 상단 네비게이션 HTML 추가
    const newNavHTML = ` 
      <div class="columns notification is-info is-light is-mobile orders-top">
        <div class="column is-2">주문날짜</div>
        <div class="column is-2">주문한 사용자 ID</div>
        <div class="column is-4">주문정보</div>
        <div class="column is-2">주문총액</div>
        <div class="column is-2">주문상태</div>
      </div>`;
    ordersContainer.insertAdjacentHTML('beforeend', newNavHTML);

    // 검색 필터링
    const filteredSamples = jsonData.filter(order =>
        order.user.username.toLowerCase().includes(searchQuery)
    );

    const orderStateOptions = ["PENDING", "ORDERED", "SHIPPING", "DELIVERED", "CANCELED"]
        .map(state => `<option value="${state}">${state}</option>`)
        .join('');

    // 필터링된 결과 출력
    if (filteredSamples.length > 0) {
      const orderHTML = filteredSamples.map(orderData => {
        const summaryParts = orderData.summaryTitle.split("\n");
        const mainProduct = summaryParts[0];
        const additionalProducts = summaryParts.slice(1).join(" / ");
        const orderId = orderData.orderId;

        return `
          <div class="columns notification is-info is-light is-mobile orders-top" data-order-id="${orderId}">
            <div class="column is-2 order-date">${orderData.orderNumber.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')}</div>
            <div class="column is-2 userId">${orderData.user.username}</div>
            <div class="column is-4 order-product" id="order-product">
              <span>${mainProduct}</span>
              <button class="toggle-button">↓</button>
              <span class="product-details" style="display: none;">${additionalProducts || ""}</span>
            </div>
            <div class="column is-2 order-total">${orderData.totalPrice.toLocaleString()}원</div>
            <div class="column is-2 order-status">
              <span>${orderData.status}</span>
              <select name="select-orderstate" class="select-orderstate">
                <option value=""></option>
                ${orderStateOptions}
              </select>
            </div>
          </div>`;
      }).join('');

      ordersContainer.insertAdjacentHTML('beforeend', orderHTML);

      // 토글 버튼 이벤트 추가
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
    } else {
      ordersContainer.innerHTML = '<p>검색 결과가 없습니다.</p>';
    }
  } catch (error) {
    console.error("Error during searchHandler execution:", error);
    ordersContainer.innerHTML = '<p>데이터를 불러오는 중 문제가 발생했습니다.</p>';
  }
}


















