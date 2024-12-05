document.addEventListener('DOMContentLoaded', function() {
    // 이전 페이지가 /company/product/introduction인지 확인
    if (document.referrer.includes('/company/product/introduction')) {
        sessionStorage.clear(); // 세션 스토리지 초기화
    }

    let productName = document.querySelector('h3').textContent.trim();

    // URL에서 productName 파라미터 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const productNameFromUrl = urlParams.get('productName');
    
    // URL의 productName이 있으면 그것을 사용
    if (productNameFromUrl) {
        productName = decodeURIComponent(productNameFromUrl);
    }
    
    console.log("설정된 productName:", productName);

    // productName에 따른 입력값 저장
    const inputGroups = {
        '프리미엄': ['premiumDateRange', 'productSelect', 'imageUpload'],
        '메인': ['mainDateRange', 'productSelect', 'imageUpload'],
        '배너': ['bannerDateRange', 'bannerProductSelect', 'imageUpload'],
        '어나운스': ['announceStartDate', 'productSelect']
    };

    const selectedInputs = inputGroups[productName] || [];

    selectedInputs.forEach(inputId => {
        const element = document.getElementById(inputId);
        if (element) {
            element.addEventListener('input', function() {
                sessionStorage.setItem(inputId, this.value);
            });
            if (element.type === 'file') {
                element.addEventListener('change', function() {
                    sessionStorage.setItem(inputId, this.files[0] ? this.files[0].name : '');
                });
            }
        }
    });

    // 페이지 로드 시 sessionStorage에서 값 복원
    selectedInputs.forEach(inputId => {
        const element = document.getElementById(inputId);
        if (element) {
            const storedValue = sessionStorage.getItem(inputId);
            if (storedValue) {
                if (element.type === 'file') {
                    // 파일 입력은 파명을 복원할 수 없으므로 무시
                } else {
                    element.value = storedValue;
                }
            }
        }
    });

    // 페이지를 벗어날 때 세션 스토리지 초기화
    window.addEventListener('beforeunload', function() {
        selectedInputs.forEach(inputId => {
            sessionStorage.removeItem(inputId);
        });
    });

    const paymentFormButton = document.getElementById('paymentFormButton');
    paymentFormButton.addEventListener('click', function() {
        let isValid = true;
        const invalidInputs = [];
        
        // 공고 선택 검증
        const selectedAdvertisement = document.getElementById('selectedAdvertisement').value;
        if (!selectedAdvertisement) {
            document.getElementById('selectedAdvertisement').style.border = '2px solid red';
            invalidInputs.push('공고');
            isValid = false;
        }

        if (productName === '프리미엄') {
            // 프리미엄 광고 검증
            const premiumDateRange = document.getElementById('premiumDateRange').value;
            const displayTime = document.getElementById('productSelect').value;
            const imageUpload = document.getElementById('imageUpload').files[0];
            
            if (!premiumDateRange) {
                document.getElementById('premiumDateRange').style.border = '2px solid red';
                invalidInputs.push('날짜');
                isValid = false;
            }
            if (!displayTime) {
                document.getElementById('productSelect').style.border = '2px solid red';
                invalidInputs.push('시간');
                isValid = false;
            }
            if (!imageUpload) {
                document.getElementById('imageUpload').style.border = '2px solid red';
                invalidInputs.push('이미지');
                isValid = false;
            }

        } else if (productName === '메인') {
            // 메인 광고 검증
            const mainDateRange = document.getElementById('mainDateRange').value;
            const duration = document.getElementById('productSelect').value;
            const imageUpload = document.getElementById('imageUpload').files[0];
            
            if (!mainDateRange) {
                document.getElementById('mainDateRange').style.border = '2px solid red';
                invalidInputs.push('날짜');
                isValid = false;
            }
            if (!duration) {
                document.getElementById('productSelect').style.border = '2px solid red';
                invalidInputs.push('기간');
                isValid = false;
            }
            if (!imageUpload) {
                document.getElementById('imageUpload').style.border = '2px solid red';
                invalidInputs.push('이미지');
                isValid = false;
            }

        } else if (productName === '배너') {
            // 배너 광고 검증
            const bannerDateRange = document.getElementById('bannerDateRange').value;
            const duration = document.getElementById('bannerProductSelect').value;
            const imageUpload = document.getElementById('imageUpload').files[0];
            
            if (!bannerDateRange) {
                document.getElementById('bannerDateRange').style.border = '2px solid red';
                invalidInputs.push('날짜');
                isValid = false;
            }
            if (!duration) {
                document.getElementById('bannerProductSelect').style.border = '2px solid red';
                invalidInputs.push('기간');
                isValid = false;
            }
            if (!imageUpload) {
                document.getElementById('imageUpload').style.border = '2px solid red';
                invalidInputs.push('이미지');
                isValid = false;
            }

        } else if (productName === '어나운스') {
            // 일반 광고 검증
            const startDate = document.getElementById('announceStartDate').value;
            const duration = document.getElementById('productSelect').value;
            
            if (!startDate) {
                document.getElementById('announceStartDate').style.border = '2px solid red';
                invalidInputs.push('시작날짜');
                isValid = false;
            }
            if (!duration) {
                document.getElementById('productSelect').style.border = '2px solid red';
                invalidInputs.push('기간');
                isValid = false;
            }
        }

        if (!isValid) {
            alert(`다음 항목을 입력해주세요: ${invalidInputs.join(', ')}`);
        } else {
            const paymentButton = document.getElementById("paymentButton");
            if (paymentButton) {
                paymentButton.click();
            } else {
                alert("결제 버튼을 찾을 수 없습니다.");
            }
        }
    });

    // 결제 결과 이벤트 수신
    document.addEventListener("paymentResult", function(event) {
        const status = event.detail.status;
        const paymentResponse = event.detail.paymentResponse;
        
        if (status === "done") {
            console.log("결제 성공");
            console.log(paymentResponse);
            
            // 상품 타입 확인 - 현재 선택된 productName 기준으로 처리
            let type;
            // 디버깅을 위한 로그 추가
            console.log("현재 productName:", productName);
            
            // 정확한 문자열 비교를 위해 trim() 추가
            switch(productName.trim()) {
                case '프리미엄':
                case 'premium':
                case '프리미엄광고':
                    type = "premium";
                    handlePremiumAd();
                    break;
                case '메인':
                case 'main':
                    type = "main";
                    handleMainAd();
                    break;
                case '배너':
                case 'banner':
                    type = "banner";
                    handleBannerAd();
                    break;
                case '어나운스':
                case 'announce':
                    type = "announce";
                    handleAnnounceAd();
                    break;
                default:
                    console.error('알 수 없는 상품 타입:', productName);
                    return;
            }
        } else {
            handlePaymentError(status);
        }

        // 프리미엄 광고 처리 함수
        function handlePremiumAd() {
            const formData = new FormData();
            const premiumDateRangeValue = document.getElementById('premiumDateRange').value;
            if (!premiumDateRangeValue || !premiumDateRangeValue.includes(' ~ ')) {
                console.error('Invalid date range:', premiumDateRangeValue);
                alert('날짜 형식이 올바르지 않습니다.');
                return;
            }

            const [startDate, endDate] = premiumDateRangeValue.split(' ~ ');
            const displayTime = document.getElementById('productSelect').value;
            const imageFile = document.getElementById('imageUpload').files[0];
            const adType = document.getElementById('adType').textContent.replace(/광고 유형: /g, '');

            if (!startDate || !endDate || !displayTime || !imageFile || !adType) {
                console.error('Missing required data:', {
                    startDate,
                    endDate,
                    displayTime,
                    hasImage: !!imageFile,
                    adType
                });
                alert('필수 입력값이 누락되었습니다.');
                return;
            }

            const premiumData = {
                recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                adType: adType,
                timeSlot: displayTime,
                startDate: startDate,
                endDate: endDate,
                displayDate: Array.from(
                    { length: (new Date(endDate) - new Date(startDate)) / (24 * 60 * 60 * 1000) + 1 },
                    (_, i) => new Date(new Date(startDate).getTime() + i * 24 * 60 * 60 * 1000).toISOString().split('T')[0]
                ),
                orderIdx: paymentResponse.orderIndex
            };

            console.log('Premium data being sent:', premiumData);

            formData.append('premiumAdDto', new Blob([JSON.stringify(premiumData)], {type: 'application/json'}));
            formData.append('image', imageFile);

            sendRequest("premium", formData);
        }

        // 메인 광고 처리 함수
        function handleMainAd() {
            const formData = new FormData();
            const mainDateRangeValue = document.getElementById('mainDateRange').value;
            if (!mainDateRangeValue || !mainDateRangeValue.includes(' ~ ')) {
                console.error('Invalid date range:', mainDateRangeValue);
                alert('날짜 형식이 올바르지 않습니다.');
                return;
            }

            const [startDate, endDate] = mainDateRangeValue.split(' ~ ');
            const duration = document.getElementById('productSelect').value;
            const imageFile = document.getElementById('imageUpload').files[0];

            if (!startDate || !endDate || !duration || !imageFile) {
                console.error('Missing required data:', {
                    startDate,
                    endDate,
                    duration,
                    hasImage: !!imageFile
                });
                alert('필수 입력값이 누락되었습니다.');
                return;
            }

            const mainData = {
                recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                exposureDays: duration,
                startDate: startDate,
                endDate: endDate,
                orderIdx: paymentResponse.orderIndex
            };

            console.log('Main data being sent:', mainData);

            formData.append('mainAdDto', new Blob([JSON.stringify(mainData)], {type: 'application/json'}));
            formData.append('image', imageFile);

            sendRequest("main", formData);
        }

        // 배너 광고 처리 함수
        function handleBannerAd() {
            const formData = new FormData();
            const bannerDateRangeValue = document.getElementById('bannerDateRange').value;
            if (!bannerDateRangeValue || !bannerDateRangeValue.includes(' ~ ')) {
                console.error('Invalid date range:', bannerDateRangeValue);
                alert('날짜 형식이 올바르지 않습니다.');
                return;
            }

            const [startDate, endDate] = bannerDateRangeValue.split(' ~ ');
            const exposureDays = document.getElementById('bannerProductSelect').value;
            const imageFile = document.getElementById('imageUpload').files[0];

            if (!startDate || !endDate || !exposureDays || !imageFile) {
                console.error('Missing required data:', {
                    startDate,
                    endDate,
                    exposureDays,
                    hasImage: !!imageFile
                });
                alert('필수 입력값이 누락되었습니다.');
                return;
            }

            const bannerData = {
                recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                exposureDays: exposureDays,
                startDate: startDate,
                endDate: endDate,
                orderIdx: paymentResponse.orderIndex
            };

            console.log('Banner data being sent:', bannerData);

            formData.append('bannerAdDto', new Blob([JSON.stringify(bannerData)], {type: 'application/json'}));
            formData.append('image', imageFile);

            sendRequest("banner", formData);
        }

        // 어나운스 광고 처리 함수
        function handleAnnounceAd() {
            const announceData = {
                recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                durationDays: parseInt(document.getElementById('productSelect').value),
                startDate: document.getElementById('announceStartDate').value,
                endDate: document.getElementById('announceStartDate').value,
                orderIdx: paymentResponse.orderIndex
            };

            if (!announceData.startDate || !announceData.durationDays) {
                console.error('Missing required data:', announceData);
                alert('필수 입력값이 누락되었습니다.');
                return;
            }

            fetch(`/com/advertisement/announce/registration`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(announceData)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        console.error('Response status:', response.status);
                        console.error('Response status text:', response.statusText);
                        console.error('Response body:', text);
                        throw new Error(`HTTP error! status: ${response.status}, message: ${text}`);
                    });
                }
                return response.json();  // JSON으로 한 번만 파싱
            })
            .then(handleResponse)
            .catch(error => {
                console.error('Request failed:', error);
                handleError(error);
            });
        }

        // 요청 전송 함수
        function sendRequest(type, formData) {
            const url = `/com/advertisement/${type}/registration`;
            console.log('Request URL:', url);
            if (type === 'premium') {
                console.log('FormData contents:', {
                    premiumAdDto: formData.get('premiumAdDto'),
                    image: formData.get('image')
                });
            }else if (type === 'main') {
                console.log('FormData contents:', {
                    mainAdDto: formData.get('mainAdDto'),
                    image: formData.get('image')
                });
            }else if (type === 'banner') {
                console.log('FormData contents:', {
                    bannerAdDto: formData.get('bannerAdDto'),
                    image: formData.get('image')
                });
            }

            fetch(url, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                },
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        console.error('Response status:', response.status);
                        console.error('Response status text:', response.statusText);
                        console.error('Response body:', text);
                        throw new Error(`HTTP error! status: ${response.status}, message: ${text}`);
                    });
                }
                return response.json();  // JSON으로 한 번만 파싱
            })
            .then(handleResponse)
            .catch(error => {
                console.error('Request failed:', error);
                handleError(error);
            });
        }
    });

    // 응답 처리 함수 수정
    function handleResponse(response) {
        // response가 이미 JSON 객체인 경우
        if (typeof response === 'object') {
            if(response.code === 200) {
                alert("광고 등록이 완료되었습니다.");
                sessionStorage.clear();
                location.href = "/company/product/management";
            } else {
                throw new Error(response.message || "광고 등록에 실패했습니다.");
            }
        } 
        // response가 Response 객체인 경우 (아직 JSON으로 파싱되지 않은 경우)
        else {
            return response.json().then(response => {
                if(response.code === 200) {
                    alert("광고 등록이 완료되었습니다.");
                    sessionStorage.clear();
                    location.href = "/company/product/management";
                } else {
                    throw new Error(response.message || "광고 등록에 실패했습니다.");
                }
            }).catch(error => {
                console.error('JSON 파싱 에러:', error);
                throw new Error("응답 처리 중 오류가 발생했습니다.");
            });
        }
    }

    // 에러 처리 함수
    function handleError(error) {
        console.error('에러 발생:', error);
        alert("광고 등록 중 오류가 발생했습니다.");
        // location.href = "/company/product/management";
    }

    // 결제 상태 에러 처리 함수
    function handlePaymentError(status) {
        switch(status) {
            case "failed":
                alert("결제가 실패했습니다. 다시 시도주세요.");
                break;
            case "cancel":
                alert("결제가 취소되었습니다.");
                break;
            default:
                alert("알 수 없는 결제 상태입니다.");
        }
        location.reload();
    }

    // 입력값 변경 시 빨간 테두리 제거
    const inputs = [
        'premiumDateRange',
        'productSelect',
        'imageUpload',
        'mainDateRange',
        'bannerDateRange',
        'bannerProductSelect',
        'announceStartDate'
    ];

    inputs.forEach(inputId => {
        const element = document.getElementById(inputId);
        if (element) {
            if (element.type === 'file') {
                element.addEventListener('change', function() {
                    this.style.border = '';
                });
            } else {
                element.addEventListener('input', function() {
                    this.style.border = '';
                });
            }
        }
    });
    

    // 공통: 공고 선택 동작
    const advertisementInputElement = document.getElementById('advertisementInput');
    const accessToken = localStorage.getItem('accessToken');
    let startDate = '';
    let endDate = '';
    let displayTime = '';
    if (advertisementInputElement) {
        advertisementInputElement.addEventListener('click', function() {
            $('#advertisementModal').modal('show');
            const advertisementList = document.getElementById('advertisementList');
            advertisementList.innerHTML = '<li class="list-group-item text-center"><span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> 로딩 중...</li>';

            fetch('/com/recruitment/list', {
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                const advertisementData = data.data;
                if (!Array.isArray(advertisementData)) {
                    throw new Error('Invalid data format');
                }
                advertisementList.innerHTML = '';
                advertisementData.forEach(item => {
                    const listItem = document.createElement('li');
                    listItem.className = 'list-group-item';
                    listItem.textContent = item.title;
                    listItem.addEventListener('click', function() {
                        console.log(item);
                        document.getElementById('selectedAdvertisement').value = item.title;
                        document.getElementById('selectedAd').textContent = `선택된 공고: ${item.title}`;
                        $('#advertisementModal').modal('hide');
                        sessionStorage.setItem("recruitmentIndex", item.index);
                        // 카드에 선택된 공고 표시
                        document.getElementById('selectedAd').textContent = `선택된 공고: ${item.title}`;
                        document.getElementById('selectedAdvertisement').style.border = '';
                    });
                    advertisementList.appendChild(listItem);
                });
            })
            .catch(error => {
                console.error('Error fetching advertisement list:', error);
                advertisementList.innerHTML = '<li class="list-group-item text-center text-danger">데이터를 불러오는 데 실패했습니다.</li>';
            });
        });
    }

    // 프리미엄 상품 신청 페이지
    if (productName.includes('프리미엄')) {
        const imageUploadElement = document.getElementById('imageUpload');
        if (imageUploadElement) {
            imageUploadElement.addEventListener('change', function(event) {
                const file = event.target.files[0];
                if (!file) return;

                const img = new Image();
                img.onload = function() {
                    // let valid = img.width === 875 && img.height === 500;
                    // if (!valid) {
                    //     alert('이미지 크기가 올바르지 않습니다. 올바 크기를 업로드하요.');
                    //     event.target.value = '';
                    // }
                };
                img.src = URL.createObjectURL(file);
            });
        }

        flatpickr("#premiumDateRange", {
            mode: "range",
            dateFormat: "Y-m-d",
            minDate: "today",
            locale: "ko",
            onChange: function(selectedDates, dateStr, instance) {
                if (selectedDates.length === 1) {
                    startDate = selectedDates[0];
                    endDate = new Date(startDate);
                    endDate.setDate(startDate.getDate() + 2);
                    instance.setDate([startDate, endDate], true, { silent: true });
                    instance.close();
                }
            }
        });

        document.getElementById('premiumDateRange').addEventListener('change', function(event) {
            document.getElementById('adDate').textContent = `광고 날짜: ${event.target.value}`;
        });

        document.getElementById('productSelect').addEventListener('change', function(event) {
            document.getElementById('displayTime').textContent = `시간대: ${event.target.value}`;
            displayTime = event.target.value;
            startDate = document.getElementById('premiumDateRange').value.split(' ~ ')[0];
            endDate = document.getElementById('premiumDateRange').value.split(' ~ ')[1];
            console.log(startDate+", "+endDate+", "+displayTime);

            fetch("/com/advertisement/premium/available-times", {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    startDate: startDate,
                    endDate: endDate,
                    timeSlot: displayTime
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('응답 데이터:', data); // 응답 이터 확인
                const itemIdx = data.data ? data.data.itemIdx : null; // 데이터가 있는지 확인
                if (itemIdx === 0 || itemIdx === "0") {
                    if(confirm("해당 시간대에 광고 신청이 불가능합니다.\n다른 시간대를 선택하시겠습니까?")) {
                        document.getElementById('productSelect').value = '';
                        document.getElementById('adType').textContent = '광고 유형: ';
                        document.getElementById('paymentAmount').textContent = '결제 금액: ';
                    } else {
                        document.getElementById('productSelect').value = '';
                        document.getElementById('premiumDateRange').value = '';
                        document.getElementById('adType').textContent = '광고 유형: ';
                        document.getElementById('paymentAmount').textContent = '결제 금액: ';
                    }
                }else{
                    sessionStorage.setItem("itemIdx", itemIdx);
                    document.getElementById('adType').textContent = '광고 유형: '+data.data.itemName;
                    document.getElementById('paymentAmount').textContent = '결제 금액: '+data.data.itemPrice+'원';
                }
            })
            .catch(error => {
                console.error('Error fetching available times:', error);
            });
        });
    }

    // 메인 상품 신청 페이지
    if (productName.includes('메인')) {
        const imageUploadElement = document.getElementById('imageUpload');
        let startDate = '';
        let endDate = '';
        if (imageUploadElement) {
            imageUploadElement.addEventListener('change', function(event) {
                const file = event.target.files[0];
                if (!file) return;

                const img = new Image();
                img.onload = function() {
                    // let valid = img.width === 1228 && img.height === 320;
                    // if (!valid) {
                    //     alert('이미지 크기가 바르지 않습니다. 올바른 크기를 업로드하세요.');
                    //     event.target.value = '';
                    // }
                };
                img.src = URL.createObjectURL(file);
            });
        }

        const mainDateInput = document.getElementById('mainDateRange');
        const durationSelect = document.getElementById('productSelect');

        mainDateInput.addEventListener('focus', function(event) {
            const durationDays = parseInt(durationSelect.value, 10);
            if (!durationDays) {
                alert('먼저 광고 기간을 선택하세요.');
                durationSelect.focus();
                return;
            }

            flatpickr(mainDateInput, {
                mode: "range",
                dateFormat: "Y-m-d",
                minDate: "today",
                locale: "ko",
                onChange: function(selectedDates, dateStr, instance) {
                    if (selectedDates.length === 1) {
                        startDate = selectedDates[0];
                        endDate = new Date(startDate);
                        endDate.setDate(startDate.getDate() + durationDays - 1);
                        instance.setDate([startDate, endDate], true);
                        instance.close();
                    }
                }
            }).open();
        });

        document.getElementById('productSelect').addEventListener('change', function(event) {
            if(event.target.value === '') {
                document.getElementById('duration').textContent = `광고 기간: `;
            } else {
                document.getElementById('duration').textContent = `광고 기간: ${event.target.value}일`;
            }
            // 메인 광고 기간 선택의 값이 변경되면 메인 광고 날짜 선택 입력칸 초기화
            mainDateInput.value = '';
            document.getElementById('adDate').textContent = '광고 날짜: ';
        });

        document.getElementById('mainDateRange').addEventListener('change', function(event) {
            document.getElementById('adDate').textContent = `광고 날짜: ${event.target.value}`;
            const mainDateValue = document.getElementById('mainDateRange').value;
            if (mainDateValue.includes(" ~ ")) {
                startDate = mainDateValue.split(" ~ ")[0];
                endDate = mainDateValue.split(" ~ ")[1];
            } else {
                startDate = mainDateValue;
                endDate = mainDateValue;
            }
            console.log(startDate+", ",endDate);
            fetch("/com/advertisement/main/available-dates", {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    startDate: startDate,
                    endDate: endDate,
                    timeSlot: displayTime
                })
            })
            .then(response => response.json())
            .then(data => {
                console.log('서버 응답:', data);
                const itemName = data.data.itemName;
                const itemPrice = data.data.itemPrice;
                const itemIdx = data.data.itemIdx;
                sessionStorage.setItem("itemIdx", itemIdx);
                document.getElementById('duration').textContent = `광고 기간: ${itemName}`;
                document.getElementById('paymentAmount').textContent = `결제 금액: ${itemPrice}원`;
            })
            .catch(error => {
                console.error('에러 발생:', error);
            });
        });
    }

    // 배너 상품 신청 페이지
    if (productName.includes('배너')) {
        const imageUploadElement = document.getElementById('imageUpload');
        if (imageUploadElement) {
            imageUploadElement.addEventListener('change', function(event) {
                const file = event.target.files[0];
                if (!file) return;

                const img = new Image();
                img.onload = function() {
                    // let valid = img.width === 1228 && img.height === 80;
                    // if (!valid) {
                    //     alert('이미지 크기가 바르지 않습니다. 올바 크기를 업로드하세요.');
                    //     event.target.value = '';
                    // }
                };
                img.src = URL.createObjectURL(file);
            });
        }

        const bannerDateInput = document.getElementById('bannerDateRange');
        const durationSelect = document.getElementById('bannerProductSelect');

        bannerDateInput.addEventListener('focus', function(event) {
            const durationDays = parseInt(durationSelect.value, 10);
            if (!durationDays) {
                alert('먼저 광고 기간을 선택세요.');
                durationSelect.focus();
                return;
            }

            flatpickr(bannerDateInput, {
                mode: "range",
                dateFormat: "Y-m-d",
                minDate: "today",
                locale: "ko",
                onChange: function(selectedDates, dateStr, instance) {
                    if (selectedDates.length === 1) {
                        const startDate = selectedDates[0];
                        const endDate = new Date(startDate);
                        endDate.setDate(startDate.getDate() + durationDays - 1);
                        instance.setDate([startDate, endDate], true);
                        instance.close();
                    }
                }
            }).open();
        });

        document.getElementById('bannerProductSelect').addEventListener('change', function(event) {
            if(event.target.value === '') {
                document.getElementById('duration').textContent = `광고 기간: `;
                document.getElementById('paymentAmount').textContent = `결제 금액: `;
            } else {
                console.log(event.target.value);
                const duration = event.target.value;
                fetch('/com/advertisement/banner/price', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        displayTime: duration
                    })
                })
                .then(response => response.json())
                .then(data => {
                    console.log('서버 응답:', data);
                    const itemPrice = data.data.itemPrice;
                    const itemIdx = data.data.itemIdx;
                    const itemName = data.data.itemName;
                    sessionStorage.setItem("itemIdx", itemIdx);
                    document.getElementById('duration').textContent = `광고 기간: ${itemName}`;
                    document.getElementById('paymentAmount').textContent = `결제 금액: ${itemPrice}원`;
                })
                .catch(error => {
                    console.error('에러 발생:', error);
                });
            }
            // 배너 광고 기간 선택의 값이 변경되면 배너 광고 날짜 선택 입력칸 초기화
            bannerDateInput.value = '';
            document.getElementById('adDate').textContent = '광고 날짜: ';
        });

        document.getElementById('bannerDateRange').addEventListener('change', function(event) {
            document.getElementById('adDate').textContent = `광고 날짜: ${event.target.value}`;
        });
    }

    // 어나운스 상품 신청 페이지
    if (productName.includes('어나운스')) {
        flatpickr("#announceStartDate", {
            dateFormat: "Y-m-d",
            minDate: "today",
            locale: "ko",
            onChange: function(selectedDates, dateStr, instance) {
                // 카드에 선택된 날짜 표시
                document.getElementById('startDate').textContent = `광고 시작일: ${dateStr}`;
            }
        });

        document.getElementById('productSelect').addEventListener('change', function(event) {
            if(event.target.value === '') {
                document.getElementById('duration').textContent = `광고 기간: `;
            } else if(event.target.value === '12') {
                document.getElementById('duration').textContent = `광고 기간: 1년`;
            } else {
                document.getElementById('duration').textContent = `광고 기간: ${event.target.value}개월`;
            }
            let duration = event.target.value;
            if (duration) {
                fetch(`/com/advertisement/announce/price?displayTime=${duration}`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${accessToken}`,
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    }
                    throw new Error('Network response was not ok');
                })
                .then(data => {
                    console.log('Fetched data:', data);
                    const price = data.data.itemPrice;
                    const itemIdx = data.data.itemIdx;
                    sessionStorage.setItem("itemIdx", itemIdx);
                    if (duration === '12') {
                        duration = '1년';
                    } else {
                        duration += '개월';
                    }
                    document.getElementById('paymentAmount').textContent = `결제 금액: ${price}원`;
                    document.getElementById('duration').textContent = `광고 기간: ${duration}`;
                })
                .catch(error => {
                    console.error('Error fetching announcement price:', error);
                });
            }
        });
    }
});
