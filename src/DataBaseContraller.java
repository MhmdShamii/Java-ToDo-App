import java.sql.*;
import java.util.LinkedList;

public class DataBaseContraller {

    private String url = System.getenv("DB_TODO_URL");
    private String userName = System.getenv("DB_USERNAME");
    private String password = System.getenv("DB_PASSWORD");
    private Connection dbconnection;

    public DataBaseContraller() throws SQLException{
        try {
            this.dbconnection = DriverManager.getConnection(this.url,this.userName,this.password);
        }catch (SQLException e){
            throw e;
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
        String sql = "SELECT * FROM tasks";
        LinkedList<Task> tasks = new LinkedList<>();
        try (PreparedStatement pstmt = dbconnection.prepareStatement(sql);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String taskData = resultSet.getString(2);
                boolean status = resultSet.getBoolean(3);
                tasks.add(new Task(id,taskData,status));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks", e);
        }
        return tasks;
    }
    public LinkedList<Task> getStatus(boolean status) {
        String sql = "SELECT * FROM tasks WHERE STATUS = ?";
        LinkedList<Task> tasks = new LinkedList<>();
        try {
            PreparedStatement pstmt = dbconnection.prepareStatement(sql);
            pstmt.setInt(1,status?1:0);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String taskData = resultSet.getString(2);
                boolean taskStatus = resultSet.getBoolean(3);
                tasks.add(new Task(id,taskData,taskStatus));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving tasks", e);
        }
        return tasks;
    }
    public Task getTask(int id){
        String sql = "SELECT * FROM tasks WHERE taskID = ?";
        Task task = null;
        try {
            PreparedStatement pstmt = dbconnection.prepareStatement(sql);
            pstmt.setInt(1,id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int taskid = resultSet.getInt(1);
                    String taskData = resultSet.getString(2);
                    boolean status = resultSet.getBoolean(3);
                    task = new Task(taskid,taskData,status);
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
    public boolean updateStatus(int id){
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
}

