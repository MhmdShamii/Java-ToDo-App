# Java ToDo App

A simple ToDo application built in Java that allows users to add, manage, and update tasks with a persistent MySQL database.

## Features
- **Add Tasks**: Allows users to add new tasks to the ToDo list.
- **Update Task Status**: Users can toggle the status of tasks between "done" and "undone."
- **View All Tasks**: Users can view all tasks with their status (done or not done).
- **Delete Tasks**: Users can remove tasks from the list.

## Technologies Used
- **Java**: Main programming language used for the application.
- **JDBC**: Java Database Connectivity for interacting with the MySQL database.
- **MySQL**: Relational database for storing tasks.

## Setup

### Prerequisites

Before running the application, make sure you have the following installed:

- **Java 8 or later**
- **MySQL**: To store and manage tasks.

### Steps to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/MhmdShamii/Java-ToDo-App.git
2. **Database:** use setup.sql file to setup your databse
3. ** Make sure to change :**
inside the DataBaseContraller.java class
   ```bash
    String url = "jdbc:mysql://localhost:3306/todo";
    String name = "your MySql username";
    String pass = "your MySql password";

  
