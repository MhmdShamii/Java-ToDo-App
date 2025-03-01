# Task Management System

## Description
This is a simple **Task Management System** application that allows users to perform CRUD operations (Create, Read, Update, Delete) on tasks stored in a MySQL database. The application uses Java for the backend, connecting to a MySQL database to manage tasks. It offers a simple console interface to interact with the system, allowing users to add tasks, view all tasks, update task status, delete tasks, and more.

## Technologies Used
- **Java**: The core language used to implement the application.
- **MySQL**: Used for database management, storing tasks and their statuses.
- **JDBC**: Java Database Connectivity is used for interaction between the Java application and the MySQL database.
- **Prepared Statements**: Used to prevent SQL injection by safely embedding variables in SQL queries.

## Features
- **Add Tasks**: Allows users to add new tasks to the database.
- **Update Task Status**: Allows users to toggle the status of tasks between done and undone.
- **Update Task Description**: Allows the user to edit a specific task Description.
- **View Tasks**: View all tasks or filter them by their status (done/undone).
- **Delete Tasks**: Allows users to delete tasks by their ID.
- **Reset Database**: Reseet the database records.
- **View Specific Task**: View details of a task by providing its ID.

## Prerequisites
To run this code on your local machine, make sure you have the following installed:

- **Java** (JDK 8 or higher)
- **MySQL Database** with a `tasks` table set up.
- **JDBC Driver for MySQL** (`mysql-connector-java`)

### Setting up MySQL Database:
- Use the setup.sql file to setup ur database
- set ur environment variables containing :
- ## Setting Up the Database Credentials

  - After cloning the repository, you'll need to configure the database connection by setting up a `.env` file with your database credentials.

### Steps to Set Up:
- **Copy the Example File**:
  Copy the `DataBase_Credentials.env.example` file to a new file called `DataBase_Credentials.env` in the root of the project following the structure provided inside the `.example` folder replace the placeholders with your info.



