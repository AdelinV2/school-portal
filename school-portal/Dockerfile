FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY school-portal/pom.xml .
COPY school-portal/src ./src
COPY school-portal/.mvn ./.mvn
COPY school-portal/mvnw .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

COPY target/*.jar school-portal.jar

EXPOSE 8080

CMD ["java", "-jar", "school-portal.jar"]