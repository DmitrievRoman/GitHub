package storage;

import units.Project;
import units.Task;
import units.Unit;
import units.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StorageTask extends Storage {
    Map<Integer, Task> tasks = new HashMap<>();
    public void add(int id, Task task) {
        tasks.put(id, task);
    }
    public void delete(int id, StorageUser storageUser, StorageProject storageProject) {
        for (Map.Entry<Integer, Task> pair : tasks.entrySet()) {
            if(pair.getKey() != id) {
                System.out.println("Задачи с таким ID не существует");
                break;
            }
        }
        Task task = tasks.get(id);
        for (Map.Entry<Integer, Project> pair : storageProject.getStorage().entrySet()) {
            Project project = pair.getValue();
            project.deleteTask(task);
        }
        for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
            User user = pair.getValue();
            user.deleteTask(task);
        }
        tasks.remove(id);
    }
    public Map<Integer, Task> getStorage() {
        return tasks;
    }
    public Map getList() {
        return tasks;
    }
    public Task create(String topic, String type, String priority,  String description, int projectID, int userID, StorageProject storageProject, StorageUser storageUser) {
        for (Map.Entry<Integer, Project> pair : storageProject.getStorage().entrySet()) {
            if (pair.getKey() != projectID) {
                System.out.println("Проекта с таким ID не существует");
                System.out.println("Задача не создана!");
                break;
            }
        }
        for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
            if (pair.getKey() != userID) {
                System.out.println("Пользователя с таким ID не существует");
                System.out.println("Задача не создана!");
                break;
            }
        }
        return new Task(storageProject.getStorage().get(projectID),topic, type, priority, storageUser.getStorage().get(userID), description, this);
    }

    @Override
    public void delete(int id) {
            tasks.remove(id);
    }

    //Это метод базового класса не работает, надо вызывать getAllTask
    //Потому что метод выводит на экран специфичные для этого класса поля (getProject..)
    public void getAll(Map<Integer, Unit> storage) {
        //сообщение для отладки
        System.out.println("Вызван неправильный метод потомка класса storage");
    }
    //Данный метод выводит на экран все задачи назначеные на конкретного пользователя
    public void getAllTasksForUser (int id, StorageUser storageUser) {
        User user = storageUser.getStorage().get(id);
        int number = 0;
        for (int i = 0; i < user.getUserTasks().size(); i++) {
            ArrayList<Task> userTasks = user.getUserTasks();
            number++;
            System.out.println(number + "." + userTasks.get(i).getName() + " ID: " + userTasks.get(i).getID());
            System.out.println("-Проект: " + userTasks.get(i).getProject().getName());
            System.out.println("-Тип: " + userTasks.get(i).getType());
            System.out.println("-Приоритет: " + userTasks.get(i).getPriority());
            System.out.println("-Описание: " + userTasks.get(i).getDescription());
        }
    }
    //Данный метод выводит на экран задачи для конкретного проекта
    public void getAllTasksInProject (int id, StorageProject storageProject) {
        Project project = storageProject.getStorage().get(id);
        int number = 0;
        for (int i = 0; i < project.getProjectTasks().size(); i++) {
            ArrayList<Task> projectTasks = project.getProjectTasks();
            number++;
            System.out.println(number + "." + projectTasks.get(i).getName() + " ID: " + projectTasks.get(i).getID());
            System.out.println("-Тип: " + projectTasks.get(i).getType());
            System.out.println("-Приоритет: " + projectTasks.get(i).getPriority());
            System.out.println("-Описание: " + projectTasks.get(i).getDescription());
            System.out.println("-Исполнитель: " + projectTasks.get(i).getExecutor().getName());
        }
    }

    public void getAllTask(Map<Integer, Task> storage) {
        if(storage.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            int number = 0;
            for (Map.Entry<Integer, Task> pair : storage.entrySet()) {
                number++;
                System.out.println(number + "." + pair.getValue().getName() + " ID: " + pair.getKey());
                System.out.println("-Проект: " + pair.getValue().getProject().getName());
                System.out.println("-Тип: " + pair.getValue().getType());
                System.out.println("-Приоритет: " + pair.getValue().getPriority());
                System.out.println("-Описание: " + pair.getValue().getDescription());
                System.out.println("-Исполнитель: " + pair.getValue().getExecutor().getName());
            }
        }
    }
}
