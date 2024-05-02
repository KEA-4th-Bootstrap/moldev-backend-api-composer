FROM openjdk:17
COPY ./build/libs/api-composer-0.0.1-SNAPSHOT.jar api-composer.jar
COPY ./elasticsearch/elastic-apm-agent-1.49.0.jar elastic-apm-agent.jar
COPY build.sh /build.sh
RUN chmod +x /build.sh
ENTRYPOINT ["/build.sh"]