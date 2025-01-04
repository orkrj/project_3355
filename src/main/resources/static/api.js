
async function get(endpoint, params = "") {
  const apiUrl = params ? `${endpoint}/${params}` : endpoint;
  console.log(`%cGET 요청: ${apiUrl} `, "color: #a25cd1;");

  // 토큰이 있으면 Authorization 헤더를 포함, 없으면 포함하지 않음
  const token = sessionStorage.getItem("Authorization");
  const headers = token ? { Authorization: `Bearer ${token}` } : {};

  const res = await fetch(apiUrl, { headers });

  // 응답 코드가 4XX 계열일 때 (400, 403 등)
  if (!res.ok) {
    const errorContent = await res.json();
    const { reason } = errorContent;

    throw new Error(reason);
  }

  const result = await res.json();
  return result;
}

async function post(endpoint, data) {
  const apiUrl = endpoint;
  const jsonData = JSON.stringify(data);
  console.log(`%cPOST 요청: ${apiUrl}`, "color: #296aba;");
  console.log(`%cPOST 요청 데이터: ${jsonData}`, "color: #296aba;");

  // 토큰이 있으면 Authorization 헤더를 포함, 없으면 포함하지 않음
  const token = sessionStorage.getItem("Authorization");
  const isJson = !(data instanceof FormData);
  const headers = {
    ...(isJson && { "Content-Type": "application/json" }),
    ...(token && { Authorization: `Bearer ${token}` }),
  };

  try {
    // FormData를 사용할 때는 Content-Type을 자동으로 설정하므로, 따로 설정하지 않음
    const res = await fetch(apiUrl, {
      method: "POST",
      headers,
      body: isJson ? jsonData : data,  // FormData 면 직접 body로 전달
    });

    // 응답 코드가 4XX 계열일 때 (400, 403 등)
    if (!res.ok) {
      const errorContent = await res.json();
      const { reason } = errorContent;
      throw new Error(`Error: ${reason}`);
    }

    const result = await res.json();
    return result;
  } catch (err) {
    // catch 블록에서 에러 로그를 더 상세히 출력
    console.error("서버에서 오류가 발생했습니다:", err);
    throw new Error("서버에서 오류가 발생했습니다. 다시 시도해 주세요.");
  }
}

async function put(endpoint, data) {
  const apiUrl = endpoint
  const jsonData = JSON.stringify(data);

  const bodyData = JSON.stringify(data);
  console.log(`%cPUT 요청: ${apiUrl}`, "color: #0b84a5;");
  console.log(`%cPUT 요청 데이터: ${jsonData}`, "color: #0b84a5;");

  const isJson = !(data instanceof FormData);
  const token = sessionStorage.getItem("Authorization");
  const headers = {
    ...(isJson && { "Content-Type": "application/json" }),
    ...(token && { Authorization: `Bearer ${token}` }),
  };

  try {
    const res = await fetch(apiUrl, {
      method: "PUT",
      headers,
      body: isJson ? jsonData : data,
    });

    if (!res.ok) {
      const errorContent = await res.json();
      const { reason } = errorContent;
      throw new Error(reason);
    }

    const result = await res.json();
    return result;
  } catch (err) {
    console.error("서버에서 오류가 발생했습니다:", err);
    throw new Error("서버에서 오류가 발생했습니다. 다시 시도해 주세요.");
  }
}


// api 로 PATCH 요청 (/endpoint/params 로, JSON 데이터 형태로 요청함)
async function patch(endpoint, params = "", data) {
  const apiUrl = params ? `${endpoint}/${params}` : endpoint;

  const bodyData = JSON.stringify(data);
  console.log(`%cPATCH 요청: ${apiUrl}`, "color: #059c4b;");
  console.log(`%cPATCH 요청 데이터: ${bodyData}`, "color: #059c4b;");

  const res = await fetch(apiUrl, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${sessionStorage.getItem("token")}`,
    },
    body: bodyData,
  });

  // 응답 코드가 4XX 계열일 때 (400, 403 등)
  if (!res.ok) {
    const errorContent = await res.json();
    const { reason } = errorContent;

    throw new Error(reason);
  }

  const result = await res.json();

  return result;
}

// 아래 함수명에 관해, delete 단어는 자바스크립트의 reserved 단어이기에,
// 여기서는 우선 delete 대신 del로 쓰고 아래 export 시에 delete로 alias 함.
async function del(endpoint, data) {
  const apiUrl = endpoint;  // URL을 직접 endpoint로 설정
  const jsonData = JSON.stringify(data);

  console.log(`%cDELETE 요청: ${apiUrl}`, "color: #059c4b;");
  console.log(`%cDELETE 요청 데이터: ${jsonData}`, "color: #059c4b;");

  const isJson = !(data instanceof FormData);
  const token = sessionStorage.getItem("Authorization");  // Authorization 토큰 가져오기
  const headers = {
    ...(isJson && { "Content-Type": "application/json" }),  // JSON일 경우 Content-Type 설정
    ...(token && { Authorization: `Bearer ${token}` }),  // Authorization 헤더 설정
  };

  try {
    const res = await fetch(apiUrl, {
      method: "DELETE",
      headers,
      body: isJson ? jsonData : undefined,  // FormData가 아닐 경우에만 body에 데이터를 첨부
    });

    if (!res.ok) {
      const errorContent = await res.json();
      const { reason } = errorContent;
      throw new Error(reason);  // 오류 발생시 reason을 포함하여 오류 처리
    }

    const result = await res.json();
    return result;  // 응답 반환

  } catch (err) {
    console.error("서버에서 오류가 발생했습니다:", err);
    throw new Error("서버에서 오류가 발생했습니다. 다시 시도해 주세요.");
  }
}


// 아래처럼 export하면, import * as Api 로 할 시 Api.get, Api.post 등으로 쓸 수 있음.
export { get, post, put, patch, del as delete };
