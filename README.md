# QuizApp

1. Project Description

QuizApp is a Spring Boot-based web application designed for creating and managing quizzes. Users can create quizzes with multiple questions of different types (single-choice, multiple-choice, and text-based). Each question can have several options, and correct answers can be marked for automatic scoring.

The application allows users to take quizzes by fetching questions without revealing the correct answers. After submission, the system calculates and returns the user’s score. The project also includes input validation and unit tests to ensure reliability and correctness.

Key Features:

Create quizzes with titles.

Add questions and options with proper validation.

Fetch questions for quizzes without exposing correct answers.

Submit answers and get an accurate score.

Unit tests for service layers to ensure functionality.



2.Setup and Run Locally

Clone or Download the Project
Download the project source code or clone it to your local machine using your preferred method.

Import into IDE
Open your IDE (e.g., Spring Tool Suite, IntelliJ IDEA) and import the project as a Maven project.

Configure MySQL Database

Ensure MySQL is installed and running on your system.

Create a database for the project, e.g., quizapp_db.

Update the database configuration in src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/quizapp_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


Install Dependencies
Maven will automatically download required dependencies defined in pom.xml when you import the project.

Run the Application

In your IDE, run the QuizAppApplication class as a Java Application.

The backend will start on the default port 8080.

Access API

Use Postman or any API client to test endpoints like:

POST /api/quizzes → Create a quiz

POST /api/questions/{quizId} → Add questions

POST /api/options/{questionId} → Add options

GET /api/questions/{quizId} → Fetch questions

POST /api/submissions/{quizId} → Submit answers



3.Running Test Cases Locally

Ensure Dependencies are Installed
Maven will automatically include the required dependencies for testing, such as:

JUnit 5 (for unit testing)

Mockito (for mocking services and repositories)

Open Test Classes
Test classes are located under:
src/test/java/com/example/demo/service/

Example test classes:

SubmissionServiceTest.java

QuestionServiceTest.java

QuizOptionServiceTest.java

QuizServiceTest.java

Run Tests in IDE

Right-click on the test class or individual test method.

Select Run As → JUnit Test (in Spring Tool Suite or IntelliJ IDEA).

The console will show the results (green = passed, red = failed).



4.Assumptions and Design Choices

Question Types

The quiz supports three types of questions:

SINGLE – Only one correct option.

MULTIPLE – Multiple correct options.

TEXT – User enters text; max 300 characters.

Each SINGLE or MULTIPLE choice question must have at least one correct option.

Data Storage

MySQL is used as the relational database to store quizzes, questions, options, and submissions.

JPA/Hibernate handles object-relational mapping.

Scoring

The score is calculated based on fully correct answers:

SINGLE: Selected option matches the correct one.

MULTIPLE: All correct options must be selected, and no wrong options selected.

Partial credit is not awarded.

Validation

Text questions cannot exceed 300 characters.

SINGLE/MULTIPLE choice questions must have at least one correct option.

Invalid question types or missing data will throw a runtime exception.

Service Layer Design

Services are separated by entity responsibility: QuizService, QuestionService, QuizOptionService, SubmissionService.

This ensures modularity and easier unit testing with JUnit and Mockito.

Assumptions

A user can only submit answers for an existing quiz.

Quiz and questions are pre-created before submission.

No user authentication is implemented; userId is passed directly during submission.
