# Task Management app

## Overview
This is a **Task Management API** built using **Spring Boot** and **PostgreSQL**. The application allows users to manage workspaces and tasks while supporting authentication via **JWT and Google Sign-In**.

## Features
- **User Authentication** (Signup, Login, Google Sign-In)
- **Workspace Management** (CRUD operations)
- **Task Management** (CRUD operations, status updates)
- **Partitioned PostgreSQL Database Schema**
- **Role-Based Access Control (RBAC)**
- **Flyway Migrations**
- **Spring Security with JWT**

## Tech Stack
- **Backend**: Spring Boot, Spring Security, JWT
- **Database**: PostgreSQL with partitioning
- **Authentication**: JWT & Google OAuth 2.0
- **Migrations**: Flyway
- **Build Tool**: Maven

---

## Getting Started

### Prerequisites
Ensure you have the following installed:
- **JDK 17+**
- **Maven**
- **PostgreSQL**
- **Google Cloud Account** (for OAuth setup)

### Clone the Repository
```bash
git clone https://github.com/dabour1/Task-management-app.git
cd task-management-app
```

### Setup Environment  
Create a new database named :
```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/taskdb
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION_MS=86400000
GOOGLE_CLIENT_ID=your_google_client_id
```

### Database Migration (Flyway)
Run the following command to apply database migrations:
```bash
mvn flyway:migrate
```

### Run the Application
```bash
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`.

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/auth/signup` | Register a new user |
| `POST` | `/auth/login` | User login |
| `POST` | `/auth/google` | Google Sign-In authentication |

### Workspaces
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/workspaces` | Get all workspaces |
| `GET` | `/workspaces/{id}` | Get workspace by ID |
| `POST` | `/workspaces` | Create a new workspace |
| `PUT` | `/workspaces/{id}` | Update a workspace |
| `DELETE` | `/workspaces/{id}` | Delete a workspace |

### Tasks
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/tasks` | Get all tasks |
| `GET` | `/tasks/{id}` | Get task by ID |
| `POST` | `/tasks` | Create a new task |
| `PUT` | `/tasks/{id}` | Update a task |
| `DELETE` | `/tasks/{id}` | Delete a task |

---

## Google Sign-In Integration

### Step 1: Create OAuth Credentials
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Navigate to **APIs & Services > Credentials**
4. Click **Create Credentials > OAuth 2.0 Client ID**
5. Set **Authorized Redirect URIs**: `http://localhost:8080/auth/google`
6. Copy **Client ID** and update `.env` file

### Step 2: Frontend Authentication
The frontend (React, Angular, etc.) should request a Google ID token:
```javascript
const token = await googleUser.getAuthResponse().id_token;
```

### Step 3: Backend Google Login API
Send the token to the backend:
```http
POST /auth/google
Content-Type: application/json
{
  "idToken": "your_google_id_token"
}
```

Backend verifies the token and issues a JWT for further API access.

---

## Testing with Postman
1. Import the API collection into Postman
2. Set **Authorization** to **Bearer Token** for protected endpoints
3. Test API endpoints

---

## Contribution
Feel free to open issues and contribute to this project!

---

## License
This project is licensed under the MIT License.

