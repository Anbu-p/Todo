package com.example.todo.adaptor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.AddNewTask;
import com.example.todo.MainActivity;
import com.example.todo.R;
import com.example.todo.model.ToDo;
import com.example.todo.utils.ToDOListManager;

import java.util.List;

public class ToDoAdaptor extends RecyclerView.Adapter<ToDoAdaptor.ViewHolder> {

    private List<ToDo> toDos;
    private MainActivity activity;
    private ToDOListManager toDOListManager;

    public ToDoAdaptor(final ToDOListManager toDOListManager, final MainActivity activity) {
        this.toDOListManager = toDOListManager;
        this.activity = activity;
    }

    /**
     * {@inheritDoc}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return {@link ViewHolder}
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);

        return new ViewHolder(view);
    }

    /**
     * {@inheritDoc}
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final ToDo toDo = toDos.get(position);

        holder.checkBox.setText(toDo.getName());
        holder.checkBox.setChecked(isStatus(toDo.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean isChecked) {
                if (isChecked) {
                    toDOListManager.updateStatus(toDo.getId(), 1);
                } else {
                    toDOListManager.updateStatus(toDo.getId(), 0);
                }
            }
        });
    }

    /**
     * <p>
     * gets the item count.
     * </p>
     *
     * @return the size of the item.
     */
    @Override
    public int getItemCount() {
        return toDos.size();
    }

    /**
     *
     * @param num
     * @return
     */
    public boolean isStatus(final int num) {
        return num != 0;
    }

    /**
     *
     * @param toDos
     */
    public void setTasks(final List<ToDo> toDos) {
        this.toDos = toDos;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return activity;
    }

    /**
     *
     * @param position
     */
    public void deleteTask(final int position) {
        final ToDo toDo = toDos.get(position);

        toDOListManager.deleteTask(toDo.getId());
        toDos.remove(position);
        notifyItemRemoved(position);
    }

    /**
     *
     * @param position
     */
    public void editTask(final int position) {
        final ToDo toDo = toDos.get(position);
        final Bundle bundle = new Bundle();

        bundle.putInt("id", toDo.getId());
        bundle.putString("task", toDo.getName());
        final AddNewTask addNewTask = new AddNewTask();

        addNewTask.setArguments(bundle);
        addNewTask.show(activity.getSupportFragmentManager(), addNewTask.getTag());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
