package com.alessiorl.spinnesoundboard.Fragments;

import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alessiorl.spinnesoundboard.Activities.MainActivity;
import com.alessiorl.spinnesoundboard.Adapters.SoundAdapter;
import com.alessiorl.spinnesoundboard.Helpers.AudioPlayer;
import com.alessiorl.spinnesoundboard.Helpers.Sound;
import com.alessiorl.spinnesoundboard.Helpers.SoundStore;
import com.alessiorl.spinnesoundboard.R;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    private ArrayList<Sound> soundItems = new ArrayList<>();
    private AudioPlayer player = new AudioPlayer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(new SoundAdapter(soundItems, getActivity(), player));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        soundItems.addAll(SoundStore.getSounds(getActivity()));
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Random rand = new Random();
        Sound sound = this.soundItems.get(rand.nextInt(soundItems.size()));
        player.playSound(getActivity(), sound.getResourceId(), 1);
        Toast.makeText(requireActivity(), "Playing: "+ sound.getTitle(), Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}