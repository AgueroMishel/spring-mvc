<!DOCTYPE html>
<html>
    <head>
        <title>Messages</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <form action="/messages" method="post">
            <label for="user">User</label>
            <input type="text" id="user" name="user" value="${user}" readonly>
            </br>
            <textarea id="message" name="message" rows="4" cols="50" placeholder="message here"></textarea>
            </br>
            <input type="submit" value="Send">
        </form>
        <form action="/messages" method="get">
            <input type="submit" value="Get Messages">
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