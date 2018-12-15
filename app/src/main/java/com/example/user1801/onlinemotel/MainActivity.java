package com.example.user1801.onlinemotel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user1801.onlinemotel.firebaseThing.setTestData;
import com.example.user1801.onlinemotel.recyclerDesign.RecyclerAdapter;
import com.example.user1801.onlinemotel.recyclerDesign.RecyclerFunction;

public class MainActivity extends AppCompatActivity {

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
                a.addNewTestData(0,15,3000,3);
//                a.addNewTestData(11,15,3000,3);
            }
        });

        RecyclerView recyclerHomeView = findViewById(R.id.recyclerHomeView);
        RecyclerFunction recyclerFunction = new RecyclerFunction(this,recyclerHomeView);

    }

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
            case R.id.action_myroom:
                break;
            case R.id.action_share:
                break;
            case R.id.action_account:
                break;
            case R.id.action_logout:
                Intent page = new Intent(this,LogInActivity.class);
                startActivity(page);
                this.finish();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
