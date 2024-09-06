# Self-Taught Banking API From Scratch (2024)

**Self-Taught Project / Projeto Autodidata** 
 
![2024-09-06_11-06](https://github.com/user-attachments/assets/38e5983d-c4c8-495d-8135-d1e023591da1)

"Robust REST API, self-taught development, simulates a banking system offering advanced features such as financial transactions, report generation, notification email sending, and savings account interest calculation. Key features include endpoints for file upload/download, JWT authentication, cache storage, automated tests, and MySQL and external Api integration, all documented in Swagger UI.",

## Features

- **REST API**:
  - Upload/Download file endpoints
  - Validations
  - Endpoints: GET, POST, PUT, DELETE
  - Reports generate
  - Login/Authorization
    
- **Dockerization**:
  - Dockefile: builds image for app
  - Docker-compose
  - Uses Mysql and Redis conteiners
   
- **Testing**:
  - Test automation with JUnit and Mockito

- **Database**:
  - MySQL database

- **Documentation**:
  - Swagger UI

- **Caching**:
  - Cache storage

- **Security**:

  - JWT authentication

- **Others**:
  
  -Email notifications
  
  -HTTP interaction with external APIs
  
  -Exception handling
  
  -Design Patterns: Factory, Strategy
  
### [www.linkedin.com/in/pedro-leon-carvalho](#)

![2024-09-06_10-50](https://github.com/user-attachments/assets/5cbec450-6a37-47a5-90ab-1d3fed7715a2)
![2024-09-06_10-50_1](https://github.com/user-attachments/assets/26b94e48-2102-4f21-97ff-d9c74e2658ed)
![2024-09-06_10-50_2](https://github.com/user-attachments/assets/d410656b-b84a-4980-b7a7-771012a4b9df)

How to Run

To run the RESTful Banking API From Scratch project, follow the steps below:

Clone the Repository:Open your terminal and clone the project repository using Git:

```
git clone git@github.com:PedroLeonCarvalho/RESTful-banking-API-from-scratch--no-curses-or-tutorials-involved-.git 
```

Navigate to the Project Directory:
Change to the project directory:

```
cd RESTful-banking-API-from-scratch--no-curses-or-tutorials-involved-
```

Build and Run Containers with Docker Compose:
Ensure you have Docker and Docker Compose installed. Then, in the project directory, run the following command to build and start the application:

```
docker-compose up --build
```

Access the Application:

The API will be accessible at http://localhost:8080.
Swagger UI documentation can be accessed at ``` http://localhost:8080/swagger-ui.html ```

Shut Down the Containers:
When you are done, stop and remove the containers with:
```
docker-compose down
```

