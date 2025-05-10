# Publication System API

A RESTful backend service designed not as a synthetic example, but as a realistic implementation of a digital publishing
platform. The system handles user registration, article submission, and demonstrates both Role-Based and Attribute-Based
Access Control in practice.

This project was originally designed and implemented by our team as part of an academic initiative at **Gdańsk 
University of Technology**, within the course ***Introduction to Cybersecurity***. It aims to explore and showcase 
real-world applications of **RBAC** and **ABAC** access control models.

## 🛠️ Technologies

- **Java 24**
- **Spring Boot 3**
- **Spring Security 6**
- **Spring Data JPA 3**
- **H2 (Dev)**
- **Maven**

## ⚠️ Authentication

Authentication is handled via **HTTP Basic Auth**. Credentials must be sent with each request to access protected 
endpoints. The credentials have to be encoded in **Base64** format as follows:

1. Combine the username and password in the format: `username:password`
2. Encode the combined string to **Base64**
3. Send the encoded credentials in the header of the HTTP request:
`Authorization: Basic d2hhdEFyZVlvdTpMb29raW5nRm9y`

## 📋 API Endpoints Overview (TODO)

### Auth

| Method | Endpoint             | Description       | Auth Required |
|--------|----------------------|-------------------|---------------|
| POST   | `/api/auth/register` | Register new user | ❌             |
| GET    | `/api/auth/check`    | Basic auth check  | ✅             |

### Accounts

| Method | Endpoint             | Description                  | Auth Required |
|--------|----------------------|------------------------------|---------------|
| GET    | `/api/accounts`      | Get list of users            | ❌             |
| GET    | `/api/accounts/{id}` | Get public details of a user | ✅             |

### Articles

| Method | Endpoint              | Description                     | Roles      |
|--------|-----------------------|---------------------------------|------------|
| GET    | `/api/articles`       | List all articles               | All roles  |
| GET    | `/api/articles/admin` | Admin-only article data         | ADMIN      |
| POST   | `/api/articles`       | Submit new article              | EDITOR     |

### Comments

| Method | Endpoint              | Description             | Roles     |
|--------|-----------------------|-------------------------|-----------|
| GET    | `/api/comments`       | List all articles       | All roles |
| GET    | `/api/comments/admin` | Admin-only article data | ADMIN     |
| POST   | `/api/comments`       | Submit new article      | EDITOR    |

<!--## 📚 Query Parameters

The API supports various query parameters for filtering, sorting, and pagination. These parameters can be added to endpoints that return collections of resources.

### Pagination Parameters

| Parameter | Description              | Default | Example    |
|-----------|--------------------------|---------|------------|
| `page`    | Page number (zero-based) | 0       | `?page=2`  |
| `size`    | Number of items per page | 10      | `?size=20` |

### Filtering Parameters

Different endpoints support specific filtering parameters:

#### Articles Endpoint (`/articles`)

| Parameter    | Description                | Example             |
|--------------|----------------------------|---------------------|
| `status`     | Filter by article status   | `?status=PUBLISHED` |
| `categoryId` | Filter by category ID      | `?categoryId=5`     |
| `authorId`   | Filter by author ID        | `?authorId=3`       |
| `title`      | Filter by title containing | `?title=REST`       |
-->

## ⚙️ Running Locally

```bash
git clone https://github.com/varev-dev/publication-system-api.git
cd publication-system-api
./mvnw spring-boot:run
```