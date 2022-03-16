package com.example.rockaroundapp.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.VenueCardBinding;
import com.example.rockaroundapp.listeners.VenueListener;
import com.example.rockaroundapp.model.Venue;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> {

    private List<Venue> venueList;
    private LayoutInflater layoutInflater;
    private final VenueListener venueListener;


    public VenueAdapter(VenueListener venueListener) {
        this.venueList = new ArrayList<>();
        this.venueListener = venueListener;
    }

    @NonNull
    @Override
    public VenueAdapter.VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        VenueCardBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.venue_card, parent, false);
        return new VenueViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        holder.bindVenue(venueList.get(position));
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }

    public void updateVenueList(final List<Venue> venueList) {
        this.venueList = venueList;
        notifyDataSetChanged();
    }

    class VenueViewHolder extends RecyclerView.ViewHolder {

        private VenueCardBinding binding;

        public VenueViewHolder(VenueCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindVenue(Venue venue) {
            binding.setVenueModel(venue);
            if (StringUtils.isEmptyOrWhitespaceOnly(venue.getProfileImg())) {
                binding.profileImage.setImageDrawable(venue.getDefaultProfiler());
            }
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> venueListener.onVenueClicked(venue));
        }
    }
}
