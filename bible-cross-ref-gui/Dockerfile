FROM openjdk:8-alpine

COPY target/uberjar/bible-cross-ref-gui.jar /bible-cross-ref-gui/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/bible-cross-ref-gui/app.jar"]
