INSERT INTO products (uuid, fantasy_name, category, description, price, available, is_deleted)
VALUES ('be1cf063-79a8-4f25-9f04-18a157f5249d', 'Classic Burger', 'HAMBURGERS_AND_HOT_DOGS',
        'A delicious classic burger with all the fixings.', 5.99, TRUE, FALSE),
       ('be1cf063-79a8-4f25-9f04-18a157f5241c', 'Cheese Pizza', 'VEGAN_FOOD',
        'A cheesy vegan pizza with a crispy crust.', 8.99, TRUE, FALSE),
       ('be1cf063-79a8-4f25-9f04-18a157f5242e', 'Chicken Wings', 'CHICKEN', 'Spicy chicken wings served with ranch.',
        7.99, TRUE, FALSE),
       ('b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e', 'Fish Tacos', 'FISH', 'Fish tacos with a fresh cilantro lime slaw.',
        6.99, TRUE, FALSE),
       ('238f3d59-c10d-4745-913d-8f9e0d36532b', 'Chocolate Cake', 'DESSERTS',
        'Rich chocolate cake with a smooth frosting.', 4.99, TRUE, FALSE),
       ('be1cf063-79a8-4f25-9f04-18a157f2149d', 'Medieval Burger', 'HAMBURGERS_AND_HOT_DOGS',
        'A delicious classic burger with all the fixings.', 5.99, TRUE, FALSE),
       ('be1cf063-79a8-4f25-9f04-18a157f2142a', 'BBQ Chicken', 'CHICKEN',
        'A delicious classic chicken with all the sauces.', 15.99, TRUE, FALSE),
       ('a4e2b14d-d1d9-4b82-a1d2-8b4d4f1c0f5f', 'Veggie Burger', 'HAMBURGERS_AND_HOT_DOGS',
        'A delicious veggie burger with a blend of vegetables and spices.', 6.49, TRUE, FALSE),
       ('b7a2c9f1-0e2c-4b54-845b-9c4e3a6b9e3a', 'Falafel Wrap', 'VEGAN_FOOD',
        'A wrap filled with crispy falafel, hummus, and vegetables.', 7.49, TRUE, FALSE),
       ('e5f2d6c8-3f3e-4a7c-8c1a-d1c7b4a2e1f5', 'Buffalo Wings', 'CHICKEN',
        'Hot and spicy buffalo wings with a side of celery and ranch.', 8.99, TRUE, FALSE),
       ('c6e8b7d2-1b3a-4b8e-9d9a-8f2b3a4e5d6f', 'Grilled Salmon', 'FISH',
        'Perfectly grilled salmon with a lemon dill sauce.', 12.99, TRUE, FALSE),
       ('f7e8c9d1-0d3a-4b2e-a1d9-8b7d6f5e3c1f', 'Carrot Cake', 'DESSERTS',
        'Moist carrot cake with cream cheese frosting.', 5.49, TRUE, FALSE),
       ('a9d2b3e4-6f7a-4e8d-9b2c-4e5d6f7a8b9c', 'Greek Salad', 'VEGAN_FOOD',
        'A fresh Greek salad with cucumbers, tomatoes, olives, and feta.', 6.99, TRUE, FALSE),
       ('b0e2f3d4-7a8b-4c1e-9d2f-3c4d5e6f7a8b', 'Steak Frites', 'HAMBURGERS_AND_HOT_DOGS',
        'Juicy steak served with crispy fries.', 13.49, TRUE, FALSE),
       ('d2e8f9a0-1b3c-4d6e-8a7b-9e0c1d2f3e4f', 'Mango Smoothie', 'VEGAN_FOOD',
        'Refreshing smoothie made with fresh mangoes and almond milk.', 4.99, TRUE, FALSE),
       ('e3c9b7d2-0a4d-4e5f-9b8a-7c2d1e3f4a5e', 'Pasta Alfredo', 'PASTA',
        'Creamy Alfredo pasta with grilled chicken.', 11.99, TRUE, FALSE),
       ('f4e8b7d1-2c4d-4e9a-8b6d-3c5f7a8b9e0c', 'Apple Pie', 'DESSERTS',
        'Classic apple pie with a flaky crust and sweet apple filling.', 4.79, TRUE, FALSE);

