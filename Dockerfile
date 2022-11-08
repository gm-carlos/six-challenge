FROM adoptopenjdk/maven-openjdk11

ENV SOURCE_DIR=/tradingplatform

WORKDIR ${SOURCE_DIR}
ADD . ${SOURCE_DIR}

RUN mvn clean install

ENTRYPOINT ["java","-jar","target/trading-platform-0.0.1-SNAPSHOT.jar","TradingPlatform.java"]