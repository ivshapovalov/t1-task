#
# Build stage of two java services
#
FROM maven:3.9.6-eclipse-temurin-21 AS BUILD_STAGE
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY . $HOME

RUN mvn -f $HOME/pom.xml clean package -DskipTests=true
