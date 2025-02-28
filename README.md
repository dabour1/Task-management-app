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
- Create a new database named task_management_app.
- change the database to yours in the application.properties file

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/task_management_app
SPRING_DATASOURCE_USERNAME=your_db_user
SPRING_DATASOURCE_PASSWORD=your_db_password

```
 

### Run the Application
```bash
mvn spring-boot:run
```
This will apply database migrations automatically 

The API will be available at `http://localhost:8080`.

---

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|---------|-------------|
| `POST` | `/api/auth/signup` | Register a new user |
| `POST` | `/api/auth/login` | User login |
| `POST` | `/api/auth/google` | Google Sign-In authentication |

### Workspaces
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/workspaces` | Get all workspaces |
| `GET` | `/api/workspaces/{id}` | Get workspace by ID |
| `GET` | `/api/workspaces/{taskid}/tasks` | Get tasks that related to the workspace by workspace ID |
| `POST` | `/api/workspaces` | Create a new workspace |
| `PUT` | `/api/workspaces/{id}` | Update a workspace |
| `DELETE` | `/api/workspaces/{id}` | Delete a workspace |

### Tasks
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/api/tasks` | Get all tasks |
| `GET` | `/api/tasks/{id}` | Get task by ID |
| `POST` | `/api/tasks` | Create a new task |
| `PUT` | `/api/tasks/{id}` | Update a task |
| `PUT` | `/api/tasks/{id}/status` | cahnge a task status |
| `DELETE` | `/api/tasks/{id}` | Delete a task |

---

## Google Sign-In Integration

### Step 1: Create OAuth Credentials
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Navigate to **APIs & Services > Credentials**
4. Click **Create Credentials > OAuth 2.0 Client ID**
5. Set **Authorized Redirect URIs**: `https://oauth.pstmn.io/v1/callback` for postman testing
6. Copy **Client ID** and update `application.properties` file

### Step 2: Frontend Authentication
The frontend (postman) should request a Google ID token:
 1. add new request
 2. navigate to the Authorization tab in your Postman account and select OAuth 2.0 as your authorization type.
 3. Scroll down to the Configure New Token section and fill in the following details.
 4. Grant Type: Select “Authorization Code”.
 5. Auth URL: https://accounts.google.com/o/oauth2/v2/auth
 6. Access Token URL: https://oauth2.googleapis.com/token.
 7. Client ID: This is the client ID that was generated in your Google Cloud Console. Store this ID in a variable to keep sensitive data secure.
 8. Client Secret: This is the client secret that was generated in your Google Cloud Console. Store the secret in a variable to keep sensitive data secure.
 9. Scope: https://www.googleapis.com/auth/userinfo.email
 10. Once you’ve entered this data, click the Get New Access Token button.
 11. When you click the button, Postman will automatically open a new tab in your web browser.
 12. Allow prompt in your web browser.
 13. Once you accept this prompt, you will see a success modal and be navigated back to Postman
 14. Once you’re back in Postman, you will see a modal. This modal will include your newly generated tokens
 15. copy the ID token and send it in rthe equest body.



 

### Step 3: Backend Google Login API
Send the token to the backend:
```http
POST /api/auth/google
Content-Type: application/json
{
  "idToken": "your_google_id_token"
}
```

Backend verifies the token and issues a JWT for further API access.

---
 

## Contribution
Please feel free to suggest edits or improvements, and to open issues and contribute to this project!

 

