package edu.uade.ritmofit.profile.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import edu.uade.ritmofit.R;
import edu.uade.ritmofit.data.api.ApiClient;
import edu.uade.ritmofit.profile.data.ProfileRepository;
import edu.uade.ritmofit.profile.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadAllUsers();
    }

    private void loadUserProfile(String userId) {
        ProfileRepository repo = ApiClient.createService(ProfileRepository.class);

        repo.getUserbyId(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    TextView nameView = findViewById(R.id.nameProfile);
                    TextView emailView = findViewById(R.id.emailProfile);

                    nameView.setText("Nombre: " + user.getName());
                    emailView.setText("Email: " + user.getEmail());
                } else {
                    Toast.makeText(ProfileActivity.this, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllUsers() {
        ProfileRepository repo = ApiClient.createService(ProfileRepository.class);

        repo.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    User firstUser = response.body().get(0);
                    TextView nameView = findViewById(R.id.nameProfile);
                    TextView emailView = findViewById(R.id.emailProfile);

                    nameView.setText("Nombre: " + firstUser.getName());
                    emailView.setText("Email: " + firstUser.getEmail());
                    Toast.makeText(ProfileActivity.this, "Primer usuario mostrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Fallo de conexión al obtener usuarios", Toast.LENGTH_SHORT).show();
            }
        });
    }
}