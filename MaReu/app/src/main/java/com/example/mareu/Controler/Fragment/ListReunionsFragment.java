package com.example.mareu.Controler.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< Updated upstream
import com.example.mareu.Controler.Services.ReunionApiService;
=======
import com.example.mareu.Di.DI;
import com.example.mareu.Services.*;
>>>>>>> Stashed changes
import com.example.mareu.Controler.Ui.ReunionListRecyclerViewAdapter;
import com.example.mareu.Model.Reunion;
import com.example.mareu.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListReunionsFragment extends Fragment
{
    private RecyclerView mRecyclerView;
    private List<Reunion> mReunions;
    private ReunionApiService mApiService;

    public ListReunionsFragment() {
        // Required empty public constructor
    }


    public static ListReunionsFragment newInstance()
    {
        ListReunionsFragment fragment = new ListReunionsFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_reunions, container, false);
        Context context = v.getContext();
        mRecyclerView = (RecyclerView) v;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return v;
    }

    /**
     * Init the List of neighbours
     */
    private void initList()
    {
        mReunions = mApiService.getReunions();
        mRecyclerView.setAdapter(new ReunionListRecyclerViewAdapter(mReunions));
    }

}

