document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('imageUpload').addEventListener('change', function(event) {
        const file = event.target.files[0];
        if (!file) return;

        const img = new Image();
        img.onload = function() {
            let valid = false;
            const productName = document.querySelector('h3').textContent.trim();

            if (productName.includes('프리미엄') && img.width === 875 && img.height === 500) {
                valid = true;
            } else if (productName.includes('메인') && img.width === 1228 && img.height === 320) {
                valid = true;
            } else if (productName.includes('배너') && img.width === 1228 && img.height === 80) {
                valid = true;
            }

            if (!valid) {
                alert('이미지 크기가 올바르지 않습니다. 올바른 크기를 업로드하세요.');
                event.target.value = ''; // 파일 선택 초기화
            }
        };

        img.src = URL.createObjectURL(file);
    });

    const advertisementInput = document.getElementById('selectedAdvertisement');
    const advertisementList = document.getElementById('advertisementList');
    const selectedAd = document.getElementById('selectedAd');

    advertisementInput.addEventListener('focus', function() {
        fetch('/com/recruitment/list')
            .then(response => response.json())
            .then(data => {
                advertisementList.innerHTML = ''; // 기존 리스트 초기화
                data.forEach(item => {
                    const listItem = document.createElement('li');
                    listItem.className = 'list-group-item';
                    listItem.textContent = item.title;
                    listItem.addEventListener('click', function() {
                        advertisementInput.value = item.title;
                        selectedAd.textContent = `선택된 공고: ${item.title}`;
                        $('#advertisementModal').modal('hide');
                    });
                    advertisementList.appendChild(listItem);
                });
                $('#advertisementModal').modal('show');
            })
            .catch(error => console.error('Error fetching advertisement list:', error));
    });
});
