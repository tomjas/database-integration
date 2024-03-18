# About

Application for transferring data from SQL (MySQL) database to NoSQL (MongoDB).
For presentation purpose data (Star Wars characters) is read from `resources/input_data/db.json`
and imported to MySQL on application startup.

Transfer to MongoDB is triggered by executing endpoint (see below).

### Prerequisites

Java 17, installed and configured MySQL/MariaDB and MongoDB according to application.properties settings.

### Exposed endpoints

`GET /api/{api_version}/mysql/characters` - presents star wars characters in MySQL database

`GET /api/{api_version}/mysql/homeworlds` - presents relational table of Star Wars characters home worlds

`GET /api/{api_version}/mongo/characters` - presents Star Wars characters in MongoDB

`GET /api/{api_version}/transfer` - triggers transfer data from MySQL to MongoDB