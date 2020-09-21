package com.alessiorl.spinnesoundboard.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alessiorl.spinnesoundboard.Adapters.SoundAdapter;
import com.alessiorl.spinnesoundboard.Helpers.Sound;
import com.alessiorl.spinnesoundboard.Helpers.SoundStore;
import com.alessiorl.spinnesoundboard.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Sound> soundItems = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(new SoundAdapter(soundItems, getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        soundItems.addAll(SoundStore.getSounds(getActivity()));
        return root;
    }
}