# 📦 SSC‑Brewery

**Spring Security Core Brewery** — przykładowa aplikacja Spring Boot demonstrująca zastosowania *Spring Security*, MVC oraz uwierzytelniania i autoryzacji użytkowników.

🔗 Repozytorium: [https://github.com/blazejknie/ssc-brewery](https://github.com/blazejknie/ssc-brewery)

---

## 🧠 Spis treści

* [Opis projektu](#-opis-projektu)
* [Funkcje](#-funkcje)
* [Technologie](#-technologie)
* [Architektura](#-architektura)
* [Wymagania](#-wymagania)
* [Uruchomienie lokalne](#-uruchomienie-lokalne)
* [Struktura katalogów](#-struktura-katalogów)
* [Spring Security – overview](#-spring-security--overview)
* [REST API endpoints](#-rest-api-endpoints)
* [Konfiguracja Spring Security](#-konfiguracja-spring-security)
* [Testy](#-testy)
* [Licencja](#-licencja)

---

## 🧾 Opis projektu

Aplikacja **SSC‑Brewery** to edukacyjny projekt Spring Boot pokazujący jak:

* zabezpieczać aplikacje webowe przy użyciu **Spring Security**,
* konfigurować uwierzytelnianie i autoryzację,
* stosować role i uprawnienia użytkowników,
* chronić endpointy MVC oraz REST API.

Projekt jest forkiem materiałów szkoleniowych i służy jako **sandbox do nauki Spring Security**.

---

## 🚀 Funkcje

* 🔐 Logowanie użytkowników (form login)
* 👤 Role użytkowników (USER / ADMIN)
* 🛡️ Autoryzacja dostępu do zasobów
* 🌐 MVC + REST API
* 🧪 Konfiguracja bezpieczeństwa gotowa do testów

---

## 🛠️ Technologie

| Technologia      | Opis                  |
| ---------------- | --------------------- |
| Java             | 17+                   |
| Spring Boot      | Framework aplikacyjny |
| Spring Security  | Bezpieczeństwo        |
| Maven            | Build tool            |
| Thymeleaf / HTML | Warstwa widoku        |

---

## 🏗️ Architektura

Projekt oparty o klasyczną architekturę **Spring Boot (monolit)**:

```
src/
├── main/
│   ├── java/
│   │   └── guru/sfg/brewery/
│   │       ├── controllers/
│   │       ├── config/
│   │       ├── services/
│   │       └── model/
│   └── resources/
│       ├── templates/
│       ├── static/
│       └── application.properties
└── test/
```

---

## 📋 Wymagania

* Java 17 lub nowsza
* Maven 3.x
* IDE (zalecane): IntelliJ IDEA

---

## ▶️ Uruchomienie lokalne

```bash
git clone https://github.com/blazejknie/ssc-brewery.git
cd ssc-brewery
mvn clean install
mvn spring-boot:run
```

Aplikacja będzie dostępna pod adresem:

```
http://localhost:8080
```

---

## 🔒 Spring Security – overview

Projekt demonstruje:

* `SecurityFilterChain`
* `UserDetailsService`
* `PasswordEncoder`
* zabezpieczenie endpointów przez role

Przykładowa konfiguracja:

```java
http
  .authorizeHttpRequests(auth -> auth
      .requestMatchers("/", "/login").permitAll()
      .anyRequest().authenticated()
  )
  .formLogin();
```

---

## 📡 REST API Endpoints (przykładowe)

| Endpoint      | Metoda   | Dostęp |
| ------------- | -------- | ------ |
| `/`           | GET      | public |
| `/login`      | GET/POST | public |
| `/brewery/**` | GET/POST | USER   |
| `/api/**`     | GET/POST | ADMIN  |

---

## 🧪 Testy

Uruchomienie testów:

```bash
mvn test
```

Projekt zawiera testy jednostkowe i integracyjne dla konfiguracji bezpieczeństwa.

---

## 📜 Licencja

Projekt objęty jest licencją **GPL‑3.0**.

---

> 📌 **Cel repozytorium:** nauka i eksperymentowanie z Spring Security w praktycznym kontekście aplikacji biznesowej.
