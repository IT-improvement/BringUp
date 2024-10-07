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
    <script src="/resources/script/company/updateAuth.js"></script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

    <jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1">
        <div class="container" style="max-width: 1260px;">
            <h2 class="mt-4 mb-3">기업 정보 수정</h2>
            <form id="updateForm" action="/com/user" method="post" class="mx-4">
                <div class="mb-3">
                    <h5 for="companyLogo" class="form-label mb-2">회사 로고</h5>
                    <div class="input-group">
                        <label for="companyLogo" class="form-control d-flex align-items-center cursor-pointer rounded-2 me-2">
                            <i class="bi bi-image me-2"></i>
                            <span id="companyLogo-file-chosen">파일을 선택하세요</span>
                        </label>
                        <input type="file" class="d-none" id="companyLogo" name="companyLogo">
						<input type="hidden" id="companyLogoHidden" name="companyLogoHidden">
                        <button type="button" class="btn btn-secondary rounded-2 d-flex justify-content-center align-items-center" id="companyLogo-viewImage" style="height: 40px; width: 40px;">
							<i class="bi bi-eye"></i>
						</button>
                    </div>
					<img id="companyLogo_img" alt="Company Logo" style="display: none;">
                </div>
                <div class="mb-3">
					<h5 for="companyImage" class="form-label mb-2">회사 대표 이미지</h5>
					<div id="companyImage-container">
						<div class="d-flex mb-2">
							<label for="companyImage0" class="form-control d-flex align-items-center cursor-pointer rounded-2 me-2" style="height: 40px;">
								<i class="bi bi-image me-2"></i>
								<span id="companyImage0-file-chosen">파일을 선택하세요</span>
							</label>
							<input type="file" class="d-none" id="companyImage0" name="companyImage[]">
							<input type="hidden" id="companyImage0Hidden" name="companyImageHidden[]" value="">
							<button type="button" class="btn btn-secondary rounded-2 d-flex justify-content-center align-items-center me-2" id="companyImage0-viewImage" style="height: 40px; width: 40px;">
								<i class="bi bi-eye"></i>
							</button>
							<button id="addImage" type="button" class="btn btn-primary rounded-2" style="height: 40px; width: 40px;">+</button>
						</div>
						<img id="companyImage0_img" src="" alt="Company Image 0" style="display: none;">
					</div>
				</div> 

                <div class="mb-3">
                    <button type="button" class="btn btn-primary" id="Image-update">이미지 수정</button>
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
					<textarea id="companyContent" name="companyContent" class="form-control form-control-lg" rows="3" required></textarea>
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
                    <textarea id="companyFinancialStatements" name="companyFinancialStatements" class="form-control"></textarea>
                </div>
                <div class="mb-3">
                    <h5 for="companySubsidiary" class="form-label mb-2">계열사</h5>
                    <textarea id="companySubsidiary" name="companySubsidiary" class="form-control"></textarea>
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
