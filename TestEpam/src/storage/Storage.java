package storage;
import units.Unit;

import java.util.Map;

public abstract class Storage {
    public abstract void delete(int id);
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
