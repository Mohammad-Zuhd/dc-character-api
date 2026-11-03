
<#import "layout.ftlh" as l>

<@l.page title=formTitle active=(character.id??)?then('', 'new')>

    <h1 class="mb-4">${formTitle}</h1>

    <p>
        Fill out the form below to add or update
        a character in the DC gallery.
    </p>

    <form method="post" action="${formAction}">

        <!-- Character Name -->
        <div class="mb-3">
            <label for="name" class="form-label">
                Character Name:
            </label>

            <input type="text"
                   id="name"
                   name="name"
                   class="form-control"
                   value="${(character.name)!''}"
                   required>
        </div>

        <!-- Universe -->
        <div class="mb-3">
            <label for="universe" class="form-label">
                Universe:
            </label>

            <input type="text"
                   id="universe"
                   name="universe"
                   class="form-control"
                   value="${(character.universe)!''}">
        </div>

        <!-- Role -->
        <div class="mb-3">
            <label for="role" class="form-label">
                Role:
            </label>

            <input type="text"
                   id="role"
                   name="role"
                   class="form-control"
                   value="${(character.role)!''}"
                   placeholder="Hero or Villain">
        </div>

        <!-- Species -->
        <div class="mb-3">
            <label for="species" class="form-label">
                Species:
            </label>

            <input type="text"
                   id="species"
                   name="species"
                   class="form-control"
                   value="${(character.species)!''}">
        </div>

        <!-- Location -->
        <div class="mb-3">
            <label for="location" class="form-label">
                Location:
            </label>

            <input type="text"
                   id="location"
                   name="location"
                   class="form-control"
                   value="${(character.location)!''}">
        </div>

        <!-- Powers -->
        <div class="mb-3">
            <label for="powers" class="form-label">
                Powers and Abilities:
            </label>

            <input type="text"
                   id="powers"
                   name="powers"
                   class="form-control"
                   value="${(character.powers)!''}">
        </div>

        <!-- Image URL -->
        <div class="mb-3">
            <label for="imageUrl" class="form-label">
                Image URL:
            </label>

            <input type="text"
                   id="imageUrl"
                   name="imageUrl"
                   class="form-control"
                   value="${(character.imageUrl)!''}"
                   placeholder="/images/batman.jpg">
        </div>

        <!-- Description -->
        <div class="mb-3">
            <label for="description" class="form-label">
                Short Description:
            </label>

            <textarea id="description"
                      name="description"
                      class="form-control"
                      rows="4"
                      required>${(character.description)!''}</textarea>
        </div>

        <!-- Buttons -->
        <div class="d-flex gap-2">

            <button type="submit" class="btn btn-primary">
                <#if character.id??>
                    Save Changes
                <#else>
                    Add Character
                </#if>
            </button>

            <#if character.id??>
                <a href="/characters/${character.id?c}"
                   class="btn btn-secondary">
                    Cancel
                </a>
            <#else>
                <a href="/" class="btn btn-secondary">
                    Cancel
                </a>
            </#if>

        </div>

    </form>

</@l.page>
