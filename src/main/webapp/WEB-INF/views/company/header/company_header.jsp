 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <!-- =======================
헤더 시작 -->
    <header class="navbar-light navbar-sticky header-static border-bottom navbar-dashboard">
        <!-- 로고 네비게이션 시작 -->
        <nav class="navbar navbar-expand-xl">
            <div class="container">
                <!-- 로고 시작 -->
                <a class="navbar-brand me-3" href="/company">
                    <img class="navbar-brand-item light-mode-item" src="/resources/style/common/images/Logo.png" alt="로고">
                    <img class="navbar-brand-item dark-mode-item" src="/resources/style/common/images/Logo_darkmode.png" alt="로고">
                </a>
                <!-- 로고 끝 -->

                <!-- 반응형 네비게이션 토글러 -->
                <button class="navbar-toggler ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="네비게이션 토글">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- 메인 네비게이션 시작 -->
                <div class="collapse navbar-collapse" id="navbarCollapse">
                    <ul class="navbar-nav mx-auto">
                        <!-- 네비 아이템 2 공고 -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="jobPostingMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">공고</a>
                            <ul class="dropdown-menu" aria-labelledby="jobPostingMenu">
                                <!-- 드롭다운 서브메뉴 -->
                                <li> <a class="dropdown-item" href="/company/jobpost/management">공고 관리</a> </li>
                                <li> <a class="dropdown-item" href="/company/jobpost/registration">공고 등록</a> </li>
                            </ul>
                        </li>

                        <!-- 네비 아이템 3 상품 -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="pagesMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">상품</a>
                            <ul class="dropdown-menu" aria-labelledby="pagesMenu">
                                <li> <a class="dropdown-item" href="/company/product/management">상품 관리</a></li>
                                <li> <a class="dropdown-item" href="/company/product/premium_job_posting">프리미엄 공고</a></li>
                                <li> <a class="dropdown-item" href="/company/product/advertising_banner">광고 배너</a></li>
                                <li> <a class="dropdown-item" href="/company/product/resume_key">이력서 열람</a></li></ul>
                        </li>

                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="reviewMenu" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">리뷰</a>
                            <ul class="dropdown-menu" aria-labelledby="postMenu">
                                <!-- 드롭다운 서브메뉴 -->
                                <li> <a class="dropdown-item" href="/company/review/corporation">기업 리뷰</a> </li>
                                <li> <a class="dropdown-item" href="/company/review/interview">면접 리뷰</a> </li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/company/recommendation" id="recommendMenu">인재 추천</a>
                        </li>
                    </ul>
                </div>
                <!-- 메인 네비게이션 끝 -->

                <!-- 네비 오른쪽 시작 -->
                <div class="nav flex-nowrap align-items-center">
                    <!-- 다크 모드  -->
                    <div class="align-baseline text-center py-0">
                        <div class="btn-group theme-icon-active" role="group" aria-label="기본 버튼 그룹">
                            <button type="button" class="btn btn-light btn-sm mb-0" data-bs-theme-value="light">
                                <svg width="16" height="16" fill="currentColor" class="bi bi-brightness-high-fill fa-fw mode-switch" viewBox="0 0 16 16">
                                    <path d="M12 8a4 4 0 1 1-8 0 4 4 0 0 1 8 0zM8 0a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 0zm0 13a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-1 0v-2A.5.5 0 0 1 8 13zm8-5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2a.5.5 0 0 1 .5.5zM3 8a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1 0-1h2A.5.5 0 0 1 3 8zm10.657-5.657a.5.5 0 0 1 0 .707l-1.414 1.415a.5.5 0 1 1-.707-.708l1.414-1.414a.5.5 0 0 1 .707 0zm-9.193 9.193a.5.5 0 0 1 0 .707L3.05 13.657a.5.5 0 0 1-.707-.707l1.414-1.414a.5.5 0 0 1 .707 0zm9.193 2.121a.5.5 0 0 1-.707 0l-1.414-1.414a.5.5 0 0 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .707zM4.464 4.465a.5.5 0 0 1-.707 0L2.343 3.05a.5.5 0 1 1 .707-.707l1.414 1.414a.5.5 0 0 1 0 .708z"/>
                                    <use href="#"></use>
                                </svg>
                            </button>
                            <button type="button" class="btn btn-light btn-sm mb-0" data-bs-theme-value="dark">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-moon-stars-fill fa-fw mode-switch" viewBox="0 0 16 16">
                                    <path d="M6 .278a.768.768 0 0 1 .08.858 7.208 7.208 0 0 0-.878 3.46c0 4.021 3.278 7.277 7.318 7.277.527 0 1.04-.055 1.533-.16a.787.787 0 0 1 .81.316.733.733 0 0 1-.031.893A8.349 8.349 0 0 1 8.344 16C3.734 16 0 12.286 0 7.71 0 4.266 2.114 1.312 5.124.06A.752.752 0 0 1 6 .278z"/>
                                    <path d="M10.794 3.148a.217.217 0 0 1 .412 0l.387 1.162c.173.518.579.924 1.097 1.097l1.162.387a.217.217 0 0 1 0 .412l-1.162.387a1.734 1.734 0 0 0-1.097 1.097l-.387 1.162a.217.217 0 0 1-.412 0l-.387-1.162A1.734 1.734 0 0 0 9.31 6.593l-1.162-.387a.217.217 0 0 1 0-.412l1.162-.387a1.734 1.734 0 0 0 1.097-1.097l.387-1.162zM13.863.099a.145.145 0 0 1 .274 0l.258.774c.115.346.386.617.732.732l.774.258a.145.145 0 0 1 0 .274l-.774.258a1.156 1.156 0 0 0-.732.732l-.258.774a.145.145 0 0 1-.274 0l-.258-.774a1.156 1.156 0 0 0-.732-.732l-.774-.258a.145.145 0 0 1 0-.274l.774-.258c.346-.115.617-.386.732-.732L13.863.1z"/>
                                    <use href="#"></use>
                                </svg>
                            </button>
                        </div>
                    </div>

                    <!-- 알림 드롭다운 시작 -->
                    <div class="nav-item ms-2 ms-md-3 dropdown">
                        <!-- 알림 버튼 -->
                        <a class="btn btn-primary-soft btn-round mb-0" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="outside">
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
                        </div>
                        <!-- 알림 드롭다운 메뉴 끝 -->
                    </div>
                    <!-- 알림 드롭다운 끝 -->

                    <!-- 프로필 드롭다운 시작 -->
                    <div class="nav-item ms-2 ms-md-3 dropdown">
                        <!-- 아바타 -->
                        <a href="#" id="profileDropdown" role="button" data-bs-auto-close="outside" data-bs-display="static" data-bs-toggle="dropdown" aria-expanded="false">
                            <span id="companyNameSpan" style="display: none;">로딩 중...</span>
                        </a>

                        <!-- 프로필 드롭다운 시작 -->
                        <ul class="dropdown-menu dropdown-animation dropdown-menu-end shadow pt-3" aria-labelledby="profileDropdown">
                            <!-- 프로필 정보 -->
                            <li class="px-3">
                                <div class="d-flex align-items-center">
                                    <div>
                                        <a class="h6 mt-2 mt-sm-0" href="/company/auth/profile"><span id="companyNameLink">로딩 중...</span></a>
                                    </div>
                                </div>
                                <hr>
                            </li>
                            <!-- 링크 -->
                            <li><a class="dropdown-item" href="/company/auth/updateProfile"><i class="bi bi-person fa-fw me-2"></i>프로필 수정</a></li>
                            <li><a class="dropdown-item" href="/company/auth/updateAuth"><i class="bi bi-gear fa-fw me-2"></i>계정 설정</a></li>
                            <li><a class="dropdown-item" href="#" id="logoutButton"><i class="bi bi-power fa-fw me-2"></i>로그아웃</a></li>
                            <li class="dropdown-divider mb-2"></li>
                            <li>
                            </li>
                        </ul>
                        <!-- 프로필 드롭다운 끝 -->
                    </div>
                    <!-- 프로필 드롭다운 끝 -->
                    <!-- 네비 오른쪽 끝 -->
                </div>
            </div>
        </nav>
        <!-- 로고 네비게이션 끝 -->
    </header>
    <!-- =======================
    헤더 끝 -->

<script src="/resources/script/company/header.js"></script>

<link rel="stylesheet" type="text/css" href="/resources/style/company/header/header.css">


