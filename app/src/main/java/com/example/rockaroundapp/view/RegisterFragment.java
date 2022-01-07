package com.example.rockaroundapp.view;

import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentRegisterBinding;
import com.example.rockaroundapp.viewmodel.RegisterViewModel;


import java.util.Objects;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    //private Button registerButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentRegisterBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_register, container, false);
        View view = binding.getRoot();
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        binding.setRegisterViewModel(registerViewModel);
        binding.setLifecycleOwner(this);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        registerViewModel.getDetails().observe(this, strings -> {
            if(TextUtils.isEmpty(Objects.requireNonNull(strings.get(0)))) {
                binding.emailField.setError("Enter an email address");
            }else if (!registerViewModel.badEmailPattern()) {
                binding.emailField.setError("Incorrect input");
            }else if(TextUtils.isEmpty(Objects.requireNonNull(strings.get(1)))) {
                binding.passwordField.setError("Enter a password");
            }else if (strings.get(2).equals("NONE"))  {
                Toast.makeText(getActivity(), "Please choose a User type", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(Objects.requireNonNull(strings.get(3)))) {
                binding.firstnameField.setError("Enter a firstname");
            }else if (TextUtils.isEmpty(Objects.requireNonNull(strings.get(4)))) {
                binding.surnameField.setError("Enter a surname");
            }
        });
        return view;
    }
}