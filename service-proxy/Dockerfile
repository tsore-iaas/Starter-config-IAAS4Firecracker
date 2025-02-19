FROM openjdk:17
LABEL authors="zaz"
VOLUME /tmp
ADD target/service-proxy.jar service-proxy.jar
ENTRYPOINT [ "java","-jar","/service-proxy.jar" ]