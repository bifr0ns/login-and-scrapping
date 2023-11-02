DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS page;
DROP TABLE IF EXISTS page_url;

-- Create the 'user' table
CREATE TABLE user_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL
);

-- Create the 'login' table
CREATE TABLE login (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_info(id) ON DELETE CASCADE,
    CONSTRAINT UNIQUE_EMAIL UNIQUE (email)
);

-- Create the 'page' table
CREATE TABLE page (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    url VARCHAR(255),
    user_id INT NOT NULL,
    count INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_info(id) ON DELETE CASCADE
);

-- Create the 'post_url' table
CREATE TABLE page_url (
    id INT AUTO_INCREMENT PRIMARY KEY,
    page_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    FOREIGN KEY (page_id) REFERENCES page(id) ON DELETE CASCADE
);

-- Index on user_id column
CREATE INDEX idx_page_user_id ON page (user_id);