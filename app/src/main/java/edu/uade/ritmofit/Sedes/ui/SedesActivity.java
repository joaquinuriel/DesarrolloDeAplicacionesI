package edu.uade.ritmofit.Sedes.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.Sedes.viewModel.SedeViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SedesActivity extends AppCompatActivity {
    private static final String TAG = "SedesActivity";
    private SedeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sedes_main);

        // Inicializar ViewModel con Hilt
        viewModel = new ViewModelProvider(this).get(SedeViewModel.class);

        // Cargar el Fragment inicial (lista de sedes)
        if (savedInstanceState == null) {
            loadFragment(new SedesFragment());
        }

        Log.d(TAG, "onCreate: Finalizado");
    }

    // Método para cargar un Fragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null); // Permite volver atrás
        transaction.commit();
    }

    // Método para navegar a los detalles desde el ViewModel o Fragment
    public void navigateToDetail(String sedeId) {
        viewModel.fetchSedeDetail(sedeId);
        loadFragment(new SedeDetailFragment());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Actividad destruida");
    }
}