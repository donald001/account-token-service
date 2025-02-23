# eMSP Account & Token Service

### Architecture
```mermaid
graph TD;
   subgraph Application Components
        direction TB
        I[Account-Token Service]
        I1[AccountController] --> I2[AccountService] --> I3[AccountDao]
        I4[TokenController] --> I5[TokenService] --> I6[TokenDao]
        I7[GlobalExceptionHandler]
        I8[LogAspect]
        I9[AppConfigs]
        I --> I1
        I --> I4
        I --> I7
        I --> I8
        I --> I9
    end

    subgraph External Dependencies
        J[Spring Boot Starter Data JPA]
        K[Spring Boot Starter Web with Undertow ]
        L[Spring Boot Starter Validation]
        M[Lombok]
        N[JUnit Jupiter]
        O[Mockito]
        P[Derby Database]

        I3 --> J
        I6 --> J
        I --> K
        I --> L
        I --> M
        I --> N
        I --> O
        I --> P
    end

    subgraph Frontend Resources
        Q[Static HTML/CSS/JS Files]
        R[emsp.html]
        S[health-check.html]
        T[JavaScript Libraries Axios, Quasar, Vue]
        U[CSS Stylesheets]

        Q --> R
        Q --> S
        Q --> T
        Q --> U
    end

    subgraph Configuration and Utilities
        V[application.properties]
        W[logback-spring.xml]
        X[EmaidGenerator.java]

        I --> V
        I --> W
        I --> X
    end

```
### Deployment of the application
```mermaid
graph TD;
A[GitHub] --> B(GitHub Actions);
B --> C[Maven Build];
C --> D[Docker Build];
D --> E[Push Image to Docker Hub];
E --> F[Pull Image to Docker Hub];
F --> G[Run Docker Image];
G --> H[Check the app at port: 8080];
```

### others important files
1. [RMDB sql](assets/database%20design.sql)
2. [Integration test case](assets/integration%20tests.xlsx)
3. [Shell scripts](linuxDeployShell.sh)
4. Cloud infrastructure diagram:  ![Cloud infrastructure diagram](assets/CloudInfrastructureDiagram.jpg)


5. Azure free tier needs credit/debit card verification which only recognize Visa and MasterCard. 
    AWS free tier needs information from an enterprise, but not individual. They are all unavailable to me. 
    So I deployed it to JD Cloud.  The preview of the application : ![preview](assets/TheEmspPreview.jpg)