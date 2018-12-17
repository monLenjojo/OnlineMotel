package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1801.onlinemotel.R;
import com.example.user1801.onlinemotel.firebaseThing.JavaBeanTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerAdapterHomePage extends RecyclerView.Adapter<RecyclerAdapterHomePage.RecyclerViewHolder>{
    Context context;
    ArrayList<JavaBeanAllRoomList> arrayList;
    String firebaseId;
    public RecyclerAdapterHomePage(Context context, ArrayList<JavaBeanAllRoomList> arrayList, String firebaseId){
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseId =  firebaseId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view, viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.textRoomName.setText(arrayList.get(i).roomNmae);
        recyclerViewHolder.textWhere.setText(arrayList.get(i).where);
        recyclerViewHolder.textPeople.setText(arrayList.get(i).people);
        recyclerViewHolder.textMoney.setText(arrayList.get(i).money);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class  RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textRoomName, textWhere, textPeople, textMoney;
        public RecyclerViewHolder(@NonNull final View itemView) {
            super(itemView);
            textRoomName = itemView.findViewById(R.id.textView_RoomName);
            textWhere = itemView.findViewById(R.id.textView_Where);
            textPeople = itemView.findViewById(R.id.textView_People);
            textMoney = itemView.findViewById(R.id.textView_Money);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialog =new AlertDialog.Builder(context);
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    final View alertDialogView = layoutInflater.inflate(R.layout.alertdialog_calendar_view,null);
                    final TextView selectDateIn = alertDialogView.findViewById(R.id.textSelectDataIn);
                    final TextView selectDateOut = alertDialogView.findViewById(R.id.textSelectDataOut);
                    final LinearLayout layout = alertDialogView.findViewById(R.id.calendarLayout);
                    final Boolean[] stateIn = {false};
                    final Boolean[] stateOut = { false };
                    final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy年MM月dd日");
                    selectDateIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CalendarView calendarView = new CalendarView(context);
                            layout.removeAllViews();
                            layout.addView(calendarView);
                            Date today = new Date();
                            calendarView.setMinDate(today.getTime());
                            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                @Override
                                public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                                    selectDateIn.setText(i+"年"+(i1+1)+"月"+i2+"日");
                                    selectDateOut.setText("請點選這裡選擇日期");
                                    stateIn[0] =true;
                                    stateOut[0] =false;
                                }
                            });
                        }
                    });
                    selectDateOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CalendarView calendarView = new CalendarView(context);
                            layout.removeAllViews();
                            layout.addView(calendarView);
                            try {
                                Date mini = timeFormat.parse(selectDateIn.getText().toString());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(mini);
                                calendar.add(Calendar.DAY_OF_YEAR,+1);
                                mini = calendar.getTime();
                                calendarView.setMinDate(mini.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "請先選擇入住日期", Toast.LENGTH_SHORT).show();
                            }
                            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                @Override
                                public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                                    selectDateOut.setText(i+"年"+(i1+1)+"月"+i2+"日");
                                    stateOut[0] = true;
                                }
                            });
                        }
                    });
                    alertDialog.setView(alertDialogView).setMessage("選擇訂房日期")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (stateIn[0] && stateOut[0]) {
                                        Long stayDay = null;
                                        try {
                                            Long checkIn = timeFormat.parse(selectDateIn.getText().toString()).getTime();
                                            Long checkOut = timeFormat.parse(selectDateOut.getText().toString()).getTime();
                                            stayDay = (checkOut-checkIn)/(1000*60*60*24);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        if (stayDay==null) {
                                            new AlertDialog.Builder(context).setMessage("失敗").show();
                                            return;
                                        }
                                        JavaBeanMyRoom data = new JavaBeanMyRoom(selectDateIn.getText().toString(),
                                                String.valueOf(stayDay),
                                                textWhere.getText().toString(),
                                                textRoomName.getText().toString(),
                                                textMoney.getText().toString(),
                                                textPeople.getText().toString());
                                        FirebaseDatabase.getInstance().getReference("userList")
                                                .child(firebaseId).child("myRoomList").child(textRoomName.getText().toString()).setValue(data)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isComplete()) {
                                                        if (task.isSuccessful()) {
                                                            new AlertDialog.Builder(context).setMessage("成功，訂房日期：\n" + selectDateIn.getText() + "\n~\n" + selectDateOut.getText())
                                                                    .setPositiveButton("好",null).setCancelable(false).show();
                                                        } else {
                                                            new AlertDialog.Builder(context).setMessage("失敗").show();
                                                        }
                                                    }
                                                }
                                                });
                                    } else {
                                        new AlertDialog.Builder(context).setMessage("失敗，請輸入日期").setPositiveButton("好",null).setCancelable(false).setCancelable(false).show();
                                    }
                                }
                            }).setNegativeButton("取消",null).show();
                }
            });
        }
    }
}
