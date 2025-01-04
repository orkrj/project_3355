import { addCommas, checkAdmin, createNavbar } from "../useful-functions.js";
import * as Api from "../api.js";

// 요소
const productsContainer = document.querySelector("#productsContainer");
const searchCategory = document.querySelector("#searchCategory");
const searchInput = document.querySelector("#searchInput");
const searchButton = document.querySelector("#searchButton");
const sortCriteria = document.querySelector("#sortCriteria");
const sortDirection = document.querySelector("#sortDirection");
const sortButton = document.querySelector("#sortButton");
const modal = document.querySelector("#modal");
const modalBackground = document.querySelector("#modalBackground");
const modalCloseButton = document.querySelector("#modalCloseButton");
const deleteCompleteButton = document.querySelector("#deleteCompleteButton");
const deleteCancelButton = document.querySelector("#deleteCancelButton");
const paginationContainer = document.querySelector("#pagination");

// 전역 변수
let productIdToDelete;

// 페이징 처리 변수
let currentPage = 0;
let totalPages = 1;

// 검색 상태 변수
let currentSearchCategory = "name"; // 기본 값
let currentSearchInput = ""; // 기본 값

// 페이지 로드 시 실행
checkAdmin(); // 관리자 확인 함수 (필요시 활성화)
addAllElements();
addAllEvents();

// 요소 삽입 함수들을 묶어주는 역할
function addAllElements() {
  createNavbar();
  insertProducts();
}

// 여러 개의 addEventListener들을 묶어주는 역할
function addAllEvents() {
  modalBackground.addEventListener("click", closeModal);
  modalCloseButton.addEventListener("click", closeModal);
  document.addEventListener("keydown", keyDownCloseModal);
  deleteCompleteButton.addEventListener("click", deleteProductData);
  deleteCancelButton.addEventListener("click", cancelDelete);
  sortButton.addEventListener("click", applySort);
  paginationContainer.addEventListener("click", handlePaginationClick);
  searchButton.addEventListener("click", () => {
    applySearch(0, 8, "createdAt", "DESC");
  });
}

// 상품 목록 삽입
async function insertProducts(page = 0, size = 8, sortBy = "createdAt", direction = "DESC") {
  const productTableBody = document.querySelector("#productTableBody");
  productTableBody.innerHTML = ""; // 기존 상품 목록 초기화

  const products = await Api.get(
      `/api/product/page?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`
  );

  currentPage = page;

  for (const product of products.content) {
    const { productId, name, categoryName, price, stockQuantity, createdAt, updatedAt } = product;

    // 날짜 형식 변환
    const formattedCreatedAt = formatDate(createdAt);
    const formattedUpdatedAt = formatDate(updatedAt);

    const row = document.createElement("tr");
    row.classList.add("product-item");
    row.id = `product-${productId}`;

    row.innerHTML = `
      <td>${categoryName}</td>
      <td>${productId}</td>
      <td>${name}</td>
      <td>${addCommas(price)}원</td>
      <td>${stockQuantity}</td>
      <td>${formattedCreatedAt}</td>
      <td>${formattedUpdatedAt}</td>
      <td>
        <a href="/product-detail/product-detail.html?productId=${productId}" class="button">바로가기</a>
      </td>
      <td>
        <a href="/product-add/product-add.html?productId=${productId}" class="button">수정</a>
      </td>
      <td>
        <button class="button is-danger" id="deleteButton-${productId}">삭제</button>
      </td>
    `;

    productTableBody.appendChild(row);

    const deleteButton = document.querySelector(`#deleteButton-${productId}`);
    deleteButton.addEventListener("click", () => {
      productIdToDelete = productId;
      openModal();
    });
  }

  totalPages = products.totalPages;
  createPaginationButtons(); // 페이징 버튼 생성
}

