# 1. Use a Maven image that supports Java 21 or 23 (matching your project)
FROM maven:3-eclipse-temurin-21 AS build
WORKDIR /app

# 2. Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# 3. Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# 4. Use a lightweight Runtime image (JRE instead of JDK)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 5. Fix the typo: '--from', not '--form'
COPY --from=build /app/target/demoAuthentication-0.0.1-SNAPSHOT.jar app.jar

# 6. Expose the port
EXPOSE 8080

# 7. Add spaces to the ENTRYPOINT array
ENTRYPOINT ["java", "-jar", "app.jar"]