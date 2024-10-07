document.addEventListener('DOMContentLoaded', function () {
    const accessToken = localStorage.getItem("accessToken");

    // 저장된 후보자 목록을 가져올 DOM 요소가 제대로 있는지 확인
    const savedCVSection = document.getElementById('savedCVSection');
    if (!savedCVSection) {
        console.error("Error: 'savedCVSection' element not found.");
        return;
    }

    // 토큰이 없는 경우 로그인 페이지로 리다이렉트
    if (!accessToken) {
        console.log("토큰이 없습니다. 로그인이 필요합니다.");
        window.location.href = "/company/login";
        return;
    }

    // 로딩 중 메시지 표시
    savedCVSection.innerHTML = "<p>로딩 중...</p>";

    // 저장된 후보자 목록을 가져오는 함수
    const fetchSavedCVs = async () => {
        try {
            const response = await fetch('/com/headhunt/list/saved', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ` + accessToken,  // Bearer 토큰 설정
                    'Content-Type': 'application/json'
                }
            });

            // 토큰이 유효하지 않을 경우 재로그인 유도
            if (response.status === 401) {
                console.log("토큰이 유효하지 않습니다. 다시 로그인해주세요.");
                window.location.href = "/company/login";
                return;
            }

            // 응답 데이터 처리
            if (response.ok) {
                const data = await response.json();
                console.log('Received data:', data); // 응답 데이터 디버깅 로그 추가
                let savedCVs = data.data;

                if (savedCVs && savedCVs.length > 0) {
                    renderSavedCVs(savedCVs);  // 저장된 후보자 목록 렌더링
                } else {
                    savedCVSection.innerHTML = "<p>저장된 후보자가 없습니다.</p>";
                }
            } else {
                savedCVSection.innerHTML = "<p>저장된 후보자가 없습니다.</p>";
            }

        } catch (error) {
            console.error("Error fetching saved CVs: ", error);
            savedCVSection.innerHTML = "<p>저장된 후보자가 없습니다.</p>";
        }
    };

    // 저장된 후보자 목록을 화면에 렌더링하는 함수
    const renderSavedCVs = (savedCVs) => {
        let html = '';

        savedCVs.forEach(cv => {
            const imgSrc = cv.cvImage ? `/resources${cv.cvImage}` : '/resources/image/default.png';

            html += `
        <div class="user-card">
            <div class="user-card-content">
                <img src="${imgSrc}" alt="CV Image" class="user-image">
                <div class="user-info">
                    <p class="user-name">${cv.userName}</p>
                    <p class="user-education">최종학력: ${cv.education}</p>
                    <p class="user-address">주소: ${cv.userAddress}</p>
                    <p class="user-skill">기술 스택: ${cv.skill}</p>
                </div>
                <div class="user-actions">
                    <button class="btn btn-secondary">이직 제안하기</button>
                </div>
            </div>
        </div>`;
        });

        // DOM 업데이트
        savedCVSection.innerHTML = html;
    };

    // 저장된 후보자 목록 가져오기
    fetchSavedCVs();
});
