document.addEventListener('DOMContentLoaded', function() {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
        console.error('액세스 토큰을 찾을 수 없습니다.');
        return;
    }

    fetch('/company/companyName', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.data && data.data.c_name) {
            const companyName = data.data.c_name;
            document.getElementById('companyNameSpan').textContent = companyName;
            document.getElementById('companyNameLink').textContent = companyName;
            console.log('회사 이름:', companyName);
        }
    })
    .catch(error => {
        console.error('회사 이름을 가져오는 데 실패했습니다:', error);
    });
});