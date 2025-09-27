package edu.uade.ritmofit;
import android.app.Application;

import com.google.firebase.FirebaseApp;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class GimApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Inicialización global si es necesario
        FirebaseApp.initializeApp(this);
    }
}
