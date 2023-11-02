-- Insert data into the 'user_info' table
INSERT INTO user_info (username) VALUES
    ('user1'),
    ('user2'),
    ('user3');

-- Insert data into the 'login' table
INSERT INTO login (user_id, email, password) VALUES
    (1, 'user1@example.com', 'password1'),
    (2, 'user2@example.com', 'password2'),
    (3, 'diana@gmail.com', '$argon2id$v=19$m=4096,t=20,p=4$ILhKy7iRPK00zS91$fmDNfMOJUVKjEtdOU6yi3kVqtlIor3cjsYgBJWC8eqUM8Z3f01rwbVUf0d5lZxE6SYIm797g3Q3jYxO9Fwlj88UXEDjCHkyM/U0+VM+BwvVx5BnODwIHY461x19QFoJXIy7oDtMxmg0TsjkYif9lIDblZBLkX3X2NmDX8slNnfg');

-- Insert data into the 'page' table
INSERT INTO page (name, url, user_id, count) VALUES
    ('Page 1', 'https://example.com/page1', 1, 1),
    ('Page 2', 'https://example.com/page2', 2, 2),
    ('Page 3', 'https://example.com/page3', 3, 3);

-- Insert data into the 'page_url' table
INSERT INTO page_url (page_id, name, url) VALUES
    (1, 'URL 1', 'https://example.com/page1/url1'),
    (2, 'URL 1', 'https://example.com/page2/url1'),
    (2, 'URL 2', 'https://example.com/page2/url2'),
    (3, 'URL 1', 'https://example.com/page3/url1'),
    (3, 'URL 2', 'https://example.com/page3/url2'),
    (3, 'URL 3', 'https://example.com/page3/url3');
