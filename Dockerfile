FROM amazoncorretto:17-alpine-jdk
COPY build/libs/magnetodna-0.0.1-SNAPSHOT.jar magnetoapp.jar
ENTRYPOINT ["java","-jar","/magnetoapp.jar"]