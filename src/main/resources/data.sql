INSERT INTO favourites(id, is_favourite, latitude, longitude, version, geo_code_id, city_name, country_name, user_id) VALUES (101, 1, 42.22715, -111.40104, 0, 5603240, 'Paris', 'United States', 'admin');

INSERT INTO app_user(id, enabled, name, password, username) VALUES (101, true, 'admin', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'admin');
INSERT INTO app_user(id, enabled, name, password, username) VALUES (102, true, 'user', '$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW', 'user');