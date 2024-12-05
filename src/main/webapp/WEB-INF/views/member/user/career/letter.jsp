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
		body {
			font-family: 'Nunito Sans', sans-serif;
		}

		.question-title {
			font-weight: bold;
			font-size: 1.2em;
			margin-top: 20px;
			position: relative;
		}

		.ai-generate-button {
			position: absolute;
			right: 0;
			margin-top: 1%;
			padding: 10px 20px;
			background-color: #ffffff;
			color: #0052ff;
			border: solid 1px;
			border-radius: 5px;
			font-size: 90%;
			cursor: pointer;
			transition: all 0.3s ease;
		}
		.ai-generate-button:hover {
			background-color: #c6eafa;

		}

		.question-textarea {
			width: 100%;
			height: 200px;
			margin-top: 40px;
			padding: 10px;
			font-size: 1em;
			border: 1px solid #ccc;
			border-radius: 5px;
		}

		.button-container {
			text-align: center;
			margin-top: 20px;
		}

		.save-button {
			background-color: #4CAF50;
			color: white;
			padding: 10px 20px;
			font-size: 1em;
			border: none;
			border-radius: 5px;
			cursor: pointer;
		}

		.loading-overlay {
			display: none;
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: rgba(0, 0, 0, 0.5);
			z-index: 9999;
			justify-content: center;
			align-items: center;
			color: #fff;
			font-size: 1.5em;
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
			<!-- 로딩 화면 -->
			<div class="loading-overlay" id="loadingOverlay">
				AI 문항 생성 중...
			</div>

			<div class="container">

				<div class="question-container">
					<div class="question-title">
						1. 학교 생활이나 사회경험 중 가장 어려웠거나 힘들었던 경험은 무엇이 있으며, 그 문제를 해결하기 위해 노력한 점이나 성공적으로 변화를 이루었던 경험을 작성해 주십시오.
						<button class="ai-generate-button" data-question="1">AI 문항 생성하기</button>
					</div>
					<textarea class="question-textarea" maxlength="800" placeholder="문항에 맞는 특정 단어를 입력하고 ai문항 작성하기를 눌러보세요!"></textarea>
				</div>

				<div class="question-container">
					<div class="question-title">
						2. 삶에 대한 본인의 가치관을 가장 잘 표현한 단어나 문장은 무엇이며 그 가치관이 형성되는데 가장 큰 영향을 끼친 일이나 인물이 있다면 설명해주십시오.
						<button class="ai-generate-button" data-question="2">AI 문항 생성하기</button>
					</div>
					<textarea class="question-textarea" maxlength="800" placeholder="문항에 맞는 특정 단어를 입력하고 ai문항 작성하기를 눌러보세요!"></textarea>
				</div>

				<div class="question-container">
					<div class="question-title">
						3. 지원한 분야와 관련된 본인의 역량(지식, skill, 경험 등)과 열정, 노력(프로젝트, 공모전, 대외활동, 논문 등)에 대해 기술해 주십시오.
						<button class="ai-generate-button" data-question="3">AI 문항 생성하기</button>
					</div>
					<textarea class="question-textarea" maxlength="800" placeholder="문항에 맞는 특정 단어를 입력하고 ai문항 작성하기를 눌러보세요!"></textarea>
				</div>
			</div>
			<div class="button-container">
				<button class="save-button">저장하기</button>
			</div>
		</main>
	</div>
</div>

<script>

			// 버튼 클릭 이벤트 추가
			document.querySelectorAll('.ai-generate-button').forEach(button => {
			button.addEventListener('click', function () {
				const questionNumber = this.getAttribute('data-question'); // 질문 번호 가져오기
				const textarea = this.parentNode.nextElementSibling; // 해당 textarea 가져오기
				const userInput = textarea.value; // 입력된 사용자 데이터
				const loadingOverlay = document.getElementById('loadingOverlay');
				loadingOverlay.style.display = 'flex'; // 로딩 화면 표시

				// API 호출
				fetch(`/openai/generate/` + questionNumber, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					body: JSON.stringify({messages: userInput}) // 요청 데이터
				})
						.then(response => {
							if (!response.ok) {
								throw new Error('AI 문항 생성에 실패했습니다.');
							}
							return response.json();
						})
						.then(data => {
							// 로딩 화면 숨기기
							loadingOverlay.style.display = 'none';

							console.log('API Response:', data); // 응답 데이터 확인
							if (data && data.data && data.data.response) {
								// 결과를 textarea에 표시
								textarea.value = data.data.response; // AI 생성 텍스트 표시
							} else {
								throw new Error('응답 데이터가 예상한 형식이 아닙니다.');
							}
						})
						.catch(error => {
							// 로딩 화면 숨기기
							loadingOverlay.style.display = 'none';

							console.error('Error:', error);
							alert('AI 문항 생성 중 오류가 발생했습니다.');
						});
			});
		});


			// 저장 버튼 로직 추가
			document.querySelector('.save-button').addEventListener('click', function () {
				const accessToken = localStorage.getItem("accessToken");

				const answers = [];
				document.querySelectorAll('.question-textarea').forEach((textarea, index) => {
					answers[index] = textarea.value; // 각 문항의 응답 수집

				});

				// 서버로 저장 요청
				fetch(`/portfolio/letter/insert`, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'Authorization': `Bearer ` + accessToken  // JWT 토큰 포함
					},
					body: JSON.stringify({
						answer1: answers[0],
						answer2: answers[1],
						answer3: answers[2]
					}) // 요청 데이터
				})
						.then(response => {
							if (!response.ok) {
								throw new Error('저장에 실패했습니다.');
							}
							return response.json();
						})
						.then(data => {
							alert('문항이 성공적으로 저장되었습니다.');
							console.log('저장 성공:', data);
						})
						.catch(error => {
							console.error('Error:', error);
							alert('저장 중 오류가 발생했습니다.');
						});
			});

</script>


<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true"/>
</body>
</html>
