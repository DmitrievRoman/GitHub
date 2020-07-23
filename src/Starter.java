import java.io.*;
import java.util.ArrayList;
import java.util.Map;
/*
Класс подгружает данные прошлой сессии
Здесь же

 */

public class Starter {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String a = System.getProperty("user.dir") + "\\LastSave.txt";
    private String b = System.getProperty("user.dir") + "\\Save.ser";
    private String pathToSourceFile = "";
    private boolean isWork = true;
    private ArrayList<User> usersForSave = new ArrayList<>();
    //В зависимости от выбора пользователя: будет загружена или не будет прошлая сессия
    public void load() throws IOException, ClassNotFoundException {
        System.out.println("1.Новая сессия");
        System.out.println("2.Загрузить последнюю сессию");
        System.out.println("3.Указать путь к файлу для его загрузки");
        String userInput = reader.readLine();
        switch (userInput) {
            case ("1"):
                Start();
                break;
            case ("2"):
                File file = new File(a);
                if (file.exists()) {
                    BufferedReader fr = new BufferedReader(new FileReader(file));
                    while (fr.ready()) {
                        pathToSourceFile = fr.readLine();
                    }
                    fr.close();
                }
                loadResource();
                break;
            case ("3"):
                //При этом варианте пользователь сам указывает путь к Save файлу
                System.out.println("Укажите путь к файлу");
                pathToSourceFile = reader.readLine();
                loadResource();
                //Start();
                break;
            default:
                System.out.println("Введите цифру для выбора дальнейшего действия");
                load();
                break;

        }
    }
    //Метод загружает данные из файла Save.ser
    public void loadResource() throws IOException, ClassNotFoundException {
        File sourceFile = new File(pathToSourceFile);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(sourceFile));
        } catch (IOException e) {
            System.out.println("Возможно вы ввели неправильный путь");
            load();
        }
        User.userCount = 0;
        Task.taskCount = 0;
        Project.projectCount = 0;
        usersForSave = (ArrayList<User>) ois.readObject();
        for (int j = 0; j < usersForSave.size(); j++) {
            User user = usersForSave.get(j);
            if (user.getID() >= User.userCount) {
                User.userCount = user.getID();
            }
            User.users.put(user.getID(), user);
            for (int i = 0; i < user.getUserTasks().size(); i++) {
                if (user.getUserTasks().get(i).getID() >= Task.taskCount) {
                    Task.taskCount = user.getUserTasks().get(i).getID();
                }
                Project project = user.getUserTasks().get(i).getProject();
                Project.projects.put(project.getID(), project);
                if (project.getID() >= Project.projectCount) {
                    Project.projectCount = project.getID();
                }
            }
        }
        Start();
    }
// метод отображает список команд и обрабатывает выбор пользователя
    public void Start() throws IOException {
        System.out.println("Для корректного выхода из программы введите \"exit\"");
        System.out.println("Для вывода дополнительного меню команд введите \"a\"");
        String userInput = "";
        Creator creator = new Creator();
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
                if (User.users.isEmpty()) {
                    System.out.println("Нет ни одного пользователя");
                } else {
                    creator.deleteUser();
                }
            } else if(userInput.equals("3")) {
                creator.createProject();
            } else if (userInput.equals("4")) {
                if (Project.projects.isEmpty()) {
                    System.out.println("Нет ни одного проекта");
                } else {
                    creator.deleteProject();
                }
            } else if (userInput.equals("5")) {
                creator.createTask();
            } else if (userInput.equals("6")) {
                if (Task.tasks.isEmpty()) {
                    System.out.println("Нет ни одной задачи");
                } else {
                    creator.deleteTask();
                }
            } else if (userInput.equals("7")) {
                creator.getAllUsers();
            } else if (userInput.equals("8")) {
                creator.getAllProjects();
            } else if (userInput.equals("9")) {
                creator.getAllTasksInProject();
            } else if (userInput.equals("0")) {
                creator.getAllTasksForUser();
            } else if (userInput.equals("exit")) {
                //Сохранение происходит перед выходом
                //очищаем список всех пользователей, потому что
                // в нем сейчас все загруженные при запуске программы пользователи
                usersForSave.clear();
                ObjectOutputStream os;
                if(pathToSourceFile.isEmpty()) {
                    os = new ObjectOutputStream(new FileOutputStream(b));
                } else {
                    os = new ObjectOutputStream(new FileOutputStream(pathToSourceFile));
                }
                    for (Map.Entry<Integer, User> pair : User.users.entrySet()) {
                        usersForSave.add(pair.getValue());
                    }
                    //Сериализуем массив всех пользователей
                    os.writeObject(usersForSave);
                    os.close();
                    OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(a));
                    osw.write(b);
                    osw.flush();
                    osw.close();
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
