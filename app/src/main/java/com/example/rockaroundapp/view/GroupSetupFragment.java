package com.example.rockaroundapp.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentGroupSetupBinding;
import com.example.rockaroundapp.viewmodel.GroupSetupViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class GroupSetupFragment extends Fragment {

    private NavController navController;
    private FragmentGroupSetupBinding binding;
    private GroupSetupViewModel groupSetupViewModel;
    private BottomNavigationView bottomNavigationView;
    private TextDrawable orgProfiler;
    private Toolbar toolbar;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private ActivityResultLauncher<Intent> getProfiler;
    private String userType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_group_setup, container, false);
        View view = binding.getRoot();
        groupSetupViewModel = new ViewModelProvider(this).get(GroupSetupViewModel.class);
        binding.setGroupSetupViewModel(groupSetupViewModel);
        binding.setLifecycleOwner(this);
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        orgProfiler = TextDrawable.builder().buildRect(String.valueOf(getArguments().getString("firstName").charAt(0)), generator.getRandomColor());
        binding.profileImageView.setImageDrawable(orgProfiler);
        assert getArguments() != null;
        userType = getArguments().getString("userType");
        observeViewModel();
        return view;
    }

    public void observeViewModel() {
        groupSetupViewModel.getGroupArtist().observe(getViewLifecycleOwner(), groupArtist -> {
            if (groupArtist.getStageName().isEmpty()) {
                binding.stageNameTextInputLayout.setError("Please provide a name for your group");
            } else {
                binding.stageNameTextInputLayout.setError(null);
            }
            if (groupArtist.getNoOfMembers().isEmpty()) {
                binding.noOfMembersText.setError("Please provide the number of members");
            } else if (Integer.parseInt(groupArtist.getNoOfMembers()) <= 1) {
                binding.noOfMembersText.setError("Please provide members greater than 1");
            } else {
                binding.noOfMembersText.setError(null);
            }
            if (groupArtist.getGenres() == null || groupArtist.getGenres().isEmpty()) {
                Toast.makeText(getActivity(), "Please choose at least one genre", Toast.LENGTH_SHORT).show();
            }
            if (groupArtist.getPrice().isEmpty()) {
                binding.priceText.setError("Please input your price per session");
            } else {
                binding.priceText.setError(null);
            }
            if (groupArtist.getContact().isEmpty()) {
                binding.contactText.setError("Please provide a contact number");
            } else {
                binding.contactText.setError(null);
            }
            /*if (!groupArtist.getStageName().isEmpty() && (!groupArtist.getNoOfMembers().equals("") || Integer.parseInt(groupArtist.getNoOfMembers()) > 1) &&
                    !groupArtist.getGenres().isEmpty() && !groupArtist.getPrice().isEmpty() &&
                    !groupArtist.getContactNumber().isEmpty()) {
                groupSetupViewModel.saveInfo();
            }*/
            if (!binding.stageNameTextInputLayout.isErrorEnabled() && !binding.noOfMembersText.isErrorEnabled()
                    && (!groupArtist.getGenres().isEmpty() || groupArtist.getGenres() != null)
                    && !binding.contactText.isErrorEnabled() && !binding.priceText.isErrorEnabled()) {
                groupSetupViewModel.saveInfo();
            }
        });
        groupSetupViewModel.getGenresStringMutable().observe(getViewLifecycleOwner(), genresString -> {
            if (!genresString.isEmpty()) {
                binding.genresTextInputLayout.setText(genresString);
            } else {
                binding.genresTextInputLayout.setText("");
            }
        });
        groupSetupViewModel.getSetUpSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Bundle bundle = new Bundle();
                bundle.putString("userType", userType);
                Toast.makeText(getActivity(), "Setup Successful", Toast.LENGTH_SHORT).show();
                NavOptions options = new NavOptions.Builder().setPopUpTo(R.id.discover, true).build();
                navController.navigate(R.id.action_groupSetupFragment_to_exploreFragment, bundle, options);
            }
        });
    }

    private void onAddGenreClicked(View view) {
        GenreDialogFragment dialogFragment = new GenreDialogFragment();
        Bundle type = new Bundle();
        type.putString("userType", userType);
        dialogFragment.setArguments(type);
        dialogFragment.show(getChildFragmentManager(), "dialog");
    }

    private void showGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent.createChooser(intent, "Select your profile image");
        getProfiler.launch(intent);
    }

    public void setUpProfileResult() {
        getProfiler = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                try {
                    if (result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        Picasso.get().load(selectedImageUri).into(binding.profileImageView);
                        groupSetupViewModel.setProfileImageUri(selectedImageUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.addGenreButton.setOnClickListener(this::onAddGenreClicked);
        binding.uploadImageButton.setOnClickListener(this::showGallery);
        setUpProfileResult();
        super.onViewCreated(view, savedInstanceState);
    }
}