document.addEventListener('DOMContentLoaded', function () {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
        console.log("토큰이 없습니다. 로그인이 필요합니다.");
        window.location.href = "company/login"; // Redirect to login
        return;
    }

    // Fetch premium CVs
    fetch('/com/headhunt/recommend', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ` + accessToken,
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            let recommendations = data.data;
            if (recommendations && recommendations.length > 0) {
                recommendations.forEach(function (cv) {
                    let imgSrc = cv.cvImage ? `/static/logos/${cv.cvImage}` : '/image/default.png';
                    let card = `
                    <div class="card">
                        <img src="${imgSrc}" class="card-img-top" alt="CV Image">
                        <div class="card-body">
                            <h5 class="card-title">${cv.education}</h5>
                            <p class="card-text">${cv.userAddress}</p>
                            <p class="card-text">Skill: ${cv.skill}</p>
                        </div>
                    </div>`;
                    document.getElementById('premiumSection').insertAdjacentHTML('beforeend', card);
                });
            } else {
                document.getElementById('premiumSection').innerHTML = "<p>추천할 유저가 없습니다.</p>";
            }
        })
        .catch(error => {
            console.log("Error fetching premium CVs: ", error);
            document.getElementById('premiumSection').innerHTML = "<p>추천할 유저가 없습니다.</p>";
        });

    // Fetch general CVs
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
                    let imgSrc = cv.cvImage ? '/resources/image/' + cv.cvImage : '/resources/image/default.png';
                    let card = `
                    <div class="card">
                        <img src="${imgSrc}" class="card-img-top" alt="CV Image">
                        <div class="card-body">
                            <p class="card-title">${cv.userName}</p>
                            <p class="card-text">최종학력 : ${cv.education}</p>
                            <p class="card-text">주소 : ${cv.userAddress}</p>
                            <p class="card-text">기술 스택 : ${cv.skill}</p>
                        </div>
                    </div>`;
                    document.getElementById('generalSection').insertAdjacentHTML('beforeend', card);
                });
            } else {
                document.getElementById('generalSection').innerHTML = "<p>추천할 유저가 없습니다.</p>";
            }
        })
        .catch(error => {
            console.log("Error fetching general CVs: ", error);
            document.getElementById('generalSection').innerHTML = "<p>추천할 유저가 없습니다.</p>";
        });
});
