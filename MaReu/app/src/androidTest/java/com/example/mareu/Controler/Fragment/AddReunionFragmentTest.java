package com.example.mareu.Controler.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.Controler.Activity.DetailsActivity;
import com.example.mareu.Controler.UtilsTests.DeleteViewActionMailsList;
import com.example.mareu.Model.Reunion;
import com.example.mareu.Model.Room;
import com.example.mareu.R;
import com.example.mareu.Services.DummyReunionApiService;
import com.example.mareu.Services.ReunionApiService;
import com.example.mareu.Services.RoomsGenerator;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.Controler.UtilsTests.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AddReunionFragmentTest
{

    private DetailsActivity mAddReunionActivity;
    private static int ITEMS_COUNT = 3;

    private ReunionApiService mApiService = new DummyReunionApiService();
    private List<Reunion> mReunions = mApiService.getReunions();
    private Room[] mRooms = RoomsGenerator.getListRooms();
    private List<String> aMails = new ArrayList<>();

    @Rule
    public ActivityTestRule<DetailsActivity> mActivityRule =
            new ActivityTestRule(DetailsActivity.class);

    @Before
    public void setUp()
    {
        mAddReunionActivity = mActivityRule.getActivity();
        assertThat(mAddReunionActivity, notNullValue());


    }

    @Test
    public void myAddReunion_clickButtonMails_addMails()
    {
        // Given : We put a String for the mail
        onView(ViewMatchers.withId(R.id.arf_mail)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_mail)).perform(replaceText("AdresseMail@test.fr"));

        // When : We click on the AddMail Button
        onView(ViewMatchers.withId(R.id.arf_add_mails_button)).perform(scrollTo(), click());

        // Then : The mails list is not empty
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).check(withItemCount(1));
    }

    @Test
    public void myAddReunion_clickDeleteMails_deleteMails()
    {
        // Given  : We put 2 Strings and Click on the AddMail Button
        onView(ViewMatchers.withId(R.id.arf_mail)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_mail)).perform(replaceText("AdresseMail@test.fr"));
        onView(ViewMatchers.withId(R.id.arf_add_mails_button)).perform(scrollTo(), click());

        onView(ViewMatchers.withId(R.id.arf_mail)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_mail)).perform(replaceText("AdresseMail2@test.fr"));
        onView(ViewMatchers.withId(R.id.arf_add_mails_button)).perform(scrollTo(), click());

        onView(ViewMatchers.withId(R.id.arf_liste_mails)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).check(withItemCount(2));

        // When : We click on the delete Button for the mail
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewActionMailsList()));

        // Then : The mails list is not empty
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_liste_mails)).check(withItemCount(1));
    }

    @Test
    public void myAddReunion_clickSpinnerHour_showSpinner()
    {
        // When : We click on the spinner Hour and choose 10
        onView(ViewMatchers.withId(R.id.arf_hours_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)),is("10"))).perform(click());

        //Then : The spinner has like text the good hour
        onView(withId(R.id.arf_hours_spinner)).check(matches(withSpinnerText(containsString("10"))));
    }

    @Test
    public void myAddReunion_clickDate_showDialogPicker()
    {
        // When : We click on the date button and choose a date
        onView(ViewMatchers.withId(R.id.arf_date)).perform(scrollTo(), click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2020, 6, 30));
        onView(withId(android.R.id.button1)).perform(click());

        // Then : The text date is good
        onView(withId(R.id.arf_date_txt)).check(matches(allOf(withText("30/06/2020"),
                isDisplayed())));
    }


    @Test
    public void myAddReunion_clickSpinnerRoom_showSpinner()
    {
        // Given : We choose a date and an hour
        onView(ViewMatchers.withId(R.id.arf_date)).perform(scrollTo(), click());
        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2020, 6, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.arf_date_txt)).check(matches(allOf(withText("30/06/2020"),
                isDisplayed())));

        onView(ViewMatchers.withId(R.id.arf_hours_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)),is("10"))).perform(click());
        onView(withId(R.id.arf_hours_spinner)).check(matches(withSpinnerText(containsString("10"))));

        // When : We click on the spinner Room and choose a room
        onView(ViewMatchers.withId(R.id.arf_room)).check(matches(isClickable()));
        onView(ViewMatchers.withId(R.id.arf_room)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)),is("Salle 1"))).perform(click());

        // Then : The spinner has like text the good room
        onView(withId(R.id.arf_room)).check(matches(withSpinnerText(containsString("Salle 1"))));

    }



    @Test
    public void myAddReunion_clickButtonCreate_addReunion()
    {
        // When : We complete and click on the create button
        onView(ViewMatchers.withId(R.id.arf_subject_edit_text)).perform(scrollTo(), click());
        onView(ViewMatchers.withId(R.id.arf_subject_edit_text)).perform(replaceText("Réunion Test"));
        onView(ViewMatchers.withId(R.id.arf_date)).perform(scrollTo(), click());

        onView(isAssignableFrom(DatePicker.class)).perform(PickerActions.setDate(2020, 6, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.arf_date_txt)).check(matches(allOf(withText("30/06/2020"),
                isDisplayed())));

        onView(ViewMatchers.withId(R.id.arf_hours_spinner)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)),is("10"))).perform(click());
        onView(withId(R.id.arf_hours_spinner)).check(matches(withSpinnerText(containsString("10"))));


        onView(ViewMatchers.withId(R.id.arf_room)).check(matches(isClickable()));


        onView(ViewMatchers.withId(R.id.arf_room)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)),is("Salle 1"))).perform(click());
        onView(withId(R.id.arf_room)).check(matches(withSpinnerText(containsString("Salle 1"))));

        onView(ViewMatchers.withId(R.id.arf_mail)).perform(replaceText("AdresseMail@test.fr"));
        onView(ViewMatchers.withId(R.id.arf_add_mails_button)).perform(scrollTo(), click());

        onView(withId(R.id.add_reunion_fragment)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                ((LinearLayout) view).addView(new Button(view.getContext()));
            }
        });

        onView(ViewMatchers.withId(R.id.arf_fab)).perform(scrollTo(), click());

        // Then : The reunion is added on the ReunionList
        onView(ViewMatchers.withId(R.id.fragment_list_reunions)).check(withItemCount(ITEMS_COUNT + 1));
    }

}