var currentPage = 1;
var itemsPerPage = 5;
var allItems = [];

$(document).ready(function() {
    $('#selectedAdvertisement').click(function() {
        if (allItems.length === 0) {
            fetchAllItems();
        } else {
            updateList();
        }
        $('#advertisementModal').modal('show');
    });

    $('#searchAdvertisement').keyup(function() {
        var searchValue = $(this).val().toLowerCase();
        var filteredItems = allItems.filter(function(item) {
            return item.recruitmentTitle.toLowerCase().includes(searchValue);
        });
        updateList(filteredItems);
    });

    $('#premiumDateRange').change(function() {
        var Date = $(this).val();

        if (Date) {
            console.log(Date);
            var startDate = Date.split(' ~ ')[0];
            var endDate = Date.split(' ~ ')[1];
            console.log(startDate);
            console.log(endDate);
            
            fetch('/com/advertisement/premium/available-times', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken'),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    startDate: startDate,
                    endDate: endDate
                })
            })
            .then(response => response.json())
            .then(result => {
                console.log(result);
                updateProductSelect(result);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('가용 시간을 불러오는 데 실패했습니다. 다시 시도해 주세요.');
            });
        } else {
            resetProductSelect();
        }
    });

    let flatpickrInstance;

    $('#productSelect').change(function() {
        const duration = parseInt($(this).val());
        if (duration) {
            initializeFlatpickr(duration);
        } else {
            if (flatpickrInstance) {
                flatpickrInstance.destroy();
            }
            $('#mainDateRange').val('');
        }
    });

    function initializeFlatpickr(inputId, selectId, fixedDuration) {
        let flatpickrInstance;

        const commonConfig = {
            mode: "range",
            dateFormat: "Y-m-d",
            minDate: "today",
            locale: "ko",
            disableMobile: "true"
        };

        if (fixedDuration) {
            flatpickrInstance = flatpickr(inputId, {
                ...commonConfig,
                onChange: function(selectedDates, dateStr, instance) {
                    if (selectedDates.length === 1) {
                        const startDate = selectedDates[0];
                        const endDate = new Date(startDate);
                        endDate.setDate(endDate.getDate() + fixedDuration - 1);
                        instance.setDate([startDate, endDate]);
                        setTimeout(() => instance.close(), 0);
                    }
                }
            });
        } else {
            $(selectId).change(function() {
                const duration = parseInt($(this).val());
                if (duration) {
                    if (flatpickrInstance) {
                        flatpickrInstance.destroy();
                    }
                    flatpickrInstance = flatpickr(inputId, {
                        ...commonConfig,
                        onChange: function(selectedDates, dateStr, instance) {
                            if (selectedDates.length === 1) {
                                const startDate = selectedDates[0];
                                const endDate = new Date(startDate);
                                endDate.setDate(endDate.getDate() + duration - 1);
                                instance.setDate([startDate, endDate]);
                                setTimeout(() => instance.close(), 0);
                            }
                        }
                    });
                } else {
                    if (flatpickrInstance) {
                        flatpickrInstance.destroy();
                    }
                    $(inputId).val('');
                }
            });
        }
    }

    // 프리미엄 광고 날짜 선택 초기화 (3일 고정)
    initializeFlatpickr("#premiumDateRange", null, 3);

    // 메인 광고 날짜 선택 초기화
    initializeFlatpickr("#mainDateRange", "#productSelect");

    // 배너 광고 날짜 선택 초기화
    initializeFlatpickr("#bannerDateRange", "#bannerProductSelect");
});

function fetchAllItems() {
    fetch('/com/recruitment/list', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer '+ localStorage.getItem('accessToken')
        }
    })
    .then(response => response.json())
    .then(result => {
        if (result.code === 200 && Array.isArray(result.data)) {
            allItems = result.data;
            updateList();
        } else {
            console.error('예상치 못한 데이터 구조:', result);
            $('#advertisementList').html('<li class="list-group-item">데이터를 불러올 수 없습니다.</li>');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        $('#advertisementList').html('<li class="list-group-item">데이터를 불러오는 중 오류가 발생했습니다.</li>');
    });
}

