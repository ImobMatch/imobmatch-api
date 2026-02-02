# ===== Stage 1: Build =====
FROM ubuntu:latest AS build

# SDKMAN env
ENV SDKMAN_DIR=/opt/sdkman
ENV JAVA_HOME=$SDKMAN_DIR/candidates/java/current
ENV PATH=$JAVA_HOME/bin:$SDKMAN_DIR/bin:$PATH

# Dependencies for build
RUN apt-get update && apt-get install -y \
    curl zip unzip git wget tar maven build-essential \
    && rm -rf /var/lib/apt/lists/*

# Install SDKMAN
RUN curl -s "https://get.sdkman.io" | bash

# Install Java 25
RUN bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh && sdk install java 25.ea.1-open"

WORKDIR /app

# Copy project and build
COPY pom.xml .
COPY src ./src
RUN bash -c "source $SDKMAN_DIR/bin/sdkman-init.sh && mvn clean install -DskipTests"

# ===== Stage 2: Runtime =====
FROM ubuntu:latest

# Java env
ENV SDKMAN_DIR=/opt/sdkman
ENV JAVA_HOME=$SDKMAN_DIR/candidates/java/current
ENV PATH=$JAVA_HOME/bin:$SDKMAN_DIR/bin:$PATH

# Minimal runtime dependencies
RUN apt-get update && apt-get install -y curl unzip zip \
    && curl -s "https://get.sdkman.io" | bash \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/imobmatch-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
