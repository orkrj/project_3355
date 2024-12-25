const BASE_URL = "/api/category";

fetchRootCategories();

async function fetchRootCategories() {
    const response = await fetch(`${BASE_URL}/findAll`);
    if (response.ok) {
        const categories = await response.json();
        const rootCategories = categories.filter(category => !category.parentId); // parentId가 null인 카테고리만


        // 루트 카테고리 개수 보이기
        const categoryCountElement = document.getElementById("categoryCount");
        categoryCountElement.textContent = `카테고리 목록 (${rootCategories.length})`;

        // 루트 카테고리출력
        const categoriesContainer = document.getElementById("categoriesContainer");
        categoriesContainer.innerHTML = "";

        rootCategories.forEach(category => {

            //자식 카테고리 개수
            const childCount = categories.filter(categ => categ.parentId === category.id).length;

            const categoryDiv = document.createElement("div");
            categoryDiv.classList.add("box", "category-card");
            categoryDiv.innerHTML = `
                <div class="parent-category" style="display: flex; align-items: center; justify-content: space-between;">
                    <div>
                        <strong>${category.name}</strong>
                         <span class="has-text-link" onclick="toggleChildren(${category.id})">
                            (하위 카테고리 보기: ${childCount}개)
                        </span>
                    </div>
                    <div class="buttons">
                        <button class="button is-primary is-small" onclick="editCategory(${category.id}, '${category.name}')">수정</button>
                        <button class="button is-danger is-small" onclick="deleteCategory(${category.id})">삭제</button>
                    </div>
                </div>
                <div id="childrenList-${category.id}" class="mt-3" style="display: none;"></div>
            `;
            categoriesContainer.appendChild(categoryDiv);
        });
    } else {
        alert("카테고리를 가져오지 못했습니다.");
    }


}

// 자식카테고리 보여주기
async function toggleChildren(parentId) {
    const childrenListDiv = document.getElementById(`childrenList-${parentId}`);
    if (childrenListDiv.style.display === "none") {
        const response = await fetch(`${BASE_URL}/findAll`);
        if (response.ok) {
            const categories = await response.json();
            const children = categories.filter(category => category.parentId === parentId);

            if (children.length > 0) {
                childrenListDiv.innerHTML = ""; // 이전 내용 초기화
                children.forEach(child => {
                    const childDiv = document.createElement("div");

                    childDiv.style.backgroundColor = "#dcdada";
                    childDiv.classList.add("box", "child-category");
                    childDiv.innerHTML = `
                        <div style="display: flex; align-items: center; justify-content: space-between;">
                            <div>
                                <strong>${child.name} </strong>
                            </div>
                            <div class="buttons">
                                <button class="button is-primary is-small" onclick="editCategory(${child.id}, '${child.name}')">수정</button>
                                <button class="button is-danger is-small" onclick="deleteCategory(${child.id})">삭제</button>
                            </div>
                        </div>
                    `;
                    childrenListDiv.appendChild(childDiv);
                });
            } else {
                childrenListDiv.innerHTML = `<p>하위 카테고리가 없습니다.</p>`;
            }

            childrenListDiv.style.display = "block";
        } else {
            alert("하위 카테고리를 가져오지 못했습니다.");
        }
    } else {
        childrenListDiv.style.display = "none";
    }
}


function editCategory(id, currentName) {
    let newName = null;

    // 반복해서 사용자 입력을 확인
    while (true) {
        newName = prompt("새로운 카테고리 이름을 입력하세요:", currentName);

        // 취소 버튼을 누른 경우
        if (newName === null) {
            alert("카테고리 이름 변경이 취소되었습니다.");
            return; // 함수 종료
        }

        // 빈 이름인 경우 (공백만 입력해도 처리)
        if (!newName.trim()) {
            alert("카테고리 이름은 공백일 수 없습니다.");
            continue; // 다시 입력창으로 돌아감
        }

        // 같은 이름인 경우
        if (newName === currentName) {
            alert("새로운 이름은 현재 이름과 다르게 입력해야 합니다.");
            continue; // 다시 입력창으로 돌아감
        }

        // 유효한 이름이면 루프 종료
        break;
    }

    // 유효한 입력인 경우에만 업데이트 함수 호출
    updateCategory(id, newName);
}

