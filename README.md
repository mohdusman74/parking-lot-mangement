# Parking Lot Management System

Spring Boot REST API starter using Java 17, Spring Data JPA, MySQL, and optional H2.

## Run
Create the database in MySQL:
```sql
CREATE DATABASE parking_db;
```
Update credentials in `src/main/resources/application.properties`, then run:
```bash
mvn spring-boot:run
```
Or run with in-memory H2 for local practice:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

## Endpoints

Create a spot:
`POST /api/spots`
```json
{"type":"CAR"}
```

List spots: `GET /api/spots`

Count available spots: `GET /api/spots/available-count?type=CAR`

Vehicle entry:
`POST /api/parking/entry`
```json
{"licensePlate":"HR26AB1234","vehicleType":"CAR"}
```

Get ticket: `GET /api/parking/tickets/1`

Vehicle exit: `POST /api/parking/exit/1`

Fee in this example: CAR = Rs. 50/hour; BIKE = Rs. 20/hour. Partial hours are rounded up and the minimum charge is one hour.

## Packages
`controller`, `dto`, `entity`, `exception`, `repository`, `service`.

## Scope and limitations
This is an interview-practice starter, not production-ready. It covers spot creation/listing, compatible spot allocation, ticket creation, fee calculation, ticket closure, and spot release. It does not yet implement payment-gateway integration, authentication, multi-floor modelling, duplicate active-ticket prevention, or a full concurrency test suite.
