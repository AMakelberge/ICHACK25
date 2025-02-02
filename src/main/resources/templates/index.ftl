<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Awaiting name</title>
    <link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
<nav class="navbar">
    <div class="navbar-brand">Awaiting name</div>
    <ul class="navbar-links">
        <li><a href="/">Home</a></li>
        <li><a href="/another">Another Page</a></li>
        <li><a href="/generate">Generate</a></li>
    </ul>
</nav>

<main class="container">
    <h1>Welcome to My Education Tool</h1>
    <p>This is our initial homepage, served as static HTML!</p>
    <p>
        We do not use any JavaScript in this example. All logic is performed
        on the server by Kotlin.
    </p>

    <p class = 'prompt'>Let's build your algorithm!</p>
    <form action="/generate" method="post">
    <label for="prompt">Prompt:</label>
    <input type="text" id="prompt" name="prompt"></input>
    <input type="submit" value="Submit">
    </form>
</main>
</body>
</html>
