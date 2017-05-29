FROM maven:3.3.9-jdk-8

ENV SERVER

WORKDIR /code
COPY *.xml /code/
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]

COPY src /code/src
RUN ["mvn", "package"]

EXPOSE 7777

CMD java -jar target/Addresses-1.0-jar-with-dependencies.jar $SERVER