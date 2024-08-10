// 전역 스코프에 logout 함수 정의
document.addEventListener('DOMContentLoaded', function() {
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            sessionStorage.removeItem('accessToken');
            window.location.href = '/';
        });
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 동작 방지
            window.logout();
        });
    }

    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
        console.error('액세스 토큰을 찾을 수 없습니다.');
        return;
    }

    function updateCompanyName(companyName) {
        const companyNameSpan = document.getElementById('companyNameSpan');
        const companyNameLink = document.getElementById('companyNameLink');
        
        if (companyNameSpan) {
            companyNameSpan.textContent = companyName;
            companyNameSpan.style.display = 'inline'; // 데이터 로드 후 표시
        }
        if (companyNameLink) companyNameLink.textContent = companyName;
    }

    fetch('/com/companyName', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + accessToken,
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.data) {
            updateCompanyName(data.data);
        } else {
            console.error('회사 이름 데이터가 올바르지 않습니다:', data);
            updateCompanyName('알 수 없음'); // 에러 시 기본값 설정
        }
    })
    .catch(error => {
        console.error('회사 이름을 가져오는 데 실패했습니다:', error);
        updateCompanyName('알 수 없음'); // 에러 시 기본값 설정
    });
});