import { addCommas, createNavbar } from "../useful-functions.js";
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

// 전역 변수
let productIdToDelete;

// 페이지 로드 시 실행
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
  searchButton.addEventListener("click", applySearch);
  sortButton.addEventListener("click", applySort);
}

// 상품 목록 삽입
async function insertProducts(page = 0, size = 8, sortBy = "createdAt", direction = "DESC") {
  try {
    const { content: products } = await Api.get(
        "api/product/page",
        `?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`
    );

    console.log(products);

    productsContainer.innerHTML = ''; // 기존 상품 목록 초기화

    for (const product of products) {
      const { productId, name, category, price, stockQuantity, createdDate, updatedDate } = product;

      productsContainer.insertAdjacentHTML(
          "beforeend",
          `
          <div class="columns notification is-info is-light is-mobile product-item" id="product-${productId}">
            <div class="column">${category.name}</div>
            <div class="column">${productId}</div>
            <div class="column">${name}</div>
            <div class="column">${addCommas(price)}원</div>
            <div class="column">${stockQuantity}</div>
            <div class="column">${createdDate}</div>
            <div class="column">${updatedDate}</div>
            <div class="column">
              <a href="/product-detail/${productId}" class="button">바로가기</a>
            </div>
            <div class="column">
              <button class="button is-primary" id="editButton-${productId}">정보수정</button>
            </div>
            <div class="column">
              <button class="button is-danger" id="deleteButton-${productId}">삭제</button>
            </div>
          </div>
        `
      );

      const deleteButton = document.querySelector(`#deleteButton-${productId}`);
      deleteButton.addEventListener("click", () => {
        productIdToDelete = productId;
        openModal();
      });
    }
  } catch (err) {
    console.error("상품 목록을 가져오는 데 실패했습니다:", err);
    alert("상품 목록을 불러오는 중 문제가 발생했습니다. 다시 시도해주세요.");
  }
}

// 상품 삭제 처리
async function deleteProductData() {
  try {
    await Api.delete("api/product", productIdToDelete);
    alert("상품이 삭제되었습니다.");

    // 삭제한 상품 화면에서 제거
    const deletedItem = document.querySelector(`#product-${productIdToDelete}`);
    if (deletedItem) deletedItem.remove();

    productIdToDelete = ""; // 초기화
    closeModal();
  } catch (err) {
    console.error("상품 삭제 중 오류가 발생했습니다:", err);
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
async function applySearch() {
  const name = searchInput.value;

  try {
    const { content: products } = await Api.get(
        "api/product/search",
        `?name=${name}&page=0&size=8&sortBy=createdAt&direction=DESC`
    );

    productsContainer.innerHTML = ''; // 기존 목록 초기화
    for (const product of products) {
      const { productId, name, category, price, stockQuantity, createdDate, updatedDate } = product;
      productsContainer.insertAdjacentHTML(
          "beforeend",
          `
          <div class="columns notification is-info is-light is-mobile product-item" id="product-${productId}">
            <div class="column">${category.name}</div>
            <div class="column">${productId}</div>
            <div class="column">${name}</div>
            <div class="column">${addCommas(price)}원</div>
            <div class="column">${stockQuantity}</div>
            <div class="column">${createdDate}</div>
            <div class="column">${updatedDate}</div>
          </div>
        `
      );
    }
  } catch (err) {
    console.error("검색 중 문제가 발생했습니다:", err);
    alert("검색 중 문제가 발생했습니다. 다시 시도해주세요.");
  }
}

// 정렬 적용
function applySort() {
  const criteria = sortCriteria.value;
  const direction = sortDirection.value;
  insertProducts(0, 8, criteria, direction);
}
