# Serwis Uwierzytelniania 

## Opis

## Metody kontroli dostępu

### ABAC

Kontrola dostępu do zasobu na podstawie atrybutów (ang. Attribute Based Access Control). Przykładowym wykorzystaniem 
tej metody jest: dostęp do zasobu wyłącznie dla osób zamieszkałych pod adresem, .

### RBAC

Kontrola dostępu do zasobu na podstawie roli (ang. Role Based Access Control). Przykładowym wykorzystaniem podanej 
metody jest dostęp do zasobu wyłącznie dla zarejestrowanych użytkowników - przydzielona rola USER, dostęp do 
zarzadzania użytkownikami - przydzielona rola ADMIN.

## Punkt uwierzytelniania
Request: 
```
POST /api/auth
Request-Body {}
```

Response:

```
Response-Code {code}
Body {
  "jwt-token": "{token}"
}
```
## Punkty dostępu
```JSON

```

## Technologie
- Java 24
- Spring Framework 6
- Spring Boot 3
- Spring Security 6
- Spring Data JPA 3
- H2 *- temporary*

## Parametry konfiguracji
Zdefiniowane w `src/main/resources/application.properties`

## Uruchamianie serwisu
```bash
    java -jar wdc-{version}-SNAPSHOT.jar
```

## Autorzy
