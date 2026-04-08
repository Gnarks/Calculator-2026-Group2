#https://spring.io/guides/gs/spring-boot-docker
FROM maven:amazoncorretto
WORKDIR /backend
COPY pom.xml .
COPY src ./src
EXPOSE 1523
ENTRYPOINT ["mvn", "spring-boot:run"]
