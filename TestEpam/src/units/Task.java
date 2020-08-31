package units;

import storage.StorageTask;
/*
Класс "Задача"
Переменная taskCount не считает текущее количество задач, а служит для правильного создания новых задач
 */
public class Task extends Unit {
    public static int taskCount = 0;
    private int taskID;
    private Project project;
    private String topic;
    private String type;
    private String priority;
    private User executor;
    private String description;

    public Task (Project project, String topic, String type, String priority, User executor, String description, StorageTask storageTask) {
        taskCount++;
        this.project = project;
        this.topic = topic;
        this.type = type;
        this.priority = priority;
        this.executor = executor;
        this.description = description;
        this.taskID = taskCount;
        storageTask.add(taskCount, Task.this);
        project.addTask(Task.this);
        executor.addTask(Task.this);
    }
    public String getType() {
        return type;
    }
    public String getPriority() {
        return priority;
    }
    public String getDescription() {
        return description;
    }
    public Project getProject() {
        return project;
    }
    public String getName() {
        return topic;
    }
    public Integer getID() {
        return taskID;
    }
    public User getExecutor() {
        return executor;
    }
}
