document.addEventListener('DOMContentLoaded', function() {
    const logoutButton = document.getElementById('logoutButton');
    const notificationButton = document.querySelector('.btn-round'); // 알림 버튼
    const notifBadge = document.querySelector('.notif-badge'); // 알림 배지 (blink 효과)
    const accessToken = localStorage.getItem('accessToken');

    // 로그아웃 처리
    if (logoutButton) {
        logoutButton.addEventListener('click', function(event) {
            event.preventDefault();
            localStorage.removeItem('accessToken');
            window.location.href = '/'; // 로그아웃 후 메인 페이지로 이동
        });
    }

    // 알림 버튼과 배지 제거 함수
    function removeNotificationElements() {
        if (notificationButton) {
            notificationButton.style.display = 'none'; // 알림 버튼 숨기기
        }
        if (notifBadge) {
            notifBadge.style.display = 'none'; // 알림 배지 숨기기
        }
    }

    // 알림 버튼과 배지 추가 함수 (로그인 상태일 때만 표시)
    function showNotificationElements() {
        if (notificationButton) {
            notificationButton.style.display = 'inline-block'; // 알림 버튼 표시
        }
        if (notifBadge) {
            notifBadge.style.display = 'inline-block'; // 알림 배지 표시
        }
    }

    // 토큰이 없으면 비회원 상태로 설정하고, 알림 버튼 및 배지 제거
    if (!accessToken) {
        console.log('Access Token이 없으므로 비회원 상태로 설정합니다.');
        updateMemberName('비회원');
        removeNotificationElements();
        return; // 토큰이 없으므로 더 이상 서버 요청을 하지 않음
    }

    // 토큰이 있으면 알림 버튼 및 배지 활성화
    showNotificationElements();

    // 회원 이름 업데이트 함수
    function updateMemberName(memberName) {
        const memberNameSpan = document.getElementById('memberNameSpan');
        const memberNameLink = document.getElementById('memberNameLink');

        if (memberNameSpan) {
            memberNameSpan.textContent = memberName;
            memberNameSpan.style.display = 'inline'; // 데이터 로드 후 표시
        }
        if (memberNameLink) {
            memberNameLink.textContent = memberName;
        }
    }

    // 회원 이름을 가져오기 위한 API 요청
    fetch('/member/name', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + accessToken,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.data) {
                updateMemberName(data.data); // 회원 이름 업데이트
            } else {
                console.error('멤버 이름 데이터가 올바르지 않습니다:', data);
                updateMemberName('알 수 없음'); // 오류 시 기본값 설정
            }
        })
        .catch(error => {
            console.error('멤버 이름을 가져오는 데 실패했습니다:', error);
            updateMemberName('알 수 없음'); // 오류 시 기본값 설정
        });

    // 테마 모드 전환 기능 이하 기존 코드
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

    (function () {
        if (localStorage.getItem('theme') === 'dark') {
            setTheme('dark');
            darkModeBtn.checked = true;
        } else {
            setTheme('light');
            lightModeBtn.checked = true;
        }
    })();

    lightModeBtn.addEventListener('change', () => setTheme('light'));
    darkModeBtn.addEventListener('change', () => setTheme('dark'));
});

// 기존의 테마 관련 함수들 유지
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

document.addEventListener('themeChanged', function(e) {
    const notificationButton = document.querySelector('.btn-round');
    if (notificationButton && notificationButton.matches(':hover')) {
        setNotificationButtonStyle(notificationButton);
    }
});

document.addEventListener('DOMContentLoaded', function() {
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');

    if (navbarToggler && navbarCollapse) {
        navbarToggler.addEventListener('click', function() {
            navbarCollapse.classList.toggle('show');
        });
    }
});
