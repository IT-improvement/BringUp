// 전역 스코프에 logout 함수 정의
document.addEventListener('DOMContentLoaded', function() {
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            localStorage.removeItem('accessToken');
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

    const accessToken = localStorage.getItem('accessToken');
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

    // 테마 모드 전환 기능
    const lightModeBtn = document.getElementById('lightMode');
    const darkModeBtn = document.getElementById('darkMode');

    function setTheme(themeName) {
        localStorage.setItem('theme', themeName);
        document.documentElement.setAttribute('data-bs-theme', themeName);
    }

    function toggleTheme() {
        if (localStorage.getItem('theme') === 'dark') {
            setTheme('light');
            lightModeBtn.checked = true;
        } else {
            setTheme('dark');
            darkModeBtn.checked = true;
        }
    }

    // 초기 테마 설정
    (function () {
        if (localStorage.getItem('theme') === 'dark') {
            setTheme('dark');
            darkModeBtn.checked = true;
        } else {
            setTheme('light');
            lightModeBtn.checked = true;
        }
    })();

    // 이벤트 리스너 추가
    lightModeBtn.addEventListener('change', () => setTheme('light'));
    darkModeBtn.addEventListener('change', () => setTheme('dark'));
});

function getCurrentTheme() {
    return document.documentElement.getAttribute('data-bs-theme');
}

function setNotificationButtonStyle(element) {
    const currentTheme = getCurrentTheme();
    if (currentTheme === 'dark') {
        element.style.backgroundColor = '#192233';
    } else {
        element.style.backgroundColor = '#E8F0FC';
    }
}

function resetNotificationButtonStyle(element) {
    element.style.backgroundColor = '';
}

// 테마 변경 시 이벤트 리스너 추가
document.addEventListener('themeChanged', function(e) {
    const notificationButton = document.querySelector('.btn-round');
    if (notificationButton && notificationButton.matches(':hover')) {
        setNotificationButtonStyle(notificationButton);
    }
});

// 반응형 네비게이션 처리
document.addEventListener('DOMContentLoaded', function() {
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');

    if (navbarToggler && navbarCollapse) {
        navbarToggler.addEventListener('click', function() {
            navbarCollapse.classList.toggle('show');
        });
    }
});