# API Documentation - SCI Projects Archive

## Base URL
```
http://localhost:8080/api
```

## Authentication

All endpoints except `/api/auth/signup` and `/api/auth/signin` require authentication.

Include the JWT token in the Authorization header:
```
Authorization: Bearer <your_jwt_token>
```

---

## 1. Authentication Endpoints

### 1.1 User Registration

**Endpoint:** `POST /auth/signup`

**Description:** Register a new user account (Student or Lecturer)

**Request Body:**
```json
{
  "username": "string (required, 3-50 chars)",
  "email": "string (required, valid email)",
  "password": "string (required, min 6 chars)",
  "fullName": "string (required)",
  "role": "STUDENT | LECTURER (required)",
  "studentId": "string (required if role=STUDENT)",
  "staffId": "string (required if role=LECTURER)"
}
```

**Example - Student Registration:**
```json
{
  "username": "alice_student",
  "email": "alice@student.sci.edu",
  "password": "securepass123",
  "fullName": "Alice Johnson",
  "role": "STUDENT",
  "studentId": "SCI/2023/001"
}
```

**Example - Lecturer Registration:**
```json
{
  "username": "dr_smith",
  "email": "smith@sci.edu",
  "password": "securepass456",
  "fullName": "Dr. Robert Smith",
  "role": "LECTURER",
  "staffId": "STAFF/001"
}
```

**Success Response (200 OK):**
```json
{
  "message": "User registered successfully!"
}
```

**Error Responses:**
- 400 Bad Request: Username or email already exists
- 400 Bad Request: Invalid role or missing required fields

---

### 1.2 User Login

**Endpoint:** `POST /auth/signin`

**Description:** Authenticate user and receive JWT token

**Request Body:**
```json
{
  "username": "string (required)",
  "password": "string (required)"
}
```

**Example:**
```json
{
  "username": "alice_student",
  "password": "securepass123"
}
```

**Success Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhbGljZV9zdHVkZW50IiwiaWF0IjoxNjQwOTk1MjAwLCJleHAiOjE2NDEwODE2MDB9.abc123...",
  "type": "Bearer",
  "id": 1,
  "username": "alice_student",
  "email": "alice@student.sci.edu",
  "fullName": "Alice Johnson",
  "role": "STUDENT"
}
```

**Error Response:**
- 401 Unauthorized: Invalid credentials

---

## 2. Project Endpoints

### 2.1 Create Project

**Endpoint:** `POST /projects`

**Role Required:** STUDENT

**Description:** Create a new project submission

**Request Body:**
```json
{
  "title": "string (required, max 200 chars)",
  "description": "string (optional, max 2000 chars)",
  "course": "string (optional, max 100 chars)",
  "academicYear": "string (optional, max 50 chars)",
  "semester": "string (optional, max 50 chars)",
  "supervisorId": "number (optional)"
}
```

**Example:**
```json
{
  "title": "E-Commerce Web Application",
  "description": "A full-stack e-commerce platform with payment integration",
  "course": "Web Development",
  "academicYear": "2023/2024",
  "semester": "Semester 2",
  "supervisorId": 5
}
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "title": "E-Commerce Web Application",
  "description": "A full-stack e-commerce platform with payment integration",
  "course": "Web Development",
  "academicYear": "2023/2024",
  "semester": "Semester 2",
  "status": "SUBMITTED",
  "lecturerComments": null,
  "marks": null,
  "submittedAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "reviewedAt": null
}
```

---

### 2.2 Update Project

**Endpoint:** `PUT /projects/{id}`

**Role Required:** STUDENT (own projects only)

**Description:** Update an existing project

**Path Parameters:**
- `id` (number): Project ID

**Request Body:** Same as Create Project

**Success Response (200 OK):** Updated project object

---

### 2.3 Get My Projects

**Endpoint:** `GET /projects/my-projects`

**Role Required:** STUDENT

**Description:** Get all projects created by the authenticated student

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "E-Commerce Web Application",
    "description": "...",
    "course": "Web Development",
    "academicYear": "2023/2024",
    "semester": "Semester 2",
    "status": "APPROVED",
    "lecturerComments": "Excellent work!",
    "marks": 90,
    "submittedAt": "2024-01-15T10:30:00",
    "reviewedAt": "2024-01-20T14:00:00"
  }
]
```

---

### 2.4 Get All Projects

**Endpoint:** `GET /projects`

**Role Required:** LECTURER or ADMIN

**Description:** Get all projects in the system

**Success Response (200 OK):** Array of project objects

