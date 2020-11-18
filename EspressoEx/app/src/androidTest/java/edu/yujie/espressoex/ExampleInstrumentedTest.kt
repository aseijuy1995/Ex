package edu.yujie.espressoex

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("edu.yujie.espressoex", appContext.packageName)
    }

////    @get:Rule
//    val mainScenario = ActivityScenario.launch(MainActivity::class.java)
//
////    @get:Rule
//    val listScenario = ActivityScenario.launch(ListActivity::class.java)
//
//    fun testMainActivity() {
//        onView(withId(R.id.button)).check(matches(isDisplayed()))
//    }

//    @Test
//    fun testDisplayUser() {
////        Intents.init()
//
//        IntentsTestRule
//
//        onView(withId(R.id.button)).perform(click())
//        intended(hasComponent(ListActivity::class.java.name))
//        onView(withId(R.id.rvView)).check(matches(isDisplayed()))
////            .perform(
////                RecyclerViewActions.actionOnItemAtPosition<ListActivity.ListAdapter.ViewHolder>(
////                    0,
////                    click()
////                )
////            )
//    }
}