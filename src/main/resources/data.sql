DELETE FROM user_roles WHERE user_id IN (1, 2);
DELETE FROM application_user WHERE id IN (1, 2);
INSERT INTO application_user (version, id, username, name, hashed_password) VALUES (1, 1, 'user', 'John Normal', '$2a$10$xdbKoM48VySZqVSU/cSlVeJn0Z04XCZ7KZBjUBC00eKo5uLswyOpe');
INSERT INTO user_roles (user_id, roles) VALUES (1, 'USER');
INSERT INTO application_user (version, id, username, name, hashed_password) VALUES (1, 2, 'admin', 'Emma Powerful', '$2a$10$jpLNVNeA7Ar/ZQ2DKbKCm.MuT2ESe.Qop96jipKMq7RaUgCoQedV.');
INSERT INTO user_roles (user_id, roles) VALUES (2, 'USER');
INSERT INTO user_roles (user_id, roles) VALUES (2, 'ADMIN');
