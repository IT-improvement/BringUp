<%--
  Created by IntelliJ IDEA.
  User: 주승재
  Date: 2024-10-10
  Time: 오후 2:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/main.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <script>
        var stompClient = null;
        $("#conversation").show();
        console.log('connect');
        var socket = new SockJS('/gs-guide-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });

        // function setConnected(connected) {
        //     $("#connect").prop("disabled", connected);
        //     $("#disconnect").prop("disabled", !connected);
        //     if (connected) {
        //         $("#conversation").show();
        //     }
        //     else {
        //         $("#conversation").hide();
        //     }
        //     $("#greetings").html("");
        // }

        // function connect() {
        //     console.log('connect');
        //     var socket = new SockJS('/gs-guide-websocket');
        //     stompClient = Stomp.over(socket);
        //     stompClient.connect({}, function (frame) {
        //         setConnected(true);
        //         console.log('Connected: ' + frame);
        //         stompClient.subscribe('/topic/greetings', function (greeting) {
        //             showGreeting(JSON.parse(greeting.body).content);
        //         });
        //     });
        // }

        // function disconnect() {
        //     if (stompClient !== null) {
        //         stompClient.disconnect();
        //     }
        //     setConnected(false);
        //     console.log("Disconnected");
        // }

        function sendName() {
            stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
        }

        function showGreeting(message) {
            $("#greetings").append("<tr><td>" + message + "</td></tr>");
        }

        $(function () {
            $("form").on('submit', function (e) {
                e.preventDefault();
            });
            // $( "#connect" ).click(function() { connect(); });
            // $( "#disconnect" ).click(function() { disconnect(); });
            $( "#send" ).click(function() { sendName(); });
        });
    </script>
    <title>Title</title>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
    enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="connect">WebSocket connection:</label>
                    <button id="connect" class="btn btn-default" type="submit">Connect</button>
                    <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                    </button>
                </div>
            </form>
        </div>
        <div class="col-md-6">
            <form class="form-inline">
                <div class="form-group">
                    <label for="name">chat?</label>
                    <input type="text" id="name" class="form-control" placeholder="Your name here...">
                </div>
                <button id="send" class="btn btn-default" type="submit">Send</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table id="conversation" class="table table-striped">
                <thead>
                <tr>
                    <th>Greetings</th>
                </tr>
                </thead>
                <tbody id="greetings">
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
