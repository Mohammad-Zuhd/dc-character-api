
<#import "layout.ftlh" as l>

<@l.page title=character.name active="home">

    <h1 class="mb-4">${character.name}</h1>

    <div class="row">

        <div class="col-md-7">

            <#assign picture = (character.imageUrl!'')?trim>

            <#if picture?has_content>
                <img src="${picture}"
                     class="img-fluid rounded"
                     alt="${character.name}">
            <#else>
                <div class="bg-secondary-subtle d-flex
                            align-items-center justify-content-center"
                     style="height: 400px;">
                    <p class="text-muted">
                        No Image Available
                    </p>
                </div>
            </#if>

        </div>

        <div class="col-md-5">

            <h2>Character Information</h2>

            <p>
                <strong>Name:</strong>
                ${character.name}
            </p>

            <p>
                <strong>Universe:</strong>
                ${character.universe!'Not listed'}
            </p>

            <p>
                <strong>Role:</strong>
                ${character.role!'Not listed'}
            </p>

            <p>
                <strong>Species:</strong>
                ${character.species!'Not listed'}
            </p>

            <p>
                <strong>Home:</strong>
                ${character.location!'Not listed'}
            </p>

            <h2>Description</h2>

            <p>
                ${character.description!'Not listed'}
            </p>

            <h2>Abilities</h2>

            <p>
                ${character.powers!'Not listed'}
            </p>

            <div class="d-flex gap-2 mt-4">

                <a href="/characters/${character.id?c}/edit"
                   class="btn btn-primary">
                    Edit Character
                </a>

                <form method="post"
                      action="/characters/${character.id?c}/delete"
                      onsubmit="return confirm('Are you sure you want to delete this character?');">

                    <button type="submit"
                            class="btn btn-danger">
                        Delete Character
                    </button>

                </form>

            </div>

            <a href="/" class="btn btn-outline-secondary mt-3">
                Back to Gallery
            </a>

        </div>

    </div>

</@l.page>
