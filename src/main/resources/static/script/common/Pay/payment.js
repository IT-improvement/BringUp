document.addEventListener("DOMContentLoaded", function () {
    const paymentButton = document.getElementById("paymentButton");

    paymentButton.removeEventListener("click", handlePayment);
    paymentButton.addEventListener("click", handlePayment, { once: true });

    async function handlePayment() {
        const accessToken = localStorage.getItem('accessToken');
        if (!accessToken) {
            alert("로그인이 필요합니다. 로그인 후 다시 시도해주세요.");
            return;
        }

        const item = sessionStorage.getItem("itemIdx");
        if (item === null) {
            alert("결제 정보가 없습니다.");
            return;
        }

        const saveOrderDto = {
            "itemIdx": item
        };

        let paymentResponse;

        try {
            const response = await fetch("/admin/order", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": "Bearer " + accessToken
                },
                body: JSON.stringify(saveOrderDto),
            });

            const data = await response.json();
            if (data.data && data.data.orderIndex) {
                paymentResponse = data.data;
            } else {
                console.error(data.message);
                alert("주문 정보 저장에 실패했습니다. 다시 시도해주세요.");
                return;
            }
        } catch (error) {
            console.error("Error:", error);
            alert("주문 정보 저장 중 오류가 발생했습니다. 다시 시도해주세요.");
            return;
        }

        if (!paymentResponse) {
            return;
        }

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

            const response = await Bootpay.requestPayment(requestData);

            let eventDetail = { status: response.event, paymentResponse: paymentResponse };

            switch (response.event) {
                case "confirm":
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
                            console.log(result);
                            if (result && result.data && result.data.status === 1) {
                                eventDetail.status = "done";
                                dispatchPaymentResultEvent(eventDetail);
                            } else {
                                eventDetail.status = "failed";
                                alert("결제가 실패했습니다. 다시 시도해주세요.");
                                dispatchPaymentResultEvent(eventDetail);
                            }
                        })
                        .catch((err) => {
                            console.error(err);
                            alert("결제 승인 중 오류가 발생했습니다. 다시 시도해주세요.");
                        });
                    break;
                case "done":
                    eventDetail.status = "done";
                    dispatchPaymentResultEvent(eventDetail);
                    break;
                case "cancel":
                    alert("결제가 취소되었습니다.");
                    eventDetail.status = "cancel";
                    dispatchPaymentResultEvent(eventDetail);
                    break;
                default:
                    break;
            }
        } catch (error) {
            if (error.message.includes("User cancelled")) {
                alert("결제가 취소되었습니다.");
            } else {
                console.log(error.message);
                alert("결제 요청 중 오류가 발생했습니다. 다시 시도해주세요.");
            }
        }
    }

    function dispatchPaymentResultEvent(eventDetail) {
        const paymentEvent = new CustomEvent("paymentResult", { detail: eventDetail });
        document.dispatchEvent(paymentEvent);
    }

    // 결과를 처리하는 이벤트 리스너
    document.addEventListener("paymentResult", function (event) {
        const status = event.detail.status;

        if (status === "done") {
            alert("결제가 성공적으로 완료되었습니다.");
        } else if (status === "failed") {
            alert("결제가 실패하였습니다. 다시 시도해주세요.");
        } else if (status === "cancel") {
            alert("결제가 취소되었습니다.");
        }
    });
});
