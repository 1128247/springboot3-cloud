FROM openjdk:17-jdk-slim

# 将构建的 jar 文件复制到容器中
COPY build/libs/service-provider-dubbo-1.0-SNAPSHOT.jar app.jar

# 设置容器启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]