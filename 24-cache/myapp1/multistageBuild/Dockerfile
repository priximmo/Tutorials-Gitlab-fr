FROM alpine/git as clone
WORKDIR /app
RUN git clone https://gitlab.com/xavki/api-springboot-postgresql-sample.git

FROM maven:3.5-jdk-8-alpine as build
WORKDIR /app
COPY --from=clone /app/api-springboot-postgresql-sample /app
RUN mvn clean install -DskipTests

FROM openjdk:14-slim-buster
CMD mkdir /jar
COPY --FROM=build /app/target/*.jar /jar/
EXPOSE 8080

