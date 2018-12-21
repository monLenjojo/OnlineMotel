package com.example.user1801.onlinemotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user1801.onlinemotel.recyclerDesign.RecyclerFunctionHomePage;
import com.example.user1801.onlinemotel.recyclerDesign.RecyclerFunctionMyRoomList;
import com.google.firebase.auth.FirebaseAuth;

public class MyRoomList extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarlist);
        setSupportActionBar(toolbar);
        auth = FirebaseAuth.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recyclerMyRoomList);
        new RecyclerFunctionMyRoomList(this,recyclerView,auth.getUid());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_room_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
        switch (id){
            case R.id.action_homePage:
                Intent page = new Intent(this,MainActivity.class);
                startActivity(page);
                MyRoomList.this.finish();
//            case R.id.action_share:
////                Intent page = new Intent(this,MyRoomList.class);
////                startActivity(page);
////                MyRoomList.this.finish();
//                break;
            case R.id.action_account:
                break;
            case R.id.action_logout:
                auth.signOut();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
