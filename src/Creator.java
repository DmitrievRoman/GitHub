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

    public User createUser() throws IOException {
        System.out.println("Введите имя пользователя: ");
        String  userName = reader.readLine();
        return  new User (userName);
    }

    public void deleteUser() throws IOException {
        getAllUsers();
        System.out.println("Введите ID пользователя для удаления");
        try {
            int userIdForDelete = Integer.parseInt(reader.readLine());
            for (Map.Entry<Integer, User> pair : User.users.entrySet() ) {
                User user = pair.getValue();
                if (user.getID() == userIdForDelete) {
                    user.deleteAllTasks();
                }
            }
            User.users.remove(userIdForDelete);
        }catch (NullPointerException x) {
            System.out.println("Возможно вы ввели не существующий ID");
            System.out.println("Удаление не выполнено!");
        }
    }

    public Project createProject() throws IOException {
        System.out.println("Введите название проекта");
        String projectName = reader.readLine();
        return new Project(projectName);
    }
    public void deleteProject() throws IOException {
        getAllProjects();
        System.out.println("Введите ID проекта для удаления");
        try {
            int projectIdForDelete = Integer.parseInt(reader.readLine());
            Project.projects.get(projectIdForDelete).deleteAllTasks();
            Project.projects.remove(projectIdForDelete);
        } catch (NullPointerException e) {
            System.out.println("Возможно вы ввели не существующий ID");
            System.out.println("Удаление не выполнено!");
        }
    }
//метод создает задачу, если нет необходимых данных, например Пользователей 0, будет предложено их создать
    public void createTask() throws IOException {
        userControl = false;
        projectControl = false;
        System.out.println("Выберите ID проекта для данной задачи");
        getAllProjects();
        if (Project.projects.isEmpty()) {
            System.out.println("Хотите создать проект?");
            System.out.println("1.да");
            System.out.println("2.нет");
            if (reader.readLine().equals("1")) {
                createProject();
                getAllProjects();
                System.out.println("Введите ID проекта для данной задачи");
            } else {
                System.out.println("Невозможно создать задачу без проекта");
                return;
            }
        }
        int inputProject = Integer.parseInt(reader.readLine());
        for (Map.Entry<Integer, Project> pair : Project.projects.entrySet()) {
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
            Project project = Project.projects.get(inputProject);
            System.out.println("Тема:");
            String topic = reader.readLine();
            System.out.println("Введите тип задачи");
            String type = reader.readLine();
            System.out.println("Введите приоритет задачи:");
            String priority = reader.readLine();
            System.out.println("Введите ID пользователя, которому адресовано данное задание");
            getAllUsers();
            if (User.users.isEmpty()) {
                System.out.println("Хотите создать пользователя?");
                System.out.println("1.Да");
                System.out.println("2.Нет");
                if (reader.readLine().equals("1")) {
                    createUser();
                    getAllUsers();
                    System.out.println("Введите ID пользователя для данной задачи");
                } else {
                    System.out.println("Невозможно создать задачу без пользователя");
                    return;
                }
            }
            try {
                int inputUser = Integer.parseInt(reader.readLine());
                for (Map.Entry<Integer, User> pair : User.users.entrySet()) {
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
                        User user = User.users.get(inputUser);
                        System.out.println("Введите описание данной задачи");
                        String description = reader.readLine();
                        new Task(project, topic, type, priority, user, description);
                    }
                } catch(NumberFormatException e){
                    System.out.println("Вы ввели не цифру!!");
                    return;
                }
        }
    }
    //метод удаляет задачу из проекта и у пользователя
    public void deleteTask() throws IOException {
        userControl = false;
        getAllTasks();
        System.out.println("Введите ID задачи для удаления");
            int idTaskForDelete = Integer.parseInt(reader.readLine());
            for (Map.Entry<Integer, Task> pair : Task.tasks.entrySet()) {
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
                Task task = Task.tasks.get(idTaskForDelete);
                for (Map.Entry<Integer, Project> pair : Project.projects.entrySet()) {
                    Project project = pair.getValue();
                    project.deleteTask(task);
                }
                for (Map.Entry<Integer, User> pair : User.users.entrySet()) {
                    User user = pair.getValue();
                    user.deleteTask(task);
                }
                Task.tasks.remove(idTaskForDelete);
            }
    }
    public void getAllUsers() {
        if (User.users.isEmpty()) {
            System.out.println("Нет ни одного пользователя");
        } else {
            int number = 0;
            for (Map.Entry<Integer, User> pair : User.users.entrySet()) {
                number++;
                System.out.println(number + "." + pair.getValue().getName() + " ID: " + pair.getKey());
            }
        }
    }
    public void getAllProjects() {
        if (Project.projects.isEmpty()) {
            System.out.println("Нет ни одного проекта");
        } else {
            int number = 0;
            for (Map.Entry<Integer, Project> pair : Project.projects.entrySet()) {
                number++;
                System.out.println(number + "." + pair.getValue().getName() + " ID: " + pair.getKey());
            }
        }
    }

    public void getAllTasksInProject() throws IOException {
        System.out.println("Введите ID проекта для получения всех задач данного проекта");
        getAllProjects();
        int numberOfProject = Integer.parseInt(reader.readLine());
        Project project = Project.projects.get(numberOfProject);
        int number = 0;
        for (int i = 0; i < project.getProjectTasks().size(); i++) {
            number++;
            System.out.println(number + "." + project.getProjectTasks().get(i).getTopic());
        }
    }
    public void getAllTasksForUser() throws IOException {
        System.out.println("Введите ID пользователя для получения всех задач данного пользователя");
        getAllUsers();
        try {
            int numberOfUser = Integer.parseInt(reader.readLine());
            User user = User.users.get(numberOfUser);
            int number = 0;
            for (int i = 0; i < user.getUserTasks().size(); i++) {
                number++;
                System.out.println(number + "." + user.getUserTasks().get(i).getTopic());
            }
        } catch (NumberFormatException e) {
            System.out.println("Возможно вы ввели не цифру!");
            getAllTasksForUser();
        }
    }

    public void getAllTasks() {
        if (Task.tasks.isEmpty()) {
            System.out.println("Нет ни одной задачи");
        } else {
            int number = 0;
            for (Map.Entry<Integer, Task> pair : Task.tasks.entrySet()) {
                number++;
                System.out.println(number + "." + pair.getValue().getTopic() + " ID " + pair.getKey());
            }
        }
    }

}
