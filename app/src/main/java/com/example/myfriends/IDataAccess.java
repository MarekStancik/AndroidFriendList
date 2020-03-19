package com.example.myfriends;

import java.util.ArrayList;
import java.util.List;

public interface IDataAccess<T> {
    public ArrayList<T> readAll();
    public T create(T obj);
    public void delete(int id);
    public void update(T obj);
}
