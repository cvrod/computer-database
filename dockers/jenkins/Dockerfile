FROM jenkins:latest

USER root

RUN apt-get update &&\
    apt-get install curl

RUN curl -sSL https://get.docker.com/ | sh