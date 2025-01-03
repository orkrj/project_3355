import { createNavbar } from "../../useful-functions.js";



// 요소(element), input 혹은 상수
const ordersContainer = document.querySelector("#ordersContainer");

// checkLogin();
addAllElements();
// addAllEvents();

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
      for (const orderData of jsonData) {
        const summaryParts = orderData.summaryTitle.split("\n");
        const mainProduct = summaryParts[0]; // 첫 번째 항목
        const additionalProducts = summaryParts.slice(1).join(" / "); // 나머지 항목

        // 새로 추가할 HTML 템플릿 생성
        const newOrderHTML = `
         <div class="columns notification is-info is-light is-mobile orders-top">
            <div class="column is-2 order-date">${orderData.orderNumber.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')}</div>
            <div class="column is-2 userId">${orderData.user.username.toLocaleString()}</div>
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


// 정렬 버튼 이벤트 리스너
const sortForm = document.querySelector('.sortForm');
sortForm.addEventListener('submit', (event) => {
  event.preventDefault(); // 폼 제출 기본 동작 방지

  // 정렬 기준과 방식 가져오기
  const sortMethod = document.getElementById('sort-method').value; // 오름차순/내림차순

  const sortStandard = document.getElementById('sort-standard').value; // 정렬 기준
  let comparison = 0;
  console.log("sortMethod: " + sortMethod);

  // 정렬 함수 정의
  jsonData.sort((a, b) => {


    if (sortStandard === "날짜") {
      const dateA = a.orderNumber.substring(0, 8);
      const dateB = b.orderNumber.substring(0, 8);
      comparison = dateA.localeCompare(dateB);
    } else if (sortStandard === "사용자 ID") {
      comparison = a.username.localeCompare(b.username);
    } else if (sortStandard === "주문총액") {
      comparison = a.totalPrice - b.totalPrice;
    }


    return sortMethod === "내림차순" ? comparison * -1 : comparison;
  });

  console.log("comparison: " + comparison);


  // 필요하면 정렬된 결과를 화면에 업데이트
  const ordersContainer = document.getElementById('ordersContainer');
  ordersContainer.innerHTML = ''; // 기존 내용 제거

  // const newNavHTML = ` <div
  //     className="columns notification is-info is-light is-mobile orders-top"
  // >
  //   <div className="column is-2">주문날짜</div>
  //   <div className="column is-2">주문한 사용자 ID</div>
  //   <div className="column is-4">주문정보</div>
  //   <div className="column is-2">주문총액</div>
  // </div>
  // `;

  for (const orderData of jsonData) {
    const summaryParts = orderData.summaryTitle.split("\n");
    const mainProduct = summaryParts[0];
    const additionalProducts = summaryParts.slice(1).join(" / ");

    const newOrderHTML = `

      <div class="columns notification is-info is-light is-mobile orders-top">
        <div class="column is-2 order-date">${orderData.orderNumber.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')}</div>
        <div class="column is-2 userId">${orderData.user.username}</div>
        <div class="column is-4 order-product" id="order-product">
          <span>${mainProduct}</span>
          <button class="toggle-button">↓</button>
          <span class="product-details" style="display: none;">${additionalProducts || ""}</span>
        </div>
        <div class="column is-2 order-total">${orderData.totalPrice.toLocaleString()}원</div>
      </div>
    `;

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
});


// 검색 버튼 이벤트 리스너
const searchButton = document.getElementById('searchButton');
const searchInput = document.getElementById('searchInput');

searchButton.addEventListener('click', () => {
  const searchQuery = searchInput.value.trim().toLowerCase(); // 검색어 가져오기
  console.log("searchQuery: " + searchQuery);

  // 검색어가 포함된 항목 필터링
  const filteredSamples = jsonData.filter(order => {
    console.log("order.user.username: " + order.user.username);
    return order.user.username.toLowerCase().includes(searchQuery);
  });


  // 기존 내용 제거
  ordersContainer.innerHTML = '';

  // 필터링된 결과 화면에 출력
  for (const orderData of filteredSamples) {
    const summaryParts = orderData.summaryTitle.split("\n");
    const mainProduct = summaryParts[0];
    const additionalProducts = summaryParts.slice(1).join(" / ");

    const newOrderHTML = `
     <div
            class="columns notification is-info is-light is-mobile orders-top"
          >
            <div class="column is-2">주문날짜</div>
            <div class="column is-2">주문한 사용자 ID</div>
            <div class="column is-4">주문정보</div>
            <div class="column is-2">주문총액</div>
      </div>

      <div class="columns notification is-info is-light is-mobile orders-top">
        <div class="column is-2 order-date">${orderData.orderNumber.substring(0, 8).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3')}</div>
        <div class="column is-2 userId">${orderData.user.username}</div>
        <div class="column is-4 order-product" id="order-product">
          <span>${mainProduct}</span>
          <button class="toggle-button">↓</button>
          <span class="product-details" style="display: none;">${additionalProducts || ""}</span>
        </div>
        <div class="column is-2 order-total">${orderData.totalPrice.toLocaleString()}원</div>
      </div>
    `;

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
  }

  // 검색 결과 없을 때 처리
  if (filteredSamples.length === 0) {
    ordersContainer.innerHTML = '<p>검색 결과가 없습니다.</p>';
  }
});















