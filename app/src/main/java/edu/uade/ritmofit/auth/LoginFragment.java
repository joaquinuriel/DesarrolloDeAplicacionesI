package edu.uade.ritmofit.auth;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;
import edu.uade.ritmofit.R;
import edu.uade.ritmofit.auth.model.LoginResponse;
import edu.uade.ritmofit.auth.repository.AuthRepository;

import javax.inject.Inject;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    @Inject
    AuthRepository authRepository;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del LoginFragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Verificar si ya hay un token
        if (authRepository.getAccessToken() != null) {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_login_to_home);
            return;
        }
        // Inicializar los elementos de la UI
        usernameEditText = view.findViewById(R.id.editTextUsername);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        loginButton = view.findViewById(R.id.buttonLogin);

        // Configurar el listener del botón de login
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamar al login
            authRepository.login(username, password, new AuthRepository.LoginCallback() {
                @Override
                public void onSuccess(LoginResponse loginResponse) {
                    String accessToken = authRepository.getAccessToken();

                    Log.d("Auth", "Login successful, token: " + accessToken);
                    // Navegar a ClassesFragment
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_login_to_home);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("Auth", errorMessage);
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}