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
										<span>직무 · 직군</span>
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
									<input type="text" id="tech-search" placeholder="직무 스킬을 검색해보세요" />
									<div id="tech-results" class="tech-results-container"></div>
								</div>

								<!-- 선택된 기술 스택 태그 표시 -->
								<div class="selected-tech-tags">
									<!-- 여기에 선택된 기술 스택 태그가 추가됩니다 -->
								</div>
								
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
							const url = "/recruitment/list"; // 공고 리스트를 가져오는 API 엔드포인트

							// 토큰 여부와 상관없이 데이터를 요청
							fetch(url, {
								method: 'GET',
								headers: {
									'Content-Type': 'application/json' // JSON 형식으로 요청
								}
							})
									.then(response => response.json())
									.then(data => {
										console.log("Received data: ", data);  // 응답 데이터를 콘솔에 출력하여 확인
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
						});

						function renderRecruitmentList(recruitments) {
							const allContainer = document.getElementById('all-recruitments');

							recruitments.forEach(function(recruitment) {
								const recruitmentTitle = recruitment.recruitmentTitle || '제목 없음';
								const category = recruitment.category || '정보 없음';
								const skill = recruitment.skill || '정보 없음';
								const recruitmentType = recruitment.recruitmentType || '정보 없음';
								const period = recruitment.period || '정보 없음';

								// 모집 공고 아이템 생성
								const recruitmentItem = document.createElement('div');
								recruitmentItem.className = 'recruitment-item';

								// 제목, 카테고리, 기술 스택 등 정보를 포함하는 content div 생성
								const contentDiv = document.createElement('div');
								contentDiv.className = 'recruitment-content';

								const titleElement = document.createElement('h5');
								titleElement.className = 'recruitment-title mb-2';
								titleElement.textContent = recruitmentTitle;

								const categoryElement = document.createElement('p');
								categoryElement.className = 'recruitment-info mb-1';
								categoryElement.textContent = '카테고리: ' + category;

								const skillElement = document.createElement('p');
								skillElement.className = 'recruitment-info mb-1';
								skillElement.textContent = '기술 스택: ' + skill;

								const recruitmentTypeElement = document.createElement('p');
								recruitmentTypeElement.className = 'recruitment-info mb-1';
								recruitmentTypeElement.textContent = '고용 형태: ' + recruitmentType;

								const periodElement = document.createElement('p');
								periodElement.className = 'recruitment-info mb-1';
								periodElement.textContent = '기간: ' + period;

								// 내용을 contentDiv에 추가
								contentDiv.appendChild(titleElement);
								contentDiv.appendChild(categoryElement);
								contentDiv.appendChild(skillElement);
								contentDiv.appendChild(recruitmentTypeElement);
								contentDiv.appendChild(periodElement);

								// 상세보기 버튼을 포함하는 div 생성
								const buttonDiv = document.createElement('div');
								buttonDiv.className = 'recruitment-button';

								const linkElement = document.createElement('a');
								// 공고 인덱스를 포함한 상세 페이지로의 링크 설정
								if (recruitment.recruitmentIndex) {
									linkElement.href = `/member/recruitment/details/` + recruitment.recruitmentIndex;
								} else {
									console.error("recruitmentIndex is null for recruitment:", recruitment);
									return; // 인덱스가 없는 경우 렌더링을 중단
								}

								linkElement.className = 'btn btn-outline-primary';
								linkElement.textContent = '상세 내용';

								// 버튼을 buttonDiv에 추가
								buttonDiv.appendChild(linkElement);

								// recruitmentItem에 contentDiv와 buttonDiv 추가
								recruitmentItem.appendChild(contentDiv);
								recruitmentItem.appendChild(buttonDiv);

								// 전체 컨테이너에 모집 공고 아이템 추가
								allContainer.appendChild(recruitmentItem);
							});
						}

						// 필터 아이템 클릭 시 드롭다운 열기/닫기 처리
						document.querySelectorAll('.ur_filter-item').forEach(function (item) {
							const dropdown = item.querySelector('.ur_filter-dropdown');

							item.addEventListener('click', function (event) {
								event.stopPropagation(); // 클릭 이벤트 전파 방지

								const isOpen = item.classList.contains('open');
								if (isOpen) {
									dropdown.style.display = 'none';
									item.classList.remove('open');
								} else {
									// 모든 드롭다운 닫기
									document.querySelectorAll('.ur_filter-item').forEach(function (otherItem) {
										const otherDropdown = otherItem.querySelector('.ur_filter-dropdown');
										if (otherDropdown) {
											otherDropdown.style.display = 'none';
											otherItem.classList.remove('open');
										}
									});

									dropdown.style.display = 'flex';
									item.classList.add('open');
								}
							});

							dropdown.addEventListener('click', function (event) {
								event.stopPropagation();
							});

							document.addEventListener('click', function () {
								dropdown.style.display = 'none';
								item.classList.remove('open');
							});
						});

						document.addEventListener('DOMContentLoaded', function () {
							const jobCategories = {
								'전체': ['전체'],
								'개발': ['백엔드/서버 개발자', '프론트엔드/웹퍼블리셔', 'SW 엔지니어', '안드로이드 개발자', 'iOS 개발자', '크로스플랫폼 앱 개발자', '데이터 엔지니어'],
								'게임개발': ['게임 서버 개발자', '게임 클라이언트 개발자', '게임 기획자', '게임 그래픽 디자이너', '게임 아티스트', '모바일 게임 개발자', '게임 운영자'],
								'디자인': ['프로덕트 디자이너', '웹/앱 디자이너', '그래픽 디자이너', 'BI/BX 디자이너', '광고 디자이너', '영상/모션 디자이너', '운영 디자이너'],
								'기획': ['서비스 기획자', 'PO/PM', '비즈니스 분석가', '사업개발/기획자', '전략 기획자', '해외 사업개발/기획자', '상품 기획자/MD'],
								'마케팅': ['퍼포먼스 마케터', '콘텐츠 마케터', '디지털 마케터', '마케팅 기획자', '브랜드 마케터', '광고 기획자', 'CRM 전문가'],
								'경영/인사': ['경영지원', '회계/경리', '조직관리', '정보보호 담당자', '인사/평가', '교육', '채용담당자'],
								'영업': ['기업영업', '영업 관리자', '기술영업', '솔루션 컨설턴트', '세일즈']
							};

							const jobList = document.querySelectorAll('.ur_filter-dropdown .job-section ul li');
							const dutyListContainer = document.querySelector('.ur_filter-dropdown .duty-section ul');
							const selectedTagContainer = document.createElement('div');
							selectedTagContainer.className = 'selected-tags';
							const jobCategoryFilter = document.getElementById('job-category-filter');
							jobCategoryFilter.appendChild(selectedTagContainer);

							// 초기 상태에서 '전체' 직무만 표시
							renderDutyList('전체');

							// 직군 선택 시 직무 리스트 업데이트
							jobList.forEach(function (jobItem) {
								jobItem.addEventListener('click', function () {
									const selectedJob = this.dataset.job;
									renderDutyList(selectedJob);
								});
							});

							// 직무 리스트 업데이트 함수
							function renderDutyList(selectedJob) {
								const relatedDuties = jobCategories[selectedJob] || [];
								dutyListContainer.innerHTML = '';

								relatedDuties.forEach(function (duty, index) {
									const dutyId = `duty-checkbox${index + 1}`;
									const dutyItem = document.createElement('li');

									const dutyLabel = document.createElement('label');
									dutyLabel.textContent = duty;
									dutyLabel.setAttribute('for', dutyId);

									const dutyCheckbox = document.createElement('input');
									dutyCheckbox.type = 'checkbox';
									dutyCheckbox.id = dutyId;

									dutyItem.appendChild(dutyCheckbox);
									dutyItem.appendChild(dutyLabel);
									dutyListContainer.appendChild(dutyItem);

									dutyItem.addEventListener('click', function () {
										dutyCheckbox.checked = !dutyCheckbox.checked;
										updateSelectedTags(selectedJob, getSelectedDuties());
									});
								});

								// 직군 선택 안 했을 경우 '전체'만 보여줌
								if (selectedJob === '전체') {
									dutyListContainer.innerHTML = `
                <li>
                    <input type="checkbox" id="duty-checkbox1">
                    <label for="duty-checkbox1">전체</label>
                </li>`;
								}

								updateSelectedTags(selectedJob, []);
							}

							// 선택된 직군 및 직무 태그 표시 함수
							function updateSelectedTags(selectedJob, selectedDuties) {
								selectedTagContainer.innerHTML = '';

								const jobTag = document.createElement('span');
								jobTag.className = 'tag';
								jobTag.textContent = selectedJob;
								selectedTagContainer.appendChild(jobTag);

								selectedDuties.forEach(function (duty) {
									const dutyTag = document.createElement('span');
									dutyTag.className = 'tag';
									dutyTag.textContent = duty;
									selectedTagContainer.appendChild(dutyTag);
								});
							}

							// 선택된 직무 가져오는 함수
							function getSelectedDuties() {
								const selectedDuties = Array.from(dutyListContainer.querySelectorAll('input[type="checkbox"]:checked'))
										.map(cb => cb.nextElementSibling.textContent.trim());
								return selectedDuties;
							}
						});
							// 경력 및 지역 필터 처리

							const experiences = [
								'전체', '경력무관', '인턴', '신입 (1년 이하)', '주니어 (1~3년)', '미들 (4~8년)', '시니어 (9년 이상)', 'Lead 레벨'
							];

							const locations = [
								'전체', '서울', '강남', '마포', '구로/가산', '경기', '판교/분당', '그 외'
							];

							const experienceListContainer = document.querySelector('.ur_filter-dropdown .experience-section ul');
							const locationListContainer = document.querySelector('.ur_filter-dropdown .location-section ul');

							// 경력 체크박스 및 레이블 생성
							if (experienceListContainer.innerHTML.trim() === '') {
								experiences.forEach(function (experience, index) {
									const expId = `exp-checkbox${index + 1}`;
									const expItem = document.createElement('li');

									const expLabel = document.createElement('label');
									expLabel.textContent = experience;
									expLabel.setAttribute('for', expId);  // label의 for 속성 설정

									const expCheckbox = document.createElement('input');
									expCheckbox.type = 'checkbox';
									expCheckbox.id = expId;  // input의 id 속성 설정

									expItem.appendChild(expCheckbox);
									expItem.appendChild(expLabel);

									experienceListContainer.appendChild(expItem);

									// label과 li 클릭 시 체크박스 토글
									expLabel.addEventListener('click', function() {
										expCheckbox.checked = !expCheckbox.checked; // 토글 체크박스 상태
										updateExperienceLocationTags();
									});

									expItem.addEventListener('click', function() {
										expCheckbox.checked = !expCheckbox.checked; // 토글 체크박스 상태
										updateExperienceLocationTags();
									});
								});
							}

							// 지역 체크박스 및 레이블 생성
							if (locationListContainer.innerHTML.trim() === '') {
								locations.forEach(function (location, index) {
									const locId = `loc-checkbox${index + 1}`;
									const locItem = document.createElement('li');

									const locLabel = document.createElement('label');
									locLabel.textContent = location;
									locLabel.setAttribute('for', locId);  // label의 for 속성 설정

									const locCheckbox = document.createElement('input');
									locCheckbox.type = 'checkbox';
									locCheckbox.id = locId;  // input의 id 속성 설정

									locItem.appendChild(locCheckbox);
									locItem.appendChild(locLabel);

									locationListContainer.appendChild(locItem);

									// label과 li 클릭 시 체크박스 토글
									locLabel.addEventListener('click', function() {
										locCheckbox.checked = !locCheckbox.checked; // 토글 체크박스 상태
										updateExperienceLocationTags();
									});

									locItem.addEventListener('click', function() {
										locCheckbox.checked = !locCheckbox.checked; // 토글 체크박스 상태
										updateExperienceLocationTags();
									});
								});
							}

							// 선택된 필터를 상단에 표시할 컨테이너
							const experienceLocationTagContainer = document.createElement('div');
							experienceLocationTagContainer.className = 'selected-tags';
							const experienceLocationFilter = document.querySelector('#experience-location-filter > span');
							experienceLocationFilter.parentElement.appendChild(experienceLocationTagContainer);

							// 선택된 항목을 상단에 표시하는 함수
							function updateExperienceLocationTags() {
								experienceLocationTagContainer.innerHTML = ''; // 기존 태그 초기화

								const selectedExperiences = Array.from(experienceListContainer.querySelectorAll('input[type="checkbox"]:checked'))
									.map(cb => cb.nextElementSibling.textContent.trim());

								const selectedLocations = Array.from(locationListContainer.querySelectorAll('input[type="checkbox"]:checked'))
									.map(cb => cb.nextElementSibling.textContent.trim());

								// 경력 태그 추가
								selectedExperiences.forEach(function (experience) {
									const experienceTag = document.createElement('span');
									experienceTag.className = 'tag';
									experienceTag.textContent = experience;
									experienceLocationTagContainer.appendChild(experienceTag);
								});

								// 지역 태그 추가
								selectedLocations.forEach(function (location) {
									const locationTag = document.createElement('span');
									locationTag.className = 'tag';
									locationTag.textContent = location;
									experienceLocationTagContainer.appendChild(locationTag);
								});
							}

							// 체크박스 변경 시 태그 업데이트
							experienceListContainer.querySelectorAll('input[type="checkbox"]').forEach(function (checkbox) {
								checkbox.addEventListener('change', updateExperienceLocationTags);
							});

							locationListContainer.querySelectorAll('input[type="checkbox"]').forEach(function (checkbox) {
								checkbox.addEventListener('change', updateExperienceLocationTags);
							});

						//언어필터
						document.addEventListener('DOMContentLoaded', function () {
							const techInput = document.getElementById('tech-search');
							const selectedTechContainer = document.querySelector('.selected-tech-tags'); // 선택된 태그를 표시할 컨테이너
							const techResultsContainer = document.getElementById('tech-results');

							const techStack = [
								'Python', 'JavaScript', 'React', 'Next.js', 'SQL', 'Slack', 'JIRA', 'Confluence',
								'Java', 'C++', 'C#', 'Ruby', 'Go', 'Rust', 'TypeScript', 'Kotlin', 'Swift',
								'PHP', 'Perl', 'Scala', 'Objective-C', 'R', 'Haskell', 'Elixir'
							];

							const selectedTechTags = [];

							techInput.addEventListener('input', function () {
								const query = techInput.value.toLowerCase();
								techResultsContainer.innerHTML = ''; // 이전 검색 결과 초기화

								if (query) {
									const filteredTech = techStack.filter(tech => tech.toLowerCase().includes(query));

									filteredTech.forEach(tech => {
										const techElement = document.createElement('span');
										techElement.textContent = tech;
										techElement.classList.add('tech-result-item');
										techResultsContainer.appendChild(techElement);

										// 클릭 시 선택된 태그로 추가
										techElement.addEventListener('click', function () {
											addTechTag(tech);
											techInput.value = ''; // 입력 필드를 비웁니다
											techResultsContainer.innerHTML = ''; // 검색 결과도 초기화
										});
									});
								}
							});

							function addTechTag(tech) {
								// 이미 선택된 기술 스택인지 확인
								if (!selectedTechTags.includes(tech)) {
									selectedTechTags.push(tech);

									const techTag = document.createElement('span');
									techTag.textContent = tech;
									techTag.classList.add('tech-tag'); // 스타일 클래스 추가
									selectedTechContainer.appendChild(techTag);

									// 선택된 태그 삭제 기능 추가
									techTag.addEventListener('click', function () {
										selectedTechTags.splice(selectedTechTags.indexOf(tech), 1); // 배열에서 제거
										selectedTechContainer.removeChild(techTag); // 화면에서 제거
									});
								}
							}
						});

					</script>
					</body>
					</html>
