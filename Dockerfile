FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY target/mini1.jar /app/mini1.jar

VOLUME ["/app/data"]

ENV USER_DATA_PATH=/app/data/users.json
ENV CART_DATA_PATH=/app/data/carts.json
ENV ORDER_DATA_PATH=/app/data/orders.json
ENV PRODUCT_DATA_PATH=/app/data/products.json

EXPOSE 8080

CMD ["java", "-jar", "/app/mini1.jar"]
