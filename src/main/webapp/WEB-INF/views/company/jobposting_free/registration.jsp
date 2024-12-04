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

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			document.getElementById('recruitmentForm').addEventListener('submit', function(e) {
					e.preventDefault();
					const accessToken = localStorage.getItem('accessToken');
					
					const formData = {
						recruitmentTitle: document.getElementById('recruitmentTitle').value,
						recruitmentType: document.getElementById('recruitmentType').value,
						category: document.getElementById('category').value,
						workDetail: document.getElementById('workDetail').value,
						skill: document.getElementById('skill').value,
						hospitality: document.getElementById('hospitality').value,
						career: document.getElementById('career').value,
						salary: document.getElementById('salary').value,
						period: document.getElementById('period').value
					}
					console.log(accessToken);
					console.log(formData);

					fetch('/com/recruitment/register', {
						method: 'POST',
						headers: {
							'Authorization': `Bearer `+ accessToken,
							'Content-Type': 'application/json',
						},
						body: JSON.stringify(formData)
					})
					.then(response => {
						if (!response.ok) {
							throw new Error('서버 응답이 실패했습니다');
						}
						return response.json();
					})
					.then(data => {
						alert('공고가 성공적으로 등록되었습니다.');
						location.href = '/company/jobpost/management';
					})
					.catch(error => {
						console.error('Error:', error);
						alert('공고 등록에 실패했습니다. 다시 시도해주세요.');
					});
				});
			});
		</script>

    <!-- 메인 JS -->
    <!-- <script src="/resources/script/company/main.js"></script> -->

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1">
		<div class="card mx-auto my-4" style="max-width: 1260px;">
			<div class="card-header">
				<h4 class="card-title">프로젝트 등록</h4>
			</div>
			<div class="card-body">
				<form action="/com/recruitment/register" method="post" id="recruitmentForm">
					<div class="d-flex align-items-center mb-1">
						<h2 id="projectTitle" class="fs-1 fw-bold">프로젝트 제목</h2>
					</div>
					<div class="row">
						<div class="col-md-8">
							<div class="mb-4">
								<h3>프로젝트 설명</h3>
								<textarea class="form-control" id="projectDescription" name="projectDescription" rows="5" required></textarea>
							</div>
							<div class="mb-4">
								<h3>예상 개발 기간</h3>
								<input type="text" class="form-control" id="expectedDuration" name="expectedDuration" required>
							</div>
							<div class="mb-4">
								<h3>필수 기술</h3>
								<input type="text" class="form-control" id="requiredSkills" name="requiredSkills" required>
							</div>
							<div class="mb-4">
								<h3>우대 기술</h3>
								<input type="text" class="form-control" id="preferredSkills" name="preferredSkills">
							</div>
							<div class="mb-4">
								<h3>근무 조건</h3>
								<textarea class="form-control" id="workConditions" name="workConditions" rows="5" required></textarea>
							</div>
							<div class="mb-4">
								<h3>예상 비용</h3>
								<input type="number" class="form-control" id="expectedCost" name="expectedCost" required>
							</div>
							<div class="mb-4">
								<h3>프로젝트 상태</h3>
								<input type="text" class="form-control" id="status" name="status" required>
							</div>
						</div>
						<div class="col-md-4">
							<div class="card border border-1 align-items-center" style="position: sticky; top: 140px; height: 280px; border-radius: 15px;">
								<div class="card-body d-flex flex-column justify-content-center w-75" style="height: 100%; position: relative;">
									<div class="d-flex flex-column align-items-center text-center mt-4">
										<div class="d-flex justify-content-between w-100 border-bottom mb-2">
											<h6 class="card-title mb-0">마감일</h6>
											<input type="date" class="form-control" id="projectPeriod" name="projectPeriod" required>
										</div>
										<div class="d-flex justify-content-between w-100 border-bottom mb-2">
											<h6 class="card-title mb-0">지원자 수</h6>
											<input type="number" class="form-control" id="projectApplicantCount" name="projectApplicantCount" required>
										</div>
										<div class="d-flex justify-content-between w-100 border-bottom mb-2">
											<h6 class="card-title mb-0">생성 날짜</h6>
											<input type="date" class="form-control" id="createDay" name="createDay" required>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<button type="button" class="btn btn-secondary" onclick="location.href='/company/jobpost/management'">돌아가기</button>
					<button type="submit" class="btn btn-primary" style="float: right;">등록</button>
				</form>
			</div>
		</div>
    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>