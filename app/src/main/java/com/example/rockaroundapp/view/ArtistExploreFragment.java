package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockaroundapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ArtistExploreFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        return inflater.inflate(R.layout.fragment_artist_explore, container, false);
    }
}