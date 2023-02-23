FROM openjdk:17-jdk-slim
ADD target/sicredi-cooperativismo-api-docker.jar sicredi-cooperativismo-api-docker.jar
EXPOSE 8080
ENV TZ America/Sao_Paulo
ENTRYPOINT ["java","-jar","sicredi-cooperativismo-api-docker.jar"]