CREATE TABLE user (
  USER_NAME   VARCHAR(255) PRIMARY KEY,
  PASSWORD    VARCHAR(255) NOT NULL,
  DEPOSIT     INTEGER NOT NULL
);

CREATE TABLE roles (
  USER_NAME   VARCHAR(255) NOT NULL,
  ROLE        VARCHAR(64) NOT NULL,
  FOREIGN KEY (USER_NAME) REFERENCES user(USER_NAME)
);

CREATE TABLE product (
  ID                 INTEGER AUTO_INCREMENT PRIMARY KEY,
  AMOUNT_AVAILABLE   INTEGER NOT NULL,
  COST               INTEGER NOT NULL,
  PRODUCT_NAME       VARCHAR(255) NOT NULL,
  SELLER_NAME        VARCHAR(255) NOT NULL,
  FOREIGN KEY (SELLER_NAME) REFERENCES USER(USER_NAME)
);

CREATE TABLE transactions (
  ID          INTEGER AUTO_INCREMENT PRIMARY KEY,
  USER_NAME   VARCHAR(255) NOT NULL,
  PRODUCT_ID  INTEGER NOT NULL,
  TIMESTAMP   TIMESTAMP NOT NULL,
  FOREIGN KEY (USER_NAME) REFERENCES USER(USER_NAME),
  FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID)
)