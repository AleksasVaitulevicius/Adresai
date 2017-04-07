FROM maven:3.3.9-jdk-8

WORKDIR /code
COPY *.xml /code/

RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

COPY src /code/src

RUN ["mvn", "package"]

EXPOSE 8080
CMD ["java","-jar","target/addressWebService-1.0-jar-with-dependencies.jar"]