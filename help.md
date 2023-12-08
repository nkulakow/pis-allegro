## POSTGRES
```bash 
docker pull postgres
docker run --name pis-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mypassword -d postgres
```
będzie password=jak wyżej, username=postgres
```bash
docker exec -it pis-postgres bash
psql -h localhost -p 5432 -U postgres -W
CREATE DATABASE pis;
\l
```
powinno się pojawić pis

potem wszystko można za pomocą np: dbeaver (https://dbeaver.io/)

## MONGO
```bash
docker pull mongo
docker network create mongo-net
docker run -d --name pis-mongo --network mongo-net -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongo -e MONGO_INITDB_ROOT_PASSWORD=mypassword mongo:latest --replSet rs0
docker run -d --name pis-mongo-second --network mongo-net -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongo -e MONGO_INITDB_ROOT_PASSWORD=mypassword mongo:latest --replSet rs0
docker run -d --name pis-mongo-third --network mongo-net -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongo -e MONGO_INITDB_ROOT_PASSWORD=mypassword mongo:latest --replSet rs0
docker exec -it pis-mongo mongosh
```

test czy działa:
```bash
show dbs
```
ma się pokazac:
```bash
admin    8.00 KiB
config  12.00 KiB
local    8.00 KiB
```
koniec testu
```bash
use admin
db.auth("mongo", "mypassword")
use pis
db.testing.insertOne({name: "Ada", age: 205})
show dbs
```
teraz powinno się tam pojawić:
```bash
pis       8.00 KiB
```
potem wszystko można za pomocą: MongoDB Compass (https://www.mongodb.com/docs/compass/master/install/)
