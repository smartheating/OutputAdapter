FROM maven:3-jdk-8

RUN git clone https://github.com/smartheating/CommonsModule.git
WORKDIR /CommonsModule
RUN mvn clean install -DskipTests -q
RUN git clone https://github.com/smartheating/OutputAdapter.git /OutputAdapter
WORKDIR /OutputAdapter
RUN mvn clean install -DskipTests -q
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=aws","-jar","target/outputAdapter-0.0.1-SNAPSHOT.jar"]