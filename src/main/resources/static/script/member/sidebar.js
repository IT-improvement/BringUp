$(document).ready(function() {
    // Collapse icon toggle for portfolio
    $('#portfolioCollapse').on('show.bs.collapse', function () {
        $('#portfolioIcon').text('▲');  // 펼침 시 아이콘 변경
    }).on('hide.bs.collapse', function () {
        $('#portfolioIcon').text('▼');  // 접힘 시 아이콘 변경
    });

    // 메뉴 클릭 시 선택 상태 저장
    $('.ms-nav-link').on('click', function() {
        // 모든 링크에서 active 클래스 제거 후 클릭된 링크에만 추가
        $('.ms-nav-link').removeClass('active');
        $(this).addClass('active');

        // 클릭된 링크의 href 속성 값을 localStorage에 저장
        var selectedMenu = $(this).attr('href');
        localStorage.setItem('activeMenu', selectedMenu);
    });

    // 페이지 로드 시 localStorage에서 저장된 메뉴 상태 복원
    var activeMenu = localStorage.getItem('activeMenu');
    if (activeMenu) {
        // 해당 링크에 active 클래스 추가
        $('.ms-nav-link[href="' + activeMenu + '"]').addClass('active');

        // 포트폴리오 섹션에 속하는 항목이 선택된 경우 펼쳐지게 처리
        if ($('#portfolioCollapse').find('.ms-nav-link[href="' + activeMenu + '"]').length > 0) {
            $('#portfolioCollapse').collapse('show');  // 포트폴리오 섹션 열기
        }
    }
});
