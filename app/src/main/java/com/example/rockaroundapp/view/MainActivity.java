package com.example.rockaroundapp.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.rv_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        bottomNavigationView = findViewById(R.id.bottom_navbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.addOnDestinationChangedListener((navController, destination, bundle) -> {
            if(destination.getId() == R.id.userReviewsFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            }else if(destination.getId() == R.id.artistProfile) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }else if(destination.getId() == R.id.account) {
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });

        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        //ft.replace(R.id.fragmentContainerView, new LoginFragment());
        // ft.commit();

    }

    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}