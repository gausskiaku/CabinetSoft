<%-- 
    Document   : index
    Created on : 29 juil. 2016, 14:52:51
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <title>Envoi Message</title>

        <meta charset="UTF-8">

        <meta name="viewport" content="width=device-width">

    </head>

    <body>

        <div>

            <input type="text" id="messageinput"/>

        </div>
        <div>

            <button type="button" onclick="openSocket();" >Open</button>
            <button type="button" onclick="send();" >Send</button>

            <button type="button" onclick="closeSocket();" >Close</button>

        </div>

        <!-- Server responses get written here -->

        <div id="messages"></div>
        <!-- Script to utilise the WebSocket -->

        <script type="text/javascript">

                    var webSocket;
                    var messages = document.getElementById("messages");
                    function openSocket() {
                    // Ensures only one connection is open at a time
                    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {

                    writeResponse("WebSocket is already opened.");
                            return;
                    }

                    // Create a new instance of the websocket

                    webSocket = new WebSocket("ws://localhost:8080/EmmaProject/echo");
                            /**
                             * Binds functions to the listeners for the websocket.
                             */


                            webSocket.onopen = function (event) {

                            // For reasons I can't determine, onopen gets called twice

                            // and the first time event.data is undefined.
                            // Leave a comment if you know the answer.


                            if (event.data === undefined)
                                    return;
                                    writeResponse(event.data);
                            };
                            webSocket.onmessage = function (event) {

                            writeResponse(event.data);
                            };
                            webSocket.onclose = function (event) {

                            writeResponse("Connection closed");
                            };
                    }

            /**
             
             * Sends the value of the text input to the server
             
             */

            function send() {
            var file = document.getElementById("messageinput").files[0];
                    var reader = new FileReader();
                    // Sends the result of the file read as soon as the reader has
                    // completed reading the image file.
                    reader.onloadend = function () {
                    webSocket.send(reader.result);
                    };
                    // Make sure the file exists and is an image
                    if (file && file.type.match("image")) {
            reader.readAsDataURL(file);
            }
            }




            function closeSocket() {

            webSocket.close();
            }

            function writeResponse(text) {
            messages.innerHTML += "<br/>" + text;
            }
            function writeResponse(image){
            messages.innerHTML += "
                    " + " < img alt = "" src = '" + image + "' / > ";
                    }

        </script>

    </body>

</html>
