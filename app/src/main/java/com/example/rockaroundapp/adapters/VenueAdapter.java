package com.example.rockaroundapp.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    private Drawable starFilled;
    private Drawable starOutline;
    private Drawable halfStar;


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
        starFilled = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_star_outline_24);
        halfStar = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_star_half_24);
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

        private final VenueCardBinding binding;

        public VenueViewHolder(VenueCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindVenue(Venue venue) {
            binding.setVenueModel(venue);
            mapRating(venue.getAvgOverallRating());
            if (StringUtils.isEmptyOrWhitespaceOnly(venue.getProfileImg())) {
                binding.profileImage.setImageDrawable(venue.getDefaultProfiler());
            }
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> venueListener.onVenueClicked(venue));
        }

        private void mapRating(double rating) {
            if (rating == 0.0) {
                binding.star1.setBackground(starOutline);
                binding.star2.setBackground(starOutline);
                binding.star3.setBackground(starOutline);
                binding.star4.setBackground(starOutline);
                binding.star5.setBackground(starOutline);
            } else {
                if (rating <= 0.50) {
                    binding.star1.setBackground(halfStar);
                }
                if (rating > 0.50) {
                    binding.star1.setBackground(starFilled);
                }
                if (rating > 1.00 && rating <= 1.50) {
                    binding.star2.setBackground(halfStar);
                }
                if (rating > 1.50) {
                    binding.star2.setBackground(starFilled);
                }
                if (rating > 2.00 && rating <= 2.50) {
                    binding.star3.setBackground(halfStar);
                }
                if (rating > 2.50) {
                    binding.star3.setBackground(starFilled);
                }
                if (rating > 3.00 && rating <= 3.50) {
                    binding.star4.setBackground(halfStar);
                }
                if (rating > 3.50) {
                    binding.star4.setBackground(starFilled);
                }
                if (rating > 4.00 && rating <= 4.50) {
                    binding.star5.setBackground(halfStar);
                }
                if (rating > 4.50) {
                    binding.star5.setBackground(starFilled);
                }
            }
        }
    }
}
