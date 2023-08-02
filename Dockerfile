FROM openjdk:19-slim

COPY . .

EXPOSE 8080

RUN apt update && apt install -y maven && apt clean && mvn clean package -DskipTests && mv target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
