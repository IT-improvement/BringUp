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
            list += '<button type="button" class="list-group-item list-group-item-action" onclick="selectAdvertisement(\'' + item.recruitmentTitle + '\', ' + item.recruitmentIndex + ')">' + item.recruitmentTitle + '</button>';
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

function submitForm() {
var form = document.getElementById('advertisementForm');
var formData = new FormData(form);

// AdvertisementRequestDto에 맞는 JSON 객체 생성
var advertisementRequestDto = {
    recruitmentIndex: formData.get('recruitmentIndex'),
    displayTime: formData.get('displayTime'), 
    type: document.querySelector('h3').textContent.split(' ')[0]
};

// FormData에서 기존 필드 제거
formData.delete('recruitmentIndex');
formData.delete('displayTime');

// JSON 객체를 문자열로 변환하여 FormData에 추가
formData.append('advertisementRequestDto', new Blob([JSON.stringify(advertisementRequestDto)], {
    type: 'application/json'
}));

// 콘솔에 FormData 내용 출력
console.log('FormData contents:');
for (var pair of formData.entries()) {
    console.log(pair[0] + ': ' + pair[1]);
}

// advertisementRequestDto 내용 출력
console.log('advertisementRequestDto contents:', advertisementRequestDto);

fetch('/com/advertisement/upload', {
    method: 'POST',
    headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
    },
    body: formData
})
.then(response => response.json())
.then(data => {
    if (data.code === 200) {
        alert('업로드 성공');
        window.location.href = '/company/product/management';
    } else {
        alert('업로드 실패: ' + data.message);
    }
})
.catch(error => {
    console.error('Error:', error);
    alert('업로드 중 오류가 발생했습니다.');
});
}

function goBack() {
window.history.back();
}