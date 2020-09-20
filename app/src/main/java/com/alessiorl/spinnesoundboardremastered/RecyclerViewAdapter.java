package com.alessiorl.spinnesoundboardremastered;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private Sound[] mData;
    private AudioPlayer player;
    private int mvolume;


    public RecyclerViewAdapter(Context mContext, Sound[] mData , AudioPlayer player, int mvolume) {
        this.mContext = mContext;
        this.mData = mData;
        this.player = player;
        this.mvolume = mvolume;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardveiw_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.sound_title.setText(mData[position].getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerViewAdapter.this.player.playSound(mContext, mData[position].getResourceId(), mvolume);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sound_title;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);

            sound_title = (TextView) itemView.findViewById(R.id.title_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);


        }
    }


}
