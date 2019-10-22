package com.example.mareu.Controler.Fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mareu.Controler.Activity.MainActivity;
import com.example.mareu.Controler.Ui.MailListRecyclerViewAdapter;
import com.example.mareu.Model.Reunion;
import com.example.mareu.Model.Room;
import com.example.mareu.R;
import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;
import com.example.mareu.Services.RoomsGenerator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddReunionFragment extends Fragment implements MailListRecyclerViewAdapter.MailsToDelete {

    @BindView(R.id.arf_subject_edit_text) EditText mSubjectEdit;
    @BindView(R.id.arf_room) Spinner mRoomSpinner;
    @BindView(R.id.arf_date) Button mDateButton;
    @BindView(R.id.arf_date_txt) TextView mDateTxt;
    @BindView(R.id.arf_hours_spinner) Spinner mHourSpinner;
    @BindView(R.id.arf_hours_txt) TextView mHourTxt;
    @BindView(R.id.arf_add_mails_button) Button mAddMailsButton;
    @BindView(R.id.arf_mail) EditText mMailEditText;
    @BindView(R.id.arf_liste_mails) RecyclerView mRecyclerView;
    @BindView(R.id.arf_final_button) Button mFinalButton;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private List<String> mNameRooms ;
    private Reunion mReunion;
    private ReunionApiService mApiService;
    private List<String> mMailsList;
    private MailListRecyclerViewAdapter mAdapter;

    public AddReunionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = new DummyReunionApiService();
        mReunion = new Reunion();
        mMailsList = new ArrayList<>();
        mAdapter = new MailListRecyclerViewAdapter(mMailsList, this::clickToDelete);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_reunion, container, false);
        ButterKnife.bind(this, v);

        configureRecyclerView();

        mReunion.setmSubject(String.valueOf(mSubjectEdit.getText()));

        mDateSetListener = generateDatePickerDialog();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDialogCalendar();
            }
        });

        updateSpinnerHour();

        mAddMailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = String.valueOf(mMailEditText.getText());
                mMailsList.add(mail);
                mReunion.setmEmails(mMailsList);
                initListMails(mMailsList);
                mMailEditText.setText("");
            }
        });

        mFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (mReunion.completeReunion())
                {
                    mApiService.addReunion(mReunion);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    mReunion.setmSubject(String.valueOf(mSubjectEdit.getText()));
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
     * @param res : The link with the res spinner file
     * @return adapter
     */
    public ArrayAdapter<CharSequence> configSpinner (int res)
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (getActivity(), res, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Method to give an Adapter for the Room spinner
     * @return adapter
     */
    private ArrayAdapter<String> configSpinnerRooms ()
    {
        mNameRooms = mApiService.generateNameRooms(mReunion);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mNameRooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    /**
     * Method to update the Room's spinner
     * Display the spinner if the date is not empty
     */
    private void updateSpinnerRoom()
    {
        mRoomSpinner.setEnabled(mReunion.getmDate() != null && mReunion.getmTime() != null);

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
    }

    /**
     * Method to update the informations of Spinner Room
     */
    private void updateSpinnerHour()
    {
        mHourSpinner.setAdapter(configSpinner(R.array.hour_spinner));
        mHourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int intHour = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
                String stringHour;
                if(intHour < 10)
                {
                    stringHour = "0"+intHour+":00";
                }
                else
                {
                    stringHour = intHour+":00";
                }
                mReunion.setmTime(stringHour);
                mHourTxt.setText(stringHour);
                updateSpinnerRoom();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    /////////////DATE//////////////////

    /**
     * Give an DatePickerDialog for the date
     * @return DatePickerDialog.OnDateSetListener
     */
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
                updateSpinnerRoom();
            }
        };
        return dateSetListener;
    }

    /**
     * Display a calendar
     */
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

    /////////////MAILS//////////////////

    /**
     * Configure the RecyclerView for the mails
     */
    private void configureRecyclerView()
    {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * Initialize the list of mails
     * @param listMails
     */
    private void initListMails (List<String> listMails)
    {
        this.mAdapter = new MailListRecyclerViewAdapter(listMails,this::clickToDelete);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Interface method to remove an item of the list of mails
     * @param position
     */
    @Override
    public void clickToDelete(int position) {
        String mail = mMailsList.get(position);
        mMailsList.remove(mail);
        initListMails(mMailsList);
    }

}
