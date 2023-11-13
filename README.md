# ExchangeCurrency

# Technologies

# Installation using docker
Java
Quarkus
QUTE
Hibernate
Postgres
OIDC
Keycloak


- postgres
docker run -d -p 5432:5432 --network=NBP-APP -e POSTGRES_PASSWORD="admin" postgres

- keycloak
docker run --name keycloak -d -p 8180:8080 --network=NBP-APP
-e KEYCLOAK_ADMIN=admin
-e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev

- optional pgadmin

docker run --name pgadmin -d -p 5050:80 --network=NBP-APP
-e PGADMIN_DEFAULT_EMAIL="admin@admin.com"
-e PGADMIN_DEFAULT_PASSWORD="admin"  dpage/pgadmin4

- quarkus app
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/code-with-quarkus-jvm .
docker run --name NBP-APP -i -p 8080:8080 --network=NBP-APP quarkus/code-with-quarkus-jvm
