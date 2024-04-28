package com.example.myapplication

import android.app.Activity
import android.app.Instrumentation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.number.OrderingComparison.greaterThan
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.test.espresso.intent.Intents
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.intent.matcher.IntentMatchers.isInternal
import org.hamcrest.CoreMatchers.equalTo
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction

import android.graphics.drawable.Drawable
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.RootMatchers.isDialog
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


@RunWith(AndroidJUnit4::class)
class TestS2 {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun testSlikanje() {
        Intents.init()
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        onView(withId(R.id.uslikajBiljkuBtn)).perform(click())
        Intents.intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
        Intents.release()
    }


    @Test
    fun intentDodavanjeBiljke() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.novaBiljkaBtn)).perform(click())

        onView(withId(R.id.nazivET)).check(matches(isDisplayed()))
        onView(withId(R.id.porodicaET)).check(matches(isDisplayed()))
        onView(withId(R.id.jeloET)).check(matches(isDisplayed()))
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(isDisplayed()))
        onView(withId(R.id.jelaLV)).check(matches(isDisplayed()))
        onView(withId(R.id.klimatskiTipLV)).check(matches(isDisplayed()))
        onView(withId(R.id.medicinskaKoristLV)).check(matches(isDisplayed()))
        onView(withId(R.id.profilOkusaLV)).check(matches(isDisplayed()))
        onView(withId(R.id.zemljisniTipLV)).check(matches(isDisplayed()))
        onView(withId(R.id.dodajBiljkuBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.dodajJeloBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.uslikajBiljkuBtn)).check(matches(isDisplayed()))
    }


        @Test
        fun dodajDruguBiljku() {

            onView(withId(R.id.novaBiljkaBtn)).perform(click())

            onView(withId(R.id.nazivET)).perform(typeText("Nova Biljka"))

            onView(withId(R.id.porodicaET)).perform(typeText("Nova Porodica"))

            onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Medicinsko upozorenje za novu biljku"))

            onView(withId(R.id.jeloET)).perform(typeText("Jelo za novu biljku"))
            onView(withId(R.id.dodajJeloBtn)).perform(click())

            onData(anything())
                .inAdapterView(withId(R.id.medicinskaKoristLV))
                .atPosition(0)
                .perform(click())

            onData(anything())
                .inAdapterView(withId(R.id.klimatskiTipLV))
                .atPosition(0).perform(click())

            onData(anything())
                .inAdapterView(withId(R.id.zemljisniTipLV))
                .atPosition(0)
                .perform(click())
            onData(anything())
                .inAdapterView(withId(R.id.profilOkusaLV))
                .atPosition(0)
                .perform(click())

            onView(withId(R.id.dodajBiljkuBtn)).perform(click())



            onView(withId(R.id.biljkeRV)).perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    allOf(
                        hasDescendant(withText("Nova Biljka")),
                        hasDescendant(withId(R.id.nazivItem))
                    )
                )
            )
    }


}