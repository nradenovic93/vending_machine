INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('admin', '$2a$10$2372noN6927DiOutTC5OhOBTv3rOEs6b7kF4KZYeDIs1vxUHuNIwy', 0); -- password: admin
INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('pinkunicorn', '$2a$10$pKuBIdjCcK0Z131OCOGlkudrmjk8tuCpPE9/k8rYCDCgJoU2VL.V6', 0); -- password: unicornmeat
INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('bluebear', '$2a$10$nBBx5KpJy5wHxWe2RnD4ROSEjyO0rrSBZ/RXx9k/on6jtvnRiB1om', 0); -- password: dancingbear
INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('orangeorange', '$2a$10$gJz8dLY0hwAE8tBdBCZ.0ubphUMqFh1G7QMIpUKpcx7i8wkrsC97C', 20); -- password: fruitfly
INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('crustytheclown', '$2a$10$CmtK6cnBFUB6YX78schaFeWBZw.atVXRS576gIhhP71ePNktL4G0O', 0); -- password: huehuehue
INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('bol', '$2a$10$VZdgNYyGqWPDEs3LpLLrFuzSLoL/xuP6vpdqEIMiAixMFU2xhunXq', 0); -- password: blue
INSERT INTO users (USERNAME, PASSWORD, DEPOSIT) VALUES ('rosie', '$2a$10$yHmikUMIOZSZXU6weB5rPOAd16duSEJljz0/gkIM9duIUdmvAB48.', 20); -- password: purple

INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('pinkunicorn', 'ROLE_BUYER');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('bluebear', 'ROLE_BUYER');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('orangeorange', 'ROLE_BUYER');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('crustytheclown', 'ROLE_BUYER');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('bol', 'ROLE_SELLER');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('rosie', 'ROLE_BUYER');
INSERT INTO authorities (USERNAME, AUTHORITY) VALUES ('rosie', 'ROLE_SELLER');

INSERT INTO product (AMOUNT_AVAILABLE, COST, PRODUCT_NAME, SELLER_NAME) VALUES (10, 15, 'DBZ figurine', 'bol');