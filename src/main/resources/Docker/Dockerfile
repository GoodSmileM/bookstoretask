FROM openjdk:18-jdk-slim-buster
ENV APP_PATH=/apps
WORKDIR $APP_PATH
ADD bookstore-0.0.1-SNAPSHOT.jar $APP_PATH/bookstore.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar"]
CMD ["bookstore.jar"]
