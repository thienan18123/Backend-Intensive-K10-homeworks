CREATE TABLE categories (
  id    BIGINT AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(100) NOT NULL,
  slug  VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE news (
  id           BIGINT AUTO_INCREMENT PRIMARY KEY,
  title        VARCHAR(255) NOT NULL,
  summary      VARCHAR(500),
  content      TEXT,
  published_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  category_id  BIGINT NOT NULL,
  CONSTRAINT fk_news_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE users (
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  username  VARCHAR(60) NOT NULL UNIQUE,
  password  VARCHAR(100) NOT NULL,
  full_name VARCHAR(120)
);

CREATE TABLE wishlist (
  id       BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id  BIGINT NOT NULL,
  news_id  BIGINT NOT NULL,
  added_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT uq_wish UNIQUE (user_id, news_id),
  CONSTRAINT fk_wish_news FOREIGN KEY (news_id) REFERENCES news(id)
);