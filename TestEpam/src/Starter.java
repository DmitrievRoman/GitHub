import storage.StorageProject;
import storage.StorageTask;
import storage.StorageUser;

import java.io.*;
/*
Класс подгружает данные прошлой сессии
Здесь же

 */

public class Starter {
    StorageUser storageUser;
    StorageProject storageProject;
    StorageTask storageTask;
    StartMenu startMenu;
    Creator creator;
    public Starter (StorageUser storageUser, StorageProject storageProject, StorageTask storageTask, StartMenu startMenu, Creator creator) {
        this.storageUser = storageUser;
        this.storageProject = storageProject;
        this.storageTask = storageTask;
        this.startMenu = startMenu;
        this.creator = creator;
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private boolean isWork = true;

// метод отображает список команд и обрабатывает выбор пользователя
    public void Start() throws IOException {
        System.out.println("Для корректного выхода из программы введите \"exit\"");
        System.out.println("Для вывода дополнительного меню команд введите \"a\"");
        String userInput = "";
        while (isWork) {
            System.out.println("Для вывода меню команд введите \"m\"");
            userInput = reader.readLine();
            if(userInput.equals("m")) {
                listOfCommands();
            } else if(userInput.equals("a")) {
                additionalCommands();
            } else if (userInput.equals("1")) {
                creator.createUser();
            } else if (userInput.equals("2")) {
                if (storageUser.getStorage().isEmpty()) {
                    System.out.println("Нет ни одного пользователя");
                } else {
                    creator.deleteUser();
                }
            } else if(userInput.equals("3")) {
                creator.createProject();
            } else if (userInput.equals("4")) {
                if (storageProject.getStorage().isEmpty()) {
                    System.out.println("Нет ни одного проекта");
                } else {
                    creator.deleteProject();
                }
            } else if (userInput.equals("5")) {
                creator.createTask();
            } else if (userInput.equals("6")) {
                if (storageTask.getStorage().isEmpty()) {
                    System.out.println("Нет ни одной задачи");
                } else {
                    creator.deleteTask();
                }
            } else if (userInput.equals("7")) {
                storageUser.getAll(storageUser.getList());
            } else if (userInput.equals("8")) {
                storageProject.getAll(storageProject.getList());
            } else if (userInput.equals("9")) {
                System.out.println("Введите ID проекта для которого хотите увидеть список задач");
                storageProject.getAll(storageProject.getList());
                int id = Integer.parseInt(reader.readLine());
                storageTask.getAllTasksInProject(id, storageProject);
            } else if (userInput.equals("0")) {
                System.out.println("Введите ID пользователя для которого хотите увидеть список задач");
                storageUser.getAll(storageUser.getList());
                int id = Integer.parseInt(reader.readLine());
                storageTask.getAllTasksForUser(id, storageUser);
            } else if (userInput.equals("exit")) {
                startMenu.save();
                  reader.close();
                isWork = false;
            } else {
                System.out.println("Выберите действие из существующих команд");
                listOfCommands();
                additionalCommands();
            }
        }
    }
    public void listOfCommands() {
        System.out.println("1.Создать нового пользователя");
        System.out.println("2.Удалить пользователя");
        System.out.println("3.Создать новый проект");
        System.out.println("4.Удалить проект");
        System.out.println("5.Создать новую задачу");
        System.out.println("6.Удалить задачу");
    }
    public void additionalCommands() {
        System.out.println("7.Список всех пользователей");
        System.out.println("8.Список всех проектов");
        System.out.println("9.Список всех задач в проекте");
        System.out.println("0.Список всех задач пользователя");
    }
}
