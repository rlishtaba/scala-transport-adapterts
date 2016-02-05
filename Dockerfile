FROM maven:3.3.3-jdk-8

MAINTAINER Roman Lishtaba "roman@lishtaba.com"

ENV SBT_VERSION 0.13.9

RUN mkdir -p /usr/local/bin && \
    wget -P /usr/local/bin/ http://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/$SBT_VERSION/sbt-launch.jar && \
    ls /usr/local/bin

COPY scripts/sbt /usr/local/bin/

COPY scripts/test-sbt.sh /tmp/

ENV SCALA_VERSION 2.11.2

RUN cd /tmp && \
    mkdir -p src/main/scala && \
    echo "object Main {}" > src/main/scala/Main.scala && \
    ./test-sbt.sh && \
    rm -rf *

COPY pom.xml /tmp/

RUN cd /tmp &&\
    mkdir maven-test && \
    cp pom.xml maven-test/ && \
    cd maven-test && \
    mvn verify && \
    rm -rf *

RUN mkdir /ondeck
WORKDIR /ondeck
COPY . WORKDIR
