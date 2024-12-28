# Conference Management System

## Description
A Spring Boot application for managing academic conferences, including user authentication, conference submissions, evaluations, and role-based access control.

## Features
- User authentication and authorization
- Conference management
- Paper submissions
- Evaluation system
- Role-based access control (Author, Editor, Reviewer)

## Technology Stack
- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Lombok
- BCrypt Password Encryption

## Project Structure
```
com.example.conferenceManagement/
├── config/
│   └── SecurityConfig.java
├── controllers/
│   ├── AuthController.java
│   ├── ConferenceController.java
│   ├── DecisionController.java
│   ├── EvaluationController.java
│   ├── SubmissionController.java
│   └── UserController.java
├── dto/
│   ├── LoginDTO.java
│   └── UserDTO.java
├── entities/
│   ├── Conference.java
│   ├── Evaluation.java
│   ├── Submission.java
│   ├── User.java
│   └── UserRole.java
├── enums/
│   ├── EEvaluationStatus.java
│   ├── ESubmissionStatus.java
│   └── EUserRole.java
├── repositories/
│   ├── ConferenceRepository.java
│   ├── EvaluationRepository.java
│   ├── SubmissionRepository.java
│   ├── UserRepository.java
│   └── UserRoleRepository.java
└── services/
    ├── impl/
    │   ├── ConferenceServiceImpl.java
    │   ├── DecisionServiceImpl.java
    │   ├── ReviewServiceImpl.java
    │   ├── SubmissionServiceImpl.java
    │   ├── UserRoleServiceImpl.java
    │   └── UserServiceImpl.java
    └── interfaces/
        ├── ConferenceService.java
        ├── DecisionService.java
        ├── EvaluationService.java
        ├── SubmissionService.java
        ├── UserRoleService.java
        └── UserService.java
```

## API Endpoints

### Authentication
```
POST /api/auth/signin - User authentication
POST /api/auth/signup - New user registration
```

### Conferences
```
GET /api/conferences - Get all conferences
GET /api/conferences/{conferenceId} - Get specific conference
POST /api/addConference - Create new conference
GET /api/conferences/decision/{conferenceId} - Get conference decision status
```

### Users
```
GET /api/users - Get all users
GET /api/users/{userId} - Get specific user
```

## Setup Instructions

1. Prerequisites
    - Java 17 or higher
    - Maven
    - MySQL database

2. Clone the repository
   ```bash
   git clone <repository-url>
   ```

3. Configure database
   Add the following properties to `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/conference_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Build the project
   ```bash
   mvn clean install
   ```

5. Run the application
   ```bash
   mvn spring-boot:run
   ```

## Database Schema

### Users Table
- id (Long, PK)
- first_name (String)
- last_name (String)
- email (String, unique)
- password (String, encrypted)
- create_at (DateTime)
- update_at (DateTime)

### Conferences Table
- id (Long, PK)
- title (String)
- description (String)
- start_date (Date)
- end_date (Date)
- theme (String)
- status (Enum)
- create_at (DateTime)
- update_at (DateTime)

### Submissions Table
- id (Long, PK)
- title (String)
- summary (String)
- pdf_url (String)
- status (Enum)
- conference_id (Long, FK)
- created_at (DateTime)
- updated_at (DateTime)

### Evaluations Table
- id (Long, PK)
- score (Integer)
- comment (Text)
- status (Enum)
- submission_id (Long, FK)
- reviewer_id (Long, FK)
- create_at (DateTime)
- update_at (DateTime)

### User_Roles Table
- user_id (Long, PK, FK)
- conference_id (Long, PK, FK)
- role (Enum)

## Security

The application uses Spring Security with:
- BCrypt password encoding
- DTO pattern for sensitive data
- Role-based access control
- Currently configured to permit all requests (for development)

## Future Improvements
1. Complete authentication flow implementation
2. Add CSRF protection
3. Implement role-based authorization
4. Complete evaluation and submission workflows
5. Add input validation
6. Implement error handling across services
7. Add logging and monitoring
8. Implement file upload for PDF submissions
9. Add email notification system
10. Implement conference deadline management