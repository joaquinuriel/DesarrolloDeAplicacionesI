package edu.uade.ritmofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.uade.ritmofit.auth.TokenManager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    private final TokenManager tokenManager = new TokenManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        String emailLink = intent.getDataString();

        if (emailLink != null && auth.isSignInWithEmailLink(emailLink)) {
            try {

                MasterKey masterKey = new MasterKey.Builder(MainActivity.this)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();

                SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                        MainActivity.this,
                        "auth_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );

                String email = securePrefs.getString("emailForSignIn", null);
                String pword = securePrefs.getString("passwordToLink", null);

                if (email != null && pword != null) {
                    auth.signInWithEmailLink(email, emailLink)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    Log.d("Auth", "Successfully signed in!");
                                    FirebaseUser user = task.getResult().getUser();
                                    AuthCredential credential = EmailAuthProvider.getCredential(email, pword);
                                    assert user != null;
                                    user.linkWithCredential(credential)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    FirebaseUser updatedUser = task1.getResult().getUser();

                                                    assert updatedUser != null;
                                                    tokenManager.saveToken(updatedUser.getUid());
                                                } else {
                                                    Exception e = task1.getException();
                                                }
                                            });
                                } else {
                                    Log.e("Auth", "Error signing in", task.getException());
                                }
                            });
                } else {
                    Log.e("Auth", "No email or password saved, cannot complete sign-in");
                }
            } catch (Exception e) {
                Log.e("Auth", "Failed to access secure storage");
            }
        }

        // Configurar NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }
}