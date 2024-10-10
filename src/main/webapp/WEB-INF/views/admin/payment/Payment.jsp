<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 테스트 페이지</title>
    <script src="https://js.bootpay.co.kr/bootpay-4.3.3.min.js" type="application/javascript"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h1>결제 테스트</h1>
<form id="paymentForm">
    <label for="itemName">제품 이름:</label>
    <input type="text" id="itemName" name="itemName" required><br>
    <label for="itemPrice">제품 가격:</label>
    <input type="number" id="itemPrice" name="itemPrice" required><br>
    <label for="itemIndex">제품 인덱스:</label>
    <input type="number" id="itemIndex" name="itemIndex" required><br>
    <button type="button" onclick="initiatePayment()">결제하기</button>
</form>

<script>
    function initiatePayment() {
        var itemName = $('#itemName').val();
        var itemPrice = $('#itemPrice').val();
        var itemIdx = $('#itemIndex').val();

        fetch('/admin/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            },
            body: JSON.stringify({
                itemIdx: itemIdx,
                itemName: itemName,
                price: itemPrice,
                price: itemPrice
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.paymentId) {
                    startPayment(data);
                } else {
                    alert('결제 생성에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('결제 생성 중 오류 발생:', error);
                alert('결제 생성 도중 문제가 발생했습니다.');
            });
    }

    function startPayment(paymentData) {
        Bootpay.requestPayment({
            application_id: paymentData.applicationId,
            price: paymentData.price,
            order_name: paymentData.itemName,
            order_id: paymentData.paymentId,
            user: {
                id: paymentData.userId,
                username: paymentData.username,
                phone: paymentData.phone,
                email: paymentData.email
            },
            items: [
                {
                    id: paymentData.itemId,
                    name: paymentData.itemName,
                    qty: 1,
                    price: paymentData.price
                }
            ]
        }).error(function(data) {
            console.error("결제 에러 발생:", data);
        }).done(function(data) {
            alert("결제가 완료되었습니다.");
            // 결제 완료 후 receipt_id를 서버로 전송하여 결제 상태 업데이트
            notifyPaymentCompletion(data.receipt_id, paymentData.paymentId);
        });
    }

    function notifyPaymentCompletion(receiptId, paymentId) {
        fetch('/admin/complete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
            },
            body: JSON.stringify({
                receiptId: receiptId,
                paymentId: paymentId
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    console.log('결제 완료가 서버에 정상적으로 전달되었습니다.');
                } else {
                    console.error('결제 완료 알림 실패:', data.message);
                }
            })
            .catch(error => {
                console.error('결제 완료 알림 중 오류 발생:', error);
            });
    }
</script>
</body>
</html>