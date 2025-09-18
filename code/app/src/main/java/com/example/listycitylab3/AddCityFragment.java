package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    private static String CITYOBJ ="city_object";
    private static String CITYPOS ="city_position";
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City city, int position);
    }
    public static AddCityFragment newInstance() {
        return new AddCityFragment();
    }

    public static AddCityFragment newInstance(City city, int position) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable(CITYOBJ, city);
        args.putInt(CITYPOS, position);
        fragment.setArguments(args);
        return fragment;
    }

    private EditText editCityName;
    private EditText editProvinceName;
    private City existingCity = null;
    private int cityPos = -1;
    private AddCityDialogListener listener;
    private boolean isEditMode = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            existingCity = (City) getArguments().getSerializable(CITYOBJ);
            cityPos = getArguments().getInt(CITYPOS,-1);
            if (existingCity != null) {
                isEditMode = true;
            }
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle(isEditMode ? "Edit City" : "Add City")
                .setPositiveButton("OK", (dialog, id) -> {
                    City city = new City(editCityName.getText().toString(),
                            editProvinceName.getText().toString());
                    if (isEditMode) {
                        listener.editCity(city, cityPos);
                    } else {
                        listener.addCity(city);
                    }
                    })
                .setNegativeButton("Cancel", null);
        return builder.create();
    }
}




