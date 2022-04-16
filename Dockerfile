# official image, using alpine because is smallest unix image
FROM openjdk:11

EXPOSE 8080

ARG JAR_FILE=./target/pds03-backend-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} /app.jar

ENTRYPOINT ["java","-jar","/app.jar","-DskipTests=true"]