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
                        <a class="navbar-item" href="/home/home.html">
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
            //categoryLink.href = `/api/category/${category.name}`;
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
                let currentPage = 0;
                childLink.href = `/product-list/product-list.html?categoryName=${child.name}`;
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

    const token = sessionStorage.getItem("Authorization");

    if (token) {
        // 관리자 여부 확인
        const res = await fetch("/api/user/admin-check", {
            method : "POST",
            headers: {
                "Content-Type" : "application/json",
                Authorization: `Bearer ${token}`,
            },
        });

        const isAdmin = await checkAdmin();

        if (isAdmin) {
            // 관리자일 경우
            const adminPage = document.createElement("a");
            adminPage.classList.add("navbar-item", "has-text-weight-semibold");
            adminPage.href = `/admin/admin.html`;
            adminPage.textContent = "관리자페이지";
            navbarEnd.appendChild(adminPage);
        } else {
            // 일반 사용자일 경우

            // 장바구니 링크 추가
            const basket = document.createElement("a");
            basket.classList.add("navbar-item", "has-text-weight-semibold");
            basket.href = `/cart/cart.html`;
            basket.textContent = "장바구니";
            navbarEnd.appendChild(basket);

            //마이페이지
            const myPage = document.createElement("a");
            myPage.classList.add("navbar-item", "has-text-weight-semibold");
            myPage.href = `/account/account.html`;
            myPage.textContent = "마이페이지";
            navbarEnd.appendChild(myPage);
        }


        // 공통 로그아웃 링크
        const logOut = document.createElement("a");
        logOut.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
        logOut.href = "#"; // 기본 링크 제거
        logOut.textContent = "로그아웃";
        logOut.addEventListener("click", (e) => {
            e.preventDefault(); // 기본 동작(페이지 이동) 막기
            logout(); // 로그아웃 함수 호출
        });
        navbarEnd.appendChild(logOut);

    } else {
        // 비로그인 상태일 때
        const join = document.createElement("a");
        join.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
        join.href = `/register/register.html`;
        join.textContent = "회원가입";
        navbarEnd.appendChild(join);

        const logIn = document.createElement("a");
        logIn.classList.add("navbar-item", "has-text-weight-semibold", "has-text-danger");
        logIn.href = `/login/login.html`;
        logIn.textContent = "로그인";
        navbarEnd.appendChild(logIn);
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

// 관리자 여부 확인 함수
async function checkAdmin() {
    const token = sessionStorage.getItem("Authorization");

    if (!token) {
        return false;
    }

    const response = await fetch("/api/user/admin-check", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
    });

    if (response.ok) {
        const { result } = await response.json();
        return result === "success"; // 관리자 여부 반환
    }

    return false;
}


async function logout() {
    try {
        const response = await fetch("/logout", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${sessionStorage.getItem("Authorization")}`, // JWT 토큰 추가
            },
        });

        if (response.ok) {
            //alert("로그아웃되었습니다.");
            sessionStorage.removeItem("Authorization"); // JWT 토큰 삭제
            window.location.href = "/home/home.html"; // 리다이렉트
        } else {
            alert("로그아웃에 실패했습니다.");
        }
    } catch (error) {
        console.error("로그아웃 중 오류 발생:", error);
    }
}
