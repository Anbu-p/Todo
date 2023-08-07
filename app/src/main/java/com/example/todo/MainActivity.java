package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todo.adaptor.ToDoAdaptor;
import com.example.todo.model.ToDo;
import com.example.todo.utils.ToDOListManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private ToDOListManager toDOListManager;
    private List<ToDo> toDoList;
    private ToDoAdaptor toDoAdaptor;

    /**
     * {@inheritDoc}
     *
     * @param savedInstanceState A {@link Bundle} containing the saved state of the activity.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.fab);
        toDOListManager = new ToDOListManager(MainActivity.this);
        toDoList = new ArrayList<>();
        toDoAdaptor = new ToDoAdaptor(toDOListManager, MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(toDoAdaptor);

        toDoList = toDOListManager.getAllTasks();
        Collections.reverse(toDoList);
        toDoAdaptor.setTasks(toDoList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
    }

    /**
     * {@inheritDoc}
     *
     * @param dialogInterface represents the dialog interface.
     */
    @Override
    public void onDialogClose(final DialogInterface dialogInterface) {
        toDoList = toDOListManager.getAllTasks();
        Collections.reverse(toDoList);
        toDoAdaptor.setTasks(toDoList);
        toDoAdaptor.notifyDataSetChanged();
    }
}