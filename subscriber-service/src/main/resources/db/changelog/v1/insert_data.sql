insert into subscriptions (id, name, price, description)
values
    ('43a583a9-beb9-44d8-8796-b1a7d9546e06', 'First subscription', 1000, 'First subscription description'),
    ('59e4c4de-720f-4f04-99c2-28dd8c7bdaec', 'Second subscription', 2000, 'Second subscription description'),
    ('6a1d6e97-cabe-407f-aa66-19f12a2aa2ca', 'Third subscription', 500, 'Third subscription description'),
    ('008214b7-11fa-46e3-8da8-1113f0653834', 'Fourth subscription', 1500, 'Fourth subscription description');

insert into tickets (id, price, concert_id, user_id)
values
    ('31e132dd-075a-41c4-8b55-8a5a93ec912d', 200, '0e996cac-4411-405d-9b3b-ebba2d4ac88d', '78a87ecf-b365-47e9-99d2-dfdd17fc06b4'),
    ('d6aa5c20-06c9-4733-a2c1-2c5891f24cce', 300, 'bb95f504-e8f4-4026-87c0-97e0414bd662', '78a87ecf-b365-47e9-99d2-dfdd17fc06b4'),
    ('7313a240-dbda-4bf0-b420-76dcaa96f696', 300, 'bb95f504-e8f4-4026-87c0-97e0414bd662', 'e11ede9c-d167-445b-a8a2-bc0172989b04'),
    ('da2b5547-fa95-4434-b267-5a5f6cbefcfa', 250, 'f72fbef6-797c-4f0a-89d6-48aee1885c84', '1e6eb988-f263-418a-893d-ceb97a8f2004'),
    ('a224a480-acd9-414f-b7b0-0e7f04330726', 100, '5a106215-97a4-4723-80ed-ccf7f05acd1a', 'f3e18d25-7122-4ded-8618-fe79521f7c2e');

insert into user_playlists (id, title, description, user_id)
values
    ('d10cc96f-dc88-4a6d-b70a-c6818454b7ae', 'Aleksandr playlist1', 'Aleksandr playlist', '78a87ecf-b365-47e9-99d2-dfdd17fc06b4'),
    ('34b41e6e-f076-4c65-8283-eaeaa6f408d1', 'Boris playlist2', 'Boris playlist', 'e11ede9c-d167-445b-a8a2-bc0172989b04'),
    ('ea5ca710-7e0c-4329-b400-76db23680d48', 'Danil playlist', 'Danil playlist', '1e6eb988-f263-418a-893d-ceb97a8f2004'),
    ('846dd2b6-c244-4867-bf2f-98428d30a79b', 'Egor playlist', 'Egor playlist', 'f3e18d25-7122-4ded-8618-fe79521f7c2e');

insert into user_subscriptions (id, is_valid, host_user_id, subscription_id)
values
    ('a3216729-26d0-4ff4-9927-6c9456516ff9', true, '78a87ecf-b365-47e9-99d2-dfdd17fc06b4', '43a583a9-beb9-44d8-8796-b1a7d9546e06'),
    ('85adb0dd-9176-4c03-836d-a153ef2611d4', true, 'e11ede9c-d167-445b-a8a2-bc0172989b04', '43a583a9-beb9-44d8-8796-b1a7d9546e06'),
    ('9e3dc479-0354-4fd4-b02e-93843ef42987', true, '1e6eb988-f263-418a-893d-ceb97a8f2004', '6a1d6e97-cabe-407f-aa66-19f12a2aa2ca'),
    ('5b0c49c9-1488-48f2-a3fa-f316639ed584', true, 'f3e18d25-7122-4ded-8618-fe79521f7c2e', '008214b7-11fa-46e3-8da8-1113f0653834');
