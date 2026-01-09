# SCI Projects Archive - School of Computing and Informatics

A comprehensive online student projects archive system built with Java Spring Boot that allows students to upload project documents and lecturers to review and approve them.

## Features

### For Students
- Create user account with student ID
- Upload project information (title, description, course, academic year, semester)
- Upload multiple project documents:
  - Product Requirements Document (PRD)
  - Software Design Document (SDD)
  - User Manual
  - Technical Documentation
  - Test Plans
  - Source Code
  - Final Reports
  - Presentations
- View project status and lecturer feedback
- Track project marks and comments
- Download uploaded documents

### For Lecturers
- Create user account with staff ID
- View all submitted projects
- Review and approve/reject projects
- Add comments and feedback
- Assign marks to projects
- Download student project documents
- Track projects by academic year and semester

### System Features
- Secure JWT-based authentication
- Role-based access control (Student, Lecturer, Admin)
- File upload and storage management
- RESTful API architecture
- H2 in-memory database (development)
- MySQL support (production)

## Technology Stack

### Backend
- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Security:** Spring Security with JWT
- **Database:** H2 (development), MySQL (production)
- **ORM:** Spring Data JPA / Hibernate
- **Build Tool:** Maven
- **File Storage:** Local file system

### Frontend
- **Single-Page Application (SPA)** - All-in-one HTML file
- **HTML5, CSS3, JavaScript (ES6+)**
- **Responsive Design** - Works on all devices
- **No framework dependencies** - Pure JavaScript
- **Modern UI/UX** - Professional interface

## Project Structure

```
sci-projects-archive/
├── src/
│   ├── main/
│   │   ├── java/com/sci/archive/
│   │   │   ├── config/           # Configuration classes
│   │   │   │   └── WebSecurityConfig.java
│   │   │   ├── controller/       # REST Controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── ProjectController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── JwtResponse.java
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── MessageResponse.java
│   │   │   │   ├── ProjectRequest.java
│   │   │   │   ├── ProjectReviewRequest.java
│   │   │   │   └── SignupRequest.java
│   │   │   ├── model/            # Entity Models
│   │   │   │   ├── Document.java
│   │   │   │   ├── Project.java
│   │   │   │   └── User.java
│   │   │   ├── repository/       # JPA Repositories
│   │   │   │   ├── DocumentRepository.java
│   │   │   │   ├── ProjectRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── security/         # Security Components
│   │   │   │   ├── AuthEntryPointJwt.java
│   │   │   │   ├── AuthTokenFilter.java
│   │   │   │   ├── JwtUtils.java
│   │   │   │   ├── UserDetailsImpl.java
│   │   │   │   └── UserDetailsServiceImpl.java
│   │   │   ├── service/          # Business Logic
│   │   │   │   ├── FileStorageService.java
│   │   │   │   └── ProjectService.java
│   │   │   └── ProjectsArchiveApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── uploads/                      # File upload directory
├── index.html                    # COMPLETE FRONTEND APP (HTML+CSS+JS)
├── pom.xml
├── README.md
├── API_DOCUMENTATION.md
├── DEPLOYMENT_GUIDE.md
├── QUICKSTART.md
├── FRONTEND_GUIDE.md            # Frontend usage guide
└── WINDOWS_SETUP_GUIDE.md
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 (for production deployment)

## Installation & Setup

### 1. Clone the repository
```bash
git clone <repository-url>
cd sci-projects-archive
```

### 2. Configure the application

Edit `src/main/resources/application.properties` for your environment:

**Development (H2 Database):**
```properties
spring.datasource.url=jdbc:h2:file:./data/scidb
spring.datasource.username=sa
spring.datasource.password=password
```

**Production (MySQL):**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sci_projects
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### 3. Build the application
```bash
mvn clean install
```

### 4. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 5. Open the Frontend
Simply **double-click** `index.html` file or open it in your browser.

The complete web interface will connect to the backend API automatically!

## API Endpoints

### Authentication Endpoints

#### Register User
```http
POST /api/auth/signup
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "role": "STUDENT",
  "studentId": "SCI001"
}
```

#### Login
```http
POST /api/auth/signin
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "fullName": "John Doe",
  "role": "STUDENT"
}
```

### Project Endpoints (Requires Authentication)

Include JWT token in header: `Authorization: Bearer <token>`

#### Create Project (Student)
```http
POST /api/projects
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Library Management System",
  "description": "A system to manage library books and users",
  "course": "Software Engineering",
  "academicYear": "2023/2024",
  "semester": "Semester 1",
  "supervisorId": 2
}
```

#### Upload Document (Student)
```http
POST /api/projects/{projectId}/documents
Content-Type: multipart/form-data
Authorization: Bearer <token>

