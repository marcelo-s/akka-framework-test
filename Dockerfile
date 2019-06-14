#Use to build docker image for linux machine
#FROM openjdk:8
#Use to build docker image for raspberrypi model 3B+
FROM arm32v7/gradle
COPY ./build/libs /akka/wsn/app/jar

COPY ./scripts/wait-for-it.sh /akka/wsn/app/jar

WORKDIR /akka/wsn/app/jar

