package edu.uade.ritmofit.Sedes.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;

@AndroidEntryPoint
public class SedesActivity extends AppCompatActivity {
    private static final String TAG = "SedesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedes_main);

        // Configurar Toolbar como ActionBar
        setSupportActionBar(findViewById(R.id.toolbar));

        // Configurar NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_sedes);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Log.d(TAG, "onCreate: Finalizado");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_sedes);
        return NavigationUI.navigateUp(navController, new AppBarConfiguration.Builder(navController.getGraph()).build())
                || super.onSupportNavigateUp();
    }

    public void navigateToDetail(String sedeId) {
        Bundle bundle = new Bundle();
        bundle.putString("sedeId", sedeId);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_sedes);
        navController.navigate(R.id.action_sedes_to_sedeDetail, bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Actividad destruida");
    }
}