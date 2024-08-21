document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('userLoginForm').addEventListener('submit', function(e) {
        e.preventDefault(); // 페이지 리로드 방지

        const userEmail = document.getElementById('userEmail').value;
        const userPassword = document.getElementById('userPassword').value;

        fetch('/member/userLogin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userEmail: userEmail,
                userPassword: userPassword
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                return response.json();
            })
            .then(data => {
                console.log('서버 응답:', data);
                if (data.code === 200 && data.data && data.data.accessToken) {
                    // 로컬 스토리지에 accessToken 저장
                    localStorage.setItem('accessToken', data.data.accessToken);

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
                        window.location.href = '/recruitment/view'; // 로그인 성공 후 리다이렉트할 페이지
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
