# Quick Start Guide - SCI Projects Archive

Get the application running in 5 minutes!

## Prerequisites
- Java 17 installed
- Maven installed

Check your versions:
```bash
java -version  # Should be 17 or higher
mvn -version   # Should be 3.6 or higher
```

## Quick Setup

### 1. Clone and Navigate
```bash
git clone <repository-url>
cd sci-projects-archive
```

### 2. Build
```bash
mvn clean install
```

### 3. Run
```bash
mvn spring-boot:run
```

That's it! The application is now running on `http://localhost:8080`

## Test the Application

### Step 1: Register a Student
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "email": "student1@sci.edu",
    "password": "password123",
    "fullName": "John Student",
    "role": "STUDENT",
    "studentId": "SCI2024001"
  }'
```

### Step 2: Register a Lecturer
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "lecturer1",
    "email": "lecturer1@sci.edu",
    "password": "password123",
    "fullName": "Dr. Smith",
    "role": "LECTURER",
    "staffId": "STAFF001"
  }'
```

### Step 3: Login as Student
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "student1",
    "password": "password123"
  }'
```

**Save the token from the response!**

### Step 4: Create a Project
```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "title": "My First Project",
    "description": "A test project",
    "course": "Software Engineering",
    "academicYear": "2023/2024",
    "semester": "Semester 1"
  }'
```

### Step 5: Upload a Document
```bash
curl -X POST http://localhost:8080/api/projects/1/documents \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -F "file=@/path/to/your/document.pdf" \
  -F "documentType=PRODUCT_REQUIREMENTS_DOCUMENT" \
  -F "description=Project requirements"
```

### Step 6: View Your Projects
```bash
curl -X GET http://localhost:8080/api/projects/my-projects \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Using Postman

### Import Collection

1. Open Postman
2. Import the following endpoints:

**Base URL:** `http://localhost:8080/api`

#### 1. Register Student
- Method: POST
- URL: `/auth/signup`
- Body (JSON):
```json
{
  "username": "student1",
  "email": "student1@sci.edu",
  "password": "password123",
  "fullName": "John Student",
  "role": "STUDENT",
  "studentId": "SCI2024001"
}
```

#### 2. Login
- Method: POST
- URL: `/auth/signin`
- Body (JSON):
```json
{
  "username": "student1",
  "password": "password123"
}
```

#### 3. Create Project (Add token to Headers)
- Method: POST
- URL: `/projects`
- Headers: `Authorization: Bearer YOUR_TOKEN`
- Body (JSON):
```json
{
  "title": "E-Commerce Platform",
  "description": "Full-stack e-commerce application",
  "course": "Web Development",
  "academicYear": "2023/2024",
  "semester": "Semester 2"
}
```

#### 4. Upload Document
- Method: POST
- URL: `/projects/1/documents`
- Headers: `Authorization: Bearer YOUR_TOKEN`
- Body (form-data):
  - `file`: (select file)
  - `documentType`: `PRODUCT_REQUIREMENTS_DOCUMENT`
  - `description`: `Project requirements document`

## Access H2 Database Console

1. Open browser: `http://localhost:8080/h2-console`
2. Use these settings:
   - JDBC URL: `jdbc:h2:file:./data/scidb`
   - Username: `sa`
   - Password: `password`
3. Click "Connect"

## Project Structure Overview

```
sci-projects-archive/
├── src/main/java/com/sci/archive/
│   ├── controller/          # API endpoints
│   ├── model/              # Database entities
│   ├── repository/         # Data access
│   ├── service/            # Business logic
│   ├── security/           # Authentication
│   └── config/             # Configuration
├── src/main/resources/
│   └── application.properties  # Configuration
├── uploads/                # Uploaded files
├── pom.xml                # Maven dependencies
└── README.md              # Full documentation
```

## Common Operations

### Student Operations
```bash
# Login
POST /api/auth/signin

# Create project
POST /api/projects

# Upload document
POST /api/projects/{id}/documents

# View my projects
GET /api/projects/my-projects

# View project details
GET /api/projects/{id}

# Download document
GET /api/projects/documents/{documentId}/download
```

### Lecturer Operations
```bash
# Login
POST /api/auth/signin

# View all projects
GET /api/projects

# View supervised projects
GET /api/projects/supervised

# Review project
POST /api/projects/{id}/review

# View all lecturers
GET /api/users/lecturers
```

## Document Types

When uploading documents, use these types:
- `PRODUCT_REQUIREMENTS_DOCUMENT`
- `SOFTWARE_DESIGN_DOCUMENT`
- `USER_MANUAL`
- `TECHNICAL_DOCUMENTATION`
- `TEST_PLAN`
- `SOURCE_CODE`
- `FINAL_REPORT`
- `PRESENTATION`
- `OTHER`

## Project Statuses

- `SUBMITTED` - Initial submission
- `UNDER_REVIEW` - Being reviewed
- `APPROVED` - Accepted
- `REJECTED` - Not accepted
- `NEEDS_REVISION` - Requires changes

## Troubleshooting

### Port 8080 already in use
Change port in `application.properties`:
```properties
server.port=8081
```

### Can't connect to database
The H2 database files are created automatically in `./data/` folder.

### File upload fails
Check the `uploads/` directory exists and has write permissions.

## Next Steps

1. Read the full [README.md](README.md) for detailed information
2. Check [API_DOCUMENTATION.md](API_DOCUMENTATION.md) for all endpoints
3. Review [DEPLOYMENT_GUIDE.md](DEPLOYMENT_GUIDE.md) for production setup

## Support

For issues:
1. Check application logs
2. View H2 console for database state
3. Verify all prerequisites are installed
4. Check file permissions in uploads directory

## Default Credentials

The application starts with no default users. You must register users through the API.

---

**Ready to build? Start coding!** 🚀
