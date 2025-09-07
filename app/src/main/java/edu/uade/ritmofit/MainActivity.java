package edu.uade.ritmofit;
/*por ahora esta desactivado*/

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import edu.uade.ritmofit.classes.activity.ClassesActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Por ahora redirigimos directo a ClassesActivity
        startActivity(new Intent(this, ClassesActivity.class));
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }
}
