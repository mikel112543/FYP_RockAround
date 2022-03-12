package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentAccountBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private NavController navController;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUid = auth.getUid();
    private FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        View view = binding.getRoot();
        binding.setLifecycleOwner(this);
        return view;
    }
}