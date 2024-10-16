document.addEventListener('DOMContentLoaded', function() {
    // 이벤트 위임을 사용하여 로그아웃 버튼 클릭 이벤트 처리
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'logoutButton' || event.target.closest('#logoutButton')) {
            console.log("로그아웃 버튼 클릭");
            event.preventDefault();
            localStorage.removeItem('accessToken');
            window.location.href = '/';
        }
    });

    // 나머지 코드는 그대로 유지
    $(document).ready(function() {
        const accessToken = localStorage.getItem("accessToken");
        
        // 초기 상태에서 두 카드 모두 숨김
        document.getElementById("before-login-card").style.display = "none";
        document.getElementById("after-login-card").style.display = "none";
        
        if (accessToken) {
            // 액세스 토큰이 있을 경우
            document.getElementById("before-login-card").style.display = "none";
            document.getElementById("after-login-card").style.display = "block";
            
            fetch('/member/name', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + accessToken
                }
            })
            .then(response => response.json())
            .then(data => {
                document.getElementById("card-title-after-login").textContent = data.data + "님 환영합니다!";
                document.getElementById("card-text-after-login").textContent = "프리미엄 회원입니다.";
            })
            .catch(() => {
                document.getElementById("card-title-after-login").textContent = "오류가 발생했습니다.";
                document.getElementById("card-text-after-login").textContent = "다시 로그인해 주세요.";
            });
        } else {
            // 액세스 토큰이 없을 경우
            document.getElementById("before-login-card").style.display = "block";
            document.getElementById("after-login-card").style.display = "none";
        }
            
        
        
        fetch('/main/premium', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("프리미엄 광고 이미지 요청 결과:", data);
            const premiumImage = document.getElementById('premium-image');
            
            premiumImage.src = data.premiumImage;
            
            const premiumLink = document.createElement('a');
            premiumLink.href = `/member/recruitment/details/${data.recruitmentIndex}`;
            premiumLink.appendChild(premiumImage);
            
            const premiumContainer = document.querySelector('.user-premium-container');
            premiumContainer.innerHTML = '';
            premiumContainer.appendChild(premiumLink);
        })
        .catch((error) => {
            console.error("광고 이미지를 가져오는 중 오류가 발생했습니다:", error);
        });


        // 메인 이미지 슬라이더
        fetch('/main/main', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("메인 광고 이미지 요청 : ", data);
            const carouselInner = document.querySelector('#main-image-slider .carousel-inner');
            const carouselIndicators = document.querySelector('#main-image-slider .carousel-indicators');
            updateCarousel(data, carouselInner, carouselIndicators, '#main-image-slider', '메인 광고 이미지');
        })
        .catch((error) => {
            console.error("메인 광고 이미지를 가져오는 중 오류가 발생했습니다:", error);
        });

        // 배너 이미지 슬라이더
        fetch('/main/banner', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("배너 광고 이미지 요청 : ", data);
            const bannerContainer = document.querySelector('.user-banner-container');
            bannerContainer.innerHTML = `
                <a href="/member/recruitment/details/${data.recruitmentIndex}" class="w-100 h-100 d-block rounded-3">
                    <img id="banner-image" src="${data.banner_Image}" class="w-100 h-100 object-fit-cover" alt="배너 광고 이미지">
                </a>
            `;
        })
        .catch((error) => {
            console.error("배너 광고 이미지를 가져오는 중 오류가 발생했습니다:", error);
        });

        // 캐러셀 업데이트 함수
        function updateCarousel(data, carouselInner, carouselIndicators, targetId, altText) {
            carouselInner.innerHTML = '';
            carouselIndicators.innerHTML = '';
            
            data.forEach((image, index) => {
                // 캐러셀 아이템 생성
                const carouselItem = document.createElement('div');
                carouselItem.className = "carousel-item" + (index === 0 ? " active" : "");
                
                const img = document.createElement('img');
                img.src = image.main_Image;
                img.className = 'd-block w-100';
                img.alt = `${altText} ${index + 1}`;
                if (targetId === '#main-image-slider') {
                    img.style.height = '320px';
                }
                
                const link = document.createElement('a');
                link.href = `/member/recruitment/details/${image.recruitmentIndex}`;
                link.appendChild(img);
                carouselItem.appendChild(link);
                
                carouselInner.appendChild(carouselItem);

                // 인디케이터 생성
                const button = document.createElement('button');
                button.type = 'button';
                button.setAttribute('data-bs-target', targetId);
                button.setAttribute('data-bs-slide-to', index.toString());
                if (index === 0) {
                    button.className = 'active';
                    button.setAttribute('aria-current', 'true');
                }
                button.setAttribute('aria-label', `Slide ${index + 1}`);
                carouselIndicators.appendChild(button);
            });
        }

        
        // 데이터 가져오기 및 카드 업데이트
        fetch('/main/list', {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            console.log("메인 공고 리스트 요청 : ", data);
            updateRecruitmentCards(data);
        })
        .catch((error) => {
            console.error("채용 공고 정보를 가져오는 중 오류가 발생했습니다:", error);
        });

        function updateRecruitmentCards(recruitmentData) {
            const recruitContainer = document.querySelector('.user-recruit-container .d-flex');
            recruitContainer.innerHTML = '';

            recruitmentData.forEach((item, index) => {
                const card = document.createElement('div');
                card.className = 'card m-2';
                card.style = 'width: 21%; min-width: 200px; margin-bottom: 20px; border: 1px solid transparent; transition: all 0.3s ease; cursor: pointer;';

                card.innerHTML = `
                    <a href="/member/recruitment/details/${item.recruitmentIndex}" style="text-decoration: none; color: inherit;">
                        <div style="height: 200px; overflow: hidden; position: relative;">
                            <img src="${item.companyImg}" class="position-absolute w-100 h-100 object-fit-cover">
                        </div>
                        <div class="card-body p-2">
                            <h6 class="card-title mb-1" style="font-size: 0.9rem; font-weight: bold;">${item.recruitmentTitle}</h6>
                            <p class="card-text mb-1" style="font-size: 0.8rem; color: #666;">${item.companyName}</p>
                            <p class="card-text mb-0 skill-tags" style="font-size: 0.75rem; color: #888;"></p>
                        </div>
                    </a>
                `;

                recruitContainer.appendChild(card);

                // 마우스 오버 이벤트 추가
                card.addEventListener('mouseover', function() {
                    this.style.borderColor = '#ddd';
                    this.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.1)';
                    this.style.backgroundColor = '#f8f9fa';
                });
                card.addEventListener('mouseout', function() {
                    this.style.borderColor = 'transparent';
                    this.style.boxShadow = 'none';
                    this.style.backgroundColor = 'transparent';
                });

                // 스킬 태그 추가
                const skillTags = card.querySelector('.skill-tags');
                item.skill.split(',').forEach(skill => {
                    const span = document.createElement('span');
                    span.className = 'badge bg-light text-dark me-1';
                    span.textContent = skill.trim();
                    skillTags.appendChild(span);
                });
            });

            // 빈 카드 추가 (4의 배수로 맞추기 위해)
            const emptyCardsNeeded = 4 - (recruitmentData.length % 4);
            if (emptyCardsNeeded < 4) {
                for (let i = 0; i < emptyCardsNeeded; i++) {
                    const emptyCard = document.createElement('div');
                    emptyCard.className = 'card m-2 invisible';
                    emptyCard.style = 'width: 21%; min-width: 200px; margin-bottom: 20px;';
                    recruitContainer.appendChild(emptyCard);
                }
            }
        }

        // 로그인 버튼 클릭 이벤트
        $("#login-button").click(function() {
            window.location.href = "/member/Login";
        });
        
        // 회원가입 버튼 클릭 이벤트
        $("#register-button").click(function() {
            window.location.href = "/register";
        });
    });
});