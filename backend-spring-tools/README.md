# Backend Spring Boot

API REST del sistema de gestión de aprendices. Requiere Java 21 y MySQL 8.

## Preparar MySQL

1. Ejecuta `sql/create_database.sql` en MySQL Workbench con una cuenta
   administradora.
2. Crea un usuario local para la aplicación y asígnale permisos sobre
   `gestion_aprendices`. Por ejemplo, en una sesión administradora:

   ```sql
   CREATE USER 'gestion_app'@'localhost' IDENTIFIED BY 'REEMPLAZA_CON_UNA_CLAVE_SEGURA';
   GRANT ALL PRIVILEGES ON gestion_aprendices.* TO 'gestion_app'@'localhost';
   ```

   Reemplaza el texto de la contraseña antes de ejecutar el SQL. No uses las
   credenciales de MySQL en el código.
3. En Spring Tools, abre la configuración de ejecución de la aplicación y agrega
   las variables `DB_USER` y `DB_PASSWORD`. Opcionalmente configura `DB_URL`
   (valor por defecto: `jdbc:mysql://localhost:3306/gestion_aprendices?serverTimezone=UTC`).
4. `spring.jpa.hibernate.ddl-auto=update` crea/actualiza las tablas al arrancar.
   Para producción, usa migraciones versionadas en vez de `update`.

Desde esta carpeta, ejecuta `.\mvnw.cmd test` para las pruebas y
`.\mvnw.cmd spring-boot:run` para iniciar la API en el puerto `8080`.

Los registros anteriores vivían solo en memoria; no se migran automáticamente.
Antes de detener el backend antiguo, exporta los registros que quieras conservar.
`CorsConfig.java` define los orígenes permitidos; si cambia la URL pública del
frontend, actualiza esa lista y publica el puerto del backend desde VS Code Ports.
