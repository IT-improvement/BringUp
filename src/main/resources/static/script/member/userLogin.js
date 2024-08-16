document.addEventListener('DOMContentLoaded', function() { //이 코드는 HTML 문서가 완전히 로드된 후에 실행되는 코드를 지정 즉, 페이지의 모든 요소가 로드된 후에 스크립트가 실행됨.
    document.getElementById('loginForm').addEventListener('submit', function(e) {//이 코드는 로그인 폼(loginForm)이 제출될 때 실행되는 이벤트 리스너를 설정 사용자가 폼의 제출 버튼을 클릭하면 이 함수가 호출
        e.preventDefault(); //페이지 리로드 방지, 자바스크립트로만 처리되도록 하는 함수

        const userEmail = document.getElementById('userEmail').value;  // ID 입력 필드(userid)의 값을 가져와 userid 변수에 저장
        const userPassword = document.getElementById('userPassword').value; // 마찬가지로 비밀번호 저장

        fetch('/member/userLogin', { //fetch 함수는 서버와 비동기적으로 통신
            method: 'POST',
            headers: { // 여기서 Content-Type을 application/json으로 설정하여 서버가 전송된 데이터가 JSON 형식임을 알림
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ //이 부분은 요청의 본문에 포함될 데이터를 JSON 형식으로 변환 , userid와 password 값을 JSON으로 변환하여 서버에 전송
                userEmail: userEmail,
                userPassword: userPassword
            })
        })
            .then(response => { //서버의 응답을 처리, fetch는 프로미스를 반환하며, then을 사용하여 응답이 도착했을 때의 동작을 지정
                if (!response.ok) {
                    console.log('서버 응답:', data);
                    throw new Error('서버 응답이 올바르지 않습니다.'); //throw new는 JavaScript에서 오류를 수동으로 발생시키는 구문
                }
                return response.json(); //응답 데이터를 JSON 형식으로 변환하여 반환
            })
            .then(data => {
                console.log('서버 응답:', data);
                if (data.code === 200 && data.data && data.data.accessToken) { //응답 데이터의 상태 코드(data.code)가 200이고, accessToken이 있는 경우

                    sessionStorage.setItem('accessToken', data.data.accessToken); // 위에 조건에 해당할경우 세션 스토리지에 accessToken을 저장
                    const modal = document.createElement('div');
                    modal.style.cssText = `
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(0,0,0,0.5);
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    z-index: 1000;
                `;
                    const modalContent = document.createElement('div');
                    modalContent.style.cssText = `
                    background-color: white;
                    padding: 20px;
                    border-radius: 5px;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                `;
                    modalContent.innerHTML = `
                    <h3>알림</h3>
                    <p>로그인에 성공하셨습니다!</p>
                    <button id="modalConfirmButton">확인</button>
                `;
                    modal.appendChild(modalContent);
                    document.body.appendChild(modal);

                    // URL 변경 방지
                    const preventUrlChange = function(e) {
                        e.preventDefault();
                        return false;
                    };

                    // 모달이 열려있는 동안 URL 변경 방지
                    window.addEventListener('popstate', preventUrlChange);
                    window.addEventListener('hashchange', preventUrlChange);

                    // 모달의 확인 버튼에 이벤트 리스너 추가
                    document.getElementById('modalConfirmButton').addEventListener('click', function() {
                        // URL 변경 방지 이벤트 리스너 제거
                        window.removeEventListener('popstate', preventUrlChange);
                        window.removeEventListener('hashchange', preventUrlChange);

                        // 모달 닫기
                        modal.remove();

                        // 다음 페이지로 이동
                        window.location.href = '/member/userLoginForm'; // 로그인 성공 후 리다이렉트할 페이지
                    });
                } else {
                    console.error('로그인 실패:', data);
                    alert('로그인에 실패했습니다. 이메일과 비밀번호를 확인해주세요.');
                }
            })
            .catch(error => {
                console.error('에러:', error);
                alert('로그인 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
            });
    });
});
