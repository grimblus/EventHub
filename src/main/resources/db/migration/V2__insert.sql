INSERT INTO users (firstName, lastName, password, email, permissionLevel, banned)
VALUES   ('Aaron', 'Lay', '0?04W!RgzEN9', 'lay.aaron@example.com', 'ORGANISER', FALSE),
         ('Andrew', 'Loi', 'tDN2l2:.041`', 'loi.andrew@example.com', 'STUDENT', FALSE),
         ('Patrick', 'Ly', '7Fig92i3B<z<', 'ly.patrick@example.com', 'ADMIN', FALSE),
         ('Donovan', 'Ong', 'm3(Xh3f=@5YX', 'ong.donovan@example.com', 'STUDENT', FALSE),
         ('Aaron', 'Tran', 'U/N£01,dP649', 'tran.aaron@example.com', 'ORGANISER', FALSE),
         ('Brody', 'Watson', 'E8T9&68Ck/W2', 'watson.brody@example.com', 'STUDENT', FALSE);

INSERT INTO events (title, description, ownerID, date, location, ticketPrice, category, keywords, tags, images)
VALUES  ('Workshop', 'Learn Basics', 5, '2025-12-01 10:00:00',
         'Old Melbourne Gaol, 377 Russell St', '54', 'General',
         'java,workshop', 'education,beginner', 'temp1.jpg,temp2.jpg'),
        ('Hackathon', 'Coding Challenge', 1, '2025-12-10 09:00:00',
         '1 Hacker Wy, Menlo Park', '43', 'Coding',
         'coding,challenge', 'competition,teamwork', 'temp3.jpg,temp4.jpg');

INSERT INTO payments (id, user_id, amount, `time`)
VALUES (UUID(), 2, 54.00, CURRENT_TIMESTAMP),
       (UUID(), 4, 43.00, CURRENT_TIMESTAMP),
       (UUID(), 6, 54.00, CURRENT_TIMESTAMP);