# 📦 SSC-Brewery

**Spring Security Core Brewery** — a sample Spring Boot application demonstrating the use of *Spring Security*, MVC, authentication, and authorization mechanisms.

🔗 Repository: [https://github.com/blazejknie/ssc-brewery](https://github.com/blazejknie/ssc-brewery)

---

## 🧠 Table of Contents

* [Project Overview](#-project-overview)
* [Features](#-features)
* [Technology Stack](#-technology-stack)
* [Architecture](#-architecture)
* [Requirements](#-requirements)
* [Running Locally](#-running-locally)
* [Project Structure](#-project-structure)
* [Spring Security Overview](#-spring-security-overview)
* [REST API Endpoints](#-rest-api-endpoints)
* [Testing](#-testing)
* [License](#-license)

---

## 🧾 Project Overview

**SSC-Brewery** is an educational Spring Boot project that demonstrates how to:

* secure web applications using **Spring Security**,
* configure authentication and authorization,
* manage user roles and permissions,
* protect MVC views and REST API endpoints.

The project is based on training materials and serves as a **sandbox for learning Spring Security in practice**.

---

## 🚀 Features

* 🔐 User authentication (form-based login)
* 👤 User roles (USER / ADMIN)
* 🛡️ Authorization of protected resources
* 🌐 MVC + REST API
* 🧪 Security configuration ready for testing and experimentation

---

## 🛠️ Technology Stack

| Technology       | Description                    |
| ---------------- | ------------------------------ |
| Java             | 17+                            |
| Spring Boot      | Application framework          |
| Spring Security  | Authentication & authorization |
| Maven            | Build tool                     |
| Thymeleaf / HTML | View layer                     |

---

## 🏗️ Architecture

The project follows a classic **Spring Boot monolithic architecture**:

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

## 📋 Requirements

* Java 17 or newer
* Maven 3.x
* IDE (recommended): IntelliJ IDEA

---

## ▶️ Running Locally

```bash
git clone https://github.com/blazejknie/ssc-brewery.git
cd ssc-brewery
mvn clean install
mvn spring-boot:run
```

The application will be available at:

```
http://localhost:8080
```

---

## 🔒 Spring Security Overview

The project demonstrates the use of:

* `SecurityFilterChain`
* `UserDetailsService`
* `PasswordEncoder`
* role-based endpoint protection

Example configuration snippet:

```java
http
  .authorizeHttpRequests(auth -> auth
      .requestMatchers("/", "/login").permitAll()
      .anyRequest().authenticated()
  )
  .formLogin();
```

---

## 📡 REST API Endpoints (examples)

| Endpoint      | Method     | Access |
| ------------- | ---------- | ------ |
| `/`           | GET        | public |
| `/login`      | GET / POST | public |
| `/brewery/**` | GET / POST | USER   |
| `/api/**`     | GET / POST | ADMIN  |

---

## 🧪 Testing

Run tests using:

```bash
mvn test
```

The project includes unit and integration tests focused on application logic and security configuration.

---

## 📜 License

This project is licensed under the **GPL-3.0 License**.

---

> 📌 **Repository goal:** learning and experimenting with Spring Security in a realistic business-style application.
