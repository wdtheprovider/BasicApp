package com.wdtheprovider.basicapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.wdtheprovider.basicapp.R;
import com.wdtheprovider.basicapp.models.DBHelper;
import com.wdtheprovider.basicapp.utils.Constants;

import java.util.HashMap;

/**
 * Created by Dingaan Letjane September 2021
 */

public class Fragment_3 extends Fragment {

    EditText edt_title, edt_qualification, edt_location, edt_experience, edt_job_desc,edt_code;
    Button btn_post;
    DBHelper dbHelper;

    int success = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_3, container, false);
        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        edt_title = view.findViewById(R.id.edt_title);
        edt_qualification = view.findViewById(R.id.edt_qualification);
        edt_location = view.findViewById(R.id.edt_location);
        edt_experience = view.findViewById(R.id.edt_experience);
        edt_job_desc = view.findViewById(R.id.edt_job_desc);
        btn_post = view.findViewById(R.id.btn_update);
        edt_code = view.findViewById(R.id.edt_id);

        dbHelper = new DBHelper();


        btn_post.setOnClickListener(v -> {
            String title = edt_title.getText().toString();
            String qualification = edt_qualification.getText().toString();
            String location = edt_location.getText().toString();
            String experience = edt_experience.getText().toString();
            String job_desc = edt_job_desc.getText().toString();

            if (edt_code.getText().toString().isEmpty()) {
                edt_code.setError(Constants.REQUIRED_FIELDS);
            } else if (title.isEmpty()) {
                edt_title.setError(Constants.REQUIRED_FIELDS);
            } else if (qualification.isEmpty()) {
                edt_qualification.setError(Constants.REQUIRED_FIELDS);
            } else if (location.isEmpty()) {
                edt_location.setError(Constants.REQUIRED_FIELDS);
            } else if (experience.isEmpty()) {
                edt_experience.setError(Constants.REQUIRED_FIELDS);
            } else if (job_desc.isEmpty()) {
                edt_job_desc.setError(Constants.REQUIRED_FIELDS);
            } else {
                success = 1;
            }

            if (success == 1) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                dialog.setTitle("Update");
                dialog.setMessage("Are you sure you want to update?");
                dialog.setPositiveButton("yes", (dialog1, which) -> {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("job_title", title);
                    hashMap.put("job_description", job_desc);
                    hashMap.put("job_experience", experience);
                    hashMap.put("job_location", location);
                    hashMap.put("job_qualification", qualification);

                    dbHelper.UpdateJob(edt_code.getText().toString(), hashMap).addOnSuccessListener(unused -> {
                        Toast.makeText(getContext(), "Job updated successfully", Toast.LENGTH_SHORT).show();
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Oops, there was an error - " + e.toString() + " updating the Job", Toast.LENGTH_SHORT).show();
                    });

                });

                dialog.setNegativeButton("No", (dialog2, which1) -> {

                });
                dialog.show();
            }
        });
    }
}