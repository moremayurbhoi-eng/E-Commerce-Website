# 🛒 E-Commerce Website - Spring Boot Backend

A full-stack E-Commerce application backend developed using Java and Spring Boot.  
The backend provides REST APIs for product management, categories, cart, orders, authentication, and other e-commerce operations.

## 🚀 Features

- User registration and login
- Spring Security authentication
- JWT-based authentication
- Product management
- Add, update and delete products
- Product categories
- Dynamic product specifications
- Product image upload
- Shopping cart management
- Cart quantity update
- Remove products from cart
- Order management
- Order details
- MySQL database integration
- RESTful APIs
- Admin product management

## 🛠️ Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- Hibernate
- MySQL
- Maven
- Jackson
- Lombok
- REST API
- Multipart File Upload

- ## 🏗️ Project Architecture

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Entity
    ↓
MySQL Database

## 📁 Project Structure

```text
Ecommerce_app/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── config/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   ├── test/
│   │
├── pom.xml
├── mvnw
├── mvnw.cmd
└── .gitignore
