<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- =======================
헤더 시작 -->
<header class="header-static border-bottom" style="z-index: 9999;">
    <!-- 상단 헤더 -->
    <nav class="navbar navbar-expand-xl">
        <div class="container-fluid px-3" style="max-width: 1260px;">
            <!-- 로고 시작 -->
            <a class="navbar-brand justify-content-center" href="/">
                <img class="navbar-brand-item light-mode-item" src="/resources/style/common/images/Logo.png" alt="로고">
                <img class="navbar-brand-item dark-mode-item" src="/resources/style/common/images/Logo_darkmode.png" alt="로고">
            </a>
            <!-- 로고 끝 -->
            <div class="d-flex justify-content-end" id="profileDropdown">
                <div class="nav-item ms-2 ms-md-3 dropdown">
                    <!-- 프로필 드롭다운 시작 -->
                    <a href="#" id="profileDropdownToggle" role="button" data-bs-toggle="dropdown" aria-expanded="false" style="text-decoration: none;">
                        <span id="memberNameSpan" style="display: none;"></span>
                    </a>
                    <!-- 프로필 드롭다운 메뉴 -->
                    <ul class="dropdown-menu dropdown-menu-end shadow pt-3" aria-labelledby="profileDropdownToggle" style="margin-top: 0.5rem;">
                        <!-- 링크 -->
                        <li><a class="dropdown-item" href="/member/memberProfile"><i class="bi bi-person fa-fw me-2"></i>프로필</a></li>
                        <li><a class="dropdown-item" href="#"><i class="bi bi-gear fa-fw me-2"></i>계정 설정</a></li>
                        <li><a class="dropdown-item" href="/" id="logoutButton"><i class="bi bi-power fa-fw me-2"></i>로그아웃</a></li>
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
            </div>
            <div class="d-flex justify-content-end" id="login">
                <div class="nav-item ms-2 ms-md-3" id="memberLogin">
                    <a href="/member/Login">로그인</a>
                </div>
                <div class="nav-item ms-2 ms-md-3" id="companyLogin">
                    <a href="/company">기업로그인</a>
                </div>
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
                            <li><a class="dropdown-item" href="/member/visitRecruitment">내가 본 공고</a></li>
                            <li><a class="dropdown-item" href="/member/AnnouncementRecruitment">내가 지원한 공고</a></li>
                        </ul>
                    </li>

                    <!-- 네비 아이템 3 상품 -->
                    <li class="nav-item dropdown">
                        <a class="nav-link" id="pagesMenu" href="/member/careerList">마이 커리어</a>
               <%--         <ul class="dropdown-menu" aria-labelledby="pagesMenu">
                            <li><a class="dropdown-item" href="/member/potofolio">내 포토폴리오</a></li>
                            <li><a class="dropdown-item" href="/member/letter">내 이력서</a></li>
                            <li><a class="dropdown-item" href="/member/resume">내 자소서</a></li>
                            <li><a class="dropdown-item" href="/member/freelancer">프리랜서</a></li>
                        </ul>--%>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="reviewMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">기업</a>
                        <ul class="dropdown-menu" aria-labelledby="postMenu">
                            <li><a class="dropdown-item" href="/member/company/list">기업</a></li>

                            <li><a class="dropdown-item" href="/member/companyReview">기업 리뷰</a></li>
                            <li><a class="dropdown-item" href="/member/interviewReview">면접 후기</a></li>
                            <li><a class="dropdown-item" href="/member/bookmark">기업 북마크</a></li>

                            <li><a class="dropdown-item" href="/member/myReview">나의 리뷰</a></li>

                        </ul>
                    </li>

                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" id="recommendMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">만남의 광장</a>
                        <ul class="dropdown-menu" aria-labelledby="postMenu">
                            <li><a class="dropdown-item" href="/member/notice">게시글 목록</a></li>
                            <li><a class="dropdown-item" href="/member/userNotice">작성한 게시글</a></li>
                            <li><a class="dropdown-item" href="/member/createNotice">게시글 등록</a></li>
                        </ul>
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