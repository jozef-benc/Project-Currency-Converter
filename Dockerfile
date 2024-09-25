FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/currency-converter.jar /app/currency-converter.jar
ENTRYPOINT ["java", "-jar", "currency-converter.jar"]
