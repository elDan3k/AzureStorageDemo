FROM openjdk:8u121-jdk
ADD ./target/azure-demo.jar azure-demo.jar
EXPOSE 8080
CMD ["java", "-jar", "azure-demo.jar"]