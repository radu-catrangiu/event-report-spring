FROM maven:3.6.3-openjdk-11-slim

EXPOSE 8081

COPY .mvn /app/.mvn
COPY mvnw /app/
COPY pom.xml /app/
COPY src /app/src

WORKDIR /app

RUN mvn install

CMD ["./mvnw", "spring-boot:run"]
