# Stage 0 - Build JAR



#STAGE 1
# base image from Atrificatory using Maven to build Spring Boot Executible JAR
FROM artifactory.cloud.cms.gov/docker/library/maven:3.6.3-jdk-11  AS BUILD_IMAGE

COPY pom.xml   .
COPY src src
RUN mvn -f pom.xml package

#STAGE 2
# Add Spring Boot Executible JAR to the Java Base Image
FROM artifactory.cloud.cms.gov/docker/openjdk/openjdk-11-rhel7
#COPY --from=BUILD_IMAGE target/meddb-boot.jar ./meddb.jar
COPY --from=BUILD_IMAGE target/webapp-boot.jar ./webapp.jar

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
EXPOSE 5000
EXPOSE 8443
EXPOSE 2049
EXPOSE 22

# 6-27 test 3
#VOLUME ["/mnt/efs_data","/efs", "/efs/mnt/efs_data", "/"]

ENTRYPOINT [ "java", "-jar", "webapp.jar"]


