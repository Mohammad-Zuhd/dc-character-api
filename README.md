# DC Character API

## API Overview

The DC Character API is a Spring Boot REST API for managing DC Comics characters, including superheroes and villains. It supports creating, retrieving, updating, deleting, filtering, and searching character records.

**Base URL:**

- **Production:** https://dc-character-api.onrender.com/api/characters

The API uses JSON request and response bodies. The `name` and `description` fields are required.

A REST API for managing DC Comics characters, including heroes and villains, built with Spring Boot using a layered architecture. Data is stored in PostgreSQL (Neon).

This branch also includes a server rendered MVC web interface for browsing and managing characters in a browser. The web interface and JSON API share the same service, repository, and database.

## Features

- Create, read, update, and delete DC characters
- Partial, case insensitive search by character name
- Filter characters by role (for example, Hero or Villain)
- Combine name and role filters in the web gallery
- Browse character cards, view details, and add, edit, or delete characters through an MVC interface
- Display character images from an image URL or local file path; show a placeholder when no image is provided
- Validate required name and description fields, preserve form values after validation errors, and show one-time success messages
- Show a friendly not found page for unknown character IDs
- Configure database credentials through environment variables, without committing secrets

## Tech Stack

| Area          | Technology                                        |
|---------------|---------------------------------------------------|
| Language      | Java 25                                           |
| Framework     | Spring Boot 4.1.1 (Web MVC, Data JPA, Validation) |
| Web interface | FreeMarker, HTML, Bootstrap 5.3.3                 |
| Database      | PostgreSQL on Neon                                |
| Build         | Maven                                             |
| Deployment    | Render (Docker)                                   |

## Architecture

The application has two controller layers for different clients. Both use the same service, repository, entity, and Neon database.

```text
Browser --------> CharacterUiController ---> FreeMarker templates (HTML)
                         |
JSON client ----> REST API controller
                         |
                 DcCharacterService
                         |
                 DcCharacterRepository (JPA)
                         |
                 DcCharacter entity / PostgreSQL (Neon)
```

The MVC controller calls the service directly. It does not send HTTP requests to its own REST API.

## Project Structure

```text
src/main/java/edu/uncg/dccharacterapi/
  DcCharacterApiApplication.java
  controller/
    CharacterUiController.java
    (existing REST API controller)
  entity/
    DcCharacter.java
  repository/
    DcCharacterRepository.java
  service/
    DcCharacterService.java

src/main/resources/
  application.properties
  templates/                  FreeMarker views for the MVC interface
    layout.ftlh               Shared navigation and page layout
    index.ftlh                Character gallery and search
    details.ftlh              Individual character details
    character-form.ftlh       Shared Add/Edit form
    about.ftlh                About page
    error.ftlh                Friendly not-found page
  static/images/              Local DC character images

pom.xml
Dockerfile
README.md
```

## MVC Web Interface

