<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fill in the Kotlin Code</title>
    <link rel="stylesheet" href="/static/css/test.css" type="text/css">
</head>
<body>
<nav class="navbar">
    <div class="navbar-brand">AlgoCraft</div>
    <ul class="navbar-links">
        <li><a href="/">Home</a></li>
        <li><a href="/generate">Generate</a></li>
    </ul>
</nav>

<div class="container">
    <!-- Code Box on the Left -->
    <div class="code-box">
        <pre>
<#assign counter = 0> <!-- Initialize the counter outside the loop -->
<#list lines as line>
    <#if line?contains("//")>
    <#-- Count leading spaces in the line before the comment -->
        <#assign indentation = line?substring(0, line?index_of(line?trim))>
        ${line} <!-- Print the line with comment -->

        <span class="dropzone" id="drop-${counter}" style="margin-left: ${indentation?length * 8}px"
              ondragover="allowDrop(event)"
              ondrop="drop(event)">Drop here</span> <!-- Apply margin-left based on indentation -->
    <#else>
        ${line} <!-- Print non-comment lines normally -->
    </#if>
    <#assign counter = counter + 1>
    <!-- Manually increment the counter -->
</#list>
        </pre>
        <button id="checkButton">Check</button>
    </div>

    <!-- Drag Items on the Right -->
    <div class="options">
        <h3>Options</h3>
        <#list keywordMap as key, value>
            <div class="draggable" draggable="true" ondragstart="drag(event)" id="option-${key}">${value}</div>
        </#list>
    </div>
</div>

<script>
    function allowDrop(event) {
        event.preventDefault();
    }

    function drag(event) {
        event.dataTransfer.setData("text", event.target.id);
    }

    function drop(event) {
        event.preventDefault();
        let data = event.dataTransfer.getData("text");
        let element = document.getElementById(data);
        let dropzone = event.target;

        if (dropzone.classList.contains("dropzone")) {
            dropzone.innerHTML = ""; // Clear placeholder text
            dropzone.appendChild(element);

            // Resize dropzone to match dropped element size
            dropzone.style.width = element.offsetWidth + "px";
            dropzone.style.height = element.offsetHeight + "px";

            // Center text vertically
            dropzone.style.display = "inline-flex";
            dropzone.style.alignItems = "center";
        }
    }

    // Wait until the DOM is loaded
    document.addEventListener("DOMContentLoaded", function () {
        // Add event listener for the button click
        document.getElementById("checkButton").addEventListener("click", checkAnswers);
    });

    function checkAnswers() {
        var keywordMap = {
            <#list keywordMap as key, value>
            "${key}": "${value}"<#if key_has_next>,</#if>
            </#list>
        };

        console.log(keywordMap)

        // Loop through the keys in the keywordMap object
        for (let key in keywordMap) {
            let dropzone = document.getElementById("drop-" + key);
            let child = dropzone.firstElementChild;

            console.log(key)
            console.log(dropzone)
            console.log(child)
            console.log(child.textContent)
            console.log(keywordMap[key])

            // Compare the dropzone child text content to the expected value
            if (child && child.textContent === keywordMap[key]) {
                dropzone.classList.add("correct");
                dropzone.classList.remove("wrong");
            } else {
                dropzone.classList.add("wrong");
                dropzone.classList.remove("correct");
            }
        }
    }
</script>

</body>
</html>

