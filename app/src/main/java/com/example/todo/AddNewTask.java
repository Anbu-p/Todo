package com.example.todo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo.model.ToDo;
import com.example.todo.utils.ToDOListManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "AddNewTask";
    private EditText editText;
    private Button button;
    private ToDOListManager toDOListManager;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    /**
     * {@inheritDoc}
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return {@link View}
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    /**
     * {@inheritDoc}
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.saveButton);
        toDOListManager = new ToDOListManager(getActivity());
        boolean isUpdate = false;
        final Bundle bundle = getArguments();

        if (Objects.nonNull(bundle)) {
            isUpdate = true;
            final String task = bundle.getString("task");

            editText.setText(task);

            if (task.length() > 0) {
                button.setEnabled(false);
            }
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, final int start, final int count, final int after) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {
                if (charSequence.toString().equals("")) {
                    button.setEnabled(false);
                    button.setBackgroundColor(Color.GRAY);
                } else {
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor((com.google.android.material.R.color.design_default_color_primary)));
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {

            }
        });
        final boolean isUpdateTask = isUpdate;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String text = editText.getText().toString();

                if (isUpdateTask) {
                    toDOListManager.updateTask(bundle.getInt("id"), text);
                } else {
                    final ToDo toDo = new ToDo();

                    toDo.setName(text);
                    toDo.setStatus(0);
                    toDOListManager.insertTask(toDo);
                }
                dismiss();
            }
        });
    }

    /**
     * <p>
     *  Dismiss the specific task.
     * </p>
     *
     * @param dialog gets the dialog console
     */
    @Override
    public void onDismiss(@NonNull final DialogInterface dialog) {
        super.onDismiss(dialog);
        final Activity activity = getActivity();

        if (activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
