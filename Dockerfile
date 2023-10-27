FROM openjdk:17 as builder
ADD . /src
WORKDIR /src
RUN ./mvnw package -DskipTests

FROM openjdk:17
WORKDIR .
ENV JAVA_HOME=/opt/java-minimal
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=builder /src/target/meteo-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app.jar"]