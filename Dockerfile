FROM maven as builder
COPY . /code/
WORKDIR /code
RUN mvn -DskipTests=true package

FROM openjdk:8-jre
RUN apt-get update; apt-get install -y netcat
COPY --from=builder /code/target/*.jar /app/
WORKDIR /app
EXPOSE 8543
CMD [ "java", "-jar", "myblog-0.0.1-SNAPSHOT.jar" ]