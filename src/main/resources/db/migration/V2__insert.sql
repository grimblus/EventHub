INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES   ('Aaron', 'Tran', 'password123', 'aaron@example.com', 'STUDENT', FALSE),
         ('Bingus', 'Bongus', 's3cur3pass', 'bingus@example.com', 'ADMIN', FALSE),
         ('Coolguy', 'Joe', 'coolguy123', 'coolguy@example.com', 'STUDENT', FALSE),
         ('Johndev', 'Programmer', 'g0d0t0man', 'johndev@example.com', 'ORGANISER', FALSE),
         ('Dexter', 'Morgan', 'b4yharb0r', 'morgan@example.com', 'STUDENT', FALSE),
         ('Deez', 'Noots', 'g0tt3m', 'deez@example.com', 'ORGANISER', FALSE);

INSERT INTO events (title, description, ownerID, date, location, ticketPrice, category, keywords, tags, images)
VALUES  ('Workshop', 'Learn Basics', 5, '2025-09-01 10:00:00',
        'Old Melbourne Gaol, 377 Russell St', '6', 'General',
        'java,workshop', 'education,beginner', 'temp.png,temp.png'),
        ('Hackathon', 'Coding Challenge', 4, '2025-10-10 09:00:00',
        '1 Hacker Wy, Menlo Park', '999999', 'Coding',
        'coding,challenge', 'competition,teamwork', 'temp.png,temp.png');

INSERT INTO payments (id, user_id, amount, `time`)
VALUES (UUID(), 1, 54.00, CURRENT_TIMESTAMP),
       (UUID(), 1, 43.00, CURRENT_TIMESTAMP),
       (UUID(), 2, 999.00, CURRENT_TIMESTAMP);