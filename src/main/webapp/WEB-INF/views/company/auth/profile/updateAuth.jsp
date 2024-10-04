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

    <script src="/resources/script/common/notification/notification.js"></script>
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

    <!-- 메인 JS -->
    <!-- <script src="/resources/script/company/main.js"></script> -->
	<script>
		document.addEventListener('DOMContentLoaded', function() {
			const accessToken = localStorage.getItem("accessToken");
			const url = "/com/companyInfo/post"
			if (accessToken) {
				fetch(url, {
					method: 'GET',
					headers: {
						'Authorization': `Bearer ` + accessToken
					}
				})
				.then(response => response.json())
				.then(data => {
					console.log(data);
					console.log(data.data);

					// 폼 필드 채우기
					document.getElementById('companyName').value = data.data.companyName;
					document.getElementById('representativeName').value = data.data.masterName;
					document.getElementById('address').value = data.data.companyAddress;
					document.getElementById('homepage').value = data.data.companyHomepage;
					document.getElementById('industry').value = data.data.companyCategory;
					document.getElementById('employeeCount').value = data.data.companySize;
					document.getElementById('representativeEmail').value = data.data.managerEmail;
					document.getElementById('phoneNumber').value = data.data.companyPhonenumber;
					document.getElementById('companyContent').value = data.data.companyContent;
					document.getElementById('companyWelfare').value = data.data.companyWelfare;
					document.getElementById('companyHistory').value = data.data.companyHistory;
					document.getElementById('companyVision').value = data.data.companyVision;
					document.getElementById('companyFinancialStatements').value = data.data.companyFinancialStatements || '';
					document.getElementById('companySubsidiary').value = data.data.companySubsidiary || '';
					
					// 이미지 파일 이름 표시 (실제 파일 업로드는 아님)
					if (data.data.companyLogo) {
						document.querySelector('#companyLogo #file-chosen').textContent = data.data.companyLogo.split('/').pop();
					}
					if (data.data.companyImg) {
						document.querySelector('#companyImage #file-chosen').textContent = data.data.companyImg.split(',')[0].split('/').pop();
					}
				})
			}
		});
	</script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

    <jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1">
        <div class="container" style="max-width: 1260px;">
            <h2 class="mt-4 mb-3">기업 정보 수정</h2>
            <form action="/company/auth/updateAuth" method="post" class="mx-4">
                <div class="mb-3">
                    <h5 for="companyLogo" class="form-label mb-2">회사 로고</h5>
                    <div class="input-group">
                        <label for="companyLogo" class="form-control d-flex align-items-center cursor-pointer rounded-2">
                            <i class="bi bi-image me-2"></i>
                            <span id="file-chosen">파일을 선택하세요</span>
                        </label>
                        <input type="file" class="d-none" id="companyLogo" name="companyLogo" accept="image/*">
                    </div>
                </div>
                <div class="mb-3">
					<h5 for="companyImage" class="form-label mb-2">회사 대표 이미지</h5>
					<div class="input-group">
                        <label for="companyImage" class="form-control d-flex align-items-center cursor-pointer rounded-2">
                            <i class="bi bi-image me-2"></i>
                            <span id="file-chosen">파일을 선택하세요</span>
                        </label>
                        <input type="file" class="d-none" id="companyImage" name="companyImage" accept="image/*">
						<button type="button" class="btn btn-danger rounded-2 ms-2">-</button>
                    </div>
				</div> 
                <div class="mb-3">
                    <h5 for="companyName" class="form-label mb-2">회사 이름</h5>
                    <input type="text" id="companyName" name="companyName" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="representativeName" class="form-label mb-2">대표 이름</h5>
                    <input type="text" id="representativeName" name="representativeName" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="address" class="form-label mb-2">주소</h5>
                    <input type="text" id="address" name="address" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="homepage" class="form-label mb-2">홈페이지 주소</h5>
                    <input type="url" id="homepage" name="homepage" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="industry" class="form-label mb-2">업종</h5>
                    <input type="text" id="industry" name="industry" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="employeeCount" class="form-label mb-2">직원 수</h5>
                    <input type="number" id="employeeCount" name="employeeCount" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="representativeEmail" class="form-label mb-2">대표 이메일</h5>
                    <input type="email" id="representativeEmail" name="representativeEmail" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="phoneNumber" class="form-label mb-2">전화번호</h5>
                    <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" required>
                </div>
                <div class="mb-3">
                    <h5 for="companyContent" class="form-label mb-2">회사 소개</h5>
                    <textarea id="companyContent" name="companyContent" class="form-control" required></textarea>
                </div>
                <div class="mb-3">
                    <h5 for="companyWelfare" class="form-label mb-2">복지</h5>
                    <textarea id="companyWelfare" name="companyWelfare" class="form-control" required></textarea>
                </div>
                <div class="mb-3">
                    <h5 for="companyHistory" class="form-label mb-2">연혁</h5>
                    <textarea id="companyHistory" name="companyHistory" class="form-control" required></textarea>
                </div>
                <div class="mb-3">
                    <h5 for="companyVision" class="form-label mb-2">회사 비전</h5>
                    <textarea id="companyVision" name="companyVision" class="form-control" required></textarea>
                </div>
                <div class="mb-3">
                    <h5 for="companyFinancialStatements" class="form-label mb-2">재무제표</h5>
                    <textarea id="companyFinancialStatements" name="companyFinancialStatements" class="form-control" required></textarea>
                </div>
                <div class="mb-3">
                    <h5 for="companySubsidiary" class="form-label mb-2">계열사</h5>
                    <textarea id="companySubsidiary" name="companySubsidiary" class="form-control" required></textarea>
                </div>
                <div class="d-flex justify-content-between">
                    <button type="button" class="btn btn-secondary">취소</button>
                    <button type="submit" class="btn btn-primary">수정 완료</button>
                </div>
            </form>
        </div>
    </main>

    <jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>