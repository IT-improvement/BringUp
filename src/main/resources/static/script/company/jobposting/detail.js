document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem('accessToken');
    const urlParams = new URLSearchParams(window.location.search);
    const recruitmentIndex = urlParams.get('index');
    console.log('recruitmentIndex:', recruitmentIndex);
    
    if (!accessToken) {
        window.location.href = '/company/auth/login';
        return;
    }

    if (!recruitmentIndex) {
        console.error('recruitmentIndex가 없습니다.');
        return;
    }

    const url = `/com/recruitment/detail/`+recruitmentIndex;
    console.log('요청 URL:', url);
    
    fetch(url, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer `+ accessToken,
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('받은 데이터:', data);
        const recruitmentData = data.data;
        
        // 데이터를 화면에 반영
        document.getElementById('c_name').innerText = recruitmentData.c_name;
        document.getElementById('recruitmentTitle').innerText = recruitmentData.r_title;
        document.getElementById('c_intro').innerText = recruitmentData.c_intro;
        document.getElementById('r_career').innerText = recruitmentData.r_career;
        document.getElementById('r_period').innerText = recruitmentData.r_period;
        document.getElementById('c_logo').src = recruitmentData.c_logo;
        document.getElementById("r_hospitality").innerText = recruitmentData.r_hospitality;
        document.getElementById('r_requirement').innerText = recruitmentData.r_requirement;
        document.getElementById('c_welfare').innerText = recruitmentData.c_welfare;
        document.getElementById('r_workdetail').innerText = recruitmentData.r_workdetail;
        document.getElementById('c_address').innerText = recruitmentData.c_address;
        document.getElementById('r_salary').innerText = recruitmentData.r_salary;
        
        // c_img 처리 부분 수정
        if (recruitmentData.c_img && recruitmentData.c_img.length > 0) {
            const carouselInner = document.querySelector('.carousel-inner');
            carouselInner.innerHTML = ''; // 기존 내용 초기화
            
            recruitmentData.c_img.forEach((imgSrc, index) => {
                const carouselItem = document.createElement('div');
                carouselItem.className = "carousel-item" + (index === 0 ? " active" : "");
                
                const img = document.createElement('img');
                img.src = imgSrc;
                img.className = 'd-block w-100';
                img.alt = `채용 공고 이미지 ${index + 1}`;
                img.style.height = '280px';
                
                carouselItem.appendChild(img);
                carouselInner.appendChild(carouselItem);
            });
            
            // 캐러셀 인디케이터 업데이트
            const carouselIndicators = document.querySelector('.carousel-indicators');
            carouselIndicators.innerHTML = ''; // 기존 인디케이터 초기화
            
            recruitmentData.c_img.forEach((_, index) => {
                const button = document.createElement('button');
                button.type = 'button';
                button.setAttribute('data-bs-target', '#carouselExampleIndicators');
                button.setAttribute('data-bs-slide-to', index.toString());
                if (index === 0) {
                    button.className = 'active';
                    button.setAttribute('aria-current', 'true');
                }
                button.setAttribute('aria-label', `Slide ${index + 1}`);
                
                carouselIndicators.appendChild(button);
            });
        } else {
            console.log('c_img 데이터가 없습니다.');
        }
        // 캐러셀 자동 슬라이드 기능 비활성화
        document.querySelector('.carousel').setAttribute('data-bs-interval', 'false');
    })
    .catch(error => {
        console.error('채용 공고 상세 정보를 가져오는 중 오류 발생:', error);
        alert('채용 공고 상세 정보를 불러올 수 없습니다. 나중에 다시 시도해주세요.');
    });
});