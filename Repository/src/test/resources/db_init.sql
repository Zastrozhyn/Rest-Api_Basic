create table tag
(
    id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(75) NOT NULL UNIQUE
);

create table gift_certificate
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(75)  NOT NULL,
    description      TINYTEXT(255) NOT NULL,
    price            DECIMAL(5,2) UNSIGNED NOT NULL,
    create_date      TIMESTAMP NOT NULL,
    last_update_date TIMESTAMP NOT NULL,
    duration         TINYINT UNSIGNED NOT NULL
);

create table tag_certificate
(
    certificate_id BIGINT UNSIGNED NOT NULL,
    tag_id              BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (ID) ON DELETE CASCADE
);