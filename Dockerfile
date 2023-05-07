FROM adoptopenjdk/openjdk11:latest

ARG APP_USER=2022

WORKDIR /home/invoices/

RUN chmod -R 777 /home/invoices/

ENV TZ=America/Costa_Rica

COPY --chown=$APP_USER target/spring-data-invoice.jar /home/invoices/spring-data-invoice.jar

VOLUME /tmp

EXPOSE 8080/tcp

USER $APP_USER

CMD  java -jar -XX:+UseG1GC spring-data-invoice.jar