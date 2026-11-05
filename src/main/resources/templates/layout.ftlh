
<#-- Shared layout for all DC Character Gallery pages -->
<#macro page title active="">

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>${title} | DC Character Gallery</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>

<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">

            <a class="navbar-brand" href="/">DC Gallery</a>

            <div class="navbar-nav">

                <a class="nav-link ${(active == 'home')?then('active', '')}"
                   href="/">Home</a>

                <a class="nav-link ${(active == 'about')?then('active', '')}"
                   href="/about">About</a>

                <a class="nav-link ${(active == 'new')?then('active', '')}"
                   href="/characters/new">Add a Character</a>

            </div>
        </div>
    </nav>

    <main class="container py-5">

        <#-- Show a success message after a redirect -->
        <#if message??>
            <div class="alert alert-success" role="alert">
                ${message}
            </div>
        </#if>

        <#-- Page-specific content goes here -->
        <#nested>

    </main>

    <footer class="bg-light text-center py-3 mt-5">
        <p class="text-muted mb-0">
            DC Character Gallery | Spring Boot MVC + FreeMarker
        </p>
    </footer>

</body>
</html>

</#macro>
