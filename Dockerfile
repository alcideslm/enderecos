from openjdk

WORKDIR /app

COPY target/endereco-0.0.1-SNAPSHOT.jar /app/endereco.jar

ENTRYPOINT [ "java", "-jar", "endereco.jar" ]