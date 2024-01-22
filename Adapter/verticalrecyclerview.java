package com.example.to_do_list_dailytaskplanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list_dailytaskplanner.Models.Users;
import com.example.to_do_list_dailytaskplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class verticalrecyclerview extends RecyclerView.Adapter<verticalrecyclerview.ViewHolder> {
    public verticalrecyclerview(Context context, ArrayList<Users> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Users> arrayList;
    Context context;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    Users naya = new Users();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.othertasks,parent,false);
        verticalrecyclerview.ViewHolder viewHolder = new verticalrecyclerview.ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskname.setText(arrayList.get(position).taskName);
        holder.taskcontent.setText(arrayList.get(position).taskContent);
        holder.date.setText(arrayList.get(position).date);
        holder.time.setText(arrayList.get(position).time);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                database = FirebaseDatabase.getInstance();
                if(position>=0&&database!=null&&mAuth!=null&&arrayList!=null) {
                    database.getReference().child("Tasks").child(mAuth.getCurrentUser().getUid()).child(arrayList.get(position).getUserid()).removeValue();
                    arrayList.remove(position);
                    notifyItemRemoved(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskcontent,taskname,date,time;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskcontent = itemView.findViewById(R.id.othercardcontent);
            taskname = itemView.findViewById(R.id.othercardtaskname);
            date = itemView.findViewById(R.id.othercarddate);
            time = itemView.findViewById(R.id.othercardtime);
            delete = itemView.findViewById(R.id.otherdeleteicon);
        }
    }
}
