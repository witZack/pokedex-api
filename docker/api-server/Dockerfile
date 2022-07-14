# Pull base image.
FROM centos:centos7

# Install
RUN yum -y update; yum clean all
RUN yum -y install epel-release

# Install java
RUN yum -y install java-1.8.0-openjdk-devel; yum clean all
ENV JAVA_HOME=/usr/lib/jvm/jre
RUN yum -y update; yum clean all

# Install sbt
RUN rm -f /etc/yum.repos.d/bintray-rpm.repo
RUN curl -L https://www.scala-sbt.org/sbt-rpm.repo > sbt-rpm.repo
RUN mv sbt-rpm.repo /etc/yum.repos.d/
RUN yum -y install sbt

### ------- Base image caching section, split later when building base image pipeline ----- ###
# Add project to cache
ADD core-api/ /tmp/project-cache/core-api
ADD core-api-server/ /tmp/project-cache/core-api-server
ADD project/ /tmp/project-cache/project
ADD build.sbt /tmp/project-cache/build.sbt
WORKDIR /tmp/project-cache

# Load up
RUN sbt core-api/compile
RUN sbt core-api-server/compile
RUN sbt core-api-server/pack

# Clean up
WORKDIR /
RUN rm -rf /tmp/project-cache/project

### ------ Installation memes done ----- ###

# Add resources
ADD core-api-server/src/main/resources /deploy/resources

# Add and pack source code
ADD core-api/ /tmp/source/app/core-api
ADD core-api-server/ /tmp/source/app/core-api-server
ADD project /tmp/source/app/project
ADD build.sbt /tmp/source/app/build.sbt
WORKDIR /tmp/source/app
RUN sbt core-api-server/pack

# Create app contents area
RUN mkdir -p /deploy/contents
RUN cp -R core-api-server/target/pack/ /deploy/contents

WORKDIR /deploy

# Start the app
CMD ["/deploy/contents/pack/bin/application"]
