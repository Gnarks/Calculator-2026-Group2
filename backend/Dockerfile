#https://spring.io/guides/gs/spring-boot-docker
FROM maven:amazoncorretto@sha256:849a0727279574eef2b9e973822d9643fe4da9ce4be58623b40591fe9bd55057
WORKDIR /backend
COPY pom.xml .
COPY src ./src
EXPOSE 1523
ENTRYPOINT ["mvn", "spring-boot:run"]
