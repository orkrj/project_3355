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
                        <button class="button is-link is-small" onclick="createSubCategory(${category.id}, '${category.name}')">추가</button>
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

// 자식 카테고리 보여주기
async function toggleChildren(parentId) {
    const childrenListDiv = document.getElementById(`childrenList-${parentId}`);
    if (childrenListDiv.style.display === "none" || childrenListDiv.style.display === "") {
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

function toggleInlineForm() {
    const formContainer = document.getElementById("inlineFormContainer");
    const inputField = document.getElementById("newCategoryName");
    const errorText = document.getElementById("errorText");


    if (formContainer.style.display === "none" || formContainer.style.display === "") {
        formContainer.style.display = "flex"; // 폼 표시
        inputField.focus(); // 입력 필드 포커스
    } else {
        formContainer.style.display = "none"; // 폼 숨기기
        inputField.value = ""; // 입력 필드 초기화

        // 오류 메시지 숨기기
        errorText.style.display = "none";
        errorText.textContent = ""; // 텍스트 초기화
    }
}

// 루트 카테고리 생성
async function createRootCategory() {
    const rootCategoryName = document.getElementById("newCategoryName").value.trim();
    const errorTextElement = document.getElementById("errorText");

    // 기존 오류 메시지 초기화
    errorTextElement.style.display = "none";
    errorTextElement.textContent = "";

    if (!rootCategoryName) {
        // 이름이 비어 있는 경우
        errorTextElement.textContent = "카테고리 이름은 공백일 수 없습니다.";
        errorTextElement.style.display = "block";
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${sessionStorage.getItem("Authorization")}`,
            },
            body: JSON.stringify({ parentId: null, name: rootCategoryName }),
        });

        if (response.ok) {
            // 성공 시 폼 닫기 및 목록 갱신
            toggleInlineForm();
            await fetchRootCategories();
            navFunction();
        }
        else {
            // 서버에서 반환된 오류 메시지 표시
            const error = await response.json();
            errorTextElement.textContent = error.errorMessage || "카테고리 추가에 실패했습니다.";
            errorTextElement.style.display = "block";
        }
    } catch (error) {
        // 네트워크 오류 또는 기타 예외 처리
        errorTextElement.textContent = `오류 발생: ${error.message}`;
        errorTextElement.style.display = "block";
    }
}


//하위 카테고리 생성
function createSubCategory(parentId, parentName) {
    const childrenListDiv = document.getElementById(`childrenList-${parentId}`);
    const formId = `addChildForm-${parentId}`;

    // 자식 카테고리 데이터를 가져와 표시
    fetch(`${BASE_URL}/findAll`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("자식 카테고리를 가져오지 못했습니다.");
            }
        })
        .then(categories => {
            const children = categories.filter(category => category.parentId === parentId);

            childrenListDiv.innerHTML = ""; // 이전 내용 초기화

            // 자식 카테고리 표시
            children.forEach(child => {
                const childDiv = document.createElement("div");

                childDiv.style.backgroundColor = "#dcdada";
                childDiv.classList.add("box", "child-category");
                childDiv.innerHTML = `
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                        <div>
                            <strong>${child.name}</strong>
                        </div>
                        <div class="buttons">
                            <button class="button is-primary is-small" onclick="editCategory(${child.id}, '${child.name}')">수정</button>
                            <button class="button is-danger is-small" onclick="deleteCategory(${child.id})">삭제</button>
                        </div>
                    </div>
                `;
                childrenListDiv.appendChild(childDiv);
            });

            // 폼이 이미 있으면 표시하지 않음
            if (!document.getElementById(formId)) {
                const formDiv = document.createElement("div");
                formDiv.id = formId;
                formDiv.style.marginBottom = "8px";
                formDiv.innerHTML = `
                    <div style="display: flex; align-items: center;">
                        <input id="childCategoryName-${parentId}" class="input is-small" type="text" placeholder="${parentName}의 하위 카테고리 이름">
                        <button class="button is-success is-small" style="margin-left: 8px;" onclick="submitSubCategory(${parentId})">추가</button>
                        <button class="button is-light is-small" style="margin-left: 8px;" onclick="removeChildForm('${formId}')">취소</button>
                    </div>
                    <p id="errorText-${parentId}" style="color: red; font-size: 0.8rem; margin-top: 4px; display: none;"></p>
                `;
                childrenListDiv.prepend(formDiv);
            }

            childrenListDiv.style.display = "block"; // 폼과 자식 카테고리 목록을 표시
        })
        .catch(error => {
            console.error(error.message);
            alert("자식 카테고리를 가져오지 못했습니다.");
        });
}



// 폼 제거
function removeChildForm(formId) {
    const formDiv = document.getElementById(formId);
    if (formDiv) {
        formDiv.remove();
    }
}

// 하위 카테고리 추가 제출
async function submitSubCategory(parentId) {
    const childCategoryName = document.getElementById(`childCategoryName-${parentId}`).value.trim();
    const errorTextElement = document.getElementById(`errorText-${parentId}`);

    // 오류 메시지 초기화
    errorTextElement.style.display = "none";
    errorTextElement.textContent = "";

    if (!childCategoryName) {
        errorTextElement.textContent = "하위 카테고리 이름은 공백일 수 없습니다.";
        errorTextElement.style.display = "block";
        return;
    }


    try {
        const response = await fetch(`${BASE_URL}/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ parentId, name: childCategoryName }),
        });

        if (response.ok) {
            // 성공 시 목록 갱신
            await fetchRootCategories();
            navFunction();
        } else {
            // 서버에서 반환된 오류 메시지 표시
            const error = await response.json();
            errorTextElement.textContent = error.errorMessage || "하위 카테고리 추가에 실패했습니다.";
            errorTextElement.style.display = "block";
        }
    } catch (error) {
        // 네트워크 오류 또는 기타 예외 처리
        errorTextElement.textContent = `오류 발생: ${error.message}`;
        errorTextElement.style.display = "block";
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
            Authorization: `Bearer ${sessionStorage.getItem("Authorization")}`,
        },
        body: JSON.stringify({ id, name }),
    });

    if (response.ok) {
        alert("카테고리가 수정되었습니다.");
        fetchRootCategories();
        navFunction();

    } else {

        // 서버에서 반환된 오류 메시지 표시
        const error = await response.json();
        alert(`${error.errorMessage}`);
    }
}

async function deleteCategory(id) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        const response = await fetch(`${BASE_URL}/delete/${id}`, {
            method: "DELETE",
            Authorization: `Bearer ${sessionStorage.getItem("Authorization")}`,
        });

        if (response.ok) {
            alert("카테고리가 삭제되었습니다.");
            fetchRootCategories();
            navFunction();
        } else {
            alert("카테고리 삭제에 실패했습니다.");
        }
    }
}