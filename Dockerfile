FROM openjdk:21-jdk-slim
ADD /build/libs/dbCourtnet-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
