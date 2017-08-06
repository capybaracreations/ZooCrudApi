FROM openjdk:8-jre-alpine

ENV JAVA_OPTS=""

# add directly the war
ADD *.jar /app.jar

EXPOSE 8080
CMD sleep ${DB_STARTUP_DELAY} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar
