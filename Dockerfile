ARG MAVEN_IMAGE=docker.io/library/maven:3-eclipse-temurin-21-alpine 
ARG JDK_IMAGE=docker.io/library/eclipse-temurin:21-alpine 
ARG WORKDIR=/app

FROM ${MAVEN_IMAGE} AS build
ARG WORKDIR
WORKDIR ${WORKDIR}
COPY ./pom.xml ${WORKDIR}
RUN [ "mvn", "dependency:go-offline" ]
COPY ./src ${WORKDIR}/src
RUN [ "mvn", "package", "-DskipTests" ]

FROM ${JDK_IMAGE} AS prod
ARG WORKDIR
WORKDIR ${WORKDIR}
COPY --from=build ${WORKDIR}/target/*.jar ${WORKDIR}/app.jar
CMD [ "java", "-jar", "app.jar", "--spring.profiles.active=prod" ]

FROM ${MAVEN_IMAGE} AS dev
ARG WORKDIR
WORKDIR ${WORKDIR}
COPY . ${WORKDIR}
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=dev"]
