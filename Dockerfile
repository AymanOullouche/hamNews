FROM openjdk:21

ARG JAR_FILE= target/hamNews-1.0-SNAPSHOT-jar-with-dependencies.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
