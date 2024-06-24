#  User Management Application using Struts 2

### Application development environment

The application is developed using
	
	1. Struts 2
	2. Java 17
	3. Hibernate/JPA
	4. MySQL
    5. Junit + Mockito
    6. Cypress
    7. Spring Security


### How to run the application with maven build (Recommended)

To run the application the following steps need to processed
	
	./mvnw clean install	 -DskipTests  -- if test cases run not required
	./mvnw clean install     -- with test case
	
The above command will build the application


The above command start the application on `9097` port.

### Testing application with sample data
 
	curl --location --request POST 'http://localhost:9097/UserManagement/' --header 'Content-Type: application/json'

### Assumption for the application
The application is developed based on assumptions

    User Management Application using Struts 2

    1.Pages:
        - Login Page:
            - Fields: Username, Password.
        - Sign Up Page:
            - Fields: Name, Email (used as username), Password, Repeat Password.
        - User Page:
            - Listing of users:
                - Columns: User ID, Name, Email.
            - Add User:
                - Fields: Name, Email (used as username), Password, Repeat Password.
            - Delete User:
                - Ability to delete existing users.

    2.Application Requirements:
    - JavaScript validations on form submissions.
    - Backend validations to prevent duplicate email registrations.
    - Unit test cases developed using JUnit.
    - UI test cases developed using Cypress.

   
### Database Structure

The application is using MySQL

	CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
    );


	
	
	 	