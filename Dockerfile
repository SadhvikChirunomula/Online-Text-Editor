FROM centos:latest

RUN curl -O https://download.oracle.com/java/18/latest/jdk-18_linux-x64_bin.rpm

RUN rpm -ivh jdk-18_linux-x64_bin.rpm

COPY ./target/OnlineTextEditor-0.0.1-SNAPSHOT.jar .

COPY ./startup-script.sh .

ENTRYPOINT ["sh", "startup-script.sh"]

EXPOSE 8080