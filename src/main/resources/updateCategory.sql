INSERT INTO account (id, first_name, last_name, enabled_notifications, age, phone)
VALUES (55, 'Max', 'Selmah', true, 33, '0952434440');
INSERT INTO account (id, first_name, last_name, enabled_notifications, age, phone)
VALUES (56, 'Max', 'Tapovsky', true, 25, '0952457478');

INSERT INTO categories (id, name, description, image, account_id)
VALUES (57, 'laptops', 'we have laptops for everything', 'laptop.jpeg', 55);