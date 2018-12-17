package com.example.user1801.onlinemotel.recyclerDesign;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user1801.onlinemotel.MyRoom;
import com.example.user1801.onlinemotel.R;

import java.util.ArrayList;

public class RecyclerAdapterMyRoomList extends RecyclerView.Adapter<RecyclerAdapterMyRoomList.ViewHolder> {
    Context context;
    ArrayList<JavaBeanAllRoomList> arrayList;

    public RecyclerAdapterMyRoomList(Context context, ArrayList<JavaBeanAllRoomList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_list_view, viewGroup, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textRoomName.setText(arrayList.get(i).roomNmae);
        viewHolder.textWhere.setText(arrayList.get(i).where);
        viewHolder.textPeople.setText(arrayList.get(i).people);
        viewHolder.textMoney.setText(arrayList.get(i).money);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textRoomName, textWhere, textPeople, textMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textRoomName = itemView.findViewById(R.id.textView_RoomName);
            textWhere = itemView.findViewById(R.id.textView_Where);
            textPeople = itemView.findViewById(R.id.textView_People);
            textMoney = itemView.findViewById(R.id.textView_Money);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent page = new Intent(context,MyRoom.class);
                    page.putExtra("ROOMNAME",textRoomName.getText().toString());
                    page.putExtra("ROOMADDRESS",textWhere.getText().toString());
                    page.putExtra("ROOMPEOPLE",textPeople.getText().toString());
                    page.putExtra("ROOMMONEY",textMoney.getText().toString());
                    context.startActivity(page);
                }
            });
        }
    }
}
