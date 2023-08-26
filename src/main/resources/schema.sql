-- Setup database
DROP DATABASE IF EXISTS preplane;
CREATE DATABASE preplane;
USE preplane;

-- Create all the tables
CREATE TABLE user (
    user_id INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    name VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL, 
    premium BOOL DEFAULT FALSE,
    last_login DATETIME DEFAULT NOW(),
    avatar VARCHAR(300),
    PRIMARY KEY (user_id)
);

CREATE TABLE thread (
    thread_id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(2048) NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    user_created INT NOT NULL,
    PRIMARY KEY (thread_id),
    FOREIGN KEY (user_created) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE comments (
    comment_id INT AUTO_INCREMENT NOT NULL,
    content VARCHAR(2048) NOT NULL,
    updated_at DATETIME DEFAULT NOW(),
    user_id INT NOT NULL,
    thread_id INT NOT NULL,
    PRIMARY KEY (comment_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (thread_id) REFERENCES thread(thread_id) ON DELETE CASCADE
);

CREATE TABLE tags (
    tag_id INT AUTO_INCREMENT NOT NULL,
    tag_type ENUM('TOPIC', 'DIFFICULTY', 'COMPANY') NOT NULL DEFAULT 'TOPIC',
    tag_name VARCHAR(128) NOT NULL,
    PRIMARY KEY (tag_id)
);


CREATE TABLE coding_problem (
    problem_id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(300) NOT NULL,
    statement VARCHAR(4096) NOT NULL,
    author INT NOT NULL,
    authors_solution VARCHAR(4096) NOT NULL,
    testcases VARCHAR(4096) NOT NULL,
    time_limit  DOUBLE NOT NULL,
    memory_limit  DOUBLE NOT NULL,
    PRIMARY KEY (problem_id),
    FOREIGN KEY (author) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE coding_tag (
    problem_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES coding_problem(problem_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

CREATE TABLE subjective_problem (
    problem_id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(300) NOT NULL,
    statement VARCHAR(4096) NOT NULL,
    explnation VARCHAR(4096) NOT NULL,
    author INT NOT NULL,
    PRIMARY KEY (problem_id),
    FOREIGN KEY (author) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE subjective_tag (
    problem_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES subjective_problem(problem_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

CREATE TABLE mcq_options (
    option_id INT AUTO_INCREMENT NOT NULL,
    content VARCHAR(1024) NOT NULL, 
    problem_id INT NOT NULL,
    PRIMARY KEY (option_id)
);

CREATE TABLE mcq_problem (
    problem_id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(300) NOT NULL,
    statement VARCHAR(4096) NOT NULL,
    explanation VARCHAR(4096) NOT NULL,
    author INT NOT NULL,
    correct_option INT,
    PRIMARY KEY (problem_id),
    FOREIGN KEY (author) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (correct_option) REFERENCES mcq_options(option_id) ON DELETE CASCADE
);

ALTER TABLE mcq_options
ADD CONSTRAINT foreign_key_relation_for_parent_problem
FOREIGN KEY (problem_id) REFERENCES mcq_problem(problem_id);

CREATE TABLE mcq_tag (
    problem_id INT NOT NULL,
    tag_id INT NOT NULL,
    FOREIGN KEY (problem_id) REFERENCES mcq_problem(problem_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE
);

CREATE TABLE coding_submission (
    submission_id INT AUTO_INCREMENT NOT NULL,
    problem_id INT NOT NULL, 
    user_id INT NOT NULL,
    submission_time DATETIME DEFAULT NOW(),
    compiler_verdict ENUM ('AC', 'WA', 'MLE', 'TLE', 'CE') DEFAULT 'WA',
    code VARCHAR(4096) NOT NULL,
    execution_time  DOUBLE NOT NULL,
    execution_memory  DOUBLE NOT NULL,
    PRIMARY KEY (submission_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (problem_id) REFERENCES coding_problem(problem_id) ON DELETE CASCADE
);

CREATE TABLE mcq_submission (
    submission_id INT AUTO_INCREMENT NOT NULL,
    problem_id INT NOT NULL, 
    user_id INT NOT NULL,
    submission_time DATETIME DEFAULT NOW(),
    verdict ENUM ('AC', 'WA') DEFAULT 'WA',
    chosen_option INT NOT NULL,
    PRIMARY KEY (submission_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (problem_id) REFERENCES mcq_problem(problem_id) ON DELETE CASCADE,
    FOREIGN KEY (chosen_option) REFERENCES mcq_options(option_id)
);