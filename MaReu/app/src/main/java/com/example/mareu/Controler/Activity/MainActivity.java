package com.example.mareu.Controler.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.Controler.Fragment.AddReunionFragment;
import com.example.mareu.Controler.Fragment.ListReunionsFragment;
import com.example.mareu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ListReunionsFragment mListReunionsFragments;
    private AddReunionFragment mAddReunionFragment;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureMainFragment();
        configureDetailsFragment();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.add_reunion_fragment) == null)
        {
           configureFaButton();
        }
    }

    public void configureMainFragment()
    {
        mListReunionsFragments = (ListReunionsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_reunions);

        if (mListReunionsFragments == null)
        {
            mListReunionsFragments = new ListReunionsFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_container, mListReunionsFragments).commit();
        }
    }

    private void configureDetailsFragment()
    {
        mAddReunionFragment = (AddReunionFragment) getSupportFragmentManager().findFragmentById(R.id.add_reunion_fragment);

        if(mAddReunionFragment == null && findViewById(R.id.activity_details_container) != null)
        {
            mAddReunionFragment = new AddReunionFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_details_container, mAddReunionFragment).commit();
        }
    }

    private void configureFaButton()
    {
        fab = findViewById(R.id.fab);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
