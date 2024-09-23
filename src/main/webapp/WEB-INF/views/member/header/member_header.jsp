<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- =======================
헤더 시작 -->
<header class="header-static border-bottom" style="z-index: 9999;">
    <!-- 상단 헤더 -->
    <nav class="navbar navbar-expand-xl">
        <div class="container-fluid px-3" style="max-width: 1260px;">
            <!-- 로고 시작 -->
            <a class="navbar-brand justify-content-center" href="/member/userLoginForm">
                <img class="navbar-brand-item light-mode-item" src="/resources/style/common/images/Logo.png" alt="로고">
                <img class="navbar-brand-item dark-mode-item" src="/resources/style/common/images/Logo_darkmode.png" alt="로고">
            </a>
            <!-- 로고 끝 -->
            <!-- 네비 오른쪽 시작 -->
         <%--   <div class="nav flex-nowrap align-items-center ms-auto">
                <!-- 알림 드롭다운 시작 -->
                <div class="nav-item ms-2 ms-md-3 dropdown">
                    <!-- 알림 버튼 -->
                    <a class="btn btn-round mb-0" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="outside" onmouseover="setNotificationButtonStyle(this)" onmouseout="resetNotificationButtonStyle(this)">
                        <i class="bi bi-bell fa-fw"></i>
                    </a>
                    <!-- 알림 점 -->
                    <span class="notif-badge animation-blink"></span>

                    <!-- 알림 드롭다운 메뉴 시작 -->
                    <div class="dropdown-menu dropdown-animation dropdown-menu-end dropdown-menu-size-md p-0 shadow-lg border-0">
                        <div class="card bg-transparent">
                            <div class="card-header bg-transparent border-bottom p-3 d-flex justify-content-between align-items-center">
                                <h6 class="m-0">알림 <span class="badge bg-danger bg-opacity-10 text-danger ms-2">2개 새 알림</span></h6>
                                <a class="small" href="#">모두 지우기</a>
                            </div>
                            <div class="card-body p-0">
                                <div class="scrollable-notifications" style="max-height: 300px; overflow-y: auto;">
                                    <ul class="list-group list-unstyled list-group-flush">
                                        <!-- 알림 아이템 -->
                                        <li>
                                            <a href="#" class="list-group-item-action border-0 border-bottom d-flex p-3">
                                                <div>
                                                    <h6 class="mb-1">12명의 새로운 멤버가 가입했습니다</h6>
                                                    <span class="small"> <i class="bi bi-clock"></i> 3분 전</span>
                                                </div>
                                                <div class="ms-auto">
                                                    <button type="button" class="btn btn-sm btn-outline-danger remove-notification">
                                                        <i class="bi bi-x"></i>
                                                    </button>
                                                </div>
                                            </a>
                                        </li>

                                        <!-- 알림 아이템 -->
                                        <li>
                                            <a href="#" class="list-group-item-action border-0 border-bottom d-flex p-3">
                                                <div>
                                                    <h6 class="mb-1">Larry Lawson이 계정을 삭제했습니다</h6>
                                                    <span class="small"> <i class="bi bi-clock"></i> 6분 전</span>
                                                </div>
                                                <div class="ms-auto">
                                                    <button type="button" class="btn btn-sm btn-outline-danger remove-notification">
                                                        <i class="bi bi-x"></i>
                                                    </button>
                                                </div>
                                            </a>
                                        </li>

                                        <!-- 알림 아이템 -->
                                        <li>
                                            <a href="#" class="list-group-item-action border-0 border-bottom d-flex p-3">
                                                <div>
                                                    <h6 class="mb-1">Byan이 당신의 게시물에 댓글을 달았습니다</h6>
                                                    <span class="small"> <i class="bi bi-clock"></i> 10분 전</span>
                                                </div>
                                                <div class="ms-auto">
                                                    <button type="button" class="btn btn-sm btn-outline-danger remove-notification">
                                                        <i class="bi bi-x"></i>
                                                    </button>
                                                </div>
                                            </a>
                                        </li>

                                        <!-- 알림 아이템 -->
                                        <li>
                                            <a href="#" class="list-group-item-action border-0 border-bottom d-flex p-3">
                                                <div>
                                                    <h6 class="mb-1">설정이 업데이트되었습니다</h6>
                                                    <span class="small"> <i class="bi bi-clock"></i> 어제</span>
                                                </div>
                                                <div class="ms-auto">
                                                    <button type="button" class="btn btn-sm btn-outline-danger remove-notification">
                                                        <i class="bi bi-x"></i>
                                                    </button>
                                                </div>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <!-- 버튼 -->
                            <div class="card-footer bg-transparent border-0 py-3 text-center position-relative">
                                <a href="#" class="stretched-link">모든 활동 보기</a>
                            </div>
                        </div>
                        <!-- 알림 드롭다운 메뉴 내용 -->
                    </div>
                    <!-- 알림 드롭다운 메뉴 끝 -->
                </div>
