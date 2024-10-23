<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<aside class="ms-sidebar">
    <div class="ms-sidebar-content">
        <nav>
            <ul class="ms-nav ms-flex-column">
                <li class="ms-nav-item">
                    <a class="ms-nav-link" href="/member/myCareer"><i class="bi bi-file-earmark-person"></i> 이력서</a>
                </li>
                <li class="ms-nav-item">
                    <a class="ms-nav-link" data-bs-toggle="collapse" href="#portfolioCollapse" role="button" aria-expanded="false" aria-controls="portfolioCollapse">
                        <i class="bi bi-folder-symlink"></i> 포트폴리오
                        <div class="ms-collapse-icon">
                            <span id="portfolioIcon" class="ms-arrow-icon">▼</span>
                        </div>
                    </a>
                    <div class="ms-collapse collapse" id="portfolioCollapse">
                        <ul class="ms-nav ms-flex-column ms-3">
                            <li class="ms-nav-item">
                                <a class="ms-nav-link ms-small-link" href="/member/git"><i class="bi bi-github"></i> Git</a>
                            </li>
                            <li class="ms-nav-item">
                                <a class="ms-nav-link ms-small-link" href="/member/notion"><i class="bi bi-journal-code"></i> Notion</a>
                            </li>
                            <li class="ms-nav-item">
                                <a class="ms-nav-link ms-small-link" href="/member/blog"><i class="bi bi-file-earmark"></i> 블로그</a>
                            </li>
                            <li class="ms-nav-item">
                                <a class="ms-nav-link ms-small-link" href="/member/file"><i class="bi bi-folder"></i> 파일</a>
                            </li>
                        </ul>
                    </div>
                </li>

                <li class="ms-nav-item">
                    <a class="ms-nav-link" href="/member/letter"><i class="bi bi-pencil-square"></i> 자소서</a>
                </li>
                <li class="ms-nav-item">
                    <a class="ms-nav-link" href="/member/record"><i class="bi bi-briefcase"></i> 이력</a>
                </li>
                <li class="ms-nav-item">
                    <a class="ms-nav-link" href="/member/impl"><i class="bi bi-bar-chart"></i> 경력</a>
                </li>
                <li class="ms-nav-item">
                    <a class="ms-nav-link" href="/member/qualifications "><i class="bi bi-award"></i> 어학 / 자격증</a>
                </li>
            </ul>
        </nav>
    </div>
</aside>

<link rel="stylesheet" type="text/css" href="/resources/style/member/sidebar.css"> <!-- 사이드바 전용 CSS 불러오기 -->
<script src="/resources/script/member/sidebar.js"></script> <!-- 사이드바 전용 JS 불러오기 -->