---

### 2.5 Get Supervised Projects

**Endpoint:** `GET /projects/supervised`

**Role Required:** LECTURER or ADMIN

**Description:** Get all projects supervised by the authenticated lecturer

**Success Response (200 OK):** Array of project objects

---

### 2.6 Get Project by ID

**Endpoint:** `GET /projects/{id}`

**Role Required:** Any authenticated user

**Description:** Get detailed information about a specific project

**Path Parameters:**
- `id` (number): Project ID

**Success Response (200 OK):** Project object with full details

---

### 2.7 Upload Document

**Endpoint:** `POST /projects/{id}/documents`

**Role Required:** STUDENT (own projects only)

**Description:** Upload a document for a project

**Content-Type:** `multipart/form-data`

**Path Parameters:**
- `id` (number): Project ID

**Form Data:**
- `file` (file, required): The document file
- `documentType` (string, required): Type of document
- `description` (string, optional): Document description

**Document Types:**
- `PRODUCT_REQUIREMENTS_DOCUMENT`
- `SOFTWARE_DESIGN_DOCUMENT`
- `USER_MANUAL`
- `TECHNICAL_DOCUMENTATION`
- `TEST_PLAN`
- `SOURCE_CODE`
- `FINAL_REPORT`
- `PRESENTATION`
- `OTHER`

**Example cURL:**
```bash
curl -X POST http://localhost:8080/api/projects/1/documents \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "file=@/path/to/document.pdf" \
  -F "documentType=PRODUCT_REQUIREMENTS_DOCUMENT" \
  -F "description=Initial requirements document"
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "fileName": "requirements.pdf",
  "filePath": "project_1/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
  "documentType": "PRODUCT_REQUIREMENTS_DOCUMENT",
  "fileType": "pdf",
  "fileSize": 2048576,
  "description": "Initial requirements document",
  "uploadedAt": "2024-01-15T11:00:00"
}
```

---

### 2.8 Get Project Documents

**Endpoint:** `GET /projects/{id}/documents`

**Role Required:** Any authenticated user

**Description:** Get all documents for a specific project

**Path Parameters:**
- `id` (number): Project ID

**Success Response (200 OK):**
```json
[
  {
    "id": 1,
    "fileName": "requirements.pdf",
    "filePath": "project_1/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
    "documentType": "PRODUCT_REQUIREMENTS_DOCUMENT",
    "fileType": "pdf",
    "fileSize": 2048576,
    "description": "Initial requirements document",
    "uploadedAt": "2024-01-15T11:00:00"
  },
  {
    "id": 2,
    "fileName": "design.docx",
    "filePath": "project_1/b2c3d4e5-f6g7-8901-bcde-fg2345678901.docx",
    "documentType": "SOFTWARE_DESIGN_DOCUMENT",
    "fileType": "docx",
    "fileSize": 1536000,
    "description": "Software design document",
    "uploadedAt": "2024-01-16T09:30:00"
  }
]
```

---

### 2.9 Download Document

**Endpoint:** `GET /projects/documents/{documentId}/download`

**Role Required:** Any authenticated user

**Description:** Download a specific document

**Path Parameters:**
- `documentId` (number): Document ID

**Success Response (200 OK):**
- Content-Type: `application/octet-stream`
- Content-Disposition: `attachment; filename="original_filename.pdf"`
- Body: File binary data

**Example cURL:**
```bash
curl -X GET http://localhost:8080/api/projects/documents/1/download \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -o downloaded_file.pdf
```

---

### 2.10 Review Project

**Endpoint:** `POST /projects/{id}/review`

**Role Required:** LECTURER or ADMIN

**Description:** Review and approve/reject a project submission

**Path Parameters:**
- `id` (number): Project ID

**Request Body:**
```json
{
  "status": "APPROVED | REJECTED | NEEDS_REVISION | UNDER_REVIEW (required)",
  "comments": "string (optional)",
  "marks": "number (optional, 0-100)"
}
```

**Example:**
```json
{
  "status": "APPROVED",
  "comments": "Excellent implementation. Well documented and tested.",
  "marks": 92
}
```

**Success Response (200 OK):**
```json
{
  "id": 1,
  "title": "E-Commerce Web Application",
  "status": "APPROVED",
  "lecturerComments": "Excellent implementation. Well documented and tested.",
  "marks": 92,
  "reviewedAt": "2024-01-20T14:30:00",
  "updatedAt": "2024-01-20T14:30:00"
}
```

---

### 2.11 Delete Project

