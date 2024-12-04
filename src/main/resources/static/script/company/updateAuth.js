document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem("accessToken");
    const url = "/com/companyInfo/post"
    // 초기 이미지 데이터를 저장할 전역 객체
    window.initialImageData = {
        companyLogo: '',
        companyImgs: []
    };

    if (accessToken) {
        fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ` + accessToken
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            console.log(data.data);

            // 초기 데이터 저장
            window.initialImageData.companyLogo = data.data.companyLogo || '';
            
            const companyImg = data.data.companyImg;
            window.initialImageData.companyImgs = companyImg ? companyImg.split(",").map(img => img.trim()) : [];

            // 회사 로고 처리
            if (data.data.companyLogo) {
                document.getElementById('companyLogo-file-chosen').textContent = data.data.companyLogo;
                document.getElementById('companyLogoHidden').value = data.data.companyLogo;
                document.getElementById('companyLogo_img').src = data.data.companyLogo;
                // 최초 렌더링에서는 이미지를 숨깁니다.
                document.getElementById('companyLogo_img').style.display = 'none';
            }

            // 회사 로고 이벤트 리스너 추가
            const companyLogoInput = document.getElementById('companyLogo');
            companyLogoInput.addEventListener('change', handleCompanyLogoChange);

            const companyLogoViewButton = document.getElementById('companyLogo-viewImage');
            companyLogoViewButton.addEventListener('click', toggleCompanyLogoVisibility);

            const companyLogoRemoveButton = document.getElementById('companyLogo-removeImage');
            companyLogoRemoveButton.addEventListener('click', removeCompanyLogo);

            // 회사 대표 이미지 처리
            const companyImgArray = companyImg ? companyImg.split(",") : [];
            
            companyImgArray.forEach((imgSrc, index) => {
                if (index < 5) {  // 최대 5개까지만 처리
                    const fileNameSpan = document.getElementById(`companyImg${index}-file-chosen`);
                    const hiddenInput = document.getElementById(`companyImg${index}Hidden`);
                    const img = document.getElementById(`companyImg${index}_img`);

                    fileNameSpan.textContent = imgSrc.trim() || '파일을 선택하세요';
                    hiddenInput.value = imgSrc.trim();
                    img.src = imgSrc.trim();
                    img.style.display = 'none';  // 초기에는 이미지를 숨깁니다.
                }
            });

            // 나머지 폼 필드 채우기
            document.getElementById('companyName').value = data.data.companyName || '';
            document.getElementById('representativeName').value = data.data.masterName || '';
            document.getElementById('address').value = data.data.companyAddress || '';
            document.getElementById('homepage').value = data.data.companyHomepage || '';
            document.getElementById('industry').value = data.data.companyCategory || '';
            document.getElementById('employeeCount').value = data.data.companySize || '';
            document.getElementById('representativeEmail').value = data.data.managerEmail || '';
            document.getElementById('phoneNumber').value = data.data.companyPhonenumber || '';
            document.getElementById('companyContent').value = data.data.companyContent || '';
            document.getElementById('companyWelfare').value = data.data.companyWelfare || '';
            document.getElementById('companyHistory').value = data.data.companyHistory || '';
            document.getElementById('companyVision').value = data.data.companyVision || '';
            document.getElementById('companyFinancialStatements').value = data.data.companyFinancialStatements || '';
            document.getElementById('companySubsidiary').value = data.data.companySubsidiary || '';

            // textarea의 rows 조정
            adjustTextareaRows('companyContent');
            adjustTextareaRows('companyWelfare');
            adjustTextareaRows('companyHistory');
            adjustTextareaRows('companyVision');
            adjustTextareaRows('companyFinancialStatements');
            adjustTextareaRows('companySubsidiary');

            // 드래그 앤 드롭 기능 초기화
            initDragAndDrop();

            // 이미지 컨테이너에 이벤트 위임 설정
            const companyImgContainer = document.getElementById('companyImgContainer');
            companyImgContainer.addEventListener('click', handleCompanyImgContainerClick);
            companyImgContainer.addEventListener('change', handleCompanyImgContainerChange);

            // 이미지 수정 버튼에 이벤트 리스너 추가
            const imageUpdateButton = document.getElementById('image-update');
            imageUpdateButton.addEventListener('click', function() {
                console.log('이미지 수정 버튼 클릭');
                
                const companyLogoHiddenInput = document.getElementById('companyLogoHidden').value;
                const companyImgInputs = document.querySelectorAll('[id^="companyImg"][id$="Hidden"]');
                const companyImgValues = Array.from(companyImgInputs).map(input => input.value);
                
                const result = {
                    companyLogo: companyLogoHiddenInput
                };

                companyImgValues.forEach((imgSrc, index) => {
                    let currentValue = imgSrc.trim();
                    const initialValue = window.initialImageData.companyImgs[index] || '';
                    
                    result[`c_img${index}`] = currentValue;
                    
                    // 초기값과 현재값을 비교하여 상태 설정
                    if (currentValue === initialValue) {
                        result[`img${index}_status`] = "유지";
                    } else if (currentValue === '') {
                        result[`img${index}_status`] = "삭제";
                    } else if (currentValue.startsWith('data:image')) {
                        result[`img${index}_status`] = "수정";
                    }
                });

                console.log(JSON.stringify(result, null, 2));

                // /com/user/image로 요청 보내기
                fetch('/com/user/image', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ` + localStorage.getItem("accessToken")
                    },
                    body: JSON.stringify(result)
                })
                .then(response => response.json())
                .then(data => {
                    console.log('서버 응답:', data);
                })
                .catch(error => {
                    console.error('요청 중 오류 발생:', error);
                });
            });
        });
    }
});

