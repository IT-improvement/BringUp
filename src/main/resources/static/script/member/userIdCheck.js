document.addEventListener('DOMContentLoaded', function() {
    const checkEmailBtn = document.getElementById('checkEmailBtn');
    const emailCheckResult = document.getElementById('emailCheckResult');

    checkEmailBtn.addEventListener('click', function() {
        const userEmail = document.getElementById('userEmail').value;

        fetch('/member/checkEmail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ userEmail: userEmail })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('서버 응답이 올바르지 않습니다.');
                }
                return response.json();
            })
            .then(data => {
                if (data.available) {
                    emailCheckResult.textContent = '사용 가능한 이메일입니다.';
                    emailCheckResult.style.color = 'green';
                } else {
                    emailCheckResult.textContent = '이미 사용 중인 이메일입니다.';
                    emailCheckResult.style.color = 'red';
                }
            })
            .catch(error => {
                console.error('에러:', error);
                emailCheckResult.textContent = '이메일 확인 중 오류가 발생했습니다.';
                emailCheckResult.style.color = 'red';
            });
    });
});