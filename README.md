# Deal Management API

A robust RESTful API built with **Spring Boot** for managing currency exchange deals.

- Create, retrieve, and list deals
- Prevents duplicate entries
- Validates data before persistence
- Uses PostgreSQL for persistence
- Dockerized with a working `docker-compose.yml`
- Logs and handles exceptions globally

## API Endpoints

### 1. Create a Deal
`POST /api/v1/deals`

Create a single deal.

#### Request Example
```json
{
  "id": "DEAL-001",
  "fromCurrencyIsoCode": "USD",
  "toCurrencyIsoCode": "EUR",
  "dealTimestamp": "2025-08-01T12:00:00",
  "dealAmount": 1500.75
}
  ```
#### Responses

    201 Created – Deal persisted

    400 Bad Request – Validation or duplicate error

### 2. Get Deal by ID

`GET /api/v1/deals/{id}`

Example

`GET /api/v1/deals/DEAL-001`

#### Responses

    200 OK – Deal found

    404 Not Found – Deal does not exist

#### 3. Get All Deals

`GET /api/v1/deals`

Returns the list of all persisted deals.

#### Validation Rules

Field	Rule
id	Required, non-empty
fromCurrencyIsoCode	Required, 3-letter
toCurrencyIsoCode	Required, 3-letter
dealAmount	Required, positive decimal

#### Testing Includes:

    Service layer tests

    Exception handling tests

    Duplicate prevention logic

#### Dockerized Setup
1. Clone the project

git clone https://github.com/soufianenajim99/ProgressSoft-FX-Deals
cd ProgressSoft-FX-Deals

2. Environment Configuration

Create the .env file.

 ```bash
POSTGRES_DB=fx_deals_db
POSTGRES_USER=youruser
POSTGRES_PASSWORD=yourpassword
   ```

3. Run the stack

 ```bash

   make run


   # OR


   docker compose up -d --build

   ```

4. Access the API

`http://localhost:8080/api/v1/deals`

 Sample Requests (Tested with .http)

Create a valid deal:

`POST http://localhost:8080/api/v1/deals`
`Content-Type: application/json`
```json

{
  "id": "DEAL-001",
  "fromCurrencyIsoCode": "USD",
  "toCurrencyIsoCode": "EUR",
  "dealTimestamp": "2025-08-01T12:00:00",
  "dealAmount": 1500.75
}
```
### Duplicate (should fail):

`POST http://localhost:8080/api/v1/deals`
`Content-Type: application/json`

```
{
  "id": "DEAL-001",
  "fromCurrencyIsoCode": "USD",
  "toCurrencyIsoCode": "GBP",
  "dealTimestamp": "2025-08-01T13:00:00",
  "dealAmount": 2000.00
}
```
### nvalid input:

`POST http://localhost:8080/api/v1/deals`
`Content-Type: application/json`
```json
{
  "id": "",
  "fromCurrencyIsoCode": "US",
  "toCurrencyIsoCode": "EURO",
  "dealTimestamp": null,
  "dealAmount": -100
}
```
### Fetch a deal:

`GET http://localhost:8080/api/v1/deals/DEAL-001`

### Fetch all:

`GET http://localhost:8080/api/v1/deals`


 Notes

    Prevents duplicate deals using ID check

    No rollback: all valid rows are persisted, invalid ones rejected independently

    Proper @RestControllerAdvice used for global error management

    DTOs ensure clean API contract

 Technologies

    Java 21

    Spring Boot 3

    Maven

    PostgreSQL

    Docker

    MapStruct

    JUnit 5

    Lombok

    SLF4J

 Author

Developed by NAJIM Soufiane