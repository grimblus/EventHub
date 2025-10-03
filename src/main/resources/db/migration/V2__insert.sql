INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES ('Aaron', 'Tran', 'password123', 'aaron@example.com', 'STUDENT', FALSE);

INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES ('Bingus', 'Bongus', 's3cur3pass', 'bingus@example.com', 'ADMIN', FALSE);

INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES ('Coolguy', 'Joe', 'coolguy123', 'coolguy@example.com', 'STUDENT', FALSE);

INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES ('Johndev', 'Programmer', 'g0d0t0man', 'johndev@example.com', 'ORGANISER', FALSE);

INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES ('Dexter', 'Morgan', 'b4yharb0r', 'morgan@example.com', 'STUDENT', FALSE);

INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES ('Deez', 'Noots', 'g0tt3m', 'deez@example.com', 'ORGANISER', FALSE);

INSERT INTO events (title, description, ownerID, date, location, ticketPrice, category, keywords, tags, images)
VALUES ('Workshop', 'Learn Basics', 5, TIMESTAMP '2025-09-01 10:00:00',
        'Old Melbourne Gaol, 377 Russell St', '6', 'General',
        'java,workshop', 'education,beginner', 'temp.png,temp.png');

INSERT INTO events (title, description, ownerID, date, location, ticketPrice, category, keywords, tags, images)
VALUES ('Hackathon', 'Coding Challenge', 4, TIMESTAMP '2025-10-10 09:00:00',
        '1 Hacker Wy, Menlo Park', '999999', 'Coding',
        'coding,challenge', 'competition,teamwork', 'temp.png,temp.png');

INSERT INTO payments (id, user_id, amount, time)
VALUES (RANDOM_UUID(), 1, 54.00, CURRENT_TIMESTAMP);

INSERT INTO payments (id, user_id, amount, time)
VALUES (RANDOM_UUID(), 1, 43.00, CURRENT_TIMESTAMP);

INSERT INTO payments (id, user_id, amount, time)
VALUES (RANDOM_UUID(), 2, 999.00, CURRENT_TIMESTAMP);