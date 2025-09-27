package edu.uade.ritmofit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class LoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        MaterialButton signInBtn = root.findViewById(R.id.confirm);
        MaterialButton signUpBtn = root.findViewById(R.id.button3);

        signInBtn.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).confirmLogin();
        });

        signUpBtn.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).showSignUp();
        });

        return root;
    }
}