async function updateCategory(id, name) {
    const response = await fetch(`${BASE_URL}/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({ id, name }),
    });

    if (response.ok) {
        alert("카테고리가 수정되었습니다.");
        fetchRootCategories();
    } else {
        alert("카테고리 수정에 실패했습니다.");
    }
}

async function deleteCategory(id) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        const response = await fetch(`${BASE_URL}/delete/${id}`, {
            method: "DELETE",
        });

        if (response.ok) {
            alert("카테고리가 삭제되었습니다.");
            fetchRootCategories();
        } else {
            alert("카테고리 삭제에 실패했습니다.");
        }
    }
}


// 전역으로 등록
// window.toggleChildren = toggleChildren;
// window.editCategory = editCategory;
// window.deleteCategory = deleteCategory;


// 전역으로 등록하지 않고 해결할 수 있을까?

// (parentId === null)인 루트 카테고리만 보여주기
// async function fetchRootCategories() {
//     const response = await fetch(`${BASE_URL}/findAll`);
//     if (response.ok) {
//         const categories = await response.json();
//         const rootCategories = categories.filter(category => !category.parentId); // parentId가 null인 카테고리만
//
//         // 루트 카테고리 개수 보이기
//         const categoryCountElement = document.getElementById("categoryCount");
//         categoryCountElement.textContent = `카테고리 목록 (${rootCategories.length})`;
//
//         // 루트 카테고리 출력
//         const categoriesContainer = document.getElementById("categoriesContainer");
//         categoriesContainer.innerHTML = "";
//
//         rootCategories.forEach(category => {
//             // 자식 카테고리 개수
//             const childCategories = categories.filter(categ => categ.parentId === category.id);
//
//             const categoryDiv = document.createElement("div");
//             categoryDiv.classList.add("box", "category-card");
//             categoryDiv.innerHTML = `
//                 <div class="parent-category" style="display: flex; align-items: center; justify-content: space-between;">
//                     <div>
//                         <strong>${category.name}</strong>
//                         <span class="has-text-link toggle-children" data-parent-id="${category.id}">
//                             (하위 카테고리 보기: ${childCategories.length}개)
//                         </span>
//                     </div>
//                     <div class="buttons">
//                         <button class="button is-primary is-small" onclick="editCategory(${category.id}, '${category.name}')">수정</button>
//                         <button class="button is-danger is-small" onclick="deleteCategory(${category.id})">삭제</button>
//                     </div>
//                 </div>
//                 <div id="childrenList-${category.id}" class="mt-3" style="display: none;"></div>
//             `;
//
//             categoriesContainer.appendChild(categoryDiv);
//         });
//
//         // 이벤트 위임 방식으로 클릭 이벤트 추가
//         categoriesContainer.addEventListener("click", event => {
//             if (event.target.classList.contains("toggle-children")) {
//                 const parentId = event.target.dataset.parentId;
//                 const childCategories = categories.filter(category => category.parentId === parseInt(parentId, 10));
//                 toggleChildren(parentId, childCategories);
//             }
//         });
//     } else {
//         alert("카테고리를 가져오지 못했습니다.");
//     }
// }
//
// async function toggleChildren(parentId, childCategories) {
//     const childrenListDiv = document.getElementById(`childrenList-${parentId}`);
//     if (childrenListDiv) {
//         if (childrenListDiv.style.display === "none" || childrenListDiv.style.display === "") {
//             if (childCategories.length > 0) {
//                 childrenListDiv.innerHTML = ""; // 초기화
//                 childCategories.forEach(child => {
//                     const childDiv = document.createElement("div");
//                     childDiv.style.backgroundColor = "#dcdada";
//                     childDiv.classList.add("box", "child-category");
//                     childDiv.innerHTML = `
//                         <div style="display: flex; align-items: center; justify-content: space-between;">
//                             <div><strong>${child.name}</strong></div>
//                             <div class="buttons">
//                                 <button class="button is-primary is-small" onclick="editCategory(${child.id}, '${child.name}')">수정</button>
//                                 <button class="button is-danger is-small" onclick="deleteCategory(${child.id})">삭제</button>
//                             </div>
//                         </div>
//                     `;
//                     childrenListDiv.appendChild(childDiv);
//                 });
//                 childrenListDiv.style.display = "block";
//             } else {
//                 childrenListDiv.innerHTML = `<p>하위 카테고리가 없습니다.</p>`;
//                 childrenListDiv.style.display = "block";
//             }
//         } else {
//             childrenListDiv.style.display = "none";
//         }
//     }
// }