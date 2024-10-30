<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>BringUp</title>

	<!-- Meta Tags -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="author" content="Webestica.com">
	<meta name="description" content="Bootstrap 기본 뉴스, 매거진 및 블로그 테마">

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
		.question-title {
			font-weight: bold;
			font-size: 1.2em;
			margin-top: 20px;
		}

		.question-textarea {
			width: 100%;
			height: 200px;
			margin-top: 10px;
			padding: 10px;
			font-size: 1em;
			border: 1px solid #ccc;
			border-radius: 5px;
		}

		.question-container {
			margin-bottom: 30px;
		}

		.button-container {
			text-align: center;
			margin-top: 20px;
		}

		.action-button {
			padding: 10px 20px;
			font-size: 1em;
			margin: 10px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}

		.save-button {
			background-color: #4CAF50;
			color: white;
		}

		.ai-generate-button {
			background-color: #008CBA;
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
			<h2>자소서 문항 작성</h2>

			<!-- Question 1 -->
			<div class="question-container">
				<div class="question-title">1. 학교 생활이나 사회경험 중 가장 어려웠거나 힘들었던 경험은 무엇이 있으며, 그 문제를 해결하기 위해 노력한 점이나 성공적으로 변화를 이루었던 경험을 작성해 주십시오.</div>
				<textarea class="question-textarea" maxlength="800" placeholder="800자 작성"></textarea>
			</div>

			<!-- Question 2 -->
			<div class="question-container">
				<div class="question-title">2. 삶에 대한 본인의 가치관을 가장 잘 표현한 단어나 문장은 무엇이며 그 가치관이 형성되는데 가장 큰 영향을 끼친 일이나 인물이 있다면 설명해주십시오.</div>
				<textarea class="question-textarea" maxlength="800" placeholder="800자 작성"></textarea>
			</div>

			<!-- Question 3 -->
			<div class="question-container">
				<div class="question-title">3. 지원한 분야와 관련된 본인의 역량(지식, skill, 경험 등)과 열정, 노력(프로젝트, 공모전, 대외활동, 논문 등)에 대해 기술해 주십시오.</div>
				<textarea class="question-textarea" maxlength="800" placeholder="800자 작성"></textarea>
			</div>

			<!-- Buttons -->
			<div class="button-container">
				<button class="action-button save-button">저장하기</button>
				<button class="action-button ai-generate-button">AI 문항 생성하기</button>
			</div>
		</main>
	</div>
</div>
<script>
	$(document).ready(function() {
		const accessToken = localStorage.getItem("accessToken");
		fetchSaveLetter(accessToken);
		function fetchSaveLetter(accessToken) {
			fetch("/portfolio/letter/insert", {
				method: "POST",
				headers: {
					"Authorization": `Bearer ` + accessToken,
					"Content-Type": "application/json"
				}
			})
			alert("문서가 완전히 로드되었습니다!");
		}
	});
</script>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
