<%--
  Created by IntelliJ IDEA.
  User: 주승재
  Date: 2024-08-30
  Time: 오후 3:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat Application</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .hidden {
            display: none;
        }
        .chat-container {
            margin-top: 20px;
        }
        .chat-message {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }
        .chat-message i {
            border-radius: 50%;
            display: inline-block;
            height: 40px;
            width: 40px;
            line-height: 40px;
            text-align: center;
            color: white;
            margin-right: 10px;
        }
        .chat-message p {
            background-color: #f1f1f1;
            border-radius: 10px;
            padding: 10px;
            margin: 0;
        }
        .event-message {
            color: gray;
            font-style: italic;
            margin-bottom: 10px;
        }
        .message-area {
            height: 400px;
            overflow-y: auto;
            border: 1px solid #ddd;
            padding: 10px;
        }
        .message-area ul {
            list-style: none;
            padding: 0;
        }
    </style>
</head>
<body>

<!-- Username 입력 페이지 -->
<div id="username-page" class="container">
    <h1 class="text-center">Enter Chat Room</h1>
    <form id="usernameForm">
        <div class="form-group">
            <label for="name">Enter Username:</label>
            <input type="text" id="name" class="form-control" placeholder="Enter your username" required />
        </div>
        <button type="submit" class="btn btn-primary btn-block">Join Chat</button>
    </form>
</div>

<!-- 채팅 페이지 -->
<div id="chat-page" class="container chat-container hidden">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <div class="message-area" id="messageArea">
                <ul></ul>
            </div>
            <form id="messageForm">
                <div class="form-group">
                    <input type="text" id="message" class="form-control" placeholder="Type your message" required />
                </div>
                <button type="submit" class="btn btn-success btn-block">Send Message</button>
            </form>
            <div class="connecting">Connecting...</div>
        </div>
    </div>
</div>

<!-- 유저 리스트 -->
<div class="container chat-container hidden">
    <h4>Active Users</h4>
    <ul id="list" class="list-group"></ul>
</div>

<!-- JQuery & Bootstrap JS (필요하면 추가 가능) -->
<script src="https://code.jquery.com/jquery-3.6.1.min.js"
        integrity="sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<!-- WebSocket 연결 및 채팅 로직 -->
<script src="/path-to-sockjs/sockjs.min.js"></script>
<script src="/path-to-stomp/stomp.min.js"></script>
<script src="your-chat-application.js"></script> <!-- JavaScript 파일 경로 추가 -->

</body>
</html>
