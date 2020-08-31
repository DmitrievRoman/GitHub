package storage;

import units.Task;
import units.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StorageUser extends Storage {
    Map<Integer, User> users = new HashMap<>();
    public void add(int id, User user) {
        users.put(id, user);
    }
    public void delete(int id) {
        User user = users.get(id);
        if(user.getUserTasks().isEmpty()) {
            users.remove(id);
        } else {
            System.out.println("На данного ползователя назначены задачи, его нельзя удалить");
        }
    }
    public void create(String name) {
        new User(name, this);
    }
    public Map<Integer, User> getStorage() {
        return users;
    }
    public Map getList() {
        return users;
    }
    public void getUserTasks(int id) {
        User user = users.get(id);
        ArrayList<Task> userTasks = user.getUserTasks();
        for (int i = 0; i < userTasks.size(); i++) {
            Task task = userTasks.get(i);
            System.out.println(task.getName());
        }
    }
}
