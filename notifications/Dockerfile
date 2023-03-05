FROM maven:latest AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD pom.xml $HOME
RUN mvn verify --fail-never
ADD . $HOME
RUN mvn clean
RUN mvn package -DskipTests

FROM bellsoft/liberica-openjre-alpine:17
ARG JAR_FILE=*.jar
COPY --from=build /usr/app/target/${JAR_FILE} application.jar
COPY wait-for.sh ./wait-for.sh
#ENTRYPOINT ["java", "-jar", "application.jar"]
