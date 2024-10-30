document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("paymentButton").addEventListener("click", async function () {
        const accessToken = localStorage.getItem('accessToken');
        if (!accessToken) {
            alert("로그인이 필요합니다. 로그인 후 다시 시도해주세요.");
            return;
        }

        const item = sessionStorage.getItem("itemIdx");
        if (item === null) {
            alert("아이템인덱스가 없습니다.");
            return;
        }

        // 주문 정보 수집
        const saveOrderDto = {
            "itemIdx": item
        };

        let paymentResponse;

        // 주문 정보 저장 요청
        await fetch("/admin/order", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + accessToken
            },
            body: JSON.stringify(saveOrderDto),
        })
        .then((response) => response.json())
        .then((data) => {
            if (data.data && data.data.orderIndex) {
                // 성공적으로 DB에 저장되었다면 PaymentResponseDto 데이터를 저장
                paymentResponse = data.data;
            } else {
                console.error(data.message);
                alert("주문 정보 저장에 실패했습니다. 다시 시도해주세요.");
                return;
            }
        })
        .catch((error) => {
            console.error("Error:", error);
            alert("주문 정보 저장 중 오류가 발생했습니다. 다시 시도해주세요.");
            return;
        });

        if (!paymentResponse) {
            // 주문 정보가 제대로 저장되지 않았을 경우 종료
            return;
        }

        // Bootpay 결제 요청
        try {
            const requestData = {
                application_id: paymentResponse.applicationId,
                price: paymentResponse.price,
                order_name: paymentResponse.itemName,
                order_id: paymentResponse.orderIndex,
                user: {
                    id: paymentResponse.userId,
                    username: paymentResponse.userName,
                    phone: paymentResponse.phoneNumber,
                    email: paymentResponse.userEmail,
                },
                items: [
                    {
                        id: saveOrderDto.itemIdx,
                        name: paymentResponse.itemName,
                        qty: 1,
                        price: paymentResponse.price,
                    },
                ],
                extra: {
                    open_type: "popup",
                    popup: {
                        width: 800,
                        height: 600,
                    },
                    card_quota: "0,2,3",
                    escrow: false,
                    separately_confirmed: true,
                },
            };

            // Bootpay 결제 요청
            const response = await Bootpay.requestPayment(requestData);

            switch (response.event) {
                case "issued":
                    // 가상계좌 입금 완료 처리
                    break;
                case "confirm":
                    // receipt_id를 dto에 담아서 서버로 전송
                    const dto = {
                        receiptId: response.receipt_id,
                    };

                    fetch("/admin/bootpay/check", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "Authorization": "Bearer " + accessToken
                        },
                        body: JSON.stringify(dto),
                    })
                    .then((res) => res.json())
                    .then((result) => {
                        if (result.code === 0) {
                            Bootpay.destroy();
                            alert(result.message);
                        } else {
                            alert(result.message);
                        }
                        location.replace("/");
                    })
                    .catch((err) => {
                        console.error(err);
                        alert("결제 승인 중 오류가 발생했습니다. 다시 시도해주세요.");
                    });
                    break;
                case "done":
                    // 결제 완료 처리
                    alert("결제 완료되었습니다.");
                    break;
                case "cancel":
                    // 결제 취소 처리
                    alert("결제가 취소되었습니다.");
                    break;
                default:
                    break;
            }
        } catch (error) {
            console.log(error.message);
            alert("결제 요청 중 오류가 발생했습니다. 다시 시도해주세요.");
        }
    });
});
