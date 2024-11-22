# Usa una imagen base de Java (Java 22)
FROM openjdk:22-jdk-slim

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom.xml y el archivo mvnw para asegurarte de que Maven está disponible
COPY pom.xml mvnw* ./
COPY .mvn/ .mvn/

# Copia el código fuente del proyecto
COPY src/ ./src/

# Ejecuta Maven para compilar el proyecto y crear el archivo JAR
RUN ./mvnw clean package -DskipTests

# Exponer el puerto en el que Spring Boot correrá (puerto 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación (ajustar el nombre del archivo JAR generado)
CMD ["java", "-jar", "target/caserito_api-0.0.1-SNAPSHOT.jar"]
