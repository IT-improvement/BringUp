document.addEventListener('DOMContentLoaded', function() {
    const accessToken = localStorage.getItem("accessToken");
    const url = "/com/companyInfo/post"
    const multipart = new FormData();
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

            // 폼 필드 채우기
            document.getElementById('companyName').value = data.data.companyName;
            document.getElementById('representativeName').value = data.data.masterName;
            document.getElementById('address').value = data.data.companyAddress;
            document.getElementById('homepage').value = data.data.companyHomepage;
            document.getElementById('industry').value = data.data.companyCategory;
            document.getElementById('employeeCount').value = data.data.companySize;
            document.getElementById('representativeEmail').value = data.data.managerEmail;
            document.getElementById('phoneNumber').value = data.data.companyPhonenumber;

            document.getElementById('companyContent').value = data.data.companyContent;
            const content_rows = data.data.companyContent ? data.data.companyContent.split('\n').length : 1;
            document.getElementById('companyContent').rows = content_rows;

            document.getElementById('companyWelfare').value = data.data.companyWelfare;
            const welfare_rows = data.data.companyWelfare ? data.data.companyWelfare.split('\n').length : 1;
            document.getElementById('companyWelfare').rows = welfare_rows;

            document.getElementById('companyHistory').value = data.data.companyHistory;
            const history_rows = data.data.companyHistory ? data.data.companyHistory.split('\n').length : 1;
            document.getElementById('companyHistory').rows = history_rows;
            
            document.getElementById('companyVision').value = data.data.companyVision;
            const vision_rows = data.data.companyVision ? data.data.companyVision.split('\n').length : 1;
            document.getElementById('companyVision').rows = vision_rows;

            document.getElementById('companyFinancialStatements').value = data.data.companyFinancialStatements || '';
            const financial_rows = data.data.companyFinancialStatements ? data.data.companyFinancialStatements.split('\n').length : 1;
            document.getElementById('companyFinancialStatements').rows = financial_rows;

            document.getElementById('companySubsidiary').value = data.data.companySubsidiary || '';
            const subsidiary_rows = data.data.companySubsidiary ? data.data.companySubsidiary.split('\n').length : 1;
            document.getElementById('companySubsidiary').rows = subsidiary_rows;
            
            console.log(data.data.companyLogo);
            console.log(data.data.companyImg);
            // 이미지 파일 이름 표시 (실제 파일 업로드는 아님)
            if (data.data.companyLogo) {
                document.querySelector('#companyLogo-file-chosen').textContent = data.data.companyLogo;
                document.getElementById('companyLogoHidden').value = data.data.companyLogo;
                multipart.set('c_logo', data.data.companyLogo);
                console.log("multipart 로고: "+multipart.get('c_logo'));
            }
            if (data.data.companyImg) {
                const img_names = data.data.companyImg.split(',');
                const imgArray = [];
                img_names.forEach((name, index) => {
                    console.log("name : "+name);
                    imgArray.push(name);
                    if (index === 0) {
                        document.querySelector('#companyImage0-file-chosen').textContent = name;
                        document.getElementById('companyImage0Hidden').value = name;
                    } else {
                        const newContainer = document.createElement('div');
                        newContainer.className = 'companyImage-container';
                        newContainer.innerHTML = `
                        <div class="d-flex mb-2">
                            <label for="companyImage${index}" class="form-control d-flex align-items-center cursor-pointer rounded-2 me-2" style="height: 40px;">
                                <i class="bi bi-image me-2"></i>
                                <span id="companyImage${index}-file-chosen">${name}</span>
                            </label>
                            <input type="file" class="d-none" id="companyImage${index}" name="companyImage[]" accept="image/*">
                            <input type="hidden" id="companyImage${index}Hidden" name="companyImageHidden[]" value="${name}">
                            <button type="button" class="btn btn-secondary rounded-2 d-flex justify-content-center align-items-center me-2" id="companyImage${index}-viewImage" style="height: 40px; width: 40px;">
                                <i class="bi bi-eye"></i>
                            </button>
                            <button type="button" id="${index}-removeImage" class="btn btn-danger rounded-2 remove-image" style="height: 40px; width: 40px;">-</button>
                        </div>
                        <img id="companyImage${index}_img" src="" alt="Company Image ${index}" style="display: none;">
                        `;
                        document.getElementById('companyImage-container').appendChild(newContainer);
                    }
                });

                
                console.log("imgArray : "+imgArray);
                // FormData에 배열로 이미지 추가
                imgArray.forEach((name, index) => {
                    multipart.append('c_imgs['+index+']', name);
                });
                    
                for (let [key, value] of multipart.entries()) {
                    if (key.startsWith('c_imgs')) {
                        console.log("multipart 이미지 " + key + " : " + value);
                    }
                }

                imageCount = img_names.length;
            }
            {
                console.log("멀티파트로고 : "+multipart.get('c_logo'));
                for (let [key, value] of multipart.entries()) {
                    if (key.startsWith('c_imgs')) {
                        console.log("multipart 이미지 " + key + " : " + value);
                    }
                }
            }
            // 이미지 추가 버튼 이벤트 리스너
            document.querySelector('#addImage').addEventListener('click', function() {
                if (imageCount < 5) {
                    const newContainer = document.createElement('div');
                    newContainer.className = 'd-flex mb-2';
                    newContainer.innerHTML = `
                        <label for="companyImage${imageCount}" class="form-control d-flex align-items-center cursor-pointer rounded-2 me-2" style="height: 40px;">
                            <i class="bi bi-image me-2"></i>
                            <span id="companyImage${imageCount}-file-chosen">파일을 선택하세요</span>
                        </label>
                        <input type="file" class="d-none" id="companyImage${imageCount}" name="companyImage[]" accept="image/*">
                        <input type="hidden" id="companyImage${imageCount}Hidden" name="companyImageHidden[]">
                        <button type="button" class="btn btn-secondary rounded-2 d-flex justify-content-center align-items-center me-2" id="companyImage${imageCount}-viewImage" style="height: 40px; width: 40px;">
                            <i class="bi bi-eye"></i>
                        </button>
                        <button type="button" id="${imageCount}-removeImage" class="btn btn-danger rounded-2 remove-image" style="height: 40px; width: 40px;">-</button>
                    `;
                    document.getElementById('companyImage-container').appendChild(newContainer);
                    imageCount++;
                } else {
                    alert('회사 대표 이미지는 최대 5개까지 등록할 수 있습니다.');
                }
            });
            

            // 이미지 제거 버튼 이벤트 리스너
            document.getElementById('companyImage-container').addEventListener('click', function(e) {
                if (e.target.classList.contains('remove-image')) {
                    const imgIndex = e.target.id.replace('-removeImage', '');
                    console.log("타겟 아이디 : "+imgIndex);
                    e.target.closest('.d-flex').remove();
                    imageCount--;
                }
            });

            // companyLogo 파일 입력 변경 이벤트 리스너
            document.getElementById('companyLogo').addEventListener('change', function(e) {
                const fileInput = e.target;
                const hiddenInput = document.getElementById('companyLogoHidden');
                const fileNameSpan = document.getElementById('companyLogo-file-chosen');

                if (fileInput.files && fileInput.files[0]) {
                    const fileName = fileInput.files[0].name;
                    fileNameSpan.textContent = fileName;
                    hiddenInput.value = fileName;

                    // 파일을 세션스토리지에 저장하는 로직
                    const fileReader = new FileReader();
                    fileReader.onload = function(e) {
                        const fileContent = e.target.result;
                        sessionStorage.setItem('companyLogo', fileContent);
                    };
                    fileReader.readAsDataURL(fileInput.files[0]);

                    multipart.set('c_logo', fileInput.files[0]);
                    console.log("multipart 로고: "+multipart.get('c_logo'));
                } else {
                    fileNameSpan.textContent = '파일을 선택하세요';
                    hiddenInput.value = '';
                    // 세션스토리지에 저장된 파일을 삭제하는 로직
                    sessionStorage.removeItem('companyLogo');
                }
            });

            

            // 이미지 보기 버튼 이벤트 리스너
            document.addEventListener('click', function(e) {
                const viewImageButton = e.target.closest('[id$="-viewImage"]');
                if (viewImageButton) {
                    console.log("뷰 이미지 버튼 클릭됨:", viewImageButton.id);
                    
                    const inputId = viewImageButton.id.replace('-viewImage', '');
                    const input = document.getElementById(inputId);
                    const hidden = document.getElementById(inputId + 'Hidden');
                    const img = document.getElementById(inputId + '_img');
                    
                    console.log("입력 요소:", inputId);
                    console.log("입력 요소 값:", input.value);
                    console.log("히든:", hidden);
                    console.log("입력 요소 값:", hidden.value);
                    console.log("이미지 요소:", inputId + '_img');

                    if (hidden.value && hidden.value.startsWith('/image')) {
                        if (img.style.display === 'block') {
                            img.style.display = 'none';
                        } else {
                            img.src = hidden.value;
                            img.style.display = 'block';
                        }
                    }
                    else {
                        const storedImage = sessionStorage.getItem(inputId);
                        if (storedImage && img.style.display === 'none') {
                            img.src = storedImage;
                            img.style.display = 'block';
                        } else {
                            img.src = '';
                            img.style.display = 'none';
                        }
                    }
                }
            });

            
            document.getElementById('companyImage-container').addEventListener('change', function(e) {
                if (e.target && e.target.id.startsWith('companyImage')) {
                    const fileInput = e.target;
                    const index = fileInput.id.replace('companyImage', '');
                    const hiddenInput = document.getElementById(`companyImage${index}Hidden`);
                    const fileNameSpan = document.getElementById(`companyImage${index}-file-chosen`);

                    if (fileInput.files && fileInput.files[0]) {
                        const fileName = fileInput.files[0].name;
                        fileNameSpan.textContent = fileName;
                        hiddenInput.value = fileName;

                        // 파일을 세션스토리지에 저장하는 로직
                        const fileReader = new FileReader();
                        fileReader.onload = function(e) {
                            const fileContent = e.target.result;
                            sessionStorage.setItem(`companyImage${index}`, fileContent);
                        };
                        fileReader.readAsDataURL(fileInput.files[0]);

                        multipart.set('c_imgs['+index+']', fileInput.files[0]);
                        console.log("multipart 이미지 " + index + " : " + multipart.get('c_imgs['+index+']'));
                    } else {
                        fileNameSpan.textContent = '파일을 선택하세요';
                        hiddenInput.value = '';
                        // 세션스토리지에 저장된 파일을 삭제하는 로직
                        sessionStorage.removeItem(`companyImage${index}`);
                    }
                }
            });

            document.getElementById('Image-update').addEventListener('click', function() {
                const url = "/com/user/image"
                console.log("전송 직전 multipart 내용:");
                for (let [key, value] of multipart.entries()) {
                    console.log(key + ": " + value);
                }
                fetch(url, {
                    method: 'put',
                    headers: {
                        'Authorization': `Bearer ` + accessToken
                    },
                    body: multipart
                })
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('수정 중 오류가 발생했습니다. : '+error.message);
                });
            });

            document.getElementById('updateForm').addEventListener('submit', function(e) {
                e.preventDefault();
                
                var formData = new FormData(this);
                
                fetch('/com/user', {
                    method: 'PUT',
                    body: formData,
                    headers: {
                        'Authorization': `Bearer ` + accessToken
                    }
                })
                .then(response => {
                    if (response.ok) {
                        alert('수정이 완료되었습니다. : '+response.data);
                        // 성공 후 처리 (예: 페이지 리다이렉트)
                    } else {
                        alert('수정 중 오류가 발생했습니다. : '+response.data);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('수정 중 오류가 발생했습니다. : '+error.message);
                });
            });
        });
    }
});

