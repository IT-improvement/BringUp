document.getElementById('companySignupForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const businessNumber = document.getElementById('businessNumber').value;
    const companyName = document.getElementById('name').value;
    const startDate = document.getElementById('startDate').value;

    const data = {
        company_licence: businessNumber,
        company_opendate: startDate,
        master_name: companyName
    };

    fetch('/company/join/first', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(result => {
        if (result.success) {
            alert('사업자 인증이 완료되었습니다.');
            // 세션 스토리지에 데이터 저장
            sessionStorage.setItem('company_licence', businessNumber);
            sessionStorage.setItem('company_opendate', startDate);
            sessionStorage.setItem('master_name', companyName);
            
            // 다음 페이지로 이동
            window.location.href = '/company/auth/signupsecond';
        } else {
            alert('사업자 인증에 실패했습니다. 다시 시도해주세요.');
        }
    })
    .catch(error => {
        console.error('오류:', error);
        alert('오류가 발생했습니다. 다시 시도해주세요.');
    });
});
