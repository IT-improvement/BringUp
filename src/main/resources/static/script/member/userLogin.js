document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('userLoginForm');

    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
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
                if (!response.ok) {
                    console.log('Login failed');
                    // 여기에 로그인 실패 처리 로직을 추가하세요
                } else {
                    return response.json();
                }
            })
            .then(data => {
                console.log(data);
                if (data && data.data.accessToken) {
                    localStorage.setItem('accessToken', data.data.accessToken);
                    console.log('Login successful');
                    window.location.href = '/'; // 로그인 성공 후 메인 페이지로 이동
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
        });
    } else {
        console.log('Login form not found on this page');
    }
});