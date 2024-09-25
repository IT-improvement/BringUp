document.addEventListener('DOMContentLoaded', function () {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
        console.log("토큰이 없습니다. 로그인이 필요합니다.");
        window.location.href = "company/login";
        return;
    }

    // 로딩 애니메이션 추가
    const userSection = document.getElementById('userSection');
    userSection.innerHTML = "<p>로딩 중...</p>";

    // 모든 CV 및 저장 여부를 한번에 가져오기
    const fetchCVsAndSavedStatus = async () => {
        try {
            const cvResponse = await fetch('/com/headhunt/list', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            const cvData = await cvResponse.json();
            let allCVs = cvData.data;

            if (allCVs && allCVs.length > 0) {
                const cvIndices = allCVs.map(cv => cv.cvIndex);

                // 저장된 상태를 한번에 가져오기 (POST 방식으로 cvIndices 전달)
                const savedResponse = await fetch(`/com/headhunt/candidate/check-saved`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ cvIndices }) // cvIndices를 JSON으로 전달
                });

                const savedStatus = await savedResponse.json();

                // 저장 여부 확인된 상태와 함께 UI 렌더링
                renderCVs(allCVs, savedStatus.data);
            } else {
                userSection.innerHTML = "<p>표시할 유저가 없습니다.</p>";
            }
        } catch (error) {
            console.error("Error fetching CVs or saved status: ", error);
            userSection.innerHTML = "<p>표시할 유저가 없습니다.</p>";
        }
    };

    // CV 렌더링 함수
    const renderCVs = (allCVs, savedStatus) => {
        let html = '';

        allCVs.forEach(cv => {
            // 저장된 상태 확인 (undefined일 경우 false로 처리)
            const isSaved = savedStatus[cv.cvIndex] === true;
            const imgSrc = cv.cvImage ? `/resources${cv.cvImage}` : '/resources/image/default.png';
            const buttonText = isSaved ? '저장된 후보자' : '후보자 저장';

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
                            <button class="btn btn-primary save-candidate" data-cvindex="${cv.cvIndex}">${buttonText}</button>
                            <button class="btn btn-secondary">이직 제안하기</button>
                        </div>
                    </div>
                </div>`;
        });

        // DOM 업데이트
        userSection.innerHTML = html;

        // 이벤트 바인딩
        document.querySelectorAll('.save-candidate').forEach(button => {
            button.addEventListener('click', handleSaveButtonClick);
        });
    };

    // 저장 버튼 클릭 핸들러
    const handleSaveButtonClick = async (event) => {
        const button = event.target;
        const cvIndex = button.getAttribute('data-cvindex');
        const isSaved = button.innerText === '저장된 후보자';
        const action = isSaved ? 'DELETE' : 'POST';
        const url = isSaved ? `/com/headhunt/candidate/del/${cvIndex}` : `/com/headhunt/candidate/save/${cvIndex}`;

        try {
            const response = await fetch(url, {
                method: action,
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                button.innerText = isSaved ? '후보자 저장' : '저장된 후보자';
                button.classList.toggle('btn-save');
                button.classList.toggle('btn-saved');
                alert(isSaved ? '후보자가 삭제되었습니다.' : '후보자가 저장되었습니다.');
            } else {
                throw new Error('작업에 실패했습니다.');
            }
        } catch (error) {
            console.error("Error:", error);
            alert('작업 중 오류가 발생했습니다.');
        }
    };

    // CV 및 저장 상태 가져오기
    fetchCVsAndSavedStatus();
});
