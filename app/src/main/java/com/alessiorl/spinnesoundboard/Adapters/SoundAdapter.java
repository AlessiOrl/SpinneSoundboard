package com.alessiorl.spinnesoundboard.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.alessiorl.spinnesoundboard.Helpers.AudioPlayer;
import com.alessiorl.spinnesoundboard.Helpers.FavDB;
import com.alessiorl.spinnesoundboard.Helpers.Sound;
import com.alessiorl.spinnesoundboard.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ViewHolder> {

    private ArrayList<Sound> soundItems;
    private AudioPlayer player;
    private Context context;
    private FavDB favDB;

    public SoundAdapter(ArrayList<Sound> soundItems, Context context) {
        this.soundItems = soundItems;
        this.context = context;
        this.player = new AudioPlayer();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound,
                parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SoundAdapter.ViewHolder holder, int position) {
        final Sound soundItem = soundItems.get(position);

        readCursorData(soundItem, holder);
        holder.titleTextView.setText(soundItem.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundAdapter.this.player.playSound(context, soundItem.getResourceId(), 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return soundItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        Button favBtn;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            favBtn = itemView.findViewById(R.id.favBtn);
            cardView = (CardView) itemView.findViewById(R.id.cardview_sound);

            //add to fav btn
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Sound soundItem = soundItems.get(position);
                    likeClick(soundItem, favBtn);
                }
            });
        }
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Sound soundItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(soundItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
                soundItem.setFavStatus(item_fav_status);

                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

    // like click
    private void likeClick(Sound soundItem, Button favBtn) {
        DatabaseReference refLike = FirebaseDatabase.getInstance().getReference().child("likes");
        final DatabaseReference upvotesRefLike = refLike.child(soundItem.getKey_id());

        if (soundItem.getFavStatus().equals("0")) {

            soundItem.setFavStatus("1");
            favDB.insertIntoTheDatabase(soundItem.getTitle(), soundItem.getResourceId(),
                    soundItem.getKey_id(), soundItem.getFavStatus());
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
            favBtn.setSelected(true);

        } else if (soundItem.getFavStatus().equals("1")) {
            soundItem.setFavStatus("0");
            favDB.remove_fav(soundItem.getKey_id());
            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);
            favBtn.setSelected(false);
        }

    }
}
