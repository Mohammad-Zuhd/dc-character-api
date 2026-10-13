# DC Character API

## API Overview

The DC Character API is a Spring Boot REST API for managing DC Comics characters, including superheroes and villains. It supports creating, retrieving, updating, deleting, filtering, and searching character records.

**Base URL:**

- **Production:** https://dc-character-api.onrender.com/api/characters

The API uses JSON request and response bodies. The `name` and `description` fields are required.

## Entity Attributes

Characters are stored in the `characters` database table.

| Attribute     | Type     | Rules                                  |
|---------------|----------|----------------------------------------|
| `id`          | `Long`   | Automatically generated unique primary key |
| `name`        | `String` | Required; cannot be null or blank      |
| `description` | `String` | Required; cannot be null or blank      |
| `role`        | `String` | Optional; character's role             |
| `species`     | `String` | Optional; character's species          |
| `location`    | `String` | Optional; character's location         |
| `powers`      | `String` | Optional; character's powers or abilities |
| `universe`    | `String` | Optional; character's universe         |

## API Endpoints

| Method   | Path                          | Purpose                                         | Success Status |
|----------|-------------------------------|-------------------------------------------------|----------------|
| `GET`    | `/api/characters`             | Retrieve all characters                         | `200 OK`       |
| `GET`    | `/api/characters/{id}`        | Retrieve a character by ID                      | `200 OK`       |
| `POST`   | `/api/characters`             | Create a character                              | `201 created`       |
| `PUT`    | `/api/characters/{id}`        | Update a character                              | `200 OK`       |
| `DELETE` | `/api/characters/{id}`        | Delete a character                              | `204 No Content` |
| `GET`    | `/api/characters?role=Hero`   | Filter characters by role                       | `200 OK`       |
| `GET`    | `/api/characters?name=bat`    | Search names using partial, case-insensitive matching | `200 OK` |

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

## Deployment

- **Render API:** [View all DC characters](https://dc-character-api.onrender.com/api/characters)
- **GitHub Repository:** [dc-character-api](https://github.com/Mohammad-Zuhd/dc-character-api)

> **Cold-start note:** The first request may take longer after inactivity while Render starts the service.