**Endpoint:** `DELETE /projects/{id}`

**Role Required:** STUDENT (own projects only)

**Description:** Delete a project and all associated documents

**Path Parameters:**
- `id` (number): Project ID

**Success Response (200 OK):**
```json
{
  "message": "Project deleted successfully"
}
```

---

### 2.12 Delete Document

**Endpoint:** `DELETE /projects/documents/{documentId}`

**Role Required:** STUDENT (own project documents only)

**Description:** Delete a specific document

**Path Parameters:**
- `documentId` (number): Document ID

**Success Response (200 OK):**
```json
{
  "message": "Document deleted successfully"
}
```

---

## 3. User Endpoints

### 3.1 Get Current User

**Endpoint:** `GET /users/me`

**Role Required:** Any authenticated user

**Description:** Get information about the currently authenticated user

**Success Response (200 OK):**
```json
{
  "id": 1,
  "username": "alice_student",
  "email": "alice@student.sci.edu",
  "fullName": "Alice Johnson",
  "studentId": "SCI/2023/001",
  "staffId": null,
  "role": "STUDENT",
  "active": true,
  "createdAt": "2024-01-10T08:00:00",
  "updatedAt": "2024-01-10T08:00:00"
}
```

---

### 3.2 Get All Lecturers

**Endpoint:** `GET /users/lecturers`

**Role Required:** Any authenticated user

**Description:** Get a list of all active lecturers

**Success Response (200 OK):**
```json
[
  {
    "id": 5,
    "username": "dr_smith",
    "email": "smith@sci.edu",
    "fullName": "Dr. Robert Smith",
    "staffId": "STAFF/001",
    "role": "LECTURER",
    "active": true
  }
]
```

---

### 3.3 Get All Users

**Endpoint:** `GET /users`

**Role Required:** ADMIN

**Description:** Get a list of all users in the system

**Success Response (200 OK):** Array of user objects

---

### 3.4 Get User by ID

**Endpoint:** `GET /users/{id}`

**Role Required:** Any authenticated user

**Description:** Get information about a specific user

**Path Parameters:**
- `id` (number): User ID

**Success Response (200 OK):** User object

---

## Status Codes

- **200 OK**: Request successful
- **201 Created**: Resource created successfully
- **400 Bad Request**: Invalid request data
- **401 Unauthorized**: Authentication required or failed
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

---

## Error Response Format

```json
{
  "message": "Error description here"
}
```

---

## Example Workflow

### Student Submitting a Project:

1. **Register:**
```bash
POST /api/auth/signup
{
  "username": "john_student",
  "email": "john@sci.edu",
  "password": "pass123",
  "fullName": "John Doe",
  "role": "STUDENT",
  "studentId": "SCI/2024/001"
}
```

2. **Login:**
```bash
POST /api/auth/signin
{
  "username": "john_student",
  "password": "pass123"
}
# Save the returned token
```

3. **Create Project:**
```bash
POST /api/projects
Authorization: Bearer <token>
{
  "title": "Mobile Banking App",
  "description": "Android banking application",
  "course": "Mobile Development",
  "academicYear": "2023/2024",
  "semester": "Semester 2"
}
# Note the returned project ID
```

4. **Upload Documents:**
```bash
POST /api/projects/1/documents
Authorization: Bearer <token>
Content-Type: multipart/form-data
file: requirements.pdf
documentType: PRODUCT_REQUIREMENTS_DOCUMENT
```

5. **Check Status:**
```bash
GET /api/projects/my-projects
Authorization: Bearer <token>
```

### Lecturer Reviewing Projects:

1. **Login:**
```bash
POST /api/auth/signin
{
  "username": "dr_smith",
  "password": "pass456"
}
```

2. **View Projects:**
```bash
GET /api/projects
Authorization: Bearer <token>
```

3. **View Project Details:**
```bash
GET /api/projects/1
Authorization: Bearer <token>
```

4. **Download Documents:**
```bash
GET /api/projects/documents/1/download
Authorization: Bearer <token>
```

5. **Review Project:**
```bash
POST /api/projects/1/review
Authorization: Bearer <token>
{
  "status": "APPROVED",
  "comments": "Great work!",
  "marks": 85
}
```

---

## Rate Limiting

Currently no rate limiting is implemented. Consider implementing in production.

## CORS

CORS is enabled for:
- `http://localhost:3000`
- `http://localhost:8080`

Modify `WebSecurityConfig.java` to add additional origins.

---

**API Version:** 1.0.0  
**Last Updated:** January 2024
