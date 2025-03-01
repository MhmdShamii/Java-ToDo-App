import java.sql.*;
import java.util.LinkedList;
import java.util.Map;

public class DataBaseController {

    private final Connection dbconnection;

    Map<String,String> env = EnvLoader.loadEnv();

    public DataBaseController() throws SQLException{
        try {
            String url = env.get("DB_TODO_URL");
            String userName = env.get("DB_USERNAME");
            String password = env.get("DB_PASSWORD");
            this.dbconnection = DriverManager.getConnection(url, userName, password);
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    //Create
    public boolean addTask(String task){
        String sql ="INSERT INTO tasks(task,status) values ( ? ,false)";
        try{
            PreparedStatement statement = dbconnection.prepareStatement(sql);
            statement.setString(1,task);

            int result = statement.executeUpdate();
            return result > 0;

        }catch (SQLException e){
            throw new RuntimeException("Error adding task" + e);
        }
    }
    //Read
    public LinkedList<Task> getAll() {
        try {
            String sql = "SELECT * FROM tasks";
            PreparedStatement preparedStmt = dbconnection.prepareStatement(sql);
            ResultSet resultSet = preparedStmt.executeQuery();
            return fillLinkedList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks", e);
        }
    }
    public LinkedList<Task> getByStatus(boolean status) {
        String sql = "SELECT * FROM tasks WHERE STATUS = ?";
        try {
            PreparedStatement preparedStmt = dbconnection.prepareStatement(sql);
            preparedStmt.setInt(1,status?1:0);
            ResultSet resultSet = preparedStmt.executeQuery();

            return fillLinkedList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks", e);
        }
    }
    public Task getTask(int id){
        String sql = "SELECT * FROM tasks WHERE taskID = ?";
        Task task;
        try {
            PreparedStatement preparedStmt = dbconnection.prepareStatement(sql);
            preparedStmt.setInt(1,id);
            try (ResultSet resultSet = preparedStmt.executeQuery()) {
                if (resultSet.next()) {
                    int taskId = resultSet.getInt("taskID");
                    String taskData = resultSet.getString("task");
                    boolean status = resultSet.getBoolean("status");
                    task = new Task(taskId,taskData,status);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks", e);
        }
        return task;
    }
    //Update
    public boolean toggleStatus(int id){
        String sql ="UPDATE tasks SET STATUS = NOT STATUS WHERE taskID = ?";
        try{
            PreparedStatement statement = dbconnection.prepareStatement(sql);
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            return result > 0;

        }catch (SQLException e){
            throw new RuntimeException("Error Updating task" + e);
        }
    }
    public boolean resetTodoList(){

        try {
            // Delete all rows from the table
            String deleteSQL = "DELETE FROM tasks";
            PreparedStatement deleteStmt = dbconnection.prepareStatement(deleteSQL);
            int deleteResult = deleteStmt.executeUpdate();
            deleteStmt.close();

            // Reset the auto-increment counter
            String resetSQL = "ALTER TABLE tasks AUTO_INCREMENT = 1";
            PreparedStatement resetStmt = dbconnection.prepareStatement(resetSQL);
            resetStmt.executeUpdate();
            resetStmt.close();

            return deleteResult > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to reset database: " + e.getMessage());
        }
    }
    public boolean updateTaskDescription(int id, String newDescription) {
        try {
            String sqlSearch = "SELECT * FROM tasks WHERE taskID = ?";
            PreparedStatement preparedStatementSearch = dbconnection.prepareStatement(sqlSearch);
            preparedStatementSearch.setInt(1, id);
            ResultSet resultSet = preparedStatementSearch.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Task with ID " + id + " does not exist.");
                return false;
            }

            String sqlUpdate = "UPDATE tasks SET task = ? WHERE taskID = ?";
            PreparedStatement preparedStatementUpdate = dbconnection.prepareStatement(sqlUpdate);
            preparedStatementUpdate.setString(1, newDescription);
            preparedStatementUpdate.setInt(2, id);
            int rowsUpdated = preparedStatementUpdate.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update Task: " + e.getMessage());
        }
    }


    //Delete
    public boolean deleteTask(int id){
        String sql ="DELETE FROM tasks WHERE taskID = ?";
        try{
            PreparedStatement statement = dbconnection.prepareStatement(sql);
            statement.setInt(1,id);

            int result = statement.executeUpdate();
            return result > 0;

        }catch (SQLException e){
            throw new RuntimeException("Error Deleting task" + e);
        }
    }
    //close connection
    public void closeConnection() {
        try {
            if (dbconnection != null && !dbconnection.isClosed()) {
                dbconnection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
        }
    }

    public LinkedList<Task> fillLinkedList(ResultSet resultSet){
        try {
            LinkedList<Task> taskList = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String taskData = resultSet.getString(2);
                boolean status = resultSet.getBoolean(3);
                taskList.add(new Task(id,taskData,status));
            }
            return taskList;
        }catch (SQLException e){
            throw new RuntimeException("cant fill ArrayList");
        }
    }
}

