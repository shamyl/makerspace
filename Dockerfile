COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install -Dmaven.test.skip=true

FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get upgrade -y

USER root
WORKDIR /app
EXPOSE 7100
COPY --from=mvnbuild /usr/src/app/target/Makerspace-0.0.1-SNAPSHOT.jar artifacts/Makerspace.jar

ENTRYPOINT ["java","-jar","/app/artifacts/Makerspace.jar"]
