import java.sql.*;
import java.util.LinkedList;

public class DataBaseContraller {
    String url = "jdbc:mysql://localhost:3306/todo";
    String name = "root";
    String pass = "132@@4";

    Connection connection;
    public DataBaseContraller()
    {
        try
        {
            connection = DriverManager.getConnection(url,name,pass);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    //select
    public LinkedList<Task> get(String mainQuery) throws SQLException {
        LinkedList<Task> data = new LinkedList<Task>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(mainQuery)){

            while (resultSet.next()){

                int id = resultSet.getInt("taskID");
                String taskdata = resultSet.getString("Task");
                boolean status = resultSet.getBoolean("status");

                data.add(new Task(id,taskdata,status));
            }
        }
        return data;
    }
    public LinkedList<Task> getAllTasks() throws SQLException {
        return get("SELECT * FROM tasks");
    }
    public LinkedList<Task> getDoneTasks() throws SQLException {
        return get("SELECT * FROM tasks where status = 1");
    }
    public  LinkedList<Task> getUnDoneTasks() throws SQLException {
        return get("SELECT * FROM tasks where status = 0");
    }
    public  LinkedList<Task> getByID(int id) throws SQLException {
        if (!doesIdExist(id)){
            System.out.println("invalid id");
            return null;
        }
        return get("SELECT * FROM tasks where taskID = "+ id);
    }

    //update
    public boolean updateStatus(int id) {
        if (doesIdExist(id)){
            String query = "UPDATE tasks SET status = NOT status WHERE taskID = " + id;

            try (Statement statement = connection.createStatement()) {
                int rowsUpdated = statement.executeUpdate(query);
                return rowsUpdated > 0; // Return true if at least one row was updated
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    //delete
    public boolean deleteTask(int id){
        if (doesIdExist(id)){
            String query = "DELETE FROM tasks WHERE taskID = " + id;

            try (Statement statement = connection.createStatement()) {
                int rowsUpdated = statement.executeUpdate(query);
                return rowsUpdated > 0; // Return true if at least one row was updated
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    //add
    public boolean addTask(String task){
        String query = "INSERT INTO tasks (Task, status) VALUES ('"+task+"', false)";

        try (Statement statement = connection.createStatement()) {
            int rowsUpdated = statement.executeUpdate(query);
            return rowsUpdated > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //id check
    public boolean doesIdExist(int id) {
        String query = "SELECT COUNT(*) FROM tasks WHERE taskID = " + id;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0){
                    System.out.println("== INVALID ID ==");
                }
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
