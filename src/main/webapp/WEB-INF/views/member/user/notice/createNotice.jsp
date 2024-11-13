<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>BringUp</title>

    <!-- 메타 태그 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Webestica.com">
    <meta name="description" content="Bootstrap 기반 뉴스, 매거진 및 블로그 테마">

    <!-- 다크 모드 -->
    <script src="/resources/script/common/darkmode/darkmode.js"></script>

    <!-- 파비콘 -->
    <link rel="shortcut icon" href="/resources/style/common/images/favicon.ico">

    <!-- 구글 폰트 -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;700&family=Rubik:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- 플러그인 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/font-awesome/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/bootstrap-icons/bootstrap-icons.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/apexcharts/css/apexcharts.css">
    <link rel="stylesheet" type="text/css" href="/resources/style/common/vendor/overlay-scrollbar/css/OverlayScrollbars.min.css">

    <!-- 테마 CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

    <!-- Bootstrap JS -->
    <script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

    <!-- 벤더 -->
    <script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
    <script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

    <!-- 테마 JS -->
    <script src="/resources/script/common/function/functions.js"></script>

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- 메인 스타일시트 -->
    <!-- <link rel="stylesheet" type="text/css" href="/resources/style/member/user/파일명.css"> -->

    <!--  JS -->
    <!-- <script src="/resources/script/member/user/파일명.js"></script> -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById('noticeForm').addEventListener('submit', function (e) {
                e.preventDefault();

                // localStorage에서 access token을 가져옵니다.
                const accessToken = localStorage.getItem('accessToken');

                // FormData 객체를 생성합니다.
                const formData = new FormData();

                // 파일이 포함된 경우, 파일 입력을 가져와서 추가합니다.
                const boardImage = document.getElementById('notice-img'); // 파일 입력 요소의 ID
                if (boardImage.files.length > 0) {
                    // 여러 파일이 있을 수 있으므로 모든 파일을 추가합니다.
                    for (let i = 0; i < boardImage.files.length; i++) {
                        formData.append('boardImage', boardImage.files[i]);
                    }
                }

                // boardRequestDto 객체 생성 (제목과 내용 포함)
                const boardRequestDto = {
                    title: document.getElementById('title').value.trim(),
                    content: document.getElementById('content').value.trim(),
                };

                // boardRequestDto를 JSON 문자열로 변환하여 FormData에 추가
                formData.append('boardRequestDto', JSON.stringify(boardRequestDto));

                // Fetch API로 POST 요청을 보냅니다.
                fetch('/member/notice/createPost', {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ` + accessToken,
                        // Content-Type을 설정할 필요는 없습니다. FormData가 자동으로 Content-Type을 설정합니다.
                    },
                    body: formData  // FormData를 body에 포함시킴
                }).then(response => {
                        if (!response.ok) {
                            // 서버 응답이 성공적이지 않은 경우 에러 메시지를 반환
                            return response.json().then(err => {
                                throw new Error(err.message || '게시글 등록에 실패했습니다.');
                            });
                        }
                        // 응답이 성공적이면 JSON 데이터 반환
                        return response.json();
                    }).then(data => {
                        // 성공적인 게시글 등록 후 사용자에게 알림
                        alert('게시글이 성공적으로 등록되었습니다.');
                        // 게시글 목록 페이지로 리디렉션
                        location.href = '/member/notice';
                    }).catch(error => {
                        // 오류 발생 시 콘솔에 에러 출력
                        console.error('Error : ', error);
                        // 사용자에게 오류 메시지 알림
                        alert(error.message || '게시글 등록에 실패했습니다. 다시 시도해주세요.');
                    });
            });
        });
    </script>

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
    <main class="flex-grow-1">
        <div class="container mx-auto my-4" style="max-width: 1260px;">
            <div class="card-header">
                <h4 class="card-title">게시글 등록</h4>
            </div>
            <div class="card-body m-4">
                <form action="/member/notice/createPost" method="post" id="noticeForm">
                    <div class="mb-3">
                        <label class="form-label" for="title">글 제목</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="notice-img" class="form-label"><i class="fas fa-image"></i> 이미지</label>
                        <input type="file" class="form-control" id="notice-img" name="notice-img" accept="image/*">
                    </div>
                    <div class="mb-3">
                        <label for="content" class="form-label"><i class="fas fa-briefcase"></i>글 내용</label>
                        <textarea class="form-control" style="height: 21rem; width: 100%" id="content" name="content" placeholder="글 내용을 입력하세요." required></textarea>
                    </div>
                    <button type="button" class="btn btn-secondary" onclick="location.href='/member/notice'">돌아가기</button>
                    <button type="submit" class="btn btn-primary" style="float: right;">완료</button>
                </form>
            </div>
        </div>
    </main>
</body>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
