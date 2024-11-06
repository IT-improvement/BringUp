const profileDropdownElement = document.getElementById('profileDropdown');
const loginElement = document.getElementById('login');
const memberNameSpan = document.getElementById('memberNameSpan');
const memberNameLink = document.getElementById('memberNameLink');

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
    // 로그인 상태 UI 업데이트
    function updateUIForLoggedInUser() {
        loginElement.classList.add('d-none');
        loginElement.classList.remove('d-flex');
        profileDropdownElement.classList.remove('d-none');
        profileDropdownElement.classList.add('d-flex');
    }

    // 로그아웃 상태 UI 업데이트
    function updateUIForLoggedOutUser() {
        loginElement.classList.remove('d-none');
        loginElement.classList.add('d-flex');
        profileDropdownElement.classList.add('d-none');
        profileDropdownElement.classList.remove('d-flex');
        memberNameSpan.style.display = 'none';
    }

    // 페이지 로드 시 실행되는 함수
    function checkAccessToken() {
        const accessToken = localStorage.getItem('accessToken');
        console.log('Checking accessToken:', accessToken);
        if (accessToken) {
            // accessToken이 있는 경우 (로그인 상태)
            updateUIForLoggedInUser();
            fetchUserName(accessToken);
        } else {
            // accessToken이 없는 경우 (로그아웃 상태)
            updateUIForLoggedOutUser();
        }
    }

    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 동작 방지
            window.logout();
        });
    }
    const accessToken = localStorage.getItem('accessToken');
    function fetchUserName(accessToken) {
        fetch('/member/name', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ` + accessToken, // 토큰을 헤더에 포함
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            const userName = data.data;
            memberNameSpan.textContent = userName;
            memberNameSpan.style.display = 'inline';
        })
        .catch(error => console.error('Error fetching user name:', error));
    }
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

    // 테마 모드 전환 기능
    const lightModeBtn = document.getElementById('lightMode');
    const darkModeBtn = document.getElementById('darkMode');

    function setTheme(themeName) {
        localStorage.setItem('theme', themeName);
        document.documentElement.setAttribute('data-bs-theme', themeName);
        setDynamicTextColor(); // 테마 변경 시 글자색 업데이트
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

    // 테마에 따른 글자색 설정 함수
    function setDynamicTextColor() {
        const theme = localStorage.getItem('theme');
        const dynamicTextElements = document.querySelectorAll('.dynamic-text-color');
        
        dynamicTextElements.forEach(element => {
            if (theme === 'dark') {
                element.style.color = 'white';
            } else {
                element.style.color = '#000';
            }
        });
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
        setDynamicTextColor(); // 초기 로드 시 글자색 설정
    })();

    // 이벤트 리스너 추가
    lightModeBtn.addEventListener('change', () => setTheme('light'));
    darkModeBtn.addEventListener('change', () => setTheme('dark'));

    // 테마 변경 감지 및 글자색 업데이트
    window.addEventListener('storage', function(e) {
        if (e.key === 'theme') {
            setDynamicTextColor();
        }
    });

    // 초기화 함수 호출
    checkAccessToken();

    // 테마 변경 감지 및 글자색 업데이트
    window.addEventListener('storage', function(e) {
        if (e.key === 'theme') {
            setDynamicTextColor();
        }
    });
});

// getCurrentTheme 함수는 전역 범위에 유지
function getCurrentTheme() {
    return document.documentElement.getAttribute('data-bs-theme');
}