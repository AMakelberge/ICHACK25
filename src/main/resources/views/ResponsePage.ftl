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

    <h1>Your total score is: ${totScore}</h1>
    <ul>
        <#list scores as item, score>
            <li>${item} - ${score}</li>
        <#else>
            <p>Nothing was input</p>
        </#list>
    </ul>
</body>
</html>