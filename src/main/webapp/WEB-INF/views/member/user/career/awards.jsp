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
        .awards-section{
            padding : 0;
        }
    </style>

</head>
<style>
    .award-card {
        border-bottom: 1px solid #ddd;
        padding: 1rem 0;
    }
    .award-card h5 {
        font-size: 1rem;
        font-weight: bold;
    }
    .award-card .text-muted {
        font-size: 0.875rem;
        color: #6c757d;
    }
    .award-card .edit-icon, .award-card .delete-icon {
        cursor: pointer;
        font-size: 1.2rem;
        color: #888;
    }
    .award-card .edit-icon:hover, .award-card .delete-icon:hover {
        color: #007bff;
    }
    .award-mt-3{
        border-top: 1px solid;
        margin-top: 2%;
    }
</style>

<body class="d-flex flex-column min-vh-100">
<!-- Header -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<div class="d-flex flex-grow-1">
    <!-- Sidebar -->
    <jsp:include page="/WEB-INF/views/member/sidebar/sidebar.jsp" flush="true" />

    <div class="container ms-main-content" style="max-width: 1000px; margin-left: 100px;">
        <main class="flex-grow-1">
            <h2>자격/어학/수상</h2>
            <section class="awards-section">
                <div class="d-flex justify-content-between align-items-center">
                    <div></div>
                    <div>

                        <a href="#" id="showSelectButton" class="text-primary me-3" style="text-decoration: none;">+ 추가</a>

                    </div>
                </div>
                <div id="initialMessage" class="mt-3 p-3 text-center" style="border: 1px solid #ddd; border-radius: 5px; color: #888;">
                    자격/어학/수상 내역을 입력해주세요
                </div>
                <div id="selectContainer" class="mt-3 p-3 text-center" style="border: 1px solid #ddd; border-radius: 5px; color: #888; display: none;">
                    <div class="row g-3">
                            <div class="col-md-4">
                                <select id="categorySelect" class="form-select">
                                <option value="" disabled selected>구분 선택 *</option>
                                    <option value="award">수상</option>
                                <option value="language">어학시험</option>
                                <option value="certification">자격증</option>
                                </select>
                        </div>
                    </div>
                </div>
                <div id="entryContainer" class="mt-3" style="box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); border: 1px solid #007bff; border-radius: 5px; padding: 20px; display: none;">
                    <input type="hidden" id="awardType" value="">
                    <div id="formContent">
                        <!-- 선택된 항목에 따라 폼이 동적으로 여기에 표시됩니다 -->
                    </div>


                </div>
                <div id="awardListContainer" class="award-mt-3"></div> <!-- 여기 추가 -->


            </section>

            <script>
                // "추가" 버튼 클릭 시 구분 선택 메뉴 표시
                document.getElementById('showSelectButton').addEventListener('click', function() {
                    document.getElementById('initialMessage').style.display = 'none';
                    document.getElementById('selectContainer').style.display = 'block';
                });

                // 구분 선택에 따라 폼을 동적으로 표시
                document.getElementById('categorySelect').addEventListener('change', function() {
                    const selectedValue = this.value; // 선택된 값 (수상, 어학시험, 자격증)
                    const awardTypeElement = document.getElementById('awardType');
                    const categoryMap = {
                        award: "수상",
                        language: "어학",
                        certification: "자격증"
                    };

                    if (awardTypeElement) {
                        awardTypeElement.value = categoryMap[selectedValue]; // Hidden 필드에 한글 값 저장
                        console.log("Award Type 값:", categoryMap[selectedValue]);
                    } else {
                        console.error("Hidden field 'awardType' 이 없음.");
                        return;
                    }


                    const entryContainer = document.getElementById('entryContainer');
                    entryContainer.style.display = 'block';


                    if (selectedValue === 'award') {
                        entryContainer.innerHTML = `
                <h3>수상 내역 추가</h3>
                <div class="row g-3">
            <input type="hidden" id="awardType" value="${'${categoryMap[selectedValue]}'}"> <!-- Hidden 필드 유지 -->

                    <div class="col-md-6">
                        <input type="text" class="form-control" placeholder="수상 제목" id="awardTitle">
                    </div>
                    <div class="col-md-6">
                        <input type="text" class="form-control" placeholder="주관(주체기관)" id="awardOrganization">
                    </div>
                    <div class="col-md-6">
                        <input type="date" class="form-control" placeholder="수상일자" id="awardDate">
                    </div>
                    <div class="col-md-6">
                        <textarea class="form-control" placeholder="수상 내역" rows="3" style="height: 50%" id="awardDetails"></textarea>
                    </div>
                </div>
                <div class="d-flex justify-content-end mt-3">
                    <button class="btn btn-outline-primary me-2" onclick="hideForm()">취소</button>
                    <button class="btn btn-primary" onclick="insertAward()">저장</button>
                </div>
            `;
                    } else if (selectedValue === 'language') {
                        entryContainer.innerHTML = `
                    <h3>어학 시험 내역 추가</h3>
                    <div class="row g-3">
                        <div class="col-md-4">
            <input type="hidden" id="awardType" value="${'${categoryMap[selectedValue]}'}"> <!-- Hidden 필드 유지 -->

                            <input type="text" class="form-control" placeholder="어학 시험명">
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" placeholder="언어">
                        </div>
                        <div class="col-md-4">
                            <input type="date" class="form-control" placeholder="취득일자">
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" placeholder="급수">
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" placeholder="점수">
                        </div>
                        <div class="col-md-4">
                            <input type="text" class="form-control" placeholder="취득 여부">
                        </div>
                    </div>
                    <div class="d-flex justify-content-end mt-3">
                        <button class="btn btn-outline-primary me-2" onclick="hideForm()">취소</button>
                        <button class="btn btn-primary">저장</button>
                    </div>
                `;
                    } else if (selectedValue === 'certification') {
                        entryContainer.innerHTML = `
                    <h3>자격증 내역 추가</h3>
                    <div class="row g-3">
                        <div class="col-md-6">
            <input type="hidden" id="awardType" value="${'${categoryMap[selectedValue]}'}"> <!-- Hidden 필드 유지 -->

                            <input type="text" class="form-control" placeholder="자격증 제목">
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" placeholder="발급 기관">
                        </div>
                        <div class="col-md-6">
                            <input type="date" class="form-control" placeholder="취득일자">
                        </div>
                    </div>
                    <div class="d-flex justify-content-end mt-3">
                        <button class="btn btn-outline-primary me-2" onclick="hideForm()">취소</button>
                        <button class="btn btn-primary">저장</button>
                    </div>
                `;
                    }
                });


                // "추가" 버튼 클릭 시 수상 추가 폼 표시
                document.getElementById('showSelectButton').addEventListener('click', function() {
                    document.getElementById('initialMessage').style.display = 'none';
                });

                // 수상 추가 폼 숨기기
                function hideForm() {
                    document.getElementById('entryContainer').style.display = 'none';
                    document.getElementById('selectContainer').style.display = 'none';
                    document.getElementById('initialMessage').style.display = 'none';
                }

                // 수상 내역 추가
                function insertAward() {
                    const accessToken = localStorage.getItem("accessToken");

                    if (!accessToken) {
                        alert("로그인이 필요합니다.");
                        return;
                    }
                    const awardTypeElement = document.getElementById('awardType');
                    if (!awardTypeElement || !awardTypeElement.value) {
                        alert("수상 구분이 설정되지 않았습니다. 구분을 선택해주세요.");
                        return;
                    }

                    const awardType = awardTypeElement.value;
                    const awardTitle = document.getElementById('awardTitle').value;
                    const awardOrganization = document.getElementById('awardOrganization').value;
                    const awardDate = document.getElementById('awardDate').value;
                    const awardDetails = document.getElementById('awardDetails').value;

                    const awardData = {
                        awardType : awardType,
                        title: awardTitle,
                        organization: awardOrganization,
                        awarDate: awardDate,
                        details: awardDetails,
                    };

                    fetch('/award/insert', {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ` + accessToken,
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(awardData)
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.code === "SU") {
                                alert("수상 내역이 저장되었습니다.");
                                fetchAwardList();
                                hideForm();
                            } else {
                                alert("수상 내역 저장에 실패했습니다.");
                            }
                        })
                        .catch(error => console.error('Error:', error));
                }

                // 수상 내역 리스트 가져오기
                function fetchAwardList() {
                    const accessToken = localStorage.getItem("accessToken");

                    if (!accessToken) {
                        alert("로그인이 필요합니다.");
                        return;
                    }

                    fetch('/award/list', {
                        method: 'GET',
                        headers: {
                            'Authorization': `Bearer ` + accessToken,
                            'Content-Type': 'application/json',

                        }
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.code === "SU") {
                                displayAwardList(data.list);

                            } else {

                            }
                        })
                        .catch(error => console.error('Error:', error));
                }


                // 수상 내역 리스트 표시
                function displayAwardList(awards) {

                    const awardListContainer = document.getElementById('awardListContainer');
                    awardListContainer.innerHTML = ''; // 초기화

                    if (awards.length === 0) {
                        document.getElementById('initialMessage').style.display = 'block';
                        return;
                    }

                    document.getElementById('initialMessage').style.display = 'none';

                    awards.forEach(award => {
                        const awardCard = document.createElement('div');

                        awardCard.className = 'award-card mb-3 p-3 d-flex align-items-center justify-content-between';
                        awardCard.id = `award-${'${award.id}'}`;


                        awardCard.innerHTML = `
            <div class="d-flex flex-column">
                            <h4>${'${award.awardType}'}</h4>
                <div class="d-flex align-items-center">
                    <h5 class="me-3 mb-0">${'${award.title}'}</h5>
                    <span>${'${new Date(award.awarDate).toISOString().split(`T`)[0]}'}</span>
                </div>
                <div class="text-muted-content">${'${award.organization}'}</div>
                <div class="text-muted">${'${award.details}'}</div>
            </div>
            <div class="d-flex align-items-center">
                <i class="bi bi-pencil-square edit-icon me-3" onclick="editAward(${'${award.id}'})"></i>
                <i class="bi bi-trash delete-icon" onclick="deleteAward(${'${award.id}'})"></i>
            </div>
        `;

                        awardListContainer.appendChild(awardCard);
                        awardCard.setAttribute('data-type', award.awardType);

                    });
                }

                // 수상 내역 삭제
                    function deleteAward(id) {
                        console.log("인덱스 : " +  id);
                    fetch(`/award/delete/` + id, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                        .then(response => response.json())
                        .then(data => {
                            if (data.code === "SU") {
                                alert("수상 내역이 삭제되었습니다.");
                                fetchAwardList();
                            } else {
                                alert("수상 내역 삭제에 실패했습니다.");
                            }
                        })
                        .catch(error => console.error('Error:', error));
                }

                // 페이지 로드 시 수상 내역 리스트 불러오기
                document.addEventListener('DOMContentLoaded', fetchAwardList);

                function editAward(id) {
                    const awardCard = document.getElementById(`award-${'${id}'}`);
                    const type = awardCard.getAttribute('data-type');
                    const title = awardCard.querySelector('h5').textContent;
                    const organization = awardCard.querySelector('.text-muted').textContent;
                    const awardDate = awardCard.querySelector('span').textContent;
                    const details = awardCard.querySelector('.text-muted:last-child')?.textContent || '';

                    let editFields = '';

                    if (type === '수상') {
                        editFields = `
            <h3>수상 내역 수정</h3>
            <div class="row g-3">
                <div class="col-md-6">
                                <input type="hidden" id="awardId" value="${'${id}'}">
                                <input type="hidden" id="awardType" value="${'${type}'}">
                    <input type="text" class="form-control" value="${'${title}'}" placeholder="수상 제목" id="awardTitle">
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" value="${'${organization}'}" placeholder="주관(주체기관)" id="awardOrganization">
                </div>
                <div class="col-md-6">
                    <input type="date" class="form-control" value="${'${awardDate}'}" placeholder="수상일자" id="awardDate">
                </div>
                <div class="col-md-6">
                    <textarea class="form-control" placeholder="수상 내역" rows="3" id="awardDetails">${'${details}'}</textarea>
                </div>
            </div>
            <div class="d-flex justify-content-end mt-3">
                <button class="btn btn-outline-primary me-2" onclick="cancelEdit(${'${id}'})">취소</button>
                <button class="btn btn-primary" onclick="editSaveAward()">저장</button>
            </div>
        `;
                    } else if (type === '어학') {
                        editFields = `
            <h3>어학 시험 수정</h3>
            <div class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" value="${'${title}'}" placeholder="어학 시험명" id="editLanguageTitle">
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" value="${'${organization}'}" placeholder="언어" id="editLanguage">
                </div>
                <div class="col-md-6">
                    <input type="date" class="form-control" value="${'${awardDate}'}" placeholder="취득일자" id="editLanguageDate">
                </div>
                <div class="col-md-6">
                    <textarea class="form-control" placeholder="점수" rows="3" id="editLanguageDetails">${'${details}'}</textarea>
                </div>
            </div>
            <div class="d-flex justify-content-end mt-3">
                <button class="btn btn-outline-primary me-2" onclick="cancelEdit(${'${id}'})">취소</button>
                <button class="btn btn-primary" onclick="saveAward(${'${id}'}, ${'${type}'})">저장</button>
            </div>
        `;
                    } else if (type === '자격증') {
                        editFields = `
            <h3>자격증 수정</h3>
            <div class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" value="${'${title}'}" placeholder="자격증 제목" id="editCertificationTitle">
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" value="${'${organization}'}" placeholder="발급 기관" id="editCertificationIssuer">
                </div>
                <div class="col-md-6">
                    <input type="date" class="form-control" value="${'${awardDate}'}" placeholder="취득일자" id="editCertificationDate">
                </div>
            </div>
            <div class="d-flex justify-content-end mt-3">
                <button class="btn btn-outline-primary me-2" onclick="cancelEdit(${'${id}'})">취소</button>
                <button class="btn btn-primary" onclick="editSaveAward(${'${id}'}, ${'${type}'})">저장</button>
            </div>
        `;
                    }

                    awardCard.innerHTML = `
        <div class="p-3" style="box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); border: 1px solid #007bff; border-radius: 5px;">
           ${'${editFields}'}
        </div>
    `;
                }


                function cancelEdit(id) {
                    console.log(id);
                    fetchAwardList(); // 기존 목록을 다시 불러와서 카드 내용을 원래 상태로 복구
                }
                function editSaveAward() {
                    const accessToken = localStorage.getItem("accessToken");
                    console.log(id);

                    if (!accessToken) {
                        alert("로그인이 필요합니다.");
                        return;
                    }

                    // 데이터 수집
                    const data = {
                        id:document.getElementById('awardId') ? document.getElementById('awardId').value : null,
                        awardType: document.getElementById('awardType') ? document.getElementById('awardType').value : null, // awardType 값 수집
                        title: document.getElementById('awardTitle') ? document.getElementById('awardTitle').value : null,
                        organization: document.getElementById('awardOrganization') ? document.getElementById('awardOrganization').value : null,
                        awardDate: document.getElementById('awardDate') ? document.getElementById('awardDate').value : null,
                        details: document.getElementById('awardDetails') ? document.getElementById('awardDetails').value : null,
                    };

                    // 필수 필드 검증
                    if (!data.title || !data.organization || !data.awardDate) {
                        alert("모든 필수 필드를 입력해주세요.");
                        return;
                    }

                    // 데이터 전송
                    fetch('/award/insert', {
                        method: 'POST',
                        headers: {
                            'Authorization': `Bearer ` + accessToken,
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(data),
                    })
                        .then(response => response.json())
                        .then(result => {
                            if (result.code === "SU") {
                                alert("수상 내역이 성공적으로 수정되었습니다.");
                                location.reload(); // 페이지를 새로고침하여 변경된 내용 반영
                            } else {
                                alert("수정에 실패했습니다: " + result.message);
                            }
                        })
                        .catch(error => console.error("Error:", error));
                }

            </script>
        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
