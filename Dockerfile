# 使用官方的OpenJDK镜像作为基础镜像
FROM openjdk:17-jdk-slim
WORKDIR /app

# 将构建好的jar文件复制到镜像中
COPY target/account-token-service-0.0.1-SNAPSHOT.jar /app/account-token-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "account-token-service-0.0.1-SNAPSHOT.jar"]