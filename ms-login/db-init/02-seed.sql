INSERT INTO categories (id, name, slug) VALUES
  (1, 'Technology', 'technology'),
  (2, 'Sports',     'sports'),
  (3, 'Business',   'business');

INSERT INTO news (id, title, summary, content, category_id) VALUES
  (1, 'Spring Boot 3.5 released', 'Faster startup and better AOT support.', 'Body one.', 1),
  (2, 'Java 21 adoption climbs',  'Virtual threads drive the upgrade.',    'Body two.', 1),
  (3, 'City wins the cup final',  'A late goal settles it.',               'Body three.', 2),
  (4, 'Markets close higher',     'Tech leads the rally.',                 'Body four.', 3);

INSERT INTO users (id, username, password, full_name) VALUES
  (1, 'alice', '$2a$10$N9qo8uLOickgx2ZMRZoMye1VdLmQ3xGJ0yq0lJ8uQFw8Lz9z1qQGu', 'Alice Nguyen'),
  (2, 'bob',   '$2a$10$N9qo8uLOickgx2ZMRZoMye1VdLmQ3xGJ0yq0lJ8uQFw8Lz9z1qQGu', 'Bob Tran');

INSERT INTO wishlist (user_id, news_id) VALUES
  (1, 1), (1, 3), (2, 2), (2, 4);