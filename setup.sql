-- Create the 'todo' database if it doesn't exist
CREATE DATABASE IF NOT EXISTS todo;

-- Use the 'todo' database
USE todo;

-- Create the 'tasks' table
CREATE TABLE IF NOT EXISTS tasks (
    taskID INT PRIMARY KEY AUTO_INCREMENT,  -- Task ID with auto-increment
    Task VARCHAR(255),                      -- Task description
    status BOOLEAN                          -- Task status (done or not)
);
