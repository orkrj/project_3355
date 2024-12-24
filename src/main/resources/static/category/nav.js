const BASE_URL = "/api/category";
navFunction();


// 네비게이션 바를 생성하는 함수
async function navFunction() {
    const response = await fetch(`${BASE_URL}/findAll`);
    if (response.ok) {
        const categories = await response.json();
        const rootCategories = categories.filter(category => !category.parentId); // parentId가 null인 카테고리만
        const childCategoriesMap = categories.reduce((acc, category) => {
            if (category.parentId) {
                acc[category.parentId] = acc[category.parentId] || [];
                acc[category.parentId].push(category);
            }
            return acc;
        }, {}); // 자식 카테고리를 부모 ID로 묶음

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

        // 동적으로 루트 카테고리 추가
        const navbarEnd = document.getElementById("navbar-end");
        rootCategories.forEach(category => {
            // 부모 카테고리 생성
            const categoryWrapper = document.createElement("div");
            categoryWrapper.classList.add("navbar-item", "has-dropdown", "is-hoverable");

            const categoryLink = document.createElement("a");
            categoryLink.classList.add("navbar-link", "has-text-weight-semibold");
            categoryLink.href = `/category/${category.id}`;
            categoryLink.textContent = category.name;

            // 드롭다운 메뉴 생성
            const dropdown = document.createElement("div");
            dropdown.classList.add("navbar-dropdown");

            // 자식 카테고리 추가
            const childCategories = childCategoriesMap[category.id] || [];
            childCategories.forEach(child => {
                const childLink = document.createElement("a");
                childLink.classList.add("navbar-item");
                childLink.href = `/category/${child.id}`;
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

        // My Page 링크 추가
        const myPage = document.createElement("a");
        myPage.classList.add("navbar-item", "has-text-weight-semibold");
        myPage.href = `/mypage`;
        myPage.textContent = "My Page";
        navbarEnd.appendChild(myPage);

        // LogOut 링크 추가
        const logOut = document.createElement("a");
        logOut.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
        logOut.href = `/logOut`;
        logOut.textContent = "LogOut";
        navbarEnd.appendChild(logOut);


        // Bulma 화살표 제거
        removeNavbarArrow();

        // 호버 효과 제거
        // removeHoverEffect();

    } else {
        console.error("네비게이션 데이터를 가져오는 데 실패했습니다.");
    }
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

// 호버 효과 제거 함수
// function removeHoverEffect() {
//     const style = document.createElement("style"); // <style> 태그 생성
//     /* <a> 태그 호버 효과 제거 */
//     style.textContent = `
//         .navbar-item:hover, .navbar-link:hover {
//             background-color: transparent !important; /* 배경색 제거 */
//             color: inherit !important; /* 텍스트 색상 유지 */
//         }
//     `;
//     document.head.appendChild(style); // <head>에 추가
// }