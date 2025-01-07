import { getImageUrl } from "../aws-s3.js";
import * as Api from "../api.js";
import {
  getUrlParams,
  addCommas,
  checkUrlParams,
  createNavbar,
} from "../useful-functions.js";
import { addToDb, putToDb } from "../indexed-db.js";

// 요소(element), input 혹은 상수
const productMainImageTag = document.querySelector("#productMainImageTag");
const manufacturerTag = document.querySelector("#manufacturerTag");
const titleTag = document.querySelector("#titleTag");
// const stockTag = document.querySelector("#stockTag"); // 재고 수량 기능 구현 시 사용
const priceTag = document.querySelector("#priceTag");
const detailDescriptionTag = document.querySelector("#detailDescriptionTag");
const addToCartButton = document.querySelector("#addToCartButton");
const purchaseButton = document.querySelector("#purchaseButton");
const productDesImageContainer = document.querySelector("#productDesImageContainer");

checkUrlParams("productId");
addAllElements();
addAllEvents();

// html에 요소를 추가하는 함수들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllElements() {
  createNavbar();
  insertProductData();
}

// addEventListener들을 묶어주어서 코드를 깔끔하게 하는 역할임.
function addAllEvents() {}

async function insertProductData() {
  const { productId } = getUrlParams();
  console.log("productId:", productId);
  //await Api.getpage(`/products/${productId}`);
  const product = await Api.get(`/api/product/${productId}`);

  // 객체 destructuring
  const {
    name,
    description,
    categoryName,
    mainImageUrls,
    stockQuantity,
    price,
    descriptionImageUrls
  } = product;

  const mainImageUrl = mainImageUrls?.[0]?.split("?")[0] || "../noimage.jpg";

  productMainImageTag.src = mainImageUrl;
  titleTag.innerText = name;
  detailDescriptionTag.innerText = description;
  manufacturerTag.innerText = categoryName;
  // stockTag.innerText = `${stockQuantity}개`; // 재고 수량 기능 구현 시 사용
  priceTag.innerText = `${addCommas(price)}원`;

  const sanitizedDescriptionImageUrls = descriptionImageUrls.map((url) =>
      url.split("?")[0]
  );

  productDesImageContainer.innerHTML = ""; // 기존 항목 제거

  // descriptionImageUrls를 순회하며 HTML 요소 추가
  sanitizedDescriptionImageUrls.forEach((imageUrl) => {
    productDesImageContainer.insertAdjacentHTML(
        "beforeend",
        `
      <figure class="image is-square">
        <img src="${imageUrl}" alt="상품 상세 이미지" />
      </figure>
      `
    );
  });

//장바구니는 페이지 이동은 하지 않고, 구매를 하면 페이지 이동을 시킨다
  addToCartButton.addEventListener("click", async () => {
    try {
      //장바구니 추가할 때 로그인되어 있는지 확인하고 아니면 리다이렉트하는 로직
      // const authToken = sessionStorage.getItem("Authorization");
      // if (!authToken) {
      //   alert("로그인이 필요합니다.");
      //   return; // 함수 종료
      // }

      await insertDb(product, productId);

      alert("장바구니에 추가");
    } catch (err) {
      // Key already exists 에러면 아래와 같이 alert함
      if (err.message.includes("Key")) {
        alert("이미 장바구니에 추가되어 있습니다.");
      }

      console.log(err);
    }
  });

  purchaseButton.addEventListener("click", async () => {
    try {
      //로그인되어 있는지 확인하하는 로직
      // const authToken = sessionStorage.getItem("Authorization");
      // if (!authToken) {
      //   alert("로그인이 필요합니다.");
      //   return; // 함수 종료
      // }

      await insertDb(product, productId);
      window.location.href = "../cart/cart.html";
    } catch (err) {
      if (err.message.includes("Key")) {
        alert("이미 장바구니에 추가되어 있습니다.");
      }
      console.log(err);

      //insertDb가 에러가 되는 경우는 이미 제품이 장바구니에 있던 경우임
      //따라서 다시 추가 안 하고 바로 order 페이지로 이동함
      window.location.href = "../cart/cart.html";
    }
  });
}

async function insertDb(product, productId) {
  // 객체 destructuring
  const { price } = product;
  const id = productId;

  // 장바구니 추가 시, indexedDB에 제품 데이터 및
  // 주문수량 (기본값 1)을 저장함.
  await addToDb("cart", { id: productId, quantity:1, checked:true }, id);

  // 장바구니 요약(=전체 총합)을 업데이트함.
  await putToDb("order", "summary", (data) => {
    // 기존 데이터를 가져옴
    const count = data.productsCount;
    const total = data.productsTotal;
    const ids = data.ids;
    const selectedIds = data.selectedIds;

    // 기존 데이터가 있다면 1을 추가하고, 없다면 초기값 1을 줌
    data.productsCount = count ? count + 1 : 1;

    // 기존 데이터가 있다면 가격만큼 추가하고, 없다면 초기값으로 해당 가격을 줌
    data.productsTotal = total ? total + price : price;

    // 기존 데이터(배열)가 있다면 id만 추가하고, 없다면 배열 새로 만듦
    data.ids = ids ? [...ids, id] : [id];

    // 위와 마찬가지 방식
    data.selectedIds = selectedIds ? [...selectedIds, id] : [id];
  });
}
