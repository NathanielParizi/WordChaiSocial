package com.psiarb.go.wordchai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button logIn, signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        logIn = (Button) findViewById(R.id.LogIn);
        signUp = (Button) findViewById(R.id.signUp);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //       Intent logActivity = new Intent(StartActivity.this, LogInActivity.class);
          //      startActivity(logActivity);


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //       Intent regActivity = new Intent(StartActivity.this, RegisterActivity.class);
         //       startActivity(regActivity);

            }
        });

    }
}
