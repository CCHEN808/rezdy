FROM openjdk:11
ADD target/java-tech-task-0.0.1-SNAPSHOT.jar java-tech-task-0.0.1-SNAPSHOT.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "java-tech-task-0.0.1-SNAPSHOT.jar"]
