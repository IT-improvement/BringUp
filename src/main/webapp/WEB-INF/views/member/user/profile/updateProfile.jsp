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

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- 메인 스타일시트 -->
    <!-- <link rel="stylesheet" type="text/css" href="/resources/style/member/user/파일명.css"> -->
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <!--  JS -->
    <!-- <script src="/resources/script/member/user/파일명.js"></script> -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const accessToken = localStorage.getItem('accessToken');
            console.log("Access token: " + accessToken);
            if (!accessToken) {
                window.location.href = "/member/Login";
                return;
            }
            const url = "/member/info"
            if(accessToken) {
                fetch(url, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ` + accessToken
                    }
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        console.log(data.data);

                        document.getElementById('name').value = data.data.userName || '';
                        document.getElementById('address').value = data.data.userAddress || '';
                        document.getElementById('phone').value = data.data.userPhonenumber || '';
                        document.getElementById('birthday').value = data.data.userBirthday || '';
                    })
                    .catch(error => {
                        console.error('Error : ', error);
                    });
            } else {
                console.log("토큰을 찾을 수 없습니다.");
            }

            document.addEventListener('DOMContentLoaded', function (){
                const memberUpdateForm = document.getElementById('updateForm');
                if (memberUpdateForm){
                    memberUpdateForm.addEventListener('submit', function (e){
                       e.preventDefault();
                       const requiredFields = [
                           {id: 'name', name: '이름'},
                           {id: 'address', name: '주소'},
                           {id: 'phone', name: '전화번호'},
                           {id: 'birthday', name: '생년월일'}
                       ];

                        for (const field of requiredFields) {
                            const element = document.getElementById(field.id);
                            if (!element || !element.value.trim()) {
                                alert(`${'${field.name}'} 필드는 필수입니다.`);
                                element.focus();
                                return;
                            }
                        }

                        const updateDto = {
                            userName: document.getElementById('name').value.trim(),
                            userAddress: document.getElementById('address').value + ' ' + (document.getElementById('address_detail').value || ''),
                            userPhonenumber: document.getElementById('phone').value.trim(),
                            userBirthday: document.getElementById('birthday').value
                        };

                        const formData = new FormData();
                        formData.append('boardRequestDto', new Blob([JSON.stringify(updateDto)], { type: 'application/json' }));

                        fetch('/member/update', {
                            method: 'PUT',
                            headers: {
                                'Authorization': `Bearer ` + accessToken
                            },
                            body: formData,
                        }).then(response => {
                                if (!response.ok){
                                    return response.json().then(error => {
                                        throw new Error(error.message || '회원정보수정에 실패했습니다.');
                                    });
                                }
                                return response.json();
                            }).then(data => {
                                alert('회원정보수정에 성공했습니다.');
                                location.href = '/member/memberProfile'
                            }).catch(error => {
                            console.error('Error : ', error);
                            alert(error.message || '게시글 등록에 실패했습니다. 다시 시도해주세요.');
                        });
                    });
                } else {
                    console.error('memberUpdateForm 요소를 찾을 수 없습니다.');
                }
            });


        });

        document.addEventListener('DOMContentLoaded', function() {
            const addressInput = document.getElementById('address');
            if (addressInput) {
                addressInput.addEventListener('click', function() {
                    openAddressSearch();
                });
            } else {
                console.error('주소 입력 필드를 찾을 수 없습니다.');
            }
        });

        function openAddressSearch() {
            new daum.Postcode({
                oncomplete: function(data) {
                    let addr = '';
                    let extraAddr = '';

                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;
                    } else {
                        addr = data.jibunAddress;
                    }

                    if(data.userSelectedType === 'R'){
                        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                            extraAddr += data.bname;
                        }
                        if(data.buildingName !== '' && data.apartment === 'Y'){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        if(extraAddr !== ''){
                            extraAddr = ' (' + extraAddr + ')';
                        }
                    }

                    document.getElementById('address').value = addr + extraAddr;
                    document.getElementById('address').dispatchEvent(new Event('input', { bubbles: true }));
                    document.getElementById('detailAddressContainer').style.display = 'block';
                    document.getElementById('address_detail').focus();
                }
            }).open();
        }
    </script>

</head>
<body>

<!-- 헤더 -->
<jsp:include page="/WEB-INF/views/member/header/member_header.jsp" flush="true" />

<!-- 메인 콘텐츠 -->
<body class="d-flex flex-column min-vh-100">
<main class="flex-grow-1">
    <div class="container mx-auto my-4" style="max-width: 1260px;">
        <div class="card-header">
            <h4 class="card-title">회원정보 수정</h4>
        </div>
        <div class="card-body m-4">
            <form action="/member/update" method="post" id="updateForm">
                <div class="mb-3">
                    <h5 for="name" class="form-label mb-2">이름</h5>
                    <input type="text" class="form-control" id="name" name="name" required>
                </div>
                <div class="mb-3">
                    <h5 for="address" class="form-label mb-2">주소</h5>
                    <input type="text" class="form-control" id="address" name="address" required readonly>
                </div>
                <div class="mb-3" id="detailAddressContainer" style="display: none;">
                    <h5 for="address_detail" class="form-label mb-2">상세 주소</h5>
                    <input type="text" class="form-control" id="address_detail" name="address_detail">
                </div>
                <div class="mb-3">
                    <h5 for="phone" class="form-label mb-2">전화 번호</h5>
                    <input type="text" class="form-control" id="phone" name="phone" required>
                </div>
                <div class="mb-3">
                    <h5 for="birthday" class="form-label mb-2">생년월일</h5>
                    <input type="date" class="form-control" id="birthday" name="birthday" autocomplete="off">
                </div>
                <button type="submit" class="btn btn-primary" style="float: right;">완료</button>
            </form>
        </div>
    </div>
</main>
</body>

<!-- 푸터 -->
<jsp:include page="/WEB-INF/views/common/footer/footer.jsp" flush="true" />

<!-- 맨 위로 가기 버튼 -->
<div class="back-top"><i class="bi bi-arrow-up-short"></i></div>

</body>
</html>
