INSERT INTO users (user_name, user_pass) VALUES ('user2', 'test');
INSERT INTO users (user_name, user_pass) VALUES ('admin2', 'test');
INSERT INTO roles (role_name) VALUES ('user');
INSERT INTO roles (role_name) VALUES ('admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('user2', 'admin');
INSERT INTO user_roles (user_name, role_name) VALUES ('admin2', 'admin');