function updateList(items = allItems) {
    var startIndex = (currentPage - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;
    var pageItems = items.slice(startIndex, endIndex);

    var list = '';
    if (pageItems.length > 0) {
        pageItems.forEach(function(item) {
            list += '<button type="button" class="list-group-item list-group-item-action" onclick="selectAdvertisement(\'' + item.title + '\', ' + item.index + ')">' + item.title + '</button>';
        });
    } else {
        list = '<p class="list-group-item text-center">표시할 항목이 없습니다.</p>';
    }
    $('#advertisementList').html(list);
    
    // 리스트 항목이 적을 때 빈 공간을 채우기 위한 더미 항목 추가
    var dummyCount = itemsPerPage - pageItems.length;
    for (var i = 0; i < dummyCount; i++) {
        list += '<div class="list-group-item" style="visibility: hidden;">&nbsp;</div>';
    }
    $('#advertisementList').html(list);
    
    updatePagination(items.length);
}

function updatePagination(totalItems) {
    var totalPages = Math.ceil(totalItems / itemsPerPage);
    var paginationHtml = '';
    for (var i = 1; i <= totalPages; i++) {
        paginationHtml += '<li class="page-item text-center"><button class="page-link btn-xs border-0 bg-transparent text-dark text-decoration-none" onclick="changePage(' + i + ')">' + i + '</button></li>';
    }
    $('#pagination').html(paginationHtml);
    $('#pagination li').eq(currentPage - 1).addClass('active');
}

function changePage(newPage) {
    currentPage = newPage;
    updateList();
}

function selectAdvertisement(title, index) {
    $('#selectedAdvertisement').val(title);
    $('#selectedRecruitmentIndex').val(index);
    $('#advertisementModal').modal('hide');
}

$(document).on('mouseenter', '#pagination .page-link', function() {
    $(this).removeClass('text-decoration-none').addClass('text-decoration-underline');
}).on('mouseleave', '#pagination .page-link', function() {
    $(this).removeClass('text-decoration-underline').addClass('text-decoration-none');
});

function updateProductSelect(availableTimes) {
    var $productSelect = $('#productSelect');
    $productSelect.empty().append('<option value="">광고 노출 시간대 선택</option>');
    availableTimes.forEach(function(time) {
        $productSelect.append('<option value="' + time + '">' + time + '</option>');
    });
    $productSelect.prop('disabled', false);
}

function resetProductSelect() {
    var $productSelect = $('#productSelect');
    $productSelect.empty().append('<option value="">광고 노출 시간대 선택</option>');
    $productSelect.prop('disabled', true);
}

function submitForm() {
    var form = document.getElementById('advertisementForm');
    var formData = new FormData(form);
    const type = document.querySelector('h3').textContent.split(' ')[0];

    // AdvertisementRequestDto에 맞는 JSON 객체 생성
    var advertisementRequestDto = {
        recruitmentIndex: formData.get('recruitmentIndex'),
    };

    if (type === '프리미엄') {
        var startDate = new Date(formData.get('premiumStartDate'));
        advertisementRequestDto.startDate = startDate.toISOString().split('T')[0]; // YYYY-MM-DD 형식
        var endDate = new Date(startDate);
        endDate.setDate(endDate.getDate() + 3);
        advertisementRequestDto.endDate = endDate.toISOString().split('T')[0]; // YYYY-MM-DD 형식
        advertisementRequestDto.timeSlot = formData.get('displayTime');
        var displayTime = formData.get('displayTime');
        if(displayTime === '22:00~01:00'){
            advertisementRequestDto.adType = 'GP';
        }
        else if(displayTime === '01:00~04:00'){
            advertisementRequestDto.adType = 'P1';
        }
        else if(displayTime === '04:00~07:00'){
            advertisementRequestDto.adType = 'P2';
        }
        else{
            advertisementRequestDto.adType = 'P3';
        }
    }

    // JSON 객체를 문자열로 변환하여 FormData에 추가
    formData.append('advertisementRequestDto', new Blob([JSON.stringify(advertisementRequestDto)], {
        type: 'application/json'
    }));

    // 콘솔에 FormData 내용 출력
    console.log('FormData 내용:');
    for (var pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }

    // advertisementRequestDto 내용 출력
    console.log('advertisementRequestDto 내용:', advertisementRequestDto);

    // 여기에 서버로 데이터를 전송하는 코드를 추가할 수 있습니다.
}

function goBack() {
    window.history.back();
}