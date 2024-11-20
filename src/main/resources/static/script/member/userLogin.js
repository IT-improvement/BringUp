document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('userLoginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', function (event) {
            event.preventDefault();

            const userEmail = document.getElementById('userEmail').value;
            const userPassword = document.getElementById('userPassword').value;

            fetch('/member/userLogin', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ userEmail, userPassword })
            })
                .then(response => {
                    // JSON 데이터와 상태 코드를 함께 반환
                    return response.json().then(data => ({
                        status: response.status,
                        body: data
                    }));
                })
                .then(({ status, body }) => {
                    if (status === 200) {
                        // 로그인 성공 처리
                        localStorage.setItem('accessToken', body.data.accessToken);
                        console.log('Login successful');
                        window.location.href = '/'; // 성공 시 메인 페이지로 이동
                    } else if (status === 401) {
                        // 이메일 또는 비밀번호 오류
                        if (body.code === 40101) {
                            alert("존재하지 않는 이메일입니다."); // 이메일 오류 메시지
                        } else if (body.code === 40102) {
                            alert("비밀번호가 일치하지 않습니다."); // 비밀번호 오류 메시지
                        } else {
                            alert("인증 오류가 발생했습니다.");
                        }
                    } else {
                        // 기타 오류
                        alert(body.message || '알 수 없는 오류가 발생했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('네트워크 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                });
        });
    } else {
        console.log('Login form not found on this page');
    }
});
