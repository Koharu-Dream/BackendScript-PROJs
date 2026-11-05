# Estágio 1: Build da Aplicação com Maven e OpenJDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Baixa dependências para cache
RUN mvn dependency:go-offline -B
COPY src ./src
# Compila e gera o arquivo JAR executável ignorando os testes
RUN mvn clean package -DskipTests

# Estágio 2: Imagem de Execução Leve com JRE 17
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Porta dinâmica da nuvem (Render/Koyeb definem a variável PORT)
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
