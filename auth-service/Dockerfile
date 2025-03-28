FROM openjdk:17
VOLUME /tmp
ADD target/auth-service.jar auth-service.jar
ENTRYPOINT [ "java","-jar","/auth-service.jar" ]