FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/trackingfriendsservice-0.0.1-SNAPSHOT.jar trackingfriendsservice.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "trackingfriendsservice.jar"]