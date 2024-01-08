FROM openjdk:17-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY ./target/chaos-app-1.0.jar chaos-app-1.0.jar
ENTRYPOINT ["java", "-jar","/chaos-app-1.0.jar"]
