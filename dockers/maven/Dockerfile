FROM ubuntu:latest

#Java 8 install
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java7-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean

#Maven install
RUN apt-get install -y maven

#Arping install
RUN apt-get install -y arping

WORKDIR /home

ADD mysql.properties .
ADD mysql-prod.properties .

#Opening http port
EXPOSE 8080