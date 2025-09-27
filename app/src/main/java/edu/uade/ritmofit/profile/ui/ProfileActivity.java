package edu.uade.ritmofit.profile.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    @Inject TokenManager tokenManager;
    private ProfileViewModel viewModel;
    private ImageView profileImage;
    private EditText nameEdit;
    private EditText emailEdit;
    private Button editButton;
    private Button saveButton;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        setupToolbar();
        setupInsets();
        initViews();
        setupViewModel();
        setupListeners();

        userId = tokenManager.getUserId();
        viewModel.fetchUserById(userId);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        profileImage = findViewById(R.id.profileImage);
        nameEdit = findViewById(R.id.editTextName);
        emailEdit = findViewById(R.id.editTextEmail);
        editButton = findViewById(R.id.btnEdit);
        saveButton = findViewById(R.id.btnSave);

        nameEdit.setEnabled(false);
        emailEdit.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        viewModel.getUserProfile().observe(this, user -> {
            if (user != null) showUser(user);
        });
        viewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null) Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        });
        viewModel.getInfo().observe(this, infoMsg -> {
            if (infoMsg != null) {
                Toast.makeText(this, infoMsg, Toast.LENGTH_SHORT).show();
                nameEdit.setEnabled(false);
                emailEdit.setEnabled(false);
                saveButton.setEnabled(false);
                editButton.setEnabled(true);
            }
        });
    }

    private void setupListeners() {
        editButton.setOnClickListener(v -> {
            nameEdit.setEnabled(true);
            emailEdit.setEnabled(true);
            saveButton.setEnabled(true);
            editButton.setEnabled(false);
        });

        saveButton.setOnClickListener(v -> {
            updateUser();
        });
    }

    private void updateUser() {
        String name = nameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        UserProfile updateUser = new UserProfile(name, email);
        viewModel.updateUser(userId, updateUser);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

   private void showUser(UserProfile user) {
        nameEdit.setText(user.getName());
        emailEdit.setText(user.getEmail());
        String fotoBase64 = user.getFoto();
        if (fotoBase64 != null && !fotoBase64.isEmpty()) {
            Bitmap bitmap = base64ToBitmap(fotoBase64);
            if (bitmap != null) {
                profileImage.setImageBitmap(bitmap);
            } else {
                android.util.Log.e("ProfileActivity", "Error al decodificar la imagen");
            }
        }
    }

    private Bitmap base64ToBitmap(String base64Str) {
        try {
            String cleanBase64 = base64Str.replaceAll("\\s", "");
            byte[] decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            android.util.Log.e("ProfileActivity", "Excepción al decodificar Base64: " + e.getMessage());
            return null;
        }
    }
}