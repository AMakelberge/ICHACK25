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

        <h1>Scrabble calculator</h1>
        <p>This is a very simple Kotlin webapp to calculate scrabble scores for words.</p>

        <form action="/submit" method="post">
            <label for="namefield">Scrabble word 1:</label>
            <input type="text" id="namefield" name="word1">
            <label for="namefield">Scrabble word 2:</label>
            <input type="text" id="namefield" name="word2">
            <label for="namefield">Scrabble word 3:</label>
            <input type="text" id="namefield" name="word3">
            <label for="namefield">Scrabble word 4:</label>
            <input type="text" id="namefield" name="word4">
            <label for="namefield">Scrabble word 5:</label>
            <input type="text" id="namefield" name="word5">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>