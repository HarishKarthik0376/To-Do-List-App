package com.example.to_do_list_dailytaskplanner.Adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_do_list_dailytaskplanner.Models.Users;
import com.example.to_do_list_dailytaskplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewadapter.ViewHolder> {
    public recyclerviewadapter(Context context,ArrayList<Users> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    ArrayList<Users> arrayList;
    Context context;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    Users naya = new Users();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todaystasks,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
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
        TextView priority,addtask,other;
        ImageView delete;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            taskcontent = itemView.findViewById(R.id.cardcontent);
            taskname = itemView.findViewById(R.id.cardtaskname);
            date = itemView.findViewById(R.id.carddate);
            time = itemView.findViewById(R.id.cardtime);
            delete = itemView.findViewById(R.id.deleteicon);
            priority = itemView.findViewById(R.id.todaystask);
            addtask = itemView.findViewById(R.id.addnewtask);
            other = itemView.findViewById(R.id.othertasks);
        }
    }
}
