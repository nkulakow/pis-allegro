# pis-allegro
System działający na podobnej zasadzie jak Allegro.

## Wybrane technologie:
- **język**: Java + Spring
- **automatyzacja budowy projektu**: Maven
- **repozytorium kodu**: GitHub
- **repozytorium mavenowe**: Nexus
- **serwer CI**: Jenkins
- **IDE**: Intellij IDEA
- **issues**: Jira
- **testy**: JUnit
- **narzędzie do mierzenia pokrycia kodu testami**: JaCoCo

## Architektura
![diagram uml](images/architektura.png)

## Przypadki użycia


## Wymagania niefunkcjonalne


## Uzasadnienie wyboru baz danych
### MongoDB
Model danych zorientowany na dokumenty: MongoDB przechowuje dane w postaci dokumentów podobnych do JSON, co pozwala na większą elastyczność i szybszy czas rozwoju oraz przechowywanie różnych atrybutów.   
Skalowalność: MongoDB wykorzystuje architekturę rozproszoną i obsługuje skalowanie poziome, co oznacza, że może obsługiwać rosnące ilości danych oraz obciążenia związane z odczytem i zapisem bez konieczności kosztownej modernizacji sprzętu.   
Wysoka dostępność: MongoDB zawiera wbudowane funkcje automatycznego przełączania awaryjnego i zestawów replik, zapewniając, że baza danych pozostaje dostępna i dostępna podczas awarii sprzętu lub innych zakłóceń.   
Indeksowanie: MongoDB obsługuje indeksowanie, aby poprawić wydajność zapytań i wyszukiwań, dzięki czemu szybciej i łatwiej zlokalizować określone dokumenty w ramach kolekcji.    
Zapewnia pewne alternatywy dla implementacji operacji atomowych: 
  - operacje zapisu są atomowe na poziomie dokumentu nawet jeśli zapis modyfikuje wiele zagnieżdżonych dokumentów wewnątrz jednego dokumentu
  - polecenia modyfikujące pojedyncze dokumenty są atomowe - i to nam wystarczy
...
### PostgreSQL
...

### Zapewnienie spójności między bazami danych
Zapewnienie spójności między bazami danych będzie kluczowe tak naprawdę jedynie w sytuacji gdy sprzedawca dodaje lub modyfikuje przedmiot. Wpływa to na zmianę w jednej tabeli w bazie PostgreSQL i jednego dokumentu w bazie MongoDB. Z tego powodu zdecydowaliśmy na użycie transakcji, zakładając, że operacja powinna się udać albo całkowicie, albo nie - na co pozwoli adnotacja `@Transacional` nad odpowiednimi metodami (jest odpowiednia dla MongoDB jeśli edytujemy 1 dokument).   
Źródła: 
- https://www.baeldung.com/spring-data-mongodb-transactions
- https://stackoverflow.com/questions/72677210/spring-boot-postgresql-and-mongodb-data-sync
- https://medium.com/javarevisited/database-isolation-level-with-postgresql-and-spring-boot-c6c2f8fe3b46
Innym problemem może być wyświetlanie produktów jeśli baza mongodb padnie (jeśli coś stanie się z postgresql aplikacja niestety się nie przyda). Wtedy należałoby wyświetlić odpowiedni komunikat o błędzie oraz produkty bez ich zdjęć, ocen czy opisów.
