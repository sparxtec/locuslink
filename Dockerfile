
#STAGE 1 : This is the Java enabled Linux Apline base Docker image
FROM openjdk:17-jdk-alpine

#STAGE 2 : Add Spring Boot Executible JAR to the Java Base Image
COPY  target/portal-boot.jar ./portal.jar

#STAGE 3 - Set up the run options, make port available to world outside the container
EXPOSE 80
EXPOSE 5000
EXPOSE 8443

ENTRYPOINT [ "java", "-jar", "portal.jar"]