Start the application and open [http://localhost:8080/](http://localhost:8080/) to use the browser interface.

| Method | Path                      | Description                                                         |
|--------|---------------------------|---------------------------------------------------------------------|
| `GET`  | `/`                       | Character gallery; optional name and role search filters            |
| `GET`  | `/characters/new`         | Show the Add Character form                                         |
| `POST` | `/characters`             | Create a new character, then redirect to the gallery                |
| `GET`  | `/characters/{id}`        | Show character details                                              |
| `GET`  | `/characters/{id}/edit`   | Show the pre-filled Edit Character form                             |
| `POST` | `/characters/{id}`        | Update the existing character, then redirect to its details page    |
| `POST` | `/characters/{id}/delete` | Delete a character after confirmation, then redirect to the gallery |
| `GET`  | `/about`                  | Explain the gallery and how the application works                   |

The gallery supports optional name and role filters. If both are provided, both must match. An empty search result shows a friendly message. The Add and Edit forms share one FreeMarker template, and successful changes use redirects and flash messages.

Character images can use paths such as `/images/batman.jpg`. Characters without an imageUrl show a placeholder.

## Data Model

**DcCharacter (table `characters`)**

| Field         | Type     | Rules                                   |
|---------------|----------|-----------------------------------------|
| `id`          | `Long`   | Generated automatically                 |
| `name`        | `String` | Required, not blank                     |
| `description` | `String` | Required, not blank                     |
| `role`        | `String` | Optional; e.g. Hero or Villain          |
| `species`     | `String` | Optional                                |
| `location`    | `String` | Optional                                |
| `powers`      | `String` | Optional                                |
| `universe`    | `String` | Optional                                |
| `imageUrl`    | `String` | Optional; image URL or local image path |

## Entity Attributes

Characters are stored in the `characters` database table.

| Attribute     | Type     | Rules                                      |
|---------------|----------|--------------------------------------------|
| `id`          | `Long`   | Automatically generated unique primary key |
| `name`        | `String` | Required; cannot be null or blank          |
| `description` | `String` | Required; cannot be null or blank          |
| `role`        | `String` | Optional; character's role                 |
| `species`     | `String` | Optional; character's species              |
| `location`    | `String` | Optional; character's location             |
| `powers`      | `String` | Optional; character's powers or abilities  |
| `universe`    | `String` | Optional; character's universe             |

## API Reference

**Base path:**

- `/api/characters`

| Method   | Path                        | Description               | Success |
|----------|-----------------------------|---------------------------|---------|
| `GET`    | `/api/characters`           | List all characters       | `200`   |
| `GET`    | `/api/characters/{id}`      | Get one character         | `200`   |
| `POST`   | `/api/characters`           | Create a character        | `201`   |
| `PUT`    | `/api/characters/{id}`      | Update a character        | `200`   |
| `DELETE` | `/api/characters/{id}`      | Delete a character        | `204`   |
| `GET`    | `/api/characters?name=bat`  | Search character names    | `200`   |
| `GET`    | `/api/characters?role=Hero` | Filter characters by role | `200`   |

## API Endpoints

| Method   | Path                        | Purpose                                               | Success Status   |
|----------|-----------------------------|-------------------------------------------------------|------------------|
| `GET`    | `/api/characters`           | Retrieve all characters                               | `200 OK`         |
| `GET`    | `/api/characters/{id}`      | Retrieve a character by ID                            | `200 OK`         |
| `POST`   | `/api/characters`           | Create a character                                    | `201 created`    |
| `PUT`    | `/api/characters/{id}`      | Update a character                                    | `200 OK`         |
| `DELETE` | `/api/characters/{id}`      | Delete a character                                    | `204 No Content` |
| `GET`    | `/api/characters?role=Hero` | Filter characters by role                             | `200 OK`         |
| `GET`    | `/api/characters?name=bat`  | Search names using partial, case-insensitive matching | `200 OK`         |

## Query parameters for GET /api/characters

| Parameter | Example      | Behavior                              |
|-----------|--------------|---------------------------------------|
| `name`    | `?name=bat`  | Partial, case-insensitive name search |
| `role`    | `?role=Hero` | Case-insensitive role filter          |

The MVC gallery additionally supports both filters together: `/?name=bat&role=Hero`.

## Example request

```http
POST /api/characters
Content-Type: application/json
```

```json
{
  "name": "Batman",
  "description": "A Gotham City hero who uses detective skills and technology.",
  "role": "Hero",
  "species": "Human",
  "location": "Gotham City",
  "powers": "Detective skills, martial arts, and gadgets",
  "universe": "DC",
  "imageUrl": "/images/batman.jpg"
}
```

The `id` is generated automatically. Local image paths must match files in `src/main/resources/static/images/`.

## Example Create Character Request

```http
POST /api/characters
Content-Type: application/json
```

```json
{
  "name": "Green Lantern",
  "description": "A hero who protects the universe using a power ring.",
  "role": "Hero",
  "species": "Human",
  "location": "Coast City",
  "powers": "Green energy constructs",
  "universe": "DC"
}
```

The `id` is generated automatically by the database.

## Example Update Character Request

```http
PUT /api/characters/1
Content-Type: application/json
```

```json
{
  "name": "Batman",
  "description": "A detective and protector of Gotham City.",
  "role": "Hero",
  "species": "Human",
  "location": "Gotham City",
  "powers": "Detective skills, martial arts, and gadgets",
  "universe": "DC"
}
```

Replace `1` with the ID of the character you want to update.

## Status codes

| Code  | Meaning                                                        |
|-------|----------------------------------------------------------------|
| `200` | Successful read or update                                      |
| `201` | Character created by the REST API                              |
| `204` | Character deleted by the REST API                              |
| `400` | Invalid request or validation error (where handled by the API) |
| `404` | Requested character does not exist                             |

The MVC interface renders HTML pages and redirects after successful form submissions. Unknown character IDs display a friendly page with HTTP 404.

## Configuration

The application reads its Neon database settings from environment variables. Do not save actual credentials in `README.md`, `application.properties`, or Git commits.

| Variable                     | Example or purpose                                         |
|------------------------------|------------------------------------------------------------|
| `SPRING_DATASOURCE_URL`      | `jdbc:postgresql://<neon-host>/<database>?sslmode=require` |
| `SPRING_DATASOURCE_USERNAME` | Neon database username                                     |
| `SPRING_DATASOURCE_PASSWORD` | Neon database password                                     |
| `PORT`                       | Set by Render; defaults to 8080 locally                    |

The application uses `spring.jpa.hibernate.ddl-auto=update` to update its database schema, including the imageUrl attribute.

## Running Locally

1. Create or open your Neon database and copy its connection details.
2. Set the environment variables in the same terminal where you will start the application.

   ```powershell
   Mac/Linux: export SPRING_DATASOURCE_URL="jdbc:postgresql://<neon-host>/<db>?sslmode=require"
   Windows: set SPRING_DATASOURCE_URL=jdbc:postgresql://<neon-host>/<db>?sslmode=require
   PowerShell: $env:SPRING_DATASOURCE_URL="jdbc:postgresql://<neon-host>/<db>?sslmode=require"
   ```
3. Start the app:

   ```bash
   ./mvnw spring-boot:run         # Mac / Linux

   .\mvnw.cmd spring-boot:run    # Windows
   ```

4. Visit [http://localhost:8080/](http://localhost:8080/) for the MVC web interface or [http://localhost:8080/api/characters](http://localhost:8080/api/characters) for the JSON API.

A new database may initially show an empty gallery. Add characters through the web form or the API. The assignment requires at least five characters with images.

## Deployment (Render)

The repository contains a Dockerfile for deployment to Render.

1. Push the `mzuhd-assignment4` branch to GitHub.
2. In Render, open your Web Service and configure it to deploy that branch using Docker.
3. Set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, and `SPRING_DATASOURCE_PASSWORD` in Render's environment settings.
4. Deploy the updated branch.
5. Test the live gallery (`/`), About page (`/about`), Add form (`/characters/new`), and REST API (`/api/characters`).

- **Render API:** [View all DC characters](https://dc-character-api.onrender.com/api/characters)
- **GitHub Repository:** [dc-character-api](https://github.com/Mohammad-Zuhd/dc-character-api)
- **Existing production API URL:** [https://dc-character-api.onrender.com/api/characters](https://dc-character-api.onrender.com/api/characters)

The MVC pages at [https://dc-character-api.onrender.com/](https://dc-character-api.onrender.com/) will be available only after the Assignment 4 branch has been successfully deployed and tested.

> **Cold-start note:** The first request may take longer after inactivity while Render starts the service.

> **Free-tier notes**
>
> - Render free services may take longer to respond after inactivity.
> - Neon may have a short first query delay after idling.
> - Render's local filesystem is not persistent. Character data is stored in Neon; bundled local images are included with the application build.