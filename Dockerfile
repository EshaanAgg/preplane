FROM openjdk:20

WORKDIR /preplane
COPY . .
RUN ["./mvnw", "clean", "package"]

ENTRYPOINT ["java","-jar","target/dev-1.0.0.jar"]