document.getElementById('companySignupForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const businessNumber = document.getElementById('businessNumber').value;
    const companyName = document.getElementById('name').value;
    const startDate = document.getElementById('startDate').value;

    const data = {
        c_name: document.getElementById('c_name').value,
        company_email: document.getElementById('company_email').value,
        password: document.getElementById('password').value,
        c_logo: document.getElementById('c_logo').files[0],
        c_address: document.getElementById('c_address').value,
        c_phone: document.getElementById('c_phone').value
    };

    fetch('/com/join/second', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(result => {

            if (result.code === 200 && result.data.isValid) {         
                alert('회원가입이 완료되었습니다.');
                window.location.href = '/company/auth/login';
            } else {
                alert('사업자 인증에 실패했습니다. 다시 시도해주세요.');
            }
        })
        .catch(error => {
            console.error('오류:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});
