import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
Класс "Пользователь"
Переменная userCount не считает текущее количество пользователей, а служит для правильного создания новых пользователей
 */

public class User implements Serializable {
    static Map<Integer, User> users = new HashMap<>();
    private ArrayList<Task> userTasks = new ArrayList<>();
    private String name;
    public static int userCount = 0;
    private int userID;
    public User (String name) {
        userCount++;
        this.name = name;
        this.userID = userCount;
        users.put(userCount, User.this);

    }

    public String getName() {
        return name;
    }
    public Integer getID() {
        return userID;
    }

    public void addTask(Task task) {
        userTasks.add(task);
    }
    public ArrayList<Task> getUserTasks() {
        return userTasks;
    }
    public void deleteAllTasks() {
        for (Map.Entry<Integer, Project> pair : Project.projects.entrySet()) {
            Project project = pair.getValue();
            ArrayList<Task> tasks = project.getProjectTasks();
            tasks.removeAll(userTasks);
        }
        userTasks.clear();
    }
    public void deleteTask(Task task) {
        userTasks.remove(task);
    }
}