// 상품 삭제 처리
async function deleteProductData(e) {
  e.preventDefault();

  const response = await fetch(`/api/product/${productIdToDelete}`, {
    method: "DELETE",
  });

  if (response.ok) {
    alert("상품이 삭제되었습니다.");

    const deletedItem = document.querySelector(`#product-${productIdToDelete}`);
    deletedItem.remove();

    // 전역변수 초기화
    productIdToDelete = "";

    closeModal();
  } else {
    alert("상품 삭제 중 문제가 발생했습니다. 다시 시도해주세요.");
  }
}

// 삭제 취소
function cancelDelete() {
  productIdToDelete = "";
  closeModal();
}

// 모달 창 열기
function openModal() {
  modal.classList.add("is-active");
}

// 모달 창 닫기
function closeModal() {
  modal.classList.remove("is-active");
}

// 키보드로 모달 창 닫기
function keyDownCloseModal(e) {
  if (e.key === "Escape") closeModal();
}

// 검색 적용
async function applySearch(page = 0, size = 8, sortBy = "createdAt", direction = "DESC") {
  const searchCategoryValue = searchCategory.value;
  const searchInputValue = searchInput.value;

  currentSearchCategory = searchCategoryValue;
  currentSearchInput = searchInputValue;

  let products;

  if (searchCategoryValue === "name" && searchInputValue !== "") {
    products = await Api.get(
        `/api/product/search?name=${searchInputValue}&page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`
    );
  } else if (searchCategoryValue === "categoryName" && searchInputValue !== "") {
    products = await Api.get(
        `/api/product/category/${searchInputValue}?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`
    );
  } else {
    products = await Api.get(
        `/api/product/page?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`
    );
  }

  currentPage = page;
  const productTableBody = document.querySelector("#productTableBody");
  productTableBody.innerHTML = "";

  for (const product of products.content) {
    const { productId, name, categoryName, price, stockQuantity, createdAt, updatedAt } = product;

    // 날짜 형식 변환
    const formattedCreatedAt = formatDate(createdAt);
    const formattedUpdatedAt = formatDate(updatedAt);

    const row = document.createElement("tr");
    row.classList.add("product-item");
    row.id = `product-${productId}`;

    row.innerHTML = `
      <td>${categoryName}</td>
      <td>${productId}</td>
      <td>${name}</td>
      <td>${addCommas(price)}원</td>
      <td>${stockQuantity}</td>
      <td>${formattedCreatedAt}</td>
      <td>${formattedUpdatedAt}</td>
      <td>
        <a href="/product-detail/product-detail.html?productId=${productId}" class="button">바로가기</a>
      </td>
      <td>
        <a href="/product-add/product-add.html?productId=${productId}" class="button">수정</a>
      </td>
      <td>
        <button class="button is-danger" id="deleteButton-${productId}">삭제</button>
      </td>
    `;

    productTableBody.appendChild(row);

    const deleteButton = document.querySelector(`#deleteButton-${productId}`);
    deleteButton.addEventListener("click", () => {
      productIdToDelete = productId;
      openModal();
    });
  }

  totalPages = products.totalPages;
  createPaginationButtons();
}

// 정렬 적용
function applySort() {
  const criteria = sortCriteria.value;
  const direction = sortDirection.value;
  applySearch(currentPage, 8, criteria, direction);
}

// 페이징 버튼 생성
function createPaginationButtons() {
  paginationContainer.innerHTML = "";

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

  const criteria = sortCriteria.value; // 현재 정렬 기준
  const direction = sortDirection.value; // 현재 정렬 방향

  if (target.classList.contains("pagination-link")) {
    currentPage = parseInt(target.dataset.page, 10);
    applySearch(currentPage, 8, criteria, direction);
  } else if (target.classList.contains("pagination-previous")) {
    if (currentPage > 0) {
      currentPage--;
      applySearch(currentPage, 8, criteria, direction);
    }
  } else if (target.classList.contains("pagination-next")) {
    if (currentPage < totalPages - 1) {
      currentPage++;
      applySearch(currentPage, 8, criteria, direction);
    }
  }
}

// 날짜 포맷 함수
function formatDate(dateString) {
  const date = new Date(dateString);

  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더해줌
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  return `${year}-${month}-${day} ${hours}:${minutes}`;
}
