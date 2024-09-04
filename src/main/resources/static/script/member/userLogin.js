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

                    // 다음 페이지로 이동 (토큰을 포함하여 이동)
                    const accessToken = localStorage.getItem('accessToken');

                    fetch('/recruitment/auth', {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ${accessToken}`
                        }
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error('페이지 로드 실패');
                            }
                            return response.text(); // 페이지의 HTML을 가져옴
                        })
                        .then(html => {
                            document.open();
                            document.write(html);
                            document.close();
                        })
                        .catch(error => {
                            console.error('에러:', error);
                            alert('페이지 로드 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
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