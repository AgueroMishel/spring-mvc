<!DOCTYPE html>
<html>
    <head>
        <title>Messages</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script>
            let getNotify = function() {
                let xhr = new XMLHttpRequest();
                xhr.responseType = "json";
                xhr.open("GET", "/notify", true);
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        console.log(xhr.response);
                        if (xhr.response === true) {
                            document.getElementById("getMessages").submit();
                        }
                    }
                };
                xhr.send();
            }
        </script>
    </head>
    <body onload="getNotify();">
        <form id="postMessages" action="/messages" method="post">
            <label for="user">User</label>
            <input type="text" id="user" name="user" value="${user}" readonly>
            </br>
            <textarea id="message" name="message" rows="4" cols="50" placeholder="message here"></textarea>
            </br>
            <input type="submit" value="Send">
        </form>
        <form id="getMessages" action="/messages" method="get">
            <!--input type="submit" value="Get Messages"-->
        </form>
        <p>Messages</p>
        <table border=1>
            <tr>
                <th>ID</th>
                <th>User</th>
                <th>Content</th>
            </tr>
            <#list messages as message>
            <tr>
                <td>${message.id}</td>
                <td>${message.user}</td>
                <td>${message.content}</td>
            </tr>
            </#list>
        </table>
    </body>
</html>