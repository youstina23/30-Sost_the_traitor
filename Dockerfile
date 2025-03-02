FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app
COPY ./ /app
EXPOSE 8080
CMD ["java","-jar","/app/target/mini1.jar"]



FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app


ENV NAME Docker_Youssef

COPY target/ target/

ENTRYPOINT ["java" ,"-jar" ,"target/task1.jar" ]