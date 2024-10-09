function initiatePayment(productName, productPrice, itemIndex) {
    fetch('/admin/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('accessToken')
        },
        body: JSON.stringify({
            itemIndex: itemIndex,
            itemName: productName,
            price: productPrice
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
        payment_name: paymentData.itemName,
        payment_id: paymentData.paymentId,
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