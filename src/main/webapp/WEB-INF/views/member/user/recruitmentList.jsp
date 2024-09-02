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
		<link rel="stylesheet" type="text/css" href="/resources/style/member/recruitment.css">

		<!-- 테마 CSS -->
		<link rel="stylesheet" type="text/css" href="/resources/style/common/css/style.css">

		<!-- Bootstrap JS -->
		<script src="/resources/style/common/vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>

		<!-- 벤더 -->
		<script src="/resources/style/common/vendor/apexcharts/js/apexcharts.min.js"></script>
		<script src="/resources/style/common/vendor/overlay-scrollbar/js/OverlayScrollbars.min.js"></script>

		<!-- 테마 JS -->
		<script src="/resources/script/common/function/functions.js"></script>

		<!-- jQuery -->
		<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	</head>

	<body class="d-flex flex-column min-vh-100">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

	<main class="flex-grow-1">
		<!-- 필터 바 섹션 -->
		<section class="ur_search-bar-container">
			<div class="ur_search-bar">
				<div class="ur_search-input">
					<input type="text" placeholder="회사/채용 공고로 검색" />
					<button class="ur_search-button">검색</button>
				</div>
				<div class="ur_filter-group">
					<!-- 필터 요소들 -->
					<div class="ur_filter-item" id="job-category-filter">
						<i class="bi bi-briefcase"></i>
						<span>직군</span>
						<i class="bi bi-chevron-down"></i>
						<div class="ur_filter-dropdown" id="job-category-dropdown">
							<div class="ur_dropdown-section job-section">
								<h4>직군</h4>
								<ul>
									<li data-job="전체">전체</li>
									<li data-job="개발">개발</li>
									<li data-job="게임개발">게임개발</li>
									<li data-job="디자인">디자인</li>
									<li data-job="기획">기획</li>
									<li data-job="마케팅">마케팅</li>
									<li data-job="경영/인사">경영/인사</li>
									<li data-job="영업">영업</li>
								</ul>
							</div>
							<div class="ur_dropdown-section duty-section">
								<h4>직무</h4>
								<ul>
									<li>
										<input type="checkbox" id="duty-checkbox1">
										<label for="duty-checkbox1">전체</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox2">
										<label for="duty-checkbox2">백엔드/서버 개발자</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox3">
										<label for="duty-checkbox3">프론트엔드/웹퍼블리셔</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox4">
										<label for="duty-checkbox4">SW 엔지니어</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox5">
										<label for="duty-checkbox5">안드로이드 개발자</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox6">
										<label for="duty-checkbox6">iOS 개발자</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox7">
										<label for="duty-checkbox7">크로스플랫폼 앱 개발자</label>
									</li>
									<li>
										<input type="checkbox" id="duty-checkbox8">
										<label for="duty-checkbox8">데이터 엔지니어</label>
									</li>
								</ul>
							</div>
						</div>
					</div>

					<!-- 경력 · 지역 필터 -->
					<div class="ur_filter-item" id="experience-location-filter">
						<i class="bi bi-geo-alt"></i>
						<span>경력 · 지역</span>
						<i class="bi bi-chevron-down"></i>
						<div class="ur_filter-dropdown" id="experience-location-dropdown">
							<div class="ur_dropdown-section experience-section">
								<h4>경력</h4>
								<ul>
									<li>
										<input type="checkbox" id="exp-checkbox1">
										<label for="exp-checkbox1">전체</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox2">
										<label for="exp-checkbox2">경력무관</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox3">
										<label for="exp-checkbox3">인턴</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox4">
										<label for="exp-checkbox4">신입 (1년 이하)</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox5">
										<label for="exp-checkbox5">주니어 (1~3년)</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox6">
										<label for="exp-checkbox6">미들 (4~8년)</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox7">
										<label for="exp-checkbox7">시니어 (9년 이상)</label>
									</li>
									<li>
										<input type="checkbox" id="exp-checkbox8">
										<label for="exp-checkbox8">Lead 레벨</label>
									</li>
								</ul>
							</div>
							<div class="ur_dropdown-section location-section">
								<h4>지역</h4>
								<ul>
									<li>
										<input type="checkbox" id="loc-checkbox1">
										<label for="loc-checkbox1">전체</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox2">
										<label for="loc-checkbox2">서울</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox3">
										<label for="loc-checkbox3">강남</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox4">
										<label for="loc-checkbox4">마포</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox5">
										<label for="loc-checkbox5">구로/가산</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox6">
										<label for="loc-checkbox6">경기</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox7">
										<label for="loc-checkbox7">판교/분당</label>
									</li>
									<li>
										<input type="checkbox" id="loc-checkbox8">
										<label for="loc-checkbox8">그 외</label>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="ur_tech-stack">
					<i class="bi bi-code-slash"></i>
					기술 스택
					<input type="text" placeholder="직무 스킬을 검색해보세요" />
				</div>
				<div class="ur_tech-tags">
					<span>Python</span>
					<span>JavaScript</span>
					<span>React</span>
					<span>Next.js</span>
					<span>SQL</span>
					<span>Slack</span>
					<span>JIRA</span>
					<span>Confluence</span>
				</div>
			</div>
		</section>

		<!-- 상단 섹션: 강조된 공고 -->
		<section class="container mt-5">
			<h2 class="section-title">이 공고 놓치지 마세요! 요즘 가장 핫한 공고 ⭐⭐⭐</h2>
			<div id="highlighted-recruitments" class="list-group recruitment-list">
				<!-- 강조된 공고가 여기에 추가됩니다 -->
			</div>
		</section>

		<!-- 전체 채용 정보 섹션 -->
		<section class="container mt-5">
			<h2 class="section-title">전체 채용 정보</h2>
			<div id="all-recruitments" class="list-group recruitment-list">
				<!-- 전체 채용 정보가 여기에 추가됩니다 -->
			</div>
		</section>
	</main>

	<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

	<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			const accessToken = localStorage.getItem("accessToken");
			console.log("Access token: " + accessToken);
			const url = "/recruitment/list"; // 공고 리스트를 가져오는 API 엔드포인트

			if (accessToken) {
				fetch(url, {
					method: 'GET',
					headers: {
						'Authorization': `Bearer ` + accessToken,
						'Content-Type': 'application/json' // JSON 형식으로 요청
					}
				})
						.then(response => {
							if (!response.ok) {
								throw new Error('Network response was not ok');
							}
							return response.json();
						})
						.then(data => {
							console.log("Received recruitment data:", data);
							if (Array.isArray(data)) {
								renderRecruitmentList(data);
							} else if (Array.isArray(data.data)) {
								renderRecruitmentList(data.data);
							} else {
								console.error("Unexpected data format:", data);
							}
						})
						.catch(error => {
							console.error('Error fetching recruitment data:', error);
						});
			} else {
				console.log("Access token not found.");
			}
		});

		function renderRecruitmentList(recruitments) {
			let highlightedContainer = document.getElementById('highlighted-recruitments');
			let allContainer = document.getElementById('all-recruitments');

			recruitments.forEach(function(recruitment) {
				let recruitmentTitle = recruitment.recruitmentTitle || '제목 없음';
				let companyId = recruitment.companyId || '정보 없음';
				let category = recruitment.category || '정보 없음';
				let skill = recruitment.skill || '정보 없음';
				let recruitmentType = recruitment.recruitmentType || '정보 없음';
				let period = recruitment.period || '정보 없음';
				let status = recruitment.status || '정보 없음';

				const recruitmentItem = document.createElement('div');
				recruitmentItem.className = 'list-group-item recruitment-item';

				const titleElement = document.createElement('h5');
				titleElement.className = 'mb-1';
				titleElement.textContent = recruitmentTitle;

				const companyIdElement = document.createElement('p');
				companyIdElement.className = 'mb-1';
				companyIdElement.textContent = '회사 ID: ' + companyId;

				const categoryElement = document.createElement('p');
				categoryElement.className = 'mb-1';
				categoryElement.textContent = '카테고리: ' + category;

				const skillElement = document.createElement('p');
				skillElement.className = 'mb-1';
				skillElement.textContent = '기술 스택: ' + skill;

				const recruitmentTypeElement = document.createElement('p');
				recruitmentTypeElement.className = 'mb-1';
				recruitmentTypeElement.textContent = '고용 형태: ' + recruitmentType;

				const periodElement = document.createElement('p');
				periodElement.className = 'mb-1';
				periodElement.textContent = '기간: ' + period;

				const statusElement = document.createElement('small');
				statusElement.className = 'text-muted';
				statusElement.textContent = '상태: ' + status;

				// 상세보기 링크 생성
				const linkElement = document.createElement('a');
				linkElement.href = `/recruitment/details/${recruitment.recruitmentIndex}`;
				linkElement.className = 'btn btn-outline-primary';
				linkElement.textContent = '상세 내용';

				// 요소를 조립하여 recruitmentItem에 추가
				const contentDiv = document.createElement('div');
				contentDiv.className = 'd-flex justify-content-between';
				const leftDiv = document.createElement('div');
				const rightDiv = document.createElement('div');
				rightDiv.className = 'align-self-center';

				leftDiv.appendChild(titleElement);
				leftDiv.appendChild(companyIdElement);
				leftDiv.appendChild(categoryElement);
				leftDiv.appendChild(skillElement);
				leftDiv.appendChild(recruitmentTypeElement);
				leftDiv.appendChild(periodElement);
				leftDiv.appendChild(statusElement);

				rightDiv.appendChild(linkElement);
				contentDiv.appendChild(leftDiv);
				contentDiv.appendChild(rightDiv);

				recruitmentItem.appendChild(contentDiv);

				if (status === '진행중') {
					highlightedContainer.appendChild(recruitmentItem);
				} else {
					allContainer.appendChild(recruitmentItem);
				}
			});
		}


		document.addEventListener('DOMContentLoaded', function() {
			// 필터 아이템 클릭 시 드롭다운 열기/닫기 처리
			document.querySelectorAll('.ur_filter-item').forEach(function(item) {
				const dropdown = item.querySelector('.ur_filter-dropdown');

				item.addEventListener('click', function(event) {
					event.stopPropagation(); // 클릭 이벤트 전파 방지

					const isOpen = item.classList.contains('open');
					if (isOpen) {
						dropdown.style.display = 'none';
						item.classList.remove('open');
					} else {
						// 모든 드롭다운 닫기
						document.querySelectorAll('.ur_filter-item').forEach(function(otherItem) {
							const otherDropdown = otherItem.querySelector('.ur_filter-dropdown');
							otherDropdown.style.display = 'none';
							otherItem.classList.remove('open');
						});

						dropdown.style.display = 'flex';
						item.classList.add('open');
					}
				});

				dropdown.addEventListener('click', function(event) {
					event.stopPropagation();
				});

				document.addEventListener('click', function() {
					dropdown.style.display = 'none';
					item.classList.remove('open');
				});
			});
			const jobList = document.querySelectorAll('.ur_filter-dropdown .job-section ul li');
			let dutyList = document.getElementById('duty-list');

			if (!dutyList) {
				// duty-list 요소가 존재하지 않는 경우 생성
				dutyList = document.createElement('ul');
				dutyList.id = 'duty-list';
				document.body.appendChild(dutyList);  // 필요에 따라 적절한 위치로 이동
				console.log('Created "duty-list" element');
			}

			const duties = {
				'전체': ['전체'],
				'개발': ['백엔드/서버 개발자', '프론트엔드/웹퍼블리셔', 'SW 엔지니어', '안드로이드 개발자', 'iOS 개발자', '크로스플랫폼 앱 개발자', '데이터 엔지니어'],
				'게임개발': ['게임 서버 개발자', '게임 클라이언트 개발자', '게임 기획자', '게임 그래픽 디자이너', '게임 아티스트', '모바일 게임 개발자', '게임 운영자'],
				'디자인': ['프로덕트 디자이너', '웹/앱 디자이너', '그래픽 디자이너', 'BI/BX 디자이너', '광고 디자이너', '영상/모션 디자이너', '운영 디자이너'],
				'기획': ['서비스 기획자', 'PO/PM', '비즈니스 분석가', '사업개발/기획자', '전략 기획자', '해외 사업개발/기획자', '상품 기획자/MD'],
				'마케팅': ['퍼포먼스 마케터', '콘텐츠 마케터', '디지털 마케터', '마케팅 기획자', '브랜드 마케터', '광고 기획자', 'CRM 전문가'],
				'경영/인사': ['경영지원', '회계/경리', '조직관리', '정보보호 담당자', '인사/평가', '교육', '채용담당자'],
				'영업': ['기업영업', '영업 관리자', '기술영업', '솔루션 컨설턴트', '세일즈']
			};

			jobList.forEach(function(jobItem) {
				jobItem.addEventListener('click', function() {
					const selectedJob = this.dataset.job;
					const relatedDuties = duties[selectedJob] || [];

					// 직무 리스트를 업데이트
					dutyList.innerHTML = '';
					relatedDuties.forEach(function(duty) {
						const dutyItem = document.createElement('li');
						dutyItem.textContent = duty;
						dutyList.appendChild(dutyItem);
					});

					// 직군 이름을 필터에 표시
					const filterLabel = document.querySelector('#job-category-filter > span');
					filterLabel.textContent = selectedJob;

					updateSelectedTags(filterLabel, selectedJob, relatedDuties);
				});
			});

			function updateSelectedTags(filterLabel, selectedJob, relatedDuties) {
				let tagContainer = document.querySelector('.selected-tags');
				if (!tagContainer) {
					tagContainer = document.createElement('div');
					tagContainer.className = 'selected-tags';
					filterLabel.parentElement.appendChild(tagContainer);
				}

				tagContainer.innerHTML = '';

				const jobTag = document.createElement('span');
				jobTag.className = 'tag';
				jobTag.textContent = selectedJob;
				tagContainer.appendChild(jobTag);

				if (Array.isArray(relatedDuties)) {
					relatedDuties.forEach(function(duty) {
						const dutyTag = document.createElement('span');
						dutyTag.className = 'tag';
						dutyTag.textContent = duty;
						tagContainer.appendChild(dutyTag);
					});
				}
			}

			// 경력 및 거주지 체크박스 처리
			document.querySelectorAll('.ur_filter-dropdown input[type="checkbox"]').forEach(function(checkbox) {
				checkbox.addEventListener('change', function(event) {
					const dropdown = this.closest('.ur_filter-dropdown');
					const filterItem = dropdown.closest('.ur_filter-item');
					const isSelectAll = this.parentElement.textContent.trim() === '전체';

					if (isSelectAll) {
						const relatedCheckboxes = this.closest('ul').querySelectorAll('input[type="checkbox"]:not(:first-child)');
						const isChecked = this.checked;
						relatedCheckboxes.forEach(cb => cb.checked = isChecked);
					} else {
						const allCheckbox = this.closest('ul').querySelector('input[type="checkbox"]:first-child');
						const relatedCheckboxes = this.closest('ul').querySelectorAll('input[type="checkbox"]:not(:first-child)');
						allCheckbox.checked = Array.from(relatedCheckboxes).every(cb => cb.checked);
					}

					updateFilterText(filterItem);
				});
			});

			function updateFilterText(filterItem) {
				const checkboxes = filterItem.querySelectorAll('.ur_filter-dropdown input[type="checkbox"]:checked');
				const selectedValues = Array.from(checkboxes).filter(cb => cb.parentElement.textContent.trim() !== '전체').map(function(checkbox) {
					return checkbox.closest('li').textContent.trim();
				});

				const spanElement = filterItem.querySelector('span');
				if (spanElement) {
					if (selectedValues.length > 0) {
						spanElement.textContent = selectedValues.join(', ');
					} else {
						if (filterItem.id === 'job-category-filter') {
							spanElement.textContent = '직군 · 직무';
						} else if (filterItem.id === 'experience-location-filter') {
							spanElement.textContent = '경력 · 지역';
						}
					}
				}

				updateSelectedTags(filterItem, selectedValues);
			}

			function updateSelectedTags(filterItem, selectedValues) {
				let tagContainer = filterItem.querySelector('.selected-tags');
				if (!tagContainer) {
					tagContainer = document.createElement('div');
					tagContainer.className = 'selected-tags';
					filterItem.appendChild(tagContainer);
				}

				tagContainer.innerHTML = '';

				if (Array.isArray(selectedValues)) {
					selectedValues.forEach(function(value) {
						const tagElement = document.createElement('span');
						tagElement.className = 'tag';
						tagElement.textContent = value;
						tagContainer.appendChild(tagElement);
					});
				}
			}
		});
	</script>
	</body>
	</html>
