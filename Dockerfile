# ----- Stage 1: Build ------
# Build step
# Base conatainer
# Dùng image Maven ( Phiên bản 4.0.0 base JDK Amazon Corretto) để build code java
FROM maven:3.9.11-amazoncorretto AS build
#create + cd -> folder
# Đặt thư mục mặt địch làm việc trong container l /app
WORKDIR /app
# <src> ... <dest>
COPY . .

RUN mvn compile package -DskipTests

# ----- Stage 2: Runtime -----
FROM openjdk:17
WORKDIR /app

COPY --from=build /app/target/kientv84-0.0.1-SNAPSHOT.jar ./kientv84.jar

#ENV SPRING_PROFILES_ACTIVE=test


#Run step: ENTRYPOIN, CMD -- Chạy ứng dụng
#ENTRYPOIN ["sh] default base on base image
CMD java -jar /app/kientv84.jar