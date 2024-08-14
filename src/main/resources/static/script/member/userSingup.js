document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('userJoin').addEventListener('submit', function(e) {
        e.preventDefault(); // 폼의 기본 제출 동작을 막습니다.

        // 폼 필드의 값을 가져옵니다.
        const userEmail = document.getElementById('userEmail').value;
        const userPassword = document.getElementById('userPassword').value;
        const userName = document.getElementById('userName').value;
        const userAddress = document.getElementById('userAddress').value;
        const userPhonenumber = document.getElementById('userPhonenumber').value;
        const userBirthday = document.getElementById('userBirthday').value;
        const freelancer = document.getElementById('freelancer').checked;
        const status = document.getElementById('status').value;

        // 서버에 보낼 데이터 객체를 만듭니다.
        const formData = {
            userEmail: userEmail,
            userPassword: userPassword,
            userName: userName,
            userAddress: userAddress,
            userPhonenumber: userPhonenumber,
            userBirthday: userBirthday,
            freelancer: freelancer,
            status: status
        };

        // Fetch API를 사용해 데이터를 서버에 전송합니다.
        fetch('/member/joinProc', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                return response.text(); // 서버 응답을 텍스트로 처리
            })
            .then(data => {
                console.log('서버 응답:', data);
                if (data.includes("회원가입이 성공적으로 완료되었습니다.")) {
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
                    <p>회원가입에 성공하셨습니다!</p>
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

                        // 로그인 페이지로 이동
                        window.location.href = '/member/userLoginForm';
                    });
                } else {
                    console.error('회원가입 실패:', data);
                    alert('회원가입에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('에러:', error);
                alert('회원가입 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
            });
    });
});
