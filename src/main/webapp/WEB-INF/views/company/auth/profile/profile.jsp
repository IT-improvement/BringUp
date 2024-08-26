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
			console.log("Access token: " + accessToken);
			const url = "/com/companyInfo/post"
			if (accessToken) {
				fetch(url, {
					method: 'GET',
					headers: {
						'Authorization': `Bearer `+ accessToken
					}
				})
				.then(response => response.json())
				.then(data => {
					console.log(data);
					// 데이터를 폼에 맞춰 입력
					document.getElementById('companyName').value = data.data.companyName;
					document.getElementById('representativeName').value = data.data.managerName;
					document.getElementById('address').value = data.data.companyAddress;
					document.getElementById('phoneNumber').value = data.data.companyPhonenumber;
					document.getElementById('homepage').value = data.data.companyHomepage;
					document.getElementById('industry').value = data.data.companyCategory;
					document.getElementById('establishDate').value = data.data.companyOpendate;
					document.getElementById('scale').value = data.data.companyScale;
					document.getElementById('employeeCount').value = data.data.companySize;
					document.getElementById('businessLicense').value = data.data.companyLicense;
					document.getElementById('representativeEmail').value = data.data.managerEmail;
					document.getElementById('representativePhoneNumber').value = data.data.managerPhonenumber;
					// 프로필 이미지 설정
					const images = data.data.companyLogo;
					console.log("회사 로고:", images);
					if (images) {
						sessionStorage.setItem('companyLogo', images);
						document.getElementById('profileImage').src = "/resources/logos/" + images;
					}
				})
				.catch(error => {
					console.error('Error:', error);
				});
			} else {
				console.log("토큰을 찾을 수 없습니다.");
			}
		});
	function uploadProfileImage() {
		const fileInput = document.getElementById('profileImageInput');
		const file = fileInput.files[0];
		if (file) {
			const formData = new FormData();
			formData.append('logo', file);
			fetch('/com/test', {
				method: 'POST',
				body: formData,
			})
			.then(response => response.json())
			.then(data => {
				console.log(data);
				// 업로드 성공 시 로고 이미지 경로를 업데이트하는 로직을 여기에 구현하세요.
				// 예시: document.getElementById('profileImage').src = data.logoUrl;
			})
			.catch(error => {
				console.error('Error uploading file:', error);
			});
		}
	}
	</script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body class="d-flex flex-column min-vh-100">

	<jsp:include page="/WEB-INF/views/company/header/company_header.jsp" flush="true" />

    <main class="flex-grow-1 m-4">
		<div class="company-profile container" style="max-width: 1260px;">
				<h2>회사 정보</h2>
				<div class="mb-3">
					<label for="profileImage" class="form-label">프로필 이미지</label>
					<div class="d-flex justify-content-start">
						<div class="rounded-circle overflow-hidden border border-secondary" style="width: 200px; height: 200px; position: relative;">
							<img src="" id="profileImage" alt="프로필 이미지" 
								 style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;"
								 onload="console.log('이미지의 src값:', this.src);">
						</div>
					</div>
				</div>
				<div class="mb-3">
					<label for="profileImageInput" class="form-label">프로필 이미지 업로드</label>
					<input type="file" class="form-control" id="profileImageInput" name="profile_image_input">
					<button type="button" class="btn btn-primary" onclick="uploadProfileImage()">업로드</button>
				</div>
				<div class="mb-3">
					<label for="companyName" class="form-label">회사 이름</label>
					<input type="text" class="form-control" id="companyName" name="company_name" readonly>
				</div>
				<div class="mb-3">
					<label for="representativeName" class="form-label">회사 대표</label>
					<input type="text" class="form-control" id="representativeName" name="representative_name" readonly>
				</div>
				<div class="mb-3">
					<label for="address" class="form-label">주소</label>
					<input type="text" class="form-control" id="address" name="address" readonly>
				</div>
				<div class="mb-3">
					<label for="phoneNumber" class="form-label">전화번호</label>
					<input type="text" class="form-control" id="phoneNumber" name="phone_number" readonly>
				</div>
				<div class="mb-3">
					<label for="homepage" class="form-label">홈페이지 주소</label>
					<input type="url" class="form-control" id="homepage" name="homepage" readonly>
				</div>
				<div class="mb-3">
					<label for="industry" class="form-label">업종</label>
					<input type="text" class="form-control" id="industry" name="industry" readonly>
				</div>
				<div class="mb-3">
					<label for="establishDate" class="form-label">설립일</label>
					<input type="date" class="form-control" id="establishDate" name="establish_date" readonly>
				</div>
				<div class="mb-3">
					<label for="scale" class="form-label">규모</label>
					<input type="text" class="form-control" id="scale" name="scale" readonly>
				</div>
				<div class="mb-3">
					<label for="employeeCount" class="form-label">직원 수</label>
					<input type="number" class="form-control" id="employeeCount" name="employee_count" readonly>
				</div>
				<div class="mb-3">
					<label for="businessLicense" class="form-label">사업자등록번호</label>
					<input type="text" class="form-control" id="businessLicense" name="business_license" readonly>
				</div>
				<div class="mb-3">
					<label for="representativeEmail" class="form-label">대표 이메일</label>
					<input type="email" class="form-control" id="representativeEmail" name="representative_email" readonly>
				</div>
				<div class="mb-3">
					<label for="representativePhoneNumber" class="form-label">대표 전화번호</label>
					<input type="text" class="form-control" id="representativePhoneNumber" name="representative_phone_number" readonly>
				</div>
		</div>
    </main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

    <!-- 맨 위로 -->
    <div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>