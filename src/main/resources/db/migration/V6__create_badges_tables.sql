CREATE TABLE IF NOT EXISTS badges (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(500));

CREATE TABLE IF NOT EXISTS user_badges (
    user_id INT,
    badge_id INT,
    PRIMARY KEY (user_id, badge_id));