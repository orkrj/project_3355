const BASE_URL = "/api/category";
navFunction();


// 네비게이션 바를 생성하는 함수
async function navFunction() {
    const response = await fetch(`${BASE_URL}/findAll`);
    if (response.ok) {
        const categories = await response.json();
        const rootCategories = categories.filter(category => !category.parentId); // parentId가 null인 카테고리만

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
            const categoryLink = document.createElement("a");
            categoryLink.classList.add("navbar-item", "has-text-weight-semibold");
            categoryLink.href = `/category/${category.id}`;
            categoryLink.textContent = category.name;
            navbarEnd.appendChild(categoryLink);
        });

        const myPage = document.createElement("a");
        myPage.classList.add("navbar-item", "has-text-weight-semibold");
        myPage.href = `/mypage`;
        myPage.textContent = "My Page";
        navbarEnd.appendChild(myPage);

        const logOut = document.createElement("a");
        logOut.classList.add("navbar-item", "has-text-weight-semibold","has-text-danger");
        logOut.href = `/logOut`;
        logOut.textContent = "LogOut";
        navbarEnd.appendChild(logOut);

    } else {
        console.error("네비게이션 데이터를 가져오는 데 실패했습니다.");
    }
}

