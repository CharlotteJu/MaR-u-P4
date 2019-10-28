package com.example.mareu.Controler.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.Controler.Fragment.AddReunionFragment;
import com.example.mareu.Controler.Fragment.ListReunionsFragment;
import com.example.mareu.Model.Room;
import com.example.mareu.R;
import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;
import com.example.mareu.Services.RoomsGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity {


    private ListReunionsFragment mListReunionsFragments;
    private AddReunionFragment mAddReunionFragment;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ReunionApiService mApiService;

    // For the filters
    private String mDate;
    private Room mRoom;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = new DummyReunionApiService();

        configureMainFragment();
        configureDetailsFragment();
        configureToolbar();
        configureFabButton();

        mDateSetListener = generateDatePickerDialog();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.no_filter :
                mListReunionsFragments.mReunions = mApiService.getReunions();
                mListReunionsFragments.dataChanged();
                return true;

            case R.id.filter_date :

                mDateSetListener = generateDatePickerDialog();
                configureDialogCalendar();
                return true;

            case R.id.filter_room :

                configureSpinnerRoom();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /////////////////CONFIGURE////////////////

    /**
     * Display ListReunionsFragment
     */
    public void configureMainFragment()
    {
        mListReunionsFragments = (ListReunionsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_reunions);
        if (mListReunionsFragments == null && findViewById(R.id.activity_details_container) == null)
        {
            mListReunionsFragments = ListReunionsFragment.newInstance(false);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, mListReunionsFragments).commit();
        }
        else if (mListReunionsFragments == null && findViewById(R.id.activity_details_container) != null)
        {
            mListReunionsFragments = ListReunionsFragment.newInstance(true);
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, mListReunionsFragments).commit();
        }
    }


    /**
     * Display AddReunionFragment - Landscape
     */
    private void configureDetailsFragment()
    {
        mAddReunionFragment = (AddReunionFragment) getSupportFragmentManager().findFragmentById(R.id.add_reunion_fragment);

        if(mAddReunionFragment == null && findViewById(R.id.activity_details_container) != null)
        {
            mAddReunionFragment = new AddReunionFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_details_container, mAddReunionFragment).commit();
        }
    }


    /**
     * Configure the button to switch activity
     */
    private void configureFabButton()
    {
        fab = findViewById(R.id.fab);

        if (findViewById(R.id.add_reunion_fragment) == null)
        {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mAddReunionFragment == null)
                    {
                        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }


    /**
     * Configure the toolbar
     */
    private void configureToolbar()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }




    /////////////////FILTERS////////////////

    /**
     * Display a calendar
     */
    private void configureDialogCalendar() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDate = new DatePickerDialog( this, mDateSetListener,
                year, month, day);
        dialogDate.getDatePicker().setMinDate(System.currentTimeMillis());
        dialogDate.show();
    }


    /**
     * Give an DatePickerDialog for the filter Date
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

                mDate = dayString + "/" + monthString + "/" + year;
                mListReunionsFragments.mReunions = mApiService.filterDate(mDate);
                mListReunionsFragments.dataChanged();
            }
        };
        return dateSetListener;
    }

    /**
     * Configure the Spinner to display an AlertBuilder of Rooms
     */
    private void configureSpinnerRoom()
    {
        AlertDialog.Builder mPopUp = new AlertDialog.Builder(this);

        View v = LayoutInflater.from(this).inflate(R.layout.dialog_spinner, null);
        Spinner spinner = v.findViewById(R.id.dsf_spinner);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < RoomsGenerator.getListRooms().length; i ++)
        {
            list.add(RoomsGenerator.getListRooms()[i].getmName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                String nameRoom = adapterView.getItemAtPosition(position).toString();
                for (int i = 0;  i < RoomsGenerator.getListRooms().length; i ++)
                {
                    if (nameRoom == RoomsGenerator.getListRooms()[i].getmName())
                    {
                        mRoom = RoomsGenerator.getListRooms()[i];
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        mPopUp.setTitle("Sélectionnez une valeur")
                .setView(v)
                .setPositiveButton("Filtrer",
                        (dialog, which) -> {
                            mListReunionsFragments.mReunions = mApiService.filterRoom(mRoom);
                            mListReunionsFragments.dataChanged();
                        })
                .setNegativeButton("Annuler",
                        (dialog, which) -> {});
        mPopUp.create().show();
    }
}
