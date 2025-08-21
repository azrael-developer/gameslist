# GameList API (Java + Spring Boot)

API REST para catálogo de jogos, construída durante um intensivão de Java Spring. O projeto demonstra uma arquitetura limpa em camadas, JPA/Hibernate, perfis de execução (H2 e Postgres), seed de banco e consultas otimizadas com projections.

> Stack: Java 21 (compatível 17+), Spring Boot 3.x, Spring Data JPA, Maven, PostgreSQL (dev/prod), H2 (test), Docker (opcional), Postman.


## ✨ Funcionalidades
- Listar jogos (GET /games)

- Buscar jogo por ID (GET /games/{id})

- Listar listas de jogos (coleções) (GET /lists)

- Listar jogos de uma lista (GET /lists/{listId}/games)

- Seed de dados automático para facilitar testes

- CORS configurável por variável de ambiente

- Arquitetura em camadas (Controller → Service → Repository) usando DTOs e Projections

- Perfis de execução: test (H2), dev/prod (Postgres)


# 🗂️ Estrutura do projeto
```
src/
└─ main/
    ├─ java/com/seuusuario/gameslist/
    │   ├─ controllers/     # REST controllers
    │   ├─ dto/             # Data Transfer Objects
    │   ├─ entities/        # JPA entities (Game, GameList, Belonging)
    │   ├─ repositories/    # Spring Data JPA
    │   └─ services/        # Regras de negócio
    └─ resources/
         ├─ application.properties
         ├─ application-test.properties     # H2
         ├─ application-dev.properties      # Postgres (local/nuvem)
         ├─ schema.sql / import.sql / data.sql  # criação + seed
         └─ ... (demais configs)
```

## Modelo de dados (tabelas):
> - tb_game (jogo)
> - tb_game_list (lista)
> - tb_belonging (associação N:N entre lista e jogo, com posição)


# 🚀 Como rodar localmente
## 1) Pré-requisitos
- Java 17+ (projeto testado com Java 21)
- Maven 3.9+
- (Opcional) Docker + Docker Compose
- Postman ou curl


## 2) Variáveis de ambiente
```
APP_PROFILE=dev             # test, dev ou prod
DB_URL=jdbc:postgresql://localhost:5432/gameslist
DB_USERNAME=postgres
DB_PASSWORD=postgres
CORS_ORIGINS=http://localhost:5173,http://localhost:3000
```
> Em test, o H2 já sobe em memória e as credenciais do Postgres não são necessárias.


## 3) Rodar com H2 (perfil test) – mais rápido
```
# Linux/Mac
./mvnw spring-boot:run -Dspring-boot.run.profiles=test

# Windows (PowerShell)
mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

## 4) Rodar com Postgres (perfil dev)

Opção A — Postgres já instalado/rodando:

- Crie um banco gameslist
- Ajuste application-dev.properties ou use as variáveis acima
- Rode

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

```

Opção B — Docker Compose (sugestão):

- Crie um docker-compose.yml na raiz do projeto:
```
version: "3.8"
services:
  db:
    image: postgres:16
    environment:
      POSTGRES_DB: gameslist
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
    volumes:
      - dbdata:/var/lib/postgresql/data
volumes:
  dbdata:

```

- Suba o banco:
```
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

```

## 5) Build do JAR

```
./mvnw clean package -DskipTests
java -jar target/*-SNAPSHOT.jar

```

# 🧪 Testando a API

- Endpoints

| Método | Rota                    | Descrição                      |
|--------|-------------------------|--------------------------------|
|    GET | `/games`                | Lista todos os jogos           |
|    GET | `/games/{id}`           | Retorna os detalhes de um jogo |
|    GET | `/lists`                | Lista todas as listas          |
|    GET | `/lists/{listId}/games` | Jogos pertencentes à lista     |

- Exemplos (curl)
```
curl http://localhost:8080/games
curl http://localhost:8080/games/1
curl http://localhost:8080/lists
curl http://localhost:8080/lists/1/games

```

