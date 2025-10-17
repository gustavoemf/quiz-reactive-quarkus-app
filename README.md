# Quiz Reactive Project

## Descrição
Projeto de quiz multiplayer usando **Quarkus reativo**, **PostgreSQL**, **Redis**, **Kafka**, **Keycloak** e arquitetura de microsserviços.  
O objetivo é implementar e estudar cada tecnologia passo a passo.

---

## Status do Projeto

- [ ] **Etapa 1: Quarkus Básico**
  - Criar projeto Quarkus
  - Adicionar extensões básicas (`resteasy-reactive`)
  - Endpoint de HealthCheck **quarkus-smallrye-health**

- [ ] **Etapa 2: Banco de Dados (PostgreSQL Reativo)**
  - Adicionar `quarkus-reactive-pg-client` ou `hibernate-reactive-panache`
  - Criar entidade `Quiz`
  - CRUD básico de quizzes
  - Testar persistência e listagem

- [ ] **Etapa 3: Cache com Redis**
  - Adicionar extensão `quarkus-redis-client`
  - Criar serviço de cache para quizzes e ranking
  - Testar TTL de cache e integração com PostgreSQL

- [ ] **Etapa 4: Kafka**
  - Adicionar `quarkus-smallrye-reactive-messaging-kafka`
  - Criar producer/consumer para eventos de respostas e ranking
  - Testar comunicação assíncrona

- [ ] **Etapa 5: Autenticação com Keycloak**
  - Subir Keycloak local ou docker
  - Configurar realm, cliente e roles
  - Adicionar `quarkus-oidc`
  - Proteger endpoints (admin e usuário)

- [ ] **Etapa 6: Microsserviços**
  - Separar serviços: Quiz, User, Score, Notification
  - Testar comunicação via REST ou Kafka
  - Garantir integração com cache e DB
     
- [ ] **Etapa 7: Observabilidade**
  - Adicionar logging estruturado (Quarkus Logging + JSON)
  - Adicionar métricas com **Micrometer / SmallRye Metrics**
  - Tracing distribuído com **OpenTelemetry / Jaeger**
  - Dashboard (Grafana / Prometheus)/(ELK) opcional para monitorar métricas

---

## Como rodar o projeto

### 1. Rodar em modo dev (hot reload)
```bash
./gradlew quarkusDev
```
Isso vai iniciar o Quarkus em modo desenvolvimento. Qualquer alteração no código será recarregada automaticamente.

### 2. Build do projeto
```bash
./gradlew build
```

Isso gera o jar do projeto em build/quarkus-app/quarkus-run.jar.

### 3. Rodar o jar gerado
```bash
java -jar build/quarkus-app/quarkus-run.jar
```

### 4. Rodar testes
```bash
./gradlew test
```
