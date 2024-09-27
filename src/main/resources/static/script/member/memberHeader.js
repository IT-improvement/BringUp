const profileDropdownElement = document.getElementById('profileDropdown');
const loginElement = document.getElementById('login');
const memberNameSpan = document.getElementById('memberNameSpan');
const memberNameLink = document.getElementById('memberNameLink');
const accessToken = localStorage.getItem('accessToken');

// 전역 함수로 logout 정의
function logout() {
    localStorage.removeItem('accessToken');
    updateUIForLoggedOutUser();
    window.location.href = '/';
}

// 페이지 로드 시 실행되는 함수
function checkAccessToken() {
    const accessToken = localStorage.getItem('accessToken');
    console.log('Checking accessToken:', accessToken);
    if (accessToken) {
        // accessToken이 있는 경우 (로그인 상태)
        updateUIForLoggedInUser();
    } else {
        // accessToken이 없는 경우 (로그아웃 상태)
        updateUIForLoggedOutUser();
    }
}

// 로그인 상태 UI 업데이트
function updateUIForLoggedInUser() {
    loginElement.classList.add('d-none');
    loginElement.classList.remove('d-flex');
    profileDropdownElement.classList.remove('d-none');
    profileDropdownElement.classList.add('d-flex');
    
    fetch('/member/name', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${accessToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        const userName = data.data;
        memberNameSpan.textContent = userName;
        memberNameSpan.style.display = 'inline';
        memberNameLink.textContent = userName;
    })
    .catch(error => console.error('Error fetching user name:', error));
}

// 로그아웃 상태 UI 업데이트
function updateUIForLoggedOutUser() {
    loginElement.classList.remove('d-none');
    loginElement.classList.add('d-flex');
    profileDropdownElement.classList.add('d-none');
    profileDropdownElement.classList.remove('d-flex');
    memberNameSpan.style.display = 'none';
}

// 페이지 로드 시 checkAccessToken 함수 실행
document.addEventListener('DOMContentLoaded', checkAccessToken);

