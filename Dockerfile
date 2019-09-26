FROM ubuntu:18.04 as builder

ENV HOME=/home/app

RUN apt update &&\
    apt -y install curl zip unzip &&\
    curl -s "https://get.sdkman.io" | bash &&\
    /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java && sdk install scala && sdk install sbt"


WORKDIR $HOME

COPY project/build.properties project/plugins.sbt $HOME/project/
COPY .scalafmt.conf build.sbt $HOME/
RUN /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sbt update"
COPY src $HOME/src
RUN /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && sbt assembly"

FROM openjdk:11

ENV HOME=/home/app
WORKDIR $HOME

COPY --from=builder $HOME/target/scala-2.13/app.jar .

CMD java -jar app.jar
