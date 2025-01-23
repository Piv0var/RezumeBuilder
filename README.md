# Resume Builder

## Project Description
Resume Builder is a web application designed to help users create professional resumes with ease. The intuitive interface allows for customization and quick generation of resumes in a variety of formats.

## Features
- Home page with project overview
- Login and registration system
- Resume creation page with customizable templates
- Real-time preview of the resume during creation

## Team Members
- **Danylo Hychak** - Team Lead | Dev
- **Vlasihin Ivan** - Developer
- **Golovan Ihor** - Developer

## Technologies Used
- **Backend:** Java, Spring Boot MVC
- **Frontend:** HTML, CSS, JavaScript
- **Database:** MySQL
- **Version Control:** Git

## Installation Instructions
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd resume-builder
   ```
3. Install dependencies:
   - For Java, ensure Maven is configured and use:
     ```bash
     mvn clean install
     ```
   - For frontend, install any required dependencies (if applicable).
4. Configure the database:
   - Create a MySQL database named `resume_builder`.
   - Update the `application.properties` file with your database credentials.
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
6. Access the application at `http://localhost:8080`.

## Testing
- Run unit tests using:
  ```bash
  mvn test
  ```
- Test coverage: 85%

## Documentation
- Code is commented following Javadoc standards.
- See the [Wiki](<repository-url>/wiki) for detailed usage instructions and FAQs.

## Contribution
Contributions are welcome! Please fork the repository and create a pull request with your changes.

## License
This project is licensed under the MIT License.
