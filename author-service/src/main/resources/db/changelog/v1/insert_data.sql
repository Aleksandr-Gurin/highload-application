insert into countries (id, country_name)
values
    ('5c2c0fad-1142-4af3-a651-d753e66548fc', 'Russian'),('f8fd23ef-99db-485c-a380-dc1da4f84f20', 'America');

insert into cities (id, city_name, country_id)
values
    ('1e56481a-a2c8-4eaf-b956-1764835c3d6a', 'SPB', '5c2c0fad-1142-4af3-a651-d753e66548fc'),
    ('6d27298a-defe-4f26-a550-9d191eb56ff1', 'Moscow', '5c2c0fad-1142-4af3-a651-d753e66548fc'),
    ('188001f3-8638-44bb-b4e4-5d0484f323cc', 'LA', 'f8fd23ef-99db-485c-a380-dc1da4f84f20'),
    ('978c8446-7c9b-4af5-8bdc-a63343ffb8fe', 'New York', 'f8fd23ef-99db-485c-a380-dc1da4f84f20');

insert into concerts (id, title, performer, concert_date, city_id)
values
    ('0e996cac-4411-405d-9b3b-ebba2d4ac88d', 'First LP Concert', 'Linkin Park', '01.01.2000', '1e56481a-a2c8-4eaf-b956-1764835c3d6a'),
    ('bb95f504-e8f4-4026-87c0-97e0414bd662', 'Second LP Concert', 'Linkin Park', '01.01.2001', '6d27298a-defe-4f26-a550-9d191eb56ff1'),
    ('f72fbef6-797c-4f0a-89d6-48aee1885c84', 'First TDG Concert', 'Three Days Grace', '01.06.2001', '188001f3-8638-44bb-b4e4-5d0484f323cc'),
    ('5a106215-97a4-4723-80ed-ccf7f05acd1a', 'Second TDG Concert', 'Three Days Grace', '01.02.2003', '978c8446-7c9b-4af5-8bdc-a63343ffb8fe');

insert into authors (id, name, description)
values
    ('a8082910-2c6f-43a9-a732-37ad1c8cf5de', 'Vlad', 'Vlad description'),
    ('2516017d-8e14-4792-bfbc-8d5ad9c61372', 'Fedor', 'Fedor description'),
    ('3d803a9a-aa36-4c7f-944c-59764d2e3d03', 'Pavel', 'Pavel description');

insert into audios (id, title, author_id)
values
    ('57084ff0-97c1-49bb-973f-3a3ec6c169e1', 'First audio', 'a8082910-2c6f-43a9-a732-37ad1c8cf5de'),
    ('ea785923-251b-461e-b0f7-7f23e4c0e569', 'Second audio', '2516017d-8e14-4792-bfbc-8d5ad9c61372'),
    ('0fd6d94f-9ce3-47f3-9409-6cc6d768f634', 'Third audio', '3d803a9a-aa36-4c7f-944c-59764d2e3d03'),
    ('5393b509-97bd-451c-aeb4-3253db9a071e', 'Fourth audio', '3d803a9a-aa36-4c7f-944c-59764d2e3d03');
