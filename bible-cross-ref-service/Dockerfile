FROM openjdk:8-alpine

COPY target/uberjar/bible-cross-ref-service.jar /bible-cross-ref-service/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/bible-cross-ref-service/app.jar"]
