FROM openjdk:17


# copy build to container
COPY ./target/restaurant-0.0.1-SNAPSHOT restaurant-0.0.1-SNAPSHOT.jar

# run the jar file
ENTRYPOINT ["java","-jar","/restaurant-0.0.1-SNAPSHOT.jar"]

