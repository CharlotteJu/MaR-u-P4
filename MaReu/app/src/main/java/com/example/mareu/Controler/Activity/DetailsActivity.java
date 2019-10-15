package com.example.mareu.Controler.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mareu.Controler.Fragment.AddReunionFragment;
import com.example.mareu.R;

public class DetailsActivity extends AppCompatActivity {

    private AddReunionFragment mAddReunionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        configureDetailsFragment();
    }

    private void configureDetailsFragment()
    {
        mAddReunionFragment = (AddReunionFragment) getSupportFragmentManager().findFragmentById(R.id.add_reunion_fragment);

        if(mAddReunionFragment == null)
        {
            mAddReunionFragment = new AddReunionFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_details_container, mAddReunionFragment).commit();
        }
    }
}
