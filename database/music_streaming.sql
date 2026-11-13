-- скрипт для создания БД
CREATE TABLE artist (
                        artist_id	SERIAL PRIMARY KEY,
                        name 		VARCHAR(120) NOT NULL,
                        country		VARCHAR (100),
                        monthly_listeners BIGINT CHECK(monthly_listeners>=0)
);

CREATE TABLE track (
                       track_id	SERIAL PRIMARY KEY,
                       title 		VARCHAR(120) NOT NULL,
                       duration_sec INT CHECK(duration_sec>=0),
                       play_count BIGINT DEFAULT 0,
                       artist_id INT NOT NULL REFERENCES artist(artist_id)
);

CREATE TABLE app_user (
                          user_id SERIAL PRIMARY KEY,
                          username VARCHAR(60) UNIQUE NOT NULL,
                          email    VARCHAR(120) UNIQUE NOT NULL,
                          registered_at DATE DEFAULT CURRENT_DATE
);

CREATE TABLE playlist (
                          playlist_id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL REFERENCES app_user(user_id),
                          title VARCHAR(120)
);

CREATE TABLE playlist_track (
                                playlist_id INT NOT NULL REFERENCES playlist(playlist_id),
                                track_id    INT NOT NULL REFERENCES track(track_id),
                                PRIMARY KEY (playlist_id, track_id)
);

-- вставка значений
INSERT INTO artist (name, country, monthly_listeners) VALUES
    ('Adele',        'UK',   55000000),
    ('The Weeknd',   'Canada',  90000000),
    ('LP',    'UK',    2500000);

INSERT INTO track (title, duration_sec, play_count, artist_id) VALUES
    ('Easy On Me',        224, 1200000000, 1),
    ('Blinding Lights',   200, 3500000000, 2),
    ('Lost on you',      261, 800000000, 3);

INSERT INTO app_user(username, email, registered_at) VALUES
    ('sofia',  'sofia@mail.com', '2026-01-19'),
    ('ivan',   'ivan@mail.com',   '2024-10-11');

INSERT INTO playlist(user_id, title) VALUES
    (1, 'melancholic'),
    (1, 'happy moments'),
    (2, 'best of The Weeknd');

INSERT INTO playlist_track (playlist_id, track_id) VALUES
   (1, 1),
   (1, 3),
   (2, 2),
   (3, 2);

INSERT INTO track (title, duration_sec, play_count, artist_id) VALUES
-- Adele
    ('Hello',            295, 1800000000, 1),
    ('Someone Like You', 285, 1500000000, 1),
    ('Rolling in the Deep', 228, 1300000000, 1),
-- The Weeknd
    ('Save Your Tears',  215, 2100000000, 2),
    ('Take My Breath',   219, 600000000, 2),
    ('Starboy',          230, 2500000000, 2),
-- LP
    ('How Low can you go',      268, 900000000, 3),
    ('Day by Day',            218, 150000000, 3);