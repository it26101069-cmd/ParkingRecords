ParkWise UI v2
==============
Copy index.html, vehicles.html, users.html, slots.html, records.html, payments.html, staff.html to:
  src/main/resources/static/
Copy css/app.css to:
  src/main/resources/static/css/app.css
Copy js/crud.js to:
  src/main/resources/static/js/crud.js

Start Spring Boot and open http://localhost:8080/index.html
All existing REST API routes are preserved.

NOTES:
- Zone dropdown reads GET /api/zones; vehicle dropdown reads GET /api/vehicles.
- Parking Records slot dropdown displays AVAILABLE slots only on create; existing selected slot may not appear on edit if no longer available. Adjust this depending on your record-edit rules.
- The frontend does not perform actual allocation/transaction. Backend must validate availability and update records/slots atomically.
- Payment ID is optional on create; frontend omits empty optional fields.
- Staff role/shift values should be adjusted if backend expects other enum strings.
- No backend files or ZIP uploaded earlier were modified.