function handleCompanyImgContainerClick(event) {
    const target = event.target;
    const button = target.closest('.btn-secondary, .btn-danger');
    if (button) {
        const index = button.closest('.companyImg-group').dataset.index;
        if (button.classList.contains('btn-secondary')) {
            toggleImageVisibility(index);
        } else if (button.classList.contains('btn-danger')) {
            removeImage(index);
        }
    }
}

function handleCompanyImgContainerChange(event) {
    const target = event.target;
    if (target.type === 'file') {
        const index = target.closest('.companyImg-group').dataset.index;
        handleFileInputChange(event, index);
    }
}

function createImageGroup(index, imgSrc) {
    const div = document.createElement('div');
    div.className = 'companyImg-group mb-3';
    div.draggable = true;
    div.dataset.index = index;
    div.innerHTML = `
        <div class="input-group mb-2">
            <div class="drag-handle me-2">&#9776;</div>
            <label for="companyImg${index}" class="form-control d-flex align-items-center cursor-pointer rounded-2 me-2">
                <i class="bi bi-image me-2"></i>
                <span id="companyImg${index}-file-chosen">${imgSrc || '파일을 선하세요'}</span>
            </label>
            <input type="file" class="d-none" id="companyImg${index}" name="companyImg${index}">
            <input type="hidden" id="companyImg${index}Hidden" name="companyImg${index}Hidden" value="${imgSrc}">
            <button type="button" class="btn btn-secondary rounded-2 d-flex justify-content-center align-items-center me-2" id="companyImg${index}-viewImage" style="height: 40px; width: 40px;">
                <i class="bi bi-eye"></i>
            </button>
            <button type="button" class="btn btn-danger rounded-2 d-flex justify-content-center align-items-center" id="companyImg${index}-removeImage" style="height: 40px; width: 40px;">X</button>
        </div>
        <img id="companyImg${index}_img" src="${imgSrc}" alt="Company Img ${index}" style="display: none; max-width: 100%; height: auto; margin-top: 10px;">
    `;

    return div;
}

function initDragAndDrop() {
    const container = document.getElementById('companyImgContainer');
    new Sortable(container, {
        animation: 150,
        handle: '.drag-handle',
        onEnd: function(evt) {
            updateImageIndexes();
        }
    });
}

function updateImageIndexes() {
    const container = document.getElementById('companyImgContainer');
    const groups = container.querySelectorAll('.companyImg-group');
    groups.forEach((group, index) => {
        group.dataset.index = index;
        group.querySelector('label').setAttribute('for', `companyImg${index}`);
        group.querySelector('input[type="file"]').id = `companyImg${index}`;
        group.querySelector('input[type="file"]').name = `companyImg${index}`;
        group.querySelector('input[type="hidden"]').id = `companyImg${index}Hidden`;
        group.querySelector('input[type="hidden"]').name = `companyImg${index}Hidden`;
        group.querySelector('span').id = `companyImg${index}-file-chosen`;
        group.querySelector('button:nth-of-type(1)').id = `companyImg${index}-viewImage`;
        group.querySelector('button:nth-of-type(2)').id = `companyImg${index}-removeImage`;
        group.querySelector('img').id = `companyImg${index}_img`;
    });
}

