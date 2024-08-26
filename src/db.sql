CREATE DATABASE store_db;

USE store_db;

CREATE TABLE Products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    price DOUBLE,
    quantity INT
);

CREATE TABLE Customers (
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) ,
    password VARCHAR(100)
);

CREATE TABLE Carts (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(50),
    product_id VARCHAR(50),
    FOREIGN KEY (customer_id) REFERENCES Customers(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);
