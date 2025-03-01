import java.util.LinkedList;

public class Display {
    public Display(){

    }
    public void displayOptions(){
        System.out.println("+---+----------------------------+");
        System.out.println("| - |          Option            |");
        System.out.println("+---+----------------------------+");
        System.out.println("| 1 | Add Task                   |");
        System.out.println("| 2 | Toggle Status              |");
        System.out.println("| 3 | Update Task Description    |");
        System.out.println("| 4 | List All Tasks             |");
        System.out.println("| 5 | List Done tasks            |");
        System.out.println("| 6 | List Un Done tasks         |");
        System.out.println("| 7 | View Specific task         |");
        System.out.println("| 8 | Delete Task                |");
        System.out.println("| 9 | Reset Table                |");
        System.out.println("| 0 | Exit                       |");
        System.out.println("+---+----------------------------+");
    }
    public void printTable(LinkedList<Task> tasks){
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
    public void printRow(Task task){
        int id = task.getId();
        String taskData = validStringLength(task.getTask());
        String status = task.getStatus() ? "Done" : "UnDone";
        System.out.println("+----+--------------------------------+--------+");
        System.out.printf("| %-2d | %-30s | %-6s | \n",id,taskData,status);
    }
    public String validStringLength(String taskData){
        int lengthLimit = 25;
        return (taskData.length() > lengthLimit)?taskData.substring(0,lengthLimit)+"...":taskData;
    }
    public String formatInput(String taskData){
        String finalString = taskData.trim().replaceAll("\\s+"," ");
        if (taskData.isEmpty() || finalString.isEmpty()){return null;}
        return finalString;
    }
    public void specificTask(Task task){
        String boarder = "";
        int length = Math.max(task.getTask().length() + 8, 28);
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