file: <file>
documentType: PRODUCT_REQUIREMENTS_DOCUMENT
description: Product requirements for the system
```

**Document Types:**
- PRODUCT_REQUIREMENTS_DOCUMENT
- SOFTWARE_DESIGN_DOCUMENT
- USER_MANUAL
- TECHNICAL_DOCUMENTATION
- TEST_PLAN
- SOURCE_CODE
- FINAL_REPORT
- PRESENTATION
- OTHER

#### Get My Projects (Student)
```http
GET /api/projects/my-projects
Authorization: Bearer <token>
```

#### Get All Projects (Lecturer)
```http
GET /api/projects
Authorization: Bearer <token>
```

#### Get Supervised Projects (Lecturer)
```http
GET /api/projects/supervised
Authorization: Bearer <token>
```

#### Review Project (Lecturer)
```http
POST /api/projects/{projectId}/review
Content-Type: application/json
Authorization: Bearer <token>

{
  "status": "APPROVED",
  "comments": "Great work! Well documented.",
  "marks": 85
}
```

**Project Statuses:**
- SUBMITTED
- UNDER_REVIEW
- APPROVED
- REJECTED
- NEEDS_REVISION

#### Get Project Documents
```http
GET /api/projects/{projectId}/documents
Authorization: Bearer <token>
```

#### Download Document
```http
GET /api/projects/documents/{documentId}/download
Authorization: Bearer <token>
```

### User Endpoints

#### Get Current User
```http
GET /api/users/me
Authorization: Bearer <token>
```

#### Get All Lecturers
```http
GET /api/users/lecturers
Authorization: Bearer <token>
```

## Database Schema

### User Table
- id (Primary Key)
- username (Unique)
- email (Unique)
- password (Encrypted)
- full_name
- student_id (for students)
- staff_id (for lecturers)
- role (STUDENT, LECTURER, ADMIN)
- active (Boolean)
- created_at
- updated_at

### Project Table
- id (Primary Key)
- title
- description
- course
- academic_year
- semester
- student_id (Foreign Key → User)
- supervisor_id (Foreign Key → User)
- status
- lecturer_comments
- marks
- submitted_at
- updated_at
- reviewed_at

### Document Table
- id (Primary Key)
- file_name
- file_path
- document_type
- file_type
- file_size
- description
- project_id (Foreign Key → Project)
- uploaded_at

## Security

- Passwords are encrypted using BCrypt
- JWT tokens are used for authentication
- Token expiration: 24 hours (configurable)
- Role-based access control enforced at endpoint level
- CORS enabled for cross-origin requests

## File Upload Configuration

- Maximum file size: 50MB
- Maximum request size: 50MB
- Upload directory: `./uploads`
- Files organized by project: `./uploads/project_{id}/`

## Testing the Application

### Using H2 Console (Development)
1. Access H2 Console: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:file:./data/scidb`
3. Username: `sa`
4. Password: `password`

### API Testing Tools
- **Postman:** Import the endpoints and test
- **cURL:** Command-line testing
- **Swagger/OpenAPI:** (Can be added as dependency)

## Example Usage Workflow

### Student Workflow:
1. Register as student (POST /api/auth/signup)
2. Login to get JWT token (POST /api/auth/signin)
3. Create a new project (POST /api/projects)
4. Upload project documents (POST /api/projects/{id}/documents)
5. View project status (GET /api/projects/my-projects)
6. Download documents (GET /api/projects/documents/{id}/download)

### Lecturer Workflow:
1. Register as lecturer (POST /api/auth/signup)
2. Login to get JWT token (POST /api/auth/signin)
3. View all projects (GET /api/projects)
4. View project details and documents (GET /api/projects/{id})
5. Download and review documents
6. Approve/reject project (POST /api/projects/{id}/review)

## Production Deployment

### Using MySQL Database:
1. Create MySQL database:
```sql
CREATE DATABASE sci_projects;
```

2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sci_projects
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

3. Build JAR file:
```bash
mvn clean package
```

4. Run the application:
```bash
java -jar target/projects-archive-1.0.0.jar
```

### Environment Variables (Production):
```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sci_projects
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export JWT_SECRET=your_secret_key
export FILE_UPLOAD_DIR=/path/to/uploads
```

## Troubleshooting

### Common Issues:

1. **Port already in use:**
   - Change port in `application.properties`: `server.port=8081`

2. **File upload fails:**
   - Check file size limits
   - Verify upload directory permissions
   - Ensure disk space is available

3. **Database connection fails:**
   - Verify database credentials
   - Check if MySQL service is running
   - Confirm database exists

4. **JWT token expired:**
   - Request new token via login endpoint
   - Adjust expiration time in properties

## Future Enhancements

- Email notifications for project reviews
- Real-time collaboration features
- Advanced search and filtering
- Analytics dashboard
- Mobile application
- PDF report generation
- Project version control
- Plagiarism detection integration

## License

This project is for educational purposes for the School of Computing and Informatics.

## Support

For issues and questions, please contact the development team or create an issue in the repository.

---

**Developed for School of Computing and Informatics (SCI)**
