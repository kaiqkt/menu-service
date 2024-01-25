CREATE TABLE IF NOT EXISTS menu_user (
    id VARCHAR(26) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(255) NOT NULL,
    role VARCHAR(15) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurant (
    id VARCHAR(26) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    delivery_fee DECIMAL NOT NULL,
    user_id VARCHAR(26),
    FOREIGN KEY (user_id) REFERENCES menu_user(id)
);

CREATE TABLE IF NOT EXISTS category (
    id VARCHAR(26) PRIMARY KEY,
    image VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    restaurant_id VARCHAR(255),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE IF NOT EXISTS item (
    id VARCHAR(26) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    images TEXT NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL NOT NULL,
    category_id VARCHAR(26),
    restaurant_id VARCHAR(26),
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);