package com.example.rockaroundapp.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.ReviewCardBinding;
import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.model.VenueReview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private List<ArtistReview> artistReviews;
    private List<VenueReview> venueReviews;
    private List<Venue> artistReviewers;
    private LayoutInflater layoutInflater;
    private final String currentUserType;
    private Drawable starFilled;
    private Drawable starOutline;
    private Drawable halfStar;

    public ReviewsAdapter(String currentUserType) {
        this.currentUserType = currentUserType;
        artistReviews = new ArrayList<>();
        venueReviews = new ArrayList<>();
        artistReviewers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ReviewCardBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.review_card, parent, false);
        starFilled = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_star_outline_24);
        halfStar = ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_baseline_star_half_24);
        return new ReviewViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.bindArtistReview(artistReviews.get(position));
        if (!artistReviewers.isEmpty()) {
            holder.bindArtistReviewer(artistReviewers.get(position));
        }

    }

    @Override
    public int getItemCount() {
        if (currentUserType.equalsIgnoreCase("venue")) {
            return artistReviews.size();
        }
        return venueReviews.size();
    }

    public void updateArtistReviewList(List<ArtistReview> reviews, List<Venue> artistReviewers) {
        this.artistReviews = reviews;
        this.artistReviewers = artistReviewers;
        notifyDataSetChanged();
    }

/*    public void updateArtistReviewers(List<Venue> reviewers) {
        this.artistReviewers = reviewers;
        notifyDataSetChanged();
    }*/

    public void updateVenueReviews(List<VenueReview> reviews) {
        this.venueReviews = reviews;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private final ReviewCardBinding binding;

        public ReviewViewHolder(ReviewCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindArtistReview(ArtistReview review) {
            binding.setReviewModel(review);
            binding.executePendingBindings();
            mapRating(review.getOverallRating());
        }

        public void bindVenueReview(VenueReview venueReview) {

        }

        public void bindArtistReviewer(Venue venue) {
            binding.reviewerName2.setText(venue.getVenueName());
            Picasso.get().load(venue.getProfileImg())
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .fit()
                    .centerCrop()
                    .into(binding.reviewerProfileImg2);
        }

        private void mapRating(double rating) {
            if (rating == 0.0) {
                binding.star1.setBackground(starOutline);
                binding.star2.setBackground(starOutline);
                binding.star3.setBackground(starOutline);
                binding.star4.setBackground(starOutline);
                binding.star5.setBackground(starOutline);
            } else if (rating <= 0.50) {
                binding.star1.setBackground(halfStar);
            } else if (rating <= 1.00) {
                binding.star1.setBackground(starFilled);
            } else if (rating <= 1.50) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(halfStar);
            } else if (rating <= 2.00) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
            } else if (rating <= 2.50) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
                binding.star3.setBackground(halfStar);
            } else if (rating <= 3.00) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
                binding.star3.setBackground(starFilled);
            } else if (rating <= 3.50) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
                binding.star3.setBackground(starFilled);
                binding.star4.setBackground(halfStar);
            } else if (rating <= 4.00) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
                binding.star3.setBackground(starFilled);
                binding.star4.setBackground(starFilled);
            } else if (rating <= 4.50) {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
                binding.star3.setBackground(starFilled);
                binding.star4.setBackground(starFilled);
                binding.star5.setBackground(halfStar);
            } else {
                binding.star1.setBackground(starFilled);
                binding.star2.setBackground(starFilled);
                binding.star3.setBackground(starFilled);
                binding.star4.setBackground(starFilled);
                binding.star5.setBackground(starFilled);
            }
        }
    }
}
