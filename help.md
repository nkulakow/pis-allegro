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
docker run -d --name pis-mongo -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=mongo -e MONGO_INITDB_ROOT_PASSWORD=mypassword mongo:latest 
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

## NOWA WERSJA MONGO
```bash
docker pull mongo:5
docker network create mongoCluster
docker run -d -p 27017:27017 --name pis-mongo --network mongoCluster mongo:5 mongod --replSet myReplicaSet --bind_ip localhost,pis-mongo
docker exec -it pis-mongo mongosh
```

w nowej konsoli:
```bash
rs.initiate({ _id: "myReplicaSet", members: [ {_id: 0, host: "localhost:27017"}]})
```
CTRL+D.   
testujemy:
```bash
docker exec -it pis-mongo mongosh --eval "rs.status()"
```
powinny być tam pis-monog-first jako PRIMARY.    
```bash
docker exec -it pis-mongo mongosh
use pis
db.createUser({ user: "mongo",  pwd: "mypassword", roles: [  { role: "readWrite", db: "pis" } ]})
db.testing.insertOne({name: "Ada", age: 205})
show dbs
```
powinna się pojawić baza danych o nazwie pis.  

