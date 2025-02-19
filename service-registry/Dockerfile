FROM openjdk:17
LABEL authors="zaz"
VOLUME /tmp
ADD target/service-registry.jar service-registry.jar
ENTRYPOINT [ "java","-jar","/service-registry.jar" ]