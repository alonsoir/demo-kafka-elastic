FROM openjdk:8-jre
MAINTAINER Alonso Isidoro <alonsoir@gmail.com>
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/aironman/demo-kafka-elastic.jar"]
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/aironman/demo-kafka-elastic.jar