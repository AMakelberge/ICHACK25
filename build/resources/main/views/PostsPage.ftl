<html lang="EN">
<head>
    <link rel="stylesheet" href="/assets/style.css">
    <title>Kotlin WebApp</title>
</head>

<body>
    <div class="header">
        <a href="/index" class="logo">Kotlin WebApp</a>
        <div class="header-right">
            <a href="/index">Scrabble Calculator</a>
            <a href="/posts">Posts</a>
        </div>
    </div>

    <h1>Posts:</h1>

    <table>
        <thead>
            <tr>
                <#list headers as header>
                    <th>${header}</th>
                </#list>
            </tr>
        </thead>
        <tbody>
            <#list rows as row>
                <tr>
                    <#list row as item>
                        <td>${item}</td>
                    </#list>
                </tr>
            </#list>
        </tbody>
    </table>

    <h1>Enter your own post: </h1>
    <form action="/submitPost" method="post">
        <label for="namefield">Title:</label>
        <input type="text" id="namefield" name="title">
        <label for="namefield">Body:</label>
        <input type="text" id="namefield" name="body">
        <input type="submit" value="Submit">
    </form>

    <h1>Remove a post: </h1>
    <form action="/removePost" method="post">
        <label for="namefield">ID:</label>
        <input type="text" id="namefield" name="removeID">
        <input type="submit" value="Submit">
    </form>

</body>
</html>