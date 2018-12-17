package com.example.user1801.onlinemotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user1801.onlinemotel.firebaseThing.setTestData;
import com.example.user1801.onlinemotel.recyclerDesign.RecyclerFunctionHomePage;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                setTestData a = new setTestData();
                a.addNewTestData(0,9,"2500","2");
                a.addNewTestData(10,15,"3000","3");
//                a.addNewTestData(11,15,3000,3);
            }
        });

        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(authStateListener);
        RecyclerView recyclerHomeView = findViewById(R.id.recyclerHomeView);
        new RecyclerFunctionHomePage(this,recyclerHomeView,auth.getUid());

    }
FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser()==null) {
            Intent page = new Intent(MainActivity.this,LogInActivity.class);
            startActivity(page);
            MainActivity.this.finish();
        }
    }
};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case R.id.action_myroom_list:
                Intent pageMyRoomList = new Intent(this,MyRoomList.class);
                startActivity(pageMyRoomList);
                break;
            case R.id.action_share:
                Intent pageMyRoom = new Intent(this,MyRoom.class);
                startActivity(pageMyRoom);
                break;
            case R.id.action_account:
                break;
            case R.id.action_logout:
                auth.signOut();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
