-- Setup database
DROP DATABASE IF EXISTS preplane;
CREATE DATABASE preplane;
USE preplane;

-- Create all the tables
CREATE TABLE user (
    user_id INT AUTO_INCREMENT NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(200) NOT NULL,
    first_name VARCHAR(200) DEFAULT 'John',
    last_name VARCHAR(200) DEFAULT 'Doe',
    email_address VARCHAR(200) NOT NULL, 
    role ENUM('ROLE_ADMIN', 'ROLE_MAINTAINER', 'ROLE_USER') NOT NULL DEFAULT 'ROLE_USER',
    last_login DATETIME DEFAULT NOW(),
    avatar VARCHAR(300),
    PRIMARY KEY (user_id)
);

-- Create a default admin user
INSERT INTO user (username, password, first_name, last_name, email_address, role) VALUES (
    'admin', '$2a$10$MNpZkeA6OHRp5UlNpA7YvuEujgpom2Ob3mKj1QIxsnwRz9H8glBu6', 'Root', 'User', 'admin@preplane.dev', 'ROLE_ADMIN'
);

CREATE TABLE thread (
    thread_id INT AUTO_INCREMENT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content VARCHAR(2048) NOT NULL,
    created_at DATETIME DEFAULT NOW(),
    user_created INT NOT NULL,
    upvotes INT NOT NULL DEFAULT 0,
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
    compiler_verdict ENUM ('AC', 'WA', 'MLE', 'TLE', 'CE', 'IN_QUEUE') DEFAULT 'IN_QUEUE',
    code VARCHAR(4096) NOT NULL,
    execution_time DOUBLE,
    execution_memory DOUBLE,
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


-- Coding problems and tag data
INSERT INTO coding_problem (title, statement, author, authors_solution, testcases, time_limit, memory_limit)
VALUES
    ('Sum of Two Numbers', 'Write a program to add two numbers and print the result.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Factorial of a Number', 'Write a program to calculate the factorial of a number.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Palindrome Check', 'Write a program to check if a given word is a palindrome.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Maximum of Three Numbers', 'Write a program to find the maximum of three numbers.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Fibonacci Series', 'Write a program to generate the first 10 numbers in the Fibonacci series.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Prime Number Check', 'Write a program to check if a given number is prime.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Reverse a String', 'Write a program to reverse a given string.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Binary to Decimal', 'Write a program to convert a binary number to decimal.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Calculate Square Root', 'Write a program to calculate the square root of a number.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Factorial Using Recursion', 'Write a recursive program to calculate the factorial of a number.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Find Largest Element', 'Write a program to find the largest element in an array.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Calculate Exponent', 'Write a program to calculate the exponent of a number.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Matrix Multiplication', 'Write a program to multiply two matrices.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Count Vowels and Consonants', 'Write a program to count the number of vowels and consonants in a string.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Find Missing Number', 'Write a program to find the missing number in an array of consecutive integers.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Sort an Array', 'Write a program to sort an array using the bubble sort algorithm.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Linear Search', 'Write a program to perform a linear search in an array.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('String Concatenation', 'Write a program to concatenate two strings.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256),
    ('Calculate Square', 'Write a program to calculate the square of a number.', 1, 'Solution code here...', 'Test case data here...', 1.0, 256);

-- Insert tags and associate them with problems
INSERT INTO tags (tag_type, tag_name)
VALUES
    ('TOPIC', 'Math'),
    ('TOPIC', 'Strings'),
    ('TOPIC', 'Arrays'),
    ('DIFFICULTY', 'Easy'),
    ('DIFFICULTY', 'Medium'),
    ('DIFFICULTY', 'Hard'),
    ('COMPANY', 'Google'),
    ('COMPANY', 'Facebook'),
    ('COMPANY', 'Amazon'),
    ('COMPANY', 'Microsoft');

-- Associate tags with problems
INSERT INTO coding_tag (problem_id, tag_id)
VALUES
    (1, 1), (1, 4), (1, 7), -- Problem 1 associated with 'Math', 'Easy', 'Google'
    (2, 2), (2, 5), (2, 8), -- Problem 2 associated with 'Strings', 'Medium', 'Facebook'
    (3, 3), (3, 6), (3, 9), -- Problem 3 associated with 'Arrays', 'Hard', 'Amazon'
    (4, 1), (4, 4), (4, 7), -- Problem 4 associated with 'Math', 'Easy', 'Google'
    (5, 2), (5, 5), (5, 8), -- Problem 5 associated with 'Strings', 'Medium', 'Facebook'
    (6, 3), (6, 6), (6, 9), -- Problem 6 associated with 'Arrays', 'Hard', 'Amazon'
    (7, 1), (7, 4), (7, 7), -- Problem 7 associated with 'Math', 'Easy', 'Google'
    (8, 2), (8, 5), (8, 8), -- Problem 8 associated with 'Strings', 'Medium', 'Facebook'
    (9, 3), (9, 6), (9, 10), -- Problem 9 associated with 'Arrays', 'Hard', 'Microsoft'
    (10, 1), (10, 4), (10, 7), -- Problem 10 associated with 'Math', 'Easy', 'Google'
    (11, 2), (11, 5), (11, 8), -- Problem 11 associated with 'Strings', 'Medium', 'Facebook'
    (12, 3), (12, 6), (12, 9), -- Problem 12 associated with 'Arrays', 'Hard', 'Amazon'
    (13, 1), (13, 4), (13, 7), -- Problem 13 associated with 'Math', 'Easy', 'Google'
    (14, 2), (14, 5), (14, 8), -- Problem 14 associated with 'Strings', 'Medium', 'Facebook'
    (15, 3), (15, 6), (15, 9), -- Problem 15 associated with 'Arrays', 'Hard', 'Amazon'
    (16, 1), (16, 4), (16, 7), -- Problem 16 associated with 'Math', 'Easy', 'Google'
    (17, 2), (17, 5), (17, 8), -- Problem 17 associated with 'Strings', 'Medium', 'Facebook'
    (18, 3), (18, 6), (18, 9), -- Problem 18 associated with 'Arrays', 'Hard', 'Amazon'
    (19, 1), (19, 4), (19, 7); -- Problem 19 associated with 'Math', 'Easy', 'Google' 

-- Insert blogs for admin
INSERT INTO thread (thread_id, title, content, created_at, user_created)
VALUES
    (1, 'Thread Title 1', 'Content of thread 1', '2023-10-22 10:00:00', 1), 
    (2, 'Thread Title 2', 'Content of thread 2', '2023-10-22 10:30:00', 1),
    (3, 'Thread Title 3', 'Content of thread 3', '2023-10-22 11:00:00', 1),
    (4, 'Thread Title 4', 'Content of thread 4', '2023-10-22 11:30:00', 1),
    (5, 'Thread Title 5', 'Content of thread 5', '2023-10-22 12:00:00', 1);

-- Create a procedure to insert code submissions for a user
DELIMITER //
CREATE PROCEDURE InsertSubmissions()
BEGIN
    DECLARE i INT DEFAULT 0;
    
    WHILE i < 100 DO
        SET @problem_id = FLOOR(1 + (RAND() * 18));        
        SET @submission_time = NOW() - INTERVAL FLOOR(1 + (RAND() * 30)) DAY;
        SET @compiler_verdict = ELT(1 + FLOOR(RAND() * 6), 'AC', 'WA', 'MLE', 'TLE', 'CE', 'IN_QUEUE');
        SET @code = CONCAT('Sample code for problem ', @problem_id);
        SET @execution_time = RAND() * 5.0;
        SET @execution_memory = 32 + (RAND() * 480);

        -- Insert the submission into the coding_submission table
        INSERT INTO coding_submission (problem_id, user_id, submission_time, compiler_verdict, code, execution_time, execution_memory)
        VALUES (@problem_id, 1, @submission_time, @compiler_verdict, @code, @execution_time, @execution_memory);
        
        SET i = i + 1;
    END WHILE;
END;
//
DELIMITER ;

-- Call the stored procedure to insert submissions
CALL InsertSubmissions();

INSERT INTO comments (content, updated_at, user_id, thread_id) VALUES ('Comment 1', '2023-10-23 12:00:00', 1, 1);
INSERT INTO comments (content, updated_at, user_id, thread_id) VALUES ('Comment 2', '2023-10-23 12:15:00', 1, 1);
INSERT INTO comments (content, updated_at, user_id, thread_id) VALUES ('Comment 3', '2023-10-23 12:30:00', 1, 1);