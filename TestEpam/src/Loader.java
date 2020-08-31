import units.Project;
import units.Task;
import units.User;
import storage.StorageProject;
import storage.StorageTask;
import storage.StorageUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
//Данный класс отвечает за загрузку данных из сейвфайла

public class Loader {
    StorageUser storageUser;
    StorageProject storageProject;
    StorageTask storageTask;
    Starter starter;
    ArrayList<User> usersForSave = new ArrayList<>();
    public Loader (StorageUser storageUser, StorageProject storageProject, StorageTask storageTask, Starter starter) {
        this.storageUser = storageUser;
        this.storageProject = storageProject;
        this.storageTask = storageTask;
        this.starter = starter;
    }
    //метод загружает данные из файла, по умолчанию это Save.ser, но пользователь может передать и другой путь к файлу
    public void load(String pathToSourceFile) throws IOException, ClassNotFoundException {
        File sourceFile = new File(pathToSourceFile);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(sourceFile));
        } catch (IOException e) {
            System.out.println("Возможно вы ввели неправильный путь");
            StartMenu startMenu = new StartMenu();
            startMenu.run();
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
            storageUser.add(user.getID(), user);
            for (int i = 0; i < user.getUserTasks().size(); i++) {
                if (user.getUserTasks().get(i).getID() >= Task.taskCount) {
                    Task.taskCount = user.getUserTasks().get(i).getID();
                }
                Project project = user.getUserTasks().get(i).getProject();
                storageProject.add(project.getID(), project);
                if (project.getID() >= Project.projectCount) {
                    Project.projectCount = project.getID();
                }
            }
        }
        starter.Start();
    }

}
