# ImobMatch API

API desenvolvida com **Spring Boot**, configurada para múltiplos ambientes usando **Spring Profiles**. Suporta PostgreSQL, H2 em memória e integração com S3 (MinIO para dev, AWS para prod).

---

## Perfis de ambiente

O projeto utiliza **Spring Boot Profiles** para separar ambientes:

| Profile | Banco de dados          | Observações                                       |
| ------- | ----------------------- | ------------------------------------------------- |
| `dev`   | PostgreSQL (via Docker) | Desenvolvimento local com dados persistentes     |
| `test`  | H2 (em memória)         | Testes rápidos automatizados                      |
| `prod`  | PostgreSQL              | Produção, configurar host e credenciais depois   |

| Profile | S3                  |
| ------- |---------------------|
| `dev`   | MinIO  (via Docker) |
| `test`  | Não possui          |
| `prod`  | AWS S3              |

O profile ativo é definido em `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: ImobMatch API
  profiles:
    active: dev  # altere para dev / test / prod conforme necessário
````

---

## Mode DEV

1. **Iniciar o container do banco (PostgreSQL) via Docker**:

```bash
docker-compose up -d
```

2. **Configurar o profile no Spring Boot**:

```yaml
spring:
     profiles:
            active: dev
```

3. **Configuração do datasource (`application-dev.yaml`)**:

* Variáveis definidas em `.env`
* Conexão automática com o container Docker
* Habilitado S3 via MinIO, JWT, e email de teste (SMTP fake ou real)

Exemplo resumido:

```yaml
spring:
      datasource:
            url: ${DATASOURCE_URL}
            driver-class-name: org.postgresql.Driver
            username: ${DATASOURCE_USERNAME}
            password: ${DATASOURCE_PASSWORD}
      jpa:
            database-platform: org.hibernate.dialect.PostgreSQLDialect
            hibernate:
               ddl-auto: validate
            show-sql: false
   
      mail:
            username: ${MAIL_USERNAME}
            password: ${MAIL_PASSWORD}

api:
     security:
         token:
            secret: ${JWT_SECRET}


aws:
      region: ${AWS_REGION}
      access-key: ${AWS_ACCESS_KEY_ID}
      secret-key: ${AWS_SECRET_ACCESS_KEY}
      s3:
            bucket:
               profile-photos: profile-photos
               creci-documents: creci-documents
               property-documents: property-documents
               property-images: property-images
```

---

## Mode TEST

1. **H2 em memória** → não precisa iniciar container.
2. **Alterar profile** no `application.yml`:

```yaml
spring:
  profiles:
    active: test
```

3. **Configuração do datasource (`application-test.yaml`)**:

```yaml
spring:
     datasource:
          url: jdbc:h2:mem:imobmatchdb-test
          driver-class-name: org.h2.Driver
          username: sa
          password:
     jpa:
          database-platform: org.hibernate.dialect.H2Dialect
          hibernate:
                ddl-auto: update
          show-sql: true
     h2:
            console:
               enabled: true
               path: /h2-console
     mail:
          host: localhost
          port: 1025
          username:
          password:

api:
  security:
    token:
      secret: test-secret  
```

---

## Mode PROD

1. **Alterar profile** no `application.yml`:

```yaml
spring:
  profiles:
    active: prod
```

2. **Configuração do datasource (`application-prod.yaml`)**:

* Variáveis obrigatórias definidas em `.env`
* PostgreSQL configurado para produção
* AWS S3 habilitado

Exemplo resumido:

```yaml
spring:
     datasource:
          url: ${DATASOURCE_URL}
          driver-class-name: org.postgresql.Driver
          username: ${DATASOURCE_USERNAME}
          password: ${DATASOURCE_PASSWORD}
     jpa:
          database-platform: org.hibernate.dialect.PostgreSQLDialect
          hibernate:
            ddl-auto: validate
          show-sql: false
       
     mail:
          username: ${MAIL_USERNAME}
          password: ${MAIL_PASSWORD}

api:
    security:
        token:
            secret: ${JWT_SECRET}


aws:
  region: ${AWS_REGION}
  access-key: ${AWS_ACCESS_KEY_ID}
  secret-key: ${AWS_SECRET_ACCESS_KEY}
  s3:
    bucket:
      profile-photos: profile-photos
      creci-documents: creci-documents
      property-documents: property-documents
      property-images: property-images
```

---

## Observações

* **JWT**: Configurado por profile
* **Email**: Fake para teste, SMTP real para dev/prod
* **S3 Buckets**: Dev usa MinIO, Prod usa AWS
* **H2 Console**: Disponível apenas no profile `test`

---

## Comandos úteis

* Rodar dev: `docker-compose up -d && ./mvnw spring-boot:run`
* Rodar testes: `./mvnw test`
* Limpar containers dev: `docker-compose down`

---

## Variáveis de ambiente

O projeto utiliza `.env` com as variáveis para:

* PostgreSQL (`DATASOURCE_URL`, `DATASOURCE_USERNAME`, `DATASOURCE_PASSWORD`)
* JWT (`JWT_SECRET`)
* Email (`MAIL_USERNAME`, `MAIL_PASSWORD`)
* AWS (`AWS_REGION`, `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_S3_ENDPOINT`)

---

> Com este setup, você consegue alternar rapidamente entre **dev**, **test** e **prod**, mantendo configurações isoladas e seguras.