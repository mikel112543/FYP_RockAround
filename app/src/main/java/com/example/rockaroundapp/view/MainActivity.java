package com.example.rockaroundapp.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.viewmodel.AccountViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private String currentUserType;
    private AccountViewModel viewModel;
    private Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        recyclerView = findViewById(R.id.rv_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        bottomNavigationView = findViewById(R.id.bottom_navbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.discover_btn) {
                Bundle bundle = new Bundle();
                bundle.putString("userType", getCurrentUserType());
                navController.navigate(R.id.discover, bundle);
            } else if (item.getItemId() == R.id.account_btn) {
                Bundle bundle = new Bundle();
                bundle.putString("currentUserType", currentUserType);
                navController.navigate(R.id.account, bundle);
            }else{
                Bundle bundle = new Bundle();
                bundle.putString("currentUserType", currentUserType);
                navController.navigate(R.id.mapsFragment, bundle);
            }
            return true;
        });
        navController.addOnDestinationChangedListener((navController, destination, bundle) -> {
            if (destination.getId() == R.id.userReviewsFragment) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
            } else if (destination.getId() == R.id.artistProfile) {
                bottomNavigationView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            } else if (destination.getId() == R.id.account) {
                recyclerView.setVisibility(View.INVISIBLE);
                toolbar.setTitle("Account");
            } else if (destination.getId() == R.id.discover) {
                if(currentUserType == null) {
                    getCurrentUserType();
                }
            } else if(destination.getId() == R.id.loginFragment) {
                currentUserType = null;
                recyclerView.setVisibility(View.INVISIBLE);
            }else if(destination.getId() == R.id.mapsFragment) {
                recyclerView.setVisibility(View.INVISIBLE);
                bottomNavigationView.setVisibility(View.VISIBLE);
                toolbar.setTitle("Map");
            }else if(destination.getId() == R.id.venueProfileFragment) {
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });
    }

    private String getCurrentUserType() {
        viewModel.getUserType().observe(this, userType -> currentUserType = userType);
        return currentUserType;
    }

    public boolean onSupportNavigateUp() {
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}