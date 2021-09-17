package com.wdtheprovider.basicapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.wdtheprovider.basicapp.R;
import com.wdtheprovider.basicapp.models.DBHelper;
import com.wdtheprovider.basicapp.models.Jobs;
import com.wdtheprovider.basicapp.utils.Constants;

/**
 * Created by Dingaan Letjane September 2021
 */
public class Fragment_2 extends Fragment {
    EditText edt_title, edt_qualification, edt_location, edt_experience, edt_job_desc, edt_code;
    Button btn_post;
    DBHelper dbHelper;
    int success = 0;

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        initComponent(view);
        return view;
    }

    private void initComponent(View view) {
        edt_title = view.findViewById(R.id.edt_title);
        edt_qualification = view.findViewById(R.id.edt_qualification);
        edt_location = view.findViewById(R.id.edt_location);
        edt_experience = view.findViewById(R.id.edt_experience);
        edt_job_desc = view.findViewById(R.id.edt_job_desc);
        btn_post = view.findViewById(R.id.btn_post);
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
                dialog.setTitle("Confirm Post");
                dialog.setMessage("Are you sure you want to Post?");
                dialog.setPositiveButton("yes", (dialog1, which) -> {
                    Jobs jobs = new Jobs(title, job_desc, experience, location, qualification);
                    dbHelper.AddPost(jobs, edt_code.getText().toString()).addOnSuccessListener(unused -> {
                        Toast.makeText(getContext(), "Job Successfully Posted", Toast.LENGTH_SHORT).show();
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();
                    }).addOnFailureListener(e -> {
                        Log.d("dbHelper", "" + e.toString());
                        Toast.makeText(getContext(), "Oops, there was an error - " + e.toString() + " posting the Job", Toast.LENGTH_SHORT).show();
                    });
                });
                dialog.setNegativeButton("No", (dialog12, which) -> {
                });
                dialog.show();
            }


        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /**
     * Gets String values
     */
    public static String getStringPreference(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }
}