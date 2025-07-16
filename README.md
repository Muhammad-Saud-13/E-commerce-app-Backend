# 🛒 E-commerce App Backend (Spring Boot + MongoDB)

This is a simple backend API for an E-commerce-style system built using **Spring Boot 3**, **Java 21**, and **MongoDB Atlas**.

---

## 💡 Features

- Add, update, get, delete products
- Add customers
- Customers can buy products (stock check included)
- View purchased products by a customer
- MongoDB integration (cloud database)
- REST API tested with Postman

---

## ⚙️ Tech Stack

- Java 21
- Spring Boot 3.5.3
- MongoDB Atlas
- Maven
- Postman (for API testing)

---

## 📬 API Endpoints

### 🔹 Product

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/product/add-product` | Add a new product |
| `GET` | `/product/get-product/{id}` | Get product by ID |
| `PUT` | `/product/update/{id}` | Update a product |
| `DELETE` | `/product/removeProduct/{id}` | Delete a product |

### 🔹 Customer

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/customer/add-customer` | Add a new customer |
| `POST` | `/customer/buy/{custId}/{prodId}` | Buy a product |
| `GET` | `/customer/get-all-purchased/{custId}` | View all purchased products by customer |

---

## 🛠️ How to Run

### 1. Clone the repo

```bash
git clone https://github.com/Muhammad-Saud-13/E-commerce-app-Backend.git
cd E-commerce-app-Backend
