package com.example.to_do_list_dailytaskplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.to_do_list_dailytaskplanner.Adapter.recyclerviewadapter;
import com.example.to_do_list_dailytaskplanner.Adapter.verticalrecyclerview;
import com.example.to_do_list_dailytaskplanner.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String priority;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dialog dialog1 = new Dialog(MainActivity.this);
        dialog1.setContentView(R.layout.addtask);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        CheckBox checkBox;
        String uid = mAuth.getCurrentUser().getUid();
        Button logout;
        RecyclerView recyclerView1 = findViewById(R.id.horizontalrecylerview);
        RecyclerView recyclerView2 = findViewById(R.id.verticalrecylcerview);
        logout = findViewById(R.id.logoutbtn);
        ProgressDialog dialog;
        dialog = new ProgressDialog(this);
        dialog.setTitle("Logging Out");
        dialog.setMessage("Please Wait");
        ArrayList<Users> arrayList = new ArrayList<>();
        ArrayList<Users> others = new ArrayList<>();
        TextView addnewtask,todaystask,othertask;
        addnewtask = findViewById(R.id.addnewtask);
        todaystask = findViewById(R.id.todaystask);
        othertask = findViewById(R.id.othertasks);



        logout.setOnClickListener(new View.OnClickListener() {
            Intent redirect = new Intent(MainActivity.this, SignIn.class);
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "Logged Out Successfuly!", Toast.LENGTH_SHORT).show();
                        startActivity(redirect);
                        finish();

                    }
                },4000);
            }
        });
        FloatingActionButton fbaftn = findViewById(R.id.addfb);
        final Calendar calendar = Calendar.getInstance();
        TextView taskname,taskcontent;
        Button date,time,addtask;
        checkBox = dialog1.findViewById(R.id.priority);
        taskname = dialog1.findViewById(R.id.cardviewtasknameenter);
        taskcontent = dialog1.findViewById(R.id.cardviewtaskcontenteenter);
        date = dialog1.findViewById(R.id.datebutton);
        time = dialog1.findViewById(R.id.timebutton);
        addtask = dialog1.findViewById(R.id.addtaskbtn);
        recyclerviewadapter adapter = new recyclerviewadapter(MainActivity.this,arrayList);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        verticalrecyclerview adaptervertical = new verticalrecyclerview(MainActivity.this,others);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        fbaftn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show();

               DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        setdate();
                   }
               };
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       new DatePickerDialog(MainActivity.this,date1,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int mins = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog;
                        timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time.setText(hourOfDay + ":" + minute);
                            }
                        },hour,mins,true);
                        timePickerDialog.setTitle("Choose Time");
                        timePickerDialog.show();
                    }
                });
                priority = "false";
               checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                   @Override
                   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                       if (isChecked)
                       {
                           priority = "true";
                       }
                       else
                       {
                           priority = "false";

                       }
                   }
               });
                addtask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Taskname = taskname.getText().toString();
                        String TaskContent = taskcontent.getText().toString();
                        String dateselcted = date.getText().toString();
                        String  timeselected = time.getText().toString();
                        String checked = priority;

                        if (!taskname.getText().toString().isEmpty() && !taskcontent.getText().toString().isEmpty() && !date.getText().toString().isEmpty() && !time.getText().toString().isEmpty()) {
                           final Users naya = new Users(Taskname, TaskContent, dateselcted, timeselected,checked);
                            arrayList.add(naya);
                            others.add(naya);
                            naya.setUserid(uid);
                            dialog1.dismiss();
                            taskname.setText("");
                            taskcontent.setText("");
                            date.setText("");
                            time.setText("");
                            checkBox.setChecked(false);
                            adapter.notifyItemInserted(arrayList.size() - 1);
                            recyclerView1.smoothScrollToPosition(arrayList.size() - 1);
                            adaptervertical.notifyItemInserted(others.size() - 1);
                            recyclerView2.smoothScrollToPosition(others.size() - 1);

                            database.getReference().child("Tasks")
                                    .child(uid)
                                    .push()
                                    .setValue(naya).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                                                    }

                                        }
                                    });

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            private void setdate()
            {
                String format = "dd/MM/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                String date2 =   date.getText().toString();
                date.setText(dateFormat.format(calendar.getTime()));

            }
        });

        database.getReference().child("Tasks")
                .child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrayList.clear();
                        others.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            Users naya = dataSnapshot.getValue(Users.class);
                            naya.setUserid(dataSnapshot.getKey());
                            if(!naya.getUserid().equals(FirebaseAuth.getInstance().getUid())) {
                                if(naya.getPriority().equals("true"))
                                {
                                    arrayList.add(naya);
                                    addnewtask.setVisibility(addnewtask.INVISIBLE);
                                    todaystask.setVisibility(todaystask.VISIBLE);

                                }
                                else if(naya.getPriority().equals("false")||naya.getPriority().equals(""))
                                {
                                    others.add(naya);
                                    addnewtask.setVisibility(addnewtask.INVISIBLE);
                                    othertask.setVisibility(othertask.VISIBLE);
                                }

                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Error!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged();
                        adaptervertical.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        recyclerView1.setAdapter(adapter);
        recyclerView2.setAdapter(adaptervertical);
    }
}