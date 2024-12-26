const NAV_URL = "/api/category/findAll";

navFunction();

// 네비게이션 바를 생성하는 함수
async function navFunction() {

    // 네비게이션 바 컨테이너 가져오기
    const navContainer = document.getElementById("navContainer");
    navContainer.innerHTML = ""; // 기존 내용을 초기화

    // 네비게이션 바 HTML 구조 생성
    const navbarHTML = `
            <nav class="navbar is-light" role="navigation" aria-label="main navigation">
                <div class="container">
                    <div class="navbar-brand">
                        <a class="navbar-item" href="/">
                            <span class="has-text-link is-size-5 has-text-weight-bold">Shopping Mall</span>
                        </a>
                    </div>
                    <div id="navbarMenu" class="navbar-menu">
                        <div id="navbar-end" class="navbar-end"></div>
                    </div>
                </div>
            </nav>
        `;
    navContainer.innerHTML = navbarHTML;

    //네비게이션바 목록 들어갈곳
    const navbarEnd = document.getElementById("navbar-end");

    //카테고리 가져오기
    const response = await fetch(`${NAV_URL}`);
    if (response.ok) {
        const categories = await response.json();

        // parentId가 null인 루트 카테고리 && id가 5인 '미분류'만 필터링
        const rootCategories = categories.filter(category => !category.parentId && category.id !== 5);

        const childCategoriesMap = categories.reduce((acc, category) => {
            if (category.parentId) {
                acc[category.parentId] = acc[category.parentId] || [];
                acc[category.parentId].push(category);
            }
            return acc;
        }, {}); // 자식 카테고리를 부모 ID로 묶음

        rootCategories.forEach(category => {
            // 부모 카테고리 생성
            const categoryWrapper = document.createElement("div");
            categoryWrapper.classList.add("navbar-item", "has-dropdown", "is-hoverable");

            const categoryLink = document.createElement("a");
            categoryLink.classList.add("navbar-link", "has-text-weight-semibold");
            categoryLink.href = `/api/category/${category.name}`;
            categoryLink.textContent = category.name;


            // 드롭다운 메뉴 생성
            const dropdown = document.createElement("div");
            dropdown.classList.add("navbar-dropdown");

            // dropdown 크기를 categoryWrapper와 동일하게 설정
            dropdown.style.width = "100%";

            // 자식 카테고리 추가
            const childCategories = childCategoriesMap[category.id] || [];
            childCategories.forEach(child => {
                const childLink = document.createElement("a");
                childLink.classList.add("navbar-item");
                childLink.href = `/api/category/${child.name}`;
                childLink.textContent = child.name;
                dropdown.appendChild(childLink);
            });

            // 부모에 드롭다운 메뉴 추가
            categoryWrapper.appendChild(categoryLink);
            if (childCategories.length > 0) {
                categoryWrapper.appendChild(dropdown);
            }
            navbarEnd.appendChild(categoryWrapper);
        });

        // Bulma 화살표 제거
        removeNavbarArrow();
    } else {
        console.error("네비게이션 데이터를 가져오는 데 실패했습니다.");
    }


    // My Page 링크 추가
    const myPage = document.createElement("a");
    myPage.classList.add("navbar-item", "has-text-weight-semibold");
    myPage.href = `/mypage`;
    myPage.textContent = "My Page";
    navbarEnd.appendChild(myPage);

    // 장바구니 링크 추가
    const basket = document.createElement("a");
    basket.classList.add("navbar-item", "has-text-weight-semibold");
    basket.href = `/basket`;
    basket.textContent = "장바구니";
    navbarEnd.appendChild(basket);


    // 회원가입 링크 추가
    const join = document.createElement("a");
    join.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
    join.href = `/join`;
    join.textContent = "회원가입";
    navbarEnd.appendChild(join);

    // Login 링크 추가
    const logIn = document.createElement("a");
    logIn.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
    logIn.href = `/logIn`;
    logIn.textContent = "LogIn";
    navbarEnd.appendChild(logIn);

    // LogOut 링크 추가
    const logOut = document.createElement("a");
    logOut.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
    logOut.href = `/logOut`;
    logOut.textContent = "LogOut";
    navbarEnd.appendChild(logOut);

}

// Bulma 화살표 제거 함수
function removeNavbarArrow() {
    const style = document.createElement("style"); // <style> 태그 생성
    /* 화살표 제거 */
    style.textContent = `
        .navbar-link::after {
            display: none !important; /* 가상 요소를 아예 숨김 */
        }
    `;
    document.head.appendChild(style); // <head>에 추가
}
