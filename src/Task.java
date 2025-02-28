public class Task {
    private int id ;
    private String task;
    private boolean status;

    public Task(int id, String task,boolean status){
        this.id=id;
        this.task=task;
        this.status=status;
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
