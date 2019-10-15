package com.example.mareu.Controler.Fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.mareu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReunionFragment extends Fragment {

    @BindView(R.id.arf_subject_edit_text)
    EditText mSubjectEdit;

    @BindView(R.id.arf_room)
    Spinner mRoomSpinner;

    @BindView(R.id.arf_date)
    Button mDateButton;

    @BindView(R.id.arf_hour)
    Spinner mHourSpinner;

    @BindView(R.id.arf_minutes)
    Spinner mMinutesSpinner;

    @BindView(R.id.arf_add_mails_button)
    Button mAddMailsButton;

    @BindView(R.id.arf_mail)
    EditText mMailEditText;

    @BindView(R.id.arf_scroll_view_mails)
    ScrollView mScrollViewMails;

    @BindView(R.id.arf_final_button)
    Button mFinalButton;


    public AddReunionFragment() {
        // Required empty public constructor
    }

    public AddReunionFragment newInstance()
    {
        AddReunionFragment fragment = new AddReunionFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_reunion, container, false);

        ButterKnife.bind(this, v);

        // Inflate the layout for this fragment
        return v;


    }

}
