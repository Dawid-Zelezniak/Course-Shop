
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS students (
    student_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    date_created DATETIME,
    CONSTRAINT UK_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS course_authors (
    author_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    date_created DATETIME,
    CONSTRAINT UK_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS courses (
    course_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255),
    date_created DATETIME,
    description VARCHAR(255),
    price FLOAT(53),
    title VARCHAR(255) UNIQUE,
    course_author_author_id BIGINT,
    CONSTRAINT FK_course_author FOREIGN KEY (course_author_author_id) REFERENCES course_authors(author_id)
);

CREATE TABLE IF NOT EXISTS authors_roles (
    author_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (author_id, role_id),
    FOREIGN KEY (author_id) REFERENCES course_authors (author_id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS created_by_author (
    author_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (author_id, course_id),
    FOREIGN KEY (author_id) REFERENCES course_authors (author_id),
    FOREIGN KEY (course_id) REFERENCES courses (course_id)
);

CREATE TABLE IF NOT EXISTS enrolled_authors (
    course_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, author_id),
    FOREIGN KEY (course_id) REFERENCES courses (course_id),
    FOREIGN KEY (author_id) REFERENCES course_authors (author_id)
);

CREATE TABLE IF NOT EXISTS enrolled_students (
    course_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES courses (course_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE IF NOT EXISTS orders (
    order_id VARCHAR(255) NOT NULL PRIMARY KEY,
    date_created DATETIME,
    total_price FLOAT(53),
    course_id BIGINT,
    FOREIGN KEY (course_id) REFERENCES courses (course_id)
);

CREATE TABLE IF NOT EXISTS students_orders (
    order_id VARCHAR(255) NOT NULL,
    student_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, student_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id)
);

CREATE TABLE IF NOT EXISTS students_roles (
    student_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, role_id),
    FOREIGN KEY (student_id) REFERENCES students (student_id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS authors_orders (
    order_id VARCHAR(255) NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (order_id, author_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (author_id) REFERENCES course_authors (author_id)
);


