package com.example.listycity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City newC);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implementAddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        City existingCity;
        Bundle args = this.getArguments();

        if (args != null) {
            Serializable serializableObject = args.getSerializable("city");
            existingCity = (City) serializableObject;
        } else {
            existingCity = null;
        }

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        // If we are editing:
        if (existingCity != null) {
            editCityName.setText(existingCity.getName());
            editProvinceName.setText(existingCity.getProvince());
        }

        return builder
                .setView(view)
                .setTitle("Add/Edit a city" )
                .setNegativeButton("Cancel"
                        , null)
                .setPositiveButton("Submit"
                        , (dialog, which) -> {
                            String cityName = editCityName.getText().toString();
                            String provinceName = editProvinceName.getText().toString();

                            // if new add
                            if (existingCity == null) {
                                listener.addCity(new City(cityName, provinceName));
                            } else {
                                listener.editCity(new City(cityName, provinceName));
                            }

                        })
                .create();
    }

    AddCityFragment newInstance(City city) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("city", city);

        this.setArguments(arguments);
        return this;
//        AddCityFragment fragment = new AddCityFragment();
//        fragment.setArguments(arguments);
//        return fragment;
    }
}
