FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:23-jdk AS runtime

COPY target/*.jar school-portal.jar

EXPOSE 8080

CMD ["java", "-jar", "school-portal.jar"]