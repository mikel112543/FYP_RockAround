package com.example.rockaroundapp.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentRegisterBinding;
import com.example.rockaroundapp.viewmodel.RegisterViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding binding;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private String userType;
    private String firstName;
    private Bundle bundle = new Bundle();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_register, container, false);
        View view = binding.getRoot();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);
        binding.setLifecycleOwner(this);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle(null);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        registerViewModel.getDetails().observe(getViewLifecycleOwner(), strings -> {
            userType = strings.get(2);
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
        });
        registerViewModel.getRegisterSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                firstName = binding.firstnameField.getEditText().getText().toString();
                bundle.putString("firstName", firstName);
                bundle.putString("userType", userType);
                Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                if (userType == "SOLO") {
                    navController.navigate(R.id.action_registerFragment_to_soloSetupFragment, bundle);
                } else if (userType == "GROUP") {
                    navController.navigate(R.id.action_registerFragment_to_groupSetupFragment, bundle);
                } else {
                    navController.navigate(R.id.action_registerFragment_to_venueSetupFragment, bundle);
                }
            }
            /*registerViewModel.getFirebaseUserRegister().observe(getViewLifecycleOwner(), firebaseUser -> {
                if (firebaseUser != null) {
                    Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    firstName = binding.txtFirstname.toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("firstName", firstName);
                    navController.navigate(R.id.action_registerFragment_to_soloSetupFragment, bundle);
                    if(userType == "SOLO") {
                    }
                } else {
                    Toast.makeText(getActivity(), registerViewModel.getFailedRegMessage(), Toast.LENGTH_SHORT).show();
                }
            });*/
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }
}