# imobmatch-api

## Perfis de ambiente

O projeto utiliza **Spring Boot Profiles** para separar ambientes:

| Profile | Banco de dados          | Observações                                       |
| ------- | ----------------------- | ------------------------------------------------- |
| `dev`   | PostgreSQL (via Docker) | Para desenvolvimento local com dados persistentes |
| `test`  | H2 (em memória)         | Para testes rápidos automatizados                 |
| `prod`  | PostgreSQL              | Produção, configurar host e credenciais depois    |

O profile ativo é definido em `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: ImobMatch API
  profiles:
    active: dev  # altere para dev / test / prod conforme necessário
```

---

## Mode DEV

1. **Iniciar o container do banco (PostgreSQL) via Docker**:

```bash
docker-compose up -d
```

2. **Configurar o profile no Spring Boot**:
   No `application.yml` principal, altere:

```yaml
spring:
  profiles:
    active: dev
```

3. **Configuração do datasource para dev (`application-dev.yaml`)**:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/imobmatch
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: postgres
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
    show-sql: true
```

> Agora o Spring Boot vai se conectar automaticamente ao container Docker quando você iniciar a aplicação.

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
```

> Perfeito para testes automatizados rápidos.

---

## Mode PROD

1. **Configurar profile**:

```yaml
spring:
  profiles:
    active: prod
```

2. **Configuração do datasource (`application-prod.yaml`)**:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://<prod-host>:5432/<prod-db>  # preencher depois
    driver-class-name: org.postgresql.Driver
    username: <prod-username>
    password: <prod-password>
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate   # não altera esquema em produção
    show-sql: false
```

---