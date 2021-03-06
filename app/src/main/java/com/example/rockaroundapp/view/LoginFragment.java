package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentLoginBinding;
import com.example.rockaroundapp.generated.callback.OnClickListener;
import com.example.rockaroundapp.viewmodel.LoginViewModel;
import com.example.rockaroundapp.viewmodel.RegisterViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private NavController navController;

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
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        loginViewModel.signOut();
        observeViewModel(loginViewModel);
        return view;
    }

    private void observeViewModel(LoginViewModel loginViewModel) {
        loginViewModel.getDetails().observe(getViewLifecycleOwner(), details -> {
            if (TextUtils.isEmpty(details.get(0))) {
                binding.loginField.setError("Please provide an email address");
            } else {
                binding.loginField.setError(null);
            }
            if (TextUtils.isEmpty(details.get(1))) {
                binding.passwordField.setError("Please provide your password to login");
            } else {
                binding.passwordField.setError(null);
            }
            if (binding.loginField.getError() == null && binding.passwordField.getError() == null) {
                loginViewModel.loginUser(details.get(0), details.get(1));
            }
        });
        loginViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), bool -> {
            if(Boolean.FALSE.equals(bool)) {
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
            }
        });
        loginViewModel.getUserType().observe(getViewLifecycleOwner(), userType -> {
            Bundle bundle = new Bundle();
            bundle.putString("userType", userType);
            NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.discover, true).build();
            navController.navigate(R.id.action_loginFragment_to_exploreFragment, bundle, options);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.registerButton.setOnClickListener(this::onRegisterClick);
        super.onViewCreated(view, savedInstanceState);
    }

    public void onRegisterClick(View view) {
        navController.navigate(R.id.action_loginFragment_to_registerFragment);
    }
}