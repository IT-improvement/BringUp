document.addEventListener('DOMContentLoaded', function () {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
        console.log("토큰이 없습니다. 로그인이 필요합니다.");
        window.location.href = "company/login";
        return;
    }

    // Fetch CVs (프리미엄과 일반 유저를 구분하지 않고 하나로 처리)
    fetch('/com/headhunt/list', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ` + accessToken,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            let allCVs = data.data;
            if (allCVs && allCVs.length > 0) {
                allCVs.forEach(function (cv) {
                    let imgSrc = cv.cvImage ? '/resources' + cv.cvImage : '/resources/image/default.png';
                    let card = `
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
                                <button class="btn btn-primary save-candidate" data-cvindex="${cv.cvIndex}">후보자 저장</button>
                                <button class="btn btn-secondary">이직 제안하기</button>
                            </div>
                        </div>
                    </div>`;
                    document.getElementById('userSection').insertAdjacentHTML('beforeend', card);
                });

                // 후보자 저장 버튼 클릭 이벤트
                document.querySelectorAll('.save-candidate').forEach(button => {
                    button.addEventListener('click', function () {
                        const cvIndex = this.getAttribute('data-cvindex');

                        fetch('/com/headhunt/candidate/save', {
                            method: 'POST',
                            headers: {
                                'Authorization': `Bearer ` + accessToken,
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify({ cvIndex: cvIndex })
                        })
                            .then(response => {
                                if (response.ok) {
                                    alert('후보자가 저장되었습니다.');
                                } else {
                                    throw new Error('후보자 저장에 실패했습니다.');
                                }
                            })
                            .catch(error => {
                                console.error("Error:", error);
                                alert('후보자 저장 중 오류가 발생했습니다.');
                            });
                    });
                });
            } else {
                document.getElementById('userSection').innerHTML = "<p>표시할 유저가 없습니다.</p>";
            }
        })
        .catch(error => {
            console.log("Error fetching CVs: ", error);
            document.getElementById('userSection').innerHTML = "<p>표시할 유저가 없습니다.</p>";
        });
});
