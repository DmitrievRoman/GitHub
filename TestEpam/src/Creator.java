import units.Project;
import units.Task;
import units.User;
import storage.StorageProject;
import storage.StorageTask;
import storage.StorageUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
/*
Класс создает и удаляет пользователей, задачи и проекты

 */

public class Creator {
    boolean projectControl = false;
    boolean userControl = false;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StorageUser storageUser;
    StorageProject storageProject;
    StorageTask storageTask;
    public Creator (StorageUser storageUser, StorageProject storageProject, StorageTask storageTask) {
        this.storageUser = storageUser;
        this.storageProject = storageProject;
        this.storageTask = storageTask;
    }

    public void createUser() throws IOException {
        System.out.println("Введите имя пользователя: ");
        String userName = reader.readLine();
        storageUser.create(userName);
    }

    public void deleteUser() throws IOException {
        storageUser.getAll(storageUser.getList());
        System.out.println("Введите ID пользователя для удаления");
        int id = Integer.parseInt(reader.readLine());
        for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
            if (id == pair.getKey()) {
                storageUser.delete(id);
                return;
            }
        }
        System.out.println("Вы ввели не существующий ID");


    }

    public void createProject() throws IOException {
        System.out.println("Введите название проекта");
        String projectName = reader.readLine();
        storageProject.create(projectName);
    }
    public void deleteProject() throws IOException {
        storageProject.getAll(storageProject.getList());
        System.out.println("Введите ID проекта для удаления");
        int id = Integer.parseInt(reader.readLine());
        for (Map.Entry<Integer, Project> pair : storageProject.getStorage().entrySet()) {
            if (id == pair.getKey()) {
                storageProject.delete(id);
            } else {
                System.out.println("Вы ввели не существующий ID");
            }
        }
    }
//метод создает задачу, если нет необходимых данных, например Пользователей 0 метод завершится и задача не будет создана
    public void createTask() throws IOException {
        userControl = false;
        projectControl = false;
        System.out.println("Выберите ID проекта для данной задачи");
        storageProject.getAll(storageProject.getList());
        if (storageProject.getStorage().isEmpty()) {
            System.out.println("Нет ни одного проекта");
            System.out.println("Невозможно создать задачу без проекта");
            return;
        }
        int inputProject = Integer.parseInt(reader.readLine());
        for (Map.Entry<Integer, Project> pair : storageProject.getStorage().entrySet()) {
            Project project = pair.getValue();
            if (project.getID() == inputProject) {
                projectControl = true;
            }
        }
        if (!projectControl) {
            System.out.println("Проекта с таким ID не найдено");
            System.out.println("Задача не создана!");
            return;
        } else {
            System.out.println("Тема:");
            String topic = reader.readLine();
            System.out.println("Введите тип задачи");
            String type = reader.readLine();
            System.out.println("Введите приоритет задачи:");
            String priority = reader.readLine();
            System.out.println("Введите ID пользователя, которому адресовано данное задание");
            storageUser.getAll(storageUser.getList());
            if (storageUser.getStorage().isEmpty()) {
                System.out.println("Нет ни одного пользователя");
                System.out.println("Невозможно создать задачу без пользователя");
            } else {
                try {
                    int inputUser = Integer.parseInt(reader.readLine());
                    for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
                        User user = pair.getValue();
                        if (user.getID() == inputUser) {
                            userControl = true;
                        }
                    }
                    if (!userControl) {
                        System.out.println("Пользователя с таким ID не найдено");
                        System.out.println("Задача не создана!");
                        return;
                    } else {
                        System.out.println("Введите описание данной задачи");
                        String description = reader.readLine();
                        storageTask.create(topic, type, priority, description, inputProject, inputUser,storageProject, storageUser);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Вы ввели не цифру!!");
                    return;
                }
            }
        }
    }
    //метод удаляет задачу из проекта и у пользователя
    public void deleteTask() throws IOException {
        userControl = false;
        storageTask.getAllTask(storageTask.getStorage());
        System.out.println("Введите ID задачи для удаления");
            int idTaskForDelete = Integer.parseInt(reader.readLine());
            for (Map.Entry<Integer, Task> pair : storageTask.getStorage().entrySet()) {
                Task task = pair.getValue();
                if (task.getID() == idTaskForDelete) {
                    userControl = true;
                } else {
                    System.out.println("Задачи с таким ID  не существует");
                    System.out.println("Задача не была удалена!");
                    return;
                }
            }
            if (userControl) {
                Task task = storageTask.getStorage().get(idTaskForDelete);
                for (Map.Entry<Integer, Project> pair : storageProject.getStorage().entrySet()) {
                    Project project = pair.getValue();
                    project.deleteTask(task);
                }
                for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
                    User user = pair.getValue();
                    user.deleteTask(task);
                }
                storageTask.delete(idTaskForDelete);
            }
    }

}
