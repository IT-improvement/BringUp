document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('userLoginForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        const userid = document.getElementById('userid').value;
        const password = document.getElementById('password').value;
        
        fetch('/company/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userid: userid,
                password: password
            })
        })
        .then(response => {
            if (!response.ok) {
                console.log('서버 응답:', data);
                throw new Error('서버 응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            console.log('서버 응답:', data);
            if (data.code === 200 && data.data && data.data.accessToken) {
                console.log('로그인 성공11:', data.code);
                // 액세스 토큰을 세션 스토리지에 저장
                sessionStorage.setItem('accessToken', data.data.accessToken);
                window.location.href = '/company';
            } else {
                console.error('로그인 실패:', data);
                alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
            }
        })
        .catch(error => {
            console.error('에러:', error);
            alert('로그인 중 오류가 발생했습니다. 나중에 다시 시도해주세요.');
        });
    });
});