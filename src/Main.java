import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Display display = new Display();
        DataBaseController dbController = null;

        try {
            dbController = new DataBaseController();
        } catch (SQLException e) {
            System.out.println("Data base connection Error :" + e.getMessage());
            System.exit(1);
        }

        while (true)
        {
            display.displayOptions();

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
                    String formated = display.formatInput(task);
                    if ( formated != null && dbController.addTask(formated)){
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
                    if (!dbController.toggleStatus(idUpdate)){
                        System.out.println("Id doesn't exist");
                    }else{
                        System.out.println("Status Changed Successfully");
                    }
                    break;
                case 3:
                    System.out.print("Are you sure you want to update task description ? (y/n): ");
                    scan.nextLine();
                    String inputUpdateNewTask = scan.nextLine().trim().toLowerCase();

                    if (!inputUpdateNewTask.equals("y")) {
                        System.out.println("Operation cancelled. The table was not reset.");
                    }else {
                        int idNewData;
                        String newDescription;
                        try {
                            System.out.print("Enter Task ID : ");
                            idNewData = scan.nextInt();
                            System.out.println();
                            System.out.print("Enter New Task Description : ");
                            scan.nextLine();
                            newDescription = scan.nextLine();
                        }catch (InputMismatchException e) {
                            System.out.println("Invalid Input");
                            continue;
                        }
                        if (display.formatInput(newDescription) != null){
                            if (!dbController.updateTaskDescription(idNewData,display.formatInput(newDescription))){
                                System.out.println("Id doesn't exist");
                            }else {
                                System.out.println("Updated Successfully");
                            }
                        }else {
                            System.out.println("invalid new data");
                        }
                    }
                    break;
                case 4 :
                    display.printTable(dbController.getAll());
                    break;
                case 5 :
                    display.printTable(dbController.getByStatus(true));
                    break;
                case 6 :
                    display.printTable(dbController.getByStatus(false));
                    break;
                case 7 :
                    System.out.print("Enter Task ID : ");
                    int idSpecific;
                    try {
                        idSpecific = scan.nextInt();
                    }catch (InputMismatchException e) {
                        System.out.println("Invalid Input");
                        scan.next();
                        continue;
                    }
                    Task Stask = dbController.getTask(idSpecific);
                    if (Stask == null){
                        System.out.println("invalid ID");
                    }else {
                        display.specificTask(Stask);
                    }
                    break;
                case 8 :
                    System.out.print("Are you sure you want to Delete the tasks ? (y/n): ");
                    scan.nextLine();
                    String inputDelete = scan.nextLine().trim().toLowerCase();

                    if (!inputDelete.equals("y")) {
                        System.out.println("Operation cancelled. The table was not reset.");
                    }else {
                        System.out.print("Enter Task ID : ");
                        int idDelete;
                        try {
                            idDelete = scan.nextInt();
                        }catch (InputMismatchException e) {
                            System.out.println("Invalid Input");
                            scan.next();
                            continue;
                        }
                        if (!dbController.deleteTask(idDelete)){
                            System.out.println("Id doesn't exist");
                        }else {
                            System.out.println("Deleted Successfully");
                        }
                    }
                    break;
                case 9 :
                    System.out.print("Are you sure you want to reset the tasks table? (y/n): ");
                    scan.nextLine();
                    String inputReset = scan.nextLine().trim().toLowerCase();

                    if (!inputReset.equals("y")) {
                        System.out.println("Operation cancelled. The table was not reset.");
                    }else {
                        if (dbController.resetTodoList()){
                            System.out.println("list reset successfully");
                        }else{
                            System.out.println("Error cant be rested");
                        }
                    }
                    break;
                case 0 :
                    dbController.closeConnection();
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("invalid Option number");
                    break;
            }
        }
    }
}
