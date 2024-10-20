<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>해시값 결과</title>
	<style>
		body {
			font-family: 'Noto Sans KR', sans-serif;
			background-color: #1a1a1a;
			margin: 0;
			padding: 0;
			color: #f0f0f0;
		}

		header {
			background-color: #00796B;
			padding: 20px;
			text-align: center;
			color: white;
			font-size: 24px;
			font-weight: bold;
		}

		main {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100vh;
			padding: 20px;
		}

		.result-container {
			background-color: #212121;
			padding: 30px;
			border-radius: 10px;
			max-width: 500px;
			width: 100%;
			box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.5);
		}

		.hash-value {
			background-color: #424242;
			padding: 15px;
			border-radius: 5px;
			color: #c5cae9;
			word-wrap: break-word;
			font-family: 'Courier New', Courier, monospace;
			margin-bottom: 20px;
		}

		.error-message {
			color: #e57373;
			font-weight: bold;
			text-align: center;
			margin-bottom: 20px;
		}

		.match-result {
			font-size: 18px;
			font-weight: bold;
			text-align: center;
			margin-bottom: 20px;
			color: #81C784;
		}

		button {
			width: 100%;
			padding: 12px;
			background-color: #00796B;
			border: none;
			border-radius: 5px;
			color: white;
			font-size: 16px;
			cursor: pointer;
			transition: background-color 0.3s;
		}

		button:hover {
			background-color: #004D40;
		}

	</style>
</head>
<body>

<header>파일 해시값 결과</header>

<main>
	<div class="result-container">
		<c:if test="${not empty calculatedHash}">
			<p>계산된 해시값:</p>
			<div class="hash-value">${calculatedHash}</div>
		</c:if>

		<c:if test="${not empty matchResult}">
			<p class="match-result">${matchResult}</p>
		</c:if>

		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>

		<form action="index.jsp">
			<button type="submit">다시 검사하기</button>
		</form>
	</div>
</main>

</body>
</html>


<%--
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
		/* 페이지 전체 스타일 */
		.main-header {
			margin-top: 20px;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.main-header h1 {
			font-size: 28px;
			font-weight: bold;
		}
		.main-header .description {
			font-size: 14px;
			color: #666;
		}
		.action-buttons {
			display: flex;
			align-items: center;
		}
		.action-buttons .btn {
			margin-left: 10px;
		}
		.search-container {
			margin-top: 20px;
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.search-container input {
			width: 400px;
			padding: 10px;
			border-radius: 5px;
			border: 1px solid #ddd;
		}

		/* 카드 리스트 스타일 */
		.card-list {
			margin-top: 30px;
			border-top: 1px solid #ddd;
		}
		.card-item {
			margin-top: 15px;
			padding: 20px;
			border-radius: 5px;
			border: 1px solid #ddd;
			background-color: #f9f9f9;
		}
		.card-item h5 {
			margin: 0;
			font-size: 18px;
		}
		.card-item p {
			margin: 10px 0;
			color: #555;
		}
		.card-item .action-buttons {
			display: flex;
			justify-content: flex-end;
		}
		.card-item .btn {
			margin-left: 10px;
		}

		/* 모달 창 스타일 */
		.modal-overlay {
			display: none;
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background: rgba(0, 0, 0, 0.5);
			z-index: 1000;
		}
		.modal-content {
			position: fixed;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			width: 600px;
			background-color: white;
			padding: 20px;
			border-radius: 10px;
			z-index: 1001;
		}
		.modal-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
		}
		.modal-header h5 {
			margin: 0;
		}
		.modal-body {
			margin-top: 20px;
		}
		.modal-footer {
			display: flex;
			justify-content: flex-end;
			margin-top: 20px;
		}
	</style>

	<script>
		$(document).ready(function() {
			// 모달 열기
			$(".btn-primary").click(function() {
				$(".modal-overlay").show();
			});

			// 모달 닫기
			$(".btn-close").click(function() {
				$(".modal-overlay").hide();
			});

			// 저장하기 버튼 클릭 시 처리
			$(".btn-save").click(function() {
				// 저장 로직을 여기에 추가
				alert("저장되었습니다.");
				$(".modal-overlay").hide();
			});
		});
	</script>
</head>
<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
	<!-- Sidebar -->
	<jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

	<div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
		<main class="flex-grow-1">
			<!-- 메인 헤더 -->
			<div class="main-header">
				<div>
					<h1>자소서 관리</h1>
					<p class="description">초안 자동생성, 맞춤법 및 표절검사, 마무리 교정까지 해주는</p>
				</div>
				<div class="action-buttons">
					<button class="btn btn-secondary">파일 등록</button>
					<button class="btn btn-primary">자소서 작성하기</button>
				</div>
			</div>

			<!-- 검색 바 -->
			<div class="search-container">
				<input type="text" placeholder="자기소개서 제목, 문항, 내용으로 검색해보세요">
			</div>

			<!-- 카드 리스트 -->
			<div class="card-list">
				<!-- 카드 항목 1 -->
				<div class="card-item">
					<h5>박주혁님의 자소서</h5>
					<p>[대학생의 DBMS 경험으로 미래를 열다] DBMS에 대해 관심을 가지고 3학년 때 여러 개의 프로젝트를 경험해 보았습니다...</p>
					<div class="action-buttons">
						<button class="btn btn-outline-primary">삭제하기</button>
						<button class="btn btn-outline-primary">수정하기</button>
					</div>
				</div>
				<!-- 추가 카드 항목 필요 시 여기에 작성 -->
			</div>
		</main>
	</div>
</div>

<!-- 모달 팝업 -->
<div class="modal-overlay">
	<div class="modal-content">
		<div class="modal-header">
			<h5>자소서 작성</h5>
			<button class="btn-close">&times;</button>
		</div>
		<div class="modal-body">
			<p>TIP: 자소서 처음 쓰기 막막하다면? 키워드만 넣고 초안을 생성해보세요!</p>
			<button class="btn btn-outline-primary">자소서 초안 자동생성하기</button>
			<br/><br/>
			<button class="btn btn-outline-secondary">아니요, 직접 작성할게요</button>
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary btn-save">저장하기</button>
		</div>
	</div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
--%>
