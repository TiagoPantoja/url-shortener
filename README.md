# URL Shortener
API encurtadora de URLs desenvolvida a fim de aplicar conceitos reais de arquitetura de sistemas distribuídos.

---

## Decisões de Arquitetura

Para este projeto, foquei em quatro pilares fundamentais:

1.  **Algoritmo Base62:** Em vez de usar IDs ou UUIDs, transformei o ID sequencial em uma string alfanumérica. Com apenas 7 caracteres, são geradas bilhões de combinações.
2.  **Estratégia de Cache** Para não sobrecarregar o banco de dados, utilizei o Redis como uma camada de leitura.
3.  **Arquitetura Camada:** Código separado entre Controllers, Services e Repositories. Isso facilita a manutenção e permite que você troque o banco de dados ou o cache sem quebrar a regra de negócio.
4.  **Docker:** Tudo sobe via containers. Usei *healthchecks* para garantir que a API só tente conectar no banco quando ele estiver 100% pronto para receber conexões.

---

## Stack Tecnológica

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3
* **Banco de Dados:** PostgreSQL
* **Cache:** Redis
* **Infra:** Docker & Docker Compose
* **Produtividade:** Lombok & Spring Data JPA

---

## Como rodar o projeto?

Você precisa ter o **Docker** instalado na sua máquina.

1.  Clone o projeto.
2.  No terminal, dentro da pasta do projeto, execute:
    ```bash
    docker-compose up -d --build
    ```
3.  Aguarde os containers subirem. A API estará disponível em: `http://localhost:8080`

---

## Testando os Endpoints

### 1. Criar um link curto
Mande um `POST` para `/api/shorten` com a URL original.

```json
{
  "url": "[https://www.google.com/search?q=como+virar+um+engenheiro+de+software+senior](https://www.google.com/search?q=como+virar+um+engenheiro+de+software+senior)"
}
```

### 2. Redirecionar
Basta colocar o código gerado direto na URL do navegador:
```
http://localhost:8080/{codigo_gerado}
```
