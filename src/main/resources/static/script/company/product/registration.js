document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('paymentButton').addEventListener('click',function (){
        const itemIdx = sessionStorage.getItem(itemIdx);
        if (itemIdx === null){
            alert("아이템인덱스가 없습니다.");
        }else {
            console.log(itemIdx);
        }
    });

    const productName = document.querySelector('h3').textContent.trim();

    // 공통: 공고 선택 동작
    const advertisementInputElement = document.getElementById('advertisementInput');
    const accessToken = localStorage.getItem('accessToken');
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
                        document.getElementById('selectedAdvertisement').value = item.title;
                        document.getElementById('selectedAd').textContent = `선택된 공고: ${item.title}`;
                        $('#advertisementModal').modal('hide');
                        // 카드에 선택된 공고 표시
                        document.getElementById('selectedAd').textContent = `선택된 공고: ${item.title}`;
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
                    let valid = img.width === 875 && img.height === 500;
                    if (!valid) {
                        alert('이미지 크기가 올바르지 않습니다. 올바른 크기를 업로드하세요.');
                        event.target.value = '';
                    }
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
                    const startDate = selectedDates[0];
                    const endDate = new Date(startDate);
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
            if(event.target.value === '01:00~04:00') {
                document.getElementById('adType').textContent = '광고 유형: P3';
            } else if(event.target.value === '04:00~07:00') {
                document.getElementById('adType').textContent = '광고 유형: P1';
            } else if(event.target.value === '07:00~10:00') {
                document.getElementById('adType').textContent = '광고 유형: GP';
            } else if(event.target.value === '10:00~13:00') {
                document.getElementById('adType').textContent = '광고 유형: P2';
            } else if(event.target.value === '13:00~16:00') {
                document.getElementById('adType').textContent = '광고 유형: P1';
            } else if(event.target.value === '16:00~19:00') {
                document.getElementById('adType').textContent = '광고 유형: GP';
            } else if(event.target.value === '19:00~22:00') {
                document.getElementById('adType').textContent = '광고 유형: P1';
            } else if(event.target.value === '22:00~01:00') {
                document.getElementById('adType').textContent = '광고 유형: P3';
            } else {
                document.getElementById('adType').textContent = '광고 유형:';
            }
        });
    }

    // 메인 상품 신청 페이지
    if (productName.includes('메인')) {
        const imageUploadElement = document.getElementById('imageUpload');
        if (imageUploadElement) {
            imageUploadElement.addEventListener('change', function(event) {
                const file = event.target.files[0];
                if (!file) return;

                const img = new Image();
                img.onload = function() {
                    let valid = img.width === 1228 && img.height === 320;
                    if (!valid) {
                        alert('이미지 크기가 올바르지 않습니다. 올바른 크기를 업로드하세요.');
                        event.target.value = '';
                    }
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
                        const startDate = selectedDates[0];
                        const endDate = new Date(startDate);
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
                    let valid = img.width === 1228 && img.height === 80;
                    if (!valid) {
                        alert('이미지 크기가 올바르지 않습니다. 올바른 크기를 업로드하세요.');
                        event.target.value = '';
                    }
                };
                img.src = URL.createObjectURL(file);
            });
        }

        const bannerDateInput = document.getElementById('bannerDateRange');
        const durationSelect = document.getElementById('bannerProductSelect');

        bannerDateInput.addEventListener('focus', function(event) {
            const durationDays = parseInt(durationSelect.value, 10);
            if (!durationDays) {
                alert('먼저 광고 기간을 선택하세요.');
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
            } else {
                document.getElementById('duration').textContent = `광고 기간: ${event.target.value}일`;
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
                method: 'GET',
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
                console.log('Fetched data:', data); // 여기서 파싱된 JSON 데이터를 출력
                const price = data.data.itemPrice;
                const item = data.data.itemPrice.itemIdx;
                sessionStorage.setItem(item, itemIdx);
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
