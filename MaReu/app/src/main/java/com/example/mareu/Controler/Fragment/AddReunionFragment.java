package com.example.mareu.Controler.Fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.mareu.Model.Reunion;
import com.example.mareu.Model.Room;
import com.example.mareu.R;
import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;
import com.example.mareu.Services.RoomsGenerator;

import java.util.Calendar;

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

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private String[] mNameRooms ;
    private CreateReunionListener mCreateReunionListener;
    private Reunion mReunion;
    private ReunionApiService mApiService;

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
        mApiService = new DummyReunionApiService();
        mReunion = new Reunion();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_reunion, container, false);
        ButterKnife.bind(this, v);

        mNameRooms = generateNameRooms();

        mRoomSpinner.setAdapter(configSpinnerRooms());
        mHourSpinner.setAdapter(configSpinner(R.array.hour_spinner));
        mMinutesSpinner.setAdapter(configSpinner(R.array.minutes_spinner));

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogDate = new DatePickerDialog(getContext(), mDateSetListener,
                        year, month, day);
                //block the calendar to current date
                dialogDate.getDatePicker().setMinDate(System.currentTimeMillis());
                dialogDate.show();
            }
        });

        mRoomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String nameRoom = adapterView.getItemAtPosition(position).toString();
                Room room;
                for (int i = 0;  i < RoomsGenerator.getListRooms().length; i ++)
                {
                    if (nameRoom == RoomsGenerator.getListRooms()[i].getmName())
                    {
                        room = RoomsGenerator.getListRooms()[i];
                        mReunion.setmRoom(room);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        mHourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int hourRoom = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
                mReunion.setmTime(hourRoom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mApiService.addReunion(mReunion);
            }
        });

        return v;

    }


    /**
     * Method to give an Adapter for the spinners
     * @param res : The link with the res spinner file
     * @return adapter
     */
    private ArrayAdapter<CharSequence> configSpinner (int res)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (getActivity(), res, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    private ArrayAdapter<String> configSpinnerRooms ()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mNameRooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    /**
     * Method to take the name of Rooms
     * This tab be used for the spinner
     * @return
     */
    private String[] generateNameRooms ()
    {
        String[] tab = new String[RoomsGenerator.getListRooms().length];

        for (int i = 0; i < RoomsGenerator.getListRooms().length; i ++)
        {
            tab[i] = RoomsGenerator.getListRooms()[i].getmName();
        }
        return tab;
    }



    public interface CreateReunionListener{
        void onCreateReunion(Reunion reunion);
    }

}
