document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('loginForm').addEventListener('submit', function(e) {
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
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                console.log('로그인 성공:', data);
                // 액세스 토큰을 쿠키에 저장
                document.cookie = `accessToken=${data.data.accessToken}; path=/; secure; samesite=strict`;
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