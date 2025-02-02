<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AlgoCraft Homepage</title>
    <link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
<nav class="navbar">
    <div class="navbar-brand">AlgoCraft</div>
    <ul class="navbar-links">
        <li><a href="/">Home</a></li>
        <li><a href="/generate">Generate</a></li>
    </ul>
</nav>

<main class="container">
    <h1>Welcome to AlgoCraft!</h1>
    <p>Enter the algorithm to learn and begin practising.</p>

    <p class = 'prompt'>Let's build your algorithm!</p>
    <form action="/generate" method="post">
    <label for="prompt">Prompt:</label>
    <input type="text" id="prompt" name="prompt">
    <input type="submit" value="Submit">
    </form>
</main>
</body>
</html>