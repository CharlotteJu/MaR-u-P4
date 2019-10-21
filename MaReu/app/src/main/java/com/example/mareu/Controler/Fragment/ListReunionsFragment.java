package com.example.mareu.Controler.Fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.Services.*;

import com.example.mareu.Di.DI;
import com.example.mareu.Services.ReunionApiService;

import com.example.mareu.Controler.Ui.ReunionListRecyclerViewAdapter;
import com.example.mareu.Model.Reunion;
import com.example.mareu.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListReunionsFragment extends Fragment implements ReunionListRecyclerViewAdapter.clickToDeleteInterface
{
    private RecyclerView mRecyclerView;
    public List<Reunion> mReunions;
    private ReunionApiService mApiService;
    RecyclerView.Adapter adapter;
    private int dimenSize;


    public ListReunionsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        dimenSize = getResources().getInteger(R.integer.title_list_reunions_size);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_reunions, container, false);
        mApiService = new DummyReunionApiService();
        Context context = v.getContext();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_list_reunions);
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
        adapter = new ReunionListRecyclerViewAdapter(mReunions, dimenSize, this::clickToDelete, this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clickToDelete(int position)
    {
        Reunion reunion = mReunions.get(position);
        mApiService.deleteReunion(reunion);
        initList();
    }

    public void dataChanged()
    {
        adapter.notifyDataSetChanged();
    }
}

