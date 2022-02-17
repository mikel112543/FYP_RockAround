package com.example.rockaroundapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockaroundapp.R;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreDialogFragment extends DialogFragment {

    private String[] genreList;
    private List<Integer> selectedGenres = new ArrayList<>();
    private boolean[] checkedGenres;
    private String genreText;
    private NavController navController;


    public GenreDialogFragment() {
        // Required empty public constructor
    }

    public String getGenreText() {
        return genreText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Genres");
        genreList = getResources().getStringArray(R.array.genres);
        checkedGenres = new boolean[genreList.length];
        alertDialogBuilder.setMultiChoiceItems(genreList, checkedGenres, (dialog, which, isChecked) -> {
            if (isChecked) {
                selectedGenres.add(which);
                checkedGenres[which] = true;
            } else if (selectedGenres.contains(which)) {
                checkedGenres[which] = false;
                selectedGenres.remove(which);
            }
        })
                .setPositiveButton("OK", (dialog, which) -> {
                    StringBuilder builder = new StringBuilder();
                    genreText = "";
                    for (int i = 0; i < selectedGenres.size(); i++) {
                        builder.append(genreList[selectedGenres.get(i)]);
                        if (i != selectedGenres.size() - 1) {
                            builder.append(", ");
                        }
                    }
                    genreText = builder.toString();
                    navController.navigate(GenreDialogFragmentDirections.actionGenreDialogToSoloSetupFragment());
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("Clear all", (dialog, which) -> {
                    Arrays.fill(checkedGenres, false);
                    selectedGenres.clear();
                });
        return alertDialogBuilder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}