FROM maven:3.8.3-openjdk-11 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11
COPY --from=build /target/moviekit-0.0.1-SNAPSHOT.jar moviekit.jar
EXPOSE 8080
ENTRYPOINT ["java" "-jar", "moviekit.jar"]