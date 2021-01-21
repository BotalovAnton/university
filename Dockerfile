FROM openjdk
COPY target/university-1.0.jar university.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/university.jar"]
EXPOSE 8080