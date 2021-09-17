package com.wdtheprovider.basicapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.wdtheprovider.basicapp.activities.MainActivity;
import com.wdtheprovider.basicapp.fragments.Dashboard;
import com.wdtheprovider.basicapp.fragments.Fragment_3;
import com.wdtheprovider.basicapp.models.DBHelper;
import com.wdtheprovider.basicapp.models.Jobs;

import java.io.Serializable;
import java.util.ArrayList;

public class jobAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Jobs> list;
    Context context;
    AppCompatActivity activity;


    public jobAdapter(ArrayList<Jobs> list, Context context,Activity activity ) {
        this.list = list;
        this.activity = (AppCompatActivity) activity;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_row,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Jobs item = list.get(position);
        viewHolder.exp.setText(item.getJob_experience());
        viewHolder.title.setText(item.getJob_title());
        viewHolder.location.setText(item.getJob_location());
        viewHolder.job_desc.setText(item.getJob_description());
        viewHolder.qualification.setText(item.getJob_qualification());


        viewHolder.share.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, "Available Job "+item.getJob_title());
            intent.setType("text/plain");
            activity.startActivity(Intent.createChooser(intent, "Send to"));
        });




        viewHolder.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu =new PopupMenu(context,viewHolder.txt_option);
            popupMenu.inflate(R.menu.omen);
            popupMenu.setOnMenuItemClickListener(items->
            {
                switch (items.getItemId())
                {
                    case R.id.menu_edit:
                        Bundle bundle = new Bundle();
                       // bundle.putSerializable("EDIT", (Serializable) item);
                        Fragment myFragment = new Fragment_3();
                        myFragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                        break;
                    case R.id.menu_remove:
                        DBHelper dao=new DBHelper();
                        dao.RemoveJob(item.getKey()).addOnSuccessListener(suc->
                        {
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(item);
                        }).addOnFailureListener(er->
                        {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title,qualification,location,job_desc,exp,txt_option,share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rv_title);
            qualification = itemView.findViewById(R.id.rv_qualification);
            location = itemView.findViewById(R.id.rv_location);
            job_desc = itemView.findViewById(R.id.rv_desc);
            exp = itemView.findViewById(R.id.rv_exp);
            txt_option = itemView.findViewById(R.id.txt_option);
            share = itemView.findViewById(R.id.rv_share);

        }
    }
}
