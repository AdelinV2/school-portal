FROM maven:3.9-eclipse-temurin-23 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk AS runtime

COPY --from=build /app/target/*.jar school-portal.jar

EXPOSE 8080

CMD ["java", "-jar", "school-portal.jar"]