document.addEventListener('DOMContentLoaded', function () {
    const accessToken = localStorage.getItem("accessToken");
    const userSection = document.getElementById('userSection');
    const loadingSpinner = createLoadingSpinner();
    userSection.appendChild(loadingSpinner); // 로딩 스피너 표시

    // 액세스 토큰 확인
    if (!accessToken) {
        console.log("토큰이 없습니다. 로그인이 필요합니다.");
        window.location.href = "company/login";
        return;
    }

    // 데이터 요청 시작
    fetchCVs(accessToken)
        .then(data => {
            const allCVs = data.data;
            if (allCVs && allCVs.length > 0) {
                renderCVsLazyLoad(allCVs); // Lazy Loading 적용
            } else {
                userSection.innerHTML = "<p>표시할 유저가 없습니다.</p>";
            }
        })
        .catch(error => {
            console.log("Error fetching CVs: ", error);
            userSection.innerHTML = "<p>표시할 유저가 없습니다.</p>";
        })
        .finally(() => {
            loadingSpinner.remove(); // 로딩 스피너 제거
        });
});

// 로딩 스피너 생성 함수
function createLoadingSpinner() {
    const spinner = document.createElement('div');
    spinner.className = 'spinner-border text-primary';
    spinner.role = 'status';
    return spinner;
}

// CV 목록을 가져오는 함수 (데이터 캐싱)
function fetchCVs(accessToken) {
    const cachedData = sessionStorage.getItem('cvList');
    if (cachedData) {
        return Promise.resolve(JSON.parse(cachedData));
    }

    return fetch('/com/headhunt/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ` + accessToken,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            sessionStorage.setItem('cvList', JSON.stringify(data)); // 데이터 캐싱
            return data;
        });
}

// Lazy Load를 적용하여 CV 렌더링
function renderCVsLazyLoad(cvList) {
    const userSection = document.getElementById('userSection');
    userSection.innerHTML = ''; // 기존 내용 제거

    cvList.forEach(function (cv, index) {
        const imgSrc = cv.cvImage ? '/resources' + cv.cvImage : '/resources/image/default.png';

        const card = document.createElement('div');
        card.className = 'user-card';
        card.innerHTML = `
            <div class="user-card-content">
                <img data-src="${imgSrc}" alt="CV Image" class="user-image lazyload">
                <div class="user-info">
                    <p class="user-name">${cv.userName}</p>
                    <p class="user-education">최종학력: ${cv.education}</p>
                    <p class="user-address">주소: ${cv.userAddress}</p>
                    <p class="user-skill">기술 스택: ${cv.skill}</p>
                </div>
                <div class="user-actions">
                    <button class="btn btn-primary">후보자 저장</button>
                    <button class="btn btn-secondary">이직 제안하기</button>
                </div>
            </div>
        `;

        userSection.appendChild(card);
    });

    // IntersectionObserver로 Lazy Loading 적용
    const lazyImages = document.querySelectorAll('img.lazyload');
    const observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.getAttribute('data-src');
                img.classList.remove('lazyload');
                observer.unobserve(img); // 이미지를 로드하면 감시 중지
            }
        });
    }, { rootMargin: "0px 0px 200px 0px" }); // 스크롤 근처에 있을 때 로드

    lazyImages.forEach(img => {
        observer.observe(img);
    });
}
