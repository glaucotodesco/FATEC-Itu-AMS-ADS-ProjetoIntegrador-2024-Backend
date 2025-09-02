FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
COPY . /home/app/backend
RUN mvn -v
RUN mvn -f /home/app/backend/pom.xml clean package -DskipTests -Dspring-boot.run.arguments="--spring.profiles.active=dev"


#Step 2 - Run backend
FROM alpine/java:21-jdk
COPY --from=build /home/app/backend/target/*.jar /usr/local/lib/backend.jar
VOLUME /tmp
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/backend.jar"]