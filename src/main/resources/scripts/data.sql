/*
TRUNCATE TABLE products;
TRUNCATE TABLE discount_card;
*/

INSERT INTO products ("name", price, discount)
VALUES ('milk', 0.99, false),
		('yogurt', 0.87, false),
		('bread', 0.7, false),
		('cookie', 1, true),
		('butter', 1.05, false),
		('deodorant', 2.18, true),
		('soap', 0.89, true),
		('shower gel', 2.94, true),
		('toothbrush', 1.03, true),
		('toothpaste', 1.79, true),
		('candies', 3.18, true);
		
INSERT INTO discount_card(discount_size)
VALUES (1.5),
		(5),
		(3);