import { getImageUrl } from "../aws-s3.js";
import * as Api from "../api.js";
import {
  randomId,
  getUrlParams,
  addCommas,
  navigate,
  checkUrlParams,
  createNavbar,
} from "../useful-functions.js";

// 요소(element), input 혹은 상수
const productItemContainer = document.querySelector("#productItemContainer");
const paginationContainer = document.querySelector("#pagination");

// 페이징 처리 변수
let currentPage = 0;
let totalPages = 1;

// URL 파라미터로 카테고리 확인
const { categoryName } = getUrlParams();
//checkUrlParams("category");
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  createNavbar();
  addProductItemsToContainer();
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {
  paginationContainer.addEventListener("click", handlePaginationClick);
}

// 제품 아이템을 컨테이너에 추가하는 함수
async function addProductItemsToContainer() {
  productItemContainer.innerHTML = ""; // 기존 항목 제거

  let products;

  if (categoryName) {
    console.log(`API 요청 URL: /api/product/category/${categoryName}?page=${currentPage}`);
    products = await Api.get(`/api/product/category/${categoryName}?page=${currentPage}`);
  } else {
    console.log(`API 요청 URL: /api/product/page?page=${currentPage}`);
    products = await Api.get(`/api/product/page?page=${currentPage}`);
  }

  console.log(products);
  for (const product of products.content) {
    const { productId, name, description, mainImageUrls, price, categoryName } = product;
    const random = randomId();

    // S3 URL을 비동기적으로 가져옴
    const imageUrl = mainImageUrls?.[0]?.split("?")[0] || "../noimage.jpg";

    productItemContainer.insertAdjacentHTML(
        "beforeend",
        `
      <div class="message media product-item" id="a${random}">
        <div class="media-left">
          <figure class="image">
            <img
              src="${imageUrl}"
              alt="제품 이미지"
            />
          </figure>
        </div>
        <div class="media-content">
          <div class="content">
            <p class="category">${categoryName}</p>
            <p class="name">${name}</p>
            <p class="description">${description}</p>
            <p class="price">${addCommas(price)}원</p>
          </div>
        </div>
      </div>
      `
    );

    const productItem = document.querySelector(`#a${random}`);
    productItem.addEventListener(
        "click",
        navigate(`/product-detail/product-detail.html?productId=${productId}`)
    );
  }

  // 페이지 정보 업데이트
  totalPages = products.totalPages;
  createPaginationButtons(); // 페이징 버튼 생성
}

// 페이징 버튼 생성
function createPaginationButtons() {
  paginationContainer.innerHTML = ""; // 기존 버튼 제거

  const prevButton = document.createElement("a");
  prevButton.className = "pagination-previous";
  prevButton.innerText = "이전";
  prevButton.disabled = currentPage === 0;

  const nextButton = document.createElement("a");
  nextButton.className = "pagination-next";
  nextButton.innerText = "다음";
  nextButton.disabled = currentPage === totalPages - 1;

  paginationContainer.appendChild(prevButton);

  const pageList = document.createElement("ul");
  pageList.className = "pagination-list";

  for (let i = 0; i < totalPages; i++) {
    const pageItem = document.createElement("li");
    const pageLink = document.createElement("a");

    pageLink.className = `pagination-link ${i === currentPage ? "is-current" : ""}`;
    pageLink.innerText = i + 1;
    pageLink.dataset.page = i;

    pageItem.appendChild(pageLink);
    pageList.appendChild(pageItem);
  }

  paginationContainer.appendChild(pageList);
  paginationContainer.appendChild(nextButton);
}

// 페이징 버튼 클릭 이벤트 처리
function handlePaginationClick(event) {
  const target = event.target;

  if (target.classList.contains("pagination-link")) {
    currentPage = parseInt(target.dataset.page, 10);
    addProductItemsToContainer();
  } else if (target.classList.contains("pagination-previous")) {
    if (currentPage > 0) {
      currentPage--;
      addProductItemsToContainer();
    }
  } else if (target.classList.contains("pagination-next")) {
    if (currentPage < totalPages - 1) {
      currentPage++;
      addProductItemsToContainer();
    }
  }
}