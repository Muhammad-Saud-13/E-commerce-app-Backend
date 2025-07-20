# 🛒 E-commerce App Backend (Spring Boot + MongoDB)

This is a simple backend API for an E-commerce-style system built using **Spring Boot 3**, **Java 21**, and **MongoDB Atlas**.

---

## 💡 Features

- Add, update, get, and delete products
- Add new customers
- Customers can:
  - Add items to cart
  - Buy products in the entire cart
  - View purchased products
- Product stock is checked before purchase (both single and cart-based)
- MongoDB Atlas for database storage
- API tested using Postman

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

| Method | Endpoint                      | Description         |
|--------|-------------------------------|---------------------|
| POST   | `/product/add-product`        | Add a new product   |
| GET    | `/product/get-product/{id}`   | Get product by ID   |
| PUT    | `/product/update/{id}`        | Update a product    |
| DELETE | `/product/removeProduct/{id}` | Delete a product    |

### 🔹 Customer

| Method | Endpoint                                   | Description                                 |
|--------|--------------------------------------------|---------------------------------------------|
| POST   | `/customer/add-customer`                   | Add a new customer                          |
| POST   | `/customer/buy/{custId}/{prodId}`          | Buy a single product                        |
| POST   | `/customer/add-to-cart/{custId}/{prodId}`  | Add a product to customer's cart            |
| POST   | `/customer/buy-cart/{custId}`              | Buy all items in cart (stock checked)       |
| GET    | `/customer/get-all-purchased/{custId}`     | View all purchased products by customer     |

---

## 🛠️ How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/Muhammad-Saud-13/E-commerce-app-Backend.git
   cd E-commerce-app-Backend
