package edu.uade.ritmofit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

public class SignUpFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        MaterialButton signUpBtn = root.findViewById(R.id.button6);
        MaterialButton signInBtn = root.findViewById(R.id.redirect);

        signUpBtn.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).confirmSignUp();
        });

        signInBtn.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).showLogin();
        });

        return root;
    }
}

