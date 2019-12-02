package com.casper.Fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.casper.Game.GameSurfaceView;
import com.casper.testdrivendevelopment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    Context context;


    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ((ViewGroup)view).addView(new GameSurfaceView(context));
        return view;
    }

}
