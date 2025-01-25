CREATE TABLE "locations"
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    source_id INT          DEFAULT NULL,
    created   DATETIME    DEFAULT NULL,
    dimension VARCHAR(255) DEFAULT NULL,
    name      VARCHAR(255) DEFAULT NULL,
    type      VARCHAR(255) DEFAULT NULL
);

CREATE TABLE "characters"
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    location_id INT          DEFAULT NULL,
    origin_id   INT          DEFAULT NULL,
    source_id   INT          DEFAULT NULL,
    created     DATETIME    DEFAULT NULL,
    gender      VARCHAR(255) DEFAULT NULL,
    image       VARCHAR(255) DEFAULT NULL,
    name        VARCHAR(255) DEFAULT NULL,
    species     VARCHAR(255) DEFAULT NULL,
    status      VARCHAR(255) DEFAULT NULL,
    type        VARCHAR(255) DEFAULT NULL,
    CONSTRAINT FK_location FOREIGN KEY (location_id) REFERENCES "locations" (id),
    CONSTRAINT FK_origin FOREIGN KEY (origin_id) REFERENCES "locations" (id)
);


CREATE TABLE "episodes"
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    source_id INT          DEFAULT NULL,
    created   TIMESTAMP    DEFAULT NULL,
    air_date  VARCHAR(255) DEFAULT NULL,
    episode   VARCHAR(255) DEFAULT NULL,
    name      VARCHAR(255) DEFAULT NULL
);

CREATE TABLE "users"
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    UNIQUE (username)
);


CREATE TABLE "user_favorites"
(
    character_id INT          DEFAULT NULL,
    id           INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(255) DEFAULT NULL,
    CONSTRAINT FK_character FOREIGN KEY (character_id) REFERENCES "characters" (id)
);

CREATE TABLE "user_roles"
(
    user_id BIGINT NOT NULL,
    role    VARCHAR(255) DEFAULT NULL,
    CONSTRAINT FK_user FOREIGN KEY (user_id) REFERENCES "users" (id)
);

CREATE TABLE "character_episode"
(
    character_id INT NOT NULL,
    episode_id   INT NOT NULL,
    PRIMARY KEY (character_id, episode_id),
    CONSTRAINT FK_character_episode_character FOREIGN KEY (character_id) REFERENCES "characters" (id),
    CONSTRAINT FK_character_episode_episode FOREIGN KEY (episode_id) REFERENCES "episodes" (id)
);