<script src="https://js.bootpay.co.kr/bootpay-4.3.3.min.js" type="application/javascript"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<button id="paymentButton">결제하기</button>
</body>
</html>

<script>
    document
        .getElementById("paymentButton")
        .addEventListener("click", async function () {
            // 부트페이 결제 로직
            try {
                // 이 코드는 개발 문서에 나와있는 그대로.
                // 전달 데이터를 따로 뺌.
                // 일반결제 요청하기 로직. 팝업 설정과 승인 분리 설정만 추가함.
                const requestData = {
                    // apikey
                    application_id: "api키",
                    price: 1000,
                    order_name: "테스트결제",
                    order_id: "TEST_ORDER_ID",
                    // 아래의 두 속성 지정하지 않을 시 통합 결제
                    //   pg: "카카오",
                    //   method: "간편",
                    tax_free: 0,
                    user: {
                        id: "회원아이디",
                        username: "회원이름",
                        phone: "01000000000",
                        email: "test@test.com",
                    },
                    items: [
                        {
                            id: "item_id",
                            name: "테스트아이템",
                            qty: 1,
                            price: 1000,
                        },
                    ],
                    extra: {
                        open_type: "popup", // 팝업 형태로 결제 창 열기
                        popup: {
                            width: 800, // 팝업 창의 너비 (픽셀)
                            height: 600, // 팝업 창의 높이 (픽셀)
                        },
                        card_quota: "0,2,3",
                        escrow: false,
                        separately_confirmed: true, // 승인 전 로직 필요할 시
                    },
                };
                // 위의 Data로 부트페이 결제 요청.
                const response = await Bootpay.requestPayment(requestData);

            } catch (error) {
                // 결제 진행중 오류 발생
                // e.error_code - 부트페이 오류 코드
                // e.pg_error_code - PG 오류 코드
                // e.message - 오류 내용
                console.log(error.message);
            }
        });
</script>
