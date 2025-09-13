package edu.uade.ritmofit;
/*por ahora esta desactivado*/

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        Button buttonSedes = findViewById(R.id.buttonSedes);


    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}