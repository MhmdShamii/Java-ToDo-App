import java.util.Scanner;
import java.sql.*;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        DataBaseContraller dbconnection = new DataBaseContraller();
        System.out.println("Welcome to My ToDo list ");
        while (true){
            System.out.println("Commands :");
            System.out.println("1 - Display All Tasks");
            System.out.println("2 - Display Done Tasks");
            System.out.println("3 - Display Un-Done Tasks");
            System.out.println("4 - Update Status Of A Task");
            System.out.println("5 - Delete Task");
            System.out.println("6 - Add Task");
            System.out.println("7 - view specific task ");
            System.out.println("8 - Exit");

            System.out.print("Enter the desired command : ");
            int option = scan.nextInt();
            if (option == 1)
            {
                try {
                    printToDO(dbconnection.getAllTasks());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (option == 2)
            {
                try {
                    printToDO(dbconnection.getDoneTasks());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (option == 3)
            {
                try {
                    printToDO(dbconnection.getUnDoneTasks());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (option == 4)
            {
                System.out.print("Task ID : ");
                int id = scan.nextInt();
                if (dbconnection.updateStatus(id))
                {
                    System.out.println("Successfully update");
                }
                else
                {
                    System.out.println("fail to update");
                }
            }

            if (option == 5)
            {
                System.out.print("Task ID : ");
                int id = scan.nextInt();
                if (dbconnection.deleteTask(id))
                {
                    System.out.println("Successfully delete");
                }
                else
                {
                    System.out.println("fail to Delete");
                }
            }

            if (option == 6)
            {
                System.out.print("Task Description : ");
                scan.nextLine();
                String task = scan.nextLine();
                if (dbconnection.addTask(task))
                {
                    System.out.println("Successfully added");
                }
                else
                {
                    System.out.println("fail to add");
                }
            }

            if (option == 7){
                System.out.print("Task ID : ");
                int id = scan.nextInt();
                if (dbconnection.getByID(id) != null){
                    specificTask(dbconnection.getByID(id));
                }
            }
            if (option == 8)
            {
                System.out.println("Exiting ...");
                break;
            }
            if (option > 8){
                System.out.println("== INVALID OPTION ==");
            }
        }
    }

    public static void printToDO(LinkedList<Task> TaskList) throws SQLException {
        System.out.println("+------+--------------------------------+------------+");
        System.out.printf("| %-4s | %-30s | %-10s |%n", "ID", "Task", "Status");
        System.out.println("+------+--------------------------------+------------+");

        if (TaskList.isEmpty())
        {
            System.out.printf("| %-4s | %-30s | %-10s |%n", "--", "No Tasks To Display", "--");
            System.out.println("+------+--------------------------------+------------+");
        }
        for (Task task : TaskList) {
            printRow(task.getId(),task.getTask(),task.getStatus());
        }
    }
    public static void printRow(int id , String task ,boolean status){
        String finaltask = "";
        if (task.length() > 25){
            finaltask = task.substring(0,26) + "...";
        }else{
            finaltask = task;
        }
        System.out.printf("| %-4s | %-30s | %-10s |%n", id, finaltask, (status ? "Done" : "Not Done"));
        System.out.println("+------+--------------------------------+------------+");
    }
    public static void specificTask(LinkedList<Task> task){
        System.out.println();
        System.out.println("===========================================================");
        System.out.println("ID : " + task.getFirst().getId());
        System.out.println("Task : " + task.getFirst().getTask());
        System.out.println("Status : " + task.getFirst().getStatus());
        System.out.println("===========================================================");
        System.out.println();
        
    }
}
