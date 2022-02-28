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

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private List<Artist> artistList;
    private LayoutInflater layoutInflater;
    private ArtistListener artistListener;

    public ArtistAdapter(ArtistListener artistListener) {
        this.artistList = new ArrayList<>();
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
        holder.bindArtist(artistList.get(position));
    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public void updateArtistList(final List<Artist> artistList) {
        this.artistList.clear();
        this.artistList = artistList;
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
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(v -> artistListener.onArtistClicked(artist));
        }
    }
}
