# Backend Spring Boot

API REST del sistema de gestion de aprendices. Requiere Java 21.

Desde esta carpeta:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

La API escucha en el puerto `8080`. `CorsConfig.java` define los origenes
permitidos; si cambia la URL publica del frontend, actualiza esa lista y publica
el puerto del backend desde VS Code Ports.
