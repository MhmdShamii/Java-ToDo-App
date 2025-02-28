import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        DataBaseContraller dbContraller = null;

        try {
            dbContraller = new DataBaseContraller();
        } catch (SQLException e) {
            System.out.println("Data base connection Error :" + e.getMessage());
            System.exit(1);
        }

        while (true)
        {
            displayOptions();

            System.out.print("Enter Option Number : ");
            int option;
            try {
                option = scan.nextInt();
            }catch (InputMismatchException e) {
                System.out.println("Invalid Input");
                scan.next();
                continue;
            }
            switch (option){
                case 1 :
                    System.out.print("Enter New Task : ");
                    scan.nextLine();
                    String task = scan.nextLine();
                    String formated = formatInput(task);
                    if ( formated != null && dbContraller.addTask(formated)){
                        System.out.println("Added Successfully");
                    }else{
                        System.out.println("Invalid input");
                    }
                    break;
                case 2 :
                    System.out.print("Enter Task ID : ");
                    int idUpdate;
                    try {
                        idUpdate = scan.nextInt();
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid Input");
                        scan.next();
                        continue;
                    }
                    if (!dbContraller.updateStatus(idUpdate)){
                        System.out.println("Id doesn't exist");
                    }else{
                        System.out.println("Updated Successfully");
                    }
                    break;
                case 3 :
                    printTable(dbContraller.getAll());
                    break;
                case 4 :
                    printTable(dbContraller.getStatus(true));
                    break;
                case 5 :
                    printTable(dbContraller.getStatus(false));
                    break;
                case 6 :
                    System.out.print("Enter Task ID : ");
                    int idSpecific;
                    try {
                        idSpecific = scan.nextInt();
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid Input");
                        scan.next();
                        continue;
                    }
                    Task Stask = dbContraller.getTask(idSpecific);
                    if (Stask == null){
                        System.out.println("invalid ID");
                    }else {
                        specificTask(Stask);
                    }
                    break;
                case 7 :
                    System.out.print("Enter Task ID : ");
                    int idDelete;
                    try {
                        idDelete = scan.nextInt();
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid Input");
                        scan.next();
                        continue;
                    }
                    if (!dbContraller.deleteTask(idDelete)){
                        System.out.println("Id doesn't exist");
                    }else {
                        System.out.println("Deleted Successfully");
                    }
                    break;
                case 8 :
                    dbContraller.closeConnection();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("invalid Option number");
                    break;
            }
        }
    }

    public static void displayOptions(){
        System.out.println("+---+----------------------------+");
        System.out.println("| - |          Option            |");
        System.out.println("+---+----------------------------+");
        System.out.println("| 1 | Add Task                   |");
        System.out.println("| 2 | Update Status              |");
        System.out.println("| 3 | List All Tasks             |");
        System.out.println("| 4 | List Done tasks            |");
        System.out.println("| 5 | List Un Done tasks         |");
        System.out.println("| 6 | View Specific task         |");
        System.out.println("| 7 | Delete Task                |");
        System.out.println("| 8 | Exit                       |");
        System.out.println("+---+----------------------------+");
    }
    public static void printTable(LinkedList<Task> tasks){
        System.out.println("+----+--------------------------------+--------+");
        System.out.printf("| %s | %-30s | %-6s | \n","ID","Task","Status");
        if (tasks.isEmpty()){
            System.out.println("+----+--------------------------------+--------+");
            System.out.printf("| %s | %-30s | %-6s | \n","--","========== No Tasks ==========","------");
        }else{
            for (Task task : tasks){
                printRow(task);
            }
        }
        System.out.println("+----+--------------------------------+--------+");
    }
    public static void printRow(Task task){
        int id = task.getId();
        String taskData = validStringLength(task.getTask());
        String status = task.getStatus() ? "Done" : "UnDone";
        System.out.println("+----+--------------------------------+--------+");
        System.out.printf("| %-2d | %-30s | %-6s | \n",id,taskData,status);
    }
    public static String validStringLength(String taskData){
        int lengthLimit = 25;
        return (taskData.length() > lengthLimit)?taskData.substring(0,lengthLimit)+"...":taskData;
    }
    public static String formatInput(String taskData){
        String finalString = taskData.trim().replaceAll("\\s+"," ");
        if (taskData.isEmpty() || finalString.isEmpty()){return null;}
        return finalString;
    }
    public static void specificTask(Task task){
        String boarder = "";
        int length = task.getTask().length() + 8;
        if (length < 28){
            length = 28;
        }
        for (int i = 0 ; i < length;i++){
            boarder += "=";
        }
        System.out.println(boarder);
        System.out.println("Task Of ID : " + task.getId());
        System.out.println("Task : " + task.getTask());
        System.out.println("Status of the task : " + (task.getStatus()?"Done":"UnDone"));
        System.out.println(boarder);
    }
}
