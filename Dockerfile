FROM openjdk:12
VOLUME /tmp
EXPOSE 8002
ADD ./target/springboot-servicio-item-0.0.1-SNAPSHOT.jar item-service.jar
ENTRYPOINT ["java","-jar","/item-service.jar"]