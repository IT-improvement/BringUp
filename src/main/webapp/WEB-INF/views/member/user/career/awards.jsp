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
                    <!-- 선택된 항목에 따라 폼이 동적으로 여기에 표시됩니다 -->
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
                    const selectedValue = this.value;
                    console.log(selectedValue);
                    const entryContainer = document.getElementById('entryContainer');
                    entryContainer.style.display = 'block';

                    if (selectedValue === 'award') {
                        entryContainer.innerHTML = `
                <h3>수상 내역 추가</h3>
                <div class="row g-3">
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
                        <button class="btn btn-outline-primary me-2">취소</button>
                        <button class="btn btn-primary">저장</button>
                    </div>
                `;
                    } else if (selectedValue === 'certification') {
                        entryContainer.innerHTML = `
                    <h3>자격증 내역 추가</h3>
                    <div class="row g-3">
                        <div class="col-md-6">
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
                        <button class="btn btn-outline-primary me-2">취소</button>
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
                    const awardTitle = document.getElementById('awardTitle').value;
                    const awardOrganization = document.getElementById('awardOrganization').value;
                    const awardDate = document.getElementById('awardDate').value;
                    const awardDetails = document.getElementById('awardDetails').value;

                    const awardData = {
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


                        let typeLabel = '';
                        if (award.type === 'award') {
                            typeLabel = '수상';
                        } else if (award.type === 'language') {
                            typeLabel = '어학시험';
                        } else if (award.type === 'certification') {
                            typeLabel = '자격증';
                        }


                        awardCard.innerHTML = `
            <div class="d-flex flex-column">
                            <h4>${'${typeLabel}'}</h4>
                <div class="d-flex align-items-center">
                    <h5 class="me-3 mb-0">${'${award.title}'}</h5>
                    <span>${'${new Date(award.awarDate).toISOString().split(`T`)[0]}'}</span>
                </div>
                <div class="text-muted">${'${award.organization}'}</div>
            </div>
            <div class="d-flex align-items-center">
                <i class="bi bi-pencil-square edit-icon me-3" onclick="editAward(${'${award.id}'}, ${'${award.type}'})"></i>
                <i class="bi bi-trash delete-icon" onclick="deleteAward(${'${award.id}'})"></i>
            </div>
        `;

                        awardListContainer.appendChild(awardCard);
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

                function editAward(id, type) {
                    const awardCard = document.getElementById(`award-${'${id}'}`);
                    let editFields = '';
                    console.log(type);

                    if (type === 'award') {
                        editFields = `
            <input type="text" class="form-control mb-2" placeholder="수상 제목" id="editAwardTitle">
            <input type="text" class="form-control mb-2" placeholder="주관(주체기관)" id="editAwardOrganization">
            <input type="date" class="form-control mb-2" placeholder="수상일자" id="editAwardDate">
            <textarea class="form-control mb-2" placeholder="수상 내역" id="editAwardDetails"></textarea>
        `;
                    } else if (type === 'language') {
                        editFields = `
            <input type="text" class="form-control mb-2" placeholder="어학 시험명" id="editLanguageTitle">
            <input type="text" class="form-control mb-2" placeholder="언어" id="editLanguage">
            <input type="date" class="form-control mb-2" placeholder="취득일자" id="editLanguageDate">
            <input type="text" class="form-control mb-2" placeholder="급수" id="editLanguageLevel">
            <input type="text" class="form-control mb-2" placeholder="점수" id="editLanguageScore">
        `;
                    } else if (type === 'certification') {
                        editFields = `
            <input type="text" class="form-control mb-2" placeholder="자격증 제목" id="editCertificationTitle">
            <input type="text" class="form-control mb-2" placeholder="발급 기관" id="editCertificationIssuer">
            <input type="date" class="form-control mb-2" placeholder="취득일자" id="editCertificationDate">
        `;
                    }

                    awardCard.innerHTML = `
        <div>${'${editFields}'}</div>
        <div class="d-flex justify-content-end mt-3">
            <button class="btn btn-outline-primary me-2" onclick="cancelEdit(${'${id}'})">취소</button>
            <button class="btn btn-primary" onclick="saveAward(${'${id}'}, ${'${type}'})">저장</button>
        </div>
    `;
                }

                function cancelEdit(id) {
                    fetchAwardList(); // 기존 목록을 다시 불러와서 카드 내용을 원래 상태로 복구
                }

                function saveAward(id, type) {
                    const updatedData = {};

                    if (type === 'award') {
                        updatedData.title = document.getElementById('editAwardTitle').value;
                        updatedData.organization = document.getElementById('editAwardOrganization').value;
                        updatedData.awarDate = document.getElementById('editAwardDate').value;
                        updatedData.details = document.getElementById('editAwardDetails').value;
                    } else if (type === 'language') {
                        updatedData.title = document.getElementById('editLanguageTitle').value;
                        updatedData.language = document.getElementById('editLanguage').value;
                        updatedData.acquisitionDate = document.getElementById('editLanguageDate').value;
                        updatedData.level = document.getElementById('editLanguageLevel').value;
                        updatedData.score = document.getElementById('editLanguageScore').value;
                    } else if (type === 'certification') {
                        updatedData.title = document.getElementById('editCertificationTitle').value;
                        updatedData.issuer = document.getElementById('editCertificationIssuer').value;
                        updatedData.acquisitionDate = document.getElementById('editCertificationDate').value;
                    }

                    const accessToken = localStorage.getItem("accessToken");

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
            </script>
        </main>
    </div>
</div>

<!-- Footer -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />
</body>
</html>
