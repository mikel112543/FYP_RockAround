package com.example.rockaroundapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.ArtistCardBinding;
import com.example.rockaroundapp.listeners.ArtistListener;
import com.example.rockaroundapp.model.Artist;
import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<Artist> artistListHolder;
    private LayoutInflater layoutInflater;
    private final ArtistListener artistListener;

    public ArtistAdapter(ArtistListener artistListener) {
        this.artistListHolder = new ArrayList<>();
        this.artistListener = artistListener;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ArtistCardBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.artist_card, parent, false);
        return new ArtistViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.bindArtist(artistListHolder.get(position));
    }

    @Override
    public int getItemCount() {
        return artistListHolder.size();
    }

    public void updateArtistList(List<Artist> artistList) {
        this.artistListHolder = artistList;
        notifyDataSetChanged();
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        private ArtistCardBinding binding;

        public ArtistViewHolder(ArtistCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindArtist(Artist artist) {
            binding.setArtistModel(artist);
            mapRating(artist.getAvgOverallRating());
            if (StringUtils.isEmptyOrWhitespaceOnly(artist.getProfileImg())) {
                binding.profileImage.setImageDrawable(artist.getDefaultProfiler());
            }
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> artistListener.onArtistClicked(artist));
        }

        private void mapRating(double rating) {
            int starFilled = R.drawable.ic_baseline_star_rate_50;
            int halfStar = R.drawable.ic_baseline_star_half_24;
            int starOutline = R.drawable.ic_baseline_star_outline_24;
            if (rating == 0.0) {
                binding.star1.setImageResource(starOutline);
                binding.star2.setImageResource(starOutline);
                binding.star3.setImageResource(starOutline);
                binding.star4.setImageResource(starOutline);
                binding.star5.setImageResource(starOutline);
            } else {
                if (rating <= 0.50) {
                    binding.star1.setImageResource(halfStar);
                }
                if (rating > 0.50) {
                    binding.star1.setImageResource(starFilled);
                }
                if (rating > 1.00 && rating <= 1.50) {
                    binding.star2.setImageResource(halfStar);
                }
                if (rating > 1.50) {
                    binding.star2.setImageResource(starFilled);
                }
                if (rating > 2.00 && rating <= 2.50) {
                    binding.star3.setImageResource(halfStar);
                }
                if (rating > 2.50) {
                    binding.star3.setImageResource(starFilled);
                }
                if (rating > 3.00 && rating <= 3.50) {
                    binding.star4.setImageResource(halfStar);
                }
                if (rating > 3.50) {
                    binding.star4.setImageResource(starFilled);
                }
                if (rating > 4.00 && rating <= 4.50) {
                    binding.star5.setImageResource(halfStar);
                }
                if (rating > 4.50) {
                    binding.star5.setImageResource(starFilled);
                }
            }
        }
    }
}
