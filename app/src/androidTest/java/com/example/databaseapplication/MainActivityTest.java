package com.example.databaseapplication;

import android.content.Context;

import androidx.test.espresso.ViewInteraction;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void sentencesTableExists(){
        onView(withId(R.id.edtInput)).perform(typeText("Delete from Sentences"));
        closeSoftKeyboard();
        onView(withId(R.id.btnRunUpdate)).perform(click());
        onView(withId(R.id.edtInput)).perform(clearText());
        onView(withId(R.id.edtInput)).perform(typeText("SELECT * FROM Sentences"));
        closeSoftKeyboard();
        onView(withId(R.id.btnRunQuery)).perform(click());
        onView(withId(R.id.txtOutput)).check(matches(withText("")));
    }

    @Test
    public void unknownTableDoesNotExist(){
        onView(withId(R.id.edtInput)).perform(typeText("SELECT * FROM UnknownTable"));
        closeSoftKeyboard();
        onView(withId(R.id.btnRunQuery)).perform(click());
        onView(withId(R.id.txtOutput)).check(matches(withText("Table does not exist!")));
    }

    @Test
    public void tableInsertAndRetrieval(){
        onView(withId(R.id.edtInput)).perform(typeText("Delete from Sentences"));
        closeSoftKeyboard();
        onView(withId(R.id.btnRunUpdate)).perform(click());
        onView(withId(R.id.edtInput)).perform(clearText());
        onView(withId(R.id.edtInput)).perform(typeText("Insert Into Sentences Values('This Is My First Sentence!')"));
        closeSoftKeyboard();
        onView(withId(R.id.btnRunUpdate)).perform(click());
        onView(withId(R.id.edtInput)).perform(clearText());
        onView(withId(R.id.edtInput)).perform(typeText("SELECT * FROM Sentences"));
        closeSoftKeyboard();
        onView(withId(R.id.btnRunQuery)).perform(click());
        onView(withId(R.id.txtOutput)).check(matches(withText("This Is My First Sentence!")));
    }

//    @Test
//    public void multipleEntries(){
//        onView(withId(R.id.edtInput)).perform(typeText("Delete from Sentences"));
//        closeSoftKeyboard();
//        onView(withId(R.id.btnRunUpdate)).perform(click());
//        onView(withId(R.id.edtInput)).perform(clearText());
//        onView(withId(R.id.edtInput)).perform(typeText("Insert Into Sentences Values('Boom'),('Boom'),('Boom'),('Boom'),('Boom')"));
//        closeSoftKeyboard();
//        onView(withId(R.id.btnRunUpdate)).perform(click());
//        onView(withId(R.id.edtInput)).perform(clearText());
//        onView(withId(R.id.edtInput)).perform(typeText("SELECT * FROM Sentences"));
//        closeSoftKeyboard();
//        onView(withId(R.id.btnRunQuery)).perform(click());
//        onView(withId(R.id.txtOutput)).check(matches(withText("Boom\nBoom\nBoom\nBoom\nBoom")));
//    }

    @Test
    public void testButtonExists(){
        ViewInteraction button = onView(withId(R.id.btnRunQuery));
        onView(withId(R.id.btnRunQuery)).check(matches(isDisplayed()));
        button.check(matches(isDisplayed()));
    }

    /*@After
    public void tearDown() throws Exception {
    }*/
}