FROM openjdk:25-ea-4-jdk-oraclelinux9
WORKDIR /app

COPY target/mini1.jar /app/mini1.jar

#COPY com.example/data /app/data

ENV USER_DATA_PATH=/app/data/users.json
ENV CART_DATA_PATH=/app/data/carts.json
ENV ORDER_DATA_PATH=/app/data/orders.json
ENV PRODUCT_DATA_PATH=/app/data/product.json

EXPOSE 8080

CMD ["java","-jar","/app/target/mini1.jar"]

