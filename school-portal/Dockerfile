FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw .

RUN chmod +x mvnw && ./mvnw package -DskipTests

COPY target/*.jar school-portal.jar

EXPOSE 8080

CMD ["java", "-jar", "school-portal.jar"]