<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제 완료</title>
    <link rel="stylesheet" href="path/to/your/styles.css"> <!-- 필요하면 스타일을 추가 -->
</head>
<body>
<h1>결제 완료 페이지</h1>
<div id="paymentSuccessInfo" style="margin-top: 20px;">
    <h2>결제 정보</h2>
    <p>결제 상태: <span id="resultStatus"></span></p>
    <p>상품명: <span id="resultOrderName"></span></p>
    <p>결제 금액: <span id="resultPrice"></span>원</p>
</div>
</body>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const paymentStatus = sessionStorage.getItem("paymentStatus");
        const paymentResponse = JSON.parse(sessionStorage.getItem("paymentResponse"));

        if (paymentStatus && paymentResponse) {
            document.getElementById("resultStatus").textContent = paymentStatus;
            document.getElementById("resultOrderName").textContent = paymentResponse.itemName;
            document.getElementById("resultPrice").textContent = paymentResponse.price;
        } else {
            alert("결제 정보가 없습니다.");
        }
    });
</script>
</html>
