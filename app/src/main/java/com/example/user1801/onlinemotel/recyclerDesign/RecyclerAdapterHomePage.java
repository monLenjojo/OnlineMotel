package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user1801.onlinemotel.R;
import com.example.user1801.onlinemotel.chaosThing.JavaBeanRoomKey;
import com.example.user1801.onlinemotel.firebaseThing.JavaBeanTime;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerAdapterHomePage extends RecyclerView.Adapter<RecyclerAdapterHomePage.RecyclerViewHolder> {
    Context context;
    ArrayList<JavaBeanRoomPath> arrayList;
    String firebaseId;

    public RecyclerAdapterHomePage(Context context, ArrayList<JavaBeanRoomPath> arrayList, String firebaseId) {
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseId = firebaseId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.textRoomName.setText(arrayList.get(i).name);
        recyclerViewHolder.textWhere.setText(arrayList.get(i).address);
        recyclerViewHolder.textPeople.setText(arrayList.get(i).people);
        recyclerViewHolder.textMoney.setText(arrayList.get(i).money);
    }

    String keyList[] = {null, null, null};
    int[] listNum = {-1};
    Long checkIn = null, checkOut = null;

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    final View alertDialogView = layoutInflater.inflate(R.layout.alertdialog_calendar_view, null);
                    final TextView selectDateIn = alertDialogView.findViewById(R.id.textSelectDataIn);
                    final TextView selectDateOut = alertDialogView.findViewById(R.id.textSelectDataOut);
                    final LinearLayout layout = alertDialogView.findViewById(R.id.calendarLayout);
                    final Boolean[] stateIn = {false};
                    final Boolean[] stateOut = {false};
                    final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy/MM/dd");
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
                                    selectDateIn.setText(i + "/" + (i1 + 1) + "/" + i2);
                                    selectDateOut.setText("請點選這裡選擇日期");
                                    stateIn[0] = true;
                                    stateOut[0] = false;
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
                                calendar.add(Calendar.DAY_OF_YEAR, +1);
                                mini = calendar.getTime();
                                calendarView.setMinDate(mini.getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "請先選擇入住日期", Toast.LENGTH_SHORT).show();
                            }
                            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                                @Override
                                public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                                    selectDateOut.setText(i + "/" + (i1 + 1) + "/" + i2);
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
                                            checkIn = timeFormat.parse(selectDateIn.getText().toString()).getTime();
                                            checkOut = timeFormat.parse(selectDateOut.getText().toString()).getTime();
//                                            stayDay = (checkOut-checkIn)/(1000*60*60*24);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
//                                        if (stayDay==null) {
//                                            new AlertDialog.Builder(context).setMessage("失敗").show();
//                                            return;
//                                        }
                                        FirebaseDatabase.getInstance().getReference("allRoomKeyList").orderByChild("room").equalTo(arrayList.get(getAdapterPosition()).path)
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                            keyList[0] = child.getKey();
                                                            keyList[1] = child.getValue(JavaBeanRoomKey.class).getChaosKey();
                                                            keyList[2] = child.getValue(JavaBeanRoomKey.class).getMacAddress();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                        FirebaseDatabase.getInstance().getReference("userList").child(firebaseId).child("myRoomList").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                                    Log.d("onDataChange: ", child.getKey());
                                                    listNum[0] += 1;
                                                    Log.d("onDataChange: ", String.valueOf(listNum[0]));
                                                }
                                                listNum[0] += 1;
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //過兩秒後要做的事情
//                                                Log.d("tag", "Delay");
                                                JavaBeanMyRoom data = new JavaBeanMyRoom(String.valueOf(checkIn),
                                                        String.valueOf(checkOut),
                                                        textRoomName.getText().toString(),
                                                        arrayList.get(getAdapterPosition()).path,
                                                        keyList[0],
                                                        keyList[1],
                                                        keyList[2]);

                                                FirebaseDatabase.getInstance().getReference("userList")
                                                        .child(firebaseId).child("myRoomList").child(String.valueOf(listNum[0])).setValue(data)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isComplete()) {
                                                                    if (task.isSuccessful()) {
                                                                        new AlertDialog.Builder(context).setMessage("成功，訂房日期：\n" + selectDateIn.getText() + "\t~\t" + selectDateOut.getText())
                                                                                .setPositiveButton("好", null).setCancelable(false).show();
                                                                    } else {
                                                                        new AlertDialog.Builder(context).setMessage("失敗").show();
                                                                    }
                                                                }
                                                            }
                                                        });
                                                keyList[0] = null;
                                                keyList[1] = null;
                                                listNum[0] = -1;
                                                checkIn = null;
                                                checkOut = null;
                                            }
                                        }, 2000);
//                                        stayGetData(listNum[0],keyList[0],keyList[1]);

                                    } else {
                                        new AlertDialog.Builder(context).setMessage("失敗，請輸入日期").setPositiveButton("好", null).setCancelable(false).setCancelable(false).show();
                                    }
                                }
                            }).setNegativeButton("取消", null).show();
                }
            });
        }
    }

    public void stayGetData(int val, String str1, String str2) {
        if (val == -1 || TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stayGetData(listNum[0], keyList[0], keyList[1]);
        }
    }
}
