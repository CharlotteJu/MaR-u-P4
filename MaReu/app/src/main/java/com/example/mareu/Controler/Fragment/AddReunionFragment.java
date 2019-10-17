package com.example.mareu.Controler.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    @BindView(R.id.arf_date_txt)
    TextView mDateTxt;
    @BindView(R.id.arf_hours_txt)
    TextView mHourTxt;
    @BindView(R.id.arf_add_mails_button)
    Button mAddMailsButton;
    @BindView(R.id.arf_mail)
    EditText mMailEditText;
    @BindView(R.id.arf_final_button)
    Button mFinalButton;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private String[] mNameRooms ;
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
        mNameRooms = generateNameRooms();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_reunion, container, false);
        ButterKnife.bind(this, v);

        mReunion.setmSubject(String.valueOf(mSubjectEdit.getText()));

        mRoomSpinner.setAdapter(configSpinnerRooms());
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

        mDateSetListener = generateDatePickerDialog();
        mTimeSetListener = generateTimePickerDialog();

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDialogTimer();
                configureDialogCalendar();
            }
        });

        mFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (mReunion.completeReunion())
                {
                    mApiService.addReunion(mReunion);
                }
                else
                {
                    Toast.makeText(getContext(), "Merci de renseigner toutes les informations", Toast.LENGTH_LONG).show();
                }
            }
        });

        return v;

    }

/////////////////////////////CONFIGURE METHODS////////////////////////////////////


    /////////////SPINNER//////////////////
    /**
     * Method to give an Adapter for the spinners
     * @return adapter
     */
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

    /////////////DATE//////////////////

    private DatePickerDialog.OnDateSetListener generateDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String dayString = String.valueOf(day);
                String monthString = String.valueOf(month+1);

                if(day < 10)
                {
                    dayString = "0" + String.valueOf(day);
                }
                if (month+1 < 10)
                {
                    monthString = "0" + String.valueOf(month+1);
                }

                mReunion.setmDate(dayString + "/" + monthString + "/" + year);
                mDateTxt.setText(dayString + "/" + monthString + "/" + year);
            }
        };
        return dateSetListener;
    }

    private void configureDialogCalendar() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDate = new DatePickerDialog(getContext(), mDateSetListener,
                year, month, day);
        dialogDate.getDatePicker().setMinDate(System.currentTimeMillis());
        dialogDate.show();
    }

    /////////////TIME//////////////////

    private TimePickerDialog.OnTimeSetListener generateTimePickerDialog (){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {

                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minutes);

                if(hour<10)
                {
                    hourString = "0" + String.valueOf(hour);
                }
                if (minutes<10)
                {
                    minuteString = 0 + String.valueOf(minutes);
                }

                mReunion.setmTime(hourString + ":" + minuteString);
                mHourTxt.setText(hourString + ":" + minuteString);
            }
        };
        return timeSetListener;
    }

    private void configureDialogTimer() {

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), mTimeSetListener,
                hour, minute, true);

        timePickerDialog.show();
    }



    public interface CreateReunionListener{
        void onCreateReunion(Reunion reunion);
    }

}
