package com.example.todo.utils;

import android.content.Context;

import com.example.todo.model.ToDo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToDOListManager {

    private final List<ToDo> toDo;

    public ToDOListManager(final Context context) {

        toDo = new ArrayList<>();
    }

    public void insertTask(final ToDo model) {
        toDo.add(model);
    }

    public void updateTask(final int id, final String task) {
        final ToDo modelToUpdate = getTaskById(id);

        if (Objects.nonNull(modelToUpdate)) {
            modelToUpdate.setName(task);
        }
    }

    public void updateStatus(final int id, final int status) {
        final ToDo modelToUpdate = getTaskById(id);

        if (Objects.nonNull(modelToUpdate)) {
            modelToUpdate.setStatus(status);
        }
    }

    public void deleteTask(int id) {
        final ToDo modelToRemove = getTaskById(id);

        if (modelToRemove != null) {
            toDo.remove(modelToRemove);
        }
    }

    public List<ToDo> getAllTasks() {
        return toDo;
    }

    private ToDo getTaskById(final int id) {
        for (ToDo model : toDo) {

            if (model.getId() == id) {
                return model;
            }
        }

        return null;
    }
}
