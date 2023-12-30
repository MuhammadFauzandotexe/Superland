FROM adoptopenjdk/openjdk11:alpine-jre
ADD target/Superland-0.0.1-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
