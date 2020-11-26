package edu.yujie.espressoex

import android.view.View
import android.widget.AdapterView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testMainActivity() {
        onView(withId(R.id.button))
            .perform(click())
        onView(withId(R.id.textView))
            .check(matches(withText("Hello Espresso!")))
    }

//    @Test // PerformException: Error performing 'load adapter data' on view 'is assignable from class: class android.widget.AdapterView'.
//    fun testSpinner() {
//        onView(withId(R.id.spinner))
//            .perform(click())
//        onData(allOf(`is`(instanceOf(String::class.java)), `is`("one")))
//            .perform(click())
//        onView(withId(R.id.tvSpinner))
//            .check(matches(withText(containsString("one"))))
//    }

//    @Test
//    fun testOptionMenu() {
//        onView(withId(xxx)).perform(click())
//        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
//        onView(withId(R.id.menuSearch))
//            .perform(click())
//        onView(withId(R.id.tvMenu))
//            .check(matches(withText("搜索")))
//    }

    @Test
    fun testVisibility() {
        onView(withId(R.id.tvDisplayed)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testExist() {
        onView(withId(R.id.tvMain2)).check(doesNotExist())
    }

    @Test
    fun testAdapterData() {
        onView(withId(R.id.spinner))
            .check(matches(object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description?) {
                    description?.appendText("one")
//                    dataMatcher.describeTo(description)
                }

                override fun matchesSafely(item: View?): Boolean {
                    if (item !is AdapterView<*>) {
                        return false
                    }
                    val adapter = item.adapter
                    for (i in 0 until adapter.count) {
//                        if(dataMatcher.matches(adapter.getItem(i))){
//                            return true
//                        }
                    }
                    return false
                }

            }))
    }
}