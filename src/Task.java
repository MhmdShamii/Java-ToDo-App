public class Task {
    private final int id ;
    private final String task;
    private final boolean status;

    public Task(int id, String taskDescription,boolean status){
        this.id = id;
        this.task = taskDescription;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getTask() {
        return task;
    }
    public boolean getStatus() {
        return status;
    }
}
