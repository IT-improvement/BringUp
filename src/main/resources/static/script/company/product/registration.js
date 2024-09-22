document.addEventListener('DOMContentLoaded', function() {
    // 모달 창에서 내 공고 목록을 불러오는 함수
    async function loadAdvertisements() {
        try {
            const response = await fetch('/com/recruitment/list', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
                }
            });
            const responseData = await response.json();
            console.log('서버 응답:', responseData);

            if (responseData && responseData.data && Array.isArray(responseData.data)) {
                document.getElementById('advertisement-list').innerHTML = ''; // 기존 목록 초기화
                responseData.data.forEach(recruitment => {
                    document.getElementById('advertisement-list').innerHTML += `<div class="mb-3 d-flex justify-content-center"><button type="button" class="btn btn-outline-primary w-100" onclick="selectAdvertisement('${recruitment.id}', '${recruitment.recruitmentTitle}')">${recruitment.recruitmentTitle}</button></div>`;
                });
            } else {

                console.error('유효한 데이터가 없습니다.');
                document.getElementById('advertisement-list').innerHTML = '<p>공고 목록을 불러올 수 없습니다.</p>';
            }
        } catch (error) {
            console.error('광고 목록 불러오기 실패:', error);
            document.getElementById('advertisement-list').innerHTML = '<p>오류가 발생했습니다. 다시 시도해 주세요.</p>';
        }
    }

    // 모달 창이 열릴 때 내 공고 목록을 불러오는 이벤트
    document.getElementById('advertisementModal').addEventListener('shown.bs.modal', function() {
        loadAdvertisements();
    });

    // 선택된 광고를 입력박스에 표시하고 모달을 닫는 함수
    window.selectAdvertisement = function(id, title) {
        document.getElementById('selectedAdvertisement').value = title;
        const modal = document.getElementById('advertisementModal');
        const bootstrapModal = bootstrap.Modal.getInstance(modal);
        bootstrapModal.hide();
        
        // 모달이 완전히 닫힌 후 배경을 제거합니다.
        modal.addEventListener('hidden.bs.modal', function (e) {
            removeModalBackdrop();
        }, { once: true });
    };

    // 모달이 닫힐 때마다 배경을 제거하는 이벤트 리스너를 추가합니다.
    document.getElementById('advertisementModal').addEventListener('hidden.bs.modal', function (e) {
        removeModalBackdrop();
    });

    // 모달 배경을 제거하는 함수
    function removeModalBackdrop() {
        const backdrop = document.querySelector('.modal-backdrop');
        if (backdrop) {
            backdrop.remove();
        }
        document.body.classList.remove('modal-open');
        document.body.style.paddingRight = '';
    }
});