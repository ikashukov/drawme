FROM openjdk:8-jdk-alpine AS builder
COPY src/ src/
COPY .mvn .mvn
COPY pom.xml .
COPY mvnw .
RUN chmod +x ./mvnw && ./mvnw package -DskipTests && mv target/*.jar /app.jar

FROM openjdk:8-jdk-alpine
COPY --from=builder /app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]