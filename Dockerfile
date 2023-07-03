FROM openjdk:17
WORKDIR /
ADD target/instancemanagement-1.0-SNAPSHOT.jar app.jar
RUN useradd -m imuser
USER imuser
EXPOSE 4000
CMD java -jar -Dspring.profiles.active=prod app.jar
