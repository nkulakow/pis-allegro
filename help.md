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

## MONGO - wersja aktualna, jeśli chcemy transakcje
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

## MONGO - po pierwszym mvn install
Trzeba zrobić indeks na product:
```bash
docker exec -it pis-mongo mongosh
use pis
db.product.createIndex({ name: "text", description: "text" })
```
!!! to działa tylko dla angielskiego języka. apkę zatem dla ułatwienia zróbmy po angielsku.