function handleFileInputChange(e, index) {
    const fileInput = e.target;
    const hiddenInput = document.getElementById(`companyImg${index}Hidden`);
    const fileNameSpan = document.getElementById(`companyImg${index}-file-chosen`);
    const img = document.getElementById(`companyImg${index}_img`);

    if (fileInput.files && fileInput.files[0]) {
        const fileName = fileInput.files[0].name;
        fileNameSpan.textContent = fileName;

        const reader = new FileReader();
        reader.onload = function(e) {
            img.src = e.target.result;
            img.style.display = 'none'; // 파일을 선택해도 이미지는 숨겨둡니다.
            hiddenInput.value = e.target.result; // 바이너리 데이터를 저장합니다.
        };
        reader.readAsDataURL(fileInput.files[0]);
        console.log(hiddenInput.value);
    } else {
        fileNameSpan.textContent = '파일을 선택하세요';
        hiddenInput.value = '';
        img.src = '';
        img.style.display = 'none';
    }
}

function toggleImageVisibility(index) {
    const allImages = document.querySelectorAll('[id^="companyImg"][id$="_img"]');
    const targetImg = document.getElementById(`companyImg${index}_img`);
    const hiddenInput = document.getElementById(`companyImg${index}Hidden`);
    
    // 타겟 이미지가 현재 보이는 상태인지 확인
    const isTargetVisible = targetImg.style.display === 'block';
    
    // 모든 이미지를 숨깁니다
    allImages.forEach(img => img.style.display = 'none');
    
    // 이미지 src가 비어있지 않고, 타겟 이미지가 숨겨져 있었을 때만 표시
    if (hiddenInput.value && !isTargetVisible) {
        targetImg.style.display = 'block';
    }
}

function removeImage(index) {
    const fileInput = document.getElementById(`companyImg${index}`);
    const hiddenInput = document.getElementById(`companyImg${index}Hidden`);
    const fileNameSpan = document.getElementById(`companyImg${index}-file-chosen`);
    const img = document.getElementById(`companyImg${index}_img`);

    fileInput.value = '';
    hiddenInput.value = null;  // 여기를 빈 문자열 대신 null로 변경
    fileNameSpan.textContent = '파일을 선택하세요';
    img.src = '';
    img.style.display = 'none';
}

function adjustTextareaRows(elementId) {
    const textarea = document.getElementById(elementId);
    const lines = textarea.value.split('\n').length;
    textarea.rows = Math.max(3, lines); // 최소 3줄, 내용에 따라 증가
}

function handleCompanyLogoChange(e) {
    const fileInput = e.target;
    const hiddenInput = document.getElementById('companyLogoHidden');
    const fileNameSpan = document.getElementById('companyLogo-file-chosen');
    const img = document.getElementById('companyLogo_img');

    if (fileInput.files && fileInput.files[0]) {
        const fileName = fileInput.files[0].name;
        fileNameSpan.textContent = fileName;
        hiddenInput.value = fileName;

        const reader = new FileReader();
        reader.onload = function(e) {
            img.src = e.target.result;
            img.style.display = 'none'; // 파일을 선택해도 이미지는 숨겨둡니다.
        };
        reader.readAsDataURL(fileInput.files[0]);
    } else {
        fileNameSpan.textContent = '파일을 선택하세요';
        hiddenInput.value = '';
        img.src = '';
        img.style.display = 'none';
    }
}

function toggleCompanyLogoVisibility() {
    const img = document.getElementById('companyLogo_img');
    const hiddenInput = document.getElementById('companyLogoHidden');
    
    // 회사 로고가 현재 보이는 상태인지 확인
    const isLogoVisible = img.style.display === 'block';
    
    // 모든 회사 이미지를 숨깁니다
    const allCompanyImages = document.querySelectorAll('[id^="companyImg"][id$="_img"]');
    allCompanyImages.forEach(img => img.style.display = 'none');
    
    // 이미지 src가 비어있지 않고, 로고가 숨겨져 있었을 때만 표시
    if (hiddenInput.value && !isLogoVisible) {
        img.style.display = 'block';
    } else {
        img.style.display = 'none';
    }
}

function removeCompanyLogo() {
    const fileInput = document.getElementById('companyLogo');
    const hiddenInput = document.getElementById('companyLogoHidden');
    const fileNameSpan = document.getElementById('companyLogo-file-chosen');
    const img = document.getElementById('companyLogo_img');

    fileInput.value = '';
    hiddenInput.value = '';
    fileNameSpan.textContent = '파일을 선택하세요';
    img.src = '';
    img.style.display = 'none';
}
