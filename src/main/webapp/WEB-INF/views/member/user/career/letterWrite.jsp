<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>BringUp</title>

    <!-- Meta Tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

    <!-- Dark Mode -->
    <script src="/resources/script/common/darkmode/darkmode.js"></script>

    <!-- Favicon -->
    <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

    <!-- Google Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- Plugins CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

    <!-- Theme CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>
        /* 자소서 작성 섹션 숨김 */
        #write-section {
            display: none;
        }
        #char-warning {
            color: red;
            display: none;
        }
        .modal-dialog-centered {
            display: flex;
            align-items: center;
            justify-content: center;
        }
        /* Dark mode header text color fix */
        header {
            color: white;
        }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <h3>자소서 작성</h3>
            <div class="input-group mb-3">
                <input type="text" class="form-control" value="박주혁님의 자소서" aria-label="자소서 검색" aria-describedby="button-search">
                <button class="btn btn-primary" type="button" id="button-search">확인</button>
            </div>

            <!-- 내용 Section -->
            <div id="intro-section">
                <h5>내용</h5>
                <div class="card p-4 bg-light text-center">
                    <div class="mb-2 text-muted">TIP. 자소서 처음 쓰기 막막하다면? 키워드만 넣고 초안을 생성해보세요!</div>
                    <button class="btn btn-outline-primary mb-3">자소서 초안 자동생성하기 &gt;</button>
                    <div><a href="#" id="write-manually" class="text-decoration-none">아니요, 직접 작성할게요. &gt;</a></div>
                </div>
            </div>

            <!-- 자소서 작성 섹션 (초기 숨김) -->
            <div id="write-section">
                <h5>내용</h5>
                <textarea id="letter-content" class="form-control" rows="8" placeholder="내용을 입력해주세요."></textarea>
                <div class="d-flex justify-content-between mt-2">
                    <small class="text-muted"><span id="char-count">0</span> / <span id="max-chars">500</span> 자 (공백포함)</small>
                    <button class="btn btn-outline-secondary" id="change-char-limit">글자수 변경</button>
                </div>
                <div id="char-warning">글자 수를 초과했습니다!</div>
            </div>

            <!-- 글자 수 변경 모달 -->
            <div class="modal fade" id="charLimitModal" tabindex="-1" aria-labelledby="charLimitModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="charLimitModalLabel">글자 수 변경</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>글자 수를 선택하세요:</p>
                            <button class="btn btn-primary set-char-limit" data-limit="250">250 자</button>
                            <button class="btn btn-primary set-char-limit" data-limit="500">500 자</button>
                            <button class="btn btn-primary set-char-limit" data-limit="1000">1000 자</button>
                        </div>
                        <div class="modal-footer">
                            <button type="button" id="confirm-limit" class="btn btn-secondary" data-bs-dismiss="modal">확인</button>
                        </div>
                    </div>
                </div>
            </div>

        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<script>
    $(document).ready(function(){
        var maxChars = 500; // 초기 글자수 제한
        var selectedLimit = maxChars; // 사용자가 선택한 글자수

        // '아니요, 직접 작성할게요' 클릭 시 동작
        $('#write-manually').on('click', function(e){
            e.preventDefault(); // 기본 링크 동작 막기
            $('#intro-section').hide(); // 초안 생성 섹션 숨기기
            $('#write-section').show(); // 자소서 작성 섹션 보이기
        });

        // 글자수 입력 감지
        $('#letter-content').on('input', function() {
            var content = $(this).val();
            var charCount = content.length;
            $('#char-count').text(charCount); // 현재 입력된 글자 수 표시

            if(charCount > maxChars) {
                $('#char-warning').show(); // 글자 수 초과 경고 표시
            } else {
                $('#char-warning').hide(); // 경고 숨기기
            }
        });

        // 글자수 변경 버튼 클릭 시 모달 표시
        $('#change-char-limit').on('click', function() {
            $('#charLimitModal').modal('show');
        });

        // 글자수 제한 설정 버튼 클릭 시
        $('.set-char-limit').on('click', function() {
            selectedLimit = $(this).data('limit'); // 선택한 글자수로 제한 변경
        });

        // '확인' 버튼 클릭 시 선택된 글자수 적용
        $('#confirm-limit').on('click', function() {
            maxChars = selectedLimit; // 선택한 글자수로 업데이트
            $('#max-chars').text(maxChars); // 최대 글자 수 업데이트
        });

        // 초기 다크모드 체크 및 헤더 색상 업데이트
        function setDynamicTextColor() {
            var theme = localStorage.getItem('theme');
            if (theme === 'dark') {
                $('header').css('color', 'white'); // 헤더 텍스트 색상 변경
            } else {
                $('header').css('color', '#000'); // 기본 텍스트 색상
            }
        }

        setDynamicTextColor();
    });
</script>

</body>
</html>
