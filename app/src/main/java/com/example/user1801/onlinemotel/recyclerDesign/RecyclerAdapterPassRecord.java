package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1801.onlinemotel.R;

import java.util.ArrayList;

public class RecyclerAdapterPassRecord extends RecyclerView.Adapter<RecyclerAdapterPassRecord.RecyclerViewHolder> {
    Context context;
    ArrayList<JavaBeanPassRecord> arrayList;
    String firebaseId;

    public RecyclerAdapterPassRecord(Context context, ArrayList<JavaBeanPassRecord> arrayList, String firebaseId) {
        this.context = context;
        this.arrayList = arrayList;
        this.firebaseId = firebaseId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_pass_pecord_view,viewGroup,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        recyclerViewHolder.txTime.setText(arrayList.get(i).time);
        recyclerViewHolder.txWho.setText(arrayList.get(i).name);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView txTime,txWho;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            txWho = itemView.findViewById(R.id.textView_passRecordName);
            txTime = itemView.findViewById(R.id.textView_passRecordTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context).setMessage(arrayList.get(getAdapterPosition()).room).show();
                }
            });
        }
    }
}
