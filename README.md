# Covid Tracking Backend Application
A Spring Boot application using the external public disease.sh api to provide covid information to registered users.
# Tech Stack  
- Spring  
- Spring Security  
- Spring Data JPA  
- MySQL
# Endpoints
- public endpoints  
    - /api/v1/users/register  
        - request body needed  
- authenticated user endpoints  
    - /api/v1/users/change-username  
        - param needed  
    - /api/v1/users/change-email  
        - param needed  
    - /api/v1/users/change-password  
        - param needed  
    - /api/v1/users/delete  
    - /api/v1/covid/data/{countries}  
        - path variable countries needed  
        - optional two params  
- authenticated user with role admin endpoints  
    - api/v1/users/all  
    - api/v1/covid/data  
        - optional two params  
# Run my application  
To run my application you will need a mysql database with the following tables:  
    - user  
    - authority  
    - user_authority  
Paste the name of your database into the application.properties file.  
Create the jar with maven clean install command and run it.  


