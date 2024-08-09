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
            if (result.code === 200 && result.data.isValid) {  // 수정된 부분
                alert('사업자 인증이 완료되었습니다.');
                // 다음 단계로 이동하는 로직을 여기에 추가하세요
            } else {
                alert('사업자 인증에 실패했습니다. 다시 시도해주세요.');
            }
        })
        .catch(error => {
            console.error('오류:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
});
