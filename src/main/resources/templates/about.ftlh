
<#import "layout.ftlh" as l>

<@l.page title="About" active="about">

    <h1 class="mb-4">About This Website</h1>

    <div class="row g-4">

        <div class="col-md-6">
            <img src="/images/batman.jpg"
                 class="img-fluid rounded"
                 alt="Batman">
        </div>

        <div class="col-md-6">

            <h2>DC Character Gallery</h2>

            <p>
                I created this website to showcase some of my
                favorite DC characters, including Batman,
                Superman, The Flash, Joker, and Harley Quinn.
            </p>

            <p>
                Users can search for characters, view their
                details, and add, edit, or delete characters.
            </p>

            <h3 class="h5 mt-4">How This Website Works</h3>

            <p>
                The frontend uses HTML, Bootstrap, and FreeMarker
                templates to display character information.
                A shared layout keeps the navigation consistent.
            </p>

            <p>
                The application uses Spring Boot MVC.
                The controller handles requests, the service
                handles application logic, and the repository
                uses Spring Data JPA to access the Neon
                PostgreSQL database. The REST API also
                provides character data as JSON.
            </p>

            <a href="/" class="btn btn-primary mt-3">
                Back to Gallery
            </a>

        </div>

    </div>

</@l.page>
