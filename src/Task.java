import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/*
Класс "Задача"
Переменная taskCount не считает текущее количество задач, а служит для правильного создания новых задач
 */
public class Task implements Serializable {
    static Map <Integer, Task> tasks = new HashMap<>();
    public static int taskCount = 0;
    private int taskID;
    private Project project;
    private String topic;
    private String type;
    private String priority;
    private User executor;
    private String description;

    public Task (Project project, String topic, String type, String priority, User executor, String description) {
        taskCount++;
        this.project = project;
        this.topic = topic;
        this.type = type;
        this.priority = priority;
        this.executor = executor;
        this.description = description;
        this.taskID = taskCount;
        tasks.put(taskCount, Task.this);
        project.addTask(Task.this);
        executor.addTask(Task.this);
    }
    public Project getProject() {
        return project;
    }
    public String getTopic() {
        return topic;
    }
    public Integer getID() {
        return taskID;
    }
}