- Respostas:
`GET /games`
```
[
  {
    "id": 1,
    "title": "Mass Effect Trilogy",
    "year": 2012,
    "imgUrl": "https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/1.png",
    "shortDescription": "Lorem ipsum dolor sit amet consectetur adipisicing elit..."
  },
  {
    "id": 2,
    "title": "Red Dead Redemption 2",
    "year": 2018,
    "imgUrl": "https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/2.png",
    "shortDescription": "Lorem ipsum dolor sit amet consectetur adipisicing elit..."
  }
]
```

- Respostas:
`GET /games/1`
```
{
  "id": 1,
  "title": "Mass Effect Trilogy",
  "year": 2012,
  "genre": "Role-playing (RPG), Shooter",
  "imgUrl": "https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/1.png",
  "shortDescription": "Lorem ipsum dolor sit amet consectetur adipisicing elit...",
  "longDescription": "Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus..."
}

```

# 🔧 Detalhes técnicos

- **Camadas e DTOs:** Controllers finos, Services contendo regras, Repositories com Spring Data JPA. Entidades expostas via DTOs.

- **ORM:** Mapeamento JPA/Hibernate (inclui relacionamento N:N entre tb_game_list e tb_game via tb_belonging, com chave composta e posição do jogo na lista).

- **Seed:** scripts SQL (ex.: schema.sql e data.sql/import.sql) criam as tabelas tb_game, tb_game_list, tb_belonging e populam registros iniciais.

- **Consultas otimizadas:** uso de Projection (interfaces) e consultas específicas para montar listagens por lista sem carregar colunas desnecessárias.

- **Perfis:** test (H2 em memória) para subir instantaneamente; dev/prod com Postgres.

- **CORS:** domínios permitidos via CORS_ORIGINS (se for consumir por um frontend React/Vite, adicione http://localhost:5173).

# 🖼️ Screenshots - API (Postman)

- `GET /games`
<img width="932" height="781" alt="image" src="https://github.com/user-attachments/assets/145f2fe2-9b55-4d84-bd0b-dfe13673f7a6" />



- `GET /games/{id}` 
<img width="906" height="382" alt="image" src="https://github.com/user-attachments/assets/d024c0b4-3fad-4649-9bb8-3f8d2a262766" />



- `GET /lists`
<img width="935" height="401" alt="image" src="https://github.com/user-attachments/assets/da6b565e-637b-42e7-a8e8-56b7b16de008" />



- `GET /lists/{listId}/games`
<img width="933" height="708" alt="image" src="https://github.com/user-attachments/assets/7d1354e6-cb21-4cba-8d68-acd9a1c7aed4" />


# 🖼️ Screenshots - Postgres (pgAdmin)

- `tb_game`
<img width="1060" height="231" alt="image" src="https://github.com/user-attachments/assets/5c8c3a9f-4d8a-4b1e-a626-eff0738d38f5" />



- `tb_belonging` 
<img width="232" height="231" alt="image" src="https://github.com/user-attachments/assets/ee219906-01db-4066-ab66-f0b441a1b835" />



- `tb_game_list`
<img width="226" height="91" alt="image" src="https://github.com/user-attachments/assets/e5749fc8-3363-4a53-a71a-c25dff7377ae" />


# 🗺️ Roadmap (ideias futuras)

- Paginação e filtros (por gênero, ano, score)
- Endpoint para reordenar jogos numa lista
- Autenticação/Autorização (JWT)
- Observabilidade (logs estruturados, métricas)
- CI/CD (GitHub Actions) e deploy gerenciado (Railway/Render)
- Documentação Swagger/OpenAPI


# 🤝 Como contribuir

- Faça um fork do projeto
- Crie uma branch: git checkout -b feature/minha-feature
- Commit: git commit -m "feat: minha feature"
- Push: git push origin feature/minha-feature
- Abra um Pull Request


# 👤 Autor
`@azrael-developer`
- **LinkedIn:** https://www.linkedin.com/in/christian-mendes-b0073118b/



