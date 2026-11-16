# ParkWise (merged project)

Spring Boot 4.1.2 · Java 21 · JDBC · MySQL. Layers: Controller → Service → Repository → MySQL.

## Run
1. Start MySQL. Edit username/password in `src/main/resources/application.properties` (default: root / 12345).
2. `./mvnw spring-boot:run`  (Windows: `mvnw.cmd spring-boot:run`) or run `ParkingApplication` from IntelliJ.
3. Open http://localhost:8080

The `parking` database and **all 7 tables are created automatically** from `src/main/resources/schema.sql` (a copy is in `database/`).

## Modules (each has full CRUD + its own UI page)
| Module | UI page | REST API | Primary key |
|---|---|---|---|
| Vehicles | /vehicles.html | /api/vehicles | vehicleNumber |
| Users | /users.html | /api/users | userId (auto) |
| Zones | /slots.html | /api/zones | zoneCode |
| Slots | /slots.html | /api/slots | slotNumber (FK → zone) |
| Parking Records | /records.html | /api/records | recordId (auto REC-1001…) |
| Part Payments | /payments.html | /api/part-payments | paymentId (auto PAY-xxxx) |
| Staff | /staff.html | /api/staff | staffId (auto) |

Modules do not depend on each other (no cross foreign keys), so each can be demonstrated on its own.
Only Slots → Zones has a foreign key (both belong to the same module).

## Changes made while merging the five zips
- One `ParkingApplication`, one `pom.xml`, one `application.properties`, one `CrudRepository`, one `GlobalExceptionHandler`.
- Slot module: the `dto` classes (`CreateParkingSlotRequest`, `CreateParkingZoneRequest`) were missing from the zip and were added.
- Staff module: rewritten into the shared pattern (`CrudRepository<Staff, Integer>`, DataSource + Connection, DTO). It now uses database `parking` and port 8080 like the rest.
- Parking Record module: was an in-memory list with no controller/DB. Now a full Controller/Service/Repository/table.
- `schema.sql` creates every table (the `vehicles` table SQL was in nobody's zip).