--%>
                <!-- 프로필 드롭다운 시작 -->
                <div class="nav-item ms-2 ms-md-3 dropdown">
                    <!-- 아바타 -->
                    <a href="#" id="profileDropdown" role="button" data-bs-auto-close="outside" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="false">
                        <span id="memberNameSpan" style="display: none;">로딩 중...</span>
                    </a>

                    <!-- 프로필 드롭다운 시작 -->
                    <ul class="dropdown-menu dropdown-animation dropdown-menu-end shadow pt-3" aria-labelledby="profileDropdown">
                        <!-- 프로필 정보 -->
                        <li class="px-3">
                            <div class="d-flex align-items-center">
                                <div>
                                    <a class="h6 mt-2 mt-sm-0" href="#"><span id="memberNameLink">로딩 중...</span></a>
                                </div>
                            </div>
                            <hr>
                        </li>
                        <!-- 링크 -->
                        <li><a class="dropdown-item" href="#"><i class="bi bi-person fa-fw me-2"></i>프로필 수정</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-gear fa-fw me-2"></i>계정 설정</a></li>
                        <li><a class="dropdown-item" href="#" id="logoutButton"><i class="bi bi-power fa-fw me-2"></i>로그아웃</a></li>
                        <li class="dropdown-divider mb-2"></li>
                        <li>
                            <!-- 다크 모드 버튼 수정 -->
                            <div class="px-3 mb-2">
                                <div class="modeSwitch d-flex" role="group" aria-label="다크 모드 전환">
                                    <input type="radio" class="btn-check" name="theme" id="lightMode" autocomplete="off" checked>
                                    <label class="btn btn-light btn-sm flex-grow-1" for="lightMode">
                                        <i class="bi bi-brightness-high-fill fa-fw mode-switch"></i> 라이트
                                    </label>
                                    <input type="radio" class="btn-check" name="theme" id="darkMode" autocomplete="off">
                                    <label class="btn btn-light btn-sm flex-grow-1" for="darkMode">
                                        <i class="bi bi-moon-stars-fill fa-fw mode-switch"></i> 다크
                                    </label>
                                </div>
                            </div>
                        </li>
                    </ul>
                    <!-- 프로필 드롭다운 끝 -->
                </div>
                <!-- 프로필 드롭다운 끝 -->
            </div>
            <!-- 네비 오른쪽 끝 -->
        </div>
    </nav>
</header>
<!-- 알림 프로필 부분 끝 -->

<!-- 메인 네비게이션 부분 시작 -->
<middle_header class="border-bottom">
    <!-- 하단 헤더 (메인 네비게이션) -->
    <nav class="navbar navbar-expand-lg navbar-dashboard">
        <div class="container px-3" style="max-width: 1260px;">
            <!-- 메인 네비게이션 시작 -->
            <div class="collapse navbar-collapse" id="navbarCollapse">
                <ul class="navbar-nav mx-auto">
                    <!-- 네비 아이템 2 공고 -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle"  id="jobPostingMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">채용 정보</a>
                        <ul class="dropdown-menu" aria-labelledby="jobPostingMenu">
                            <li><a class="dropdown-item" href="/member/recruitmentPage">홈</a></li>
                            <li><a class="dropdown-item" href="/member/topRecruitment">인기 공고 TOP 100</a></li>
                            <li><a class="dropdown-item" href="/member/confirmRecruitment">내가 본 공고</a></li>
                            <li><a class="dropdown-item" href="/member/AnnouncementRecruitment">내가 지원한 공고</a></li>
                            <li><a class="dropdown-item" href="/member/proposeRecruitment">제안 받은 공고</a></li>
                        </ul>
                    </li>

                    <!-- 네비 아이템 3 상품 -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="pagesMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">마이 커리어</a>
                        <ul class="dropdown-menu" aria-labelledby="pagesMenu">
                            <li><a class="dropdown-item" href="/member/potofolio">내 포토폴리오</a></li>
                            <li><a class="dropdown-item" href="/member/letter">내 이력서</a></li>
                            <li><a class="dropdown-item" href="/member/resume">내 자소서</a></li>
                            <li><a class="dropdown-item" href="#">프리랜서</a></li>
                            <li><a class="dropdown-item" href="#">채팅방</a></li>
                        </ul>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="reviewMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">기업</a>
                        <ul class="dropdown-menu" aria-labelledby="postMenu">
                            <li><a class="dropdown-item" href="/member/companyReview">기업 리뷰</a></li>
                            <li><a class="dropdown-item" href="/member/interviewReview">면접 후기</a></li>
                        </ul>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/member/notice" id="recommendMenu">만남의 광장</a>
                    </li>

                </ul>
            </div>
            <!-- 메인 네비게이션 끝 -->
        </div>
    </nav>
</middle_header>
<!-- 메인 네비게이션 부분 끝 -->
<link rel="stylesheet" href="/resources/style/company/header/header.css">

<script src="/resources/script/member/memberHeader.js"></script>