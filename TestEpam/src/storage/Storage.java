package storage;
import units.Unit;

import java.util.Map;
//Класс родитель для классов хранящих массивы проектов, задач и пользователей
public abstract class Storage {
    public abstract void delete(int id);
    //данный метод выводит в консоль содержимое Map, в качестве параметра принимает Map, содержимое которой требуется вывести
    public void getAll(Map<Integer, Unit> storage) {
        if(storage.isEmpty()) {
            System.out.println("Список пуст");
        } else {
            int number = 0;
            for (Map.Entry<Integer, Unit> pair : storage.entrySet()) {
                number++;
                System.out.println(number + "." + pair.getValue().getName() + " ID: " + pair.getKey());
            }
        }
    }
}
