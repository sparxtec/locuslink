# Stage 0 - Build JAR



#STAGE 1
# base image from Atrificatory using Maven to build Spring Boot Executible JAR
#FROM artifactory.cloud.cms.gov/docker/library/maven:3.6.3-jdk-11  AS BUILD_IMAGE

#FROM maven:3.6.3-jdk-11  AS BUILD_IMAGE

#FROM maven:3.5.2-jdk-8-alpine AS BUILD_IMAGE

FROM alpine:3.10 AS BUILD_IMAGE


#FROM openjdk

#COPY src src
#COPY pom.xml   .
#WORKDIR src

#run mkdir -p bin

#ADD apache-maven-3.3.9 /opt/maven3

#RUN mvn -f pom.xml package


# C.Sparks 12-20-2022 This is the step to copy the JAR into the image
COPY --from=BUILD_IMAGE target/portal-boot.jar ./portal.jar


#STAGE 2
## Add Spring Boot Executible JAR to the Java Base Image
#FROM artifactory.cloud.cms.gov/docker/openjdk/openjdk-11-rhel7
##COPY --from=BUILD_IMAGE target/meddb-boot.jar ./meddb.jar
#COPY --from=BUILD_IMAGE target/portal-boot.jar ./portal.jar

# 6-24-2022
#tried without the dot, and throws can make the directory error
#RUN mkdir -p ./mnt/efs_data
#RUN mkdir -p ./efs
#VOLUME /efs ./mnt/efs_data efs

# 6-27 test 1, task wont start
#VOLUME .  /efs  /mnt/efs_data

# 6-27 test 2
#VOLUME ./mnt/efs_data /mnt/efs_data  /efs efs

#RUN mkdir -p ./mnt/efs_data
#WORKDIR /

#STAGE 3 - Set up the run options, make port available to world outside the container
EXPOSE 80
EXPOSE 5000
EXPOSE 8443

# 6-27 test 3
#VOLUME ["/mnt/efs_data","/efs", "/efs/mnt/efs_data", "/"]

ENTRYPOINT [ "java", "-jar", "portal.jar"]


