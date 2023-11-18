# ExchangeCurrency

Main features:
- user login
- user registration
- download data from NBP api and upadate a database by user who have admin role
- calculating exchange rates between currencies by other roles

# Technologies

- Java
- Quarkus
- Qute
- Hibernate
- Postgres
- OpenID Connect & JWT
- Keycloak
- HTML/CSS/JavaScript/Bootstrap


# Installation using docker

- create network

 docker network create NBP-APP

- postgres

docker run -d -p 5432:5432 --network=NBP-APP -e POSTGRES_PASSWORD="admin" postgres

- keycloak

docker run --name keycloak -d -p 8180:8080 --network=NBP-APP
-e KEYCLOAK_ADMIN=admin
-e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev

Go to http://localhost:8180/ and click on administration Console.
Create client

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/bcee2a64-4160-4743-9fc5-b9c148a792de)

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/c822592c-94d7-43fc-8e94-7004447dcaa8)

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/1a503d5a-e759-4689-9b4c-a49ca86c8f79)

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/01c2bf84-d09e-48db-864e-dfcdd27a13a9)

Get client secret

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/46e51d55-3183-44ce-b046-6aa221cb310c)

and print here in application

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/7de327a2-d849-4c78-8961-8fef91327ae6)


- optional pgadmin

docker run --name pgadmin -d -p 5050:80 --network=NBP-APP
-e PGADMIN_DEFAULT_EMAIL="admin@admin.com"
-e PGADMIN_DEFAULT_PASSWORD="admin"  dpage/pgadmin4

- quarkus app

./mvnw package

docker build -f src/main/docker/Dockerfile.jvm -t quarkus/code-with-quarkus-jvm .

docker run --name NBP-APP -i -p 8080:8080 --network=NBP-APP quarkus/code-with-quarkus-jvm

# Application
Path: localhost:8080/login

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/1fe0d9cc-2ad8-411c-bdc9-7aed12787569)

Path: localhost:8080/registration

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/e9333810-88f8-4b8e-856e-8bd9ca3bc9c2)

Path: localhost:8080/exchangeCurrency

![image](https://github.com/Matiu-dev/ExchangeCurrency/assets/73337576/88dd6b97-8ff2-4dcb-a357-9a9caf9ddbcc)

