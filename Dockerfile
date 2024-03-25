# Utilisez l'image de base Java
FROM openjdk:17-slim as builder

# Installez Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Définissez le répertoire de travail dans le conteneur
WORKDIR /app

# Copiez le fichier pom.xml et téléchargez les dépendances
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiez les sources du projet
COPY src src

# Construisez l'application
RUN mvn package -DskipTests

# Préparez la phase de déploiement
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
