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

    <style>
        /* 메인 컨테이너 크기 확장 */
        .container {
            max-width: 900px;
            margin: auto;
            padding: 20px;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 32px;
        }

        /* 폼 레이아웃 스타일 */
        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 16px;
        }

        textarea {
            resize: none;
        }

        button {
            display: block;
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            border: none;
            color: white;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            margin-top: 20px;
        }

        button:hover {
            background-color: #0056b3;
        }

        /* 게이지 바 스타일 */
        .rating-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 10px;
            cursor: pointer;
        }

        .bar-container {

            width: 100%;
            height: 20px;
            border-radius: 5px;
            position: relative;
            margin-right: 10px;
            cursor: pointer;
            display: flex;
        }

        .bar-segment {
            background-color: #e0e0e0;
            height: 100%;
            width: 18%;
            margin-right: 2%;
            transition: background-color 0.3s ease;
        }

        .bar-segment.active {
            background-color: #28a745;
        }

        .rating-value {
            width: 30px;
            text-align: right;
            font-size: 16px;
        }

        .rating-label {
            font-weight: bold;
            margin-bottom: 5px;
            display: inline-block;
        }
    </style>
</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<div class="container">
    <main class="flex-grow-1">
        <h1>기업 리뷰 작성</h1>
        <form id="createReviewForm">
            <div class="form-group">
                <label for="companyName">회사 이름</label>
                <input type="text" id="companyName" name="companyName" required>
            </div>
            <div class="form-group">
                <label for="companyReviewTitle">리뷰 제목</label>
                <input type="text" id="companyReviewTitle" name="companyReviewTitle" required>
            </div>

            <!-- 승진 기회 및 가능성 게이지 -->
            <div class="form-group">
                <span class="rating-label">승진 기회 및 가능성</span>
                <div class="rating-bar" onclick="updateBar('advancement', event)">
                    <div class="bar-container">
                        <div class="bar-segment" id="advancement-seg-1"></div>
                        <div class="bar-segment" id="advancement-seg-2"></div>
                        <div class="bar-segment" id="advancement-seg-3"></div>
                        <div class="bar-segment" id="advancement-seg-4"></div>
                        <div class="bar-segment" id="advancement-seg-5"></div>
                    </div>
                    <span id="advancement-value" class="rating-value">0/5</span>
                </div>
            </div>

            <!-- 복지 및 급여 게이지 -->
            <div class="form-group">
                <span class="rating-label">복지 및 급여</span>
                <div class="rating-bar" onclick="updateBar('benefit', event)">
                    <div class="bar-container">
                        <div class="bar-segment" id="benefit-seg-1"></div>
                        <div class="bar-segment" id="benefit-seg-2"></div>
                        <div class="bar-segment" id="benefit-seg-3"></div>
                        <div class="bar-segment" id="benefit-seg-4"></div>
                        <div class="bar-segment" id="benefit-seg-5"></div>
                    </div>
                    <span id="benefit-value" class="rating-value">0/5</span>
                </div>
            </div>

            <!-- 업무와 삶의 균형 게이지 -->
            <div class="form-group">
                <span class="rating-label">업무와 삶의 균형</span>
                <div class="rating-bar" onclick="updateBar('worklife', event)">
                    <div class="bar-container">
                        <div class="bar-segment" id="worklife-seg-1"></div>
                        <div class="bar-segment" id="worklife-seg-2"></div>
                        <div class="bar-segment" id="worklife-seg-3"></div>
                        <div class="bar-segment" id="worklife-seg-4"></div>
                        <div class="bar-segment" id="worklife-seg-5"></div>
                    </div>
                    <span id="worklife-value" class="rating-value">0/5</span>
                </div>
            </div>

            <!-- 사내 문화 게이지 -->
            <div class="form-group">
                <span class="rating-label">사내 문화</span>
                <div class="rating-bar" onclick="updateBar('culture', event)">
                    <div class="bar-container">
                        <div class="bar-segment" id="culture-seg-1"></div>
                        <div class="bar-segment" id="culture-seg-2"></div>
                        <div class="bar-segment" id="culture-seg-3"></div>
                        <div class="bar-segment" id="culture-seg-4"></div>
                        <div class="bar-segment" id="culture-seg-5"></div>
                    </div>
                    <span id="culture-value" class="rating-value">0/5</span>
                </div>
            </div>

            <!-- 경영진 게이지 -->
            <div class="form-group">
                <span class="rating-label">경영진</span>
                <div class="rating-bar" onclick="updateBar('management', event)">
                    <div class="bar-container">
                        <div class="bar-segment" id="management-seg-1"></div>
                        <div class="bar-segment" id="management-seg-2"></div>
                        <div class="bar-segment" id="management-seg-3"></div>
                        <div class="bar-segment" id="management-seg-4"></div>
                        <div class="bar-segment" id="management-seg-5"></div>
                    </div>
                    <span id="management-value" class="rating-value">0/5</span>
                </div>
            </div>

            <!-- 리뷰 내용 -->
            <div class="form-group">
                <label for="content">리뷰 내용</label>
                <textarea id="content" name="content" rows="5" required></textarea>
            </div>
            <button type="submit">리뷰 작성</button>
        </form>
    </main>
</div>
</body>

<script>
    // 각 게이지 클릭 시 게이지를 업데이트하는 함수
    function updateBar(field, event) {
        const container = event.currentTarget.querySelector('.bar-container');
        const segments = container.querySelectorAll('.bar-segment');
        const rect = container.getBoundingClientRect();
        const clickX = event.clientX - rect.left;
        const newRating = Math.min(Math.ceil((clickX / container.clientWidth) * 5), 5);  // 최대 5로 제한

        // 게이지 세그먼트를 업데이트
        segments.forEach((seg, index) => {
            if (index < newRating) {
                seg.classList.add('active');
            } else {
                seg.classList.remove('active');
            }
        });

        // 값을 전역 변수에 저장 및 표시 업데이트
        document.getElementById(field + '-value').textContent = newRating + '/5';
        window[field] = newRating;
    }

    document.addEventListener('DOMContentLoaded', function () {
        const accessToken = localStorage.getItem('accessToken');

        if (!accessToken) {
            alert('로그인이 필요합니다.');
            window.location.href = '/member/Login';
            return;
        }

        const companyId = "${companyId}";

        

        // 폼 제출 처리
        document.getElementById('createReviewForm').addEventListener('submit', function (e) {
            e.preventDefault();
            const formData = {
                companyName: document.getElementById('companyName').value,
                companyReviewTitle: document.getElementById('companyReviewTitle').value,
                advancement: window.advancement || 0,
                benefit: window.benefit || 0,
                workLife: window.worklife || 0,
                companyCulture: window.culture || 0,
                management: window.management || 0,
                content: document.getElementById('content').value
            };

            fetch('/member/m_create', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ` + accessToken,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`서버 오류 발생: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log(data);  // 받은 데이터 확인
                    // 여기서 code가 200인 경우 성공으로 처리
                    if (data.code === 200) {  // data.status 대신 data.code 확인
                        alert('리뷰가 성공적으로 작성되었습니다.');
                        window.location.href = '/member/companyReview';
                    } else {
                        alert('리뷰 작성에 실패했습니다. 서버에서 성공 응답을 받지 못했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert(`리뷰 작성 중 오류 발생: ${error.message}`);
                });
        });
    });
</script>
<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
