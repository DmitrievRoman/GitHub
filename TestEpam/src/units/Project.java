package units;

import java.util.ArrayList;
import storage.StorageProject;
/*
Класс "Проект"
Переменная projectCount не считает текущее количество проектов, а служит для правильного создания новых проектов
 */
public class Project extends Unit {
    private ArrayList<Task> projectTasks = new ArrayList<>();
    private String name;
    public static int projectCount = 0;
    private int projectID;

    public Project (String name, StorageProject storageProject) {
        projectCount++;
        this.name = name;
        this.projectID = projectCount;
        storageProject.add(projectCount, Project.this);
    }

    public String getName() {
        return name;
    }
    public Integer getID() {
        return projectID;
    }
    public void addTask(Task task) {
        projectTasks.add(task);
    }
    public ArrayList<Task> getProjectTasks() {
        return projectTasks;
    }
    //Метод удаляет задачи проекта у всех пользователей, а затем очищает список задач проекта
    //Необходимо вызывать при удалении проекта
//    public void deleteAllTasks() {
//        for (Map.Entry<Integer, User> pair : User.users.entrySet()) {
//            User user = pair.getValue();
//            for (int i = 0; i < projectTasks.size(); i++) {
//                user.deleteTask(projectTasks.get(i));
//            }
//        }
//        projectTasks.clear();
//    }
    public void deleteTask(Task task) {
        this.projectTasks.remove(task);
    }
}
