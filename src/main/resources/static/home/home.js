document.addEventListener("DOMContentLoaded", loadProducts);

async function loadProducts() {
  const productContainer = document.querySelector("#productContainer");

  try {
    const products = await fetch("/api/product")
        .then((res) => res.json());

    products.forEach((product) => {
      const { productId, name, price, mainImageUrls } = product;

      const imageUrl = mainImageUrls?.[0]?.split("?")[0] || "../noimage.jpg";

      const productCard = `
        <div class="column is-one-quarter">
          <div class="card" id="product-${productId}">
            <div class="card-image">
              <figure class="image is-4by3">
                <img src="${imageUrl}" alt="${name} 이미지" />
              </figure>
            </div>
            <div class="card-content">
              <p class="title is-5">${name}</p>
              <p class="subtitle is-6">${price.toLocaleString()}원</p>
            </div>
          </div>
        </div>
      `;

      productContainer.insertAdjacentHTML("beforeend", productCard);

      const card = document.querySelector(`#product-${productId}`);
      card.addEventListener("click", () => {
        window.location.href = `/product-detail/product-detail.html?productId=${productId}`;
      });
    });
  } catch (err) {
    console.error("상품 로딩 에러", err);
    productContainer.innerHTML = `
      <div class="notification is-danger">
        잠시 후 다시 이용해주셈
      </div>
    `;
  }
}