INSERT INTO clients (document, name, email, phone, delivery_address, is_deleted)
VALUES ('CE-123456789', 'John Doe', 'john.doe@example.com', '123-4567890', '123 Main St', FALSE),
       ('CC-234567890', 'Jane Smith', 'jane.smith@example.com', '234-5678901', '456 Elm St', FALSE),
       ('TI-345678901', 'Alice Johnson', 'alice.johnson@example.com', '345-6789012', '789 Oak St', FALSE),
       ('P-456789012', 'Bob Brown', 'bob.brown@example.com', '456-7890123', '101 Pine St', FALSE),
       ('CE-567890123', 'Charlie Davis', 'charlie.davis@example.com', '567-8901234', '202 Maple St', FALSE),
       ('CE-678901234', 'David Evans', 'david.evans@example.com', '678-9012345', '303 Birch St', FALSE),
       ('CC-789012345', 'Emma Wilson', 'emma.wilson@example.com', '789-0123456', '404 Cedar St', FALSE),
       ('TI-890123456', 'Frank Harris', 'frank.harris@example.com', '890-1234567', '505 Spruce St', FALSE),
       ('P-901234567', 'Grace Martin', 'grace.martin@example.com', '901-2345678', '606 Fir St', FALSE),
       ('CE-012345678', 'Henry Clark', 'henry.clark@example.com', '012-3456789', '707 Ash St', FALSE),
       ('CC-123456780', 'Ivy Lewis', 'ivy.lewis@example.com', '123-4567801', '808 Pine St', FALSE),
       ('TI-234567891', 'Jack Walker', 'jack.walker@example.com', '234-5678912', '909 Maple St', FALSE),
       ('P-345678902', 'Karen Hall', 'karen.hall@example.com', '345-6789023', '1010 Oak St', FALSE),
       ('CE-456789013', 'Leo Allen', 'leo.allen@example.com', '456-7890134', '1111 Elm St', FALSE),
       ('CC-567890124', 'Mia Young', 'mia.young@example.com', '567-8901245', '1212 Birch St', FALSE),
       ('TI-678901235', 'Noah King', 'noah.king@example.com', '678-9012356', '1313 Cedar St', FALSE),
       ('P-789012346', 'Olivia Scott', 'olivia.scott@example.com', '789-0123467', '1414 Spruce St', FALSE),
       ('CE-890123457', 'Paul Green', 'paul.green@example.com', '890-1234578', '1515 Fir St', FALSE),
       ('CC-901234568', 'Quinn Baker', 'quinn.baker@example.com', '901-2345689', '1616 Ash St', FALSE),
       ('TI-012345679', 'Rachel Adams', 'rachel.adams@example.com', '012-3456790', '1717 Pine St', FALSE);


INSERT INTO orders (uuid, creation_date_time, client_document, extra_information, sub_total, tax, grand_total, delivered, delivery_date)
VALUES
    ('2cf7a83b-814b-41ab-a028-f1d6e5a6bde7', '2024-09-07 12:00:00', 'CE-123456789', 'Leave at the front door', 25.97, 2.60, 28.57, FALSE, NULL),
    ('0ba4788b-1d4b-492d-b42e-1bde1346d3e6', '2024-09-07 14:00:00', 'CC-234567890', 'Call when arrived', 17.98, 1.80, 19.78, FALSE, NULL),
    ('5cf7e39b-5eb2-474a-a6d6-f17e5b8d8e3f', '2024-09-06 11:30:00', 'TI-345678901', 'Deliver to the side entrance', 13.98, 1.40, 15.38, FALSE, NULL),
    ('bd93f908-4591-41d2-987a-7bdf9d34ea6c', '2024-09-06 13:45:00', 'P-456789012', 'No contact delivery', 23.98, 2.40, 26.38, FALSE, NULL),
    ('da2437e5-d785-46cf-98da-9937434c84f1', '2024-09-06 16:00:00', 'CE-567890123', 'Ring the bell', 30.97, 3.10, 34.07, FALSE, NULL),
    ('a48c783a-4e49-4092-8f2f-fc3f63409e5f', '2024-09-07 09:30:00', 'CE-678901234', 'Call me when you arrive', 11.98, 1.20, 13.18, FALSE, NULL),
    ('cfe8d981-46d4-4894-8c61-f17e4b6c8271', '2024-09-05 18:00:00', 'CC-789012345', 'Do not knock, leave at the door', 21.97, 2.20, 24.17, FALSE, NULL),
    ('aed9b3e4-3567-4878-b4b9-66c4ef81d0ed', '2024-09-05 19:30:00', 'TI-890123456', 'Deliver to the back door', 9.99, 1.00, 10.99, FALSE, NULL),
    ('c9234edb-6d1a-4e92-a0e2-69c927934c43', '2024-09-07 15:00:00', 'P-901234567', 'Ring the bell twice', 16.48, 1.60, 18.08, FALSE, NULL),
    ('4a7e273a-014e-48cf-a7e9-5d2a79a6a9e1', '2024-09-06 20:00:00', 'CE-012345678', 'Call on arrival', 22.97, 2.30, 25.27, FALSE, NULL);

INSERT INTO order_has_products (order_id, product_id, quantity)
VALUES
    (1, 1, 8),  -- Orden 1: 2 Classic Burgers
    (1, 2, 1),  -- Orden 1: 1 Cheese Pizza
    (2, 5, 2),  -- Orden 2: 2 Chocolate Cakes
    (2, 7, 1),  -- Orden 2: 1 BBQ Chicken
    (3, 4, 1),  -- Orden 3: 1 Fish Taco
    (3, 3, 2),  -- Orden 3: 2 Chicken Wings
    (4, 6, 2),  -- Orden 4: 2 Medieval Burgers
    (5, 10, 1), -- Orden 5: 1 Buffalo Wings
    (5, 9, 2),  -- Orden 5: 2 Falafel Wraps
    (6, 13, 1), -- Orden 6: 1 Greek Salad
    (7, 15, 2), -- Orden 7: 2 Carrot Cakes
    (8, 1, 1),  -- Orden 8: 1 Classic Burger
    (9, 16, 1), -- Orden 9: 1 Mango Smoothie
    (9, 17, 1), -- Orden 9: 1 Pasta Alfredo
    (10, 17, 1),-- Orden 10: 1 Apple Pie
    (10, 8, 2); -- Orden 10: 2 Veggie Burgers
