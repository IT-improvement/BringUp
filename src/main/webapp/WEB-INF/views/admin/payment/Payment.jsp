<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>부트페이</title>
</head>
<body>
<h1>제품 결제</h1>
<span>제품 idx : </span><input type="number" id="item_idx"><br>
<span>제품 가격 : </span><input type="number" id="item_price"><br>
<span>제품 이름 : </span><input type="text" id="item_name"><br>
<span>제품 수량 : </span><input type="number" id="item_amount"><br>
<button id="paymentButton">결제하기</button>
</body>
<script
        src="https://js.bootpay.co.kr/bootpay-4.3.3.min.js"
        type="application/javascript"
></script>
<script>
    document
        .getElementById("paymentButton")
        .addEventListener("click", async function () {
            saveOrderDto = {
                item: {
                    itemIdx: document.querySelector("#item_idx").value,
                    itemPrice: document.querySelector("#item_price").value,
                    itemName: document.querySelector("#item_name").value,
                    itemAmount: document.querySelector("#item_amount").value,
                },
            };

            var orderIdx;

            await fetch("/api/v1/order", {
                method: "POST",
                headers: {
                    "Content-type": "application/json",
                },
                body: JSON.stringify(saveOrderDto),
            })
                .then((response) => response.json())
                .then((data) => {
                    if (data.code == 0) {
                        // 성공적으로 DB에 저장되었다면, orderIdx를 data로 반환한다.
                        orderIdx = data.data;
                    } else {
                        console.log(data.message);
                    }
                })
                .catch((error) => {
                    // 오류 처리
                    console.error("Error:", error);
                });

            // 부트페이 결제 로직
            try {
                // 이 코드는 개발 문서에 나와있는 그대로.
                // 전달 데이터를 따로 뺌.
                // 일반결제 요청하기 로직. 팝업 설정과 승인 분리 설정만 추가함.
                const requestData = {
                    // apikey
                    application_id: "",
                    // 가격 수정
                    price: saveOrderDto.item.itemPrice,
                    order_name: "테스트결제",
                    // order테이블에서 받아온 idx 값을 넣는다.
                    order_id: orderIdx,
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
                            // 가격 수정
                            price: saveOrderDto.item.itemPrice,
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

                switch (response.event) {
                    case "issued":
                        // 가상계좌 입금 완료 처리
                        break;
                    case "confirm":
                        // receipt_id를 dto에 담아서
                        const dto = {
                            receiptId: response.receipt_id,
                        };

                        //  '/api/v1/bootpay/check' 로 보낸다. (구현 예정)
                        fetch("/api/v1/bootpay/check", {
                            method: "POST",
                            headers: {
                                "Content-type": "application/json",
                            },
                            body: JSON.stringify(dto),
                        })
                            .then((res) => res.json())
                            .then((result) => {
                                // 정상적으로 처리되었는지 메시지를 띄운다.
                                // 정상적으로 승인이 되면 코드에 0을 반환할 것이고,
                                if (result.code === 0) {
                                    // 결제창을 닫는다.
                                    Bootpay.destroy();
                                    alert(result.message);

                                    // 아니라면 다른 숫자가 반환된다.
                                } else {
                                    alert(result.message);
                                }
                                location.replace("/");
                            })
                            .catch((err) => {
                                console.error(err);
                            });
                        break;
                    case "done":
                        // 결제 완료 처리
                        alert("결제 done");
                    case "cancel":
                        // 결제 취소 처리
                        alert("결제 cancel");
                    default:
                        break;
                }
            } catch (error) {
                // 결제 진행중 오류 발생
                // e.error_code - 부트페이 오류 코드
                // e.pg_error_code - PG 오류 코드
                // e.message - 오류 내용
                console.log(error.message);
            }
        });
</script>
</html>