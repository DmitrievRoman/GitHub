package units;

import java.io.Serializable;
//Класс родитель для основных объектов программы(Проектов, Юзеров, Задач)
//Имплементирует Serializable для возможности сохранения объектов
public abstract class Unit implements Serializable {
    public abstract String getName();
}
