package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentLoginBinding;
import com.example.rockaroundapp.viewmodel.LoginViewModel;
import com.example.rockaroundapp.viewmodel.RegisterViewModel;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_login, container, false);
        View view = binding.getRoot();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        observeViewModel(loginViewModel);
        return view;
    }

    private void observeViewModel(LoginViewModel loginViewModel) {
        loginViewModel.getDetails().observe(getViewLifecycleOwner(), details -> {
            if(TextUtils.isEmpty(details.get(0))) {
                binding.loginField.setError("Please provide an email address");
            }else{
                binding.loginField.setError(null);
            }
            if(TextUtils.isEmpty(details.get(1))) {
                binding.passwordField.setError("Please provide your password to login");
            }else{
                binding.passwordField.setError(null);
            }
            if(binding.loginField.getError() == null && binding.passwordField.getError() == null) {
                loginViewModel.loginUser(details.get(0), details.get(1));
            }
        });
        loginViewModel.getFirebaseUserMutableLiveData().observe(getViewLifecycleOwner(), firebaseUser -> {
            if(firebaseUser != null) {
                Toast.makeText(getActivity(), "Error logging in", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
            }
        });
        loginViewModel.getLoginFailureMsg().observe(getViewLifecycleOwner(), failureMsg -> {
            if(!failureMsg.isEmpty()) {
                Toast.makeText(getActivity(), failureMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}