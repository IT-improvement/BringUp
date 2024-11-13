document.addEventListener('DOMContentLoaded', function() {
    // 이전 페이지가 /company/product/introduction인지 확인
    if (document.referrer.includes('/company/product/introduction')) {
        sessionStorage.clear(); // 세션 스토리지 초기화
    }

    let productName = document.querySelector('h3').textContent.trim();

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
                    // 파일 입력은 파일명을 복원할 수 없으므로 무시
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
                const type = productName.includes("프리미엄") ? "premium" : productName.includes("메인") ? "main" : productName.includes("배너") ? "banner" : "announce";
                const formData = new FormData();
                let data = {};

                if (type === "premium" || type === "main" || type === "banner") {
                    data = {
                        recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                        startDate: startDate,
                        endDate: endDate
                    };

                    if (type === "premium") {
                        // PremiumAdRequestDto 형식에 맞게 데이터 구성
                        const premiumData = {
                            recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                            adType: document.getElementById('adType').textContent.replace(/광고 유형: /g, ''),
                            timeSlot: displayTime,
                            startDate: startDate,
                            endDate: endDate,
                            displayDate: Array.from(
                                { length: (new Date(endDate) - new Date(startDate)) / (24 * 60 * 60 * 1000) + 1 },
                                (_, i) => new Date(new Date(startDate).getTime() + i * 24 * 60 * 60 * 1000).toISOString().split('T')[0]
                            )
                        };

                        const imageFile = document.getElementById('imageUpload').files[0];
                        formData.append('premiumAdDto', new Blob([JSON.stringify(premiumData)], {type: 'application/json'}));
                        formData.append('image', imageFile);

                    } else if (type === "main") {
                        const mainDateRangeValue = document.getElementById('mainDateRange').value;
                        if (mainDateRangeValue.includes(' ~ ')) {
                            startDate = mainDateRangeValue.split(' ~ ')[0];
                            endDate = mainDateRangeValue.split(' ~ ')[1];
                        } else {
                            startDate = mainDateRangeValue;
                            endDate = mainDateRangeValue;
                        }
                        data.exposureDays = document.getElementById('productSelect').value;
                        const imageFile = document.getElementById('imageUpload').files[0];
                        const mainData = {
                            recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                            exposureDays: document.getElementById('productSelect').value,
                            startDate: startDate,
                            endDate: endDate,
                            useDate: Array.from(
                                { length: (new Date(endDate) - new Date(startDate)) / (24 * 60 * 60 * 1000) + 1 },
                                (_, i) => new Date(new Date(startDate).getTime() + i * 24 * 60 * 60 * 1000).toISOString().split('T')[0]
                            )
                        };
                        formData.append('mainAdDto', new Blob([JSON.stringify(mainData)], {type: 'application/json'}));
                        formData.append('image', imageFile);
                    } else if (type === "banner") {
                        const mainDateRangeValue = document.getElementById('mainDateRange').value;
                        if (mainDateRangeValue.includes(' ~ ')) {
                            startDate = mainDateRangeValue.split(' ~ ')[0];
                            endDate = mainDateRangeValue.split(' ~ ')[1];
                        } else {
                            startDate = mainDateRangeValue;
                            endDate = mainDateRangeValue;
                        }
                        data.exposureDays = document.getElementById('bannerProductSelect').value;
                        const imageFile = document.getElementById('imageUpload').files[0];
                        const bannerData = {
                            recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                            exposureDays: document.getElementById('bannerProductSelect').value,
                            startDate: new Date(startDate).toISOString().split('T')[0],
                            endDate: new Date(endDate).toISOString().split('T')[0]
                        };
                        formData.append('bannerAdDto', new Blob([JSON.stringify(bannerData)], {type: 'application/json'}));
                        formData.append('image', imageFile);
                    }

                    fetch(`/com/advertisement/${type}`, {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ${accessToken}`
                        },
                        body: formData
                    })
                    .then(response => {
                        if (!response.ok) {
                            console.log("프리미엄 데이터"+formData.get('premiumAdDto'));
                            console.log("메인 데이터"+formData.get('mainAdDto'));
                            console.log("배너 데이터"+formData.get('bannerAdDto'));
                            console.log("이미지"+formData.get('image'));
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(responseData => {
                        if(responseData.code === 200) {
                            alert("광고 등록이 완료되었습니다.");
                            sessionStorage.clear();
                            location.href = "/company/product/management";
                        } else {
                            alert("광고 등록에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error('에러 발생:', error);
                        alert("광고 등록 중 오류가 발생했습니다.");
                    });

                } else if (type === "announce") {
                    console.log('Start Date:', document.getElementById('announceStartDate').value);
                    console.log('Duration Months:', document.getElementById('productSelect').value);
                    data = {
                        recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                        durationDays: parseInt(document.getElementById('productSelect').value),
                        startDate: document.getElementById('announceStartDate').value,
                        endDate: document.getElementById('announceStartDate').value
                    };

                    fetch(`/com/advertisement/${type}`, {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ${accessToken}`,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(data)
                    })
                    .then(response => response.json())
                    .then(responseData => {
                        console.log('응답 데이터:', responseData);
                        if(responseData.code === 200) {
                            alert("광고 등록이 완료되었습니다.");
                            sessionStorage.clear();
                            location.href = "/company/product/management";
                        } else {
                            alert("광고 등록에 실패했습니다.");
                        }
                    })
                    .catch(error => {
                        console.error('에러 발생:', error);
                    });
                }
            } else {
                alert("결제 버튼을 찾을 수 없습니다.");
            }
        }
    });

    // 결제 결과 이벤트 수신
    document.addEventListener("paymentResult", function(event) {
        const status = event.detail.status;
        switch (status) {
            case "done":
                console.log("결제 성공");
                // const type = productName.includes("프리미엄") ? "premium" : productName.includes("메인") ? "main" : productName.includes("배너") ? "banner" : "announce";
                // const formData = new FormData();
                // if(type === "premium") {
                //     const data = {
                //         recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                //         adType: document.getElementById('adType').value,
                //         timeSlot: displayTime,
                //         startDate: startDate,
                //         endDate: endDate
                //     }
                //     formData.append('image', document.getElementById('imageUpload').files[0]);
                //     formData.append('data', JSON.stringify(data));
                // } else if(type === "main") {
                //     const data = {
                //         recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                //         exposureDays: document.getElementById('productSelect').value,
                //         startDate: startDate,
                //         endDate: endDate
                //     }
                //     formData.append('image', document.getElementById('imageUpload').files[0]);
                //     formData.append('data', JSON.stringify(data));
                // } else if(type === "banner") {
                //     const data = {
                //         recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                //         exposureDays: document.getElementById('bannerProductSelect').value, 
                //         startDate: startDate,
                //         endDate: endDate
                //     }
                //     formData.append('image', document.getElementById('imageUpload').files[0]);
                //     formData.append('data', JSON.stringify(data));
                // } else if(type === "announce") {
                //     const data = {
                //         recruitmentIndex: parseInt(sessionStorage.getItem("recruitmentIndex")),
                //         durationDays: document.getElementById('productSelect').value,
                //         startDate: startDate,
                //         endDate: endDate
                //     }
                //     formData.append('data', JSON.stringify(data));
                // }
                
                // try {
                //     fetch(`/com/advertisement/${type}`, {
                //         method: 'POST',
                //         headers: {
                //             'Authorization': `Bearer ${accessToken}`,
                //             'Content-Type': 'application/json'
                //         },
                //         body: formData
                //     })
                //     .then(data => {
                //         // FormData의 모든 항목 출력
                //         for (let pair of formData.entries()) {
                //             console.log(pair[0] + ': ' + pair[1]);
                //         }
                //         console.log('상품 등록 응답:', data);
                //     })
                //     .catch(error => {
                //         console.error('상품 등록 에러:', error);
                //     });
                // } catch (error) {
                //     console.error("상품 등록 실패:", error);
                // }
                break;
            case "failed":
                console.log("결제 실패");
                alert("결제가 실패했습니다. 다시 시도해주세요.");
                location.reload(); // 페이지 새로고침
                break;
            case "cancel":
                console.log("결제 취소");
                alert("결제가 취소되었습니다.");
                location.reload(); // 페이지 새로고침
                break;
            default:
                console.log("알 수 없는 결제 상태");
                alert("알 수 없는 결제 상태입니다.");
                break;
        }
    });

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
                console.log('응답 데이터:', data); // 응답 데이터 확인
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
                    //     alert('이미지 크기가 올바르지 않습니다. 올바른 크기를 업로드하세요.');
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
                    //     alert('이미지 크기가 올바르지 않습니다. 올바른 크기를 업로드하세요.');
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
