import units.User;
import storage.StorageProject;
import storage.StorageTask;
import storage.StorageUser;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class StartMenu {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    StorageUser storageUser = new StorageUser();
    StorageProject storageProject = new StorageProject();
    StorageTask storageTask = new StorageTask();
    Creator creator = new Creator(storageUser, storageProject, storageTask);
    Starter starter =  new Starter(storageUser, storageProject, storageTask,this, creator);
    Loader loader = new Loader(storageUser, storageProject, storageTask, starter);
    String a = System.getProperty("user.dir") + "\\LastSave.txt";
    String b = System.getProperty("user.dir") + "\\Save.ser";
    String pathToSourceFile = "";
    public void run() throws IOException, ClassNotFoundException {
        System.out.println("1.Новая сессия");
        System.out.println("2.Загрузить последнюю сессию");
        System.out.println("3.Указать путь к файлу для его загрузки");
    String userInput = reader.readLine();
        switch (userInput) {
            case ("1"):
                starter.Start();
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
                loader.load(pathToSourceFile);
                break;
            case ("3"):
                //При этом варианте пользователь сам указывает путь к Save файлу
                System.out.println("Укажите путь к файлу");
                pathToSourceFile = reader.readLine();
                loader.load(pathToSourceFile);
                break;
//            default:
//                System.out.println("Введите цифру для выбора дальнейшего действия");
//                load();
//                break;
        }

    }
    public void save() throws IOException {
        ArrayList<User> usersForSave = new ArrayList<>();
        for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
            usersForSave.add(pair.getValue());
        }
        usersForSave.clear();
        ObjectOutputStream os;
        if(pathToSourceFile.isEmpty()) {
            os = new ObjectOutputStream(new FileOutputStream(b));
        } else {
            os = new ObjectOutputStream(new FileOutputStream(pathToSourceFile));
        }
        for (Map.Entry<Integer, User> pair : storageUser.getStorage().entrySet()) {
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
    }
}
