-- ОЧИСТКА ВСЕГО
DELETE FROM registered_service;
DELETE FROM account;
DELETE FROM billing;
DELETE FROM billing_users;
DELETE FROM payment;


-- ВСТАВКА
INSERT INTO registered_service (id, title) VALUES (0, 'ЖКХ'), (1, 'Парковки');

INSERT INTO account (id, balance, user_id) VALUES
    ('720d4cd6-31b1-4d22-9cba-b413353040b7', 0, 1),
    ('26c81461-1c15-479e-9687-2d34eccf77c4', 100, 2),
    ('69163668-e5af-4a15-b98d-3159cfd4d11d', 1000, 3),
    ('239ce0be-5bcb-403a-bed6-38fc72af4af4', 0, 4),
    ('2f528363-e3b6-4481-bb65-793a49c1c97e', 5999, 5),
    ('47c5e962-c6eb-412c-95cf-9e87e5da4174', 0, 6);

INSERT INTO billing (uuid, amount, created_at, state, service_id) VALUES
    ('e69890fe-6155-4306-a91b-fbc56243fcab', 300, '2025-11-28T10:30:00Z', 'NOT_PAID', 0),
    ('428d1ea3-405a-4d95-9101-3618de8642f6', 900, '2025-11-25T15:30:00Z', 'PAID', 1),
    ('4bad9bde-37b9-40c1-a924-ad109780a793', 1000, '2025-11-20T12:55:00Z', 'NOT_PAID', 1),
    ('1de143b6-4442-456c-9f4f-cfe848a1250f', 10, '2025-11-20T12:55:00Z', 'PAID', 0),

    ('10faeb14-8ca2-4d25-b055-de2d85891921', 324, '2025-11-28T10:30:00Z', 'PAID', 0),
    ('b278a807-3a5f-481d-a979-a31668d7aa8e', 75, '2025-11-25T15:30:00Z', 'PAID', 0),
    ('e2a50a58-c515-4b51-844a-d27c14868157', 45, '2025-11-20T12:55:00Z', 'PAID', 1),
    ('af17f588-3e30-4a30-8c25-be3d6ed44464', 1324, '2025-11-20T12:55:00Z', 'PAID', 0),

    ('e0a062ba-7c15-421e-ae53-991962e857ca', 344, '2025-11-28T10:30:00Z', 'NOT_PAID', 0),
    ('96e5b40e-bba5-41d4-bddf-bb000d9eb7be', 901, '2025-11-25T15:30:00Z', 'NOT_PAID', 1),
    ('380d7b68-fa20-40b5-9515-6fcd91e296f2', 1040, '2025-11-20T12:55:00Z', 'NOT_PAID', 1),
    ('51c6b7a9-4c8d-4240-ac21-ff82c66d8562', 14, '2025-11-20T12:55:00Z', 'NOT_PAID', 0);


INSERT INTO billing_users (billing_uuid, user_id ) VALUES
    ('e69890fe-6155-4306-a91b-fbc56243fcab', 1),
    ('428d1ea3-405a-4d95-9101-3618de8642f6', 1),
    ('4bad9bde-37b9-40c1-a924-ad109780a793', 1),
    ('1de143b6-4442-456c-9f4f-cfe848a1250f', 1),

    ('10faeb14-8ca2-4d25-b055-de2d85891921', 2),
    ('b278a807-3a5f-481d-a979-a31668d7aa8e', 2),
    ('e2a50a58-c515-4b51-844a-d27c14868157', 2),
    ('af17f588-3e30-4a30-8c25-be3d6ed44464', 2),

    ('e0a062ba-7c15-421e-ae53-991962e857ca', 3),
    ('96e5b40e-bba5-41d4-bddf-bb000d9eb7be', 3),
    ('380d7b68-fa20-40b5-9515-6fcd91e296f2', 3),
    ('51c6b7a9-4c8d-4240-ac21-ff82c66d8562', 3);

INSERT INTO payment (uuid, amount, created_at, type, user_id, billing_uuid) VALUES
    ('b8c2e868-5230-4f02-a0b4-b590fc28074c', 910, '2025-11-29T12:55:00Z', 'REFILL_ACCOUNT', 1, null),
    ('293b7b16-fcbc-4a8a-be59-12e9edd328d0', 10, '2025-11-29T12:59:00Z', 'PAY_BILLING', 1, '1de143b6-4442-456c-9f4f-cfe848a1250f'),
    ('428d1ea3-405a-4d95-9101-3618de8642f6', 910, '2025-11-29T12:55:00Z', 'PAY_BILLING', 1, null),

    ('94e6cb8a-8c2e-492e-815e-08cca120a7f8', 324, '2025-11-29T10:00:00Z', 'REFILL_ACCOUNT', 2, null),
    ('e21ae42e-8b14-43ba-83ec-b315a51848be', 324, '2025-11-29T10:01:00Z', 'PAY_BILLING', 2, '10faeb14-8ca2-4d25-b055-de2d85891921'),
    ('f6fd2108-dfb6-4c99-99d4-e251f21d60d9', 75, '2025-11-29T10:02:00Z', 'REFILL_ACCOUNT', 2, null),
    ('2d4b477e-4492-40d6-92d7-83697beb7bd4', 75, '2025-11-29T10:03:00Z', 'PAY_BILLING', 2, 'b278a807-3a5f-481d-a979-a31668d7aa8e'),
    ('586242a4-a746-4842-a809-789a928c0d15', 45, '2025-11-29T10:04:00Z', 'REFILL_ACCOUNT', 2, null),
    ('5e0c08e0-58ae-4e82-b1e1-ce8a24e9f405', 45, '2025-11-29T10:05:00Z', 'PAY_BILLING', 2, 'e2a50a58-c515-4b51-844a-d27c14868157'),
    ('ba7c1d32-db1b-4835-9456-881c6ae79f3b', 1324, '2025-11-29T10:06:00Z', 'REFILL_ACCOUNT', 2, null),
    ('c7edb1b6-d6ea-45f7-9e8d-e7c176e7911c', 1324, '2025-11-29T10:07:00Z', 'PAY_BILLING', 2, 'af17f588-3e30-4a30-8c25-be3d6ed44464'),
    ('3976f721-1574-4a39-8e12-b75d743fcf14', 100, '2025-11-29T10:08:00Z', 'REFILL_ACCOUNT', 2, null),

    ('0ef3350b-0bfb-47e5-be42-6af28cd0b8bf', 1000, '2025-11-29T10:09:00Z', 'REFILL_ACCOUNT', 3, null),

    ('09e17bc9-284f-4b51-bf3f-a3546fd6de65', 5000, '2025-11-29T10:10:00Z', 'REFILL_ACCOUNT', 5, null),
    ('53cb05a7-7313-433a-9df8-cbf58491a04c', 999, '2025-11-29T10:08:00Z', 'REFILL_ACCOUNT', 5, null);