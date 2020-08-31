package units;

import java.util.ArrayList;
import storage.StorageUser;
/*
Класс "Пользователь"
Переменная userCount не считает текущее количество пользователей, а служит для правильного создания новых пользователей
 */



public class User extends Unit {
    private ArrayList<Task> userTasks = new ArrayList<>();
    private String name;
    public static int userCount = 0;
    private int userID;
    public User (String name, StorageUser storageUser) {
        userCount++;
        this.name = name;
        this.userID = userCount;
        storageUser.add(userID, User.this);
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
    //    public void deleteAllTasks() {
//        for (Map.Entry<Integer, Project> pair : Project.projects.entrySet()) {
//            Project project = pair.getValue();
//            ArrayList<Task> tasks = project.getProjectTasks();
//            tasks.removeAll(userTasks);
//        }
//        userTasks.clear();
//    }
    public void deleteTask(Task task) {
        userTasks.remove(task);
    }
}
