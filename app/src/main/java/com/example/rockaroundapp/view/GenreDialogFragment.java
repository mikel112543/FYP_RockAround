package com.example.rockaroundapp.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentGenreDialogBinding;
import com.example.rockaroundapp.viewmodel.GroupSetupViewModel;
import com.example.rockaroundapp.viewmodel.SoloSetupViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenreDialogFragment extends DialogFragment {

    private String[] genreList;
    private List<Integer> selectedGenres = new ArrayList<>();
    private boolean[] checkedGenres;
    private List<String> selectedNames = new ArrayList<>();
    private String genreText;
    private FragmentGenreDialogBinding fragmentGenreDialogBinding;
    private SoloSetupViewModel soloSetupViewModel;
    private GroupSetupViewModel groupSetupViewModel;

    public GenreDialogFragment() {
        // Required empty public constructor
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
                        selectedNames.add(genreList[selectedGenres.get(i)]);
                        if (i != selectedGenres.size() - 1) {
                            builder.append(", ");
                        }
                    }

                    genreText = builder.toString();
                    try {
                        soloSetupViewModel.setGenre(selectedNames);
                        soloSetupViewModel.setGenresString(genreText);
                    } catch (Exception e) {
                        Log.e("POST ERROR", e.getMessage());
                    }

                    dialog.dismiss();

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("Clear all", (dialog, which) -> {
                    Arrays.fill(checkedGenres, false);
                    genreText = "";
                    selectedNames.clear();
                    selectedGenres.clear();
                    soloSetupViewModel.setGenre(selectedNames);
                    soloSetupViewModel.setGenresString(genreText);
                });
        return alertDialogBuilder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentGenreDialogBinding = FragmentGenreDialogBinding.inflate(inflater, container, false);
        fragmentGenreDialogBinding.setDialogViewModel(soloSetupViewModel);
        soloSetupViewModel = new ViewModelProvider(requireParentFragment()).get(SoloSetupViewModel.class);
        groupSetupViewModel = new ViewModelProvider(requireParentFragment()).get(GroupSetupViewModel.class);

        return fragmentGenreDialogBinding.getRoot();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_genre_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}