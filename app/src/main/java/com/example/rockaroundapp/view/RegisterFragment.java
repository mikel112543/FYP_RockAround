package com.example.rockaroundapp.view;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentRegisterBinding;
import com.example.rockaroundapp.viewmodel.RegisterViewModel;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;
import java.util.Objects;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding binding;
    private NavController navController;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_register, container, false);
        View view = binding.getRoot();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);
        binding.setLifecycleOwner(this);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        registerViewModel.getDetails().observe(getViewLifecycleOwner(), strings -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(strings.get(0)))) {
                binding.emailField.setError("Enter an email address");
            } else if (!registerViewModel.validateEmail()) {
                binding.emailField.setError("Incorrect input");
            } else {
                binding.emailField.setError(null);
            }
            if (strings.get(1).isEmpty()) {
                binding.passwordField.setError("Enter a password");
            } else {
                binding.passwordField.setError(null);
            }
            if (strings.get(2).equals("NONE")) {
                Toast.makeText(getActivity(), "Please choose a User type", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(strings.get(3)))) {
                binding.firstnameField.setError("Enter a firstname");
            } else {
                binding.firstnameField.setError(null);
            }
            if (TextUtils.isEmpty(Objects.requireNonNull(strings.get(4)))) {
                binding.surnameField.setError("Enter a surname");
            } else {
                binding.surnameField.setError(null);
            }
            if (registerViewModel.validateEmail() && registerViewModel.validatePassword() &&
                    registerViewModel.validateFirstname() && registerViewModel.validateLastname() && registerViewModel.validateType()) {
                registerViewModel.register();
            }
            registerViewModel.getFirebaseUserRegister().observe(getViewLifecycleOwner(), firebaseUser -> {
                if (firebaseUser != null) {
                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.action_registerFragment_to_exploreFragment);
                } else {
                    Toast.makeText(getActivity(), registerViewModel.getFailedRegMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }
}