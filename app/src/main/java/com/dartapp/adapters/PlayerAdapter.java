package com.dartapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dartapp.R;
import com.dartapp.model.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private final List<Player> playerList;
    private final OnItemClickListener listener;

    public PlayerAdapter(List<Player> playerList, OnItemClickListener listener) {
        this.playerList = playerList;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updatePlayerList(List<Player> newPlayerList) {
        playerList.clear();
        playerList.addAll(newPlayerList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onDeleteClick(Player player);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playerList.get(position);
        holder.bind(player);
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(player);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }


    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        private final TextView playerIdTextView, playerNameTextView, playerDoublesTextView;

        private final ImageButton btnDelete;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            playerIdTextView = itemView.findViewById(R.id.playerIdTextView);
            playerNameTextView = itemView.findViewById(R.id.playerNameTextView);
            playerDoublesTextView = itemView.findViewById(R.id.playerDoublesTextView);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            // Set up the click listener for the delete button
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onDeleteClick(playerList.get(position));
                    }
                }
            });
        }

        // Bind data to the views
        @SuppressLint("SetTextI18n")
        public void bind(Player player) {
            playerIdTextView.setText("ID: " + player.getId());
            playerNameTextView.setText("Name: " + player.getName());
            playerDoublesTextView.setText("Favorite Doubles: " + player.getFavouriteDoubles());
        }
    }

}
