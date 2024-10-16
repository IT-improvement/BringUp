$(document).ready(function() {
    // Collapse icon toggle for portfolio
    $('#portfolioCollapse').on('show.bs.collapse', function () {
        $('#portfolioIcon').text('▲');  // 펼침 시 아이콘 변경
    }).on('hide.bs.collapse', function () {
        $('#portfolioIcon').text('▼');  // 접힘 시 아이콘 변경
    });

    // Add active class on click
    $('.ms-nav-link').on('click', function() {
        $('.ms-nav-link').removeClass('active');
        $(this).addClass('active');
    });
});
