package storage;

import units.Project;
import units.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class StorageProject extends Storage{
    Map<Integer, Project> projects = new HashMap<>();
    public void add(int id, Project project) {
        projects.put(id, project);
    }
    @Override
    public void delete(int id) {
        Project project = projects.get(id);
        if(project.getProjectTasks().isEmpty()) {
            projects.remove(id);
        } else {
            System.out.println("В данном проекте есть задачи, его нельзя удалить");
        }
    }
    public Project create(String name) {
        return new Project(name, this);
    }
    public Map<Integer, Project> getStorage() {
        return projects;
    }
    public Map getList() {
        return projects;
    }

    public void getProjectTasks(int id) {
        Project project = projects.get(id);
        ArrayList<Task> projectTasks = project.getProjectTasks();
        for (int i = 0; i < projectTasks.size(); i++) {
            Task task = projectTasks.get(i);
            System.out.println(task.getName());
        }
    }
}
