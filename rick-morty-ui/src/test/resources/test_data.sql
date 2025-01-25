INSERT INTO "locations"
VALUES (1, 1, '2017-11-10 12:42:04.162000', 'Dimension C-137', 'Earth (C-137)', 'Planet'),
       (2, 2, '2017-11-10 13:06:38.182000', 'unknown', 'Abadango', 'Cluster'),
       (3, 3, '2017-11-10 13:08:13.191000', 'unknown', 'Citadel of Ricks', 'Space station'),
       (4, 4, '2017-11-10 13:08:20.569000', 'unknown', 'Worldender''s lair', 'Planet'),
       (5, 5, '2017-11-10 13:08:46.060000', 'Dimension C-137', 'Anatomy Park', 'Microverse'),
       (6, 6, '2017-11-10 13:09:09.102000', 'unknown', 'Interdimensional Cable', 'TV'),
       (7, 7, '2017-11-10 13:09:17.136000', 'unknown', 'Immortality Field Resort', 'Resort'),
       (8, 8, '2017-11-10 13:09:22.551000', 'Post-Apocalyptic Dimension', 'Post-Apocalyptic Earth', 'Planet'),
       (9, 9, '2017-11-10 13:09:29.566000', 'Replacement Dimension', 'Purge Planet', 'Planet'),
       (10, 10, '2017-11-18 11:21:51.643000', 'unknown', 'Venzenulon 7', 'Planet');


INSERT INTO "characters"
VALUES (1, 3, 1, 1, '2017-11-04 18:48:46.250000', 'Male', 'https://rickandmortyapi.com/api/character/avatar/1.jpeg',
        'Rick Sanchez', 'Human', 'Alive', ''),
       (2, 5, NULL, 2, '2017-11-04 18:50:21.651000', 'Male', 'https://rickandmortyapi.com/api/character/avatar/2.jpeg',
        'Morty Smith', 'Human', 'Alive', ''),
       (3, 8, 5, 3, '2017-11-04 19:09:56.428000', 'Female', 'https://rickandmortyapi.com/api/character/avatar/3.jpeg',
        'Summer Smith', 'Human', 'Alive', ''),
       (4, 7, 5, 4, '2017-11-04 19:22:43.665000', 'Female', 'https://rickandmortyapi.com/api/character/avatar/4.jpeg',
        'Beth Smith', 'Human', 'Alive', ''),
       (5, 10, 10, 5, '2017-11-04 19:26:56.301000', 'Male', 'https://rickandmortyapi.com/api/character/avatar/5.jpeg',
        'Jerry Smith', 'Human', 'Alive', ''),
       (6, 2, 2, 6, '2017-11-04 19:50:28.250000', 'Female', 'https://rickandmortyapi.com/api/character/avatar/6.jpeg',
        'Abadango Cluster Princess', 'Alien', 'Alive', ''),
       (7, 7, 5, 7, '2017-11-04 19:59:20.523000', 'Male', 'https://rickandmortyapi.com/api/character/avatar/7.jpeg',
        'Abradolf Lincler', 'Human', 'unknown', 'Genetic experiment'),
       (8, 3, NULL, 8, '2017-11-04 20:03:34.737000', 'Male', 'https://rickandmortyapi.com/api/character/avatar/8.jpeg',
        'Adjudicator Rick', 'Human', 'Dead', ''),
       (9, 4, 5, 9, '2017-11-04 20:06:54.976000', 'Male', 'https://rickandmortyapi.com/api/character/avatar/9.jpeg',
        'Agency Director', 'Human', 'Dead', ''),
       (10, 4, NULL, 10, '2017-11-04 20:19:09.017000', 'Male',
        'https://rickandmortyapi.com/api/character/avatar/10.jpeg', 'Alan Rails', 'Human', 'Dead',
        'Superhuman (Ghost trains summoner)');

INSERT INTO "episodes"
VALUES (1, 1, '2017-11-10 12:56:33.798000', 'December 2, 2013', 'S01E01', 'Pilot'),
       (2, 2, '2017-11-10 12:56:33.916000', 'December 9, 2013', 'S01E02', 'Lawnmower Dog'),
       (3, 3, '2017-11-10 12:56:34.022000', 'December 16, 2013', 'S01E03', 'Anatomy Park'),
       (4, 4, '2017-11-10 12:56:34.129000', 'January 13, 2014', 'S01E04', 'M. Night Shaym-Aliens!'),
       (5, 5, '2017-11-10 12:56:34.236000', 'January 20, 2014', 'S01E05', 'Meeseeks and Destroy'),
       (6, 6, '2017-11-10 12:56:34.339000', 'January 27, 2014', 'S01E06', 'Rick Potion #9'),
       (7, 7, '2017-11-10 12:56:34.441000', 'March 10, 2014', 'S01E07', 'Raising Gazorpazorp'),
       (8, 8, '2017-11-10 12:56:34.543000', 'March 17, 2014', 'S01E08', 'Rixty Minutes'),
       (9, 9, '2017-11-10 12:56:34.645000', 'March 24, 2014', 'S01E09', 'Something Ricked This Way Comes'),
       (10, 10, '2017-11-10 12:56:34.747000', 'April 7, 2014', 'S01E10', 'Close Rick-counters of the Rick Kind');

INSERT INTO "character_episode"
VALUES (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 1),
       (2, 3),
       (2, 4),
       (2, 5),
       (3, 1),
       (3, 2),
       (3, 4),
       (3, 5),
       (4, 1),
       (4, 2),
       (4, 3),
       (4, 5),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 4),
       (6, 1),
       (6, 2),
       (6, 3),
       (6, 4),
       (7, 1),
       (7, 2),
       (7, 3),
       (7, 4),
       (8, 1),
       (8, 2),
       (8, 3),
       (8, 4),
       (9, 1),
       (9, 2),
       (9, 3),
       (9, 4),
       (10, 1),
       (10, 2),
       (10, 3),
       (10, 4);


INSERT INTO "users"
VALUES (1, '$$2a$10$S3dfdAxuMnpRHOoJ.JXJLOXi.91j1DkwHoa/rWMpKZjSNh0TBCKGy', 'rick'),
       (2, '$2a$10$S3dfdAxuMnpRHOoJ.JXJLOXi.91j1DkwHoa/rWMpKZjSNh0TBCKGy', 'morty');

INSERT INTO "user_roles"
VALUES (1, 'USER'), (1, 'MODERATOR'), (1, 'ADMIN'), (2, 'MODERATOR');
