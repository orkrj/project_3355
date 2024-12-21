const BASE_URL = "/api/category";

// (parentId === null)인 루트 카테고리만 보여주기
async function fetchRootCategories() {
    const response = await fetch(`${BASE_URL}/detail`);
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
            const childCount = categories.filter(cat => cat.parentId === category.id).length;

            const categoryDiv = document.createElement("div");
            categoryDiv.classList.add("box", "category-card");
            categoryDiv.innerHTML = `
                <div class="parent-category" style="display: flex; align-items: center; justify-content: space-between;">
                    <div>
                        <strong>${category.name}</strong> 
                         <span class="has-text-link" onclick="toggleChildren(${category.id})">
                            (자식 카테고리 보기: ${childCount}개)
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

// Toggle children categories and allow edit/delete for them
async function toggleChildren(parentId) {
    const childrenListDiv = document.getElementById(`childrenList-${parentId}`);
    if (childrenListDiv.style.display === "none") {
        const response = await fetch(`${BASE_URL}/detail`);
        if (response.ok) {
            const categories = await response.json();
            const children = categories.filter(category => category.parentId === parentId);

            if (children.length > 0) {
                childrenListDiv.innerHTML = ""; // 이전 내용 초기화
                children.forEach(child => {
                    const childDiv = document.createElement("div");

                    childDiv.style.backgroundColor = "#dcdada"; // 회색 배경
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
                childrenListDiv.innerHTML = `<p>자식 카테고리가 없습니다.</p>`;
            }

            childrenListDiv.style.display = "block";
        } else {
            alert("자식 카테고리를 가져오지 못했습니다.");
        }
    } else {
        childrenListDiv.style.display = "none";
    }
}

// Edit category
function editCategory(id, currentName) {
    const newName = prompt("새로운 카테고리 이름을 입력하세요:", currentName);
    if (newName && newName !== currentName) {
        updateCategory(id, newName);
    }
}

// Update category
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

// Delete category
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

// Load root categories on page load
document.addEventListener("DOMContentLoaded", fetchRootCategories);
