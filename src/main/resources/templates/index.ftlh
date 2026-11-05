
<#import "layout.ftlh" as l>

<@l.page title="Home" active="home">

    <h1 class="mb-4">DC Character Gallery</h1>

    <p>
        This website shows some popular characters
        from the DC universe.
    </p>

    <!-- Search and Filter -->
    <form method="get" action="/" class="mb-4">

        <div class="row g-3">

            <div class="col-md-5">
                <label for="name" class="form-label">
                    Search Characters:
                </label>

                <input type="text"
                       id="name"
                       name="name"
                       class="form-control"
                       value="${name!''}"
                       placeholder="Search by name">
            </div>

            <div class="col-md-5">
                <label for="role" class="form-label">
                    Filter by Role:
                </label>

                <input type="text"
                       id="role"
                       name="role"
                       class="form-control"
                       value="${role!''}"
                       placeholder="Example: Hero  or Villain">
            </div>

            <div class="col-md-2 d-flex align-items-end">
                <button type="submit"
                        class="btn btn-primary w-100">
                    Search
                </button>
            </div>

        </div>

        <#if name?has_content || role?has_content>
            <a href="/" class="btn btn-outline-secondary mt-3">
                Clear Search
            </a>
        </#if>

    </form>

    <!-- Number of Characters -->
    <p class="text-muted">
        ${characters?size} character(s) found
    </p>

    <!-- Character Cards -->
    <div class="row g-4">

        <#list characters as character>

            <div class="col-md-4">

                <div class="card h-100 shadow-sm">

                    <#assign picture = (character.imageUrl!'')?trim>

                    <#if picture?has_content>
                        <img src="${picture}"
                             class="card-img-top"
                             alt="${character.name}"
                             style="height: 250px; object-fit: cover;">
                    <#else>
                        <div class="card-img-top bg-secondary-subtle
                                    d-flex align-items-center justify-content-center"
                             style="height: 250px;"
                             role="img"
                             aria-label="No character image available">
                            <span class="text-secondary">
                                No Image Available
                            </span>
                        </div>
                    </#if>

                    <div class="card-body d-flex flex-column">

                        <h5 class="card-title">
                            ${character.name}
                        </h5>

                        <h6 class="card-subtitle mb-2 text-muted">
                            ${character.location!'Unknown Location'}
                        </h6>

                        <p class="card-text">
                            ${(character.description!'No description available.')?truncate(140)}
                        </p>

                        <p class="card-text">
                            <strong>Role:</strong>
                            ${character.role!'Not listed'}
                        </p>

                        <p class="card-text">
                            <strong>Species:</strong>
                            ${character.species!'Not listed'}
                        </p>

                        <a href="/characters/${character.id?c}"
                           class="btn btn-primary mt-auto">
                            View Details
                        </a>

                    </div>

                </div>

            </div>

        <#else>

            <div class="col-12 text-center py-5">

                <h3>No characters found</h3>

                <p class="text-muted">
                    Try a different name or role.
                </p>

                <a href="/" class="btn btn-primary">
                    Show All Characters
                </a>

            </div>

        </#list>

    </div>

</@l.page>
