package edu.uade.ritmofit.profile.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;

import javax.inject.Inject;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.auth.TokenManager;
import edu.uade.ritmofit.profile.data.model.UserProfile;

@AndroidEntryPoint
public class ProfileActivity extends AppCompatActivity {
    @Inject
    TokenManager tokenManager;
    private ProfileViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Observa los LiveData del ViewModel
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.getUserProfile().observe(this, user -> {
            if (user != null) {
                showUser(user);
            }
        });

        viewModel.getAllUsers().observe(this, users -> {
            if (users != null && !users.isEmpty()) {
                showUser(users.get(0));
                Toast.makeText(this, "Primer usuario mostrado", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) {
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar usuario
        String userId = tokenManager.getUserId();
        viewModel.fetchUserById(userId);
    }

    private void showUser(UserProfile user) {
        TextView nameView = findViewById(R.id.nameProfile);
        TextView emailView = findViewById(R.id.emailProfile);

        nameView.setText("Nombre: " + user.getName());
        emailView.setText("Email: " + user.getEmail());
    }
}