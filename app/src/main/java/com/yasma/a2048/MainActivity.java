package com.yasma.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
View satrt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

     satrt=findViewById(R.id.startgame);
     satrt.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i =new Intent(MainActivity.this,MainActivity2.class);
             startActivity(i);
         }
     });
    }
}
//https://www.shutterstock.com/image-vector/happy-school-boy-jumping-laughing-isolated-1025181436