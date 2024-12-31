import { addImageToS3 } from "../aws-s3.js";
import * as Api from "../api.js";
import { checkLogin, randomId, createNavbar } from "../useful-functions.js";

// 요소(element)들과 상수들
const nameInput = document.querySelector("#nameInput");
const categorySelectBox = document.querySelector("#categorySelectBox");
const priceInput = document.querySelector("#priceInput");
const stockInput = document.querySelector("#stockInput");
const detailDescriptionInput = document.querySelector("#detailDescriptionInput");
const mainImageInput = document.querySelector("#mainImageInput");
const desImageInput = document.querySelector("#desImageInput");
const submitButton = document.querySelector("#submitButton");
const registerProductForm = document.querySelector("#registerProductForm");
const mainFileNameSpan = document.querySelector("#mainFileNameSpan");
const desFileNameSpan = document.querySelector("#desFileNameSpan");

addAllElements();
addAllEvents();

function addAllElements() {
  createNavbar();
  addOptionsToSelectBox();
}

function addAllEvents() {
  mainImageInput.addEventListener("change", handleMainImageUpload);
  desImageInput.addEventListener("change", handleDesImageUpload);
  submitButton.addEventListener("click", handleSubmit);
  categorySelectBox.addEventListener("change", handleCategoryChange);
}

async function handleSubmit(e) {
  e.preventDefault();

  const name = nameInput.value;
  const categoryId = categorySelectBox.value;
  const price = parseInt(priceInput.value);
  const stockQuantity = parseInt(stockInput.value);
  const description = detailDescriptionInput.value;
  const mainImage = mainImageInput.files[0];
  const desImage = desImageInput.files[0];

  // 입력 칸이 비어 있으면 진행 불가
  if (!name || !categoryId || !price || !stockQuantity || !description) {
    return alert("빈 칸 없이 입력해 주세요.");
  }

  // 카테고리 선택 확인
  if (!categoryId || categoryId === "undefined") {
    return alert("카테고리를 선택해 주세요.");
  }

  if (mainImage && mainImage.size > 3e6) {
    return alert("메인 사진은 최대 3MB 크기까지 가능합니다.");
  }

  if (desImage && desImage.size > 3e6) {
    return alert("상세 설명 사진은 최대 3MB 크기까지 가능합니다.");
  }

  try {
    // 이미지 업로드 후, 반환된 S3 파일 경로가 null인 경우를 처리.
    const mainImageKey = mainImage ? await addImageToS3(mainImageInput, `products/main`) : null;
    const desImageKey = desImage ? await addImageToS3(desImageInput, `products/description`) : null;

    // 데이터 준비
    const formData = new FormData();
    formData.append("name", name);
    formData.append("categoryId", categoryId);
    formData.append("price", price);
    formData.append("stockQuantity", stockQuantity);
    formData.append("description", description);

    // 이미지 파일들 추가
    if (mainImageKey) {
      formData.append("mainImageFiles", mainImage); // 파일 추가
    }
    if (desImageKey) {
      formData.append("descriptionImageFiles", desImage); // 파일 추가
    }

    console.log("보낼 데이터:", formData);

    // 요청 보내기
    const response = await Api.post("/api/product", formData);

    if (response) {
      alert(`${name} 제품이 정상적으로 등록되었습니다.`);
    }

    // 폼 초기화
    registerProductForm.reset();
    mainFileNameSpan.innerText = "메인사진파일 (png, jpg, jpeg)";
    desFileNameSpan.innerText = "상세사진파일 (png, jpg, jpeg)";
    categorySelectBox.style.color = "black";
    categorySelectBox.style.backgroundColor = "white";
  } catch (err) {
    console.error(err.stack);
    alert(`문제가 발생하였습니다. 확인 후 다시 시도해 주세요: ${err.message}`);
  }
}

// 사용자가 사진을 업로드했을 때, 파일 이름이 화면에 나타나도록 함.
function handleMainImageUpload() {
  const file = mainImageInput.files[0];
  mainFileNameSpan.innerText = file ? file.name : "메인사진파일 (png, jpg, jpeg)";
}

function handleDesImageUpload() {
  const file = desImageInput.files[0];
  desFileNameSpan.innerText = file ? file.name : "상세사진파일 (png, jpg, jpeg)";
}

// 선택할 수 있는 카테고리 종류를 api로 가져와서, 옵션 태그를 만들어 삽입함.
async function addOptionsToSelectBox() {
  try {
    const categories = await Api.get("/api/category/findAll");
    categories.forEach((category) => {
      const { id, name } = category;

      categorySelectBox.insertAdjacentHTML(
          "beforeend",
          `
      <option value="${id}" class="notification">${name}</option>`
      );
    });
  } catch (err) {
    console.error("카테고리 목록을 가져오는 데 실패했습니다.", err);
    alert("카테고리 목록을 불러오는 데 문제가 발생했습니다.");
  }
}

function handleCategoryChange() {
  const index = categorySelectBox.selectedIndex;
  categorySelectBox.className = categorySelectBox[index].className;
}
